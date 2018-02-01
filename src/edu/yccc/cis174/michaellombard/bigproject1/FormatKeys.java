package edu.yccc.cis174.michaellombard.bigproject1;

import java.util.TreeMap;

public class FormatKeys {

    private final static TreeMap<Integer, String> map = new TreeMap<Integer, String>();

    static {

        map.put(1000, "M");
        map.put(900, "CM");
        map.put(500, "D");
        map.put(400, "CD");
        map.put(100, "C");
        map.put(90, "XC");
        map.put(50, "L");
        map.put(40, "XL");
        map.put(10, "X");
        map.put(9, "IX");
        map.put(5, "V");
        map.put(4, "IV");
        map.put(1, "I");

    }
    
    public static String setAnswerFormat(int formatType, int answerValue) {
    	/* Answer Types are as follows:
    	 * 1 = Letter, only 26 options available
    	 * 2 = DoubleLetter, 26*26 options available
    	 * 3 = Numbers, all numbers available
    	 * 4 = Roman Numerals, can handle about 3000 correctly. 
    	 */
   	String convertedAnswer = "---";
    	if (formatType == 1) {
    		convertedAnswer = answerValue > 0 && answerValue < 27 ? String.valueOf((char)(answerValue + 64)) : null;//find and return ASCII value of modified number(limits to capital letters)
    	} else if (formatType == 2) {
    		int answerValuea = (answerValue/26) + 1;
    		int answerValueb = (answerValue%26);
    		String convertedAnswera = answerValuea > 0 && answerValuea < 27 ? String.valueOf((char)(answerValuea + 64)) : null;//find and return ASCII value of modified number(limits to capital letters)
    		String convertedAnswerb = answerValueb > 0 && answerValueb < 27 ? String.valueOf((char)(answerValueb + 64)) : null;//find and return ASCII value of modified number(limits to capital letters)
    		convertedAnswer = convertedAnswera + convertedAnswerb;
    	} else if (formatType == 3) {
    		convertedAnswer = Integer.toString(answerValue);
    	} else if (formatType == 4) {
    		convertedAnswer = toRoman(answerValue);
    	}
   	return convertedAnswer;
    }

    public static int setAnswerFormat(int formatType,String minOrMax) {
    	/* Answer Types are as follows:
    	 * 1 = Letter, only 26 options available
    	 * 2 = DoubleLetter, 26*26 options available
    	 * 3 = Numbers, all numbers available
    	 * 4 = Roman Numerals, can handle about 3000 correctly. 
    	 */
    	int convertedAnswer = 0;
    	if (formatType == 1) {
    		convertedAnswer = 1;
    	} else if (formatType == 2) {
    		convertedAnswer = 2;
    	} else if (formatType == 3 && minOrMax.equalsIgnoreCase("max")) {
    		convertedAnswer = 6;
    	} else if (formatType == 3 && minOrMax.equalsIgnoreCase("min")) {
    		convertedAnswer = 1;
     	} else if (formatType == 4 && minOrMax.equalsIgnoreCase("max")) {
    		convertedAnswer = 20;
    	} else if (formatType == 4 && minOrMax.equalsIgnoreCase("min")) {
    		convertedAnswer = 1;
    	}
    	return convertedAnswer;
    }


    public final static String toRoman(int number) {
        int l =  map.floorKey(number);
        if ( number == l ) {
            return map.get(number);
        }
        return map.get(l) + toRoman(number-l);
    }

}