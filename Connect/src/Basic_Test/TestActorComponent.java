package Basic_Test;

import Core.*;

public class TestActorComponent extends ActorComponent {

	public TestActorComponent() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TestActorComponent(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
	
	public void init(){
		this.addPort(new ConsumingPort("PortX", this));
		this.addPort(new ConsumingPort("PortY", this));
		this.addPort(new ConsumingPort("PortZ", this));
		
		this.setAttribute("t1", "45654265467");
		this.setAttribute("t2", "45654265467");
		this.setAttribute("t3", "45654265467");
		this.setAttribute("t4", "45654265467");
		this.setAttribute("t5", "45654265467");
		this.setAttribute("t6", "45654265467");
		this.setAttribute("t7", "45654265467");
		this.setAttribute("t8", "45654265467");
		this.setAttribute("t9", "45654265467");
		this.setAttribute("t10", "45654265467");
		this.setAttribute("t11", "45654265467");
		this.setAttribute("t12", "45654265467");
		this.setAttribute("t13", "45654265467");
		this.setAttribute("t14", "45654265467");
		this.setAttribute("t15", "45654265467");
		this.setAttribute("t16", "45654265467");
	}
	
	public void exec() {
		System.out.println("PortX: "+this.getPort("PortX").getState());
		System.out.println("PortY: "+this.getPort("PortY").getState());
		System.out.println("PortZ: "+this.getPort("PortZ").getState());
	}

}
