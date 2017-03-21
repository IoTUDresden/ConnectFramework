package Core.ComplexComponents;

import java.io.EOFException;

import org.eclipse.swt.widgets.Display;

import Core.ActorComponent;
import Core.ConsumingPort;
import Core.ProducingPort;

public class DoubleEventComponent extends ActorComponent {

	public DoubleEventComponent() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static final String PORT_ID_INPUT = "input";
	public static final String PORT_ID_OUTPUT = "output";
	public static final String ATTRIBUTE_LIMIT_TIME = "time limit";
	public static final String ATTRIBUTE_EXTEND_TIME = "extend time";
	public static final String ATTRIBUTE_PAUSE_TIME = "pause time";

	private DoubleEventComponent me;
	private Thread thr2;

	private boolean firstEvent = false;
	private boolean secondEvent = false;
	private boolean pause = false;
	private boolean inThread = false;
	long beginOutput = -1;

	public DoubleEventComponent(String id) {
		super(id);
	}

	@Override
	public void exec() {
		if (!this.SecurityIsStopped) {
			if (!inThread) {
				if (this.getPort(this.PORT_ID_INPUT).getState()) {
					final long time_limit = Long.parseLong(this
							.getAttribute(ATTRIBUTE_LIMIT_TIME)) / 10;
					final long time_extend = Long.parseLong(this
							.getAttribute(ATTRIBUTE_EXTEND_TIME)) / 10;
					final long time_pause = Long.parseLong(this
							.getAttribute(ATTRIBUTE_PAUSE_TIME)) / 10;

					thr2 = new Thread(new Runnable() {
						public void run() {
							long n = 0;

							inThread = true;
							// System.out.println("RunLimitTimer");
							while (inThread) {
								try {
									Thread.sleep(10);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								System.out.println(n);

								if (!secondEvent) {
									if (!firstEvent) {
										System.out.println("!first && !second");
										if (!me.getPort(me.PORT_ID_INPUT)
												.getState()) {
											firstEvent = true;
											System.out
													.println("set firstEvent");
										}
									} else {
										System.out.println("first && !second");
										if (n > time_pause
												&& me.getPort(me.PORT_ID_INPUT)
														.getState()) {
											secondEvent = true;
											System.out
													.println("set secondEvent");
										}
										n += 1;
									}
								} else {
									System.out.println("xfrst && second");
									if (beginOutput == -1) {
										if (!me.getPort(me.PORT_ID_INPUT)
												.getState()) {
											System.out.println("beginOutput");
											beginOutput = n;
											me.getPort(me.PORT_ID_OUTPUT)
													.setState(true);
										}
									} else {
										if (n > beginOutput + time_extend) {
											me.getPort(me.PORT_ID_OUTPUT)
													.setState(false);
											System.out.println("endOutput");
											beginOutput = -1;
											firstEvent = false;
											secondEvent = false;
											inThread = false;
										}
									}
									n += 1;
								}
								if (n > time_limit + time_extend) {
									beginOutput = -1;
									firstEvent = false;
									secondEvent = false;
									inThread = false;
								}

							}
						}
					});
					;
					thr2.start();
				}
			}

			super.exec();
		}
	}

	@Override
	public void init() {

		// this.setAttribute(ATTRIBUTE_EXTEND_TIME, "1000");
		this.setAttribute(ATTRIBUTE_LIMIT_TIME, "5000");
		this.setAttribute(ATTRIBUTE_EXTEND_TIME, "1000");
		this.setAttribute(ATTRIBUTE_PAUSE_TIME, "1000");
		this.addPort(new ConsumingPort(PORT_ID_INPUT, this));
		this.addPort(new ProducingPort(PORT_ID_OUTPUT));

		me = this;

		super.init();
	}

	@Override
	public void finalies() {
		thr2.interrupt();
		super.finalies();
	}

}
