package Core.ComplexComponents;

import Core.*;

public class TriggerComponent extends ActorComponent {

	public TriggerComponent() {
		super();
		// TODO Auto-generated constructor stub
	}

	public final String PORT_ID_INPUT = "input";
	public final String PORT_ID_OUTPUT = "output";

	private boolean internState = false;

	public TriggerComponent(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void exec() {
		if (!this.SecurityIsStopped) {

			boolean i = this.getPort(PORT_ID_INPUT).getState();
			boolean o = this.getPort(PORT_ID_OUTPUT).getState();

			if (this.getPort(PORT_ID_OUTPUT).getState()) {
				if (this.getPort(PORT_ID_INPUT).getState()) {
					if (!internState) {
						internState = false;
						this.getPort(PORT_ID_OUTPUT).setState(false);
					}
				} else {
					if (internState) {
						internState = false;
					}
				}
			} else {
				if (this.getPort(PORT_ID_INPUT).getState()) {
					if (!internState) {
						this.getPort(PORT_ID_OUTPUT).setState(true);
						internState = true;
					}
				} else {
					if (internState)
						internState = true;
				}
			}
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
