package common;

import java.util.HashMap;

public class Property {
	
	public String testcasefileLocation;
	public String configfileLocation;
	public ThreadLocal<String> FileSeperator;
	public String authenticationtype;
	public String authenticationvalue;
	public String apiurl;
	public HashMap<String, String> globalhashmap;
	public String testdataSeperator;
	
	public HashMap<String,String> getglobalhashmap(){
		return globalhashmap;
	}
	
	
	public Property(){
		
			FileSeperator = new ThreadLocal<String>() {
		    @Override
		    protected String initialValue() {
			return System.getProperty("file.separator");
		    }
		};
		
		globalhashmap = new HashMap<String, String>();
		
		this.testcasefileLocation = "src" + FileSeperator.get() + "main" + FileSeperator.get() + "resources" + FileSeperator.get()
				+ "TestCase" + FileSeperator.get() + "REST_Services_Automation.csv";
		
		this.configfileLocation = "src" + FileSeperator.get() + "main" + FileSeperator.get() + "resources" + FileSeperator.get()
				+ "config.properties";
		
		this.testdataSeperator = "#";
		
	}
}


