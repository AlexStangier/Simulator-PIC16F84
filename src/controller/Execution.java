package controller;

import model.Register;

public class Execution {

    void executeOperation(Operation op, Register reg) {

        switch (op.getTypeDecider()) {
            case BYTE:
                switch (op.getType()) {
                    case ADDWF:
                        addwf(op, reg);
                        break;
                    case ANDWF:
                        andwf(op, reg);
                        break;
                    case CLRF:
                        clrf(op, reg);
                        break;
                    case CLRW:
                        clrw(reg);
                        break;
                    case COMF:
                        comf(op, reg);
                        break;
                    case DECF:
                        decf(op, reg);
                        break;
                    case DECFSZ:
                        decfsz(op, reg);
                        break;
                    case INCF:
                        incf(op, reg);
                        break;
                    case INCFSZ:
                        incfsz(op, reg);
                    case IORWF:
                        iorwf(op, reg);
                        break;
                    case MOVF:
                        movf(op, reg);
                        break;
                    case MOVWF:
                        movwf(op, reg);
                        break;
                    case NOP:
                        nop();
                        break;
                    case RLF:
                        rlf(op, reg);
                        break;
                    case RRF:
                        rrf(op, reg);
                        break;
                    case SUBWF:
                        subwf(op, reg);
                        break;
                    case SWAPF:
                        swapf(op, reg);
                        break;
                    case XORWF:
                        xorwf(op, reg);
                        break;
                }
                break;
            case BIT:
                switch (op.getType()) {
                    case BCF:
                        bcf(op, reg);
                        break;
                    case BSF:
                        bsf(op, reg);
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
                        retlw(op.getLiteral(), reg);
                        break;
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
                        call(op, reg);
                        break;
                    case GOTO:
                        gotoCommand(op, reg);
                        break;
                    case RETURN:
                        returnCommand(op, reg);
                        break;
                    case CLRWDT:
                    case SLEEP:
                    case RETFIE:
                }
        }

    }

    //Implementation for Literal Operations

    void addlw(int literal, Register reg) {
        int wReg = reg.getWorking_Register();
        int result = literal + wReg;
        reg.setWorking_Register(result);
        reg.checkForStatusFlags(result);

    }

    void andlw(int literal, Register reg) {
        int wReg = reg.getWorking_Register();
        int result = literal & wReg;
        int toReturn = result;
        reg.setWorking_Register(toReturn);
        reg.checkForZeroFlag(result);
    }

    void iorlw(int literal, Register reg) {
        int wReg = reg.getWorking_Register();
        int toReturn = wReg | literal;
        reg.setWorking_Register(toReturn);
        reg.checkForZeroFlag(toReturn);
    }

    void movlw(int literal, Register reg) {
        reg.setWorking_Register(literal);
    }


    void retlw(int literal, Register reg) {
        reg.setWorking_Register(literal);
        reg.setProgramm_Counter(reg.getStack_Register(0));
    }


    void sublw(int literal, Register reg) {
        int wReg = reg.getWorking_Register();
        int toReturn = (literal - wReg);
        reg.setWorking_Register(reg.checkForStatusFlags(toReturn));
    }

    void xorlw(int literal, Register reg) {
        int wReg = reg.getWorking_Register();
        int toReturn = (wReg ^ literal);
        reg.setWorking_Register(toReturn);

    }

    //Implementation for Byte Operations

    void addwf(Operation op, Register reg) {
        int address = reg.getFromFileRegister(op.getFileAddress(), op.getDestinationBit());
        int contentWReg = reg.getWorking_Register();
        int toCalc = (contentWReg + address);
        saveToRegister(op, reg, toCalc);
    }

    void andwf(Operation op, Register reg) {
        int address = reg.getFromFileRegister(op.getFileAddress(), op.getDestinationBit());
        int contentWReg = reg.getWorking_Register();
        int toCalc = (contentWReg & address);
        saveToRegister(op, reg, toCalc);
    }

    void clrf(Operation op, Register reg) {
        reg.writeToFileRegister(op, 0);
        reg.setZeroFlag();
    }

    void clrw(Register reg) {
        reg.setWorking_Register(0);
        reg.setZeroFlag();
    }

    void comf(Operation op, Register reg) {
        int toCalc = calcComplement(reg.getFromFileRegister(op.getFileAddress(), op.getDestinationBit()));
        saveToRegister(op, reg, toCalc);
    }

    void decf(Operation op, Register reg) {
        int toCalc = reg.getFromFileRegister(op.getFileAddress(), op.getDestinationBit());
        toCalc--;
        saveToRegister(op, reg, toCalc);
    }

    void decfsz(Operation op, Register reg) {
        int toCalc = reg.getFromFileRegister(op.getFileAddress(), op.getDestinationBit());
        toCalc--;
        if (toCalc <= 0) {
            nop();
        }
        saveToRegister(op, reg, toCalc);
    }

    void incf(Operation op, Register reg) {
        int toCalc = reg.getFromFileRegister(op.getFileAddress(), op.getDestinationBit());
        toCalc++;
        saveToRegister(op, reg, toCalc);
    }

    void incfsz(Operation op, Register reg) {
        int toCalc = reg.getFromFileRegister(op.getFileAddress(), op.getDestinationBit());
        toCalc++;
        if (toCalc <= 0) {
            nop();
        }
        saveToRegister(op, reg, toCalc);
    }

