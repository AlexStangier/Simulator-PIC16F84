package controller;


public class Decoder {

    static Operation determineMoreTypes(int operationCode) {

        Operation operationBlock = new Operation();
        int moreTypesOfOperations = 0b11_1100_0000_0000;
        int toCheck = operationCode & moreTypesOfOperations;


        switch (toCheck) {
            case 0x0009:
                operationBlock.type = OperationType.RETFIE;
                break;
            case 0x0063:
                operationBlock.type = OperationType.SLEEP;
                break;
            case 0x0064:
                operationBlock.type = OperationType.CLRWDT;
                break;
            case 0x0080:
                operationBlock.type = OperationType.MOVWF;
                break;
        }
        return operationBlock;
    }


    static Operation determineType(int operationCode) {
        Operation operationBlock = new Operation();

        int typeOfOperation = 0b11_0000_0000_0000;
        int moreTypesOfOperations = 0b11_1100_0000_0000;

        //Byte Oriented File Register Operation
        int byteOrientedMask = 0b11_1111_0000_0000;
        int destinationBit = 0b00_0000_0100_000000;
        int byteOrientedAddress = 0b0000000011111111;

        //Bit Oriented File Register Operation
        int bitOrientedMask = 0b10_1100_0000_0000;

        int toCheck = operationCode & typeOfOperation;
        int toCheckMore = operationCode & moreTypesOfOperations;

        switch (toCheck) {
            case 0x0000:
                operationBlock.type = OperationType.NOP;
                break;
            case 0x0100:
                operationBlock.type = OperationType.CLRF;
                break;
            case 0x0200:
                operationBlock.type = OperationType.SUBWF;
                break;
            case 0x0300:
                operationBlock.type = OperationType.DECF;
                break;
            case 0x0A00:
                operationBlock.type = OperationType.INCF;
                break;
            case 0x0B00:
                operationBlock.type = OperationType.DECFSZ;
                break;
            case 0x0C00:
                operationBlock.type = OperationType.RRF;
                break;
            case 0x0D00:
                operationBlock.type = OperationType.RLF;
                break;
            case 0x0E00:
                operationBlock.type = OperationType.SWAPF;
                break;
            case 0x0F00:
                operationBlock.type = OperationType.INCFSZ;
                break;
            case 0x0180:
                operationBlock.type = OperationType.ANDWF;
                break;
            case 0x0400:
                operationBlock.type = OperationType.IORWF;
                break;
            case 0x0500:
                operationBlock.type = OperationType.ADDWF;
                break;
            case 0x0600:
                operationBlock.type = OperationType.XORWF;
                break;
            case 0x0700:
                operationBlock.type = OperationType.ADDWF;
                break;
            case 0x0800:
                operationBlock.type = OperationType.MOVF;
                break;
            case 0x0900:
                operationBlock.type = OperationType.COMF;
                break;
            case 0x2000:
                operationBlock.type = OperationType.CALL;
                break;
            case 0x2100:
                operationBlock.type = OperationType.CALL;
                break;
            case 0x2800:
                operationBlock.type = OperationType.GOTO;
                break;
            case 0x3000:
                operationBlock.type = OperationType.MOVLW;
                break;
            case 0x3400:
                operationBlock.type = OperationType.RETLW;
                break;
            case 0x3800:
                operationBlock.type = OperationType.IORLW;
                break;
            case 0x3900:
                operationBlock.type = OperationType.ANDLW;
                break;
            case 0x3A00:
                operationBlock.type = OperationType.XORLW;
                break;
            case 0x3C00:
                operationBlock.type = OperationType.SUBLW;
                break;
            case 0x3E00:
                operationBlock.type = OperationType.ADDLW;
                break;
            default:
                operationBlock = determineMoreTypes(operationCode);
                break;
        }
        return operationBlock;
    }
}
