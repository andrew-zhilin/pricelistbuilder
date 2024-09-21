package com.gmail.dev.zhilin.pricelistbuilder.templates.item.advanced;

import java.util.ArrayList;
import java.util.List;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.ss.util.RegionUtil;

import com.gmail.dev.zhilin.pricelistbuilder.enums.Truck;
import com.gmail.dev.zhilin.pricelistbuilder.enums.Warehouse;
import com.gmail.dev.zhilin.pricelistbuilder.models.Item;
import com.gmail.dev.zhilin.pricelistbuilder.stylesets.item.StyleSet;
import com.gmail.dev.zhilin.pricelistbuilder.templates.top.AdvancedTop;

public abstract class ItemTemplate {
    
    private static final String LINK_TEXT = "ссылка";

    private Sheet sheet;
    private StyleSet styleSet;
    private AdvancedTop top;

    protected ItemTemplate(Sheet sheet, StyleSet styleSet, AdvancedTop top) {
        if (sheet.getWorkbook() != styleSet.getWorkbook())
            throw new IllegalArgumentException("The sheet and the styleSet belong to different workbooks");

        this.sheet = sheet;
        this.styleSet = styleSet;
        this.top = top;
    }

    public void insert(Item item) {
        Row[] itemArea = createItemArea(item);
        insertCode(itemArea, item);
        insertManufacturer(itemArea, item);
        insertPartNumber(itemArea, item);
        insertCrossReference(itemArea, item);
        insertApplication(itemArea, item);
        insertName(itemArea, item);
        insertLink(itemArea, item);
        insertPrice(itemArea, item);
        insertYourPrice(itemArea);
        insertStock(itemArea, item);
        insertWarehouses(itemArea, item);
        insertOrderCell(itemArea);
        insertAmount(itemArea);
        mergeCells(itemArea);
    }

    protected Row[] createItemArea(Item item) {
        int columns = 13;
        Row[] itemArea = new Row[item.getStock().size()];

        for (int i = 0; i < itemArea.length; i++) {
            itemArea[i] = this.sheet.createRow(this.sheet.getLastRowNum() + 1);
            itemArea[i].setHeight((short) 450);

            for (int k = 0; k < columns; k++) {
                itemArea[i].createCell(k);
            }
        }

        return itemArea;
    }

    protected void insertCode(Row[] itemArea, Item item) {
        Cell cell;

        for (Row row : itemArea) {
            cell = row.getCell(0);

            cell.setCellValue(item.getCode().trim());
            cell.setCellStyle(styleSet.getCodeStyle());
        }
    }

    protected void insertManufacturer(Row[] itemArea, Item item) {
        Cell cell = itemArea[0].getCell(1);

        if (item.getManufacturer() != null)
            cell.setCellValue(item.getManufacturer().trim());

        cell.setCellStyle(styleSet.getManufacturerStyle());
    }

    protected void insertPartNumber(Row[] itemArea, Item item) {
        Cell cell = itemArea[0].getCell(2);

        if (item.getPartNumber() != null)
            cell.setCellValue(item.getPartNumber().trim());

        cell.setCellStyle(styleSet.getPartNumberStyle());
    }

    protected void insertCrossReference(Row[] itemArea, Item item) {
        Cell cell = itemArea[0].getCell(3);

        if (item.getCrossReference() != null)
            cell.setCellValue(item.getCrossReference().trim());

        cell.setCellStyle(styleSet.getCrossReferenceStyle());
    }

    protected void insertApplication(Row[] itemArea, Item item) {
        Cell cell = itemArea[0].getCell(4);

        if (item.getApplication() != null) {
            StringBuilder sb = new StringBuilder();

            for (Truck truck : item.getApplication()) {
                if (sb.length() != 0)
                    sb.append(" ");

                sb.append(truck.getName());
                sb.append(";");
            }

            cell.setCellValue(sb.toString());
        }

        cell.setCellStyle(styleSet.getApplicationStyle());
    }

    protected void insertName(Row[] itemArea, Item item) {
        Cell cell = itemArea[0].getCell(5);

        cell.setCellValue(item.getName().trim());
        cell.setCellStyle(styleSet.getNameStyle());
    }

    protected void insertLink(Row[] itemArea, Item item) {
        Cell cell = itemArea[0].getCell(6);

        if (item.getLink() != null) {
            CreationHelper creationHelper = cell.getSheet().getWorkbook().getCreationHelper();
            Hyperlink url = creationHelper.createHyperlink(HyperlinkType.URL);

            cell.setCellValue(LINK_TEXT);
            url.setAddress(item.getLink());
            cell.setHyperlink(url);
        }

        cell.setCellStyle(styleSet.getLinkStyle());
    }

