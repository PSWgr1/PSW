package com.example;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.Engine.PropertiesReader;
import com.example.Engine.RepositoryChecker;

@Controller
public class MainApplicationController {

	@RequestMapping(value= "/", method=RequestMethod.GET)
	public String main(Model model){
		return "main";
	}
	
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
    public String getData(HttpServletRequest request, Model model) {
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
}
