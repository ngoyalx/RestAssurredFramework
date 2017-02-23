package com;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import org.json.JSONException;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Features;
import com.jayway.restassured.builder.RequestSpecBuilder;
import com.jayway.restassured.specification.RequestSpecification;
import common.Actions;
import common.Property;
import common.Utility;

    public class RestAssurred{
    	    	
    	public static RequestSpecification requestSpec; 
    	private List<String[]> testdata;
    	private Properties objprop;
    	Actions action;
    	Utility utility;
    	Property property;
    	
    	    	
    	public RestAssurred() throws Exception{
    	property = new Property();
    	utility = new Utility(property);  
    	objprop = new Properties();
       	}
    	
    @BeforeTest
    public void setup() throws FileNotFoundException, IOException{
    	try{
    		//load config.properties file
    		objprop.load(new FileInputStream(property.configfileLocation));
    		
    		//populate config.properties file in hashmap
    		utility.populatePropertiesinHashMap(objprop);
    		
    		property.authenticationtype = utility.getkeyvaluefromHashMap("APIauthenticationtype");
    		property.authenticationvalue = utility.getkeyvaluefromHashMap("APIauthenticationvalue");
    		property.apiurl = utility.getkeyvaluefromHashMap("APIURL");
    		
    		//preparing request headers for the apis
    		RequestSpecBuilder builder = new RequestSpecBuilder();
     		builder.addHeader("Authorization", property.authenticationtype + " " + property.authenticationvalue);
     		builder.addHeader("Accept", "application/json");
     		requestSpec = builder.build();
     		requestSpec.baseUri(property.apiurl);
    		}catch(Exception e){
    			e.printStackTrace();
    		}
    	}
    	
    
    @Test(dataProvider="getdata")
    @Features("Test feature")
    @Description("Check POST, GET and DELETE")
    public void apitest(String testcaseid, String step, String action, String data) throws FileNotFoundException, JSONException {
    	
    	this.action = new Actions();
    	if(!data.equalsIgnoreCase("") && action.equalsIgnoreCase("postRequest")){
    	String[] pathcomponents = data.split(property.testdataSeperator);
    	String postURL = pathcomponents[0];
    	String apitype = pathcomponents[1];
    	String contentitemtype = pathcomponents[2];
    	String filename = pathcomponents[3];
    	int poststatuscode = Integer.parseInt(pathcomponents[4]);
    	
    	
    	String filepath = "src" + property.FileSeperator.get() + "main" + property.FileSeperator.get() + "resources" + property.FileSeperator.get()
				+ "api" + property.FileSeperator.get() + apitype + property.FileSeperator.get() +
				"input" + property.FileSeperator.get() + contentitemtype + property.FileSeperator.get() + filename;
    	
    	this.action.post(postURL,filepath,contentitemtype,poststatuscode);
    	}
    	
    	if(!data.equalsIgnoreCase("") && action.equalsIgnoreCase("getRequest")){
    		String[] getcomponents = data.split(property.testdataSeperator);
    		String geturl = getcomponents[0];
    		int getstatuscode = Integer.parseInt(getcomponents[1]);
    		this.action.get(geturl,getstatuscode);
    	}
    	
    	if(!data.equalsIgnoreCase("")&& action.equalsIgnoreCase("deleteRequest")){
    		String[] deletecomponents = data.split(property.testdataSeperator);
    		String deleteurl = deletecomponents[0];
    		int deletestatuscode = Integer.parseInt(deletecomponents[1]);
    		this.action.delete(deleteurl,deletestatuscode);
    	}
    		
    	}
    
    @DataProvider(name = "getdata")
    public Object[][] getdata() throws Exception{
    	
    	testdata = utility.getRequiredRows(property.testcasefileLocation);
    	System.out.println(testdata.size());
    	Object[][] data = new Object[testdata.size()-1][4];
    	for(int i=1,j=0; i<testdata.size(); i++,j++){
    		String [] line = testdata.get(i);
    			for(int k=0;k<line.length;k++){
    				data[j][k] = line[k];	
    			}
    			System.out.println();
    		}
    	return data;
    }
    
    

  }