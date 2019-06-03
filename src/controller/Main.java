package controller;


import model.Register;

public class Main {

    public static void main(String[] args) {
        Register register = new Register();
        Simulator sim = new Simulator(register);
        register.resetRegisters();
        sim.startExecuting(7, 500);              //1:x   2:x  3:x  4:120 5:x

    }
}
