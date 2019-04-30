package controller;

public class Operation {

    OperationType type;
    OperationTypeDecider typeDecider;
    int opCode;
    int cycles;
    int destinationBit;
    int fileAddress;
    int literal;
    int bitAddress;

    public void setOpCode(int opCode) {
        this.opCode = opCode;
    }

    public void setType(OperationType type) {
        this.type = type;
    }

    public void setLiteral(int literal) {
        this.literal = literal;
    }

    public void setTypeDecider(OperationTypeDecider typeDecider) {
        this.typeDecider = typeDecider;
    }

    public void setCycles(int cycles) {
        this.cycles = cycles;
    }

    public void setDestinationBit(int destinationBit) {
        this.destinationBit = destinationBit;
    }

    public void setFileAddress(int fileAddress) {
        this.fileAddress = fileAddress;
    }

    public void setBitAddress(int bitAddress) {
        this.bitAddress = bitAddress;
    }

    public OperationType getType() {
        return type;
    }

    public OperationTypeDecider getTypeDecider() {
        return typeDecider;
    }

    public int getCycles() {
        return cycles;
    }

    public int getDestinationBit() {
        return destinationBit;
    }

    public int getFileAddress() {
        return fileAddress;
    }

    public int getBitAddress() {
        return bitAddress;
    }

    public int getLiteral() {
        return literal;
    }

    public int getOpCode() {
        return opCode;
    }
}
