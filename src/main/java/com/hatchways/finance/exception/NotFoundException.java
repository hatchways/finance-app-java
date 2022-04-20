package com.hatchways.finance.exception;

public class NotFoundException extends RuntimeException {

  private String resource;

  public NotFoundException(String resource) {
    super();
    this.resource = resource;
  }

  public String getResource() {
    return resource;
  }
}
