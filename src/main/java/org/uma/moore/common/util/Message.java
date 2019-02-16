package org.uma.moore.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Message to be sent between components. It is composed of a map of pairs <key, value> */
public class Message {
	public String type ;
	public Map<String, Object> attributes;

	public Message(String type) {
	  this.type = type ;
    attributes = new HashMap<>() ;
	}

  public Message() {
    this("") ;
  }

	public Message(Message message) {
    this.type = message.type ;
	  attributes = new HashMap<>(message.getAttributes()) ;
  }

  public Object getAttribute(String attributeName) {
		return attributes.getOrDefault(attributeName, null);
	}

	public void setAttribute(String attributeName, Object value) {
    attributes.put(attributeName, value);
	}

	public Map<String, Object> getAttributes() {
	  return attributes;
  }

  public void setAttributes(Map<String, Object> attributes) {
	  this.attributes = attributes ;
  }
}
