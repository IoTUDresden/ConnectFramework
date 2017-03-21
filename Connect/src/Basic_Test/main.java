package Basic_Test;

import Core.DynamicGeneratedWebServiceComponent;
import Core.Manager;
import Core.XMLPersistenceHandler;

public class main {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		Manager.getInstance().makeComponent("Basic_Test.TestActorComponent", "OutputDevice");
		Manager.getInstance().makeComponent("Basic_Test.TestSensorComponent", "InputDevice");
		
		Manager.getInstance().getComponent("OutputDevice").exec();
		
		Manager.getInstance().connectPorts("InputDevice", "Port1", "OutputDevice", "PortX");
		Manager.getInstance().connectPorts("InputDevice", "Port2", "OutputDevice", "PortY");
		Manager.getInstance().connectPorts("InputDevice", "Port2", "OutputDevice", "PortZ");
		
		Manager.getInstance().getComponent("InputDevice").exec();
		
		//Manager.getInstance().addComponent(new DynamicGeneratedWebServiceComponent("WSDL", "RobotManager.wsdl"));
		
		Manager.getInstance().saveConfiguration("test.xml");
		
		Manager manager2 = new Manager();
		
		manager2.loadConfiguration("test.xml");
		
		manager2.getComponent("OutputDevice").setAttribute("Test1", "Boooom");
		
		manager2.saveConfiguration("test2.xml");

	}

}
