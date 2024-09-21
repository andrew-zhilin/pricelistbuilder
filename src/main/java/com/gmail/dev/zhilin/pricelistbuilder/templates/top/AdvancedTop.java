package com.gmail.dev.zhilin.pricelistbuilder.templates.top;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.openxml4j.opc.PackageRelationship;
import org.apache.poi.openxml4j.opc.PackageRelationshipTypes;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Shape;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.openxmlformats.schemas.drawingml.x2006.main.CTHyperlink;
import org.openxmlformats.schemas.drawingml.x2006.main.CTNonVisualDrawingProps;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTPicture;
import org.openxmlformats.schemas.drawingml.x2006.spreadsheetDrawing.CTPictureNonVisual;
import org.springframework.core.io.ClassPathResource;
import com.gmail.dev.zhilin.pricelistbuilder.enums.PriceType;
import com.gmail.dev.zhilin.pricelistbuilder.stylesets.top.StyleSet;

public class AdvancedTop {

    private static final Map<String, Integer> HEADERS = new LinkedHashMap<>();
    private static final String LINK = "https://ссылка_на_сайт_компани";
    private static final String DISCOUNT_MESSAGE = "Если у вас есть скидка, внесите ее в поле \"Ваша скидка\" и цены пересчитаются.";
    private static final String PRICE_TYPE_HEADER = "Тип цен:";
    private static final String DISCOUNT_HEADER = "Ваша скидка:";
    private static final String ORDER_PRICE_HEADER = "Сумма заказа:";

    static {
        HEADERS.put("Код", 0);
        HEADERS.put("Бренд", 3650);
        HEADERS.put("Артикул", 3650);
        HEADERS.put("Кросс-номера", 7300);
        HEADERS.put("Применимость", 5120);
        HEADERS.put("Наименование", 14280);
        HEADERS.put("Фото", 2200);
        HEADERS.put("Цена", 0);
        HEADERS.put("Ваша цена", 2550);
        HEADERS.put("Остаток", 2550);
        HEADERS.put("Склад", 4030);
        HEADERS.put("Заказ", 2550);
        HEADERS.put("Сумма", 2550);
        HEADERS.put("Новое поступление!", 5120);
    }

    private StyleSet styleSet;
    private Cell discountCell;
    private Cell orderAmountCell;
    private Row headerRow;

    public AdvancedTop(Sheet sheet, PriceType priceType) {
        int columns = 13;
        int rows = 5;
        Row[] topArea = createTopArea(sheet, rows, columns);
        this.discountCell = topArea[2].getCell(12);
        this.orderAmountCell = topArea[3].getCell(12);
        this.headerRow = topArea[4];

        initializeStyleSet(sheet.getWorkbook());
        insertBanner(topArea[0].getCell(1));
        insertDiscountMessage(topArea[0].getCell(8));
        insertPriceTypeHeader(topArea[1].getCell(10));
        insertDiscountHeader(topArea[2].getCell(10));
        insertOrderAmountHeader(topArea[3].getCell(10));
        insertPriceTypeCell(topArea[1].getCell(12), priceType);
        insertDiscountCell(this.discountCell);
        insertOrderAmountCell(this.orderAmountCell);
        insertHeaders(this.headerRow);
        freezeTop(topArea);
    }

    public AdvancedTop(Sheet sheet, PriceType priceType, String message) {
        int columns = 13;
        int rows = 6;
        Row[] topArea = createTopArea(sheet, rows, columns);
        this.discountCell = topArea[2].getCell(12);
        this.orderAmountCell = topArea[3].getCell(12);
        this.headerRow = topArea[5];

        initializeStyleSet(sheet.getWorkbook());
        insertBanner(topArea[0].getCell(1));
        insertDiscountMessage(topArea[0].getCell(8));
        insertPriceTypeHeader(topArea[1].getCell(10));
        insertDiscountHeader(topArea[2].getCell(10));
        insertOrderAmountHeader(topArea[3].getCell(10));
        insertPriceTypeCell(topArea[1].getCell(12), priceType);
        insertDiscountCell(this.discountCell);
        insertOrderAmountCell(this.orderAmountCell);
        insertMessage(topArea[4].getCell(1), message);
        insertHeaders(this.headerRow);
        freezeTop(topArea);
    }

    public Cell getDiscountCell() {
        return discountCell;
    }

    private void initializeStyleSet(Workbook workbook) {
        this.styleSet = new StyleSet(workbook);
    }

    private Row[] createTopArea(Sheet sheet, int rows, int columns) {
        Row[] topArea = new Row[rows];

        for (int i = 0; i < rows; i++) {
            topArea[i] = sheet.createRow(sheet.getLastRowNum() + 1);

            for (int k = 0; k < columns; k++) {
                topArea[i].createCell(k);
            }
        }

        return topArea;
    }

