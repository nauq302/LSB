/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

/**
 *
 * @author nauq3
 */
public class Biswise {
    
    /**
     * 
     * @param value
     * @return 
     */
    public static int getLastBit(int value) {
        return value & 1;
    }
    
    /**
     * 
     * @param value
     * @param newLastBit
     * @return 
     */
    public static int setLastBit(int value, int newLastBit) {
        switch (newLastBit) {
            case 1:
                return value | 1;
            case 0:
                return value & ~1;
            default:
                throw new RuntimeException("Last bit must be 0 or 1");
        }
    }
    
    /**
     * 
     * @param c
     * @return 
     */
    public static String toBitString(char c) {
        String temp = Integer.toBinaryString((int)c);
        return String.format("%8s", temp).replace(' ', '0');
    }
    
    /**
     * 
     * @param str
     * @return 
     */
    public static String toBitString(String str) {
        String result = "";
        for (int i = 0; i < str.length(); ++i) {
            result += toBitString(str.charAt(i));
        }
        return result;
    }
}
