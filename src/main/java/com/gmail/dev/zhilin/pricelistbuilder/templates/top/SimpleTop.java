package com.gmail.dev.zhilin.pricelistbuilder.templates.top;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import com.gmail.dev.zhilin.pricelistbuilder.stylesets.top.StyleSet;

public class SimpleTop {

    private static final Map<String, Integer> HEADERS = new LinkedHashMap<>();

    static {
        HEADERS.put("Код", 3650);
        HEADERS.put("Бренд", 3650);
        HEADERS.put("Артикул", 3650);
        HEADERS.put("Кросс-номера", 7300);
        HEADERS.put("Применимость", 5120);
        HEADERS.put("Наименование", 14280);
        HEADERS.put("Фото", 2200);
        HEADERS.put("Цена", 2550);
        HEADERS.put("Остаток", 2550);
        HEADERS.put("Склад", 4030);
        HEADERS.put("Заказ", 2550);
    }

    private StyleSet styleSet;
    private Row headerRow;

    public SimpleTop(Sheet sheet) {
        headerRow = createHeaderRow(sheet);
        styleSet = new StyleSet(sheet.getWorkbook());

        insertHeaders(headerRow);
    }

    private Row createHeaderRow(Sheet sheet) {
        int columns = 11;
        Row row = sheet.createRow(sheet.getLastRowNum() + 1);

        for (int i = 0; i < columns; i++)
            row.createCell(i);

        return row;
    }

    private void insertHeaders(Row row) {
        List<String> headers = new ArrayList<>(HEADERS.keySet());
        List<Integer> width = new ArrayList<>(HEADERS.values());
        Cell headerCell;

        for (int i = 0; i < HEADERS.size(); i++) {
            headerCell = row.getCell(i);

            headerCell.setCellValue(headers.get(i));
            headerCell.setCellStyle(styleSet.getHeaderStyle());
            row.getSheet().setColumnWidth(headerCell.getColumnIndex(), width.get(i));
        }

        row.setHeight((short) 450);
        row.getSheet().setColumnHidden(row.getCell(0).getColumnIndex(), true);
    }

    public void activateFilter() {
        Cell firstCell = headerRow.getCell(headerRow.getFirstCellNum());
        Cell lastCell = headerRow.getCell(headerRow.getLastCellNum() - 1);

        headerRow.getSheet().setAutoFilter(new CellRangeAddress(firstCell.getRowIndex(), lastCell.getRowIndex(),
                firstCell.getColumnIndex(), lastCell.getColumnIndex()));
    }

}
