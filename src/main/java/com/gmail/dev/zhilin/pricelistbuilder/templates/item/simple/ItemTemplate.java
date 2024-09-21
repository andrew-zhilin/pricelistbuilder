package com.gmail.dev.zhilin.pricelistbuilder.templates.item.simple;

import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.gmail.dev.zhilin.pricelistbuilder.enums.Truck;
import com.gmail.dev.zhilin.pricelistbuilder.enums.Warehouse;
import com.gmail.dev.zhilin.pricelistbuilder.models.Item;
import com.gmail.dev.zhilin.pricelistbuilder.stylesets.item.StyleSet;

public abstract class ItemTemplate {
    
    private static final String LINK_TEXT = "ссылка";
    private StyleSet styleSet;

    protected ItemTemplate(StyleSet styleSet) {
        this.styleSet = styleSet;
    }

    public void insert(Sheet sheet, Item item, Warehouse warehouse) {
        Row row = createItemRow(sheet);

        insertCode(row, item);
        insertManufacturer(row, item);
        insertPartNumber(row, item);
        insertCrossReference(row, item);
        insertApplication(row, item);
        insertName(row, item);
        insertLink(row, item);
        insertPrice(row, item, warehouse);
        insertStock(row, item, warehouse);
        insertWarehouse(row, item, warehouse);
        insertOrderCell(row);
    }

    protected Row createItemRow(Sheet sheet) {
        Row row = sheet.createRow(sheet.getLastRowNum() + 1);
        int columns = 11;

        for (int i = 0; i < columns; i++)
            row.createCell(i);

        row.setHeight((short) 450);

        return row;
    }

    protected void insertCode(Row row, Item item) {
        Cell cell = row.getCell(0);

        cell.setCellStyle(styleSet.getCodeStyle());
        cell.setCellValue(item.getCode().trim());
    }

    protected void insertManufacturer(Row row, Item item) {
        Cell cell = row.getCell(1);

        cell.setCellStyle(styleSet.getManufacturerStyle());

        if (item.getManufacturer() != null)
            cell.setCellValue(item.getManufacturer().trim());
    }

    protected void insertPartNumber(Row row, Item item) {
        Cell cell = row.getCell(2);

        cell.setCellStyle(styleSet.getPartNumberStyle());

        if (item.getPartNumber() != null)
            cell.setCellValue(item.getPartNumber().trim());
    }

    protected void insertCrossReference(Row row, Item item) {
        Cell cell = row.getCell(3);

        cell.setCellStyle(styleSet.getCrossReferenceStyle());

        if (item.getCrossReference() != null)
            cell.setCellValue(item.getCrossReference().trim());
    }

    protected void insertApplication(Row row, Item item) {
        Cell cell = row.getCell(4);

        cell.setCellStyle(styleSet.getApplicationStyle());

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
    }

    protected void insertName(Row row, Item item) {
        Cell cell = row.getCell(5);

        cell.setCellStyle(styleSet.getNameStyle());
        cell.setCellValue(item.getName());
    }

    protected void insertLink(Row row, Item item) {
        Cell cell = row.getCell(6);

        cell.setCellStyle(styleSet.getLinkStyle());

        if (item.getLink() != null) {
            CreationHelper creationHelper = cell.getSheet().getWorkbook().getCreationHelper();
            Hyperlink url = creationHelper.createHyperlink(HyperlinkType.URL);

            cell.setCellValue(LINK_TEXT);
            url.setAddress(item.getLink());
            cell.setHyperlink(url);
        }
    }

    protected abstract void insertPrice(Row row, Item item, Warehouse warehouse);

    protected void insertStock(Row row, Item item, Warehouse warehouse) {
        Cell cell = row.getCell(8);
        String measure = item.getMeasure() + ".";

        cell.setCellValue(item.getStock().get(warehouse) + " " + measure);
        cell.setCellStyle(styleSet.getBalanceStyle());
    }

    protected void insertWarehouse(Row row, Item item, Warehouse warehouse) {
        Cell cell = row.getCell(9);

        cell.setCellValue(warehouse.getName());
        cell.setCellStyle(styleSet.getWarehouseStyle());
    }

    protected void insertOrderCell(Row row) {
        row.getCell(10).setCellStyle(styleSet.getOrderStyle());
    }

}
