package com.gmail.dev.zhilin.pricelistbuilder.storages;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LocalStorage {

    @Value("${path.pricelistfolder.advanced}")
    private String advancedPricelistFolder;
    @Value("${path.pricelistfolder.simple}")
    private String simplePricelistFolder;
    @Value("${path.tempfolder}")
    private String tempFileFolder;
    private static final String XLSX_SUFFIX = ".xlsx";
    private static final String XLS_SUFFIX = ".xls";

    public void saveAdvancedPriceList(Workbook workbook, String name) {
        File file;

        if (workbook instanceof XSSFWorkbook) {
            file = new File(advancedPricelistFolder + name.replaceAll(" /", ",") + XLSX_SUFFIX);
        } else {
            file = new File(advancedPricelistFolder + name.replaceAll(" /", ",") + XLS_SUFFIX);
        }

        try (FileOutputStream out = new FileOutputStream(file)){
            synchronized (file.getCanonicalPath().intern()) {
                workbook.write(out);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveSimplePriceList(Workbook workbook, String name) {
        File file;

        if (workbook instanceof XSSFWorkbook) {
            file = new File(simplePricelistFolder + name.replaceAll(" /", ",") + XLSX_SUFFIX);
        } else {
            file = new File(simplePricelistFolder + name.replaceAll(" /", ",") + XLS_SUFFIX);
        }

        try (FileOutputStream out = new FileOutputStream(file)) {
            synchronized (file.getCanonicalPath().intern()) {
                workbook.write(out);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public File getAdvancedPriceList(String name) throws IOException {
        File source = new File(advancedPricelistFolder + name);
        File pricelist = File.createTempFile("advanced", XLSX_SUFFIX, new File(tempFileFolder));

        try {
            synchronized (source.getCanonicalPath().intern()) {
                FileUtils.copyFile(source, pricelist);
            }
        } catch (Exception e) {
            pricelist.delete();
            throw e;
        }

        return pricelist;
    }

    public File getSimplePriceList(String name) throws IOException {
        File source = new File(simplePricelistFolder + name);
        File pricelist = File.createTempFile("simple", XLS_SUFFIX, new File(tempFileFolder));

        try {
            synchronized (source.getCanonicalPath().intern()) {
                FileUtils.copyFile(source, pricelist);
            }
        } catch (Exception e) {
            pricelist.delete();
            throw e;
        }

        return pricelist;
    }

}
