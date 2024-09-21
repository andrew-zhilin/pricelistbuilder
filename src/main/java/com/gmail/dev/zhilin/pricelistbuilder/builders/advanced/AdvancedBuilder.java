package com.gmail.dev.zhilin.pricelistbuilder.builders.advanced;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.apache.poi.ss.usermodel.Sheet;
import com.gmail.dev.zhilin.pricelistbuilder.comparators.ItemBySection;
import com.gmail.dev.zhilin.pricelistbuilder.comparators.ItemBySubsection;
import com.gmail.dev.zhilin.pricelistbuilder.comparators.ItemBySubsubsection;
import com.gmail.dev.zhilin.pricelistbuilder.models.Item;
import com.gmail.dev.zhilin.pricelistbuilder.stylesets.section.SectionStyleSet;
import com.gmail.dev.zhilin.pricelistbuilder.stylesets.section.SubsectionStyleSet;
import com.gmail.dev.zhilin.pricelistbuilder.stylesets.section.SubsubsectionStyleSet;
import com.gmail.dev.zhilin.pricelistbuilder.templates.section.SectionTemplate;

public abstract class AdvancedBuilder {

    protected static final String SHEET_NAME = "Прайс-лист";
    
    protected final void sortItems(List<Item> items) {
        Collections.sort(items,new ItemBySection()
                .thenComparing(new ItemBySubsection())
                .thenComparing(new ItemBySubsubsection())
                .thenComparing(Comparator.comparing(Item::getName)));
    }

    protected final SectionTemplate initializeSectionTemplate(Sheet sheet) {
        return new SectionTemplate(sheet, new SectionStyleSet(sheet.getWorkbook()));
    }

    protected final SectionTemplate initializeSubsectionTemplate(Sheet sheet) {
        return new SectionTemplate(sheet, new SubsectionStyleSet(sheet.getWorkbook()));
    }

    protected final SectionTemplate initializeSubsubsectionTemplate(Sheet sheet) {
        return new SectionTemplate(sheet, new SubsubsectionStyleSet(sheet.getWorkbook()));
    }

}
