package view;

import controller.Simulator;
import model.Register;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


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
    private JRadioButton INTERadioButton;
    private JButton stepButton;
    private JRadioButton RBIERadioButton;
    private JRadioButton TOIERadioButton;
    private JRadioButton GIERadioButton;
    private Simulator runningSimulation;


    public SimulatorGUI() {

        Register reg = new Register();
        reg.resetRegisters();
        runningSimulation = new Simulator(reg);
        runningSimulation.setPath(6);
        runningSimulation.setOpCodes();


        runtimeLabel.setText(String.valueOf(0));
        wreglabel.setText(String.valueOf(0));
        fsrlabel.setText(String.valueOf(0));
        pcllabel.setText(String.valueOf(0));


        //LST


        //Start Button
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                runningSimulation.startExecuting(5);
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
            }
        });

        //INTE Radio
        INTERadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reg.setINTE();
            }
        });

        //RBIE Radio
        RBIERadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reg.setRBIE();
            }
        });
        //TOIE Radio
        TOIERadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reg.setTOIE();
            }
        });
        //GIE Radio
        GIERadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reg.setGIE();
            }
        });

        //RB4:7 Interrupts

        rb4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reg.rbInterrupt();
            }
        });
        rb5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reg.rbInterrupt();
            }
        });
        rb6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reg.rbInterrupt();
            }
        });
        rb7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reg.rbInterrupt();
            }
        });

        //Reset Button
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reg.resetRegisters();

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
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("Simulator-PIC16F84");
        frame.setContentPane(new SimulatorGUI().root);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(1000, 700);
        frame.setVisible(true);


    }
}