package GUI;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

import Core.Component;
import Core.Port;
import IO.KeyboardArrowComponent;

public class GUI_KeyboardArrowComponent extends GUI_Component {

	private KeyListener tast;

	public GUI_KeyboardArrowComponent(Composite guiParent, Component logDevice) {
		super(guiParent, logDevice);
		// TODO Auto-generated constructor stub
		final GUI_KeyboardArrowComponent me = this;

		System.out.println("KAC-created");
		
		tast = new KeyListener() {

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				switch (arg0.keyCode) {
				case 16777217:
					System.out.println("OBER!");
					me.myDevice
							.getPort(
									KeyboardArrowComponent.STD_INPUT_BTN_INPUT1)
							.setState(false);
					break;
				case 16777218:
					me.myDevice
							.getPort(
									KeyboardArrowComponent.STD_INPUT_BTN_INPUT2)
							.setState(false);
					break;
				case 16777219:
					me.myDevice
							.getPort(
									KeyboardArrowComponent.STD_INPUT_BTN_INPUT3)
							.setState(false);
					break;
				case 16777220:
					me.myDevice
							.getPort(
									KeyboardArrowComponent.STD_INPUT_BTN_INPUT4)
							.setState(false);
					break;
				}
			}

			@Override
			public void keyPressed(KeyEvent arg0) {
				switch (arg0.keyCode) {
				case 16777217:
					me.myDevice
							.getPort(
									KeyboardArrowComponent.STD_INPUT_BTN_INPUT1)
							.setState(true);
					break;
				case 16777218:
					me.myDevice
							.getPort(
									KeyboardArrowComponent.STD_INPUT_BTN_INPUT2)
							.setState(true);
					break;
				case 16777219:
					me.myDevice
							.getPort(
									KeyboardArrowComponent.STD_INPUT_BTN_INPUT3)
							.setState(true);
					break;
				case 16777220:
					me.myDevice
							.getPort(
									KeyboardArrowComponent.STD_INPUT_BTN_INPUT4)
							.setState(true);
					break;
				}
			}

		};

		this.myDevice.getPorts().get(0).getMyGUI().btnPort.addKeyListener(tast);
	}

}
