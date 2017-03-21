package Core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : XMLPersistenceHandler.java
//  @ Date : 19.05.2014
//  @ Author : 
//
//
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartDocument;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import GUI.GUI_Component;
import IO.FloatLimitProducingPort;

public class XMLPersistenceHandler implements PersistenceHandler {
	@Override
	public void saveConfiguration(String confFile, Manager manager)
			throws Exception {
		// Instancen der n�tigen XML-Klassen
		XMLOutputFactory outputFactory = XMLOutputFactory.newFactory();
		XMLEventWriter eventWriter = outputFactory
				.createXMLEventWriter(new FileOutputStream(confFile));
		XMLEventFactory eventFactory = XMLEventFactory.newInstance();
		XMLEvent end = eventFactory.createDTD("\n");
		// Start des Schreibens des Dokumentes
		StartDocument startDocument = eventFactory.createStartDocument();
		eventWriter.add(startDocument);
		// Root-Tag
		eventWriter.add(eventFactory.createStartElement("", "", "conf"));
		eventWriter.add(end);

		// Components
		List<Component> componentList = manager.getComponents();

		for (Component c : componentList) {
			// Component Anfang
			eventWriter.add(eventFactory
					.createStartElement("", "", "component"));
			eventWriter.add(end);

			// Component Attribute
			createNode(eventWriter, "class", c.getClass().getName());
			createNode(eventWriter, "id", c.getId());
			
			// GUI-Informationen
			if(c.getMyGUI() != null)
			{
				eventWriter.add(eventFactory
						.createStartElement("", "", "gui"));
				eventWriter.add(end);
				
				GUI_Component gui = c.getMyGUI();
				
				createNode(eventWriter, "x", Integer.toString(gui.getBounds().x));
				createNode(eventWriter, "y", Integer.toString(gui.getBounds().y));
				
				eventWriter.add(eventFactory.createEndElement("", "", "gui"));
				eventWriter.add(end);
			}
			
			//Attribute
			Map<String, String> attributMap = c.getAttributes();
			
			if(attributMap.size() > 0){
				eventWriter.add(eventFactory
						.createStartElement("", "", "attributes"));
				eventWriter.add(end);
				
				Set<String> keySet = attributMap.keySet();
				Iterator<String> ite = keySet.iterator();
				
				while(ite.hasNext()){
					eventWriter.add(eventFactory
							.createStartElement("", "", "attribut"));
					eventWriter.add(end);
					
					String nextKey = ite.next();
					createNode(eventWriter, "key", nextKey);
					createNode(eventWriter, "value", attributMap.get(nextKey));
					
					eventWriter.add(eventFactory.createEndElement("", "", "attribut"));
					eventWriter.add(end);
				}
				
				eventWriter.add(eventFactory.createEndElement("", "", "attributes"));
				eventWriter.add(end);
			}
			
			// Ports
			List<Port> portList = c.getPorts();

			if (portList.size() > 0) {

				eventWriter.add(eventFactory
						.createStartElement("", "", "ports"));
				eventWriter.add(end);

				for (Port p : portList) {
					// Port Anfang
					eventWriter.add(eventFactory.createStartElement("", "",
							"port"));
					eventWriter.add(end);
					// Component Attribute
					createNode(eventWriter, "class", p.getClass().getName());
					createNode(eventWriter, "id", p.getId());
					createNode(eventWriter, "state", p.getState().toString());
					// todo: dynamische Attribute abfragen

					// verbundene Ports // Check auf ProducingPort
					boolean isProducingPort = false;
					Class cl = p.getClass();
					
					while(!cl.getName().equals("java.lang.Object") && !isProducingPort)
					{
						if(cl.getName().equals("Core.ProducingPort")) isProducingPort = true;
						cl = cl.getSuperclass();
					}
					
					// Check auf FloatLimitProducingPort
					boolean isFloatLimitProducingPort = false;
					cl = p.getClass();
					
					while(!cl.getName().equals("java.lang.Object") && !isFloatLimitProducingPort)
					{
						if(cl.getName().equals("IO.FloatLimitProducingPort")) isFloatLimitProducingPort = true;
						cl = cl.getSuperclass();
					}
					if (isFloatLimitProducingPort)
					{
						float limit = ((FloatLimitProducingPort) p).getLimit();
						createNode(eventWriter, "limit", String.valueOf(limit));
					}
					if (isProducingPort) {
						List<ConsumingPort> conPortList = ((ProducingPort) p)
								.getConsumingPorts();

						if (conPortList.size() > 0) {

							eventWriter.add(eventFactory.createStartElement("",
									"", "connectedPorts"));
							eventWriter.add(end);

							for (ConsumingPort cp : conPortList) {
								// Port Anfang
								eventWriter.add(eventFactory
										.createStartElement("", "",
												"connectedPort"));
								eventWriter.add(end);

								createNode(eventWriter, "componentId", cp
										.getActorComponent().getId());
								createNode(eventWriter, "portId", cp.getId());

								// Port Ende
								eventWriter.add(eventFactory.createEndElement(
										"", "", "connectedPort"));
								eventWriter.add(end);
							}

							eventWriter.add(eventFactory.createEndElement("",
									"", "connectedPorts"));
							eventWriter.add(end);
						}

					}

					// Port Ende
					eventWriter.add(eventFactory.createEndElement("", "",
							"port"));
					eventWriter.add(end);
				}
				eventWriter.add(eventFactory.createEndElement("", "", "ports"));
				eventWriter.add(end);
			}

			// Component Ende
			eventWriter.add(eventFactory.createEndElement("", "", "component"));
			eventWriter.add(end);
		}

		// Ende des Dokumentes
		eventWriter.add(eventFactory.createEndElement("", "", "config"));
		eventWriter.add(end);
		eventWriter.add(eventFactory.createEndDocument());
		eventWriter.close();
	}

