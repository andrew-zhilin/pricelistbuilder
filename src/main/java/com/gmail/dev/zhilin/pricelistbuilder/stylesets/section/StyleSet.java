package com.gmail.dev.zhilin.pricelistbuilder.stylesets.section;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

public abstract class StyleSet {

    private Workbook workbook;
    private CellStyle firstCellStyle;
    private CellStyle secondCellStyle;
    private CellStyle middleCellStyle;
    private CellStyle lastCellStyle;

    public StyleSet(Workbook workbook) {
        this.workbook = workbook;
        this.firstCellStyle = initializeFirstCellStyle(workbook);
        this.secondCellStyle = initializeSecondCellStyle(workbook);
        this.middleCellStyle = initializeMiddleCellStyle(workbook);
        this.lastCellStyle = initializeLastCellStyle(workbook);
    }

    public Workbook getWorkbook() {
        return workbook;
    }

    public CellStyle getFirstCellStyle() {
        return firstCellStyle;
    }

    public CellStyle getSecondCellStyle() {
        return secondCellStyle;
    }

    public CellStyle getMiddleCellStyle() {
        return middleCellStyle;
    }

    public CellStyle getLastCellStyle() {
        return lastCellStyle;
    }

    protected abstract CellStyle initializeFirstCellStyle(Workbook workbook);

    protected abstract CellStyle initializeSecondCellStyle(Workbook workbook);

    protected abstract CellStyle initializeMiddleCellStyle(Workbook workbook);

    protected abstract CellStyle initializeLastCellStyle(Workbook workbook);

}
