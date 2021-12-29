package com.finanzaspersonales.model;

import lombok.Getter;

@Getter
abstract class Validator {
  protected boolean isValid = false;
  protected String messages = "";
}
