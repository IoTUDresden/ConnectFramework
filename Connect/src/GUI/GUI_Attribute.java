package GUI;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class GUI_Attribute extends Composite {

	private Text text;
	private Label lblAttribute;
	
	
	public GUI_Attribute(Composite parent) {
		super(parent, SWT.NONE);
		
		this.setLayout(null);
		
		lblAttribute = new Label(this, SWT.NONE);
		lblAttribute.setBounds(10, 10, 110, 15);
		lblAttribute.setText("Attribute:");
		
		text = new Text(this, SWT.BORDER);
		text.setBounds(167, 10, 203, 21);
	}
	
	public void setValue(String v)
	{
		text.setText(v);
	}
	
	public String getValue()
	{
		return text.getText();
	}
	
	public void setKey(String k)
	{
		lblAttribute.setText(k);
	}
	
	public String getKey()
	{
		return lblAttribute.getText();
	}

}
