package controller;

import model.*;


public class Main {

    static Parser parser = new Parser();
    static Register register = new Register();
    static Decoder decoder = new Decoder();
    static int programCounter = register.getProgramm_Counter_Low();

    public static int getProgramCounter() {
        return programCounter;
    }

    public static String[] getOperationCodes() {
        return operationCodes;
    }

    static String[] operationCodes;
    static int rowCounter = -1;

    public static void main(String[] args) {
        String path = "./LST Files/SimTest_OG/TPicSim10.LST";
        int byteOrientedMask = 0b11_1100_0000_0000;
        System.out.println(" #    OPCode  PCL");

        operationCodes = parser.toParse(path);
        for (String s : operationCodes) {
            rowCounter++;
            register.incrementPCL();
            int arg = byteOrientedMask & Integer.parseInt(s, 16);
            String hex = Integer.toHexString(arg);
            System.out.println(" " + rowCounter + "     " + s + "   " + register.getProgramm_Counter_Low() + "     " + hex);
        }


    }


}
