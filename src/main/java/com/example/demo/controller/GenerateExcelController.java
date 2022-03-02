package com.example.demo.controller;

import com.example.demo.service.GenerateExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

@Controller
public class GenerateExcelController {

    @Autowired
    private GenerateExcelService generateExcelService;


    @RequestMapping("/generate")
    @ResponseBody
    public String generateExcel(){
        System.out.println("excel generated successfully");

        /**************** Product Information **********************/
        HashMap<String, ArrayList<String>> categoryAndSubCategory = new HashMap<>();
        ArrayList<String> brandNameList = new ArrayList<>();
        ArrayList<String> countryOfOriginList = new ArrayList<>();
        ArrayList<String> allergenList = new ArrayList<>();
        ArrayList<String> foodTypeList = new ArrayList<>();

        categoryAndSubCategory.put("countries", new ArrayList<>(Arrays.asList("india", "canada", "Germany")));
        categoryAndSubCategory.put("vegetables", new ArrayList<>(Arrays.asList("potato", "tomato", "carrot")));
        categoryAndSubCategory.put("fruits", new ArrayList<>(Arrays.asList("banana", "apple", "grapes")));

        brandNameList.addAll(Arrays.asList("Brand-A","Brand-B","Brand-C"));
        countryOfOriginList.addAll(Arrays.asList("Country - A","Country - B", "Country - C"));
        allergenList.addAll(Arrays.asList("allergen - A", "allergen - B","allergen - C"));
        foodTypeList.addAll(Arrays.asList("Food - A","Food - B","Food - c"));

        /******************** Inventory & Pricing *********************/
        ArrayList<String> bypassInventory = new ArrayList<>();
        ArrayList<String> unit = new ArrayList<>();

        bypassInventory.addAll(Arrays.asList("Bypass - A","Bypass - B","Bypass - C"));
        unit.addAll(Arrays.asList("Unit - A","Unit - B", "Unit - C"));

        /********************* pass all the data and get the generated excel at path ******************/
        generateExcelService.passDropDownDataAndGenerateExcel(categoryAndSubCategory,brandNameList,countryOfOriginList
                ,allergenList,foodTypeList,bypassInventory,unit);

        return "Excel Generated Successfully";
    }

}
