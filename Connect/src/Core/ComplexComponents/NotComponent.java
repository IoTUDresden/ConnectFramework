package Core.ComplexComponents;

import Core.*;

public class NotComponent extends ActorComponent {

	public NotComponent() {
		super();
		// TODO Auto-generated constructor stub
	}

	public final String PORT_ID_INPUT = "input";
	public final String PORT_ID_OUTPUT = "output";

	public NotComponent(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void exec() {
		if (!this.SecurityIsStopped) {
			this.getPort(PORT_ID_OUTPUT).setState(
					!this.getPort(PORT_ID_INPUT).getState());
		}
	}

	@Override
	public void init() {

		this.addPort(new ConsumingPort(PORT_ID_INPUT, this));
		this.addPort(new ProducingPort(PORT_ID_OUTPUT));

		super.init();
	}

	@Override
	public void finalies() {
		// TODO Auto-generated method stub
		super.finalies();
	}

}
