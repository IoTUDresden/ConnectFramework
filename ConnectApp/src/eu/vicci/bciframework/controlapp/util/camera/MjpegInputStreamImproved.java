package eu.vicci.bciframework.controlapp.util.camera;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

@SuppressLint("NewApi") public class MjpegInputStreamImproved extends MjpegInputStream {

	private final static int HEADER_MAX_LENGTH = 110;
	private final static int FRAME_MAX_LENGTH = 50000 + HEADER_MAX_LENGTH;
	private final String CONTENT_LENGTH = "Content-Length:";
	private final String CONTENT_END = "\r\n";
	private final static byte[] gFrameData = new byte[FRAME_MAX_LENGTH];
	private final static byte[] gHeader = new byte[HEADER_MAX_LENGTH];
	BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
	byte[] CONTENT_LENGTH_BYTES;
	byte[] CONTENT_END_BYTES;

	public MjpegInputStreamImproved(InputStream in) {
		super(in);
		bitmapOptions.inSampleSize = 1;
		bitmapOptions.inPreferredConfig = Bitmap.Config.RGB_565;
		bitmapOptions.inPreferQualityOverSpeed = false;
		bitmapOptions.inPurgeable = true;
		try {
			CONTENT_LENGTH_BYTES = CONTENT_LENGTH.getBytes("UTF-8");
			CONTENT_END_BYTES = CONTENT_END.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public Bitmap readMjpegFrame() throws IOException {		
		mark(FRAME_MAX_LENGTH);
		int headerLen = getStartOfSequence(SOI_MARKER);
		
		if (headerLen > HEADER_MAX_LENGTH) return null;
		
		reset();
		readFully(gHeader, 0, headerLen);

		int contentLen;

		try {
			contentLen = parseContentLength(gHeader, headerLen);
		} catch (NumberFormatException nfe) {
			nfe.getStackTrace();
			contentLen = getEndOfSequence(EOF_MARKER);
		}

		reset();
		skipBytes(headerLen);
		readFully(gFrameData, 0, contentLen);


		Bitmap bm = BitmapFactory.decodeByteArray(gFrameData, 0, contentLen,bitmapOptions);
		bitmapOptions.inBitmap = bm;
		
		return bm;
	}

	protected int parseContentLength(byte[] headerBytes, int headerLen)
			throws IOException, NumberFormatException {
		ByteArrayInputStream headerIn = new ByteArrayInputStream(headerBytes);
		Properties props = new Properties();
		props.load(headerIn);
		return Integer.parseInt(props.getProperty(CONTENT_LENGTH));
	}

}
