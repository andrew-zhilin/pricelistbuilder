package com.gmail.dev.zhilin.pricelistbuilder.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.gmail.dev.zhilin.pricelistbuilder.storages.LocalStorage;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class IndexController {

    @Autowired
    private LocalStorage localStorage;

    @GetMapping("/")
    public String index() {
        return "index";
    }
    
    @RequestMapping(value = "/pricelists/advanced/{file_name}", method = RequestMethod.GET)
    public void getAdvancedPricelist(@PathVariable("file_name") String fileName, HttpServletResponse response) {
        File pricelist = null;

        try {
            pricelist = localStorage.getAdvancedPriceList(fileName);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try (InputStream is = new FileInputStream(pricelist)) {
            IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (pricelist != null && pricelist.exists() && !pricelist.isDirectory()) {
            pricelist.delete();
        }
    }

    @RequestMapping(value = "/pricelists/simple/{file_name}", method = RequestMethod.GET)
    public void getSimplePricelist(@PathVariable("file_name") String fileName, HttpServletResponse response) {
        File pricelist = null;

        try {
            pricelist = localStorage.getSimplePriceList(fileName);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try (InputStream is = new FileInputStream(pricelist)) {
            IOUtils.copy(is, response.getOutputStream());
            response.flushBuffer();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (pricelist != null && pricelist.exists() && !pricelist.isDirectory()) {
            pricelist.delete();
        }
    }

}
