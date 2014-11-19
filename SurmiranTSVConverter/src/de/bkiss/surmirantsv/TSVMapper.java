package de.bkiss.surmirantsv;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class TSVMapper {
	
	
	public List<String[]> createTSV(String tsvFieldNamesTargetPath, String tsvDataSrcPath, Map<String, String> mapSrcToTargetFieldNames){
		List<String[]> srcData = readSV(tsvDataSrcPath, "\t");
		String[] targetFields = readSV(tsvFieldNamesTargetPath, "\t").get(0);
		
		System.out.print("[INFO] applying mapped target field names: ");
		//change mapped src field names to target field names
		for (int i = 0; i < srcData.get(0).length ; i++){
			System.out.print(srcData.get(0)[i] + "->");
			if (mapSrcToTargetFieldNames.containsKey(srcData.get(0)[i]))
				srcData.get(0)[i] = mapSrcToTargetFieldNames.get(srcData.get(0)[i]);
			System.out.print(srcData.get(0)[i] + " ");
		}
		System.out.println("...OK");
		
		System.out.print("[INFO] adding unused target field names... ");
		//add unused target field names
		List<String> fields = new ArrayList<String>();
		for (String s :srcData.get(0))
			fields.add(s);
		for (String s : targetFields)
			if (!fields.contains(s))
				fields.add(s);
		srcData.remove(0);
		String[] newFields = new String[fields.size()];
		srcData.add(0, fields.toArray(newFields));
		
		System.out.println("OK");
		return srcData;
	}
	
	
	public void writeSV(List<String[]> svData, String delimiter, String path){
		System.out.print("[INFO] writing data to " + path + " ... ");
		StringBuilder sb = new StringBuilder();
		
		for (String[] sArr : svData){
			for (String s : sArr){
				sb.append(s + delimiter);
			}
			sb.append("\n");
		}
		
		try {
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(path), "UTF-8"));
			out.write(sb.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("OK");
	}

	
	private List<String[]> readSV(String tsvPath, String delimiter){
		System.out.print("[INFO] reading " + tsvPath + "... ");
		List<String[]> tsv = new ArrayList<String[]>();
		
		try {
			Scanner scanner = new Scanner(new File(tsvPath));
			while (scanner.hasNextLine())
				tsv.add(scanner.nextLine().split(delimiter));
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		System.out.println("OK");
		return tsv;
	}
	
}