    private void insertBanner(Cell cell) {
        Workbook workbook = cell.getSheet().getWorkbook();
        Sheet sheet = cell.getSheet();

        try {
            File banner = new ClassPathResource("static/banner.png").getFile();
            InputStream inputStream = new FileInputStream(banner);

            byte[] imageBytes = IOUtils.toByteArray(inputStream);
            int pictureIdx = sheet.getWorkbook().addPicture(imageBytes, workbook.PICTURE_TYPE_PNG);

            inputStream.close();

            CreationHelper helper = sheet.getWorkbook().getCreationHelper();
            Drawing<Shape> drawing = (Drawing<Shape>) sheet.createDrawingPatriarch();
            ClientAnchor anchor = helper.createClientAnchor();

            anchor.setCol1(cell.getColumnIndex());
            anchor.setRow1(cell.getRowIndex());
            anchor.setCol2(cell.getColumnIndex() + 6);
            anchor.setRow2(cell.getRowIndex() + 4);

            XSSFPicture xssfPicture = (XSSFPicture) drawing.createPicture(anchor, pictureIdx);
            XSSFDrawing xssfDrawing = xssfPicture.getSheet().createDrawingPatriarch();
            PackageRelationship packageRelationship = xssfDrawing.getPackagePart().addExternalRelationship(LINK,
                    PackageRelationshipTypes.HYPERLINK_PART);
            String rId = packageRelationship.getId();

            CTPicture ctPicture = xssfPicture.getCTPicture();
            CTPictureNonVisual ctPictureNonVisual = ctPicture.getNvPicPr();

            if (ctPictureNonVisual == null)
                ctPictureNonVisual = ctPicture.addNewNvPicPr();
            CTNonVisualDrawingProps ctnonvisualdrawingprops = ctPictureNonVisual.getCNvPr();

            if (ctnonvisualdrawingprops == null)
                ctnonvisualdrawingprops = ctPictureNonVisual.addNewCNvPr();
            CTHyperlink cthyperlink = ctnonvisualdrawingprops.getHlinkClick();

            if (cthyperlink == null)
                cthyperlink = ctnonvisualdrawingprops.addNewHlinkClick();
            cthyperlink.setId(rId);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void insertDiscountMessage(Cell cell) {
        cell.setCellStyle(styleSet.getDiscountMessageStyle());
        cell.setCellValue(DISCOUNT_MESSAGE);

        CellRangeAddress mergeRegion = new CellRangeAddress(cell.getRowIndex(), cell.getRowIndex() + 3,
                cell.getColumnIndex(), cell.getColumnIndex() + 1);

        cell.getSheet().addMergedRegion(mergeRegion);
    }

    private void insertPriceTypeHeader(Cell cell) {
        cell.setCellStyle(styleSet.getPriceTypeHeaderStyle());
        cell.setCellValue(PRICE_TYPE_HEADER);

        CellRangeAddress mergeRegion = new CellRangeAddress(cell.getRowIndex(), cell.getRowIndex(),
                cell.getColumnIndex(), cell.getColumnIndex() + 1);

        RegionUtil.setBorderTop(BorderStyle.THIN, mergeRegion, cell.getSheet());
        RegionUtil.setBorderBottom(BorderStyle.THIN, mergeRegion, cell.getSheet());
        RegionUtil.setBorderLeft(BorderStyle.THIN, mergeRegion, cell.getSheet());
        RegionUtil.setBorderRight(BorderStyle.THIN, mergeRegion, cell.getSheet());

        cell.getSheet().addMergedRegion(mergeRegion);
    }

    private void insertPriceTypeCell(Cell cell, PriceType priceType) {
        cell.setCellStyle(styleSet.getPriceTypeStyle());
        cell.setCellValue(priceType.getName());
    }

    private void insertDiscountHeader(Cell cell) {
        cell.setCellStyle(styleSet.getDiscountHeaderStyle());
        cell.setCellValue(DISCOUNT_HEADER);

        CellRangeAddress mergeRegion = new CellRangeAddress(cell.getRowIndex(), cell.getRowIndex(),
                cell.getColumnIndex(), cell.getColumnIndex() + 1);

        RegionUtil.setBorderTop(BorderStyle.THIN, mergeRegion, cell.getSheet());
        RegionUtil.setBorderBottom(BorderStyle.THIN, mergeRegion, cell.getSheet());
        RegionUtil.setBorderLeft(BorderStyle.THIN, mergeRegion, cell.getSheet());
        RegionUtil.setBorderRight(BorderStyle.THIN, mergeRegion, cell.getSheet());

        cell.getSheet().addMergedRegion(mergeRegion);
    }

    private void insertDiscountCell(Cell cell) {
        this.discountCell = cell;
        cell.setCellStyle(styleSet.getDiscountStyle());
        cell.setCellValue(0);
    }

    private void insertOrderAmountHeader(Cell cell) {
        cell.setCellStyle(styleSet.getOrderAmountHeaderStyle());
        cell.setCellValue(ORDER_PRICE_HEADER);

        CellRangeAddress mergeRegion = new CellRangeAddress(cell.getRowIndex(), cell.getRowIndex(),
                cell.getColumnIndex(), cell.getColumnIndex() + 1);

        RegionUtil.setBorderTop(BorderStyle.THIN, mergeRegion, cell.getSheet());
        RegionUtil.setBorderBottom(BorderStyle.THIN, mergeRegion, cell.getSheet());
        RegionUtil.setBorderLeft(BorderStyle.THIN, mergeRegion, cell.getSheet());
        RegionUtil.setBorderRight(BorderStyle.THIN, mergeRegion, cell.getSheet());

        cell.getSheet().addMergedRegion(mergeRegion);
    }

    private void insertOrderAmountCell(Cell cell) {
        cell.setCellStyle(styleSet.getOrderAmountStyle());
    }

    private void insertMessage(Cell cell, String message) {
        cell.setCellStyle(styleSet.getMessageStyle());
        cell.setCellValue(message);

        CellRangeAddress mergeRegion = new CellRangeAddress(cell.getRowIndex(), cell.getRowIndex(),
                cell.getColumnIndex(), cell.getColumnIndex() + 11);

        RegionUtil.setBorderTop(BorderStyle.THIN, mergeRegion, cell.getSheet());
        RegionUtil.setBorderBottom(BorderStyle.THIN, mergeRegion, cell.getSheet());
        RegionUtil.setBorderLeft(BorderStyle.THIN, mergeRegion, cell.getSheet());
        RegionUtil.setBorderRight(BorderStyle.THIN, mergeRegion, cell.getSheet());

        cell.getSheet().addMergedRegion(mergeRegion);
    }

    private void insertHeaders(Row row) {
        List<String> headers = new ArrayList<>(HEADERS.keySet());
        List<Integer> width = new ArrayList<>(HEADERS.values());
        Cell headerCell;

        for (int i = 0; i < 13; i++) {
            headerCell = row.getCell(i);

            headerCell.setCellValue(headers.get(i));
            headerCell.setCellStyle(styleSet.getHeaderStyle());
            row.getSheet().setColumnWidth(headerCell.getColumnIndex(), width.get(i));
        }

        row.setHeight((short) 450);
    }

    public void addArrivalColumn() {
        Cell cell = this.headerRow.createCell(this.headerRow.getLastCellNum());
        String columnKey = (String) HEADERS.keySet().toArray()[13];

        cell.setCellValue(columnKey);
        cell.setCellStyle(styleSet.getArrivalHeaderStyle());
        cell.getSheet().setColumnWidth(cell.getColumnIndex(), HEADERS.get(columnKey));
    }

    private void freezeTop(Row[] topArea) {
        Row firstRow = topArea[0];
        Row lastRow = topArea[topArea.length - 1];

        firstRow.getSheet().createFreezePane(firstRow.getRowNum(), (lastRow.getRowNum() + 1));
    }

    public void activateOrderAmountFormula() {
        Sheet sheet = headerRow.getSheet();
        Cell firstCell = sheet.getRow(headerRow.getRowNum() + 1).getCell(12);
        Cell lastCell = sheet.getRow(sheet.getLastRowNum()).getCell(12);

        CellReference firstCellReference = new CellReference(firstCell);
        CellReference lastCellReference = new CellReference(lastCell);
        String firstCellCoordinates = firstCellReference.getCellRefParts()[2] + firstCellReference.getCellRefParts()[1];
        String lastCellCoordinates = lastCellReference.getCellRefParts()[2] + lastCellReference.getCellRefParts()[1];
        FormulaEvaluator evaulator = sheet.getWorkbook().getCreationHelper().createFormulaEvaluator();
        String formula = new String("SUM(" + firstCellCoordinates + ":" + lastCellCoordinates + ")");

        orderAmountCell.setCellFormula(formula.toString());
        evaulator.evaluate(orderAmountCell);
    }

    public void activateFilter() {
        Cell firstCell = headerRow.getCell(headerRow.getFirstCellNum());
        Cell lastCell = headerRow.getCell(headerRow.getLastCellNum() - 1);

        headerRow.getSheet().setAutoFilter(new CellRangeAddress(firstCell.getRowIndex(), lastCell.getRowIndex(),
                firstCell.getColumnIndex(), lastCell.getColumnIndex()));
    }

}
