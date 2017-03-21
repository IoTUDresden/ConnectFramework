package GUI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

import Core.Port;
import Core.ProducingPort;

public class GUI_SwitchInputPort extends GUI_ProducingPort {
	
	private Button btnTrue;

	public GUI_SwitchInputPort(Composite parent, final ProducingPort port) {
		super(parent, port);
				
		btnTrue = new Button(this, SWT.TOGGLE);
		btnTrue.setBounds(76, 0, 75, 20);
		btnTrue.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				port.setState(!btnTrue.getSelection());
				if(!btnTrue.getSelection()) btnTrue.setText("TRUE"); else btnTrue.setText("FALSE");
			}
			@Override
			public void mouseUp(MouseEvent e) {
			}
		});
		btnTrue.setText("FALSE");
	}
	

}
