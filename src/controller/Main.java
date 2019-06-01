package controller;


import model.Register;

public class Main {

    public static void main(String[] args) {
        Register register = new Register();
        Simulator sim = new Simulator(register);
        sim.startExecuting(4, 300);

    }
}
