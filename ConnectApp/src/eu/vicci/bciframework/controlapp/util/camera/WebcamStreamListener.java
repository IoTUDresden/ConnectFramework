package eu.vicci.bciframework.controlapp.util.camera;

import android.graphics.Bitmap;

public interface WebcamStreamListener {

	public void onStatusMessage(String message);
	public void onBitmapReceived(Bitmap bitmap);
	
}
