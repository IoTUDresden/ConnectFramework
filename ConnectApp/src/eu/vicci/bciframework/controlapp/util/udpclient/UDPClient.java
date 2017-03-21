package eu.vicci.bciframework.controlapp.util.udpclient;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

import android.os.AsyncTask;

public class UDPClient extends AsyncTask<Void, Void, Void> {

	public static final int TABLET_GYRO_MODE = 0;
	public static final int TABLET_BUTTON_MODE = 1;
	
	private double valueX,valueY;
	private String ip;
	private final Object lock = new Object();
	private static final int DEFAULT_PORT = 9876;
	private int mode;
	
	private boolean sendMessage=false;
	public UDPClient(String ip, int mode) {
		this.ip = ip;
		this.mode = mode;
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		DatagramSocket clientSocket = null;
		InetAddress IPAddress = null;
		try {
			clientSocket = new DatagramSocket();
			IPAddress = InetAddress.getByName(ip);
		} catch (SocketException e1) {
			e1.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		if(clientSocket==null||IPAddress==null) return null;
		while (!isCancelled()) {
			if(sendMessage){
				try {
					byte[] sendData = prepareBytesToSend((float)valueX,(float)valueY);
					DatagramPacket sendPacket = new DatagramPacket(sendData,
							sendData.length, IPAddress, DEFAULT_PORT);
					clientSocket.send(sendPacket);
				} catch (SocketException e) {
					e.printStackTrace();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				if(mode==TABLET_GYRO_MODE) {
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						cancel(true);
					}
				}
			}
		}
		if (clientSocket != null) {
			sendStopCommand(clientSocket);
			clientSocket.close();
		}
			
		return null;
	}
	
	private void sendStopCommand(DatagramSocket clientSocket) {
		InetAddress IPAddress = null;
		try {
			clientSocket = new DatagramSocket();
			IPAddress = InetAddress.getByName(ip);
			byte[] sendData = prepareBytesToSend(0.0f,0.0f);
			DatagramPacket sendPacket = new DatagramPacket(sendData,
					sendData.length, IPAddress, DEFAULT_PORT);
			clientSocket.send(sendPacket);
		} catch (SocketException e1) {
			e1.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private byte[] prepareBytesToSend(float valueX, float valueY) throws Exception{
	    ByteArrayOutputStream byteStream=new ByteArrayOutputStream();
	    DataOutputStream dataStream=new DataOutputStream(byteStream);
		synchronized(lock){
			dataStream.writeInt(mode);
			dataStream.writeFloat(valueX);
			dataStream.writeFloat(valueY);
			sendMessage = false;
		}
		dataStream.close();
		return byteStream.toByteArray();
	}

	public static String toByteArray(Double value) {
	    byte[] bytes = new byte[8];
	    ByteBuffer.wrap(bytes).putDouble(value);
	    return new String(bytes);
	}

	public synchronized void sendValue(double valueX,double valueY) {
		synchronized(lock){
			this.valueX = valueX;
			this.valueY = valueY;
			sendMessage = true;
		}
	}

}
