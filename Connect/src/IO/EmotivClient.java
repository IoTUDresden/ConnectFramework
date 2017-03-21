package IO;

import Core.Component;
import Core.ProducingPort;
import Edk.Edk;
import Edk.EdkErrorCode;
import Edk.EmoState;

import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

public class EmotivClient extends Thread {

	private Pointer eEvent;
	private Pointer eState;
	private String ip = "127.0.0.1";
	private short port = 3008;
	private static EmotivClient me;
	private long n = 0;

	private ProducingPort emgPortBlink;
	private ProducingPort emgPortWinkL;
	private ProducingPort emgPortWinkR;
	private ProducingPort emgPortLookL;
	private ProducingPort emgPortLookR;
	private FloatLimitProducingPort emgPortRaiseBrow;
	private FloatLimitProducingPort emgPortFurrowBrow;
	private FloatLimitProducingPort emgPortSmile;
	private FloatLimitProducingPort emgPortClench;
	private FloatLimitProducingPort emgPortLeftSmirk;
	private FloatLimitProducingPort emgPortRightSmirk;
	private FloatLimitProducingPort emgPortLaught;
	
	private FloatLimitProducingPort eegEngegement;
	private FloatLimitProducingPort eegFrustration;
	private FloatLimitProducingPort eegMeditation;
	private FloatLimitProducingPort eegInstExcitement;
	private FloatLimitProducingPort eegLTExcitement;
	
	private FloatLimitProducingPort eegcNeutral;
	private FloatLimitProducingPort eegcPush;
	private FloatLimitProducingPort eegcPull;
	private FloatLimitProducingPort eegcLift;
	private FloatLimitProducingPort eegcDrop;
	private FloatLimitProducingPort eegcLeft;
	private FloatLimitProducingPort eegcRight;
	private FloatLimitProducingPort eegcRotLeft;
	private FloatLimitProducingPort eegcRotRight;
	private FloatLimitProducingPort eegcRotBackward;
	private FloatLimitProducingPort eegcRotForward;
	private FloatLimitProducingPort eegcRotClockwise;
	private FloatLimitProducingPort eegcRotCounterClockwise;
	private FloatLimitProducingPort eegcDisappear;

	private EmotiveEpocExpressionComponent eec;
	private EmotiveEpocAffectivComponent eac;
	private EmotiveEpocCognitivComponent ecc;
	private EmotiveEpocGyroComponent gyro;
	
	private FloatLimitProducingPort gyroTop;
	private FloatLimitProducingPort gyroDown;
	private FloatLimitProducingPort gyroLeft;
	private FloatLimitProducingPort gyroRight;
	
	IntByReference x;
	IntByReference y;

	public EmotivClient() {
		eEvent = Edk.INSTANCE.EE_EmoEngineEventCreate();
		eState = Edk.INSTANCE.EE_EmoStateCreate();
		me = this;
		x = new IntByReference(0);
		y = new IntByReference(0);
		connectEpoc();
		this.start();
	}

	public static EmotivClient getInstance() {
		if (me == null) {
			me = new EmotivClient();
		}
		return me;
	}

