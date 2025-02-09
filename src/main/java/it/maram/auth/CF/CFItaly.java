package it.maram.auth.CF;

import it.maram.logging.InvalidParametersException;

import java.util.*;

public class CFItaly {
    private final static String[] v = { "A", "E", "I", "U", "O" };

    private final static String[] c = { "B", "C", "D", "F", "G", "H",
            "J", "K", "L", "M", "N", "P", "Q", "R", "S", "T", "V", "W", "X",
            "Y", "Z" };
    private final static String[] months = { "A", "B", "C", "D", "E",
            "H", "L", "M", "P", "R", "S", "T" };

    private final static String[] map_r = { "A", "B", "C", "D", "E", "F", "G",
            "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z" };
    private final static Map<String, Integer> CP;
    private final static Map<String, Integer> CD;
    private final static Map<String, String> CTC;
    static {
        CP = new HashMap<>();
        CP.put("0",1);
        CP.put("1",0);
        CP.put("2",5);
        CP.put("3",7);
        CP.put("4",9);
        CP.put("5",13);
        CP.put("6",15);
        CP.put("7",17);
        CP.put("8",19);
        CP.put("9",21);
        CP.put("A",1);
        CP.put("B",0);
        CP.put("C",5);
        CP.put("D",7);
        CP.put("E",9);
        CP.put("F",13);
        CP.put("G",15);
        CP.put("H",17);
        CP.put("I",19);
        CP.put("J",21);
        CP.put("K",2);
        CP.put("L",4);
        CP.put("M",18);
        CP.put("N",20);
        CP.put("O",11);
        CP.put("P",3);
        CP.put("Q",6);
        CP.put("R",8);
        CP.put("S",12);
        CP.put("T",14);
        CP.put("U",16);
        CP.put("V",10);
        CP.put("W",22);
        CP.put("X",25);
        CP.put("Y",24);
        CP.put("Z",23);
    }
    static{
        CD = new HashMap<>();
        CD.put("0",0);
        CD.put("1",1);
        CD.put("2",2);
        CD.put("3",3);
        CD.put("4",4);
        CD.put("5",5);
        CD.put("6",6);
        CD.put("7",7);
        CD.put("8",8);
        CD.put("9",9);
        CD.put("A",0);
        CD.put("B",1);
        CD.put("C",2);
        CD.put("D",3);
        CD.put("E",4);
        CD.put("F",5);
        CD.put("G",6);
        CD.put("H",7);
        CD.put("I",8);
        CD.put("J",9);
        CD.put("K",10);
        CD.put("L",11);
        CD.put("M",12);
        CD.put("N",13);
        CD.put("O",14);
        CD.put("P",15);
        CD.put("Q",16);
        CD.put("R",17);
        CD.put("S",18);
        CD.put("T",19);
        CD.put("U",20);
        CD.put("V",21);
        CD.put("W",22);
        CD.put("X",23);
        CD.put("Y",24);
        CD.put("Z",25);
    }
    static {
        CTC = new HashMap<>();
        Scanner scanner = new Scanner(CFItaly.class.getResourceAsStream("/CFData/CityCodes"));
        while(scanner.hasNextLine()){
            String s = scanner.nextLine();
            String[] ss = s.split("\t");
            CTC.put(ss[1], ss[5]);
        }
        scanner = null;
        System.gc();
    }
    public enum sex{
        MALE,
        FEMALE
    }
    public static String calculate(String name, String surname, int year, int month, int day, sex s, String city){
        name = name.toUpperCase();
        surname = surname.toUpperCase();
        city = city.toUpperCase();
        StringBuilder sb = new StringBuilder();
        String nmc = cons(name);
        String nmv = voc(name);
        String snc = cons(surname);
        String snv = voc(surname);
        if(year>=2000) year-=2000;
        else year-=1900;
        if(s==sex.FEMALE) day +=40;
        if (snc.length()>=3){
            sb.append(snc, 0, 3);
        }else{
            sb.append(snc);
            sb.append(snv, 0, 3-snc.length());
        }
        if(nmc.length()>=4){
            sb.append(nmc, 0,2);
            sb.append(nmc,3, 4);
        }else if(nmc.length() == 3){
            sb.append(nmc);
        }else{
            sb.append(nmc);
            sb.append(nmv, 0, 3-nmc.length());
        }
        sb.append(getNum(year));
        sb.append(months[month]);
        sb.append(getNum(day));
        sb.append(CTC.get(city));
        return calcCC(sb.toString());
    }
    private static String calcCC(String partialCC){
        if(partialCC.length()!=15) throw new InvalidParametersException("Expected length 15, found " + partialCC.length());
        List<String> p = new ArrayList<>();
        List<String> d = new ArrayList<>();
        for(int a = 0; a<partialCC.length(); a+=2){
            p.add(partialCC.substring(a,a+1));
        }
        for(int a = 1; a<partialCC.length(); a+=2){
            d.add(partialCC.substring(a,a+1));
        }
        int np =0, nd=0;
        for(String s : p){
            np+=CP.get(s);
        }
        for (String s : d){
            nd+=CD.get(s);
        }
        int  r = (np+nd)%26;
        return partialCC + map_r[r];
    }
    private static String getNum(int num){
        StringBuilder sb = new StringBuilder();
        if(num<10) sb.append("0").append(num);
        else sb.append(num);
        return sb.toString();
    }
    private static String cons(String value){
        StringBuilder toReturn = new StringBuilder();
        for(int a = 0; a<value.length(); a++){
            String car = value.substring(a, a+1);
            if (isCons(car)) toReturn.append(car);
        }
        return toReturn.toString();
    }
    private static String voc(String value){
        StringBuilder toReturn = new StringBuilder();
        for(int a = 0; a<value.length(); a++){
            String car = value.substring(a, a+1);
            if (isVoc(car)) toReturn.append(car);
        }
        return toReturn.toString();
    }
    private static boolean isCons(String value){
        for(String s : c){
            if(value.equals(s)) return true;
        }
        return false;
    }
    private static boolean isVoc(String value){
        for(String s : v){
            if(value.equals(s)) return true;
        }
        return false;
    }
}
