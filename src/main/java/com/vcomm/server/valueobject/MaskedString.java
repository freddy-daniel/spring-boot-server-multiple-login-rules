package com.vcomm.server.valueobject;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.io.Serializable;


@RequiredArgsConstructor
@Value
public class MaskedString implements Serializable{

  @NonNull String value;

  @Override
  public String toString() {
    return "**PASSWORD**";
  }
}
