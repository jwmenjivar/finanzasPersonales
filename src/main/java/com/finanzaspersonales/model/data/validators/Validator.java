package com.finanzaspersonales.model.data.validators;

import lombok.Getter;

@Getter
public abstract class Validator {
  protected boolean isValid = false;
  protected String messages = "";
}