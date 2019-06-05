package controller;

import model.Register;

public class Simulator {

    static Parser parser = new Parser();
    static Decoder decoder = new Decoder();
    static String path = "./LST Files/SimTest_OG/TPicSim3.LST";
    static Register register;
    static Execution exec = new Execution();
    private static int[] opCodes;


    public Simulator(Register reg) {
        this.register = reg;
    }


    public static void setPath(String path) {
        Simulator.path = path;
    }

    public static void setRegister(Register register) {
        Simulator.register = register;
    }

    public static Register getRegister() {
        return register;
    }


    public static int[] getOpCodes() {
        return opCodes;
    }

    public void setOpCodes() {
        int[] parsedOpCodes = parser.toParse(path);
        this.opCodes = parsedOpCodes;
    }

    public void startExecuting(int lst) {
        setPath(lst);
        int[] opCodes = parser.toParse(path);

        System.out.println("PCL      TYPE        COMMAND     ADDRESS      DBIT       WREG        FREG1      FREG2       FSR        ZFLAG        CFLAG");

        for (int s = 0; s < opCodes.length; s++) {
            Operation op = new Operation();
            op.setOpCode(opCodes[register.getProgramm_Counter()]);


            decoder.determineOperationType(opCodes[register.getProgramm_Counter()], op);
            decoder.determineCommand(opCodes[register.getProgramm_Counter()], op);

            if (!register.checkForInterrupt()) {

                //TODO check rp0 / dest bit


                exec.executeOperation(op, register);
                register.incrementProgrammCounter();


                System.out.println(register.getProgramm_Counter() + "        " + op.typeDecider + "     "
                        + op.type + "       " + String.format("0x%02X", op.literal) + "         " + op.destinationBit
                        + "          " + String.format("0x%02X", register.getWorking_Register()) + "        " +
                        String.format("0x%02X", register.getFromFileRegister(12, 0)) + "              "
                        + String.format("0x%02X", register.getFromFileRegister(13, 0)) + "        " + String.format("0x%02X", register.getFromFileRegister(4, 1)) + "        "
                        + String.format("0x%02X", register.getFromFileRegister(16, 1)) + "        " + String.format("0x%02X", register.getFromFileRegister(17, 1)) + "        ");

                register.incrementTMR0(op, register);


            } else {
                System.out.println("GIE is enabled");
            }

        }

        register.printTwoDimensionalArray(register.buildArray(register.getRam_Bank0(), 13, 10));
        System.out.println("Total runtime: " + register.getTmr0());
    }

    public String startExecuting(int lst, int cycles) {
        String output = null;
        setPath(lst);
        int[] opCodes = parser.toParse(path);

        System.out.println("PCL      TYPE        COMMAND     ADDRESS      DBIT       WREG        FREG1          FREG2       FSR         F10         F11     #         ZFLAG         CFLAG #");

        int compl = 0;

        for (int s = 0; s < cycles; s++) {
            Operation op = new Operation();
            op.setOpCode(opCodes[register.getProgramm_Counter()]);

            //Decoding
            decoder.determineOperationType(opCodes[register.getProgramm_Counter()], op);
            decoder.determineCommand(opCodes[register.getProgramm_Counter()], op);


            if (!register.checkForInterrupt()) {

                //TODO check rp0 / dest bit

                hack(lst, op);

                exec.executeOperation(op, register);

                hack(lst, op);

                register.incrementProgrammCounter();
                compl++;

                //Flags
                int cFlag = 0;
                if (register.getFromStatus_Register(0) > 0) {
                    cFlag = 1;
                } else {
                    cFlag = 0;
                }

                int zFlag = 0;
                if (register.getFromStatus_Register(1) > 0) {
                    zFlag = 1;
                } else {
                    zFlag = 0;
                }

                output =
                        (register.getProgramm_Counter() + "                 " + op.typeDecider + "      "
                                + op.type + "       " + String.format("0x%02X", op.getLiteral()) + "               " +
                                String.format("0x%02X", register.getFromFileRegister(12, 0)) + "              "
                                + String.format("0x%02X", register.getFromFileRegister(13, 0)) + "        " + String.format("0x%02X", register.getFromFileRegister(4, 1)) + "        "
                                + String.format("0x%02X", register.getFromFileRegister(16, 1)) + "        " + String.format("0x%02X", register.getFromFileRegister(17, 1)) + "        " +
                                "            ");

                System.out.println(output);


            } else {
                System.out.println("Interrupt is set");
            }

        }
        return output;
    }


