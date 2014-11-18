package de.bkiss.surmirantsv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import com.opencsv.CSVReader;

public class TSVMapper {
	
	
	public List<String[]> createTSV(String tsvFieldNamesTargetPath, String tsvDataSrcPath, Map<String, String> mapSrcToTargetFieldNames){
		List<String[]> srcData = readTSV(tsvDataSrcPath);
		String[] targetFields = readTSV(tsvFieldNamesTargetPath).get(0);
		
		
		
		return null;
	}
	
	
	private List<String[]> readTSV(String path){
		FileReader fr;
		CSVReader csvr;
		List<String[]> srcData = null;
		
		try {
			fr = new FileReader(new File(path));
			csvr = new CSVReader(fr, '\t');
			srcData = csvr.readAll();
			csvr.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return srcData;
	}
	
}
