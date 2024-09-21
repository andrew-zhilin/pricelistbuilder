package com.gmail.dev.zhilin.pricelistbuilder.builders.simple;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.gmail.dev.zhilin.pricelistbuilder.stylesets.item.GreyStyleSet;
import com.gmail.dev.zhilin.pricelistbuilder.stylesets.item.WhiteStyleSet;
import com.gmail.dev.zhilin.pricelistbuilder.templates.item.simple.ItemTemplate;
import com.gmail.dev.zhilin.pricelistbuilder.templates.item.simple.RetailTemplate;

@Component
@Scope("prototype")
public class SimpleRetailBuilder extends SimpleBuilder {

    @Override
    protected ItemTemplate initializeWhiteItemTemplate(Workbook workbook) {
        return new RetailTemplate(new WhiteStyleSet(workbook));
    }

    @Override
    protected ItemTemplate initializeGreyItemTemplate(Workbook workbook) {
        return new RetailTemplate(new GreyStyleSet(workbook));
    }

}
