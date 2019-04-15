package controller;

import model.Register;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExecutionTest extends Execution {
    Register reg = new Register();


    @Test
    void addlw1() {
        reg.setWorking_Register((byte) 0);
        int literal = 0x11;
        addlw(literal, reg);
        assertEquals(0x11, reg.getWorking_Register());
    }

    @Test
    void andlw1() {
        reg.setWorking_Register((byte) 0x11);
        int literal = 0x30;
        andlw(literal,reg);
        assertEquals(0x10,reg.getWorking_Register());
    }

    @Test
    void iorlw1() {
        reg.setWorking_Register((byte) 0);
        int literal = 0x1;
        iorlw(literal,reg);
        assertEquals(1,reg.getWorking_Register());
    }

    @Test
    void movlw1() {
        reg.setWorking_Register((byte) 0);
        int literal = 0x11;
        movlw(literal,reg);
        assertEquals(0x11,reg.getWorking_Register());

    }

    @Test
    void retlw1() {
        reg.setWorking_Register((byte) 0);

    }

    @Test
    void sublw1() {
        reg.setWorking_Register((byte) 1);
        int literal = 0x2;
        sublw(literal,reg);
        assertEquals(1,reg.getWorking_Register());
    }

    @Test
    void xorlw1() {
        reg.setWorking_Register((byte) 1);
        int literal = 0x1;
        xorlw(literal,reg);
        assertEquals(0,reg.getWorking_Register());
    }
}