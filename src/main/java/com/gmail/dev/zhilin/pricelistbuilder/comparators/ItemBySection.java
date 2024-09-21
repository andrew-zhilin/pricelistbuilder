package com.gmail.dev.zhilin.pricelistbuilder.comparators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import com.gmail.dev.zhilin.pricelistbuilder.models.Item;

public class ItemBySection implements Comparator<Item> {

    private static final List<String> SECTION_ORDER = new ArrayList<>(Arrays.asList("Двигатель", "Трансмиссия",
            "Подвеска", "Рулевое управление", "Тормозная система", "Топливная система", "Выхлопная система",
            "Электрооборудование", "Кузов и интерьер", "Колеса и компоненты", "Прочее", "Спецтехника"));

    @Override
    public int compare(Item o1, Item o2) {
        if (SECTION_ORDER.contains(o1.getSection()) && SECTION_ORDER.contains(o2.getSection())) {
            int o1Arg = SECTION_ORDER.indexOf(o1.getSection());
            int o2Arg = SECTION_ORDER.indexOf(o2.getSection());

            if (o1Arg < o2Arg)
                return -1;

            if (o1Arg > o2Arg)
                return 1;

            return 0;
        }

        if (SECTION_ORDER.contains(o1.getSection()))
            return 1;

        if (SECTION_ORDER.contains(o2.getSection()))
            return -1;

        return o1.getSection().compareTo(o2.getSection());
    }

}
