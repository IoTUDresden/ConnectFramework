package eu.vicci.bciframework.controlapp;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import eu.vicci.bciframework.controlapp.util.gyro.GyroSensorListener;
import eu.vicci.bciframework.controlapp.util.gyro.GyroSensorReader;
import eu.vicci.bciframework.controlapp.util.udpclient.UDPClient;

@SuppressLint("NewApi")
public class GyroActivity extends ActionBarActivity implements
		GyroSensorListener {

	//private WebcamView webcamView;
	//private InputStreamProvider inputStreamProvider;
	private GyroSensorReader gyroSensorReader;
	//private final static String webcam_url_unformated = "http://%s:8080/stream?topic=/camera/image_raw?quality=80";
	//private String webcam_url;
	private UDPClient udpClient;

	private ImageView imageViewUp, imageViewDown, imageViewLeft,
			imageViewRight;
	private Bitmap bitmapUp, bitmapDown, bitmapLeft, bitmapRight;
	private Bitmap bitmapUpGray, bitmapDownGray, bitmapLeftGray,
			bitmapRightGray;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gyro);

		gyroSensorReader = new GyroSensorReader(this.getApplicationContext(),
				this);
		udpClient = new UDPClient(getIntent().getStringExtra(
				MainActivity.ECO_IP_SAVE),UDPClient.TABLET_GYRO_MODE);

		initBitmaps();
		initImageViews();
	}

	private void initBitmaps() {
		bitmapUp = BitmapFactory
				.decodeResource(getResources(), R.drawable.oben);
		bitmapDown = BitmapFactory.decodeResource(getResources(),
				R.drawable.unten);
		bitmapLeft = BitmapFactory.decodeResource(getResources(),
				R.drawable.links);
		bitmapRight = BitmapFactory.decodeResource(getResources(),
				R.drawable.rechts);
		bitmapUpGray = BitmapFactory.decodeResource(getResources(),
				R.drawable.oben_g);
		bitmapDownGray = BitmapFactory.decodeResource(getResources(),
				R.drawable.unten_g);
		bitmapLeftGray = BitmapFactory.decodeResource(getResources(),
				R.drawable.links_g);
		bitmapRightGray = BitmapFactory.decodeResource(getResources(),
				R.drawable.rechts_g);
	}

	private void initImageViews() {
		imageViewUp = (ImageView) findViewById(R.id.button_up);
		imageViewDown = (ImageView) findViewById(R.id.button_down);
		imageViewLeft = (ImageView) findViewById(R.id.button_left);
		imageViewRight = (ImageView) findViewById(R.id.button_right);
	}

	int midCount = 0;

	private void visualizeGyro(double x_g, double y_g) {

		if (y_g < 0) {
			Bitmap bitmap = Bitmap.createBitmap(200, 200, Config.ARGB_8888);
			Canvas c = new Canvas(bitmap);
			y_g = Math.abs(y_g);
			if (y_g > 1)
				y_g = 1;
			y_g = (1 - y_g);
			Rect r1 = new Rect(0, 0, 200, (int) (200 * y_g));
			Rect r2 = new Rect(0, (int) (200 * y_g), 200, 200);
			c.drawBitmap(bitmapUpGray, r1, r1, null);
			c.drawBitmap(bitmapUp, r2, r2, null);
			imageViewUp.setImageBitmap(bitmap);
			imageViewDown.setImageBitmap(bitmapDownGray);
		} else if (y_g > 0) {
			Bitmap bitmap = Bitmap.createBitmap(200, 200, Config.ARGB_8888);
			Canvas c = new Canvas(bitmap);
			if (y_g > 1)
				y_g = 1;
			Rect r1 = new Rect(0, 0, 200, (int) (200 * y_g));
			Rect r2 = new Rect(0, (int) (200 * y_g), 200, 200);
			c.drawBitmap(bitmapDown, r1, r1, null);
			c.drawBitmap(bitmapDownGray, r2, r2, null);
			imageViewDown.setImageBitmap(bitmap);
			imageViewUp.setImageBitmap(bitmapUpGray);
		}
		if (x_g < 0) { // Links
			Bitmap bitmap = Bitmap.createBitmap(200, 200, Config.ARGB_8888);
			Canvas c = new Canvas(bitmap);
			x_g = Math.abs(x_g);
			if (x_g > 1)
				x_g = 1;
			x_g = (1 - x_g);
			Rect r1 = new Rect(0, 0, (int) (200 * x_g), 200);
			Rect r2 = new Rect((int) (200 * x_g), 0, 200, 200);
			c.drawBitmap(bitmapLeftGray, r1, r1, null);
			c.drawBitmap(bitmapLeft, r2, r2, null);
			imageViewLeft.setImageBitmap(bitmap);
			imageViewRight.setImageBitmap(bitmapRightGray);
		} else if (x_g > 0) {
			Bitmap bitmap = Bitmap.createBitmap(200, 200, Config.ARGB_8888);
			Canvas c = new Canvas(bitmap);
			if (x_g > 1)
				x_g = 1;
			Rect r1 = new Rect(0, 0, (int) (200 * x_g), 200);
			Rect r2 = new Rect((int) (200 * x_g), 0, 200, 200);
			c.drawBitmap(bitmapRight, r1, r1, null);
			c.drawBitmap(bitmapRightGray, r2, r2, null);
			imageViewRight.setImageBitmap(bitmap);
			imageViewLeft.setImageBitmap(bitmapLeftGray);
		}
	}

	@SuppressLint("NewApi")
	@Override
	protected void onStart() {
		super.onStart();
		gyroSensorReader.start();
		// inputStreamProvider.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
		// webcam_url);
		udpClient.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
	}

	@Override
	protected void onStop() {
		udpClient.cancel(true);
		gyroSensorReader.stop();
		// inputStreamProvider.cancel(true);
		super.onStop();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.gyro, menu);
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

	@Override
	public void onSensorData(double xAxis, double yAxis) {
		udpClient.sendValue(xAxis, yAxis);
		visualizeGyro(xAxis, yAxis);
	}
}
