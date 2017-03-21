package Basic_Test;

import Core.*;

public class TestSensorComponent extends SensorComponent {

	public TestSensorComponent() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TestSensorComponent(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
	
	public void init(){
		this.addPort(new ProducingPort("Port1"));
		this.addPort(new ProducingPort("Port2"));
		
		this.setAttribute("Test-Attribute", "159.147.2.15");
		this.setAttribute("Test-Attribute2", "Test");
	}
	
	public void exec() {
		this.getPort("Port1").setState(true);
		this.getPort("Port2").setState(true);
	}

}
