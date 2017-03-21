package GUI;

import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowLayout;

import swing2swt.layout.BorderLayout;

import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Button;
import org.eclipse.wb.swt.SWTResourceManager;

import swing2swt.layout.FlowLayout;

import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import Core.Component;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class GUI_AttributeWindow extends Shell {

	Composite composite_1;
	Component myComponent;
	Shell me;
	
	public GUI_AttributeWindow(Display display, Component c) {
		super(display, SWT.CLOSE | SWT.TITLE);
		
		myComponent = c;
		me = this;
		setLayout(new BorderLayout(0, 0));
		
		ScrolledComposite scrolledComposite = new ScrolledComposite(this, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setLayoutData(BorderLayout.CENTER);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);
		
		composite_1 = new Composite(scrolledComposite, SWT.NONE);
		composite_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		composite_1.setLayout(new FillLayout(SWT.VERTICAL));
		scrolledComposite.setContent(composite_1);
		scrolledComposite.setMinSize(composite_1.computeSize(SWT.DEFAULT, SWT.DEFAULT));
		
		readComponent(c);
		
		Composite composite = new Composite(this, SWT.NONE);
		composite.setLayoutData(BorderLayout.SOUTH);
		
		Button btnSchlieen = new Button(composite, SWT.NONE);
		btnSchlieen.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Control[] cl = composite_1.getChildren();
				
				for(Control ce : cl)
				{
					myComponent.setAttribute(((GUI_Attribute)ce).getKey(),((GUI_Attribute)ce).getValue());
				}
				me.dispose();
			}
		});
		btnSchlieen.setBounds(306, 0, 75, 25);
		btnSchlieen.setText("Save");
		
		Button btnNewButton = new Button(composite, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				me.dispose();
			}
		});
		btnNewButton.setBounds(230, 0, 75, 25);
		btnNewButton.setText("Cancel");
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText(myComponent.getId() + " - Attributes");
		setSize(387, 517);

	}
	
	public void readComponent(Component c)
	{
		Iterator<String> aite = c.getAttributes().keySet().iterator();
		while(aite.hasNext())
		{
			String attKey = aite.next();
			GUI_Attribute newAtt = new GUI_Attribute(composite_1);
			newAtt.setKey(attKey);
			newAtt.setValue(c.getAttribute(attKey));
		}
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
