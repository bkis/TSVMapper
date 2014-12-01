package de.bkiss.dictconverter;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;


/**
 * Klasse zur Konvertierung eines Eintrages aus den DE-EN Wörterbuchtdaten
 * der TU Chemnitz (http://ftp.tu-chemnitz.de/pub/Local/urz/ding/de-en/)
 * nach TSV
 * @author bkiss
 *
 */

public class Converter {
	
	private List<DictEntry> entries;
	
	
	public Converter(){
		entries = new ArrayList<DictEntry>();
	}
	
	public void addEntry(String rawEntryLine){
		System.out.println("RAW: " + rawEntryLine);
		if (rawEntryLine == null || rawEntryLine.length() == 0 || rawEntryLine.charAt(0) == '#' || rawEntryLine.length() < 3) return;
		
		//DE-EN
		rawEntryLine = rawEntryLine.replaceAll("§§§", "");
		String[] deEN = rawEntryLine.split(" :: ");
		if (deEN.length != 2){
			return;
		} else {
			String de = deEN[0].split(" §§§ ")[0];
			String en = deEN[1].split(" §§§ ")[0];
			addEntry(de, en);
		}
	}
	
	private void addEntry(String rawDe, String rawEn){
		DictEntry entry = new DictEntry();
		
		//DE_WORD
		entry.setField(DictEntry.DE_WORD, rawDe.replaceAll("[^\\p{L}\\s].*", ""));
		
		//DE_GEN
		if (rawDe.indexOf('{') != -1){
			entry.setField(DictEntry.DE_WORD_GEN, rawDe.substring(rawDe.indexOf('{')+1, rawDe.indexOf('}')));
			rawDe = rawDe.substring(rawDe.indexOf('}')+1, rawDe.length());
		} else {entry.setField(DictEntry.DE_WORD_GEN, "");}
		
		//PLURALE AUSSCHLIESSEN
		if (entry.getField(DictEntry.DE_WORD_GEN).equals("pl")) return;
		
		//DE_SEM
		if (rawDe.indexOf('(') != -1){
			entry.setField(DictEntry.DE_WORD_SEM, rawDe.substring(rawDe.indexOf('(')+1, rawDe.indexOf(')')));
			rawDe = rawDe.substring(rawDe.indexOf(')')+1, rawDe.length());
		} else {entry.setField(DictEntry.DE_WORD_SEM, "");}

		//DE_SUBSEM
		if (rawDe.indexOf('[') != -1){
			entry.setField(DictEntry.DE_WORD_SUBSEM, rawDe.substring(rawDe.indexOf('[')+1, rawDe.indexOf(']')));
			rawDe = rawDe.substring(rawDe.indexOf(']')+1, rawDe.length());
		} else {entry.setField(DictEntry.DE_WORD_SUBSEM, "");}
		
		
		
		//EN_WORD
		entry.setField(DictEntry.EN_WORD, rawEn.replaceAll("[^\\p{L}\\s].*", ""));
		
		//EN_GEN
		if (rawEn.indexOf('{') != -1){
			entry.setField(DictEntry.EN_WORD_GEN, rawEn.substring(rawEn.indexOf('{')+1, rawEn.indexOf('}')));
			rawEn = rawEn.substring(rawEn.indexOf('}')+1, rawEn.length());
		} else {entry.setField(DictEntry.EN_WORD_GEN, "");}
		
		//EN_SEM
		if (rawEn.indexOf('(') != -1){
			entry.setField(DictEntry.EN_WORD_SEM, rawEn.substring(rawEn.indexOf('(')+1, rawEn.indexOf(')')));
			rawEn = rawEn.substring(rawEn.indexOf(')')+1, rawEn.length());
		} else {entry.setField(DictEntry.EN_WORD_SEM, "");}
		
		//EN_SUBSEM
		if (rawEn.indexOf('[') != -1){
			entry.setField(DictEntry.EN_WORD_SUBSEM, rawEn.substring(rawEn.indexOf('[')+1, rawEn.indexOf(']')));
			rawEn = rawEn.substring(rawEn.indexOf(']')+1, rawEn.length());
		} else {entry.setField(DictEntry.EN_WORD_SUBSEM, "");}
		
		entries.add(entry);
	}

	public List<DictEntry> getData(){
		return entries;
	}
	
	public void writeSV(List<String[]> svData, String delimiter, String path){
		System.out.print("[INFO] writing data to " + path + " ... ");
		StringBuilder sb = new StringBuilder();
		
		for (String[] sArr : svData){
			for (String s : sArr){
				sb.append(s + delimiter);
			}
			sb.replace(sb.length()-delimiter.length(), sb.length(), "\n");
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
}
