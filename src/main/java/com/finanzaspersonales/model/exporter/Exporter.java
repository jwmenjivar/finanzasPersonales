package com.finanzaspersonales.model.exporter;

import java.io.IOException;

interface Exporter {
  void exportYear(String filename, int year) throws IOException;
}
