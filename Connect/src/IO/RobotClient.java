package IO;

import java.net.URI;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_10;
import org.java_websocket.handshake.ServerHandshake;


public class RobotClient extends WebSocketClient {

	static Draft_10 draft = new Draft_10();
	private boolean connected=false;
	private static RobotClient robotClient = null;
	private static String ws = null;
	private static float speed;
	
	private boolean pseudoDisconnect = false;
	
	public RobotClient(String ip) {
		super(URI.create(ip), draft);
		ws = ip;
	}

	@Override
	public void onClose(int arg0, String arg1, boolean arg2) {
		// TODO Auto-generated method stub

	}

	public String getWs() {
		return ws;
	}

	@Override
	public void onError(Exception arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMessage(String arg0) {
		String answer = arg0;
		System.out.println("Answer: "+answer);

	}

	@Override
	public void onOpen(ServerHandshake arg0) {
			System.out.println("Verbunden mit Server");
			connected=true;
	}

	public void doMoveForward(){
		System.out.println(speed);
		doPublishCommandVelocity((float) (0.1*speed),0.0f,0.0f,0.0f,0.0f,0.0f);
	}
	
	public void doMoveBackward(){
		doPublishCommandVelocity((float) (-0.1*speed),0.0f,0.0f,0.0f,0.0f,0.0f);
	}
	
	public void doMoveLeft(){
		doPublishCommandVelocity(0.0f,0.0f,0.0f,0.0f,0.0f,speed*0.5f);
	}
	
	public void doMoveRight(){
		doPublishCommandVelocity(0.0f,0.0f,0.0f,0.0f,0.0f,-speed*0.5f);
	}
	
	public void doMoveForwardLeft(){
		doPublishCommandVelocity((float) (0.1*speed),0.0f,0.0f,0.0f,0.0f,speed*0.5f);
	}
	
	public void doMoveForwardRight(){
		doPublishCommandVelocity((float) (0.1*speed),0.0f,0.0f,0.0f,0.0f,-speed*0.5f);
	}
	
	public void doMoveBackwardLeft(){
		doPublishCommandVelocity((float) (-0.1*speed),0.0f,0.0f,0.0f,0.0f,speed*0.5f);
	}
	
	public void doMoveBackwardRight(){
		doPublishCommandVelocity((float) (-0.1*speed),0.0f,0.0f,0.0f,0.0f,-speed*0.5f);
	}
	
	public static float getSpeed() {
		return speed;
	}

	public static void setSpeed(float speed) {
		RobotClient.speed = speed;
	}

	public void doPublishCommandVelocity(float lin_x, float lin_y, float lin_z, float ang_x , float ang_y, float ang_z){
		if(!connected) return;
		if(pseudoDisconnect) return;
		//String message = "{'op': 'publish', 'topic': '/mobile_base/commands/velocity', 'msg':{'linear':{'x':"+lin_x+",'y':"+lin_y+",'z':"+lin_z+"},'angular':{'x':"+ang_x+",'y':"+ang_y+",'z':"+ang_z+"}}}";
		//String message = "{'op': 'publish', 'topic': '/cmd_vel_mux/input/safety-controller', 'msg':{'linear':{'x':"+lin_x+",'y':"+lin_y+",'z':"+lin_z+"},'angular':{'x':"+ang_x+",'y':"+ang_y+",'z':"+ang_z+"}}}";
		String message = "{'op': 'publish', 'topic': '/key_cmd_vel', 'msg':{'linear':{'x':"+lin_x+",'y':"+lin_y+",'z':"+lin_z+"},'angular':{'x':"+ang_x+",'y':"+ang_y+",'z':"+ang_z+"}}}";

		//String message = "{'op': 'publish', 'topic': '/key_cmd_vel', 'msg':{'linear':{"+lin_x+","+lin_y+","+lin_z+"},'angular':{"+ang_x+","+ang_y+","+ang_z+"}}";
		message  = message.replace("'", "\"");
        System.out.println(message);
        send(message );
	}
	
	public void doPublishCommandLED(int value){
		if(!connected) return;
		String message = "{'op': 'publish', 'topic': '/mobile_base/commands/led1', 'msg':{'value':"+value+"}}";
        message  = message.replace("'", "\"");
        System.out.println(message);
        send(message );
	}
	
	public void sendServiceCall(String serviceName) {
		String message = "{'op': 'call_service', 'service': '"+serviceName+"' }";
		message  = message.replace("'", "\"");
		System.out.println(message);
		send(message);
	}

	public void setPseudoDisconnect(boolean pseudoDisconnect) {
		this.pseudoDisconnect = pseudoDisconnect;
	}
	
}
