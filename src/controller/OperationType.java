package controller;

public enum OperationType {

    //  Byte Oriented

    ADDWF, ANDWF, CLRF, CLRW, COMF, DECF, DECFSZ, INCF, INCFSZ, IORWF, MOVF, MOVWF, NOP, RLF, RRF, SUBWF, SWAPF, XORWF,

    //  Bit Oriented

    BCF, BSF, BTFSC, BTFSS,

    //  Literal/Control

    ADDLW, ANDLW, CALL, CLRWDT, GOTO, IORLW, MOVLW, RETFIE, RETLW, RETURN, SLEEP, SUBLW, XORLW;


}
