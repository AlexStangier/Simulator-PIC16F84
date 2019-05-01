package controller;

import model.Register;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExecutionTest extends Execution {
    Register reg = new Register();
    Operation op = new Operation();
    int workingReg;
    int fileReg;

    @BeforeEach
    void setUp() {
        reg.resetRegisters();
        op.setFileAddress(0xF);
        reg.setWorking_Register(0x1);
        reg.writeToFileRegister(op, 0x82);

        workingReg = reg.getWorking_Register();
        fileReg = reg.getFromFileRegister(op.getFileAddress(), op.getDestinationBit());
    }

    @AfterEach
    void tearDown() {
        op.setDestinationBit(0);
        op.setFileAddress(0);
        op.setOpCode(0);
        op.setLiteral(0);
        op.setType(null);
        op.setTypeDecider(null);
        op.setBitAddress(0);
    }

    @Test
    void fileRegsiterIO1() {
        reg.writeToFileRegister(op, 0x7C);
        assertEquals(0x7C, reg.getFromFileRegister(op.getFileAddress(), op.getDestinationBit()));
    }

    @Test
    void fileRegsiterIO2() {
        op.setDestinationBit(1);
        reg.writeToFileRegister(op, 0x7C);
        assertEquals(0x7C, reg.getFromFileRegister(op.getFileAddress(), op.getDestinationBit()));
    }

    @Test
    void fileRegsiterIO3() {
        op.setFileAddress(0xF);
        reg.writeToFileRegister(op, 0x7C);
        assertEquals(0x7C, reg.getFromFileRegister(op.getFileAddress(), op.getDestinationBit()));
    }

    @Test
    void addlw1() {
        reg.setWorking_Register(0);
        int literal = 0x11;
        addlw(literal, reg);
        assertEquals(0x11, reg.getWorking_Register());
    }

    @Test
    void andlw1() {
        reg.setWorking_Register(0x11);
        int literal = 0x30;
        andlw(literal, reg);
        assertEquals(0x10, reg.getWorking_Register());
    }

    @Test
    void iorlw1() {
        reg.setWorking_Register(0);
        int literal = 0x1;
        iorlw(literal, reg);
        assertEquals(1, reg.getWorking_Register());
    }

    @Test
    void movlw1() {
        reg.setWorking_Register(0);
        int literal = 0x11;
        movlw(literal, reg);
        assertEquals(0x11, reg.getWorking_Register());

    }

    @Test
    void retlw1() {
        reg.setWorking_Register(0);

    }

    @Test
    void sublw1() {
        reg.setWorking_Register(1);
        int literal = 0x2;
        sublw(literal, reg);
        assertEquals(1, reg.getWorking_Register());
    }

    @Test
    void xorlw1() {
        reg.setWorking_Register(1);
        int literal = 0x1;
        xorlw(literal, reg);
        assertEquals(0, reg.getWorking_Register());
    }

    @Test
    void addwf1() {
        addwf(op, reg);
        assertEquals(0x83, reg.getWorking_Register());
    }

    @Test
    void andwf1() {
        andwf(op, reg);
        assertEquals(workingReg & fileReg, reg.getWorking_Register());
    }

    @Test
    void clrwf1() {
        clrw(reg);
        assertEquals(0, reg.getWorking_Register());
    }

    @Test
    void comf1() {
        comf(op, reg);
        assertEquals(calcComplement(fileReg), reg.getWorking_Register());
    }

    @Test
    void decf1() {
        decf(op, reg);
        assertEquals(fileReg - 1, reg.getWorking_Register());
    }

    @Test
    void incf1() {
        incf(op, reg);
        assertEquals(fileReg + 1, reg.getWorking_Register());

    }

    @Test
    void iorwf1() {
        iorwf(op, reg);
        assertEquals(workingReg | fileReg, reg.getWorking_Register());
    }

    @Test
    void movf1() {
        movf(op, reg);
        assertEquals(fileReg, reg.getWorking_Register());
    }

    @Test
    void movwf1() {
        movwf(op, reg);
        assertEquals(workingReg, reg.getFromFileRegister(op.getFileAddress(), op.getDestinationBit()));
    }

    @Test
    void subwf1() {
        subwf(op, reg);
        assertEquals(fileReg - workingReg, reg.getWorking_Register());
    }

    @Test
    void swapf1() {
        op.setOpCode(0b1111_0000);
        swapf(op, reg);
        assertEquals(0b0000_1111, reg.getWorking_Register());
    }

    @Test
    void rrf1() {
        rrf(op, reg);
        assertEquals((fileReg >> 1) & 0b1111_1111, reg.getWorking_Register());
    }

    @Test
    void rlf1() {
        rlf(op, reg);
        assertEquals((fileReg << 1) & 0b1111_1111, reg.getWorking_Register());
    }

    @Test
    void xorwf1() {
        xorwf(op, reg);
        assertEquals(workingReg ^ fileReg, reg.getWorking_Register());
    }

    @Test
    void bcf1() {
        op.setBitAddress(1);
        bcf(op, reg);
        assertEquals(0x80, reg.getFromFileRegister(op.getFileAddress(), op.getDestinationBit()));
    }

    @Test
    void bsf1() {
        op.setBitAddress(4);
        bsf(op, reg);
        assertEquals(0x92, reg.getFromFileRegister(op.getFileAddress(), op.getDestinationBit()));
    }
}