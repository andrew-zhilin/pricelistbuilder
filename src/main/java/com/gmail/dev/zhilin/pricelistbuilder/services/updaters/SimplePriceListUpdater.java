package com.gmail.dev.zhilin.pricelistbuilder.services.updaters;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.gmail.dev.zhilin.pricelistbuilder.builders.simple.SimpleRetailBuilder;
import com.gmail.dev.zhilin.pricelistbuilder.builders.simple.SimpleWholesaleBuilder;
import com.gmail.dev.zhilin.pricelistbuilder.enums.PriceType;
import com.gmail.dev.zhilin.pricelistbuilder.enums.Warehouse;
import com.gmail.dev.zhilin.pricelistbuilder.models.Item;
import com.gmail.dev.zhilin.pricelistbuilder.storages.LocalStorage;

@Component
public class SimplePriceListUpdater {
    
    private static final String FILE_NAME_TEMPLATE = "Сайт-компании.com - %s (%s цены)";

    @Autowired
    private LocalStorage localStorage;
    @Autowired
    private SimpleRetailBuilder retailBuilder;
    @Autowired
    private SimpleWholesaleBuilder wholesaleBuilder;
    private static ReentrantLock lock = new ReentrantLock();

    public void update(List<Item> items) {
        if (lock.tryLock()) {
            updateWarehousePricelists(items);
            lock.unlock();
        } else {
            do {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (lock.isLocked());
        }
    }

    private void updateWarehousePricelists(List<Item> items) {
        Workbook workbook;

        for (Warehouse warehouse : Warehouse.values()) {
            for (PriceType type : PriceType.values()) {
                workbook = new XSSFWorkbook();

                if (type == PriceType.RETAIL) {
                    retailBuilder.build(workbook, items, warehouse);
                } else {
                    wholesaleBuilder.build(workbook, items, warehouse);
                }

                localStorage.saveSimplePriceList(workbook,
                        String.format(FILE_NAME_TEMPLATE, warehouse.getName(), type.getName()));
            }
        }
    }
    
}
