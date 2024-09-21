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

class ItemBySectionTest {

    private static final List<String> RANDOM_ORDER = new ArrayList<>(Arrays.asList("Трансмиссия", "Подвеска",
            "Рулевое управление", "Топливная система", "Тормозная система", "Электрооборудование", "Двигатель",
            "Спецтехника", "Выхлопная система", "Прочее", "Колеса и компоненты", "Кузов и интерьер"));
    private static final List<String> RIGHT_ORDER = new ArrayList<>(Arrays.asList("Двигатель", "Трансмиссия",
            "Подвеска", "Рулевое управление", "Тормозная система", "Топливная система", "Выхлопная система",
            "Электрооборудование", "Кузов и интерьер", "Колеса и компоненты", "Прочее", "Спецтехника"));
    private static List<Item> RIGHT_ORDER_ITEMS;
    private List<Item> randomOrderItems;

    @BeforeAll
    static void beforeAll() {
        RIGHT_ORDER_ITEMS = new ArrayList<>();
        Item item;
        
        for (int i = 0; i < RIGHT_ORDER.size(); i++) {
            item = new Item();
            
            item.setCode(Integer.toString(i));
            item.setSection(RIGHT_ORDER.get(i));
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
            item.setSection(RANDOM_ORDER.get(i));
            randomOrderItems.add(item);
        }
    }

    @Test
    void compare_rightOrder() {
        Collections.sort(randomOrderItems, new ItemBySection());

        assertEquals(randomOrderItems, RIGHT_ORDER_ITEMS);
    }

    @Test
    void compare_greaterThan() {
        Item itemOne = new Item();
        Item itemTwo = new Item();
        
        itemOne.setSection("Двигатель");
        itemTwo.setSection("Название не из списка");
        
        assertEquals(new ItemBySection().compare(itemOne, itemTwo), 1);
    }

    @Test
    void compare_lessThan() {
        Item itemOne = new Item();
        Item itemTwo = new Item();
        
        itemOne.setSection("Название не из списка");
        itemTwo.setSection("Двигатель");

        assertEquals(new ItemBySection().compare(itemOne, itemTwo), -1);
    }

}
