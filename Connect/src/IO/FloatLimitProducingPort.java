package IO;

import org.eclipse.swt.widgets.Display;

import Core.Port;
import Core.ProducingPort;

public class FloatLimitProducingPort extends ProducingPort {

	private float limit;
	private float value;
	
	public FloatLimitProducingPort(String id) {
		super(id);
		// TODO Auto-generated constructor stub
		limit = 0;
	}

	public float getLimit() {
		return limit;
	}

	public void setLimit(float limit) {
		this.limit = limit;
		
		this.setValue(value);
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
		if(limit > value) setState(false); else setState(true);
		if(myGUI != null) Display.getDefault().syncExec(r);
	}
	
	

}
