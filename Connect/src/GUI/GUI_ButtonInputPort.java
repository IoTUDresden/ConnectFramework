package GUI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import Core.Port;
import Core.ProducingPort;
import IO.ButtonInputPort;

public class GUI_ButtonInputPort extends GUI_ProducingPort {
	
	private Button btnTrue;

	public GUI_ButtonInputPort(Composite parent, final ProducingPort port) {
		super(parent, port);
				
		btnTrue = new Button(this, SWT.NONE);
		btnTrue.setBounds(76, 0, 75, 20);
		btnTrue.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				port.setState(true);
			}
			@Override
			public void mouseUp(MouseEvent e) {
				port.setState(false);
			}
		});
		btnTrue.setText("TRUE");
	}
	

}
