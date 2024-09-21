package com.gmail.dev.zhilin.pricelistbuilder.templates.item.advanced.arrival;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;

import com.gmail.dev.zhilin.pricelistbuilder.models.Item;
import com.gmail.dev.zhilin.pricelistbuilder.stylesets.item.ArrivalStyleSet;
import com.gmail.dev.zhilin.pricelistbuilder.templates.item.advanced.ItemTemplate;
import com.gmail.dev.zhilin.pricelistbuilder.templates.top.AdvancedTop;

public abstract class ArrivalTemplate extends ItemTemplate {
    
    private static final String MESSAGE = "Новое поступление!";

    private Sheet sheet;
    private ArrivalStyleSet styleSet;

    public ArrivalTemplate(Sheet sheet, ArrivalStyleSet styleSet, AdvancedTop top) {
        super(sheet, styleSet, top);
        this.sheet = sheet;
        this.styleSet = styleSet;
    }

    @Override
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
        insertArrivalMessage(itemArea);
        mergeCells(itemArea);
    }

    protected Row[] createItemArea(Item item) {
        int columns = 14;
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

    protected void insertArrivalMessage(Row[] itemArea) {
        Cell cell = itemArea[0].getCell(13);

        cell.setCellStyle(styleSet.getArrivalMessageStyle());
        cell.setCellValue(MESSAGE);
    }

    @Override
    protected void mergeCells(Row[] itemArea) {
        if (itemArea.length > 1) {
            int firstRow = itemArea[0].getRowNum();
            int lastRow = itemArea[itemArea.length - 1].getRowNum();
            int[] columns = { 1, 2, 3, 4, 5, 6, 13 };

            for (int i = 0; i < columns.length; i++) {
                CellRangeAddress mergeRegion = new CellRangeAddress(firstRow, lastRow, columns[i], columns[i]);

                RegionUtil.setBorderLeft(BorderStyle.THIN, mergeRegion, this.sheet);

                if(columns[i] != 13) {
                    RegionUtil.setBorderTop(BorderStyle.THIN, mergeRegion, this.sheet);
                    RegionUtil.setBorderBottom(BorderStyle.THIN, mergeRegion, this.sheet);
                    RegionUtil.setBorderRight(BorderStyle.THIN, mergeRegion, this.sheet);
                }
                
                this.sheet.addMergedRegion(mergeRegion);
            }
        }
    }
    
}
