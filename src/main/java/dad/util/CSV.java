package dad.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

public class CSV {
	
	public static List<String[]> readAllLines(Path filePath) throws Exception {

	    try (Reader reader = Files.newBufferedReader(filePath)) {
	        try (CSVReader csvReader = new CSVReader(reader)) {
	            return csvReader.readAll();
	        }
	    }
	}
	
	public static List<String> readAllLinesSpecificCollumn(Path filePath,int value) throws Exception {

	    try (Reader reader = Files.newBufferedReader(filePath)) {
	        try (CSVReader csvReader = new CSVReader(reader)) {
	        	List<String> specificCollumn = new ArrayList<String>();
	        	csvReader.forEach(e -> {
	        		if(!e[value].isBlank() && !e[value].isEmpty()) {
	        			specificCollumn.add("+" + e[value]);
	        		}
	        	});
	        	return specificCollumn;
	        }
	    }
	}
	
	public static void writeLine(Path filePath,String[] line) throws Exception {
		FileWriter fileWritter = new FileWriter(filePath.toString(),true);
		BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
	    try (Writer writer = bufferWritter) {
	        try (CSVWriter csvReader = new CSVWriter(writer)) {
	            csvReader.writeNext(line);
	        }
	    }
	}

}
