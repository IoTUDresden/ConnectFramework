package IO;

import Core.ProducingPort;
import Core.SensorComponent;

public class EmotiveEpocCognitivComponent extends SensorComponent {

	public final String EEGC_PORT_NEUTRAL = "Neutral";
	public final String EEGC_PORT_PUSH = "Push";
	public final String EEGC_PORT_PULL = "Pull";
	public final String EEGC_PORT_LIFT = "Lift";
	public final String EEGC_PORT_DROP = "Drop";
	public final String EEGC_PORT_LEFT = "Left";
	public final String EEGC_PORT_RIGHT = "Right";
	public final String EEGC_PORT_ROTLEFT = "Rotate Left";
	public final String EEGC_PORT_ROTRIGHT = "Rotate Right";
	public final String EEGC_PORT_ROTFORWARD = "Rotate Forward";
	public final String EEGC_PORT_ROTBACKWARD = "Rotate Backward";
	public final String EEGC_PORT_ROTCLOCKWISE = "Rotate Clockwise";
	public final String EEGC_PORT_ROTCOUTERCLOCK = "Rotate Counter Clockwise";
	public final String EEGC_PORT_DISAPPEAR = "Disappear";	
	
	@Override
	public void exec() {
		// TODO Auto-generated method stub
		super.exec();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		super.init();
		
		this.addPort(new FloatLimitProducingPort(this.EEGC_PORT_NEUTRAL));
		this.addPort(new FloatLimitProducingPort(this.EEGC_PORT_PUSH));
		this.addPort(new FloatLimitProducingPort(this.EEGC_PORT_PULL));
		this.addPort(new FloatLimitProducingPort(this.EEGC_PORT_LIFT));
		this.addPort(new FloatLimitProducingPort(this.EEGC_PORT_DROP));
		this.addPort(new FloatLimitProducingPort(this.EEGC_PORT_LEFT));
		this.addPort(new FloatLimitProducingPort(this.EEGC_PORT_RIGHT));
		this.addPort(new FloatLimitProducingPort(this.EEGC_PORT_ROTLEFT));
		this.addPort(new FloatLimitProducingPort(this.EEGC_PORT_ROTRIGHT));
		this.addPort(new FloatLimitProducingPort(this.EEGC_PORT_ROTFORWARD));
		this.addPort(new FloatLimitProducingPort(this.EEGC_PORT_ROTBACKWARD));
		this.addPort(new FloatLimitProducingPort(this.EEGC_PORT_ROTCLOCKWISE));
		this.addPort(new FloatLimitProducingPort(this.EEGC_PORT_ROTCOUTERCLOCK));
		this.addPort(new FloatLimitProducingPort(this.EEGC_PORT_DISAPPEAR));
				
		EmotivClient.getInstance().setEcc(this);
	}

	@Override
	public void finalies() {
		EmotivClient.getInstance().UnsetEmotiveEpocCognitivComponent();
		if(EmotivClient.getInstance().isUseless()) EmotivClient.getInstance().interrupt();
		// TODO Auto-generated method stub
		super.finalies();
	}
	
}
