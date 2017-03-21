package GUI;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import java.awt.Color;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;

import javax.wsdl.WSDLException;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import Basic_Test.TestBlinkComponent;
import Basic_Test.TestSensorComponent;
import Core.Component;
import Core.ConsumingPort;
import Core.DynamicGeneratedWebServiceComponent;
import Core.Manager;
import Core.Port;
import Core.ProducingPort;
import Core.ComplexComponents.TimeExtendComponent;
import IO.EmotivClient;
import IO.KeyboardArrowComponent;
import IO.RosTurtleBotComponent;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetAdapter;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;

public class RootWindow_FW {

	protected Shell shell;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			RootWindow_FW window = new RootWindow_FW();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		addKeyListenerForTurtlebot();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(829, 559);
		shell.setText("Connect Framework");
		shell.setLayout(null);
		shell.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent e) {
				shell.update();
			}
		});

		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);

		MenuItem mntmNutzerprofil = new MenuItem(menu, SWT.CASCADE);
		mntmNutzerprofil.setText("User Profile");

		Menu menu_5 = new Menu(mntmNutzerprofil);
		mntmNutzerprofil.setMenu(menu_5);

		MenuItem mntmNeu = new MenuItem(menu_5, SWT.NONE);
		mntmNeu.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				// Manager.getInstance().reset();
			}
		});
		mntmNeu.setText("New");

		MenuItem mntmSpeichern = new MenuItem(menu_5, SWT.NONE);
		mntmSpeichern.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog dialog = new FileDialog(shell, SWT.SAVE);
				dialog.setFilterNames(new String[] { "XML-File" });
				dialog.setFilterExtensions(new String[] { "*.xml" }); // Windows
				// wild
				// cards
				dialog.setFilterPath("c:\\"); // Windows path
				try {
					Manager.getInstance().saveConfiguration(dialog.open());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mntmSpeichern.setText("Save");

		MenuItem mntmLaden = new MenuItem(menu_5, SWT.NONE);
		mntmLaden.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog dialog = new FileDialog(shell, SWT.OPEN);
				dialog.setFilterNames(new String[] { "XML-File" });
				dialog.setFilterExtensions(new String[] { "*.xml" }); // Windows
				// wild
				// cards
				dialog.setFilterPath("c:\\"); // Windows path
				try {
					Manager.getInstance().loadConfiguration(dialog.open());
					buildGUIfromManager();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		mntmLaden.setText("Load");

		MenuItem mntmHinzufgen_1 = new MenuItem(menu, SWT.CASCADE);
		mntmHinzufgen_1.setText("Add Components");

		Menu menu_2 = new Menu(mntmHinzufgen_1);
		mntmHinzufgen_1.setMenu(menu_2);

		MenuItem mntmInputdevices = new MenuItem(menu_2, SWT.CASCADE);
		mntmInputdevices.setText("SensorComponent");

		Menu menu_3 = new Menu(mntmInputdevices);
		mntmInputdevices.setMenu(menu_3);

		MenuItem mntmEpocEmotiv = new MenuItem(menu_3, SWT.CASCADE);
		mntmEpocEmotiv.setText("EPOC Emotiv");

		Menu menu_10 = new Menu(mntmEpocEmotiv);
		mntmEpocEmotiv.setMenu(menu_10);

		MenuItem mntmEmotivcognitivsiute = new MenuItem(menu_10, SWT.NONE);
		mntmEmotivcognitivsiute.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					getGUIFromComponent(Manager.getInstance().makeComponent(
							"IO.EmotiveEpocCognitivComponent",
							"Emotive Cognitiv"));
				} catch (InstantiationException | IllegalAccessException
						| ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mntmEmotivcognitivsiute.setText("EmotivCognitivSuite");

		MenuItem mntmEmotivexpressivesiute = new MenuItem(menu_10, SWT.NONE);
		mntmEmotivexpressivesiute.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					getGUIFromComponent(Manager.getInstance().makeComponent(
							"IO.EmotiveEpocExpressionComponent",
							"Emotive Expressions"));
				} catch (InstantiationException | IllegalAccessException
						| ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mntmEmotivexpressivesiute.setText("EmotivExpressiveSuite");

		MenuItem mntmEmotivaffectivsuite = new MenuItem(menu_10, SWT.NONE);
		mntmEmotivaffectivsuite.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					getGUIFromComponent(Manager.getInstance().makeComponent(
							"IO.EmotiveEpocAffectivComponent",
							"Emotive Affectiv"));
				} catch (InstantiationException | IllegalAccessException
						| ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mntmEmotivaffectivsuite.setText("EmotivAffectivSuite");

		MenuItem mntmEmotivgyroscop = new MenuItem(menu_10, SWT.NONE);
		mntmEmotivgyroscop.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					getGUIFromComponent(Manager.getInstance().makeComponent(
							"IO.EmotiveEpocGyroComponent", "Emotive Gyroscop"));
				} catch (InstantiationException | IllegalAccessException
						| ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mntmEmotivgyroscop.setText("EmotivGyroscop");

		MenuItem mntmKeyboardarrowcomponent = new MenuItem(menu_3, SWT.NONE);
		mntmKeyboardarrowcomponent.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					getGUIFromComponent(Manager.getInstance().makeComponent(
							"IO.KeyboardArrowComponent", "Keyboard Arrows"));
				} catch (InstantiationException | IllegalAccessException
						| ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mntmKeyboardarrowcomponent.setText("KeyboardArrowComponent");

		MenuItem mntmTestsensorcomponent = new MenuItem(menu_3, SWT.NONE);
		mntmTestsensorcomponent.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					GUI_Component comp = new GUI_Component(shell, Manager
							.getInstance().makeComponent(
									"Basic_Test.TestSensorComponent",
									"TestSensorComponent"));
				} catch (InstantiationException | IllegalAccessException
						| ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mntmTestsensorcomponent.setText("TestSensorComponent");

		MenuItem mntmButtonsensorcomponent = new MenuItem(menu_3, SWT.NONE);
		mntmButtonsensorcomponent.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					GUI_Component comp = new GUI_Component(shell, Manager
							.getInstance().makeComponent(
									"IO.ButtonSensorComponent", "Buttons"));
				} catch (InstantiationException | IllegalAccessException
						| ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mntmButtonsensorcomponent.setText("ButtonSensorComponent");

		MenuItem mntmSwitchsensorcomponent = new MenuItem(menu_3, SWT.NONE);
		mntmSwitchsensorcomponent.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					GUI_Component comp = new GUI_Component(shell, Manager
							.getInstance().makeComponent(
									"IO.SwitchSensorComponent", "Switches"));
				} catch (InstantiationException | IllegalAccessException
						| ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mntmSwitchsensorcomponent.setText("SwitchSensorComponent");

		MenuItem mntmBlinksensorcomponent = new MenuItem(menu_3, SWT.NONE);
		mntmBlinksensorcomponent.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					GUI_Component comp = new GUI_Component(shell, Manager
							.getInstance().makeComponent(
									"Basic_Test.TestBlinkComponent",
									"TestBlinkComponent"));
				} catch (InstantiationException | IllegalAccessException
						| ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		mntmBlinksensorcomponent.setText("TestBlinkComponent");

		MenuItem mntmTabletButtoncomponent = new MenuItem(menu_3, SWT.NONE);
		mntmTabletButtoncomponent.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					GUI_Component comp = new GUI_Component(shell, Manager
							.getInstance().makeComponent(
									"IO.TabletButtonComponent",
									"TabletButtonComponent"));
				} catch (InstantiationException | IllegalAccessException
						| ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		mntmTabletButtoncomponent.setText("TabletButtonComponent");

		MenuItem mntmTabletGyrocomponent = new MenuItem(menu_3, SWT.NONE);
		mntmTabletGyrocomponent.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					GUI_Component comp = new GUI_Component(shell, Manager
							.getInstance().makeComponent(
									"IO.TabletGyroComponent",
									"TabletGyroscopeComponent"));
				} catch (InstantiationException | IllegalAccessException
						| ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		mntmTabletGyrocomponent.setText("TabletGyroscopeComponent");

		MenuItem mntmActorcomponent = new MenuItem(menu_2, SWT.CASCADE);
		mntmActorcomponent.setText("ActorComponent");

		Menu menu_1 = new Menu(mntmActorcomponent);
		mntmActorcomponent.setMenu(menu_1);

		MenuItem mntmTestactorcomponent = new MenuItem(menu_1, SWT.NONE);
		mntmTestactorcomponent.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					GUI_Component comp = new GUI_Component(shell, Manager
							.getInstance().makeComponent(
									"Basic_Test.TestActorComponent",
									"TestActorComponent"));
				} catch (InstantiationException | IllegalAccessException
						| ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mntmTestactorcomponent.setText("TestActorComponent");

		MenuItem mntmRosturtlebotcomponent = new MenuItem(menu_1, SWT.NONE);
		mntmRosturtlebotcomponent.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					getGUIFromComponent(Manager.getInstance().makeComponent(
							"IO.RosTurtleBotComponent", "TurtleBot"));
				} catch (InstantiationException | IllegalAccessException
						| ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mntmRosturtlebotcomponent.setText("RosTurtleBotComponent");

		MenuItem mntmComponentFromWsdl = new MenuItem(menu_1, SWT.NONE);
		mntmComponentFromWsdl.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Component c = null;
				FileDialog dialog = new FileDialog(shell, SWT.OPEN);
				dialog.setFilterNames(new String[] { "WSDL-File" });
				dialog.setFilterExtensions(new String[] { "*.wsdl" }); // Windows
				// wild
				// cards
				dialog.setFilterPath("c:\\"); // Windows path
				try {
					c = new DynamicGeneratedWebServiceComponent("WSDL-Service",
							dialog.open());
					Manager.getInstance().addComponent(c);
					GUI_Component comp = new GUI_Component(shell, c);
				} catch (WSDLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				if (c != null) {
					Manager.getInstance().addComponent(c);
				} else {
					MessageBox box = new MessageBox(shell, SWT.ERROR);
					box.setText("Error");
					box.setMessage("It is not possible to load this WSDL-File.");
					box.open();
				}
			}
		});
		mntmComponentFromWsdl.setText("Component from WSDL");

		MenuItem mntmComplexcomponent = new MenuItem(menu_2, SWT.CASCADE);
		mntmComplexcomponent.setText("ComplexComponent");

		Menu menu_4 = new Menu(mntmComplexcomponent);
		mntmComplexcomponent.setMenu(menu_4);

		MenuItem mntmLogical = new MenuItem(menu_4, SWT.CASCADE);
		mntmLogical.setText("logical");

		Menu menu_6 = new Menu(mntmLogical);
		mntmLogical.setMenu(menu_6);

		MenuItem mntmNot = new MenuItem(menu_6, SWT.NONE);
		mntmNot.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					GUI_Component comp = new GUI_Component(shell, Manager
							.getInstance().makeComponent(
									"Core.ComplexComponents.NotComponent",
									"NOT"));
				} catch (InstantiationException | IllegalAccessException
						| ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mntmNot.setText("NOT");

		MenuItem mntmAnd = new MenuItem(menu_6, SWT.NONE);
		mntmAnd.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					GUI_Component comp = new GUI_Component(shell, Manager
							.getInstance().makeComponent(
									"Core.ComplexComponents.AndComponent",
									"AND"));
				} catch (InstantiationException | IllegalAccessException
						| ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mntmAnd.setText("AND");

		MenuItem mntmOr = new MenuItem(menu_6, SWT.NONE);
		mntmOr.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					GUI_Component comp = new GUI_Component(shell, Manager
							.getInstance().makeComponent(
									"Core.ComplexComponents.OrComponent", "OR"));
				} catch (InstantiationException | IllegalAccessException
						| ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mntmOr.setText("OR");

		MenuItem mntmNand = new MenuItem(menu_6, SWT.NONE);
		mntmNand.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					GUI_Component comp = new GUI_Component(shell, Manager
							.getInstance().makeComponent(
									"Core.ComplexComponents.NandComponent",
									"NAND"));
				} catch (InstantiationException | IllegalAccessException
						| ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mntmNand.setText("NAND");

		MenuItem mntmNor = new MenuItem(menu_6, SWT.NONE);
		mntmNor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					GUI_Component comp = new GUI_Component(shell, Manager
							.getInstance().makeComponent(
									"Core.ComplexComponents.NorComponent",
									"NOR"));
				} catch (InstantiationException | IllegalAccessException
						| ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mntmNor.setText("NOR");

		MenuItem mntmXor = new MenuItem(menu_6, SWT.NONE);
		mntmXor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					GUI_Component comp = new GUI_Component(shell, Manager
							.getInstance().makeComponent(
									"Core.ComplexComponents.XorComponent",
									"XOR"));
				} catch (InstantiationException | IllegalAccessException
						| ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mntmXor.setText("XOR");

		MenuItem mntmSaveOfState = new MenuItem(menu_4, SWT.CASCADE);
		mntmSaveOfState.setText("save of state");

		Menu menu_7 = new Menu(mntmSaveOfState);
		mntmSaveOfState.setMenu(menu_7);

		MenuItem mntmFlipflop = new MenuItem(menu_7, SWT.NONE);
		mntmFlipflop.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					GUI_Component comp = new GUI_Component(shell, Manager
							.getInstance().makeComponent(
									"Core.ComplexComponents.FlipFlopComponent",
									"FlipFlop"));
				} catch (InstantiationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mntmFlipflop.setText("FlipFlop");

		MenuItem mntmTrigger = new MenuItem(menu_7, SWT.NONE);
		mntmTrigger.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					GUI_Component comp = new GUI_Component(shell, Manager
							.getInstance().makeComponent(
									"Core.ComplexComponents.TriggerComponent",
									"Trigger"));
				} catch (InstantiationException | IllegalAccessException
						| ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mntmTrigger.setText("Trigger");

		MenuItem mntmTimeabel = new MenuItem(menu_4, SWT.CASCADE);
		mntmTimeabel.setText("timeable");

		Menu menu_8 = new Menu(mntmTimeabel);
		mntmTimeabel.setMenu(menu_8);

		MenuItem mntmExtendend = new MenuItem(menu_8, SWT.NONE);
		mntmExtendend.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					GUI_Component comp = new GUI_Component(
							shell,
							Manager.getInstance()
									.makeComponent(
											"Core.ComplexComponents.TimeExtendComponent",
											"Time Extend"));
				} catch (InstantiationException | IllegalAccessException
						| ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mntmExtendend.setText("extendend");

		MenuItem mntmDoubleEvent = new MenuItem(menu_8, SWT.NONE);
		mntmDoubleEvent.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					GUI_Component comp = new GUI_Component(
							shell,
							Manager.getInstance()
									.makeComponent(
											"Core.ComplexComponents.DoubleEventComponent",
											"Double Event"));
				} catch (InstantiationException | IllegalAccessException
						| ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mntmDoubleEvent.setText("Double Event");

		MenuItem mntmHelp = new MenuItem(menu, SWT.CASCADE);
		mntmHelp.setText("Help");

		Menu menu_9 = new Menu(mntmHelp);
		mntmHelp.setMenu(menu_9);

		MenuItem mntmTest = new MenuItem(menu_9, SWT.NONE);
		mntmTest.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				HttpClient client = new DefaultHttpClient();
				HttpGet httppost = new HttpGet(
						"http://localhost:8080/TestWebLight/services/LightManager?method=changeLight1");

				try {
					client.execute(httppost);
				} catch (ClientProtocolException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		mntmTest.setText("Test");

		// buildGUIfromManager();

	}

	public void addKeyListenerForTurtlebot() {
		Display.getDefault().addFilter(SWT.KeyDown, new Listener() {
			
			@Override
			public void handleEvent(Event e) {
				if(e.keyCode==32){
					onSpaceKeyPressed();
					System.out.println("Disconnect");
				}
				if(e.keyCode==99){
					onCKeyPressed();
					System.out.println("Connect");
				}
			}
		});
	}
	
	private void onCKeyPressed() {
		Iterator<Component> compIte = Manager.getInstance().getComponents()
				.iterator();
		while (compIte.hasNext()) {
			Component c = compIte.next();
			if (c instanceof RosTurtleBotComponent) {
				RosTurtleBotComponent rosTurtle = (RosTurtleBotComponent) c;
				rosTurtle.getRobot().setPseudoDisconnect(false);
			}
		}
	}

	private void onSpaceKeyPressed() {
		Iterator<Component> compIte = Manager.getInstance().getComponents()
				.iterator();
		while (compIte.hasNext()) {
			Component c = compIte.next();
			if (c instanceof RosTurtleBotComponent) {
				RosTurtleBotComponent rosTurtle = (RosTurtleBotComponent) c;
				rosTurtle.getRobot().doPublishCommandVelocity(0, 0, 0, 0, 0, 0);
				rosTurtle.getRobot().setPseudoDisconnect(true);
			}
		}
	}

	public void buildGUIfromManager() {
		// Manager-Componenten auf GUI-Componenten abbilden

		Iterator<Component> compIte = Manager.getInstance().getComponents()
				.iterator();
		while (compIte.hasNext()) {
			Component c = compIte.next();
			getGUIFromComponent(c);

			// System.out.println(c.getMyGUI().getClass().getName());
		}

		// Relationen abbilden

		Iterator<Component> compIte1 = Manager.getInstance().getComponents()
				.iterator();
		while (compIte1.hasNext()) {
			// Suche nach ProducingPorts
			Component c = compIte1.next();
			Iterator<Port> portIte = c.getPorts().iterator();

			while (portIte.hasNext()) {
				Port p = portIte.next();

				Boolean isProducingPort = false;

				Class cl = p.getClass();

				while (!cl.getName().equals("java.lang.Object")
						&& !isProducingPort) {
					if (cl.getName().equals("Core.ProducingPort"))
						isProducingPort = true;
					cl = cl.getSuperclass();
				}

				if (isProducingPort) {
					Iterator<ConsumingPort> cpIte = ((ProducingPort) p)
							.getConsumingPorts().iterator();

					while (cpIte.hasNext()) {
						ConsumingPort cp = cpIte.next();
						GUI_Relation rel = new GUI_Relation(shell,
								(ProducingPort) p, cp);
						p.getMyGUI().getMyGUIComponent().addRelation(rel);
						cp.getMyGUI().getMyGUIComponent().addRelation(rel);
					}
				}

			}

			// System.out.println(c.getMyGUI().getClass().getName());
		}

	}

	public void getGUIFromComponent(Component c) {
		GUI_Component newGUI = null;
		switch (c.getClass().getName()) {
		case "IO.KeyboardArrowComponent":
			newGUI = new GUI_KeyboardArrowComponent(shell, c);
			break;
		default:
			newGUI = new GUI_Component(shell, c);
			break;
		}

		if (c.getTempX() > 0 || c.getTempX() > 0) {
			newGUI.setBounds(c.getTempX(), c.getTempY(),
					newGUI.getBounds().width, newGUI.getBounds().height);
		}
	}
}
