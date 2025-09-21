// exception/BusinessException.java
package com.microshop.order.exception;
public class BusinessException extends RuntimeException {
  public BusinessException(String msg){ super(msg); }
}
