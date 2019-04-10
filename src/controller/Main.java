package controller;

import model.*;


public class Main {

    static String[] operationCodes;

    public static void main(String[] args) {
        String path = "./LST Files/SimTest_OG/TPicSim10.LST";
        Parser parser = new Parser();
        Register register = new Register();
        register.resetRegisters();
        register.decrementPCL();
        register.incrementPCL();
        int pcl = register.getProgramm_Counter_Low();


        operationCodes = parser.toParse(path);
        for (String s : operationCodes) {
            System.out.println(s);
        }
    }

}
