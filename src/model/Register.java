package model;

import controller.Main;


public class Register extends Main {

    //Initialization of RAM storage
    static byte[] ram_Bank0 = new byte[128];
    //
    //  ram_Bank0[3]    PCL         Implemented separately
    //  ram_Bank0[4]    STATUS
    //  ram_Bank0[5]    FSR         Implemented separately
    //  ram_Bank0[6]    PORTA
    //  ram_Bank0[7]    PORTB
    //  ram_Bank0[10]   PCLATH      Implemented separately
    //  ram_Bank0[11]   INTCON
    //
    static byte[] ram_Bank1 = new byte[128];
    //
    //  ram_Bank0[3]    PCL         Implemented separately
    //  ram_Bank0[4]    STATUS
    //  ram_Bank0[5]    FSR         Implemented separately
    //  ram_Bank0[6]    TRISA
    //  ram_Bank0[7]    TRISB
    //  ram_Bank0[10]   PCLATH      Implemented separately
    //  ram_Bank0[11]   INTCON
    //


    //Initialization of the Programm Counter
    static byte programm_Counter_Low = 0;                                                  //Program Counter Latch Low     PCL
    static byte programm_Counter_High = 0;                                                 //Program Counter Latch High    PCLATH

    //Initialization of Programm Memory
    static short[] programm_Memory = new short[14336];                                     //1024 x 14B

    //Initialization of the Working Register
    static byte working_Register = 0;

    //Initialization of the Status Register


    ///Initialization of the File Select Register                                          //contains pointer for indirect addressing
    static byte file_Save_Register = 0;

    /**
     * Sets all Registers to null
     */
    public void resetRegisters() {
        programm_Counter_Low = 0;
        programm_Counter_High = 0;
        file_Save_Register = 0;
        working_Register = 0;
        for (int i = 0; i < ram_Bank0.length; i++) {
            ram_Bank0[i] = 0;
            ram_Bank1[i] = 0;
        }
    }

    /**
     * increment the Program Counter by 1 and make sure its value stays below 7 and above 0
     */
    public void incrementPCL() {
        switch (Register.programm_Counter_Low) {
            case 7:
                Register.programm_Counter_Low = 1;
                break;
            default:
                Register.programm_Counter_Low++;
                break;
        }

    }

    /**
     * decrement the Program Counter by 1 and make sure to reset it back to 7 when its value would be smaller than 0
     */
    public void decrementPCL() {
        switch (Register.programm_Counter_Low) {
            case -1:
                Register.programm_Counter_Low = 7;
                break;
            default:
                Register.programm_Counter_Low--;
                break;
        }

    }

    /**
     * Returns the current value of the Program Counter
     *
     * @return an int that represents the current position of the Program Counter
     */
    public byte getProgramm_Counter_Low() {
        return programm_Counter_Low;
    }
}
