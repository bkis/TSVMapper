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
		for (String s : srcData.get(0))
			fields.add(s);
		for (String s : targetFields)
			if (!fields.contains(s))
				fields.add(s);
		srcData.remove(0);
		String[] newFields = new String[fields.size()];
		srcData.add(0, fields.toArray(newFields));
		
		//fill empty fields with empty values
		String[] curr;
		for (int i = 1; i < srcData.size(); i++) {
			curr = new String[srcData.get(0).length];
			for (int j = 0; j < srcData.get(i).length; j++){
				curr[j] = srcData.get(i)[j];
			}
			for (int j = 0; j < curr.length; j++) {
				if (curr[j] == null) curr[j] = "";
			}
			srcData.remove(i);
			srcData.add(i, curr);
		}
		
		System.out.println("OK");
		return srcData;
	}
	
	
	public void writeSV(List<String[]> svData,
						String delimiter,
						String path,
						String excludePattern,
						String excludeEntriesWithEmptyDataFor){
		
		System.out.print("[INFO] writing data to " + path + " ... ");
		StringBuilder sb = new StringBuilder();
		
		int excludeEmpty = findIndexOf(svData.get(0), excludeEntriesWithEmptyDataFor);
		//check find
		if (excludeEmpty == -1){
			System.out.println("[ERROR] target column \"" + excludeEntriesWithEmptyDataFor + "\" not found. cancelling...");
			return;
		}
		
		//generate text data
		for (String[] sArr : svData){
			//exclude entries with empty data for excludeEntriesWithEmptyDataFor
			if (sArr[excludeEmpty].equals("")) continue;
			
			for (String s : sArr){
				sb.append(s.replaceAll(excludePattern, "") + delimiter);
			}
			sb.replace(sb.length()-delimiter.length(), sb.length(), "\n");
		}
		
		//frite to file
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
	
	public void printEmptyData(String tsvPath, String delimiter, String targetColumn){
		List<String[]> data = readSV(tsvPath, delimiter);
		String[] columnNames = data.remove(0);
		int targetIndex = findIndexOf(columnNames, targetColumn);
		int count = 0;
		
		//check find
		if (targetIndex == -1){
			System.out.println("[ERROR] target column \"" + targetColumn + "\" not found. cancelling...");
			return;
		}
		
		//search for empty data
		for (String[] sArr : data){
			if (sArr[targetIndex].equals("")){
				count++;
				System.out.print("EMPTY DATA FOR \"" + targetColumn + "\" IN LINE: ");
				for (String s : sArr) System.out.print(s + delimiter);
				System.out.println("");
			}
		}
		System.out.println("TOTAL EMPTY ENTRIES FOR \"" + targetColumn + "\": " + count);
	}
	
	private int findIndexOf(String[] array, String value){
		//find target index
		for (int i = 0; i < array.length; i++)
			if (array[i].equals(value))
				return i;
		return -1;
	}
	
}
