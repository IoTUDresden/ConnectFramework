package GUI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import Core.Port;

public class GUI_Port extends Composite{
	
	protected Label lblPort;
	protected Button btnPort;

	public GUI_Port(Composite parent, Port port) {
		super(parent, SWT.BORDER);
		
		lblPort = new Label(this, SWT.NONE);
		lblPort.setBounds(0, 0, 96, 15);
		lblPort.setText(port.getId());
		
		btnPort = new Button(this, SWT.NONE);
		btnPort.setBounds(157, 0, 27, 20);
		btnPort.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD));
		
		port.setMyGUI(this);
	}

	public void updateGUI(Port port) {
		if(port.getState()) btnPort.setText("X"); else btnPort.setText("");
		getMyGUIComponent().updateRelationsValue();
	}
	
	public String getPortId()
	{
		return lblPort.getText() + ";" + ((GUI_Component)(this.getParent().getParent())).getLblName().getText();
	}
	
	public GUI_Component getMyGUIComponent()
	{
		return (GUI_Component)(this.getParent().getParent());
	}
}