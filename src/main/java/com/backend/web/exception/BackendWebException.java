package com.backend.web.exception;

public class BackendWebException extends Exception {
  private static final long serialVersionUID = 1L;

  public BackendWebException() {
    super();
  }

  public BackendWebException(String message) {
    super(message);
  }

  public BackendWebException(Throwable cause) {
    super(cause);
  }

  public BackendWebException(String message, Throwable cause) {
    super(message, cause);
  }
}
