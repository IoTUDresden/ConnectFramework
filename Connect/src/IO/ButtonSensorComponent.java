package IO;

import Core.Component;



public class ButtonSensorComponent extends Component{

	public final String STD_INPUT_BTN_INPUT1 = "Input1";
	public final String STD_INPUT_BTN_INPUT2 = "Input2";
	public final String STD_INPUT_BTN_INPUT3 = "Input3";
	public final String STD_INPUT_BTN_INPUT4 = "Input4";
	public final String STD_INPUT_BTN_INPUT5 = "Input5";
	public final String STD_INPUT_BTN_INPUT6 = "Input6";
	
	public ButtonSensorComponent(String name) {
		super(name);
	}
	
	public ButtonSensorComponent() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init()
	{
		this.addPort(new ButtonInputPort(this.STD_INPUT_BTN_INPUT1));
		this.addPort(new ButtonInputPort(this.STD_INPUT_BTN_INPUT2));
		this.addPort(new ButtonInputPort(this.STD_INPUT_BTN_INPUT3));
		this.addPort(new ButtonInputPort(this.STD_INPUT_BTN_INPUT4));
		this.addPort(new ButtonInputPort(this.STD_INPUT_BTN_INPUT5));
		this.addPort(new ButtonInputPort(this.STD_INPUT_BTN_INPUT6));
	}
	
	public void exec()
	{
		
	}

}
