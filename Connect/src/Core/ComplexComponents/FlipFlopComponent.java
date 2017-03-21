package Core.ComplexComponents;

import Core.*;

public class FlipFlopComponent extends ActorComponent {

	public FlipFlopComponent() {
		super();
		// TODO Auto-generated constructor stub
	}

	public final String PORT_ID_SETTER = "setter";
	public final String PORT_ID_RESETTER = "resetter";
	public final String PORT_ID_OUTPUT = "output";

	public FlipFlopComponent(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void exec() {
		if (!this.SecurityIsStopped) {
			if (this.getPort(PORT_ID_RESETTER).getState())
				this.getPort(PORT_ID_OUTPUT).setState(false);
			if (this.getPort(PORT_ID_SETTER).getState())
				this.getPort(PORT_ID_OUTPUT).setState(true);
		}
	}

	@Override
	public void init() {

		this.addPort(new ConsumingPort(PORT_ID_SETTER, this));
		this.addPort(new ConsumingPort(PORT_ID_RESETTER, this));
		this.addPort(new ProducingPort(PORT_ID_OUTPUT));

		super.init();
	}

	@Override
	public void finalies() {
		// TODO Auto-generated method stub
		super.finalies();
	}

}
