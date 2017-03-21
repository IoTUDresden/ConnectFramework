package IO;

import java.util.ArrayList;
import java.util.List;

import Core.*;

public class RosTurtleBotComponent extends ActorComponent {

	RobotClient robot;

	public static final String PORT_MOVEFORWARD = "MoveForward";
	public static final String PORT_MOVEBACKWARD = "MoveBackward";
	public static final String PORT_MOVELEFT = "MoveLeft";
	public static final String PORT_MOVERIGHT = "MoveRight";

	public static final String ATTRIBUT_IP = "IP";
	public static final String ATTRIBUT_SPEED = "Speed (0-10)";
	public static final String ATTRIBUT_LOOP_TIME = "Loop-Time (ms)";

	private Thread thr;
	private boolean isRunning = true;
	private RosTurtleBotComponent me;

	// public static enum Ports {
	// MoveForward(0), MoveBackward(1), MoveLeft(2), MoveRight(3);
	// private int value;
	//
	// private Ports(int value) {
	// this.value = value;
	// }
	//
	// public int getValue() {
	// return value;
	// }
	// };

	public RosTurtleBotComponent(String name) {
		super(name);

	}

	public RosTurtleBotComponent() {
		super();
	}

	// // @Override
	// // protected List<String> getPortNames() {
	// // List<String> portNames = new ArrayList<String>();
	// //
	// // Ports[] concretPorts = Ports.values();
	// //
	// // for (int n = 0; n < concretPorts.length; n++) {
	// // portNames.add(concretPorts[n].name());
	// // }
	// //
	// // return portNames;
	// // }
	//
	// protected void addPorts() {
	// for (int n = 0; n < Ports.values().length; n++) {
	// addPort(Ports.values()[n].name());
	// }
	// }

	public void init() {
		this.addPort(new ConsumingPort(PORT_MOVEFORWARD, this));
		this.addPort(new ConsumingPort(PORT_MOVEBACKWARD, this));
		this.addPort(new ConsumingPort(PORT_MOVELEFT, this));
		this.addPort(new ConsumingPort(PORT_MOVERIGHT, this));

		setAttribute(ATTRIBUT_IP, "ws://192.168.1.3:9090");
		setAttribute(ATTRIBUT_SPEED, "3");
		setAttribute(ATTRIBUT_LOOP_TIME, "10");

		robot = new RobotClient(getAttribute(ATTRIBUT_IP));
		robot.connect();

		me = this;

		thr = new Thread(new Runnable() {
			public void run() {
				while (isRunning) {
					try {
						thr.sleep(Long.parseLong(me
								.getAttribute(me.ATTRIBUT_LOOP_TIME)));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					me.exec();
				}

			}
		});
		;
		thr.start();
	}

	public void exec() {
		if (!this.SecurityIsStopped) {
			if (!getAttribute(ATTRIBUT_IP).equals(robot.getWs())) {
				robot.close();
				robot = new RobotClient(getAttribute(ATTRIBUT_IP));
				robot.connect();
			}
			if (Float.parseFloat(getAttribute(ATTRIBUT_SPEED)) != robot
					.getSpeed())
				robot.setSpeed(Float.parseFloat(getAttribute(ATTRIBUT_SPEED)));

			boolean f = this.getPort(PORT_MOVEFORWARD).getState();
			boolean b = this.getPort(PORT_MOVEBACKWARD).getState();
			boolean l = this.getPort(PORT_MOVELEFT).getState();
			boolean r = this.getPort(PORT_MOVERIGHT).getState();

			if (f) {
				if (l) {
					robot.doMoveForwardLeft();
				} else {
					if (r) {
						robot.doMoveForwardRight();
					} else {
						robot.doMoveForward();
					}
				}
			} else {
				if (b) {
					if (l) {
						robot.doMoveBackwardLeft();
					} else {
						if (r) {
							robot.doMoveBackwardRight();
						} else {
							robot.doMoveBackward();
						}
					}
				} else {
					if (l) {
						robot.doMoveLeft();
					} else {
						if (r)
							robot.doMoveRight();
					}
				}
			}
		}
	}

	protected void finalize() throws Throwable {
		robot.close();
		thr.interrupt();
	}

	public RobotClient getRobot(){
		return robot;
	}
	
}