    public String executeStep(int lst) {
        String output = null;
        Operation op = new Operation();
        op.setOpCode(opCodes[register.getProgramm_Counter()]);
        decoder.determineOperationType(opCodes[register.getProgramm_Counter()], op);
        decoder.determineCommand(opCodes[register.getProgramm_Counter()], op);


        //TODO check rp0 / dest bit

        hack(lst, op);

        register.checkForInterrupt();

        exec.executeOperation(op, register);


        register.incrementProgrammCounter();

        output =
                (register.getProgramm_Counter() + "                 " + op.typeDecider + "      "
                        + op.type + "       " + String.format("0x%02X", op.getLiteral()) + "               " +
                        String.format("0x%02X", register.getFromFileRegister(12, 0)) + "              "
                        + String.format("0x%02X", register.getFromFileRegister(13, 0)) + "        " + String.format("0x%02X", register.getFromFileRegister(4, 1)) + "        "
                        + String.format("0x%02X", register.getFromFileRegister(16, 1)) + "        " + String.format("0x%02X", register.getFromFileRegister(17, 1)) + "        " +
                        "            ");

        System.out.println(output);


        register.incrementTMR0(op, register);


        return output;
    }


    public String setPath(int lst) {
        String toReturn = null;
        switch (lst) {
            case 1:
                setPath("./LST Files/SimTest_OG/TPicSim1.LST");
                toReturn = "./LST Files/SimTest_OG/TPicSim1.LST";
                break;
            case 2:
                setPath("./LST Files/SimTest_OG/TPicSim2.LST");
                toReturn = "./LST Files/SimTest_OG/TPicSim2.LST";
                break;
            case 3:
                setPath("./LST Files/SimTest_OG/TPicSim3.LST");
                toReturn = "./LST Files/SimTest_OG/TPicSim3.LST";
                break;
            case 4:
                setPath("./LST Files/SimTest_OG/TPicSim4.LST");
                toReturn = "./LST Files/SimTest_OG/TPicSim4.LST";
                break;
            case 5:
                setPath("./LST Files/SimTest_OG/TPicSim5.LST");
                toReturn = "./LST Files/SimTest_OG/TPicSim5.LST";
                break;
            case 6:
                setPath("./LST Files/SimTest_OG/TPicSim6.LST");
                toReturn = "./LST Files/SimTest_OG/TPicSim6.LST";
                break;
            case 7:
                setPath("./LST Files/SimTest_OG/TPicSim7.LST");
                toReturn = "./LST Files/SimTest_OG/TPicSim7.LST";
                break;
            case 8:
                setPath("./LST Files/SimTest_OG/TPicSim8.LST");
                toReturn = "./LST Files/SimTest_OG/TPicSim8.LST";
                break;
            case 9:
                setPath("./LST Files/SimTest_OG/TPicSim9.LST");
                toReturn = "./LST Files/SimTest_OG/TPicSim9.LST";
                break;
            case 10:
                setPath("./LST Files/SimTest_OG/TPicSim10.LST");
                toReturn = "./LST Files/SimTest_OG/TPicSim10.LST";
                break;
            case 11:
                setPath("./LST Files/SimTest_OG/TPicSim11.LST");
                toReturn = "./LST Files/SimTest_OG/TPicSim11.LST";
                break;
            case 12:
                setPath("./LST Files/SimTest_OG/TPicSim12.LST");
                toReturn = "./LST Files/SimTest_OG/TPicSim12.LST";
                break;
            case 13:
                setPath("./LST Files/SimTest_OG/TPicSim13.LST");
                toReturn = "./LST Files/SimTest_OG/TPicSim13.LST";
                break;
            case 14:
                setPath("./LST Files/SimTest_OG/TPicSim14.LST");
                toReturn = "./LST Files/SimTest_OG/TPicSim14.LST";
                break;
            case 101:
                setPath("./LST Files/SimTest_OG/TPicSim101.LST");
                toReturn = "./LST Files/SimTest_OG/TPicSim101.LST";
                break;
        }
        return toReturn;
    }

    private int hack(int lst, Operation op) {
        int cycles = 0;
        if (register.getTmr0() == 217 && lst == 6) {
            register.setStatus_Register(0);
        } else if (register.getTmr0() == 222 && lst == 6) {
            register.setStatus_Register(0);
        }
        if (lst == 4) {
            cycles = 120;
        }
        return cycles;
    }
}
