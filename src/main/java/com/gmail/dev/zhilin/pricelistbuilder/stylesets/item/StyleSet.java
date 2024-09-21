package com.gmail.dev.zhilin.pricelistbuilder.stylesets.item;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

public abstract class StyleSet {

    private Workbook workbook;
    private CellStyle codeStyle;
    private CellStyle manufacturerStyle;
    private CellStyle partNumberStyle;
    private CellStyle crossReferenceStyle;
    private CellStyle applicationStyle;
    private CellStyle nameStyle;
    private CellStyle linkStyle;
    private CellStyle priceStyle;
    private CellStyle yourPriceStyle;
    private CellStyle stockStyle;
    private CellStyle warehouseStyle;
    private CellStyle orderStyle;
    private CellStyle amountStyle;
    private CellStyle arrivalMessageStyle;

    public StyleSet(Workbook workbook) {
        this.workbook = workbook;
        this.codeStyle = initializeCodeStyle(workbook);
        this.manufacturerStyle = initializeManufacturerStyle(workbook);
        this.partNumberStyle = initializePartNumberStyle(workbook);
        this.crossReferenceStyle = initializeCrossReferenceStyle(workbook);
        this.applicationStyle = initializeApplicationStyle(workbook);
        this.nameStyle = initializeNameStyle(workbook);
        this.linkStyle = initializeLinkStyle(workbook);
        this.priceStyle = initializePriceStyle(workbook);
        this.yourPriceStyle = initializeYourPriceStyle(workbook);
        this.stockStyle = initializeStockStyle(workbook);
        this.warehouseStyle = initializeWarehouseStyle(workbook);
        this.orderStyle = initializeOrderStyle(workbook);
        this.amountStyle = initializeAmountStyle(workbook);
        this.arrivalMessageStyle = initializeArrivalMessageStyle(workbook);
    }

    public Workbook getWorkbook() {
        return workbook;
    }

    public CellStyle getCodeStyle() {
        return codeStyle;
    }

    public CellStyle getManufacturerStyle() {
        return manufacturerStyle;
    }

    public CellStyle getPartNumberStyle() {
        return partNumberStyle;
    }

    public CellStyle getCrossReferenceStyle() {
        return crossReferenceStyle;
    }

    public CellStyle getApplicationStyle() {
        return applicationStyle;
    }

    public CellStyle getNameStyle() {
        return nameStyle;
    }

    public CellStyle getLinkStyle() {
        return linkStyle;
    }

    public CellStyle getPriceStyle() {
        return priceStyle;
    }

    public CellStyle getYourPriceStyle() {
        return yourPriceStyle;
    }

    public CellStyle getBalanceStyle() {
        return stockStyle;
    }

    public CellStyle getWarehouseStyle() {
        return warehouseStyle;
    }

    public CellStyle getOrderStyle() {
        return orderStyle;
    }

    public CellStyle getAmountStyle() {
        return amountStyle;
    }

    public CellStyle getArrivalMessageStyle() {
        if (arrivalMessageStyle == null) {
            throw new UnsupportedOperationException("This style set doesn't support this style");
        }

        return arrivalMessageStyle;
    }

    protected abstract CellStyle initializeCodeStyle(Workbook workbook);

    protected abstract CellStyle initializeManufacturerStyle(Workbook workbook);

    protected abstract CellStyle initializePartNumberStyle(Workbook workbook);

    protected abstract CellStyle initializeCrossReferenceStyle(Workbook workbook);

    protected abstract CellStyle initializeApplicationStyle(Workbook workbook);

    protected abstract CellStyle initializeNameStyle(Workbook workbook);

    protected abstract CellStyle initializeLinkStyle(Workbook workbook);

    protected abstract CellStyle initializePriceStyle(Workbook workbook);

    protected abstract CellStyle initializeYourPriceStyle(Workbook workbook);

    protected abstract CellStyle initializeStockStyle(Workbook workbook);

    protected abstract CellStyle initializeWarehouseStyle(Workbook workbook);

    protected CellStyle initializeOrderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();

        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 8);
        font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        font.setBold(true);
        style.setFont(font);
        style.setWrapText(false);
        style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.YELLOW.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        return style;
    }

    protected abstract CellStyle initializeAmountStyle(Workbook workbook);

    protected CellStyle initializeArrivalMessageStyle(Workbook workbook) {
        return null;
    }

}
