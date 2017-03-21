package Basic_Test;

import Core.ProducingPort;
import Core.SensorComponent;

public class TestBlinkComponent extends SensorComponent{
	
	public static String ATTRIBUT_TIME = "blink time";
	
	Thread thr;
	boolean isRunning = true;
	
	public TestBlinkComponent()
	{
		super();
	}

	@Override
	public void exec() {
		// TODO Auto-generated method stub
		super.exec();
	}

	@Override
	public void init() {
		addPort(new ProducingPort("BlinkInput"));
		this.setAttribute(ATTRIBUT_TIME, "500");
		
		final TestBlinkComponent me = this;
		thr = new Thread(new Runnable() {
			public void run() {
				while(isRunning)
				{
				try {
					thr.sleep(Long.parseLong(me.getAttribute(ATTRIBUT_TIME)));
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					getPort("BlinkInput").setState(!getPort("BlinkInput").getState());
				}
				
			}
		});;
		thr.start();

		super.init();
	}

	@Override
	public void finalies() {
		// TODO Auto-generated method stub
		super.finalies();
		thr.stop();
	}
	
	

}
