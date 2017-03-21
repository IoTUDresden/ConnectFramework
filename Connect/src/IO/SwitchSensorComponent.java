package IO;

import Core.Component;


public class SwitchSensorComponent extends Component{

	public SwitchSensorComponent() {
		super();
		// TODO Auto-generated constructor stub
	}

	public final String STD_INPUT_BTN_INPUT1 = "Input1";
	public final String STD_INPUT_BTN_INPUT2 = "Input2";
	public final String STD_INPUT_BTN_INPUT3 = "Input3";
	public final String STD_INPUT_BTN_INPUT4 = "Input4";
	public final String STD_INPUT_BTN_INPUT5 = "Input5";
	public final String STD_INPUT_BTN_INPUT6 = "Input6";
	
	public SwitchSensorComponent(String name) {
		super(name);
	}
	
	public void init()
	{
		this.addPort(new SwitchInputPort(this.STD_INPUT_BTN_INPUT1));
		this.addPort(new SwitchInputPort(this.STD_INPUT_BTN_INPUT2));
		this.addPort(new SwitchInputPort(this.STD_INPUT_BTN_INPUT3));
		this.addPort(new SwitchInputPort(this.STD_INPUT_BTN_INPUT4));
		this.addPort(new SwitchInputPort(this.STD_INPUT_BTN_INPUT5));
		this.addPort(new SwitchInputPort(this.STD_INPUT_BTN_INPUT6));
	}
	
	public void exec()
	{
		
	}

}
