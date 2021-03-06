package Core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Basic_Test.*;
import Core.ComplexComponents.*;
import IO.*;

//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Manager.java
//  @ Date : 17.05.2014
//  @ Author : 
//
//




public class Manager {
	private static Manager instance;
	private List<Component> components;
	private PersistenceHandler myPersistenceHandler;
	
	public Manager() throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		super();
		components = new ArrayList<Component>();
		
		this.myPersistenceHandler = new XMLPersistenceHandler();
		
		this.makeComponent("Core.SecurityComponent", "SecurityComponent");
	}
	
	public PersistenceHandler getMyPersistenceHandler() {
		return myPersistenceHandler;
	}

	public void setMyPersistenceHandler(PersistenceHandler myPersistenceHandler) {
		this.myPersistenceHandler = myPersistenceHandler;
	}
	
	public boolean hasComponent(String compId)
	{
		Iterator<Component> ite = components.iterator();
		
		while(ite.hasNext())
		{
			if(ite.next().getId().equals(compId)) return true;
		}
		
		return false;
	}
	
	public boolean reset()
	{
		Iterator<Component> ite = components.iterator();
		
		while(ite.hasNext())
		{
			Component c = ite.next();
			c.getMyGUI().dispose();
			c.finalies();
			components.remove(c);
			
			
		}
		
		return false;
	}
	
	public void addComponent(Component c)
	{
		components.add(c);
	}
	
	public void removeComponent(Component c)
	{
		Iterator<Port> pite = c.getPorts().iterator();
		while(pite.hasNext())
		{
			Port p = pite.next();
			
			boolean isConsumingPort = false;
			Class cl = p.getClass();
			
			while(!cl.getName().equals("java.lang.Object") && !isConsumingPort)
			{
				if(cl.getName().equals("Core.ConsumingPort")) isConsumingPort = true;
				cl = cl.getSuperclass();
			}
			
			if(isConsumingPort)
			{
				Iterator<Component> cite = components.iterator();
				while(cite.hasNext())
				{
					Iterator<Port> pite2 = cite.next().getPorts().iterator();
					while(pite2.hasNext())
					{
						Port pp = pite2.next();
						
						boolean isProducingPort = false;
						Class cl2 = pp.getClass();
						
						while(!cl2.getName().equals("java.lang.Object") && !isProducingPort)
						{
							if(cl2.getName().equals("Core.ProducingPort")) isProducingPort = true;
							cl2 = cl2.getSuperclass();
						}
						
						if(isProducingPort)
						{
							((ProducingPort)pp).unregisterConsumingPort((ConsumingPort) p);
						}
					}
				}
			}
		}
		c.finalies();
		components.remove(c);
	}
	
	public Component getComponent(String id)
	{
		for(Component c: components)
		{
			if(c.getId().equals(id))
			{
				return c;
			}
		}
		return null;
	}

	public Component makeComponent(String ComponentClass, String id) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		
		int n = 1;
		String tmpId = "";
		
		tmpId = id;
		
		while(hasComponent(tmpId))
		{
			n++;
			tmpId = id+"_"+n;
		}
		
		
		Component c = createComponent(ComponentClass, tmpId);
		
		if(c != null)
		{
			addComponent(c);
		}
		
		return c;
	}
		
	
	public Component createComponent(String ComponentClass, String id) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		/*if(ComponentClass.equals("Core.Component")) return new Component(id); 
		if(ComponentClass.equals("Core.ActorComponent")) return new ActorComponent(id);
		if(ComponentClass.equals("Core.SensorComponent")) return new SensorComponent(id);
		if(ComponentClass.equals("Core.SecurityComponent")) return new SecurityComponent(id);
		if(ComponentClass.equals("Basic_Test.TestSensorComponent")) return new TestSensorComponent(id);
		if(ComponentClass.equals("Basic_Test.TestActorComponent")) return new TestActorComponent(id);
		if(ComponentClass.equals("Core.DynamicGeneratedWebServiceComponent")) return new Core.DynamicGeneratedWebServiceComponent(id);*/
		
		Component c = (Component)Class.forName(ComponentClass).newInstance();
		c.setId(id);
		
		return c;
	}
	
	static public Manager getInstance() {
		if(instance==null)
		{
			try {
				instance = new Manager();
			} catch (InstantiationException | IllegalAccessException
					| ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return instance;
	}
	
	public void SecurityStop() {
		for(Component c : components)
		{
			c.SecurityStop();
		}
	}
	
	public void SecurityRestart() {
		for(Component c : components)
		{
			c.SecurityRestart();
		}
	}
	
	public void saveConfiguration(String confFile) throws Exception {
		this.myPersistenceHandler.saveConfiguration(confFile, this);
	}
	
	public void loadConfiguration(String confFile) throws Exception {
		this.myPersistenceHandler.loadConfiguration(confFile, this);
	}
	
	public void connectPorts(String sourceComponentId, String sourcePortId, String sinkComponentId, String sinkPortId) {
		ProducingPort pp = (ProducingPort)this.getComponent(sourceComponentId).getPort(sourcePortId);
		ConsumingPort cp = (ConsumingPort)this.getComponent(sinkComponentId).getPort(sinkPortId);
		
		pp.registerConsumingPort(cp);
	}

	public List<Component> getComponents() {
		return components;
	}

	public void setComponents(List<Component> components) {
		this.components = components;
	}
}