	@Override
	public void run() {
		while (true) {
			loop();
			try {
				me.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void connectEpoc() {
		System.out.println("Target IP of EmoComposer: " + ip + ":" + port);

		if (Edk.INSTANCE.EE_EngineRemoteConnect(ip, port, "Emotiv Systems-5") != EdkErrorCode.EDK_OK
				.ToInt()) {
			System.out.println("Cannot connect to EmoComposer on " + ip + ":"
					+ port);
		} else {
			System.out
					.println("Connected to EmoComposer on " + ip + ":" + port);
		}
	}

	public void loop() {
		// System.out.print("Loop");
		if (Edk.INSTANCE.EE_EngineGetNextEvent(eEvent) == EdkErrorCode.EDK_OK
				.ToInt()) {
			if (Edk.INSTANCE.EE_EmoEngineEventGetType(eEvent) == Edk.EE_Event_t.EE_EmoStateUpdated
					.ToInt()) {
				Edk.INSTANCE.EE_EmoEngineEventGetEmoState(eEvent, eState);

				// Expressive
				if (eec != null) {
					// Binäre Werte
					this.emgPortBlink.setState(EmoState.INSTANCE
							.ES_ExpressivIsBlink(eState) == 1);
					this.emgPortWinkL.setState(EmoState.INSTANCE
							.ES_ExpressivIsLeftWink(eState) == 1);
					this.emgPortWinkR.setState(EmoState.INSTANCE
							.ES_ExpressivIsRightWink(eState) == 1);
					this.emgPortLookL.setState(EmoState.INSTANCE
							.ES_ExpressivIsLookingLeft(eState) == 1);
					this.emgPortLookR.setState(EmoState.INSTANCE
							.ES_ExpressivIsLookingRight(eState) == 1);

					// Floatwerte
					if (EmoState.INSTANCE
							.ES_ExpressivGetUpperFaceAction(eState) == 32)
						this.emgPortRaiseBrow.setValue(EmoState.INSTANCE
								.ES_ExpressivGetUpperFaceActionPower(eState)); else this.emgPortRaiseBrow.setValue(0);
					if (EmoState.INSTANCE
							.ES_ExpressivGetUpperFaceAction(eState) == 64)
						this.emgPortFurrowBrow.setValue(EmoState.INSTANCE
								.ES_ExpressivGetUpperFaceActionPower(eState)); else this.emgPortFurrowBrow.setValue(0);

					if (EmoState.INSTANCE
							.ES_ExpressivGetLowerFaceAction(eState) == 128)
						this.emgPortSmile.setValue(EmoState.INSTANCE
								.ES_ExpressivGetLowerFaceActionPower(eState)); else this.emgPortSmile.setValue(0);
					if (EmoState.INSTANCE
							.ES_ExpressivGetLowerFaceAction(eState) == 256)
						this.emgPortClench.setValue(EmoState.INSTANCE
								.ES_ExpressivGetLowerFaceActionPower(eState)); else this.emgPortClench.setValue(0);
					if (EmoState.INSTANCE
							.ES_ExpressivGetLowerFaceAction(eState) == 1024)
						this.emgPortLeftSmirk.setValue(EmoState.INSTANCE
								.ES_ExpressivGetLowerFaceActionPower(eState)); else this.emgPortLeftSmirk.setValue(0);
					if (EmoState.INSTANCE
							.ES_ExpressivGetLowerFaceAction(eState) == 2048)
						this.emgPortRightSmirk.setValue(EmoState.INSTANCE
								.ES_ExpressivGetLowerFaceActionPower(eState)); else this.emgPortRightSmirk.setValue(0);
					if (EmoState.INSTANCE
							.ES_ExpressivGetLowerFaceAction(eState) == 512)
						this.emgPortLaught.setValue(EmoState.INSTANCE
								.ES_ExpressivGetLowerFaceActionPower(eState)); else this.emgPortLaught.setValue(0);
				}
				
				if (eac != null) {
					this.eegEngegement.setValue(EmoState.INSTANCE.ES_AffectivGetEngagementBoredomScore(eState));
					this.eegFrustration.setValue(EmoState.INSTANCE.ES_AffectivGetFrustrationScore(eState));
					this.eegMeditation.setValue(EmoState.INSTANCE.ES_AffectivGetMeditationScore(eState));
					this.eegInstExcitement.setValue(EmoState.INSTANCE.ES_AffectivGetExcitementShortTermScore(eState));
					this.eegLTExcitement.setValue(EmoState.INSTANCE.ES_AffectivGetExcitementLongTermScore(eState));
				}
				
				if(ecc != null)
				{
					if(EmoState.INSTANCE.ES_CognitivIsActive(eState)==1)
					{
						int action = EmoState.INSTANCE.ES_CognitivGetCurrentAction(eState);
						float power = EmoState.INSTANCE.ES_CognitivGetCurrentActionPower(eState);
						
						if(action == 1) this.eegcNeutral.setValue(power); else this.eegcNeutral.setValue(0);
						if(action == 2) this.eegcPush.setValue(power); else this.eegcPush.setValue(0);
						if(action == 4) this.eegcPull.setValue(power); else this.eegcPull.setValue(0);
						if(action == 8) this.eegcLift.setValue(power); else this.eegcLift.setValue(0);
						if(action == 16) this.eegcDrop.setValue(power); else this.eegcDrop.setValue(0);
						if(action == 32) this.eegcLeft.setValue(power); else this.eegcLeft.setValue(0);
						if(action == 64) this.eegcRight.setValue(power); else this.eegcRight.setValue(0);
						if(action == 128) this.eegcRotLeft.setValue(power); else this.eegcRotLeft.setValue(0);
						if(action == 256) this.eegcRotRight.setValue(power); else this.eegcRotRight.setValue(0);
						if(action == 512) this.eegcRotClockwise.setValue(power); else this.eegcRotClockwise.setValue(0);
						if(action == 1024) this.eegcRotCounterClockwise.setValue(power); else this.eegcRotCounterClockwise.setValue(0);
						if(action == 2048) this.eegcRotForward.setValue(power); else this.eegcRotForward.setValue(0);
						if(action == 4096) this.eegcRotBackward.setValue(power); else this.eegcRotBackward.setValue(0);
						if(action == 8192) this.eegcDisappear.setValue(power); else this.eegcDisappear.setValue(0);
						
					}
					
				}
				
				if(gyro != null)
				{
				
					IntByReference tmpX = new IntByReference(0);
					IntByReference tmpY = new IntByReference(0);
					
					Edk.INSTANCE.EE_HeadsetGetGyroDelta(0, tmpX, tmpY);
					
					x.setValue(x.getValue() + tmpX.getValue());
					y.setValue(y.getValue() + tmpY.getValue());
					
					//System.out.println("x: "+x.getValue()+" | y: "+y.getValue());
					
					if(x.getValue()<=0)
					{
						this.gyroLeft.setValue(-1*x.getValue()*Float.parseFloat(gyro.getAttribute(gyro.ATTRINBUT_FIELD_RADIUS)));
						this.gyroRight.setValue(0);
					} else {
						this.gyroRight.setValue(x.getValue()*Float.parseFloat(gyro.getAttribute(gyro.ATTRINBUT_FIELD_RADIUS)));
						this.gyroLeft.setValue(0);
					}
					
					if(y.getValue()<=0)
					{
						this.gyroDown.setValue(-1*y.getValue()*Float.parseFloat(gyro.getAttribute(gyro.ATTRINBUT_FIELD_RADIUS)));
						this.gyroTop.setValue(0);
					} else {
						this.gyroTop.setValue(y.getValue()*Float.parseFloat(gyro.getAttribute(gyro.ATTRINBUT_FIELD_RADIUS)));
						this.gyroDown.setValue(0);
					}
				
				}

			}
		}
	}

	public void closeConnection() {
		Edk.INSTANCE.EE_EngineDisconnect();
		Edk.INSTANCE.EE_EmoEngineEventFree(eEvent);
		Edk.INSTANCE.EE_EmoStateFree(eState);
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public short getPort() {
		return port;
	}

	public void setPort(short port) {
		this.port = port;
	}

	public void UnsetEmotiveEpocExpressionComponent() {
		this.eec = null;
	}
	
	public EmotiveEpocExpressionComponent getEec() {
		return eec;
	}

	public void setEec(EmotiveEpocExpressionComponent eec) {
		this.eec = eec;

		this.emgPortBlink = (ProducingPort) eec.getPort(eec.EMG_PORT_BLINK);
		this.emgPortWinkL = (ProducingPort) eec.getPort(eec.EMG_PORT_WINKL);
		this.emgPortWinkR = (ProducingPort) eec.getPort(eec.EMG_PORT_WINKR);
		this.emgPortLookL = (ProducingPort) eec.getPort(eec.EMG_PORT_LOOKL);
		this.emgPortLookR = (ProducingPort) eec.getPort(eec.EMG_PORT_LOOKR);

		this.emgPortRaiseBrow = (FloatLimitProducingPort) eec
				.getPort(eec.EMG_PORT_RAISEBROW);
		this.emgPortFurrowBrow = (FloatLimitProducingPort) eec
				.getPort(eec.EMG_PORT_FURROWBROW);
		this.emgPortClench = (FloatLimitProducingPort) eec
				.getPort(eec.EMG_PORT_CLENCH);
		this.emgPortSmile = (FloatLimitProducingPort) eec
				.getPort(eec.EMG_PORT_SMILE);
		this.emgPortLeftSmirk = (FloatLimitProducingPort) eec
				.getPort(eec.EMG_PORT_LEFTSMIRK);
		this.emgPortRightSmirk = (FloatLimitProducingPort) eec
				.getPort(eec.EMG_PORT_RIGHTSMIRK);
		this.emgPortLaught = (FloatLimitProducingPort) eec
				.getPort(eec.EMG_PORT_LAUGH);
	}
	
	public void UnsetEmotiveEpocAffectivComponent() {
		this.eac = null;
	}
	
	public EmotiveEpocAffectivComponent getEac() {
		return eac;
	}

	public void setEac(EmotiveEpocAffectivComponent eac) {
		this.eac = eac;
		
		this.eegEngegement = (FloatLimitProducingPort) eac.getPort(eac.EEG_PORT_ENGAGEMENT);
		this.eegFrustration = (FloatLimitProducingPort) eac.getPort(eac.EEG_PORT_FRUSTRATION);
		this.eegMeditation = (FloatLimitProducingPort) eac.getPort(eac.EEG_PORT_MEDITATION);
		this.eegInstExcitement = (FloatLimitProducingPort) eac.getPort(eac.EEG_PORT_INST_EXCITEMENT);
		this.eegLTExcitement = (FloatLimitProducingPort) eac.getPort(eac.EEG_PORT_LT_EXCITEMENT);
	}

	public void UnsetEmotiveEpocCognitivComponent(){
		this.ecc = null;
	}
	
	public EmotiveEpocCognitivComponent getEcc() {
		return ecc;
	}

	public void setEcc(EmotiveEpocCognitivComponent ecc) {
		this.ecc = ecc;
		
		this.eegcNeutral = (FloatLimitProducingPort) ecc.getPort(ecc.EEGC_PORT_NEUTRAL);
		this.eegcPush = (FloatLimitProducingPort) ecc.getPort(ecc.EEGC_PORT_PUSH);
		this.eegcPull = (FloatLimitProducingPort) ecc.getPort(ecc.EEGC_PORT_PULL);
		this.eegcLift = (FloatLimitProducingPort) ecc.getPort(ecc.EEGC_PORT_LIFT);
		this.eegcDrop = (FloatLimitProducingPort) ecc.getPort(ecc.EEGC_PORT_DROP);
		this.eegcLeft = (FloatLimitProducingPort) ecc.getPort(ecc.EEGC_PORT_LEFT);
		this.eegcRight = (FloatLimitProducingPort) ecc.getPort(ecc.EEGC_PORT_RIGHT);
		this.eegcRotLeft = (FloatLimitProducingPort) ecc.getPort(ecc.EEGC_PORT_ROTLEFT);
		this.eegcRotRight = (FloatLimitProducingPort) ecc.getPort(ecc.EEGC_PORT_ROTRIGHT);
		this.eegcRotForward = (FloatLimitProducingPort) ecc.getPort(ecc.EEGC_PORT_ROTFORWARD);
		this.eegcRotBackward = (FloatLimitProducingPort) ecc.getPort(ecc.EEGC_PORT_ROTBACKWARD);
		this.eegcRotClockwise = (FloatLimitProducingPort) ecc.getPort(ecc.EEGC_PORT_ROTCLOCKWISE);
		this.eegcRotCounterClockwise = (FloatLimitProducingPort) ecc.getPort(ecc.EEGC_PORT_ROTCOUTERCLOCK);
		this.eegcDisappear = (FloatLimitProducingPort) ecc.getPort(ecc.EEGC_PORT_DISAPPEAR);
		
	}
	
	public int getGyroX()
	{
		return x.getValue();
	}
	
	public int getGyroY()
	{
		return y.getValue();
	}
	
	public void recalibateGyro()
	{
		x.setValue(0);
		y.setValue(0);
	}

	public void UnsetEmotiveEpocGyroComponent() {
		this.gyro = null;
	}
	
	public EmotiveEpocGyroComponent getGyro() {
		return gyro;
	}

	public void setGyro(EmotiveEpocGyroComponent gyro) {
		this.gyro = gyro;
		
		this.gyroTop = (FloatLimitProducingPort) gyro.getPort(gyro.GYRO_PORT_TOP);
		this.gyroDown = (FloatLimitProducingPort) gyro.getPort(gyro.GYRO_PORT_DOWN);
		this.gyroLeft = (FloatLimitProducingPort) gyro.getPort(gyro.GYRO_PORT_LEFT);
		this.gyroRight = (FloatLimitProducingPort) gyro.getPort(gyro.GYRO_PORT_RIGHT);
	}
	
	public boolean isUseless(){
		return this.gyro == null && this.eac == null && this.ecc == null && this.eec == null;
	}
}
