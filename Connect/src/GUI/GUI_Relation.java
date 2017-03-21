package GUI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import Core.ConsumingPort;
import Core.ProducingPort;
import Core.Manager;
import Core.Port;

public class GUI_Relation {
	
	private Label label1;
	private Label label2;
	private Label label3;
	private Button btnX;
	
	private ProducingPort source;
	private ConsumingPort sink;
	
	private int offsetSourcePos;
	private int offsetSinkPos;
	private int middle;
	private int diff;
	
	public GUI_Relation(Shell shell, final ProducingPort nsource, final ConsumingPort nsink) {
		
		this.source = nsource;
		this.sink = nsink;
		
		final GUI_Relation me = this;
		
		btnX = new Button(shell, SWT.NONE);
		btnX.setText("X");
		btnX.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				me.killMe();
				
				((ProducingPort) source).unregisterConsumingPort(sink);
				
				source.getMyGUI().getMyGUIComponent().removeRelation(me);
				sink.getMyGUI().getMyGUIComponent().removeRelation(me);
				sink.setState(false);
			}
		});

		label1 = new Label(shell, SWT.HORIZONTAL);
				
		label2 = new Label(shell, SWT.VERTICAL);
		label2.setCursor(new Cursor(Display.getCurrent(),
				SWT.CURSOR_SIZEWE));
		label2.addMouseMoveListener(new MouseMoveListener() {
			public void mouseMove(MouseEvent e) {
				if(label2.getBackground().equals(SWTResourceManager.getColor(SWT.COLOR_BLUE)))
				{
					label2.setBounds(e.x - 1, label2.getBounds().y, label2.getBounds().width, label2.getBounds().height);	
					middle = label2.getBounds().x - label1.getBounds().x;
					
					updateGUILocation();
				}
				
			}
			
		});
		label2.addMouseListener(new MouseListener() {
			public void mouseDown(MouseEvent e) {
				label2.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
				//System.out.println("X:" + e.x + " Y:" + e.y );
			}
			public void mouseUp(MouseEvent e) {
				updateGUIValue();
			}
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		label3 = new Label(shell, SWT.HORIZONTAL);
		
		label1.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		label2.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		label3.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		
		offsetSourcePos = source.getMyGUI().getMyGUIComponent().getLblName().getBounds().height;
		offsetSourcePos += (source.getMyGUI().getBounds().height / 2);	
		offsetSourcePos += source.getMyGUI().getMyGUIComponent().getMyDevice().getPorts().indexOf(source) * source.getMyGUI().getBounds().height;
		
		offsetSinkPos = sink.getMyGUI().getMyGUIComponent().getLblName().getBounds().height;
		offsetSinkPos += (sink.getMyGUI().getBounds().height / 2);	
		offsetSinkPos += sink.getMyGUI().getMyGUIComponent().getMyDevice().getPorts().indexOf(sink) * sink.getMyGUI().getBounds().height;
		
		middle = -1;
		
		updateGUILocation();
		
		System.out.println("Relation von " + source + " zu " + sink);
		
	}
	
	public ProducingPort getSource() {
		return source;
	}

	public ConsumingPort getSink() {
		return sink;
	}

	public void updateGUILocation()
	{
		int startPointx = source.getMyGUI().getMyGUIComponent().getBounds().x + source.getMyGUI().getMyGUIComponent().getBounds().width;
		int startPointy = source.getMyGUI().getMyGUIComponent().getBounds().y + offsetSourcePos;
		
		int goalPointx = sink.getMyGUI().getMyGUIComponent().getBounds().x;
		int goalPointy = sink.getMyGUI().getMyGUIComponent().getBounds().y + offsetSinkPos;
		
		diff = goalPointx - startPointx;
		
		if(middle == -1)
		{
			middle = diff/2;
		}
		
		if(startPointy < goalPointy)
		{
			label1.setBounds(startPointx, startPointy, middle, 2);
			label2.setBounds(startPointx + middle +1, startPointy, 2, goalPointy - startPointy);
			label3.setBounds(goalPointx - (diff - middle), goalPointy, diff - middle, 2);
		}
		else
		{
			label1.setBounds(startPointx, startPointy, middle, 2);
			label2.setBounds(startPointx + middle +1, goalPointy, 2, startPointy - goalPointy);
			label3.setBounds(goalPointx - (diff - middle), goalPointy, diff - middle, 2);
		}
		
		btnX.setBounds(label3.getBounds().x + label3.getBounds().width - 30, label3.getBounds().y -10, 20, 20);
	}
	
	public void updateGUIValue()
	{
		if(source.getState())
		{
			label1.setBackground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
			label2.setBackground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
			label3.setBackground(SWTResourceManager.getColor(SWT.COLOR_GREEN));
		}else{
			label1.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
			label2.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
			label3.setBackground(SWTResourceManager.getColor(SWT.COLOR_GRAY));
		}
	}
	
	public void killMe()
	{
		label1.dispose();
		label2.dispose();
		label3.dispose();
		btnX.dispose();
	}

}
