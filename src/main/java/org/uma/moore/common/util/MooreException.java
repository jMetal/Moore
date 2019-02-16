package org.uma.moore.common.util;

import java.io.Serializable;
import java.util.logging.Level;

/**
 * jMetal exception class
 *
 * @author Antonio J. Nebro <antonio@lcc.uma.es>
 */
@SuppressWarnings("serial")
public class MooreException extends RuntimeException implements Serializable {
  public MooreException(String message) {
    super(message);
  }
  public MooreException(Exception e) {
    MooreLogger.logger.log(Level.SEVERE, "Error", e);
  }
  public MooreException(String message, Exception e) {
    MooreLogger.logger.log(Level.SEVERE, message, e);
  }

}
