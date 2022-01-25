package com.finanzaspersonales.model;

import com.finanzaspersonales.model.exporter.ExportExcel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;


public class ExportExcelTest {

    @Test
    @DisplayName("Check - Creating file")
    public void exportYearFilenameTest() {
        ExportExcel exportExcel = new ExportExcel();
        Assertions.assertDoesNotThrow(()-> {
            exportExcel.exportYear("testName",2020);
        });
    }

    @Test
    @DisplayName("Check - Null or filename has no name")
    public void exportYearNullNameTest() {
        ExportExcel exportExcel = new ExportExcel();
        Assertions.assertDoesNotThrow(()-> {
            String filename = LocalDate.now() + ".xls";
            exportExcel.exportYear(filename,2020);
        });
    }
}
