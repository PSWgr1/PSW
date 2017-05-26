package com.example.Engine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;

public class ASCIIFile {

	static int counter = 0;
	static List<String> HEADERS = new ArrayList<>();
	static List<String> UNITS = new ArrayList<>();
	static Map<Integer,List<Double>> VALUES = new LinkedHashMap<>();
	
	public static ASCIIFile readASCIIFile(File file) throws IOException {
		ASCIIFile asciiFile = new ASCIIFile();
		
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			
			Files.lines(file.toPath()).forEach(argument ->{
				counter++;
				String[] atributes = argument.split("\\s"); 
				switch(counter){
				case 1:
					addHeaders(asciiFile,atributes);
					break;
				default:
					addValues(asciiFile,atributes);
					break;
				}
			});
		}
		return asciiFile;
	}
	private static void addValues(ASCIIFile asciiFile, String[] atributes) {
		for (int i = 0; i < atributes.length; i++) {
			if(NumberUtils.isParsable(atributes[i]) || NumberUtils.isNumber(atributes[i])){
				asciiFile.VALUES.get(i+1).add(NumberUtils.toDouble(atributes[i]));
			} else {
				asciiFile.UNITS.add(atributes[i]);
			}
		}
	}
	private static void addHeaders(ASCIIFile asciiFile, String[] atributes) {
		for (int i = 0; i < atributes.length; i++) {
			asciiFile.HEADERS.add(atributes[i]);
		}
		for (int j = 0; j < HEADERS.size(); j++) {
			asciiFile.VALUES.put(j+1, new ArrayList<>());
		}
	}

}
