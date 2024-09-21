package com.gmail.dev.zhilin.pricelistbuilder.comparators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.gmail.dev.zhilin.pricelistbuilder.models.Item;

public class ItemBySubsection implements Comparator<Item> {

    private static final List<String> LAST_SUBSECTIONS = new ArrayList<>(Arrays.asList("Прочие запчасти двигателя",
            "Прочие запчасти трансмиссии", "Прочие запчасти подвески", "Прочие запчасти рулевого управления",
            "Прочие запчасти тормозной системы", "Прочие запчасти топливной системы",
            "Прочие запчасти выхлопной системы", "Прочие запчасти электрооборудования",
            "Прочие запчасти кузова и интерьера", "Прочие запчасти", "Прочие запчасти для спецтехники"));

    @Override
    public int compare(Item o1, Item o2) {
        if (LAST_SUBSECTIONS.contains(o1.getSubsection()) && LAST_SUBSECTIONS.contains(o2.getSubsection())) {
            int o1Arg = LAST_SUBSECTIONS.indexOf(o1.getSubsection());
            int o2Arg = LAST_SUBSECTIONS.indexOf(o2.getSubsection());

            if (o1Arg < o2Arg)
                return -1;

            if (o1Arg > o2Arg)
                return 1;

            return 0;
        }

        if (LAST_SUBSECTIONS.contains(o1.getSubsection()))
            return 1;

        if (LAST_SUBSECTIONS.contains(o2.getSubsection()))
            return -1;

        return o1.getSubsection().compareTo(o2.getSubsection());
    }

}
