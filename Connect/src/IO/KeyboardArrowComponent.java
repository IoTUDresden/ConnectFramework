package IO;

import Core.Component;
import Core.ProducingPort;

public class KeyboardArrowComponent extends Component{
	
	public static final String STD_INPUT_BTN_INPUT1 = "hoch";
	public static final String STD_INPUT_BTN_INPUT2 = "runter";
	public static final String STD_INPUT_BTN_INPUT3 = "links";
	public static final String STD_INPUT_BTN_INPUT4 = "rechts";

	public KeyboardArrowComponent(String new_name) {
		super(new_name);
		// TODO Auto-generated constructor stub
	}
	
	public KeyboardArrowComponent()
	{
		
	}
	
	public void init()
	{
		this.addPort(new ProducingPort(this.STD_INPUT_BTN_INPUT1));
		this.addPort(new ProducingPort(this.STD_INPUT_BTN_INPUT2));
		this.addPort(new ProducingPort(this.STD_INPUT_BTN_INPUT3));
		this.addPort(new ProducingPort(this.STD_INPUT_BTN_INPUT4));
	}
	

}
