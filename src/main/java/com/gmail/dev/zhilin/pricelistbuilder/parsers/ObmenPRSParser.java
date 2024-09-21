package com.gmail.dev.zhilin.pricelistbuilder.parsers;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.gmail.dev.zhilin.pricelistbuilder.enums.Truck;
import com.gmail.dev.zhilin.pricelistbuilder.enums.Warehouse;
import com.gmail.dev.zhilin.pricelistbuilder.models.Item;

@Component
public class ObmenPRSParser {

    @Value("${path.xml}")
    private String obmenPRSPath;

    private List<Item> items;

    public synchronized List<Item> parse() {
        items = new ArrayList<>();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser;
        XMLHandler handler = new XMLHandler();

        try {
            parser = factory.newSAXParser();
            parser.parse(new File(obmenPRSPath), handler);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return items;
    }

    private class XMLHandler extends DefaultHandler {
        private String currentElementName;
        private String section;
        private String subsection;
        private String subsubsection;
        private Item item;
        private Map<Warehouse, Integer> stock;
        StringBuilder sb = new StringBuilder();

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes)
                throws SAXException {
            currentElementName = qName;

            if (qName.equals("level_2")) {
                section = attributes.getValue(0);
            }

            if (qName.equals("level_3")) {
                subsection = attributes.getValue(0);
            }

            if (qName.equals("level_4")) {
                subsubsection = attributes.getValue(0);
            }

            if (qName.equals("item")) {
                item = new Item();
                stock = new HashMap<>();
            }

            if (qName.equals("code")) {
                sb.setLength(0);
            }

            if (qName.equals("brand")) {
                sb.setLength(0);
            }

            if (qName.equals("vendor_code")) {
                sb.setLength(0);
            }

            if (qName.equals("crosses")) {
                sb.setLength(0);
            }

            if (qName.equals("applicability")) {
                sb.setLength(0);
            }

            if (qName.equals("name")) {
                sb.setLength(0);
            }

            if (qName.equals("link")) {
                sb.setLength(0);
            }

            if (qName.equals("Интернет-магазин")) {
                sb.setLength(0);
            }

            if (qName.equals("wholesale_price")) {
                sb.setLength(0);
            }

            if (qName.equals("wholesale_region_price")) {
                sb.setLength(0);
            }

            if (qName.equals("moscow_balance")) {
                sb.setLength(0);
            }

            if (qName.equals("khabarovsk_balance")) {
                sb.setLength(0);
            }

            if (qName.equals("novosibirsk_balance")) {
                sb.setLength(0);
            }

            if (qName.equals("measure")) {
                sb.setLength(0);
            }
        }

        @Override
        public void characters(char[] ch, int start, int lenght) throws SAXException {
            String information = new String(ch, start, lenght);

            if (currentElementName.equals("code")) {
                sb.append(information);
            }

            if (currentElementName.equals("brand")) {
                sb.append(information);
            }

            if (currentElementName.equals("vendor_code")) {
                sb.append(information);
            }

            if (currentElementName.equals("crosses")) {
                sb.append(information);
            }

            if (currentElementName.equals("applicability")) {
                sb.append(information);
            }

            if (currentElementName.equals("name")) {
                sb.append(information);
            }

            if (currentElementName.equals("link")) {
                sb.append(information);
            }

            if (currentElementName.equals("Интернет-магазин")) {
                sb.append(information);
            }

            if (currentElementName.equals("wholesale_price")) {
                sb.append(information);
            }

            if (currentElementName.equals("wholesale_region_price")) {
                sb.append(information);
            }

            if (currentElementName.equals("moscow_balance")) {
                sb.append(information);
            }

            if (currentElementName.equals("khabarovsk_balance")) {
                sb.append(information);
            }

            if (currentElementName.equals("novosibirsk_balance")) {
                sb.append(information);
            }

            if (currentElementName.equals("measure")) {
                sb.append(information);
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            if (qName.equals("level_2")) {
                section = null;
            }

            if (qName.equals("level_3")) {
                subsection = null;
            }

            if (qName.equals("level_4")) {
                subsubsection = null;
            }

            if (qName.equals("item")) {
                item.setSection(section);
                item.setSubsection(subsection);
                item.setSubsubsection(subsubsection);
                item.setStock(stock);
                items.add(item);
            }

            if (qName.equals("code")) {
                item.setCode(sb.toString().trim());
            }

            if (qName.equals("brand")) {
                item.setManufacturer(sb.toString().trim());
            }

            if (qName.equals("vendor_code")) {
                item.setPartNumber(sb.toString().trim());
            }

            if (qName.equals("crosses")) {
                item.setCrossReference(sb.toString().trim());
            }

            if (qName.equals("applicability")) {
                Set<Truck> trucks = extractTrucks(sb.toString().trim());

                if (!trucks.isEmpty())
                    item.setApplication(trucks);
            }

            if (qName.equals("name")) {
                item.setName(sb.toString().trim());

            }

            if (qName.equals("link")) {
                item.setLink(sb.toString().trim());
            }

            if (qName.equals("Интернет-магазин")) {
                item.setRetailPrice(extractInteger(sb.toString().trim()));
            }

            if (qName.equals("wholesale_price")) {
                item.setWholesalePrice(extractInteger(sb.toString().trim()));
            }

            if (qName.equals("wholesale_region_price")) {
                item.setRegionalWholesalePrice(extractInteger(sb.toString().trim()));
            }

            if (qName.equals("moscow_balance")) {
                int moskowStock = extractInteger(sb.toString().trim());

                if (moskowStock > 0)
                    stock.put(Warehouse.MOSCOW, moskowStock);
            }

            if (qName.equals("khabarovsk_balance")) {
                int khabarovskStock = extractInteger(sb.toString().trim());

                if (khabarovskStock > 0)
                    stock.put(Warehouse.KHABAROVSK, khabarovskStock);
            }

            if (qName.equals("novosibirsk_balance")) {
                int novosibirskStock = extractInteger(sb.toString().trim());

                if (novosibirskStock > 0)
                    stock.put(Warehouse.NOVOSIBIRSK, novosibirskStock);
            }

            if (qName.equals("measure")) {
                item.setMeasure(sb.toString().trim());
            }
        }

        private Set<Truck> extractTrucks(String information) {
            List<String> truckNames = Arrays.asList(information.split("; "));
            List<Truck> trucks = Arrays.asList(Truck.values());
            Set<Truck> result = new HashSet<>();

            for (Truck truck : trucks) {
                if (truckNames.contains(truck.getName())) {
                    result.add(truck);
                }
            }

            return result;
        }

        private int extractInteger(String information) {
            int result = 0;

            if (information != null && !information.isBlank()) {
                String[] price = information.trim().split(",");

                result = Integer.parseInt(price[0]);
            }

            return result;
        }
    }

}
