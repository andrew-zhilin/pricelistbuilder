package com.gmail.dev.zhilin.pricelistbuilder.controllers;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
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
import org.springframework.web.multipart.MultipartFile;
import com.gmail.dev.zhilin.pricelistbuilder.builders.advanced.arrival.ArrivalWholesaleBuilder;
import com.gmail.dev.zhilin.pricelistbuilder.models.Item;
import com.gmail.dev.zhilin.pricelistbuilder.parsers.ObmenPRSParser;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/arrival")
public class ArrivalController {

    private static final List<String> TYPES = Arrays.asList("только поступившие", "по разделам", "по бренду");
    private static final String FILE_NAME = "Новое поступление.xlsx";

    @Autowired
    private ObmenPRSParser parser;
    @Autowired
    private ArrivalWholesaleBuilder builder;

    @GetMapping
    public String getPage(Model model) {
        Form form = new Form();

        form.setType(TYPES.get(0));
        model.addAttribute(form);
        model.addAttribute("types", TYPES);

        return "arrival";
    }

    @PostMapping
    public void getPricelist(@ModelAttribute("form") Form form, HttpServletResponse response) {
        List<String> itemNames = readFile(form.getFile());
        Workbook workbook = createPriceList(itemNames, form.getType());

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

    private List<String> readFile(MultipartFile file) {
        List<String> result = new ArrayList<>();
        String line;

        try (InputStream is = file.getInputStream();
                Reader isr = new InputStreamReader(is);
                BufferedReader reader = new BufferedReader(isr)) {

            while ((line = reader.readLine()) != null) {
                result.add(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private Workbook createPriceList(List<String> arrivedItemNames, String type) {
        List<Item> items = pickUpItems(arrivedItemNames, type);
        Workbook workbook = new XSSFWorkbook();

        builder.build(workbook, items, arrivedItemNames);

        return workbook;
    }

    private List<Item> pickUpItems(List<String> arrivedItemNames, String type) {
        List<Item> items = parser.parse();
        List<Item> arrivedItems = items.stream().filter(i -> arrivedItemNames.contains(i.getName()))
                .collect(Collectors.toList());
        Set<Item> result = new HashSet<>(arrivedItems);

        if (type.equals(TYPES.get(1))) {
            for (Item item : arrivedItems) {
                items.stream().filter(i -> item.sameFolder(i)).forEach(i -> result.add(i));
            }
        }

        if (type.equals(TYPES.get(2))) {
            String manufacturer = arrivedItems.get(0).getManufacturer();

            result.addAll(items.stream().filter(i -> i.getManufacturer() != null)
                    .filter(i -> i.getManufacturer().equals(manufacturer)).collect(Collectors.toList()));
        }

        return new ArrayList<>(result);
    }

    private class Form {

        private String type;
        private MultipartFile file;

        public MultipartFile getFile() {
            return file;
        }

        public void setFile(MultipartFile file) {
            this.file = file;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

    }

}
