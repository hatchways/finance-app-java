package com.hatchways.finance.exception;

public class CredentialsException extends UnauthorizedException {

  public CredentialsException() {
    super("Could not validate credentials");
  }
}
