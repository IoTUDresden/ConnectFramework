package IO;

import Core.ProducingPort;
import Core.SensorComponent;

public class EmotiveEpocExpressionComponent extends SensorComponent {

	public final String EMG_PORT_BLINK = "Blink";
	public final String EMG_PORT_WINKL = "WinkL";
	public final String EMG_PORT_WINKR = "WinkR";
	public final String EMG_PORT_LOOKL = "LookL";
	public final String EMG_PORT_LOOKR = "LookR";
	public final String EMG_PORT_RAISEBROW = "RaiseBrow";
	public final String EMG_PORT_FURROWBROW = "FurrowBrow";
	public final String EMG_PORT_SMILE = "Smile";
	public final String EMG_PORT_CLENCH = "Clench";
	public final String EMG_PORT_RIGHTSMIRK = "RightSmirk";
	public final String EMG_PORT_LEFTSMIRK = "LeftSmirk";
	public final String EMG_PORT_LAUGH = "Laugh";
	
	
	@Override
	public void exec() {
		// TODO Auto-generated method stub
		super.exec();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		super.init();
		
		this.addPort(new ProducingPort(this.EMG_PORT_BLINK));
		this.addPort(new ProducingPort(this.EMG_PORT_WINKL));
		this.addPort(new ProducingPort(this.EMG_PORT_WINKR));
		this.addPort(new ProducingPort(this.EMG_PORT_LOOKL));
		this.addPort(new ProducingPort(this.EMG_PORT_LOOKR));
		this.addPort(new FloatLimitProducingPort(this.EMG_PORT_RAISEBROW));
		this.addPort(new FloatLimitProducingPort(this.EMG_PORT_FURROWBROW));
		this.addPort(new FloatLimitProducingPort(this.EMG_PORT_SMILE));
		this.addPort(new FloatLimitProducingPort(this.EMG_PORT_CLENCH));
		this.addPort(new FloatLimitProducingPort(this.EMG_PORT_RIGHTSMIRK));
		this.addPort(new FloatLimitProducingPort(this.EMG_PORT_LEFTSMIRK));
		this.addPort(new FloatLimitProducingPort(this.EMG_PORT_LAUGH));
		
		EmotivClient.getInstance().setEec(this);
	}

	@Override
	public void finalies() {
		EmotivClient.getInstance().UnsetEmotiveEpocExpressionComponent();
		if(EmotivClient.getInstance().isUseless()) EmotivClient.getInstance().interrupt();
		// TODO Auto-generated method stub
		super.finalies();
	}
	
}
