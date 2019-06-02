package view;

import controller.Simulator;
import model.Register;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SimulatorGUI {
    private JPanel root;
    private JTable fileregisterTable;
    private JButton start;
    private JButton rb0;
    private JButton reset;
    private JButton rb4;
    private JButton rb5;
    private JButton rb6;
    private JButton rb7;
    private JTextArea LSTView;
    private JTextField runtimeField;
    private JPanel lstView;
    private JLabel runtimeLabel;
    private JLabel wreglabel;
    private JLabel fsrlabel;
    private JLabel pcllabel;
    private JButton stepButton;
    private JCheckBox GIE;
    private JCheckBox gieButt;
    private JCheckBox inteButt;
    private JCheckBox rbportchange;
    private JLabel gie;
    private JLabel t0ifLabel;
    private JLabel intfLabel;
    private JLabel rbifLabel;
    private JLabel gieLabel;
    private JCheckBox t0ieButt;
    private Simulator runningSimulation;


    public SimulatorGUI() {

        int lst = 6;
        Register reg = new Register();
        reg.resetRegisters();
        runningSimulation = new Simulator(reg);
        runningSimulation.setPath(lst);
        runningSimulation.setOpCodes();


        runtimeLabel.setText(String.valueOf(0));
        wreglabel.setText(String.valueOf(0));
        fsrlabel.setText(String.valueOf(0));
        pcllabel.setText(String.valueOf(0));
        t0ifLabel.setText(String.valueOf(0));
        intfLabel.setText(String.valueOf(0));
        rbifLabel.setText(String.valueOf(0));
        gieLabel.setText(String.valueOf(0));

        //LST


        //FSR Table
        fileregisterTable.setModel(buildTableModel(reg).getModel());

        //Start Button
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                runningSimulation.startExecuting(lst, 121);
                root.updateUI();
                updateSpecialRegister(reg);

            }
        });

        //Step button
        stepButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                runningSimulation.executeStep();
                updateSpecialRegister(reg);
            }
        });


        rb0.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reg.rb0Interrupt();
                updateSpecialRegister(reg);
            }

        });

        //INTE Radio
        rbportchange.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reg.setRBPC();
                updateSpecialRegister(reg);
            }
        });

        //RBIE Radio
        inteButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reg.setRBIE();
                updateSpecialRegister(reg);
            }
        });
        //TOIE Radio
        t0ieButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reg.setTOIE();
                updateSpecialRegister(reg);
            }
        });


        //RB4:7 Interrupts

        rb4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reg.rbInterrupt();
                updateSpecialRegister(reg);
            }
        });
        rb5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reg.rbInterrupt();
                updateSpecialRegister(reg);
            }
        });
        rb6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reg.rbInterrupt();
                updateSpecialRegister(reg);
            }
        });
        rb7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reg.rbInterrupt();
                updateSpecialRegister(reg);
            }
        });

        //Reset Button
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reg.resetRegisters();
                updateSpecialRegister(reg);

            }
        });
        gieButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reg.setGIE();
                updateSpecialRegister(reg);
            }
        });
    }

    private void updateSpecialRegister(Register reg) {


        //Runtime Display
        runtimeLabel.setText(String.valueOf(reg.getTmr0()));

        //WREG Display
        wreglabel.setText(String.valueOf(String.format("0x%02X", reg.getWorking_Register())));

        //FSR Display
        fsrlabel.setText(String.valueOf(String.format("0x%02X", reg.getFromFileRegister(4, 1))));

        //PCL Label
        pcllabel.setText(String.valueOf(reg.getProgramm_Counter()));

        //GIE
        gieLabel.setText(String.valueOf(reg.getIntcon(7)));

        //T0IF
        t0ifLabel.setText(String.valueOf(reg.getIntcon(2)));

        //INTF
        intfLabel.setText(String.valueOf(reg.getIntcon(1)));

        //RBIF
        rbifLabel.setText(String.valueOf(reg.getIntcon(0)));

        //FSR Table
        fileregisterTable.setModel(buildTableModel(reg).getModel());


    }

    private JTable buildTableModel(Register reg) {
        String[][] fsrArray = reg.buildArrayFromFSR(10, 13);
        String[] columnNames = new String[13];
        columnNames[0] = "1";
        columnNames[1] = "2";
        columnNames[2] = "3";
        columnNames[3] = "4";
        columnNames[4] = "5";
        columnNames[5] = "6";
        columnNames[6] = "7";
        columnNames[7] = "8";
        columnNames[8] = "9";
        columnNames[9] = "10";
        columnNames[10] = "11";
        columnNames[11] = "12";
        columnNames[12] = "13";


        JTable model = new JTable(fsrArray, columnNames);

        return model;
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("Simulator-PIC16F84");
        frame.setContentPane(new SimulatorGUI().root);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(1200, 700);
        frame.setVisible(true);


    }
}