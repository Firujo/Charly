package com.android.charly.data;

/**
 * Created by Jdandrade on 4/30/2017.
 */

public class HttpHeader {

  private final String name;
  private final String value;

  HttpHeader(String name, String value) {
    this.name = name;
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public String getName() {
    return name;
  }
}

