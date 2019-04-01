package controller;
import view.*;
import model.*;

public class Main {

    static String[] operationCodes;

    public static void main(String[] args) {
        String path = "./LST Files/SimTest_OG/TPicSim10.LST";
        Parser parser = new Parser();
        operationCodes = parser.toParse(path);
        for (String s : operationCodes) {
            System.out.println(s);
        }
    }

}
