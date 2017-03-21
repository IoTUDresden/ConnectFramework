package IO;

import Core.ProducingPort;
import Core.SensorComponent;

public class EmotiveEpocAffectivComponent extends SensorComponent {

	public final String EEG_PORT_FRUSTRATION = "Frustration";
	public final String EEG_PORT_ENGAGEMENT = "Engagement";
	public final String EEG_PORT_MEDITATION = "Meditation";
	public final String EEG_PORT_INST_EXCITEMENT = "Inst. Excitement";
	public final String EEG_PORT_LT_EXCITEMENT = "Long Term Excitement";
	
	
	@Override
	public void exec() {
		// TODO Auto-generated method stub
		super.exec();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		super.init();
		
		this.addPort(new FloatLimitProducingPort(this.EEG_PORT_FRUSTRATION));
		this.addPort(new FloatLimitProducingPort(this.EEG_PORT_ENGAGEMENT));
		this.addPort(new FloatLimitProducingPort(this.EEG_PORT_MEDITATION));
		this.addPort(new FloatLimitProducingPort(this.EEG_PORT_INST_EXCITEMENT));
		this.addPort(new FloatLimitProducingPort(this.EEG_PORT_LT_EXCITEMENT));
		
		EmotivClient.getInstance().setEac(this);
	}

	@Override
	public void finalies() {
		EmotivClient.getInstance().UnsetEmotiveEpocAffectivComponent();
		if(EmotivClient.getInstance().isUseless()) 
		{
			EmotivClient.getInstance().stop();
		}// TODO Auto-generated method stub
		super.finalies();
	}
	
}
