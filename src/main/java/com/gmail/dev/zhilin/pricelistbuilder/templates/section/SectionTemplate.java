package com.gmail.dev.zhilin.pricelistbuilder.templates.section;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import com.gmail.dev.zhilin.pricelistbuilder.stylesets.section.StyleSet;

public class SectionTemplate {

    private Sheet sheet;
    private StyleSet styleSet;

    public SectionTemplate(Sheet sheet, StyleSet styleSet) {
        if (sheet.getWorkbook() != styleSet.getWorkbook())
            throw new IllegalArgumentException("The sheet and the styleSet belong to different workbooks");

        this.sheet = sheet;
        this.styleSet = styleSet;
    }

    public void insert(String name) {
        int columns = 13;
        Row row = this.sheet.createRow(this.sheet.getLastRowNum() + 1);
        Cell cell;

        for (int i = 0; i < columns; i++) {
            cell = row.createCell(i);

            if (i == 0) {
                cell.setCellStyle(styleSet.getFirstCellStyle());
            }

            if (i == 1) {
                cell.setCellValue(name);
                cell.setCellStyle(styleSet.getSecondCellStyle());
            }

            if (i >= 2 && i <= 11) {
                cell.setCellStyle(styleSet.getMiddleCellStyle());
            }

            if (i == 12) {
                cell.setCellStyle(styleSet.getLastCellStyle());
            }
        }
    }

}
