package com.gmail.dev.zhilin.pricelistbuilder.templates.item.advanced;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.gmail.dev.zhilin.pricelistbuilder.models.Item;
import com.gmail.dev.zhilin.pricelistbuilder.stylesets.item.StyleSet;
import com.gmail.dev.zhilin.pricelistbuilder.templates.top.AdvancedTop;

public class RetailTemplate extends ItemTemplate {

    private StyleSet styleSet;

    public RetailTemplate(Sheet sheet, StyleSet styleSet, AdvancedTop top) {
        super(sheet, styleSet, top);
        this.styleSet = styleSet;
    }

    @Override
    protected void insertPrice(Row[] itemArea, Item item) {
        Cell cell;

        for (Row row : itemArea) {
            cell = row.getCell(7);

            cell.setCellValue(item.getRetailPrice());
            cell.setCellStyle(styleSet.getPriceStyle());
        }
    }

}
