package org.rasea.core.entity;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {
	
	public Application createApplication() {
		return new Application();
	}
	
	public Data createData() {
		return new Data();
	}
}
