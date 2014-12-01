package de.bkiss.dictconverter;

import java.util.HashMap;
import java.util.Map;

/**
 * Klasse zur Repräsentation eines Eintrages aus den DE-EN Wörterbuchtdaten
 * der TU Chemnitz: http://ftp.tu-chemnitz.de/pub/Local/urz/ding/de-en/
 * @author bkiss
 *
 */

public class DictEntry {
	
	public static final String DE_WORD 				= "de_word";
	public static final String DE_WORD_GEN 			= "de_word_gen";
	public static final String DE_WORD_SEM			= "de_word_sem";
	public static final String DE_WORD_SUBSEM		= "de_word_subsem";
	//public static final String DE_WORD_GRAM			= "de_word_gram";
	
	public static final String EN_WORD 				= "en_word";
	public static final String EN_WORD_GEN 			= "en_word_gen";
	public static final String EN_WORD_SEM			= "en_word_sem";
	public static final String EN_WORD_SUBSEM		= "en_word_subsem";
	//public static final String EN_WORD_GRAM			= "en_word_gram";
	
	
	private Map<String, String> entryData;
	
	
	public DictEntry(){
		entryData = new HashMap<String, String>();
	}
	
	public void setField(String fieldKey, String fieldValue){
		entryData.put(fieldKey, fieldValue);
	}
	
	public String getField(String fieldKey){
		String value = entryData.get(fieldKey);
		return value != null ? value : "";
	}
	
	@Override public String toString(){
		return    DE_WORD + ":" + entryData.get(DE_WORD) + "\n"
				+ DE_WORD_GEN + ":" + entryData.get(DE_WORD_GEN) + "\n"
				+ DE_WORD_SEM + ":" + entryData.get(DE_WORD_SEM) + "\n"
				+ DE_WORD_SUBSEM + ":" + entryData.get(DE_WORD_SUBSEM) + "\n"
				+ EN_WORD + ":" + entryData.get(EN_WORD) + "\n"
				+ EN_WORD_GEN + ":" + entryData.get(EN_WORD_GEN) + "\n"
				+ EN_WORD_SEM + ":" + entryData.get(EN_WORD_SEM) + "\n"
				+ EN_WORD_SUBSEM + ":" + entryData.get(EN_WORD_SUBSEM);
	}

}
