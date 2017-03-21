package Core.ComplexComponents;

import java.io.EOFException;

import org.eclipse.swt.widgets.Display;

import Core.ActorComponent;
import Core.ConsumingPort;
import Core.ProducingPort;

public class TimeExtendComponent extends ActorComponent {

	public TimeExtendComponent() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static final String PORT_ID_INPUT = "input";
	public static final String PORT_ID_OUTPUT = "output";
	public static final String ATTRIBUTE_EXTEND_TIME = "extend time";

	private TimeExtendComponent me;
	private Thread thr;

	public TimeExtendComponent(String id) {
		super(id);
	}

	@Override
	public void exec() {
		if (!this.SecurityIsStopped) {

			if (getPort(PORT_ID_INPUT).getState()) {
				getPort(PORT_ID_OUTPUT).setState(true);
				thr.stop();
			}

			if (!getPort(PORT_ID_INPUT).getState()
					&& getPort(PORT_ID_OUTPUT).getState()) {
				thr = new Thread(new Runnable() {
					public void run() {
						try {
							thr.sleep(Long.parseLong(me
									.getAttribute(ATTRIBUTE_EXTEND_TIME)));
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						me.getPort(PORT_ID_OUTPUT).setState(false);
					}
				});
				;
				thr.start();
			}

			super.exec();
		}
	}

	@Override
	public void init() {

		this.setAttribute(ATTRIBUTE_EXTEND_TIME, "1000");
		this.addPort(new ConsumingPort(PORT_ID_INPUT, this));
		this.addPort(new ProducingPort(PORT_ID_OUTPUT));

		me = this;
		thr = new Thread(new Runnable() {
			public void run() {
				try {
					thr.sleep(Long.parseLong(me
							.getAttribute(ATTRIBUTE_EXTEND_TIME)));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				me.getPort(PORT_ID_OUTPUT).setState(false);
			}
		});
		;

		super.init();
	}

	@Override
	public void finalies() {
		thr.interrupt();
		// TODO Auto-generated method stub
		super.finalies();
	}

}
