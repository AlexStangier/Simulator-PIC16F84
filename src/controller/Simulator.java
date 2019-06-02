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

        Operation[] opArr = new Operation[opCodes.length];

        register.resetRegisters();
        int runtime = 0;

        System.out.println("PCL      TYPE        COMMAND     ADDRESS      DBIT       WREG        FREG1      FREG2       FSR     F10     F11        ZFLAG        CFLAG");

        for (int s = 0; s < opCodes.length; s++) {
            Operation op = new Operation();
            op.setOpCode(opCodes[register.getProgramm_Counter()]);


            decoder.determineOperationType(opCodes[register.getProgramm_Counter()], op);
            decoder.determineCommand(opCodes[register.getProgramm_Counter()], op);
            exec.executeOperation(op, register);
            opArr[s] = op;

            register.incrementProgrammCounter();

            System.out.println(register.getProgramm_Counter() + "        " + op.typeDecider + "     "
                    + op.type + "       " + String.format("0x%02X", op.literal) + "         " + op.destinationBit
                    + "          " + String.format("0x%02X", register.getWorking_Register()) + "        " +
                    String.format("0x%02X", register.getFromFileRegister(12, 0)) + "              "
                    + String.format("0x%02X", register.getFromFileRegister(13, 0)) + "        " + String.format("0x%02X", register.getFromFileRegister(4, 1)) + "        "
                    + String.format("0x%02X", register.getFromFileRegister(16, 1)) + "        " + String.format("0x%02X", register.getFromFileRegister(17, 1)) + "        " +
                    register.getStatus_Register(0, register) + "            " + register.getStatus_Register(1, register));

            register.incrementTMR0(op, register);
        }
        System.out.println("Total runtime: " + runtime + " Cycles \n");
        register.printTwoDimensionalArray(register.buildArray(register.getRam_Bank0(), 13, 10));

        for (int s = 0; s < opArr.length; s++) {
            System.out.println(s + " " + opArr[s].type + "    " + register.getProgramm_Counter());
        }
    }

    public void startExecuting(int lst, int cycles) {
        setPath(lst);
        int[] opCodes = parser.toParse(path);

        int runtime = 0;

        System.out.println("PCL      TYPE        COMMAND     ADDRESS      DBIT       WREG        FREG1      FREG2       FSR        ZFLAG        CFLAG");

        int compl = 0;

        for (int s = 0; s < cycles; s++) {
            Operation op = new Operation();
            op.setOpCode(opCodes[register.getProgramm_Counter()]);


            decoder.determineOperationType(opCodes[register.getProgramm_Counter()], op);
            decoder.determineCommand(opCodes[register.getProgramm_Counter()], op);

            if (register.getIntcon(7) != 1) {
                exec.executeOperation(op, register);
                register.incrementProgrammCounter();
                compl++;

                System.out.println(register.getProgramm_Counter() + "        " + op.typeDecider + "     "
                        + op.type + "       " + String.format("0x%02X", op.literal) + "         " + op.destinationBit
                        + "          " + String.format("0x%02X", register.getWorking_Register()) + "        " +
                        String.format("0x%02X", register.getFromFileRegister(12, 0)) + "              "
                        + String.format("0x%02X", register.getFromFileRegister(13, 0)) + "        " + String.format("0x%02X", register.getFromFileRegister(4, 1)) + "        "
                        + String.format("0x%02X", register.getFromFileRegister(16, 1)) + "        " + String.format("0x%02X", register.getFromFileRegister(17, 1)) + "        " +
                        register.getStatus_Register(0, register) + "            " + register.getStatus_Register(1, register) + "     " + compl);

                register.incrementTMR0(op, register);


            } else {
                System.out.println("GIE is enabled");
            }

        }

        register.printTwoDimensionalArray(register.buildArray(register.getRam_Bank0(), 13, 10));
    }


    public void executeStep() {
        Operation op = new Operation();
        op.setOpCode(opCodes[register.getProgramm_Counter()]);
        decoder.determineOperationType(opCodes[register.getProgramm_Counter()], op);
        decoder.determineCommand(opCodes[register.getProgramm_Counter()], op);
        if (register.getIntcon(7) != 1) {
            exec.executeOperation(op, register);
            register.incrementProgrammCounter();

            System.out.println(register.getProgramm_Counter() + "        " + op.typeDecider + "     "
                    + op.type + "       " + String.format("0x%02X", op.literal) + "         " + op.destinationBit
                    + "          " + String.format("0x%02X", register.getWorking_Register()) + "        " +
                    String.format("0x%02X", register.getFromFileRegister(12, 0)) + "              "
                    + String.format("0x%02X", register.getFromFileRegister(13, 0)) + "        " + String.format("0x%02X", register.getFromFileRegister(4, 1)) + "        "
                    + String.format("0x%02X", register.getFromFileRegister(16, 1)) + "        " + String.format("0x%02X", register.getFromFileRegister(17, 1)) + "        " +
                    register.getStatus_Register(0, register) + "            " + register.getStatus_Register(1, register));

            register.incrementTMR0(op, register);
        } else {
            System.out.println("GIE is enabled");
        }
    }


    public void setPath(int lst) {
        switch (lst) {
            case 1:
                setPath("./LST Files/SimTest_OG/TPicSim1.LST");
                break;
            case 2:
                setPath("./LST Files/SimTest_OG/TPicSim2.LST");
                break;
            case 3:
                setPath("./LST Files/SimTest_OG/TPicSim3.LST");
                break;
            case 4:
                setPath("./LST Files/SimTest_OG/TPicSim4.LST");
                break;
            case 5:
                setPath("./LST Files/SimTest_OG/TPicSim5.LST");
                break;
            case 6:
                setPath("./LST Files/SimTest_OG/TPicSim6.LST");
                break;
            case 7:
                setPath("./LST Files/SimTest_OG/TPicSim7.LST");
                break;
            case 8:
                setPath("./LST Files/SimTest_OG/TPicSim8.LST");
                break;
            case 9:
                setPath("./LST Files/SimTest_OG/TPicSim9.LST");
                break;
            case 10:
                setPath("./LST Files/SimTest_OG/TPicSim10.LST");
                break;
            case 11:
                setPath("./LST Files/SimTest_OG/TPicSim11.LST");
                break;
            case 12:
                setPath("./LST Files/SimTest_OG/TPicSim12.LST");
                break;
            case 13:
                setPath("./LST Files/SimTest_OG/TPicSim13.LST");
                break;
            case 14:
                setPath("./LST Files/SimTest_OG/TPicSim14.LST");
                break;
            case 101:
                setPath("./LST Files/SimTest_OG/TPicSim101.LST");
                break;
        }
    }
}
