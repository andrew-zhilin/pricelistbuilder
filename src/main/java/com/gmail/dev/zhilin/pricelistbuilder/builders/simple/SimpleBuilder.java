package com.gmail.dev.zhilin.pricelistbuilder.builders.simple;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import com.gmail.dev.zhilin.pricelistbuilder.enums.Warehouse;
import com.gmail.dev.zhilin.pricelistbuilder.models.Item;
import com.gmail.dev.zhilin.pricelistbuilder.templates.item.simple.ItemTemplate;
import com.gmail.dev.zhilin.pricelistbuilder.templates.top.SimpleTop;

public abstract class SimpleBuilder {

    public final void build(Workbook workbook, List<Item> items, Warehouse warehouse) {
        Sheet sheet = workbook.createSheet();
        SimpleTop top = new SimpleTop(sheet);

        workbook.setSheetName(workbook.getSheetIndex(sheet), "Прайс-лист");

        if (!items.isEmpty()) {
            sortItems(items);
            insertItems(sheet, items, warehouse);
            top.activateFilter();
        }
    }

    protected final void sortItems(List<Item> items) {
        Collections.sort(items, Comparator.comparing(Item::getName));
    }

    protected final void insertItems(Sheet sheet, List<Item> items, Warehouse warehouse) {
        ItemTemplate whiteItemTemplate = initializeWhiteItemTemplate(sheet.getWorkbook());
        ItemTemplate greyItemTemplate = initializeGreyItemTemplate(sheet.getWorkbook());
        boolean isWhite = true;

        for (Item item : items) {
            if (item.getStock().containsKey(warehouse)) {
                if (isWhite)
                    whiteItemTemplate.insert(sheet, item, warehouse);
                else
                    greyItemTemplate.insert(sheet, item, warehouse);

                isWhite = !isWhite;
            }
        }
    }

    protected abstract ItemTemplate initializeWhiteItemTemplate(Workbook workbook);

    protected abstract ItemTemplate initializeGreyItemTemplate(Workbook workbook);

}
