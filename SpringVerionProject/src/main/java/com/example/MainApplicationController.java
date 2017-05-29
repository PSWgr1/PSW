package com.example;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Engine.ASCIIFile;
import com.example.Engine.PropertiesReader;
import com.example.Engine.RepositoryChecker;

@Controller
public class MainApplicationController {

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
		return "result";
    }
	
	@RequestMapping(value = "/result", method = RequestMethod.POST)
    public String getData(@RequestParam("CheckFileName") List<File> values ,HttpServletRequest request, Model model) {
		List<ASCIIFile> asciiList = new ArrayList<>();
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
		return "chosenfiles";
    }
}
