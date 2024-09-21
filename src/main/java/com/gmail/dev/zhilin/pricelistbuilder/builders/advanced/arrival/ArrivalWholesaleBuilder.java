package com.gmail.dev.zhilin.pricelistbuilder.builders.advanced.arrival;

import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Component;
import com.gmail.dev.zhilin.pricelistbuilder.enums.PriceType;
import com.gmail.dev.zhilin.pricelistbuilder.stylesets.item.ArrivalStyleSet;
import com.gmail.dev.zhilin.pricelistbuilder.stylesets.item.GreyStyleSet;
import com.gmail.dev.zhilin.pricelistbuilder.stylesets.item.WhiteStyleSet;
import com.gmail.dev.zhilin.pricelistbuilder.templates.item.advanced.ItemTemplate;
import com.gmail.dev.zhilin.pricelistbuilder.templates.item.advanced.WholesaleTemplate;
import com.gmail.dev.zhilin.pricelistbuilder.templates.item.advanced.arrival.ArrivalTemplate;
import com.gmail.dev.zhilin.pricelistbuilder.templates.item.advanced.arrival.ArrivalWholesaleTemplate;
import com.gmail.dev.zhilin.pricelistbuilder.templates.top.AdvancedTop;

@Component
public class ArrivalWholesaleBuilder extends ArrivalBuilder {

    @Override
    protected AdvancedTop insertTop(Sheet sheet, String message) {
        return new AdvancedTop(sheet, PriceType.WHOLESALE, message);
    }

    @Override
    protected ItemTemplate initializeWhiteItemTemplate(Sheet sheet, AdvancedTop top) {
        return new WholesaleTemplate(sheet, new WhiteStyleSet(sheet.getWorkbook()), top);
    }

    @Override
    protected ItemTemplate initializeGreyItemTemplate(Sheet sheet, AdvancedTop top) {
        return new WholesaleTemplate(sheet, new GreyStyleSet(sheet.getWorkbook()), top);
    }

    @Override
    protected ArrivalTemplate initializeArrivedItemTemplate(Sheet sheet, AdvancedTop top) {
        return new ArrivalWholesaleTemplate(sheet, new ArrivalStyleSet(sheet.getWorkbook()), top);
    }

}
