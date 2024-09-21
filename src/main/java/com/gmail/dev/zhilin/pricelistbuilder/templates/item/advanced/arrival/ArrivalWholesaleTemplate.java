package com.gmail.dev.zhilin.pricelistbuilder.templates.item.advanced.arrival;

import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.gmail.dev.zhilin.pricelistbuilder.enums.Warehouse;
import com.gmail.dev.zhilin.pricelistbuilder.models.Item;
import com.gmail.dev.zhilin.pricelistbuilder.stylesets.item.ArrivalStyleSet;
import com.gmail.dev.zhilin.pricelistbuilder.templates.top.AdvancedTop;

public class ArrivalWholesaleTemplate extends ArrivalTemplate {
    
    private ArrivalStyleSet styleSet;

    public ArrivalWholesaleTemplate(Sheet sheet, ArrivalStyleSet styleSet, AdvancedTop top) {
        super(sheet, styleSet, top);
        this.styleSet = styleSet;
    }

    @Override
    protected void insertPrice(Row[] itemArea, Item item) {
        List<Warehouse> warehouses = new ArrayList<>(item.getStock().keySet());
        Cell cell;
        
        for (int i = 0; i < itemArea.length; i ++) {
            cell = itemArea[i].getCell(7);

            if(warehouses.get(i) == Warehouse.KHABAROVSK) {
                cell.setCellValue(item.getWholesalePrice());
            } else {
                cell.setCellValue(item.getRegionalWholesalePrice());
            }

            cell.setCellStyle(styleSet.getPriceStyle());
        }
    }

}
