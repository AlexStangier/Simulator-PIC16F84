package view;

import controller.LSTLabel;
import controller.Parser;
import controller.Simulator;
import model.Register;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


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
    private JComboBox comboBox1;
    private JButton initializeButton;
    private JLabel lstLabel0;
    private JLabel lstLabel1;
    private JLabel lstLabel2;
    private JLabel lstLabel3;
    private Simulator runningSimulation;
    private JTable lstTable;

    private String pathToLST;
    private int lst = 1;
    private String[] lstArr;

    Parser psr = new Parser();
    Register reg = new Register();


    public SimulatorGUI() {


        runningSimulation = new Simulator(reg);
        pathToLST = runningSimulation.setPath(lst);
        runningSimulation.setOpCodes();
        lstArr = psr.cleanUpArray(psr.readCommands(pathToLST));
        initView(reg, lstArr);


        //Combo Box
        switch (comboBox1.getSelectedIndex()) {
            case 0:
                lst = 1;
                break;
            case 1:
                lst = 2;
                break;
            case 2:
                lst = 3;
                break;
            case 3:
                lst = 4;
                break;
            case 4:
                lst = 5;
                break;
            case 5:
                lst = 6;
                break;
            case 6:
                lst = 7;
                break;
            case 7:
                lst = 8;
                break;
            case 8:
                lst = 9;
                break;
            default:
                lst = 0;
                break;
        }
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initView(reg, lstArr);
            }
        });

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
                updateLST(lstArr);
            }
        });

        initializeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initView(reg, lstArr);
                updateLST(lstArr);
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

        //RBIE Radio
        rbportchange.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reg.toggleRegister(3);
                updateSpecialRegister(reg);
            }
        });

        //INTE Radio
        inteButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reg.toggleRegister(4);
                updateSpecialRegister(reg);
            }
        });
        //TOIE Radio
        t0ieButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reg.toggleRegister(5);
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
                updateLST(lstArr);

            }
        });
        gieButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reg.toggleRegister(7);
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
        gieLabel.setText(String.valueOf(reg.getIntcon()));

        //T0IF
        t0ifLabel.setText(String.valueOf(reg.getIntcon()));

        //INTF
        intfLabel.setText(String.valueOf(reg.getIntcon()));

        //RBIF
        rbifLabel.setText(String.valueOf(reg.getIntcon()));

    }

    private void updateLST(String[] lstArr) {
        //LST VIEW
        try {
            lstLabel0.setText(lstArr[reg.getProgramm_Counter() - 1]);
            lstLabel1.setText(lstArr[reg.getProgramm_Counter()]);
            lstLabel2.setText(lstArr[reg.getProgramm_Counter() + 1]);
            lstLabel3.setText(lstArr[reg.getProgramm_Counter() + 2]);
        } catch (IndexOutOfBoundsException e) {
        }
        lstLabel1.setForeground(Color.gray);
        lstLabel2.setForeground(Color.gray);
        lstLabel3.setForeground(Color.gray);
    }

    private void initView(Register reg, String[] lstArr) {
        reg.resetRegisters();
        updateLST(lstArr);
        runtimeLabel.setText(String.valueOf(0));
        wreglabel.setText(String.valueOf(0));
        fsrlabel.setText(String.valueOf(0));
        pcllabel.setText(String.valueOf(0));
        t0ifLabel.setText(String.valueOf(0));
        intfLabel.setText(String.valueOf(0));
        rbifLabel.setText(String.valueOf(0));
        gieLabel.setText(String.valueOf(0));
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