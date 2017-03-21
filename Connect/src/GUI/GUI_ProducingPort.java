package GUI;

import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Composite;

import Core.Port;
import Core.ProducingPort;

public class GUI_ProducingPort extends GUI_Port{

	public GUI_ProducingPort(final Composite parent, ProducingPort port) {
		super(parent, port);
		
		DragSource ds = new DragSource(btnPort, DND.DROP_MOVE);
	    ds.setTransfer(new Transfer[] { TextTransfer.getInstance() });
	    ds.addDragListener(new DragSourceAdapter() {
	      public void dragSetData(DragSourceEvent event) {
	        // Set the data to be the first selected item's text
	        event.data = getPortId();
	      }
	    });
	}

}
