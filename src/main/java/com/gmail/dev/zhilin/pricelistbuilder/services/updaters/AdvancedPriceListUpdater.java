package com.gmail.dev.zhilin.pricelistbuilder.services.updaters;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.gmail.dev.zhilin.pricelistbuilder.builders.advanced.general.GeneralRetailBuilder;
import com.gmail.dev.zhilin.pricelistbuilder.builders.advanced.general.GeneralWholesaleBuilder;
import com.gmail.dev.zhilin.pricelistbuilder.enums.PriceType;
import com.gmail.dev.zhilin.pricelistbuilder.enums.Truck;
import com.gmail.dev.zhilin.pricelistbuilder.enums.Warehouse;
import com.gmail.dev.zhilin.pricelistbuilder.models.Item;
import com.gmail.dev.zhilin.pricelistbuilder.storages.LocalStorage;

@Component
public class AdvancedPriceListUpdater {

    private static final String FILE_NAME_TEMPLATE = "Сайт-компании.com - %s (%s цены)";
    private static final String GENERAL_PRICELIST_NAME = "Общий прайс-лист";
    private static final String TRUCK_MESSAGE = "Это не полный наш прайс-лист! Отобраны только позиции для \"%s\"";
    private static final String WAREHOUSE_MESSAGE = "Это не полный наш прайс-лист! Отобраны только позиции в %s";

    @Autowired
    private LocalStorage localStorage;
    @Autowired
    private GeneralRetailBuilder retailBuilder;
    @Autowired
    private GeneralWholesaleBuilder wholesaleBuilder;
    private static ReentrantLock lock = new ReentrantLock();

    public void update(List<Item> items) {
        if (lock.tryLock()) {
            updateGeneralPricelists(items);
            updateTruckPricelists(items);
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

    private void updateGeneralPricelists(List<Item> items) {
        List<Item> content = new ArrayList<Item>(items);
        Workbook workbook;

        for (PriceType type : PriceType.values()) {
            workbook = new XSSFWorkbook();

            if (type == PriceType.RETAIL) {
                retailBuilder.build(workbook, content);
            } else {
                wholesaleBuilder.build(workbook, content);
            }

            localStorage.saveAdvancedPriceList(workbook,
                    String.format(FILE_NAME_TEMPLATE, GENERAL_PRICELIST_NAME, type.getName()));
        }
    }

    private void updateTruckPricelists(List<Item> items) {
        Workbook workbook;
        List<Item> content;

        for (Truck truck : Truck.values()) {
            content = items.stream().filter(i -> i.getApplication() != null)
                    .filter(i -> i.getApplication().contains(truck)).collect(Collectors.toList());

            for (PriceType type : PriceType.values()) {
                workbook = new XSSFWorkbook();

                if (type == PriceType.RETAIL) {
                    retailBuilder.build(workbook, content, String.format(TRUCK_MESSAGE, truck.getName()));
                } else {
                    wholesaleBuilder.build(workbook, content, String.format(TRUCK_MESSAGE, truck.getName()));
                }

                localStorage.saveAdvancedPriceList(workbook,
                        String.format(FILE_NAME_TEMPLATE, truck.getName(), type.getName()));
            }
        }
    }

    private void updateWarehousePricelists(List<Item> items) {
        Workbook workbook;
        List<Item> content;

        for (Warehouse warehouse : Warehouse.values()) {
            content = new ArrayList<>();

            content = items.stream().filter(i -> i.getStock().containsKey(warehouse)).map(i -> (Item) i.clone())
                    .collect(Collectors.toList());

            content.stream().forEach(i -> i.getStock().keySet().removeIf(keys -> keys != warehouse));

            for (PriceType type : PriceType.values()) {
                workbook = new XSSFWorkbook();

                if (type == PriceType.RETAIL) {
                    retailBuilder.build(workbook, content, String.format(WAREHOUSE_MESSAGE, warehouse.getName()));
                } else {
                    wholesaleBuilder.build(workbook, content, String.format(WAREHOUSE_MESSAGE, warehouse.getName()));
                }

                localStorage.saveAdvancedPriceList(workbook,
                        String.format(FILE_NAME_TEMPLATE, warehouse.getName(), type.getName()));
            }
        }
    }

}
