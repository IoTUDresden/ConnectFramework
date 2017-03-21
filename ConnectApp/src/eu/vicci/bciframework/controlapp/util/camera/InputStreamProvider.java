package eu.vicci.bciframework.controlapp.util.camera;

import java.io.IOException;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.Bitmap;
import android.os.AsyncTask;

// source: http://stackoverflow.com/questions/10550139/android-ics-and-mjpeg-using-asynctask

public class InputStreamProvider extends AsyncTask<String, Object, Void> {

	private WebcamStreamListener streamListener;

	public InputStreamProvider(WebcamStreamListener streamListener) {
		this.streamListener = streamListener;
	}

	@Override
	protected Void doInBackground(String... url) {
		//Process.setThreadPriority(Process.THREAD_PRIORITY_FOREGROUND);
		while (!isCancelled()) {

			HttpResponse res = null;
			DefaultHttpClient httpclient = new DefaultHttpClient();
			publishProgress("Sending http request");
			try {
				res = httpclient.execute(new HttpGet(URI.create(url[0])));
				publishProgress("Request finished, status = "
						+ res.getStatusLine().getStatusCode());
				if (res.getStatusLine().getStatusCode() == 401) {
					publishProgress("Request failed - User Access Control is enabled");
					continue;
				}

				publishProgress("Webcam stream available");
				MjpegInputStream in = new MjpegInputStreamImproved(res.getEntity()
						.getContent());
				
//				long time = System.currentTimeMillis();
				
				while (!isCancelled()) {
					try {
						//
						Bitmap bitmap = in.readMjpegFrame();
						if(bitmap!=null){
							//System.out.println(1/((System.currentTimeMillis()-time)*0.001));
							onProgressUpdate("dummy", bitmap);
						}
						//time = System.currentTimeMillis();			
					} catch (HttpHostConnectException e) {
						publishProgress("HttpHostConnectException");
					}
					
					
				}
				in.close();

			} catch (ClientProtocolException e) {
				e.printStackTrace();
				publishProgress("Request failed - ClientProtocolException");
				// Error connecting to camera
			} catch (IOException e) {
				e.printStackTrace();
				publishProgress("Request failed - IOException");
				// Error connecting to camera e.g due to missing internet
				// permission
				// for this app or no internet connection at all
			} catch (Exception e) {
				publishProgress("Unexpected Exception");
				e.printStackTrace();
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
		return null;
	}

	@Override
	protected void onProgressUpdate(Object... status) {
		super.onProgressUpdate(status);
		if (status.length == 1) {
			streamListener.onStatusMessage((String) status[0]);
		} else if (status.length == 2) {
			streamListener.onBitmapReceived((Bitmap) status[1]);
		}
	}

}
