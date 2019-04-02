package model;

import controller.Main;

public class Register extends Main {

    //Initialization of RAM storage
    byte[] ram_Bank0 = new byte[128];
    byte[] ram_Bank1 = new byte[128];

    //Initialization of the Programm Counter
    byte programm_Counter = 0;

    //Initialization of Programm Memory
    short[] programm_Memory = new short[14336];  //1024 x 14B

    //Initialization of the Working Register
    byte working_Register = 0;




}
