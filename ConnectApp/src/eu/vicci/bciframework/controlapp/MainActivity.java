package eu.vicci.bciframework.controlapp;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;


public class MainActivity extends ActionBarActivity {

	public final static String ECO_IP_SAVE = "ECO_IP_SAVE";
	public final static String MODE_SELECT_SAVE = "MODE_SELECT";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    
    
    @Override
    protected void onStart() {
    	super.onStart();
        SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
        String eco_ip =prefs.getString(ECO_IP_SAVE, "");
        EditText editText = (EditText)findViewById(R.id.ip_field);
        editText.setText(eco_ip);
                
        boolean gyro_mode = prefs.getBoolean(MODE_SELECT_SAVE, true);
        RadioGroup rg = (RadioGroup)findViewById(R.id.radio_group1);
        if(gyro_mode){
        	rg.check(R.id.gyro_mode);
        } else {
        	rg.check(R.id.button_mode);
        }
    }
    
    @Override
    protected void onStop() {
        SharedPreferences.Editor editor = getPreferences(Context.MODE_PRIVATE).edit();
        EditText editText = (EditText)findViewById(R.id.ip_field);
        editor.putString(ECO_IP_SAVE, editText.getText().toString());
        
        
        RadioButton rb = (RadioButton) findViewById(R.id.gyro_mode);
        editor.putBoolean(MODE_SELECT_SAVE, rb.isChecked());
        editor.commit();
    	super.onStop();
    }
    

    public void onConnectButtonClicked(View v){
    	if (v.getId()==R.id.connect_button) {
    		RadioButton rb = (RadioButton) findViewById(R.id.gyro_mode);
    		if(rb.isChecked()) transitTo(GyroActivity.class);
    		else transitTo(ButtonActivity.class);
		}
    }

    @SuppressWarnings("rawtypes")
	private void transitTo(Class transitClass) {
        Intent intent = new Intent(this,transitClass);
        String message = ((EditText)findViewById(R.id.ip_field)).getText().toString();
        intent.putExtra(ECO_IP_SAVE, message);
        startActivity(intent);
    }


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
