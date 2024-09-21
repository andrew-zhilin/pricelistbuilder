package com.gmail.dev.zhilin.pricelistbuilder.models;

import com.gmail.dev.zhilin.pricelistbuilder.enums.Truck;
import com.gmail.dev.zhilin.pricelistbuilder.enums.Warehouse;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Item implements Cloneable {

    private String section;
    private String subsection;
    private String subsubsection;
    private String code;
    private String manufacturer;
    private String partNumber;
    private String crossReference;
    private Set<Truck> application;
    private String name;
    private String link;
    private int retailPrice;
    private int wholesalePrice;
    private int regionalWholesalePrice;
    private Map<Warehouse, Integer> stock;
    private String measure;

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getSubsection() {
        return subsection;
    }

    public void setSubsection(String subsection) {
        this.subsection = subsection;
    }

    public String getSubsubsection() {
        return subsubsection;
    }

    public void setSubsubsection(String subsubsection) {
        this.subsubsection = subsubsection;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getCrossReference() {
        return crossReference;
    }

    public void setCrossReference(String crossReference) {
        this.crossReference = crossReference;
    }

    public Set<Truck> getApplication() {
        return application;
    }

    public void setApplication(Set<Truck> application) {
        this.application = application;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(int retailPrice) {
        this.retailPrice = retailPrice;
    }

    public int getWholesalePrice() {
        return wholesalePrice;
    }

    public void setWholesalePrice(int wholesalePrice) {
        this.wholesalePrice = wholesalePrice;
    }

    public int getRegionalWholesalePrice() {
        return regionalWholesalePrice;
    }

    public void setRegionalWholesalePrice(int regionalWholesalePrice) {
        this.regionalWholesalePrice = regionalWholesalePrice;
    }

    public Map<Warehouse, Integer> getStock() {
        return stock;
    }

    public void setStock(Map<Warehouse, Integer> stock) {
        this.stock = stock;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Item other = (Item) obj;
        return Objects.equals(code, other.code);
    }

    @Override
    public String toString() {
        return "Item [code=" + code + ", name=" + name + "]";
    }

    @Override
    public Object clone() {
        Item item = new Item();

        item.setSection(this.section);
        item.setSubsection(this.subsection);
        item.setSubsubsection(this.subsubsection);
        item.setCode(this.code);
        item.setManufacturer(this.manufacturer);
        item.setPartNumber(this.partNumber);
        item.setCrossReference(this.crossReference);

        if (this.application != null)
            item.setApplication(new HashSet<Truck>(this.application));

        item.setName(this.name);
        item.setLink(this.link);
        item.setRetailPrice(this.retailPrice);
        item.setWholesalePrice(this.wholesalePrice);
        item.setRegionalWholesalePrice(this.regionalWholesalePrice);

        if (this.stock != null)
            item.setStock(new HashMap<Warehouse, Integer>(this.stock));

        item.setMeasure(this.measure);

        return item;
    }

    public boolean sameFolder(Item item) {
        if (!this.section.equals(item.getSection()))
            return false;

        if (!this.subsection.equals(item.getSubsection()))
            return false;

        if (this.subsubsection == null && item.getSubsubsection() != null)
            return false;

        if (this.subsubsection != null && item.getSubsubsection() == null)
            return false;

        if (this.subsubsection == null && item.getSubsubsection() == null)
            return true;

        return this.subsubsection.equals(item.getSubsubsection());
    }

}
