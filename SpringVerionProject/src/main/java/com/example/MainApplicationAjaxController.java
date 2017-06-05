package com.example;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Engine.ASCIIFile;
import com.example.Engine.PropertiesReader;
import com.example.Engine.RepositoryChecker;


@RestController	
public class MainApplicationAjaxController {
	
	List<ASCIIFile> asciiList;
	
	@RequestMapping(value = "/getResult", method = RequestMethod.POST)
    public Response postCustomer(@RequestBody String file) {
		file = getProperString(file);
		List<File> FileOut= new ArrayList<>();
		try {
			PropertiesReader.readProperties();
			String default_path = System.getProperty("user.dir")+PropertiesReader.getProperties().get("REPOSITORY_PATH");
			FileOut = RepositoryChecker.filterRepository(default_path, file.toString(),FileOut);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		asciiList = new ArrayList<>();
		if(FileOut.size() >0){
			try {
				for (File asciiFile : FileOut) {
					asciiList.add(ASCIIFile.readASCIIFile(asciiFile));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
        Response response = new Response("Done", asciiList);
        return response;
    }

	
	
	
	public String getProperString(String param){
		StringTokenizer token = new StringTokenizer(param, ":");
		String finalString = null;
		while(token.hasMoreTokens()){
			finalString = token.nextToken();
		}
		
		return finalString.replace("}", "").replaceAll("\"", "");
	}

}

class Response {
    private String status;
    private Object data;
     
    public Response(){
         
    }
     
    public Response(String status, Object data){
        this.status = status;
        this.data = data;
    }
 
    public String getStatus() {
        return status;
    }
 
    public void setStatus(String status) {
        this.status = status;
    }
 
    public Object getData() {
        return data;
    }
 
    public void setData(Object data) {
        this.data = data;
    }
}