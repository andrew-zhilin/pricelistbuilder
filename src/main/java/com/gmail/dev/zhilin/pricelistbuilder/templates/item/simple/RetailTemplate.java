package com.gmail.dev.zhilin.pricelistbuilder.templates.item.simple;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.gmail.dev.zhilin.pricelistbuilder.enums.Warehouse;
import com.gmail.dev.zhilin.pricelistbuilder.models.Item;
import com.gmail.dev.zhilin.pricelistbuilder.stylesets.item.StyleSet;

public class RetailTemplate extends ItemTemplate {

    private StyleSet styleSet;

    public RetailTemplate(StyleSet styleSet) {
        super(styleSet);
        this.styleSet = styleSet;
    }

    @Override
    protected void insertPrice(Row row, Item item, Warehouse warehouse) {
        Cell cell = row.getCell(7);

        cell.setCellValue(item.getRetailPrice());
        cell.setCellStyle(styleSet.getPriceStyle());
    }

}
