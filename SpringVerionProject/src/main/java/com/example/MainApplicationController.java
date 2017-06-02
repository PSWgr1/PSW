package com.example;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.Engine.ASCIIFile;
import com.example.Engine.PropertiesReader;
import com.example.Engine.RepositoryChecker;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class MainApplicationController {
	
	List<ASCIIFile> asciiList;
	
	@RequestMapping(value= "/", method=RequestMethod.GET)
	public String main(Model model){
		return "main";
	}
	
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
    public String getFiles(HttpServletRequest request, Model model) {
		List<File> FileOut= new ArrayList<>();
		String filename = request.getParameter("filename");
		try {
			PropertiesReader.readProperties();
			String default_path = System.getProperty("user.dir")+PropertiesReader.getProperties().get("REPOSITORY_PATH");
			FileOut = RepositoryChecker.filterRepository(default_path, filename,FileOut);
		} catch (IOException e) {
			e.printStackTrace();
		}
		model.addAttribute("files", FileOut);
		return "main";
    }
	
	@RequestMapping(value = "/result", method = RequestMethod.POST)
    public String getData(@RequestParam("CheckFileName") List<File> values ,HttpServletRequest request, Model model) {
		asciiList = new ArrayList<>();
			if(values.size() >0){
				try {
					for (File asciiFile : values) {
						asciiList.add(ASCIIFile.readASCIIFile(asciiFile));
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		if(asciiList.size() == 1){
			model.addAttribute("DataRow1",asciiList.get(0).HEADERS);
			model.addAttribute("DataRow2",asciiList.get(0).HEADERS);
		} else if(asciiList.size() == 2){
			model.addAttribute("DataRow1",new ArrayList<>());
			model.addAttribute("DataRow2",new ArrayList<>());
		}
		return "main";
    }
	
	@RequestMapping(value = "/charts", method = RequestMethod.POST)
	public String getChart(@RequestParam("DataRow1data") String XAsis, @RequestParam("DataRow2data") String YAsis ,HttpServletRequest request, Model model) {
		System.out.println(XAsis);
		System.out.println(YAsis);
		int indexOfXValue =1;
		int indexOfYValue =1;
		for (String string : asciiList.get(0).HEADERS) {
			if(string.equals(XAsis)){
				break;
			}
			indexOfXValue++;
		}
		for (String string : asciiList.get(0).HEADERS) {
			if(string.equals(YAsis)){
				break;
			}
			indexOfYValue++;
		}
		
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			List<Double> listX = asciiList.get(0).VALUES.get(indexOfXValue);
			List<Double> listY = asciiList.get(0).VALUES.get(indexOfYValue);
			File fileXAsis = new File(System.getProperty("user.dir")+"/src/main/resources/static/Files/XAsis.json");
			objectMapper.writeValue(fileXAsis, listX);
			objectMapper.writeValue(new File(System.getProperty("user.dir")+"/ConfigurationFiles/YAsis.json"), listY);
//			response.getOutputStream().write(IOUtils.toByteArray(new FileInputStream(fileXAsis)));
//			response.setContentType("application/json");
			System.out.println(fileXAsis.getAbsolutePath());
			model.addAttribute("datasX", listX);
			JSONArray listXX = new JSONArray(listX);
			JSONArray listYY = new JSONArray(listY);
			model.addAttribute("dataJsonX", listXX.toString());
			model.addAttribute("dataJsonY", listYY.toString());
			model.addAttribute("XHeader", XAsis);
			model.addAttribute("YHeader", YAsis);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "main";
    }
	
}
