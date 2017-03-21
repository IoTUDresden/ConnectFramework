package GUI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.dnd.DND;

import Core.ConsumingPort;
import Core.Manager;
import Core.Port;
import Core.ProducingPort;

public class GUI_ConsumingPort extends GUI_Port{

	public GUI_ConsumingPort(Composite parent, final ConsumingPort port) {
		
		super(parent, port);

		lblPort.setLayoutData(SWT.RIGHT);
		lblPort.setBounds(101, 0, 86, 15);
		btnPort.setBounds(0, 0, 27, 20);
		
		 DropTarget dt = new DropTarget(btnPort, DND.DROP_MOVE);
		    dt.setTransfer(new Transfer[] { TextTransfer.getInstance() });
		    dt.addDropListener(new DropTargetAdapter() {
		      public void drop(DropTargetEvent event) {
		        // Set the buttons text to be the text being dropped
		        String[] newStringList = ((String) event.data).split(";");
		        ProducingPort inPort = null;
				inPort = (ProducingPort) (Manager.getInstance().getComponent(newStringList[1]).getPort(newStringList[0]));
		        inPort.registerConsumingPort(port);
		        port.setState(inPort.getState());
		        
		        GUI_Relation rel = new GUI_Relation(btnPort.getShell(), inPort, port);
		        inPort.getMyGUI().getMyGUIComponent().addRelation(rel);
		        port.getMyGUI().getMyGUIComponent().addRelation(rel);
		      }
		    });
	}

}
