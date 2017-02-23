package common;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import org.json.JSONException;
import org.json.JSONObject;
import com.RestAssurred;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.ValidatableResponse;
import com.jayway.restassured.specification.RequestSpecification;
import static com.jayway.restassured.RestAssured.given;




public class Actions {
	
	private String slug = "";
	private JSONObject completeJsonObjOfInputJson;
	private RequestSpecification requestspec;
	
	public Actions(){
		this.requestspec = RestAssurred.requestSpec;
	}
	
	
	public void post(String posturl, String path, String contentitemtype,int poststatuscode) throws FileNotFoundException, JSONException{
	    try{	
		Scanner scanner = new Scanner(new File(path));
	 		String next = scanner.useDelimiter("\\Z").next();
	 		scanner.close();
	 		System.out.println(next);
	 		completeJsonObjOfInputJson = new JSONObject(next);

	 		  ValidatableResponse res = 
	 		  given().spec(requestspec)
	         .body(completeJsonObjOfInputJson.toString())
	         .contentType(ContentType.JSON)
	         .when()
	         .post(posturl)
	         .then()
	         .statusCode(poststatuscode);
	 		 
	 		 String output = res.extract().body().asString();
	 		 System.out.println(output);	
	 		  
	 		 JSONObject responseJSON = new JSONObject(output);
	 		 JSONObject contentitem = responseJSON.getJSONObject(contentitemtype);
	 		 slug = contentitem.get("contentitem_id").toString();
	 		 System.out.println(slug);
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	    }
	
	 public void get(String geturl ,int getstatuscode){
    	 try{
    		 ValidatableResponse getres = 
       	    	  given(requestspec)
       	        /* .headers("Authorization","bearer 11lh79o86gaekvc141mfojfjt3183llpiben")
       	         .headers("Accept","application/json")*/
       	         .when()
       	         .get(geturl+slug)
       	         .then()
       	         .statusCode(getstatuscode);
       	    	 System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
       	    	 System.out.println(getres.extract().body().asString());
    	 	}catch(Exception e){
    	 		e.printStackTrace();
    	 	}
		 	
    }
    
    public void delete(String deleteurl,int deletestatuscode){
    	try{
    		given(requestspec)
            .when()
            .delete(deleteurl+slug)
            .then()
            .statusCode(deletestatuscode);	
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	 
    }
    
	

}
