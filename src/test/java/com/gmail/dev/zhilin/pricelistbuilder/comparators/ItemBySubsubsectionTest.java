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

class ItemBySubsubsectionTest {

    private static final List<String> RANDOM_ORDER = new ArrayList<>(Arrays.asList(
            // Трансмиссия
            "Другие валы трансмиссии", "Прочие муфты трансмиссии", "Прочие подшипники трансмиссии",
            "Прочие ремкомплекты трансмиссии", "Прочие тросы трансмиссии",
            // Прочее
            "Прочие компоненты седельно-сцепных устройств", "Прочие соединители",
            // Рулевое управление
            "Прочие компоненты гидроусилителей руля", "Прочие ремкомплекты рулевого управления",
            // Выхлопная система
            "Прочие компоненты системы EGR",
            // Двигатель
            "Прочие компоненты валов двигателя", "Прочие компоненты горных тормозов", "Прочие компоненты маховиков",
            "Прочие насосы двигателя", "Прочие компоненты поршневой группы", "Прочие прокладки двигателя",
            "Прочие компоненты радиаторов, интеркулеров", "Прочие сальники двигателя", "Прочие тросы двигателя",
            "Прочие компоненты турбокомпрессоров", "Прочие фильтры двигателя",
            // Тормозная система
            "Прочие компоненты тормозных колодок", "Компоненты компрессоров",
            "Прочие компоненты масловлагоотделителей, осушителей", "Прочие ремкомплекты тормозной системы",
            "Прочие компоненты ручного (стояночного) тормоза", "Прочие компоненты суппортов тормозных",
            "Прочие усилители тормозной системы",
            // Спецтехника
            "Другие фильтры для спецтехники",
            // Топливная система
            "Прочие компоненты топливных насосов", "Прочие компоненты топливных форсунок",
            // Кузов и интерьер
            "Прочие компоненты зеркал", "Прочие компоненты оптики", "Прочие компоненты системы отопления",
            // Электрооборудование
            "Прочие компоненты генераторов", "Прочие датчики", "Прочие моторы", "Прочие реле",
            // Подвеска
            "Прочие компоненты балансира рессор", "Прочие болты подвески", "Прочие втулки, полувтулки подвески",
            "Прочие кронштейны подвески", "Прочие отбойники подвески", "Прочие ремкомплекты подвески",
            "Прочие сайлентблоки подвески", "Прочие сальники подвески", "Прочие компоненты тяг, рычагов подвески",
            // Колеса и компоненты
            "Прочие компоненты колесных дисков", "Прочие компоненты ступицы"));
    private static final List<String> RIGHT_ORDER = new ArrayList<>(Arrays.asList(
            // Двигатель
            "Прочие компоненты валов двигателя", "Прочие компоненты горных тормозов", "Прочие компоненты маховиков",
            "Прочие насосы двигателя", "Прочие компоненты поршневой группы", "Прочие прокладки двигателя",
            "Прочие компоненты радиаторов, интеркулеров", "Прочие сальники двигателя", "Прочие тросы двигателя",
            "Прочие компоненты турбокомпрессоров", "Прочие фильтры двигателя",
            // Трансмиссия
            "Другие валы трансмиссии", "Прочие муфты трансмиссии", "Прочие подшипники трансмиссии",
            "Прочие ремкомплекты трансмиссии", "Прочие тросы трансмиссии",
            // Подвеска
            "Прочие компоненты балансира рессор", "Прочие болты подвески", "Прочие втулки, полувтулки подвески",
            "Прочие кронштейны подвески", "Прочие отбойники подвески", "Прочие ремкомплекты подвески",
            "Прочие сайлентблоки подвески", "Прочие сальники подвески", "Прочие компоненты тяг, рычагов подвески",
            // Рулевое управление
            "Прочие компоненты гидроусилителей руля", "Прочие ремкомплекты рулевого управления",
            // Тормозная система
            "Прочие компоненты тормозных колодок", "Компоненты компрессоров",
            "Прочие компоненты масловлагоотделителей, осушителей", "Прочие ремкомплекты тормозной системы",
            "Прочие компоненты ручного (стояночного) тормоза", "Прочие компоненты суппортов тормозных",
            "Прочие усилители тормозной системы",
            // Топливная система
            "Прочие компоненты топливных насосов", "Прочие компоненты топливных форсунок",
            // Выхлопная система
            "Прочие компоненты системы EGR",
            // Электрооборудование
            "Прочие компоненты генераторов", "Прочие датчики", "Прочие моторы", "Прочие реле",
            // Кузов и интерьер
            "Прочие компоненты зеркал", "Прочие компоненты оптики", "Прочие компоненты системы отопления",
            // Колеса и компоненты
            "Прочие компоненты колесных дисков", "Прочие компоненты ступицы",
            // Прочее
            "Прочие компоненты седельно-сцепных устройств", "Прочие соединители",
            // Спецтехника
            "Другие фильтры для спецтехники"));
    private static List<Item> RIGHT_ORDER_ITEMS;
    private List<Item> randomOrderItems;

    @BeforeAll
    static void beforeAll() {
        RIGHT_ORDER_ITEMS = new ArrayList<>();
        Item item;
        
        for (int i = 0; i < RIGHT_ORDER.size(); i++) {
            item = new Item();
            
            item.setCode(Integer.toString(i));
            item.setSubsubsection(RIGHT_ORDER.get(i));
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
            item.setSubsubsection(RANDOM_ORDER.get(i));
            randomOrderItems.add(item);
        }
    }

    @Test
    void compare_rightOrder() {
        Collections.sort(randomOrderItems, new ItemBySubsubsection());

        assertEquals(randomOrderItems, RIGHT_ORDER_ITEMS);
    }
    
    @Test
    void compare_greaterThan() {
        Item itemOne = new Item();
        Item itemTwo = new Item();
        
        itemOne.setSubsubsection("Прочие компоненты валов двигателя");
        itemTwo.setSubsubsection("Название не из списка");
        
        assertEquals(new ItemBySubsubsection().compare(itemOne, itemTwo), 1);
    }

    @Test
    void compare_lessThan() {
        Item itemOne = new Item();
        Item itemTwo = new Item();
        
        itemOne.setSubsubsection("Название не из списка");
        itemTwo.setSubsubsection("Прочие компоненты валов двигателя");

        assertEquals(new ItemBySubsubsection().compare(itemOne, itemTwo), -1);
    }

    @Test
    void compare_firstIsNull_equal() {
        Item itemOne = new Item();
        Item itemTwo = new Item();
        
        itemOne.setSubsubsection(null);
        itemTwo.setSubsubsection("Прочие компоненты валов двигателя");
        
        assertEquals(new ItemBySubsubsection().compare(itemOne, itemTwo), 0);
    }
    
    @Test
    void compare_secondIsNull_equal() {
        Item itemOne = new Item();
        Item itemTwo = new Item();
        
        itemOne.setSubsubsection("Прочие компоненты валов двигателя");
        itemTwo.setSubsubsection(null);
        
        assertEquals(new ItemBySubsubsection().compare(itemOne, itemTwo), 0);
    }

    @Test
    void compare_bothAreNull_equal() {
        Item itemOne = new Item();
        Item itemTwo = new Item();
        
        itemOne.setSubsection(null);
        itemTwo.setSubsection(null);
        
        assertEquals(new ItemBySubsubsection().compare(itemOne, itemTwo), 0);
    }
}
