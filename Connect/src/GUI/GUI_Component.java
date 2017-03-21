package GUI;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;

import Core.Component;
import Core.ConsumingPort;
import Core.Manager;
import Core.Port;
import Core.ProducingPort;
import IO.RosTurtleBotComponent;

public class GUI_Component extends Composite{
	
	ArrayList<Composite> guiPorts;
	Component myDevice;
	Label lblName;
	ArrayList<GUI_Relation> relations;
	Composite GUI_Extraspace;
	Button btnQ;
	Button btnX;
	Button btnM;

	public GUI_Component(Composite guiParent, final Component logDevice) {
		super(guiParent, SWT.BORDER);
		
		logDevice.setMyGUI(this);
		myDevice = logDevice;
		
		relations = new ArrayList<GUI_Relation>();
		
		final GUI_Component me = this;
		
		this.setLayout(new FormLayout());
		
		int height = 24 * logDevice.getPorts().size();
		//int height = 0;
		this.setBounds(0, 0, 194, height+22);
		this.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		
		lblName = new Label(this, SWT.NONE);
		lblName.addMouseMoveListener(new MouseMoveListener() {
			public void mouseMove(MouseEvent e) {
				if(lblName.getBackground().equals(SWTResourceManager.getColor(SWT.COLOR_BLUE)))
				{
					me.setLocation(me.getLocation().x+e.x-30, me.getLocation().y+e.y-10);
					updateRelationsPosition();
				}
			}
		});
		lblName.addMouseListener(new MouseListener() {
			public void mouseDown(MouseEvent e) {
				lblName.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
				me.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLUE));
				//System.out.println("X:" + e.x + " Y:" + e.y );
			}
			public void mouseUp(MouseEvent e) {
				lblName.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
				me.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
				
			}
			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		lblName.setBounds(20, 0, 135, 21);
		lblName.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.BOLD));
		lblName.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblName.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		lblName.setText(logDevice.getId());
		lblName.setCursor(new Cursor(Display.getCurrent(),
				SWT.CURSOR_HAND));
		
		btnM = new Button(this, SWT.NONE);
		btnM.setBounds(1, 1, 17, 17);
		btnM.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		btnM.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				GUI_AttributeWindow newWindow = new GUI_AttributeWindow(me.getDisplay(),myDevice);
				newWindow.open();
			}
		});		
		btnM.setText("v");
		
		btnQ = new Button(this, SWT.NONE);
		btnQ.setBounds(156, 1, 17, 17);
		btnQ.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		btnQ.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				MessageBox box = new MessageBox(getShell(), SWT.OK | SWT.ICON_INFORMATION);
                box.setText("help to " + myDevice.getId());
                box.setMessage(myDevice.getHelp());
                box.open();
			}
		});		
		btnQ.setText("?");
		
		btnX = new Button(this, SWT.NONE);
		btnX.setBounds(173, 1, 17, 17);
		btnX.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		btnX.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				logDevice.finalies();
				Manager.getInstance().removeComponent(logDevice);
				Iterator<GUI_Relation> rite = relations.iterator();
				while(rite.hasNext())
				{
					rite.next().killMe();
				}
				me.dispose();
			}
		});
		btnX.setText("X");
		
		Composite GUI_Portspace = new Composite(this, SWT.NONE);
		GUI_Portspace.setLayout(new RowLayout(SWT.HORIZONTAL));
		GUI_Portspace.setBounds(0, 20, 189, height);
		
		guiPorts = new ArrayList<Composite>();
		
		List<Port> list = logDevice.getPorts();
		Iterator<Port> ite = list.iterator();
		int x = 0;
		while(ite.hasNext())
		{
			Port port = ite.next();
			GUI_Port newGUIPort;
			
			switch(port.getClass().getName())
			{
			case "Core.ProducingPort": newGUIPort = new GUI_ProducingPort(GUI_Portspace, (ProducingPort) port); break;
			case "IO.FloatLimitProducingPort": newGUIPort = new GUI_FloatLimitProducingPort(GUI_Portspace, (ProducingPort) port); break;
			case "Core.ConsumingPort": newGUIPort = new GUI_ConsumingPort(GUI_Portspace, (ConsumingPort) port); break;
			case "IO.ButtonInputPort": newGUIPort = new GUI_ButtonInputPort(GUI_Portspace, (ProducingPort) port); break;
			case "IO.SwitchInputPort": newGUIPort = new GUI_SwitchInputPort(GUI_Portspace, (ProducingPort) port); break;
			default: newGUIPort = new GUI_Port(GUI_Portspace, port); break;
			}
			newGUIPort.setBounds(0, 0+(x*24), 189, 24);
			
			x++;
		}
	}
	
	public void exec()
	{

	}

	public Component getMyDevice() {
		return myDevice;
	}

	public void setMyDevice(Component myDevice) {
		this.myDevice = myDevice;
	}

	public Label getLblName() {
		return lblName;
	}

	public void setLblName(Label lblName) {
		this.lblName = lblName;
	}
	
	public void addRelation(GUI_Relation rel)
	{
		relations.add(rel);
		updateRelationsValue();
	}

	public void removeRelation(GUI_Relation rel)
	{
		relations.remove(rel);
	}
	
	public void removeRelation(Port source, Port sink)
	{
		Iterator<GUI_Relation> rite = relations.iterator();
		int n = -1;
		
		while(rite.hasNext())
		{
			GUI_Relation r = rite.next();
			if(r.getSource().equals(source) && r.getSink().equals(sink)) n = relations.indexOf(r);
		}
		
		if(n>-1) relations.remove(n);
	}
	
	public void updateRelationsPosition()
	{
		Iterator<GUI_Relation> ite = relations.iterator();
		
		while(ite.hasNext())
		{
			ite.next().updateGUILocation();
		}
	}
	
	public void updateRelationsValue()
	{
		Iterator<GUI_Relation> ite = relations.iterator();
		
		while(ite.hasNext())
		{
			ite.next().updateGUIValue();
		}
	}
	
}
