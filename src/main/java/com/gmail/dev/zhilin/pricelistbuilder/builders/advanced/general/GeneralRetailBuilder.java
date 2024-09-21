package com.gmail.dev.zhilin.pricelistbuilder.builders.advanced.general;

import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.gmail.dev.zhilin.pricelistbuilder.enums.PriceType;
import com.gmail.dev.zhilin.pricelistbuilder.stylesets.item.GreyStyleSet;
import com.gmail.dev.zhilin.pricelistbuilder.stylesets.item.WhiteStyleSet;
import com.gmail.dev.zhilin.pricelistbuilder.templates.item.advanced.ItemTemplate;
import com.gmail.dev.zhilin.pricelistbuilder.templates.item.advanced.RetailTemplate;
import com.gmail.dev.zhilin.pricelistbuilder.templates.top.AdvancedTop;

@Component
@Scope("prototype")
public class GeneralRetailBuilder extends GeneralBuilder {

    @Override
    protected AdvancedTop insertTop(Sheet sheet) {
        return new AdvancedTop(sheet, PriceType.RETAIL);
    }

    @Override
    protected AdvancedTop insertTop(Sheet sheet, String message) {
        return new AdvancedTop(sheet, PriceType.RETAIL, message);
    }

    @Override
    protected ItemTemplate initializeWhiteItemTemplate(Sheet sheet, AdvancedTop top) {
        return new RetailTemplate(sheet, new WhiteStyleSet(sheet.getWorkbook()), top);
    }

    @Override
    protected ItemTemplate initializeGreyItemTemplate(Sheet sheet, AdvancedTop top) {
        return new RetailTemplate(sheet, new GreyStyleSet(sheet.getWorkbook()), top);
    }

}
