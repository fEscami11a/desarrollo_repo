package mx.com.invex.msi.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "book")
@XmlAccessorType(XmlAccessType.NONE)
public class Book {
	@XmlElement(name = "name")
	private String name;
	@XmlElement(name = "availablity")
	private boolean availablity;
	public String getName() {
	    return name;
	}

	public void setName(String name) {
	    this.name = name;
	}

	public boolean isAvailablity() {
	    return availablity;
	}

	public void setAvailablity(boolean availablity) {
	    this.availablity = availablity;
	}

	
}
