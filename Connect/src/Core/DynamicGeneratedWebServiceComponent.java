package Core;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.wsdl.Binding;
import javax.wsdl.Definition;
import javax.wsdl.Message;
import javax.wsdl.Service;
import javax.wsdl.WSDLException;
import javax.wsdl.extensions.ExtensibilityElement;
import javax.wsdl.factory.WSDLFactory;
import javax.wsdl.xml.WSDLReader;
import javax.xml.bind.Element;
import javax.xml.namespace.QName;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_10;
import org.java_websocket.handshake.ServerHandshake;

import com.ibm.wsdl.ServiceImpl;

public class DynamicGeneratedWebServiceComponent extends ActorComponent {

	public static final String ATTRIBUT_WSDL_LOCATION = "wsdlLocation";
	public static final String ATTRIBUT_WSDL_URI = "wsdlURI";

	private HttpClient client;
	private HttpGet httppost;

	public DynamicGeneratedWebServiceComponent() {
		super();
	}

	public DynamicGeneratedWebServiceComponent(String id) {
		super(id);
	}

	public DynamicGeneratedWebServiceComponent(String id, String wsdlLocation)
			throws WSDLException {
		super(id);
		this.setAttribute(this.ATTRIBUT_WSDL_LOCATION, wsdlLocation);
		this.generateComponentFromWsdl();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void exec() {
		if (!this.SecurityIsStopped) {

			Iterator<Port> portIte = this.getPorts().iterator();

			// TEST

			// !!!!!!!!!!!!!!!!!!!!!

			while (portIte.hasNext()) {
				Port nextPort = portIte.next();
				if (nextPort.getState()) {
					// System.out.println(this.getAttribute(this.ATTRIBUT_WSDL_URI)
					// + "?method=" + nextPort.getId());
					httppost = new HttpGet(
							this.getAttribute(this.ATTRIBUT_WSDL_URI)
									+ "?method=" + nextPort.getId());

					try {
						client.execute(httppost);
					} catch (ClientProtocolException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					httppost.releaseConnection();
				}
			}
		}
	}

	@Override
	public void init() {
		super.init();
		client = new DefaultHttpClient();
	}

	@Override
	public void finalies() {
		// TODO Auto-generated method stub
		super.finalies();
	}

	public void generateComponentFromWsdl() throws WSDLException {
		if (this.getAttribute(this.ATTRIBUT_WSDL_LOCATION) != null) {
			this.setId(this.getId() + " - "
					+ this.getAttribute(this.ATTRIBUT_WSDL_LOCATION));

			WSDLFactory factory = WSDLFactory.newInstance();
			WSDLReader reader = factory.newWSDLReader();
			Definition wsdlInstance = reader.readWSDL(null,
					this.getAttribute(this.ATTRIBUT_WSDL_LOCATION));

			Map<?, Message> messages = wsdlInstance.getMessages();

			// URI aus Service auslesen
			Map<QName, Service> services = wsdlInstance.getServices();

			Set<QName> serviceKeySet = services.keySet();

			Iterator<QName> serviceAttributeIte = serviceKeySet.iterator();
			QName serviceKey = serviceAttributeIte.next();

			Service service = services.get(serviceKey);

			// this.setAttribute(this.ATTRIBUT_WSDL_URI,serviceKey.getNamespaceURI());

			Map<?, javax.wsdl.Port> ports = service.getPorts();
			Set<?> portkey = ports.keySet();
			Iterator<?> portite = portkey.iterator();

			javax.wsdl.Port porttest = ports.get(portite.next());

			String extstring = porttest.getExtensibilityElements().get(0)
					.toString();

			this.setAttribute(this.ATTRIBUT_WSDL_URI,
					extstring.substring(extstring.indexOf("locationURI=") + 12));

			Map<QName, Binding> bindings = wsdlInstance.getBindings();

			Iterator<QName> bindingsIte = bindings.keySet().iterator();

			Binding binding = bindings.get(bindingsIte.next());

			List<com.ibm.wsdl.BindingOperationImpl> operationList = binding
					.getBindingOperations();

			Iterator<com.ibm.wsdl.BindingOperationImpl> opIte = operationList
					.iterator();

			while (opIte.hasNext()) {
				this.addPort(new ConsumingPort(opIte.next().getName(), this));
			}
		}
	}

}
