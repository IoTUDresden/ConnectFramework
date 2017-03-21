package IO;

import Core.Component;

public class TabletGyroComponent extends Component {

	public final String GYRO_PORT_TOP = "Up";
	public final String GYRO_PORT_DOWN = "Down";
	public final String GYRO_PORT_LEFT = "Left";
	public final String GYRO_PORT_RIGHT = "Right";
	
	@Override
	public void init() {
		super.init();
		this.addPort(new FloatLimitProducingPort(this.GYRO_PORT_TOP));
		this.addPort(new FloatLimitProducingPort(this.GYRO_PORT_DOWN));
		this.addPort(new FloatLimitProducingPort(this.GYRO_PORT_LEFT));
		this.addPort(new FloatLimitProducingPort(this.GYRO_PORT_RIGHT));
		TabletClient.getInstance().setGyro(this);
	}
	
	@Override
	public void finalies() {
		TabletClient.getInstance().unsetGyro();
		super.finalies();
	}
	
}
