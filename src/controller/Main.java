package controller;

import model.Register;

public class Main {

    static Parser parser = new Parser();
    static Decoder decoder = new Decoder();
    static String path = "./LST Files/SimTest_OG/TPicSim1.LST";
    static Register register = new Register();
    static Execution exec = new Execution();


    public static void main(String[] args) {
        int[] opCodes = parser.toParse(path);

        register.resetRegisters();

        System.out.println("PCL      TYPE        COMMAND     ADDRESS      DBIT       WREG        STACKP         ZFLAG        CFLAG");

        for (int s = 0; s < 100; s++) {

            Operation op = new Operation();
            op.setOpCode(opCodes[register.getProgramm_Counter()]);

            decoder.determineOperationType(opCodes[register.getProgramm_Counter()], op);
            decoder.determineCommand(opCodes[register.getProgramm_Counter()], op);

            exec.executeOperation(op, register);

            register.incrementProgrammCounter();

            System.out.println(register.getProgramm_Counter() + "        " + op.typeDecider + "     "
                    + op.type + "       " + String.format("0x%02X", op.literal) + "         " + op.destinationBit
                    + "          " + String.format("0x%02X", register.getWorking_Register()) + "        " + register.getStackpointer() + "              "
                    + register.getStatus_Register(0, register) + "            " + register.getStatus_Register(1, register));
        }
    }

}
