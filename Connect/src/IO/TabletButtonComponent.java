package IO;

import Core.Component;
import Core.ProducingPort;

public class TabletButtonComponent extends Component {

	public final String BUTTON_PORT_TOP = "Up";
	public final String BUTTON_PORT_DOWN = "Down";
	public final String BUTTON_PORT_LEFT = "Left";
	public final String BUTTON_PORT_RIGHT = "Right";
	
	@Override
	public void init() {
		super.init();
		this.addPort(new ProducingPort(BUTTON_PORT_TOP));
		this.addPort(new ProducingPort(BUTTON_PORT_DOWN));
		this.addPort(new ProducingPort(BUTTON_PORT_LEFT));
		this.addPort(new ProducingPort(BUTTON_PORT_RIGHT));
		TabletClient.getInstance().setButton(this);
	}
	
	@Override
	public void finalies() {
		TabletClient.getInstance().unsetButton();
		super.finalies();
	}
	
}
