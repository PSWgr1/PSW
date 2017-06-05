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
	public List<String> HEADERS = new ArrayList<>();
	public List<String> UNITS = new ArrayList<>();
	public Map<Integer, List<Double>> VALUES = new LinkedHashMap<>();
	public String name;

	public static ASCIIFile readASCIIFile(File file) throws IOException {
		ASCIIFile asciiFile = new ASCIIFile();
		asciiFile.name = file.getName();
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			Files.lines(file.toPath()).forEach(argument -> {
				counter++;
				String[] atributes = argument.split("\\s");
				switch (counter) {
				case 1:
					asciiFile.addHeaders(atributes);
					break;
				default:
					asciiFile.addValues(atributes);
					break;
				}
			});
		}
		counter=0;
		return asciiFile;
	}

	private void addValues(String[] atributes) {
		for (int i = 0; i < atributes.length; i++) {
			if (NumberUtils.isParsable(atributes[i]) || NumberUtils.isNumber(atributes[i])) {
				try{
					Map<Integer, List<Double>> VALUES = this.VALUES;
					double todouble = NumberUtils.toDouble(atributes[i]);
					VALUES.get(i + 1).add(todouble);
				} catch (NullPointerException e) {
					System.out.println("Iteration: " + i);
					System.out.println("Lenght of Line: " + atributes.length);
					System.out.println(e.getStackTrace());
				}
			} else {
				this.UNITS.add(atributes[i]);
			}
		}
	}

	private void addHeaders(String[] atributes) {
		for (int i = 0; i < atributes.length; i++) {
			this.HEADERS.add(atributes[i]);
		}
		for (int j = 0; j < HEADERS.size(); j++) {
			this.VALUES.put(j + 1, new ArrayList<>());
		}
	}

}
