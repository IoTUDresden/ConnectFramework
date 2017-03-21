package IO;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import Core.ProducingPort;

public class TabletClient extends Thread{

	private static final int DEFAULT_PORT = 9876;
	private static final int TABLET_GYRO_MODE = 0;
	private static final int TABLET_BUTTON_MODE = 1;
	
	private static TabletClient me;
	
	private TabletGyroComponent gyro;
	private FloatLimitProducingPort gyroTop;
	private FloatLimitProducingPort gyroDown;
	private FloatLimitProducingPort gyroLeft;
	private FloatLimitProducingPort gyroRight;
	
	private TabletButtonComponent button;
	private ProducingPort buttonTop;
	private ProducingPort buttonDown;
	private ProducingPort buttonLeft;
	private ProducingPort buttonRight;
	
	private TabletClient(){
		setDaemon(true);
		start();
	}
	
	public static TabletClient getInstance(){
		if (me == null) {
			me = new TabletClient();
		}
		return me;
	}
	
	@Override
	public void run() {
		DatagramSocket serverSocket = null;
		byte[] receiveData= new byte[1024];
		try {
			serverSocket = new DatagramSocket(DEFAULT_PORT);
		} catch (SocketException e1) {
			e1.printStackTrace();
		}
		while(!isInterrupted()){
            DatagramPacket receivePacket = new DatagramPacket(receiveData, 1024);
            try {
				serverSocket.receive(receivePacket);
	            byte[] receivedData = receivePacket.getData();
	            ByteArrayInputStream byteInput = new ByteArrayInputStream(receivedData);
	            DataInputStream dataStream = new DataInputStream(byteInput);
	            int mode = dataStream.readInt();
	            float x = dataStream.readFloat();
	            float y = dataStream.readFloat();
	            if(gyro!=null&&mode==TABLET_GYRO_MODE){
	            	publishGyroDataOnPorts(x,y);
	            }
	           if(button!=null&&mode==TABLET_BUTTON_MODE){
	        	   publishButtonDataOnPorts(x,y);
	           }
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(serverSocket!=null) serverSocket.close();
	}
	
	private void publishButtonDataOnPorts(float x, float y) {
		if(x<0) {
			this.buttonLeft.setState(true);
			this.buttonRight.setState(false);
		} else if(x>0){
			this.buttonRight.setState(true);
			this.buttonLeft.setState(false);
		} else {
			this.buttonRight.setState(false);
			this.buttonLeft.setState(false);
		}
		
		if(y<0)
		{
			this.buttonTop.setState(true);
			this.buttonDown.setState(false);
		} else if(y>0){
			this.buttonDown.setState(true);
			this.buttonTop.setState(false);
		} else {
			this.buttonTop.setState(false);
			this.buttonDown.setState(false);
		}
	}

	private void publishGyroDataOnPorts(float x, float y) {
		if(x<=0) {
			this.gyroLeft.setValue((float)(-x));
			this.gyroRight.setValue(0);
		} else {
			this.gyroRight.setValue((float)x);
			this.gyroLeft.setValue(0);
		}
		
		if(y<=0)
		{
			this.gyroTop.setValue((float)(-y));
			this.gyroDown.setValue(0);
		} else {
			this.gyroDown.setValue((float)(y));
			this.gyroTop.setValue(0);
		}
	}
	
	public void setGyro(TabletGyroComponent gyro) {
		this.gyro = gyro;
		this.gyroTop = (FloatLimitProducingPort) gyro.getPort(gyro.GYRO_PORT_TOP);
		this.gyroDown = (FloatLimitProducingPort) gyro.getPort(gyro.GYRO_PORT_DOWN);
		this.gyroLeft = (FloatLimitProducingPort) gyro.getPort(gyro.GYRO_PORT_LEFT);
		this.gyroRight = (FloatLimitProducingPort) gyro.getPort(gyro.GYRO_PORT_RIGHT);
	}
	
	public void unsetGyro(){
		this.gyro = null;
	}
	
	public void setButton(TabletButtonComponent button){
		this.button = button;
		this.buttonTop = (ProducingPort) button.getPort(button.BUTTON_PORT_TOP);
		this.buttonDown =  (ProducingPort) button.getPort(button.BUTTON_PORT_DOWN);
		this.buttonLeft = (ProducingPort) button.getPort(button.BUTTON_PORT_LEFT);
		this.buttonRight = (ProducingPort) button.getPort(button.BUTTON_PORT_RIGHT);
	}
	
	public void unsetButton(){
		this.button = null;
	}
	
}
