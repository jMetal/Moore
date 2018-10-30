package org.uma.moore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.uma.jmetal.solution.Solution;

/**
 * Message to be sent between components. It is composed of a map of pairs <key, value> */
public class Message {
	public Map<String, Object> data;

	public Message() {
		super() ;
		data = new HashMap<>() ;
	}

	public Message(Message message) {
	  data = new HashMap<>(message.getAttributes()) ;
  }

  public Object getAttribute(String attributeName) {
		return data.getOrDefault(attributeName, null);
	}

	public void setAttribute(String attributeName, Object value) {
		data.put(attributeName, value);
	}

	public Map<String, Object> getAttributes() {
	  return data ;
  }

  public void setAttributes(Map<String, Object> attributes) {
	  this.data = attributes ;
  }
}
