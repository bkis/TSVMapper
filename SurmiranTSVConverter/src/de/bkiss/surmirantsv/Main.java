package de.bkiss.surmirantsv;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

	public static void main(String[] args) {
		TSVMapper tsvm = new TSVMapper();
		Map<String,String> fieldMap = new HashMap<String,String>();
		
		fieldMap.put("ID", "RecID");
		fieldMap.put("DStichwortSort", "DStichwort_sort");
		fieldMap.put("RStichwortSort", "RStichwort_sort");
		fieldMap.put("DSempraez", "DSubsemantik");
		fieldMap.put("RSempraez", "RSubsemantik");
		
		List<String[]> tsv = tsvm.createTSV("input.tab", "surmiran.tab", fieldMap);
		
		tsvm.writeSV(tsv, "\t", "output.tab");
		
	}

}
