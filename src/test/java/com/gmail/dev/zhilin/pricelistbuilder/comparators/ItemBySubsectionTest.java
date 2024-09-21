package com.gmail.dev.zhilin.pricelistbuilder.comparators;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gmail.dev.zhilin.pricelistbuilder.models.Item;

class ItemBySubsectionTest {

    private static final List<String> RANDOM_ORDER = new ArrayList<>(Arrays.asList("Прочие запчасти топливной системы",
            "Прочие запчасти выхлопной системы", "Прочие запчасти двигателя", "Прочие запчасти подвески",
            "Прочие запчасти трансмиссии", "Прочие запчасти", "Прочие запчасти тормозной системы",
            "Прочие запчасти электрооборудования", "Прочие запчасти для спецтехники",
            "Прочие запчасти кузова и интерьера", "Прочие запчасти рулевого управления"));
    private static final List<String> RIGHT_ORDER = new ArrayList<>(Arrays.asList("Прочие запчасти двигателя",
            "Прочие запчасти трансмиссии", "Прочие запчасти подвески", "Прочие запчасти рулевого управления",
            "Прочие запчасти тормозной системы", "Прочие запчасти топливной системы",
            "Прочие запчасти выхлопной системы", "Прочие запчасти электрооборудования",
            "Прочие запчасти кузова и интерьера", "Прочие запчасти", "Прочие запчасти для спецтехники"));
    private static List<Item> RIGHT_ORDER_ITEMS;
    private List<Item> randomOrderItems;

    @BeforeAll
    static void beforeAll() {
        RIGHT_ORDER_ITEMS = new ArrayList<>();
        Item item;
        
        for (int i = 0; i < RIGHT_ORDER.size(); i++) {
            item = new Item();
            
            item.setCode(Integer.toString(i));
            item.setSubsection(RIGHT_ORDER.get(i));
            RIGHT_ORDER_ITEMS.add(item);
        }
    }
    
    @BeforeEach
    void beforeEach() {
        randomOrderItems = new ArrayList<>();
        Item item;

        for (int i = 0; i < RANDOM_ORDER.size(); i++) {
            item = new Item();
            
            item.setCode(Integer.toString(RIGHT_ORDER.indexOf(RANDOM_ORDER.get(i))));
            item.setSubsection(RANDOM_ORDER.get(i));
            randomOrderItems.add(item);
        }
    }

    @Test
    void compare_rightOrder() {
        Collections.sort(randomOrderItems, new ItemBySubsection());

        assertEquals(randomOrderItems, RIGHT_ORDER_ITEMS);
    }

    @Test
    void compare_greaterThan() {
        Item itemOne = new Item();
        Item itemTwo = new Item();
        
        itemOne.setSubsection("Прочие запчасти подвески");
        itemTwo.setSubsection("Название не из списка");
        
        assertEquals(new ItemBySubsection().compare(itemOne, itemTwo), 1);
    }

    @Test
    void compare_lessThan() {
        Item itemOne = new Item();
        Item itemTwo = new Item();
        
        itemOne.setSubsection("Название не из списка");
        itemTwo.setSubsection("Прочие запчасти подвески");

        assertEquals(new ItemBySubsection().compare(itemOne, itemTwo), -1);
    }
    
}
