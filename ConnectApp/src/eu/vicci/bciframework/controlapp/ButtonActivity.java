package eu.vicci.bciframework.controlapp;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageButton;
import eu.vicci.bciframework.controlapp.util.udpclient.UDPClient;

@SuppressLint({ "NewApi", "ClickableViewAccessibility" }) public class ButtonActivity extends ActionBarActivity implements OnTouchListener{

	private ImageButton up,down,left,right;
	private UDPClient udpClient;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_button);
		
		up = (ImageButton) findViewById(R.id.button_up);
		down = (ImageButton) findViewById(R.id.button_down);
		left = (ImageButton) findViewById(R.id.button_left);
		right = (ImageButton) findViewById(R.id.button_right);
		
		up.setOnTouchListener(this);
		down.setOnTouchListener(this);
		left.setOnTouchListener(this);
		right.setOnTouchListener(this);
		
		udpClient = new UDPClient(getIntent().getStringExtra(MainActivity.ECO_IP_SAVE),UDPClient.TABLET_BUTTON_MODE);
		initKeyStatus();
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		udpClient.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
	}
	
	@Override
	protected void onStop() {
		udpClient.cancel(true);
		super.onStop();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.button, menu);
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

	SparseBooleanArray keyStatus = new SparseBooleanArray();
	
	private final int KEY_UP = 0;
	private final int KEY_DOWN = 1;
	private final int KEY_LEFT = 2;
	private final int KEY_RIGHT = 3;
	
	private void initKeyStatus(){
		keyStatus.put(KEY_UP, false);
		keyStatus.put(KEY_DOWN, false);
		keyStatus.put(KEY_LEFT, false);
		keyStatus.put(KEY_RIGHT, false);
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch(v.getId()){
		case R.id.button_up:
			if(isDown(event)) {
				keyStatus.put(KEY_UP, true);
				up.setImageResource(R.drawable.oben);
			} else{
				keyStatus.put(KEY_UP, false);
				up.setImageResource(R.drawable.oben_g);
			}
			break;
		case R.id.button_left:
			if(isDown(event)){
				keyStatus.put(KEY_LEFT, true);
				left.setImageResource(R.drawable.links);
			} else {
				keyStatus.put(KEY_LEFT, false);
				left.setImageResource(R.drawable.links_g);
			}
			break;
		case R.id.button_down:
			if(isDown(event)) {
				keyStatus.put(KEY_DOWN, true);
				down.setImageResource(R.drawable.unten);
			} else {
				keyStatus.put(KEY_DOWN, false);
				down.setImageResource(R.drawable.unten_g);
			}
			break;
		case R.id.button_right:
			if(isDown(event)) {
				keyStatus.put(KEY_RIGHT, true);
				right.setImageResource(R.drawable.rechts);
			} else {
				keyStatus.put(KEY_RIGHT, false);
				right.setImageResource(R.drawable.rechts_g);
			}
			break;
		}
		sendCommand();
		return false;
	}
	
	private boolean isDown(MotionEvent event){
		return event.getAction()==MotionEvent.ACTION_MOVE||event.getAction()==MotionEvent.ACTION_DOWN;
	}
	
	private void sendCommand(){
		double x=0,y=0;
		if(keyStatus.get(KEY_UP)) y-=1;
		if(keyStatus.get(KEY_LEFT)) x-=1;
		if(keyStatus.get(KEY_DOWN)) y+=1;
		if(keyStatus.get(KEY_RIGHT)) x+=1;
		udpClient.sendValue(x, y);
	}
	
}
