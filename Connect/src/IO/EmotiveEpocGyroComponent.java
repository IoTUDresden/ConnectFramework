package IO;

import Core.ComplexComponent;
import Core.ConsumingPort;
import Core.ProducingPort;
import Core.SensorComponent;

public class EmotiveEpocGyroComponent extends ComplexComponent {

	public final String GYRO_PORT_TOP = "Top";
	public final String GYRO_PORT_DOWN = "Down";
	public final String GYRO_PORT_LEFT = "Left";
	public final String GYRO_PORT_RIGHT = "Right";
	public final String GYRO_PORT_RECALIB = "Recalbribration";
	
	public final String ATTRINBUT_FIELD_RADIUS = "Radius";
	
	
	
	@Override
	public void exec() {
		// TODO Auto-generated method stub
		
		if(this.getPort(GYRO_PORT_RECALIB).getState())
		{
			EmotivClient.getInstance().recalibateGyro();
		}
		
		super.exec();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		super.init();
		
		this.setAttribute(ATTRINBUT_FIELD_RADIUS, "0.0005");
		
		this.addPort(new FloatLimitProducingPort(this.GYRO_PORT_TOP));
		this.addPort(new FloatLimitProducingPort(this.GYRO_PORT_DOWN));
		this.addPort(new FloatLimitProducingPort(this.GYRO_PORT_LEFT));
		this.addPort(new FloatLimitProducingPort(this.GYRO_PORT_RIGHT));
		this.addPort(new ConsumingPort(this.GYRO_PORT_RECALIB, this));

		
		EmotivClient.getInstance().setGyro(this);
	}

	@Override
	public void finalies() {
		EmotivClient.getInstance().UnsetEmotiveEpocGyroComponent();
		if(EmotivClient.getInstance().isUseless()) EmotivClient.getInstance().interrupt();
		// TODO Auto-generated method stub
		super.finalies();
	}
	
}
