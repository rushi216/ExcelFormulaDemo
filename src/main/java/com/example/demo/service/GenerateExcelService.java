package com.example.demo.service;

import com.example.demo.dtos.ProductInformationDropDownDataContainer;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class GenerateExcelService {

    @Value("${generate.excel.path}")
    private String path;

    private static final Integer MAX_ROWS_WITH_DROPDOWN = 10000;

    public void passDropDownDataAndGenerateExcel(HashMap<String, ArrayList<String>> categoryAndSubcategoryMap
                                                 , ArrayList<String> brandNameList, ArrayList<String> countryOfOriginList,
                                                 ArrayList<String> allergenList, ArrayList<String> foodTypeListList,
                                                 ArrayList<String> bypassInventory, ArrayList<String> unit){

        ProductInformationDropDownDataContainer productInformationDropDownDataContainer
                = new ProductInformationDropDownDataContainer(categoryAndSubcategoryMap, brandNameList,
                countryOfOriginList, allergenList, foodTypeListList,bypassInventory,unit);

        generateExcel(productInformationDropDownDataContainer);

    }

    private void generateExcel(ProductInformationDropDownDataContainer productInformationDropDownDataContainer){
        boolean isGeneratedSuccessfully = false;

        Workbook workbook = null;
        InputStream is;
        try {
            is = new ClassPathResource("Bulk Products Import - final.xlsx").getInputStream();
            workbook = new XSSFWorkbook(is);
        } catch (IOException e) {
            e.printStackTrace();
        }


        createHiddenSheetForDependentDropDownInProductInformation(productInformationDropDownDataContainer.getCategoryAndSubcategoryMap(),
                workbook);
        Sheet productInformationSheet;

        //visible data productInformationSheet
        productInformationSheet = workbook.getSheet("Product Information");

        createHeadersForProductInformation(productInformationSheet);
        try {
            setFirstRowBoldAndCenter(productInformationSheet, workbook);
        } catch (IOException e) {
            e.printStackTrace();
        }
        productInformationSheet.setActiveCell(new CellAddress("B2"));


        //data validations
        DataValidationHelper dvHelper = productInformationSheet.getDataValidationHelper();


        //data validation for categories in B2:
        DataValidationConstraint dvConstraint = dvHelper.createFormulaListConstraint("ListSheet!$A$1:$C$1");
        
        CellRangeAddressList addressList = new CellRangeAddressList(1, MAX_ROWS_WITH_DROPDOWN-1, 1, 1);
        DataValidation validation = dvHelper.createValidation(dvConstraint, addressList);
        productInformationSheet.addValidationData(validation);


        for (int i = 2; i <= MAX_ROWS_WITH_DROPDOWN; ++i) {
            //data validation for items of the selected category in B2:
            
            String formulaString = "=INDIRECT(SUBSTITUTE(SUBSTITUTE($B$" + i + ",\" \",\"_\"), \",\", \"_\"))";

            dvConstraint = dvHelper.createFormulaListConstraint(formulaString);
            addressList = new CellRangeAddressList(i-1, i-1, 2, 2);
            validation = dvHelper.createValidation(dvConstraint, addressList);
            productInformationSheet.addValidationData(validation);
        }

        // column : 3 brand name
        CellRangeAddressList brandNameListCellRangeAddress = new CellRangeAddressList(1,MAX_ROWS_WITH_DROPDOWN-1,3,3);
        DataValidationConstraint brandNameDataValidationConstraint = dvHelper.createExplicitListConstraint(
                productInformationDropDownDataContainer.getBrandNameList().toArray(new String[0]));
        DataValidation brandNameDataValidation = dvHelper.createValidation(brandNameDataValidationConstraint,brandNameListCellRangeAddress);
        productInformationSheet.addValidationData(brandNameDataValidation);


        // column : 4 country of origin
        CellRangeAddressList countryOfOriginListCellRangeAddress = new CellRangeAddressList(1,MAX_ROWS_WITH_DROPDOWN -1,4,4);
        DataValidationConstraint countryOfOriginDataValidationConstraint = dvHelper.createExplicitListConstraint(
                productInformationDropDownDataContainer.getCountryOfOriginList().toArray(new String[0]));
        DataValidation countryOfOriginDataValidation = dvHelper.createValidation(countryOfOriginDataValidationConstraint,countryOfOriginListCellRangeAddress);
        productInformationSheet.addValidationData(countryOfOriginDataValidation);

        // column : 6 allergen
        CellRangeAddressList allergenListCellRangeAddress = new CellRangeAddressList(1,MAX_ROWS_WITH_DROPDOWN - 1,6,6);
        DataValidationConstraint allergenDataValidationConstraint = dvHelper.createExplicitListConstraint(
                productInformationDropDownDataContainer.getAllergenList().toArray(new String[0]));
        DataValidation allergenDataValidation = dvHelper.createValidation(allergenDataValidationConstraint,allergenListCellRangeAddress);
        productInformationSheet.addValidationData(allergenDataValidation);

        // column : 7 food type
        CellRangeAddressList foodTypeListCellRangeAddress = new CellRangeAddressList(1,MAX_ROWS_WITH_DROPDOWN - 1,7,7);
        DataValidationConstraint foodTypeDataValidationConstraint = dvHelper.createExplicitListConstraint(
                productInformationDropDownDataContainer.getFoodTypeListList().toArray(new String[0]));
        DataValidation foodTypeDataValidation = dvHelper.createValidation(foodTypeDataValidationConstraint,foodTypeListCellRangeAddress);
        productInformationSheet.addValidationData(foodTypeDataValidation);


        /*------------------------------------------------------ Inventory & Pricing -------------------------------------------------*/
        Sheet inventoryAndPricingSheet;
        inventoryAndPricingSheet = workbook.getSheet("Inventory & Pricing");
        createHeadersForInventoryAndPricing(inventoryAndPricingSheet);
        try {
            setFirstRowBoldAndCenter(inventoryAndPricingSheet, workbook);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // column : 1 bypass Inventory
        CellRangeAddressList bypassInventoryListCellRangeAddress = new CellRangeAddressList(1,MAX_ROWS_WITH_DROPDOWN - 1,1,1);
        DataValidationConstraint bypassInventoryDataValidationConstraint = dvHelper.createExplicitListConstraint(
                productInformationDropDownDataContainer.getBypassInventory().toArray(new String[0]));
        DataValidation bypassInventoryDataValidation = dvHelper.createValidation(bypassInventoryDataValidationConstraint,bypassInventoryListCellRangeAddress);
        inventoryAndPricingSheet.addValidationData(bypassInventoryDataValidation);

        // column : 2 unit
        CellRangeAddressList unitListCellRangeAddress = new CellRangeAddressList(1,MAX_ROWS_WITH_DROPDOWN - 1,2,2);
        DataValidationConstraint unitDataValidationConstraint = dvHelper.createExplicitListConstraint(
                productInformationDropDownDataContainer.getUnit().toArray(new String[0]));
        DataValidation unitDataValidation = dvHelper.createValidation(unitDataValidationConstraint,unitListCellRangeAddress);
        inventoryAndPricingSheet.addValidationData(unitDataValidation);


        /******************* generate file ********************/
        //hide the ListSheet
        workbook.setSheetHidden(4, true);
        //set Sheet1 active
        workbook.setActiveSheet(1);
        try {
            FileOutputStream out = new FileOutputStream(path);
            workbook.write(out);
            workbook.close();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void createHeadersForInventoryAndPricing(Sheet sheet) {
        sheet.createRow(0).createCell(0).setCellValue("Product Name(Required)");
        sheet.getRow(0).createCell(1).setCellValue("Bypass Inventory(Required)");
        sheet.getRow(0).createCell(2).setCellValue("Unit(Required)");
        sheet.getRow(0).createCell(3).setCellValue("Stock Keeping Unit(Required)");
        sheet.getRow(0).createCell(4).setCellValue("Unit Cost Price(Required)");
        sheet.getRow(0).createCell(5).setCellValue("Unit Selling Price(Required)");
        sheet.getRow(0).createCell(6).setCellValue("Base Inventory Unit(Required)");
        sheet.getRow(0).createCell(7).setCellValue("Margin in Percentage(Required)");
        sheet.getRow(0).createCell(8).setCellValue("Minimum Order Quantity(Required)");
        sheet.getRow(0).createCell(9).setCellValue("Maximum Order Quantity");
    }

    private void setFirstRowBoldAndCenter(Sheet sheet, Workbook workbook) throws IOException {

        CellStyle style = workbook.createCellStyle();//Create style
        Font font = workbook.createFont();//Create font
        font.setBold(true);//Make font bold
        style.setFont(font);//set it to bold
        style.setAlignment(HorizontalAlignment.CENTER);
        Row row = sheet.getRow(0);
        for(int i = 0; i < row.getLastCellNum(); i++){//For each cell in the row
            row.getCell(i).setCellStyle(style);//Set the style
            sheet.autoSizeColumn(i);
        }
    }

    private void createHeadersForProductInformation(Sheet sheet) {
        sheet.createRow(0).createCell(0).setCellValue("Product Name(Required)");
        sheet.getRow(0).createCell(1).setCellValue("Category(Required)");
        sheet.getRow(0).createCell(2).setCellValue("Sub Category");
        sheet.getRow(0).createCell(3).setCellValue("Brand Name");
        sheet.getRow(0).createCell(4).setCellValue("Country of Origin");
        sheet.getRow(0).createCell(5).setCellValue("Shelf Life");
        sheet.getRow(0).createCell(6).setCellValue("Allergen");
        sheet.getRow(0).createCell(7).setCellValue("Food Type");
        sheet.getRow(0).createCell(8).setCellValue("License/FDA");
        sheet.getRow(0).createCell(9).setCellValue("Product Description");

    }

    private void createHiddenSheetForDependentDropDownInProductInformation(Map<String, ArrayList<String>> categoryItems, Workbook workbook) {
        //hidden sheet for list values
        Sheet sheet = workbook.createSheet("ListSheet");

        Row row;
        Name namedRange;
        String colLetter;
        String reference;

        int c = 0;
        //put the data in
        for (String key : categoryItems.keySet()) {
            int r = 0;
            row = sheet.getRow(r);
            if (row == null) row = sheet.createRow(r);
            r++;
            row.createCell(c).setCellValue(key);
            String[] items = new String[categoryItems.get(key).size()];
            items = categoryItems.get(key).toArray(items);
            for (String item : items) {
                row = sheet.getRow(r);
                if (row == null) row = sheet.createRow(r);
                r++;
                row.createCell(c).setCellValue(item);
            }
            //create names for the item list constraints, each named from the current key
            colLetter = CellReference.convertNumToColString(c);
            namedRange = workbook.createName();
            String newKey = key.replace(",", "_");
            newKey = newKey.replace(" ", "_");
            namedRange.setNameName(newKey);
            reference = "ListSheet!$" + colLetter + "$2:$" + colLetter + "$" + r;
            namedRange.setRefersToFormula(reference);
            c++;
        }

        //create name for Categories list constraint
        colLetter = CellReference.convertNumToColString((c-1));
        //namedRange = workbook.createName();
        //namedRange.setNameName("Categories");
        reference = "ListSheet!$A$1:$" + colLetter + "$1";
        //namedRange.setRefersToFormula(reference);

        //unselect that sheet because we will hide it later
        sheet.setSelected(false);
    }


}
