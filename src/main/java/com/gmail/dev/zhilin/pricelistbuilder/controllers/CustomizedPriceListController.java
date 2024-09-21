package com.gmail.dev.zhilin.pricelistbuilder.controllers;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.gmail.dev.zhilin.pricelistbuilder.builders.advanced.general.GeneralRetailBuilder;
import com.gmail.dev.zhilin.pricelistbuilder.builders.advanced.general.GeneralWholesaleBuilder;
import com.gmail.dev.zhilin.pricelistbuilder.builders.simple.SimpleRetailBuilder;
import com.gmail.dev.zhilin.pricelistbuilder.builders.simple.SimpleWholesaleBuilder;
import com.gmail.dev.zhilin.pricelistbuilder.enums.PriceType;
import com.gmail.dev.zhilin.pricelistbuilder.enums.Truck;
import com.gmail.dev.zhilin.pricelistbuilder.enums.Warehouse;
import com.gmail.dev.zhilin.pricelistbuilder.models.Item;
import com.gmail.dev.zhilin.pricelistbuilder.parsers.ObmenPRSParser;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/customized-pricelist")
public class CustomizedPriceListController {

    private static final List<String> MANUFACTURERS = Arrays.asList("Бренд_1", "Бренд_2");
    private static final String FILE_NAME = "Индивидуальный прайс-лист.xlsx";

    @Autowired
    private ObmenPRSParser parser;

    @GetMapping
    @ModelAttribute
    public String getPage(Model model) {
        Form form = new Form();

        model.addAttribute("form", form);
        model.addAttribute("allwarehouses", Arrays.asList(Warehouse.values()));
        model.addAttribute("allpricetypes", Arrays.asList(PriceType.values()));
        model.addAttribute("alltrucks", Arrays.asList(Truck.values()));
        model.addAttribute("manufacturers", MANUFACTURERS);

        return "customized-pricelist";
    }

    @PostMapping
    public void getPricelist(@ModelAttribute("preset") Form form, HttpServletResponse response) {
        Workbook workbook = createPricelist(form);

        try {
            Cookie cookie = new Cookie("downloadStarted", "1");

            cookie.setMaxAge(20);
            response.addCookie(cookie);
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.builder("attachment")
                    .filename(FILE_NAME, StandardCharsets.UTF_8).build().toString());
            workbook.write(response.getOutputStream());
            response.flushBuffer();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private Workbook createPricelist(Form form) {
        List<Item> items = filterItems(parser.parse(), form);
        Workbook workbook = new XSSFWorkbook();

        if (form.isAdvanced()) {
            items = leaveOnly(items, form.getWarehouses());

            if (form.getPriceType() == PriceType.RETAIL) {
                new GeneralRetailBuilder().build(workbook, items);
            } else {
                new GeneralWholesaleBuilder().build(workbook, items);
            }
        } else {
            if (form.getPriceType() == PriceType.RETAIL) {
                new SimpleRetailBuilder().build(workbook, items, form.getWarehouse());
            } else {
                new SimpleWholesaleBuilder().build(workbook, items, form.getWarehouse());
            }
        }

        return workbook;
    }

    private List<Item> leaveOnly(List<Item> items, List<Warehouse> warehouses) {
        Set<Item> result = new HashSet<>();

        if (warehouses != null) {
            for (Warehouse warehouse : warehouses) {
                items.stream().filter(i -> i.getStock().containsKey(warehouse)).forEach(i -> result.add(i));
            }
        }

        result.stream().forEach(i -> i.getStock().keySet().removeIf(k -> !warehouses.contains(k)));

        return new ArrayList<Item>(result);
    }

    private List<Item> filterItems(List<Item> items, Form form) {
        List<Item> result = new ArrayList<>(items);

        if (form.getTrucks() != null && form.getTrucks().size() != 0) {
            result.retainAll(filterByTruck(items, form.getTrucks()));
        }

        if (form.getManufacturers() != null && form.getManufacturers().size() != 0) {
            result.retainAll(filterByManufacturer(items, form.getManufacturers()));
        }

        return new ArrayList<Item>(result);
    }

    private List<Item> filterByTruck(List<Item> items, List<Truck> trucks) {
        Set<Item> result = new HashSet<>();

        if (trucks != null && trucks.size() != 0) {
            for (Truck truck : trucks) {
                for (Item item : items) {
                    if (item.getApplication() != null && item.getApplication().contains(truck))
                        result.add(item);
                }
            }
        }

        return new ArrayList<Item>(result);
    }

    private List<Item> filterByManufacturer(List<Item> items, List<String> manufacturers) {
        Set<Item> result = new HashSet<>();

        if (manufacturers != null && manufacturers.size() != 0) {
            for (Item item : items) {
                if (item.getManufacturer() != null && manufacturers.contains(item.getManufacturer()))
                    result.add(item);
            }
        }

        return new ArrayList<Item>(result);
    }

    public class Form {

        private boolean advanced;
        private PriceType priceType;
        private Warehouse warehouse;
        private List<Warehouse> warehouses;
        private List<Truck> trucks;
        private List<String> manufacturers;

        public Form() {
            this.advanced = true;
            this.warehouse = Warehouse.KHABAROVSK;
            this.warehouses = (Arrays.asList(Warehouse.values()));
            this.priceType = PriceType.WHOLESALE;
        }

        public boolean isAdvanced() {
            return advanced;
        }

        public void setAdvanced(boolean advanced) {
            this.advanced = advanced;
        }

        public PriceType getPriceType() {
            return priceType;
        }

        public void setPriceType(PriceType priceType) {
            this.priceType = priceType;
        }

        public Warehouse getWarehouse() {
            return warehouse;
        }

        public void setWarehouse(Warehouse warehouse) {
            this.warehouse = warehouse;
        }

        public List<Warehouse> getWarehouses() {
            return warehouses;
        }

        public void setWarehouses(List<Warehouse> warehouses) {
            this.warehouses = warehouses;
        }

        public List<Truck> getTrucks() {
            return trucks;
        }

        public void setTrucks(List<Truck> trucks) {
            this.trucks = trucks;
        }

        public List<String> getManufacturers() {
            return manufacturers;
        }

        public void setManufacturers(List<String> manufacturers) {
            this.manufacturers = manufacturers;
        }
        
    }

}
