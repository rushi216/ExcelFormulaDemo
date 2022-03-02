package com.example.demo.dtos;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ProductInformationDropDownDataContainer {

    /*********************** for product information ******************************************/
    private HashMap<String,ArrayList<String>> categoryAndSubcategoryMap = new HashMap<>();

    private ArrayList<String> brandNameList = new ArrayList<>();

    private ArrayList<String> countryOfOriginList = new ArrayList<>();

    private ArrayList<String> allergenList = new ArrayList<>();

    private ArrayList<String> foodTypeListList = new ArrayList<>();

    /********************** for Inventory and pricing ******************************************/
    private ArrayList<String> bypassInventory = new ArrayList<>();

    private ArrayList<String> unit = new ArrayList<>();


    public ProductInformationDropDownDataContainer(HashMap<String, ArrayList<String>> categoryAndSubcategoryMap,
                                                   ArrayList<String> brandNameList, ArrayList<String> countryOfOriginList,
                                                   ArrayList<String> allergenList,
                                                   ArrayList<String> foodTypeListList, ArrayList<String> bypassInventory,
                                                   ArrayList<String> unit) {
        this.categoryAndSubcategoryMap = categoryAndSubcategoryMap;
        this.brandNameList = brandNameList;
        this.countryOfOriginList = countryOfOriginList;
        this.allergenList = allergenList;
        this.foodTypeListList = foodTypeListList;
        this.bypassInventory = bypassInventory;
        this.unit = unit;
    }

    public ArrayList<String> getBypassInventory() {
        return bypassInventory;
    }

    public void setBypassInventory(ArrayList<String> bypassInventory) {
        this.bypassInventory = bypassInventory;
    }

    public ArrayList<String> getUnit() {
        return unit;
    }

    public void setUnit(ArrayList<String> unit) {
        this.unit = unit;
    }

    public HashMap<String, ArrayList<String>> getCategoryAndSubcategoryMap() {
        return categoryAndSubcategoryMap;
    }

    public void setCategoryAndSubcategoryMap(HashMap<String, ArrayList<String>> categoryAndSubcategoryMap) {
        this.categoryAndSubcategoryMap = categoryAndSubcategoryMap;
    }

    public ArrayList<String> getBrandNameList() {
        return brandNameList;
    }

    public void setBrandNameList(ArrayList<String> brandNameList) {
        this.brandNameList = brandNameList;
    }

    public ArrayList<String> getCountryOfOriginList() {
        return countryOfOriginList;
    }

    public void setCountryOfOriginList(ArrayList<String> countryOfOriginList) {
        this.countryOfOriginList = countryOfOriginList;
    }

    public ArrayList<String> getAllergenList() {
        return allergenList;
    }

    public void setAllergenList(ArrayList<String> allergenList) {
        this.allergenList = allergenList;
    }

    public ArrayList<String> getFoodTypeListList() {
        return foodTypeListList;
    }

    public void setFoodTypeListList(ArrayList<String> foodTypeListList) {
        this.foodTypeListList = foodTypeListList;
    }
}