	private void createNode(XMLEventWriter eventWriter, String name,
			String value) throws XMLStreamException {

		XMLEventFactory eventFactory = XMLEventFactory.newInstance();
		XMLEvent end = eventFactory.createDTD("\n");
		XMLEvent tab = eventFactory.createDTD("\t");
		// create Start node
		StartElement sElement = eventFactory.createStartElement("", "", name);
		eventWriter.add(tab);
		eventWriter.add(sElement);
		// create Content
		Characters characters = eventFactory.createCharacters(value);
		eventWriter.add(characters);
		// create End node
		EndElement eElement = eventFactory.createEndElement("", "", name);
		eventWriter.add(eElement);
		eventWriter.add(end);
	}

	@Override
	public void loadConfiguration(String confFile, Manager manager)
			throws Exception {
		
		manager.getComponents().clear();

		XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		// Setup a new eventReader
		InputStream in = new FileInputStream(confFile);
		XMLEventReader eventReader = inputFactory.createXMLEventReader(in);

		// States des auslesens
		boolean inConf = false;
		boolean inComponent = false;
		boolean inPorts = false;
		boolean inPort = false;
		boolean inConsumingPorts = false;
		boolean inConsumingPort = false;
		boolean inAttributes = false;
		boolean inGui = false;

		// Speicher f�r tempor�re Namen
		String tempComponentClass = null;
		String tempComponentId = null;
		String tempPortClass = null;
		String tempPortId = null;
		String tempPortValue = null;
		String tempPortLimit = null;
		String tempConPortId = null;
		String tempConComponentId = null;
		String tempAttributKey = null;
		int tempGuiX = 0;
		int tempGuiY = 0;

		// Speicher f�r tempor�re Verbindungen
		String[][] connections = new String[100][4];
		int connectionsCount = 0;

		while (eventReader.hasNext()) {
			XMLEvent event = eventReader.nextEvent();

			if (event.isStartElement()) {
				StartElement startElement = event.asStartElement();

				if (startElement.getName().getLocalPart().equals("conf")) {
					inConf = true;
				}

				if (inConf) {

					if (startElement.getName().getLocalPart()
							.equals("component")) {
						inComponent = true;
					}

					if (inComponent) {

						if (!inPort) {
							if (startElement.asStartElement().getName()
									.getLocalPart().equals("class")) {
								event = eventReader.nextEvent();
								tempComponentClass = event.asCharacters()
										.getData();
							}

							if (startElement.getName().getLocalPart()
									.equals("id")) {
								event = eventReader.nextEvent();
								tempComponentId = event.asCharacters()
										.getData();
								manager.makeComponent(tempComponentClass,
										tempComponentId);
							}

						}
						
						//neu
						if (startElement.getName().getLocalPart()
								.equals("gui")) {
							inGui = true;
						}
						
						if(inGui)
						{
							if (startElement.getName().getLocalPart()
									.equals("x")) {
								event = eventReader.nextEvent();
								tempGuiX = Integer.parseInt(event.asCharacters()
										.getData());
							}
							if (startElement.getName().getLocalPart()
									.equals("y")) {
								event = eventReader.nextEvent();
								manager.getComponent(tempComponentId).setTempCoordinates(tempGuiX, Integer.parseInt(event.asCharacters()
										.getData()));
							}
						}
						//Ende neu
						
						if (startElement.getName().getLocalPart()
								.equals("attribut")) {
							inAttributes = true;
						}
						
						if(inAttributes)
						{
							if (startElement.getName().getLocalPart()
									.equals("key")) {
								event = eventReader.nextEvent();
								tempAttributKey = event.asCharacters()
										.getData();
							}
							if (startElement.getName().getLocalPart()
									.equals("value")) {
								event = eventReader.nextEvent();
								manager.getComponent(tempComponentId).setAttribute(tempAttributKey, event.asCharacters().getData());
							}
						}

						if (startElement.getName().getLocalPart()
								.equals("ports")) {
							inPorts = true;
						}

						if (inPorts) {

							if (startElement.getName().getLocalPart()
									.equals("port")) {
								inPort = true;
							}

							if (inPort) {

								if (startElement.asStartElement().getName()
										.getLocalPart().equals("class")) {
									event = eventReader.nextEvent();
									tempPortClass = event.asCharacters()
											.getData();
								}

								if (startElement.getName().getLocalPart()
										.equals("id")) {
									event = eventReader.nextEvent();
									tempPortId = event.asCharacters().getData();
									
									//Ports hinzuf�gen falls dynamisch erstellt
									if(!manager.getComponent(tempComponentId).hastPort(tempPortId))
									{
										if(tempPortClass.equals("Core.ConsumingPort"))
										{
											manager.getComponent(tempComponentId).addPort(new ConsumingPort(tempPortId, (ActorComponent) Manager.getInstance().getComponent(tempComponentId)));
										}
										else
										{
											manager.getComponent(tempComponentId).addPort(new ProducingPort(tempPortId));
										}	
									}
								}

								if (startElement.getName().getLocalPart()
										.equals("state")) {
									event = eventReader.nextEvent();
									tempPortValue = event.asCharacters()
											.getData();
									manager.getComponent(tempComponentId)
											.getPort(tempPortId)
											.setState(
													tempPortValue
															.equals("true"));
								}
								
								if (startElement.getName().getLocalPart()
										.equals("limit")) {
									event = eventReader.nextEvent();
									tempPortLimit = event.asCharacters()
											.getData();
									((FloatLimitProducingPort) manager.getComponent(tempComponentId)
											.getPort(tempPortId))
											.setLimit(Float.valueOf(tempPortLimit));
								}

								if (startElement.getName().getLocalPart()
										.equals("connectedPorts")) {
									inConsumingPorts = true;
								}

								if (inConsumingPorts) {

									if (startElement.getName().getLocalPart()
											.equals("connectedPort")) {
										inConsumingPort = true;
									}

									if (inConsumingPort) {
										if (startElement.getName()
												.getLocalPart()
												.equals("portId")) {
											event = eventReader.nextEvent();
											tempConPortId = event
													.asCharacters().getData();
										}
										if (startElement.getName()
												.getLocalPart()
												.equals("componentId")) {
											event = eventReader.nextEvent();
											tempConComponentId = event
													.asCharacters().getData();
										}

									}
								}
							}
						}
					}
				}

				// System.out.println(startElement.getName().getLocalPart());
			}

			if (event.isEndElement()) {
				EndElement endElement = event.asEndElement();

				if (inConf
						&& endElement.getName().getLocalPart().equals("conf")) {
					inConf = false;
				}

				if (inComponent
						&& endElement.getName().getLocalPart()
								.equals("component")) {
					inComponent = false;
				}
				
				if (inGui
						&& endElement.getName().getLocalPart().equals("gui")) {
					inGui = false;
				}
				
				if (inAttributes
						&& endElement.getName().getLocalPart().equals("attribut")) {
					inAttributes = false;
				}

				if (inPorts
						&& endElement.getName().getLocalPart().equals("ports")) {
					inPorts = false;
				}

				if (inPort
						&& endElement.getName().getLocalPart().equals("port")) {
					inPort = false;
				}

				if (inConsumingPorts
						&& endElement.getName().getLocalPart()
								.equals("connectedPorts")) {
					inConsumingPorts = false;
				}

				if (inConsumingPort
						&& endElement.getName().getLocalPart()
								.equals("connectedPort")) {
					inConsumingPort = false;

					connections[connectionsCount][0] = tempComponentId;
					connections[connectionsCount][1] = tempPortId;
					connections[connectionsCount][2] = tempConComponentId;
					connections[connectionsCount][3] = tempConPortId;

					connectionsCount++;
				}

				// System.out.println(startElement.getName().getLocalPart());
			}
		}

		// Verbindungen nachtr�glich herstellen.
		for (int n = 0; n < connectionsCount; n++) {
			((ProducingPort) manager.getComponent(connections[n][0]).getPort(
					connections[n][1]))
					.registerConsumingPort((ConsumingPort) manager
							.getComponent(connections[n][2]).getPort(
									connections[n][3]));
		}

	}
}