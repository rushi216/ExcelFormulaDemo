#Excel Generation Procedure

- Set generation path in application.properties file,
in a parameter generate.excel.path .

- Currently drop-down row limit is 10000, one can change it
by setting value in MAX_ROWS_WITH_DROPDOWN in GenerateExcelService.java

- Currently source excel file is in /resources/ folder, with name
  "Bulk Products Import - final.xlsx". One can change it for variation in source excel. like
  guidelines, etc.
  
- Currently excel file can be generated via calling with api endpoint {server:port}/generate. The mapping is created in
GenerateExcelController

- GenerateExcelService is having one method called passDropDownDataAndGenerateExcel({seeddata}). where seeddata should contain required hashmap and  arraylist with string for generating excel.
  one can see the method and get idea. for the reference point of view /generate mapping has the same logic written.
  
   
  
  
 

