package com.gmail.dev.zhilin.pricelistbuilder.stylesets.top;

import java.awt.Color;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFColor;

public class StyleSet {

    private CellStyle discountMessageStyle;
    private CellStyle priceTypeHeaderStyle;
    private CellStyle priceTypeStyle;
    private CellStyle discountHeaderStyle;
    private CellStyle discountStyle;
    private CellStyle orderAmountHeaderStyle;
    private CellStyle orderAmountStyle;
    private CellStyle messageStyle;
    private CellStyle headerStyle;
    private CellStyle arrivalHeaderStyle;

    public StyleSet(Workbook workbook) {
        discountMessageStyle = initializeDiscountMessageStyle(workbook);
        priceTypeHeaderStyle = initializePriceTypeHeaderStyle(workbook);
        priceTypeStyle = initializePriceTypeStyle(workbook);
        discountHeaderStyle = initializeDiscountHeaderStyle(workbook);
        discountStyle = initializeDiscountStyle(workbook);
        orderAmountHeaderStyle = initializeOrderAmountHeaderStyle(workbook);
        orderAmountStyle = initializeOrderAmountStyle(workbook);
        messageStyle = initializeMessageStyle(workbook);
        headerStyle = initializeHeaderStyle(workbook);
        arrivalHeaderStyle = initializeArrivalHeaderStyle(workbook);
    }

    public CellStyle getDiscountMessageStyle() {
        return discountMessageStyle;
    }

    public CellStyle getPriceTypeHeaderStyle() {
        return priceTypeHeaderStyle;
    }

    public CellStyle getPriceTypeStyle() {
        return priceTypeStyle;
    }

    public CellStyle getDiscountHeaderStyle() {
        return discountHeaderStyle;
    }

    public CellStyle getDiscountStyle() {
        return discountStyle;
    }

    public CellStyle getOrderAmountHeaderStyle() {
        return orderAmountHeaderStyle;
    }

    public CellStyle getOrderAmountStyle() {
        return orderAmountStyle;
    }

    public CellStyle getMessageStyle() {
        return messageStyle;
    }

    public CellStyle getHeaderStyle() {
        return headerStyle;
    }

    public CellStyle getArrivalHeaderStyle() {
        return arrivalHeaderStyle;
    }

    private CellStyle initializeDiscountMessageStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();

        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 8);
        font.setColor(HSSFColor.HSSFColorPredefined.RED.getIndex());
        font.setBold(true);
        style.setWrapText(true);
        style.setFont(font);
        style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.YELLOW.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        return style;
    }

    private CellStyle initializePriceTypeHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();

        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 9);
        font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        font.setBold(true);
        style.setFont(font);
        style.setWrapText(false);
        style.setFillForegroundColor(new XSSFColor(new Color(183, 222, 232), null));
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        return style;
    }

    private CellStyle initializePriceTypeStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();

        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 9);
        font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        font.setBold(true);
        style.setFont(font);
        style.setWrapText(false);
        style.setFillForegroundColor(new XSSFColor(new Color(183, 222, 232), null));
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        return style;
    }

    private CellStyle initializeDiscountHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();

        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 9);
        font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        font.setBold(true);
        style.setFont(font);
        style.setWrapText(false);
        style.setFillForegroundColor(new XSSFColor(new Color(255, 192, 0), null));
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        return style;
    }

    private CellStyle initializeDiscountStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();

        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 9);
        font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        font.setBold(true);
        style.setFont(font);
        style.setWrapText(false);
        style.setDataFormat(workbook.createDataFormat().getFormat("0%"));
        style.setFillForegroundColor(new XSSFColor(new Color(255, 192, 0), null));
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        return style;
    }

    private CellStyle initializeOrderAmountHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();

        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 9);
        font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        font.setBold(true);
        style.setFont(font);
        style.setWrapText(false);
        style.setFillForegroundColor(new XSSFColor(new Color(196, 215, 155), null));
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        return style;
    }

    private CellStyle initializeOrderAmountStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();

        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 9);
        font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        font.setBold(true);
        style.setFont(font);
        style.setWrapText(false);
        style.setDataFormat(workbook.createDataFormat().getFormat("# ##0 ₽"));
        style.setFillForegroundColor(new XSSFColor(new Color(196, 215, 155), null));
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.RIGHT);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        return style;
    }

    private CellStyle initializeMessageStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();

        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 8);
        font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
        font.setBold(true);
        style.setFont(font);
        style.setWrapText(true);
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

    private CellStyle initializeHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();

        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 8);
        font.setBold(true);
        style.setFont(font);
        style.setWrapText(false);
        style.setFillForegroundColor(new XSSFColor(new Color(183, 222, 232), null));
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        return style;
    }

    private CellStyle initializeArrivalHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();

        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 8);
        font.setColor(HSSFColorPredefined.DARK_RED.getIndex());
        font.setBold(true);
        style.setFont(font);
        style.setWrapText(false);
        style.setFillForegroundColor(new XSSFColor(new Color(255, 230, 153), null));
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        return style;
    }

}
