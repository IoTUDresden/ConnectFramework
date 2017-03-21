package Core.ComplexComponents;

import Core.*;

public class OrComponent extends ActorComponent {

	public OrComponent() {
		super();
		// TODO Auto-generated constructor stub
	}

	public final String PORT_ID_INPUT1 = "input1";
	public final String PORT_ID_INPUT2 = "input2";
	public final String PORT_ID_OUTPUT = "output";

	public OrComponent(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void exec() {
		if (!this.SecurityIsStopped) {

			this.getPort(PORT_ID_OUTPUT).setState(
					this.getPort(PORT_ID_INPUT1).getState()
							|| this.getPort(PORT_ID_INPUT2).getState());
		}
	}

	@Override
	public void init() {

		this.addPort(new ConsumingPort(PORT_ID_INPUT1, this));
		this.addPort(new ConsumingPort(PORT_ID_INPUT2, this));
		this.addPort(new ProducingPort(PORT_ID_OUTPUT));

		super.init();
	}

	@Override
	public void finalies() {
		// TODO Auto-generated method stub
		super.finalies();
	}

}
