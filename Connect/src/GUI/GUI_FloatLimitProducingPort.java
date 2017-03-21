package GUI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;


import IO.FloatLimitProducingPort;
import Core.Port;
import Core.ProducingPort;
import GUI.GUI_Component;;

public class GUI_FloatLimitProducingPort extends GUI_ProducingPort {
	
	private ProgressBar progressBar;
	private Label label_2;

	public GUI_FloatLimitProducingPort(Composite parent, final ProducingPort port) {
		super(parent, port);
		
		final GUI_FloatLimitProducingPort me = this;

		label_2 = new Label(this, SWT.NONE);
		label_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_BLUE));
		label_2.setLocation(97, 0);
		label_2.setSize(4, 24);
		label_2.setCursor(new Cursor(Display.getCurrent(), SWT.CURSOR_SIZEWE));
		label_2.addMouseMoveListener(new MouseMoveListener() {
			public void mouseMove(MouseEvent e) {
				if (label_2.getBackground().equals(
						SWTResourceManager.getColor(SWT.COLOR_GREEN))) {
					int x = 97 + e.x;

					if (x < 97)
						x = 97;
					if (x > 155)
						x = 155;

					label_2.setLocation(x, 0);
				}
			}
		});
		label_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				label_2.setBackground(SWTResourceManager
						.getColor(SWT.COLOR_GREEN));
				// System.out.println("X:" + e.x + " Y:" + e.y );
			}

			@Override
			public void mouseUp(MouseEvent e) {
				label_2.setBackground(SWTResourceManager
						.getColor(SWT.COLOR_RED));
				((FloatLimitProducingPort)port).setLimit((label_2.getLocation().x - (float)97)/58);
				((GUI_Component)me.getParent().getParent()).updateRelationsValue();
			}
		});

		progressBar = new ProgressBar(this, SWT.NONE);
		progressBar.setForeground(SWTResourceManager
				.getColor(SWT.COLOR_LIST_FOREGROUND));
		progressBar.setBounds(97, 5, 60, 10);
		
		label_2.setLocation((int) ((((FloatLimitProducingPort)port).getLimit()*(float)58)+97) , label_2.getLocation().y);
	}
	
	public void updateGUI(Port port)
	{
		super.updateGUI(port);
		progressBar.setSelection((int)(((FloatLimitProducingPort)port).getValue()*100));
		if(port.getState())
		{
			progressBar.setState(SWT.NORMAL);
		}else{
			progressBar.setState(SWT.ERROR);
		}
	}
	

}
