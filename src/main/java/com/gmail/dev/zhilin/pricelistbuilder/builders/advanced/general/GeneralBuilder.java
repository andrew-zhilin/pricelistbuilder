package com.gmail.dev.zhilin.pricelistbuilder.builders.advanced.general;

import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import com.gmail.dev.zhilin.pricelistbuilder.builders.advanced.AdvancedBuilder;
import com.gmail.dev.zhilin.pricelistbuilder.models.Item;
import com.gmail.dev.zhilin.pricelistbuilder.templates.item.advanced.ItemTemplate;
import com.gmail.dev.zhilin.pricelistbuilder.templates.section.SectionTemplate;
import com.gmail.dev.zhilin.pricelistbuilder.templates.top.AdvancedTop;

public abstract class GeneralBuilder extends AdvancedBuilder {

    private SectionTemplate sectionTemplate;
    private SectionTemplate subsectionTemplate;
    private SectionTemplate subsubsectionTemplate;
    private ItemTemplate whiteItemTemplate;
    private ItemTemplate greyItemTemplate;

    public void build(Workbook workbook, List<Item> items) {
        Sheet sheet = workbook.createSheet();
        AdvancedTop top = insertTop(sheet);

        workbook.setSheetName(workbook.getSheetIndex(sheet), SHEET_NAME);

        if (!items.isEmpty()) {
            sheet.setRowSumsBelow(false);
            sortItems(items);
            initializeTemplates(sheet, top);
            insertContent(sheet, items);
            top.activateOrderAmountFormula();
            top.activateFilter();
        }
    }

    public void build(Workbook workbook, List<Item> items, String message) {
        Sheet sheet = workbook.createSheet();
        AdvancedTop top = insertTop(sheet, message);

        workbook.setSheetName(workbook.getSheetIndex(sheet), SHEET_NAME);

        if (!items.isEmpty()) {
            sheet.setRowSumsBelow(false);
            sortItems(items);
            initializeTemplates(sheet, top);
            insertContent(sheet, items);
            top.activateOrderAmountFormula();
            top.activateFilter();
        }
    }

    protected abstract AdvancedTop insertTop(Sheet sheet);

    protected abstract AdvancedTop insertTop(Sheet sheet, String message);

    protected abstract ItemTemplate initializeWhiteItemTemplate(Sheet sheet, AdvancedTop top);

    protected abstract ItemTemplate initializeGreyItemTemplate(Sheet sheet, AdvancedTop top);

    private void initializeTemplates(Sheet sheet, AdvancedTop top) {
        this.sectionTemplate = initializeSectionTemplate(sheet);
        this.subsectionTemplate = initializeSubsectionTemplate(sheet);
        this.subsubsectionTemplate = initializeSubsubsectionTemplate(sheet);
        this.whiteItemTemplate = initializeWhiteItemTemplate(sheet, top);
        this.greyItemTemplate = initializeGreyItemTemplate(sheet, top);
    }

    protected void insertContent(Sheet sheet, List<Item> items) {
        List<Item> section = new ArrayList<>();
        Item previousItem = null;

        for (Item item : items) {
            if (previousItem == null) {
                section.add(item);
            } else if (item.getSection().equals(previousItem.getSection())) {
                section.add(item);
            } else {
                insertSection(sheet, section);
                section.clear();
                section.add(item);
            }

            if (items.indexOf(item) == items.size() - 1) {
                insertSection(sheet, section);
            }

            previousItem = item;
        }
    }

    private void insertSection(Sheet sheet, List<Item> items) {
        List<Item> subsection = new ArrayList<>();
        int firstRowIndex = 0;
        Item previousItem = null;

        for (Item item : items) {
            if (previousItem == null) {
                sectionTemplate.insert(item.getSection());
                subsection.add(item);

                firstRowIndex = sheet.getLastRowNum() + 1;
            } else if (item.getSubsection().equals(previousItem.getSubsection())) {
                subsection.add(item);
            } else {
                insertSubsection(sheet, subsection);
                subsection.clear();
                subsection.add(item);
            }

            if (items.indexOf(item) == items.size() - 1) {
                insertSubsection(sheet, subsection);
            }

            previousItem = item;
        }

        sheet.groupRow(firstRowIndex, sheet.getLastRowNum());
    }

    private void insertSubsection(Sheet sheet, List<Item> items) {
        if (items.get(0).getSubsubsection() == null) {
            subsectionTemplate.insert(items.get(0).getSubsection());

            int firstRowIndex = sheet.getLastRowNum() + 1;

            insertItems(items);
            sheet.groupRow(firstRowIndex, sheet.getLastRowNum());
        } else {
            List<Item> subsubsection = new ArrayList<>();
            int firstRowIndex = 0;
            Item previousItem = null;

            for (Item item : items) {
                if (previousItem == null) {
                    subsectionTemplate.insert(item.getSubsection());

                    firstRowIndex = sheet.getLastRowNum() + 1;

                    subsubsection.add(item);
                } else if (item.getSubsubsection().equals(previousItem.getSubsubsection())) {
                    subsubsection.add(item);
                } else {
                    insertSubsubsection(sheet, subsubsection);
                    subsubsection.clear();
                    subsubsection.add(item);
                }

                if (items.indexOf(item) == items.size() - 1) {
                    insertSubsubsection(sheet, subsubsection);
                }

                previousItem = item;
            }

            sheet.groupRow(firstRowIndex, sheet.getLastRowNum());
        }
    }

    private void insertSubsubsection(Sheet sheet, List<Item> items) {
        subsubsectionTemplate.insert(items.get(0).getSubsubsection());
        int firstRowIndex = sheet.getLastRowNum() + 1;

        insertItems(items);
        sheet.groupRow(firstRowIndex, sheet.getLastRowNum());
    }

    private void insertItems(List<Item> items) {
        boolean isWhite = true;

        for (Item item : items) {
            if (isWhite) {
                this.whiteItemTemplate.insert(item);
            } else {
                this.greyItemTemplate.insert(item);
            }

            isWhite = !isWhite;
        }
    }

}
