package controller;


public class Decoder {

    /**
     * Is used to determine between one of the four declared Operation decider types
     *
     * @param opCode Operation Code of which the type is to be determined
     * @param op     Operation Object to flag
     */
    void determineOperationType(int opCode, Operation op) {
        int toCheck = opCode & 0b11_0000_0000_0000;

        switch (toCheck) {
            case 0x0:
                op.setTypeDecider(OperationTypeDecider.BYTE);
                break;
            case 0x1000:
                op.setTypeDecider(OperationTypeDecider.BIT);
                break;
            case 0x3000:
                op.setTypeDecider(OperationTypeDecider.LITERAL);
                break;
            default:
                op.setTypeDecider(OperationTypeDecider.CONTROL);
                break;
        }
        if (op.typeDecider.equals(OperationTypeDecider.BYTE)) {
            switch (opCode & 0b00_0000_1111_1111) {
                case 0x0008:
                    op.setTypeDecider(OperationTypeDecider.CONTROL);
                    break;
                case 0x0009:
                    op.setTypeDecider(OperationTypeDecider.CONTROL);
                    break;
                case 0x0063:
                    op.setTypeDecider(OperationTypeDecider.CONTROL);
                    break;
                case 0x0064:
                    op.setTypeDecider(OperationTypeDecider.CONTROL);
                    break;

            }

        } else if (op.typeDecider.equals(OperationTypeDecider.BYTE)) {
            if ((opCode & 0b00_0000_1000_0000) == 0x0080) {
                op.setTypeDecider(OperationTypeDecider.CONTROL);
            }
        }

    }

    /**
     * Mask an given OpCode with a bit mask to determine Operations
     *
     * @param opCode Operation Code that holds the information used to determine a Operation command
     * @return a masked int
     */
    int byteMask(int opCode) {
        int toReturn = opCode & 0b11_1111_0000_0000;
        return toReturn;
    }

    /**
     * Mask an given OpCode with a bit mask to determine Operations
     *
     * @param opCode Operation Code that holds the information used to determine a Operation command
     * @return a masked int
     */
    int bitMask(int opCode) {
        int toReturn = opCode & 0b11_1100_0000_0000;
        return toReturn;
    }

    /**
     * Mask an given OpCode with a bit mask to determine Operations
     *
     * @param opCode Operation Code that holds the information used to determine a Operation command
     * @return a masked int
     */
    int literalMask(int opCode) {
        int toReturn = (opCode & 0b11_1111_0000_0000);
        return toReturn;
    }

    /**
     * Mask an given OpCode with a bit mask to determine Operations
     *
     * @param opCode Operation Code that holds the information used to determine a Operation command
     * @return a masked int
     */
    int controlMask(int opCode) {
        int toReturn = opCode & 0b11_1000_0000_0000;
        return toReturn;
    }

    /**
     * Is used to retrieve the precise command from a opCode
     *
     * @param opCode int holding an Operation Code
     * @param op     Operation Object to flag
     */
    void handleByte(int opCode, Operation op) {

        determineDestinationBit(opCode, op);
        assignByteAddress(opCode, op);

        switch (byteMask(opCode)) {
            case 0x0700:
                op.setType(OperationType.ADDWF);
                op.setCycles(1);
                break;
            case 0x0500:
                op.setType(OperationType.ANDWF);
                op.setCycles(1);
                break;
            case 0x0100:
                if (op.destinationBit == 1) {
                    op.setType(OperationType.CLRF);
                    op.setCycles(1);
                } else if (op.destinationBit == 0) {
                    op.setType(OperationType.CLRW);
                    op.setCycles(1);
                }
                break;
            case 0x0900:
                op.setType(OperationType.COMF);
                op.setCycles(1);
                break;
            case 0x300:
                op.setType(OperationType.DECF);
                op.setCycles(1);
                break;
            case 0x0B00:
                op.setType(OperationType.DECFSZ);
                op.setCycles(2);
                break;
            case 0x0A00:
                op.setType(OperationType.INCF);
                op.setCycles(1);
                break;
            case 0x0F00:
                op.setType(OperationType.INCFSZ);
                op.setCycles(2);
                break;
            case 0x0400:
                op.setType(OperationType.IORWF);
                op.setCycles(1);
                break;
            case 0x0800:
                op.setType(OperationType.MOVF);
                op.setCycles(1);
                break;
            case 0x0080:
                op.setType(OperationType.MOVWF);
                op.setDestinationBit(1);
                op.setCycles(1);
                break;
            case 0x0000:
                op.setType(OperationType.NOP);
                op.setDestinationBit(0);
                op.setCycles(1);
                break;
            case 0x0D00:
                op.setType(OperationType.RLF);
                op.setCycles(1);
                break;
            case 0x0C00:
                op.setType(OperationType.RRF);
                op.setCycles(1);
                break;
            case 0x0200:
                op.setType(OperationType.SUBWF);
                op.setCycles(1);
                break;
            case 0x0E00:
                op.setType(OperationType.SWAPF);
                op.setCycles(1);
                break;
            case 0x0600:
                op.setType(OperationType.XORWF);
                op.setCycles(1);
                break;
        }

        if ((opCode & 0b00_0000_1000_0000) == 0x80 && op.getType().equals(OperationType.NOP)) {
            op.setType(OperationType.MOVWF);
            op.setDestinationBit(1);
            op.setCycles(1);
        }

    }

