package controller;

import model.*;

public class Execution {

    void executeOperation(Operation op, Register reg) {

        switch (op.getTypeDecider()) {
            case BYTE:
                switch (op.getType()) {
                    case ADDWF:
                    case ANDWF:
                    case CLRF:
                    case CLRW:
                    case COMF:
                    case DECF:
                    case DECFSZ:
                    case INCF:
                    case INCFSZ:
                    case IORWF:
                    case MOVF:
                    case MOVWF:
                    case NOP:
                    case RLF:
                    case RRF:
                    case SUBWF:
                    case SWAPF:
                    case XORWF:
                }
                break;
            case BIT:
                switch (op.getType()) {
                    case BCF:
                    case BSF:
                    case BTFSC:
                    case BTFSS:
                }
            case LITERAL:
                switch (op.getType()) {
                    case ADDLW:
                        addlw(op.getLiteral(), reg);
                        break;
                    case ANDLW:
                        andlw(op.getLiteral(), reg);
                        break;
                    case IORLW:
                        iorlw(op.getLiteral(), reg);
                        break;
                    case MOVLW:
                        movlw(op.getLiteral(), reg);
                        break;
                    case RETLW:
                    case SUBLW:
                        sublw(op.getLiteral(), reg);
                        break;
                    case XORLW:
                        xorlw(op.getLiteral(), reg);
                        break;
                }
            case CONTROL:
                switch (op.getType()) {
                    case CALL:
                    case GOTO:
                    case RETURN:
                    case CLRWDT:
                    case SLEEP:
                    case RETFIE:
                }
        }

    }

    //Check for zero values in the Working Regsiter
    public void checkForZero(Register reg) {
        if (reg.getWorking_Register() == 0) {
            //Set Zero Flag
        }
    }


    //Implementation for Literal Operations

    void addlw(int literal, Register reg) {
        byte wReg = reg.getWorking_Register();
        reg.setWorking_Register((byte) (literal + wReg));
    }

    void andlw(int literal, Register reg) {
        byte wReg = reg.getWorking_Register();
        byte toReturn = (byte) (wReg & literal);
        reg.setWorking_Register(toReturn);
    }

    void iorlw(int literal, Register reg) {
        byte wReg = reg.getWorking_Register();
        byte toReturn = (byte) (wReg | literal);
        reg.setWorking_Register(toReturn);
    }

    void movlw(int literal, Register reg) {
        reg.setWorking_Register((byte) literal);
    }

    void retlw(Operation op, Register reg) {
        reg.setWorking_Register((byte) op.literal);
        //TODO missing Program Counter logic, missing Stack logic

    }

    void sublw(int literal, Register reg) {
        byte wReg = reg.getWorking_Register();
        byte toReturn = (byte) (literal - wReg);
        reg.setWorking_Register(toReturn);
    }

    void xorlw(int literal, Register reg) {
        byte wReg = reg.getWorking_Register();
        byte toReturn = (byte) (wReg ^ literal);
        reg.setWorking_Register(toReturn);

    }

    //Implementation for Byte Operations

}