    protected abstract void insertPrice(Row[] itemArea, Item item);

    protected void insertYourPrice(Row[] itemArea) {
        Cell discount = top.getDiscountCell();
        Cell price;
        Cell yourPrice;

        for (Row row : itemArea) {
            price = row.getCell(7);
            yourPrice = row.getCell(8);
            CellReference priceReference = new CellReference(price);
            CellReference discountReference = new CellReference(discount);
            String priceCoordinates = priceReference.getCellRefParts()[2] + priceReference.getCellRefParts()[1];
            String discountCoordinates = "$" + discountReference.getCellRefParts()[2] + "$"
                    + discountReference.getCellRefParts()[1];
            FormulaEvaluator evaluator = this.sheet.getWorkbook().getCreationHelper().createFormulaEvaluator();

            yourPrice.setCellFormula("IF(" + "ISBLANK(" + discountCoordinates + ")," + priceCoordinates + ","
                    + priceCoordinates + "-(" + priceCoordinates + "*" + discountCoordinates + "))");
            evaluator.evaluate(yourPrice);
            yourPrice.setCellStyle(styleSet.getYourPriceStyle());
        }
    }

    protected void insertStock(Row[] itemArea, Item item) {
        List<Warehouse> warehouses = new ArrayList<>(item.getStock().keySet());
        String measure = item.getMeasure() + ".";
        Cell cell;

        for (int i = 0; i < itemArea.length; i++) {
            cell = itemArea[i].getCell(9);

            cell.setCellValue(item.getStock().get(warehouses.get(i)) + " " + measure);
            cell.setCellStyle(styleSet.getBalanceStyle());
        }
    }

    protected void insertWarehouses(Row[] itemArea, Item item) {
        List<Warehouse> warehouses = new ArrayList<>(item.getStock().keySet());
        Cell cell;

        for (int i = 0; i < itemArea.length; i++) {
            cell = itemArea[i].getCell(10);

            cell.setCellValue(warehouses.get(i).getName());
            cell.setCellStyle(styleSet.getWarehouseStyle());
        }
    }

    protected void insertOrderCell(Row[] itemArea) {
        for (Row row : itemArea) {
            row.getCell(11).setCellStyle(styleSet.getOrderStyle());
        }
    }

    protected void insertAmount(Row[] itemArea) {
        Cell yourPrice;
        Cell order;
        Cell amount;

        for (Row row : itemArea) {
            order = row.getCell(11);
            yourPrice = row.getCell(8);
            amount = row.getCell(12);
            CellReference yourPriceReference = new CellReference(yourPrice);
            CellReference orderCellReference = new CellReference(order);
            String yourPriceCoordinates = yourPriceReference.getCellRefParts()[2]
                    + yourPriceReference.getCellRefParts()[1];
            String orderCellCoordinates = orderCellReference.getCellRefParts()[2]
                    + orderCellReference.getCellRefParts()[1];
            FormulaEvaluator evaluator = this.sheet.getWorkbook().getCreationHelper().createFormulaEvaluator();

            amount.setCellFormula(yourPriceCoordinates + "*" + orderCellCoordinates);
            evaluator.evaluate(amount);
            amount.setCellStyle(styleSet.getAmountStyle());
        }
    }

    protected void mergeCells(Row[] itemArea) {
        if (itemArea.length > 1) {
            int firstRow = itemArea[0].getRowNum();
            int lastRow = itemArea[itemArea.length - 1].getRowNum();
            int[] columns = { 1, 2, 3, 4, 5, 6 };

            for (int i = 0; i < columns.length; i++) {
                CellRangeAddress mergeRegion = new CellRangeAddress(firstRow, lastRow, columns[i], columns[i]);

                RegionUtil.setBorderTop(BorderStyle.THIN, mergeRegion, this.sheet);
                RegionUtil.setBorderBottom(BorderStyle.THIN, mergeRegion, this.sheet);
                RegionUtil.setBorderLeft(BorderStyle.THIN, mergeRegion, this.sheet);
                RegionUtil.setBorderRight(BorderStyle.THIN, mergeRegion, this.sheet);
                this.sheet.addMergedRegion(mergeRegion);
            }
        }
    }

}
