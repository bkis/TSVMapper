package de.bkiss.dictconverter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Converter con = new Converter();
		
		try {
			Scanner scanner = new Scanner(new File("de-en.txt"));
			while (scanner.hasNextLine())
				con.addEntry(scanner.nextLine());
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		List<DictEntry> dict = con.getData();
		List<String[]> svData = new ArrayList<String[]>();
		svData.add(new String[]{DictEntry.DE_WORD,
								DictEntry.DE_WORD_GEN,
								DictEntry.DE_WORD_SEM,
								DictEntry.DE_WORD_SUBSEM,
								DictEntry.EN_WORD,
								DictEntry.EN_WORD_GEN,
								DictEntry.EN_WORD_SEM,
								DictEntry.EN_WORD_SUBSEM,});
		for (int i = 0; i < dict.size(); i++) {
			System.out.println("CREATING SV DATA: " + dict.get(i).getField(DictEntry.DE_WORD));
			
			//leere skippen
			if (dict.get(i).getField(DictEntry.DE_WORD).equals("")) continue;
			
			DictEntry entry = dict.get(i);
			svData.add(new String[]{entry.getField(DictEntry.DE_WORD),
									entry.getField(DictEntry.DE_WORD_GEN),
									entry.getField(DictEntry.DE_WORD_SEM),
									entry.getField(DictEntry.DE_WORD_SUBSEM),
									entry.getField(DictEntry.EN_WORD),
									entry.getField(DictEntry.EN_WORD_GEN),
									entry.getField(DictEntry.EN_WORD_SEM),
									entry.getField(DictEntry.EN_WORD_SUBSEM)});
		}

		con.writeSV(svData, "\t", "de-en.tab");
	}

}
