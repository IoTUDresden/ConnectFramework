package eu.vicci.bciframework.controlapp.util.camera;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

//source: http://stackoverflow.com/questions/10550139/android-ics-and-mjpeg-using-asynctask

public class MjpegInputStream extends DataInputStream {
	private static final String TAG = "MjpegInputStream";

	protected final byte[] SOI_MARKER = { (byte) 0xFF, (byte) 0xD8 };
	protected final byte[] EOF_MARKER = { (byte) 0xFF, (byte) 0xD9 };
	protected final String CONTENT_LENGTH = "Content-Length";
	private final static int HEADER_MAX_LENGTH = 100;
	protected final static int FRAME_MAX_LENGTH = 400000 + HEADER_MAX_LENGTH;
	private int mContentLength = -1;

	public MjpegInputStream(InputStream in) {
		super(new BufferedInputStream(in, FRAME_MAX_LENGTH));
	}

	protected int getEndOfSequence(byte[] sequence) throws IOException {
		int seqIndex = 0;
		byte c;
		for (int i = 0; i < FRAME_MAX_LENGTH; i++) {
			c = (byte) readUnsignedByte();
			if (c == sequence[seqIndex]) {
				seqIndex++;
				if (seqIndex == sequence.length) {
					return i + 1;
				}
			} else {
				seqIndex = 0;
			}
		}
		return -1;
	}

	protected int getStartOfSequence(byte[] sequence) throws IOException {
		int end = getEndOfSequence(sequence);
		return (end < 0) ? (-1) : (end - sequence.length);
	}

	protected int parseContentLength(byte[] headerBytes) throws IOException,
			NumberFormatException {
		ByteArrayInputStream headerIn = new ByteArrayInputStream(headerBytes);
		Properties props = new Properties();
		props.load(headerIn);
		return Integer.parseInt(props.getProperty(CONTENT_LENGTH));
	}

	public Bitmap readMjpegFrame() throws IOException {
		mark(FRAME_MAX_LENGTH);
		int headerLen = getStartOfSequence(SOI_MARKER);
		reset();
		byte[] header = new byte[headerLen];
		readFully(header);
		try {
			mContentLength = parseContentLength(header);
		} catch (NumberFormatException nfe) {
			nfe.getStackTrace();
			Log.d(TAG, "catch NumberFormatException hit", nfe);
			mContentLength = getEndOfSequence(EOF_MARKER);
		}
		reset();
		byte[] frameData = new byte[mContentLength];
		skipBytes(headerLen);
		readFully(frameData);
		return BitmapFactory.decodeStream(new ByteArrayInputStream(frameData));
	}
}