    void iorwf(Operation op, Register reg) {
        int toCalc = reg.getWorking_Register() | reg.getFromFileRegister(op.getFileAddress(), op.getDestinationBit());
        saveToRegister(op, reg, toCalc);
    }

    void movf(Operation op, Register reg) {
        int toMove = reg.getFromFileRegister(op.getFileAddress(), op.getDestinationBit());
        saveToRegister(op, reg, toMove);
    }

    void movwf(Operation op, Register reg) {
        reg.writeToFileRegister(op, reg.getWorking_Register());
    }

    void nop() {

    }

    void rlf(Operation op, Register reg) {
        int mask = 0b1111_1111;
        int toRotate = reg.getFromFileRegister(op.getFileAddress(), op.getDestinationBit());
        toRotate = (toRotate << 1) & mask;
        saveToRegister(op, reg, toRotate);
        reg.setCarryFlag();
    }

    void rrf(Operation op, Register reg) {
        int mask = 0b1111_1111;
        int toRotate = reg.getFromFileRegister(op.getFileAddress(), op.getDestinationBit());
        toRotate = (toRotate >> 1) & mask;
        saveToRegister(op, reg, toRotate);
    }

    void subwf(Operation op, Register reg) {
        int toCalc = reg.getFromFileRegister(op.getFileAddress(), op.getDestinationBit()) - reg.getWorking_Register();
        saveToRegister(op, reg, toCalc);
    }

    void swapf(Operation op, Register reg) {
        int lowerNibbles = (calcLowerNibbles(op.opCode) << 4) & 0b1111_11111;
        int upperNibbles = (calcUpperNibbles(op.opCode) >> 4) & 0b1111_11111;
        int toCalc = (lowerNibbles & upperNibbles) & 0b1111_1111;
        saveToRegister(op, reg, toCalc);
    }

    void xorwf(Operation op, Register reg) {
        int toCalc = reg.getWorking_Register() ^ reg.getFromFileRegister(op.getFileAddress(), op.getDestinationBit());
        saveToRegister(op, reg, toCalc);
    }


    //Implementation for Bit Operations

    void bcf(Operation op, Register reg) {
        int fromFileReg = reg.getFromFileRegister(op.getFileAddress(), op.getDestinationBit());
        int mask = calcMaskToUnsetBit(op.getBitAddress());
        fromFileReg = fromFileReg & mask;
        reg.writeToFileRegister(op, fromFileReg);
    }

    void bsf(Operation op, Register reg) {
        int fromFileReg = reg.getFromFileRegister(op.getFileAddress(), op.getDestinationBit());
        int mask = calcMaskToSetBit(op.getBitAddress());
        fromFileReg = fromFileReg | mask;
        reg.writeToFileRegister(op, fromFileReg);
    }

    //Implementations for Control Operations

    void gotoCommand(Operation op, Register reg) {
        int newPCL = op.getFileAddress();
        reg.setProgramm_Counter(newPCL - 1);
    }

    void call(Operation op, Register reg) {
        reg.push(op.getFileAddress(), reg);
        reg.push(reg.getProgramm_Counter() + 1, reg);
        reg.incrementStackPointer();                                                            //TODO check if necessary
    }

    void returnCommand(Operation op, Register reg) {
        int returnTo = reg.pop();
        reg.setStack_Register(reg, returnTo);
    }


    int calcComplement(int i) {
        int ones = (Integer.highestOneBit(i) << 1) - 1;
        return (i ^ ones) + 1;
    }

    int calcLowerNibbles(int opCode) {
        int mask = 0b00_0000_0000_1111;
        int toReturn = opCode & mask;
        return toReturn;
    }

    int calcUpperNibbles(int opCode) {
        int mask = 0b00_0000_1111_0000;
        int toReturn = opCode & mask;
        return toReturn;
    }

    void saveToRegister(Operation op, Register reg, int toCalc) {
        toCalc = reg.checkForStatusFlags(toCalc);
        switch (op.getDestinationBit()) {
            case 0:
                reg.setWorking_Register(toCalc);
                break;
            case 1:
                reg.writeToFileRegister(op, toCalc);
                break;
        }
    }

    int calcMaskToUnsetBit(int bitAdress) {
        int mask = 0;
        switch (bitAdress) {
            case 0:
                mask = 0b1111_1110;
                break;
            case 1:
                mask = 0b1111_1101;
                break;
            case 2:
                mask = 0b1111_1011;
                break;
            case 3:
                mask = 0b1111_0111;
                break;
            case 4:
                mask = 0b1110_1111;
                break;
            case 5:
                mask = 0b1101_1111;
                break;
            case 6:
                mask = 0b1011_1111;
                break;
            case 7:
                mask = 0b0111_1111;
                break;
        }
        return mask;
    }

    int calcMaskToSetBit(int bitAdress) {
        int mask = 0;
        switch (bitAdress) {
            case 0:
                mask = 0b0000_0001;
                break;
            case 1:
                mask = 0b0000_0010;
                break;
            case 2:
                mask = 0b0000_0100;
                break;
            case 3:
                mask = 0b0000_1000;
                break;
            case 4:
                mask = 0b0001_0000;
                break;
            case 5:
                mask = 0b0010_0000;
                break;
            case 6:
                mask = 0b0100_0000;
                break;
            case 7:
                mask = 0b1000_0000;
                break;
        }
        return mask;
    }
}