    /**
     * Is used to retrieve the precise command from a opCode
     *
     * @param opCode int holding an Operation Code
     * @param op     Operation Object to flag
     */
    void handleBit(int opCode, Operation op) {

        assignBitAddress(opCode, op);
        assignByteAddress(opCode, op);

        switch (bitMask(opCode)) {
            case 0x1000:
                op.setType(OperationType.BCF);
                op.setCycles(1);
                break;
            case 0x1400:
                op.setType(OperationType.BSF);
                op.setCycles(1);
                break;
            case 0x1800:
                op.setType(OperationType.BTFSC);
                op.setCycles(2);
                break;
            case 0x1C00:
                op.setType(OperationType.BTFSS);
                op.setCycles(2);
                break;
        }
    }

    /**
     * Is used to retrieve the precise command from a opCode
     *
     * @param opCode int holding an Operation Code
     * @param op     Operation Object to flag
     */
    void handleLiteral(int opCode, Operation op) {

        assignLiteralAddress(opCode, op);

        switch (literalMask(opCode)) {
            case 0x3E00:
                op.setType(OperationType.ADDLW);
                op.setCycles(1);
                break;
            case 0x3900:
                op.setType(OperationType.ANDLW);
                op.setCycles(1);
                break;
            case 0x3800:
                op.setType(OperationType.IORLW);
                op.setCycles(1);
                break;
            case 0x3000:
                op.setType(OperationType.MOVLW);
                op.setCycles(1);
                op.setDestinationBit(1);
                break;
            case 0x3400:
                op.setType(OperationType.RETLW);
                op.setCycles(2);
                break;
            case 0x3C00:
                op.setType(OperationType.SUBLW);
                op.setCycles(1);
                break;
            case 0x3A00:
                op.setType(OperationType.XORLW);
                op.setCycles(1);
                break;
        }
    }

    /**
     * Is used to retrieve the precise command from a opCode
     *
     * @param opCode int holding an Operation Code
     * @param op     Operation Object to flag
     */
    void handleControl(int opCode, Operation op) {

        assignFileAddress(opCode, op);

        switch (opCode & 0b00_0000_1111_1111) {
            case 0x0008:
                op.setType(OperationType.RETURN);
                op.setCycles(2);
                break;
            case 0x0009:
                op.setType(OperationType.RETFIE);
                op.setCycles(2);
                break;
            case 0x0063:
                op.setType(OperationType.SLEEP);
                op.setCycles(1);
                break;
            case 0x0064:
                op.setType(OperationType.CLRWDT);
                op.setCycles(1);
                break;
        }

        if ((opCode & 0b00_0000_1000_0000) == 0x80) {
            op.setType(OperationType.MOVWF);            //extra check
            op.setDestinationBit(1);
        }

        switch (controlMask(opCode)) {
            case 0x2800:
                op.setType(OperationType.GOTO);
                op.setCycles(2);
                break;
            case 0x2000:
                op.setType(OperationType.CALL);

                op.setCycles(2);
                break;
        }
    }

    /**
     * Method used to call the right handle  method based on a OperationTypeDecider field
     *
     * @param opCode int containing a Operation Code
     * @param op     Operation Object to flag
     */
    void determineCommand(int opCode, Operation op) {

        switch (op.typeDecider) {
            case BYTE:
                handleByte(opCode, op);
                break;
            case BIT:
                handleBit(opCode, op);
                break;
            case LITERAL:
                handleLiteral(opCode, op);
                break;
            case CONTROL:
                handleControl(opCode, op);
                break;
        }


    }


    /**
     * Is used to determine if the destination Bit is set or unset
     *
     * @param opCode int containing a Operation Code
     * @param op     Operation Object from which the destination Bit is determined from
     */
    void determineDestinationBit(int opCode, Operation op) {
        int toCheck = opCode & 0b00_0000_1000_0000;
        switch (toCheck) {
            case 0x80:
                op.setDestinationBit(1);
                break;
            default:
                op.setDestinationBit(0);
        }
    }

    /**
     * Is used to retrieve a Bit Address from a given opCode
     *
     * @param opCode int holding an operation code
     * @param op     Operation Object from which the Bit Address is determined from
     */
    void assignBitAddress(int opCode, Operation op) {
        int toCheck = opCode & 0b00_0011_1000_0000;
        op.setBitAddress(toCheck);
    }

    private void assignByteAddress(int opCode, Operation op) {
        int toCheck = opCode & 0b00_0000_0111_1111;
        op.setFileAddress(toCheck);
    }

    /**
     * Is used to assign a Literal address from a operation Code to an Operation object
     *
     * @param opCode int holding an operation code
     * @param op     Operation Object to which the address is going to be assigned
     */
    void assignLiteralAddress(int opCode, Operation op) {
        int toCheck = opCode & 0xFF;
        op.setLiteral(toCheck);
    }

    /**
     * Is used to assign a Control Operation address from a operation Code to an Operation object
     *
     * @param opCode int holding an operation code
     * @param op     Operation Object to which the address is going to be assigned
     */
    void assignFileAddress(int opCode, Operation op) {
        int toCheck = opCode & 0b00_0111_1111_1111;
        op.setFileAddress(toCheck);
    }
}
