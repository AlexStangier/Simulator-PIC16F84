package view;

import controller.*;
import model.Register;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
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
    private JButton initializeButton;
    private JLabel lstLabel0;
    private JLabel lstLabel1;
    private JLabel lstLabel2;
    private JLabel lstLabel3;
    private JLabel stack0;
    private JLabel ps0Label;
    private JLabel ps1Label;
    private JLabel ps2Label;
    private JLabel psaLabel;
    private JLabel t0seLabel;
    private JLabel t0csLabel;
    private JLabel inetdgLabel;
    private JLabel rbpuLabel;
    private JLabel carryLabel;
    private JLabel dcLabel;
    private JLabel zeroLabel;
    private JLabel pdLabel;
    private JLabel toLabel;
    private JLabel rp0Label;
    private JLabel rp1Label;
    private JLabel irpLabel;
    private JLabel rbieLabel;
    private JLabel inteLabel;
    private JLabel t0ieLabel;
    private JLabel eeieLabel;
    private JLabel tmr0Label;
    private JButton rb1Butt;
    private JButton rb2Butt;
    private JButton rb3Butt;
    private JButton RA4T0CKLButton;
    private JButton RA3Button;
    private JButton RA2Button;
    private JButton RA1Button;
    private JButton RA0Button;
    private JPanel RA;
    private JPanel RB;
    private JLabel ra0Label;
    private JLabel ra1LAbel;
    private JLabel ra2Label;
    private JLabel ra3Label;
    private JLabel ra4Label;
    private JLabel ra5LAbel;
    private JLabel ra6Label;
    private JLabel ra7Label;
    private JLabel rb0Label;
    private JLabel rb1Label;
    private JLabel rb2Label;
    private JLabel rb3LAbel;
    private JLabel rb4Label;
    private JLabel rb5Label;
    private JLabel rb6Label;
    private JLabel rb7Label;
    private JTextPane consOut;
    private JTextPane fsrPanel;
    private JTextField consOut2;
    private JLabel intconAmount;
    private JLabel statusAmmount;
    private JLabel optionAmmount;
    private JLabel pa0;
    private JLabel pa1;
    private JLabel pa2;
    private JLabel pa3;
    private JLabel pa4;
    private JLabel pa5;
    private JLabel pa6;
    private JLabel pa7;
    private JLabel pb0;
    private JLabel pb1;
    private JLabel pb2;
    private JLabel pb3;
    private JLabel pb4;
    private JLabel pb5;
    private JLabel pb6;
    private JLabel pb7;
    private JRadioButton cFlagToggleRadioButton;
    private JLabel EEIE;
    private JLabel stack1;
    private JLabel stack2;
    private JLabel stack3;
    private JLabel stack4;
    private JLabel stack5;
    private JLabel stack6;
    private JLabel stack7;
    private Simulator runningSimulation;
    private JTable lstTable;
    private String pathToLST;
    private double timePerCycle = 1;

    /**
     * Settings
     **/
    private int lst = 7;
    private double clockFreq = 4000000;             //4000000MHz default


    private String[] lstArr;
    private Parser psr = new Parser();
    private static Register reg = new Register();
    private String[][] fsrArr;
    private DefaultTableModel fsrModel;
    private Decoder dec = new Decoder();
    private Operation[] opArr;

    public SimulatorGUI() {


        runningSimulation = new Simulator(reg);
        pathToLST = runningSimulation.setPath(lst);
        runningSimulation.setOpCodes();
        lstArr = psr.cleanUpArray(psr.readCommands(pathToLST));
        initView(reg, lstArr);
        calcCycleTime(clockFreq);
        StringBuilder sb = new StringBuilder();


        //Combo Box
        /** switch (comboBox1.getSelectedIndex()) {
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
        @Override public void actionPerformed(ActionEvent e) {
        pathToLST = runningSimulation.setPath(lst);
        lstArr = psr.cleanUpArray(psr.readCommands(pathToLST));
        initView(reg, lstArr);
        }
        });**/


        //Register
        fsrPanel.setText(reg.printRegister(reg.buildArray(reg.getRam_Bank1(), 13, 10)));


        //Start Button
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String con = runningSimulation.startExecuting(lst, 127);
                sb.append(con + "\n");
                consOut2.setText(sb.toString());
                root.updateUI();
                update(lstArr, reg);

            }
        });

        //Step button
        stepButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                consOut2.setText(runningSimulation.executeStep(lst));
                update(lstArr, reg);
            }
        });

        //Reset Button
        reset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reg.resetRegisters();
                updateLabel(reg);
                update(lstArr, reg);

            }
        });

        initializeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                initView(reg, lstArr);
                update(lstArr, reg);
            }
        });

        /** RA Buttons **/

        RA0Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reg.togglePortARegister(0);
                update(lstArr, reg);
            }

        });

        RA1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reg.togglePortARegister(1);
                update(lstArr, reg);
            }

        });

        RA2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reg.togglePortARegister(2);
                update(lstArr, reg);
            }

        });

        RA3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reg.togglePortARegister(3);
                update(lstArr, reg);
            }

        });

        RA4T0CKLButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reg.togglePortARegister(4);
                update(lstArr, reg);
            }

        });


        /** RB Buttons **/

        rb0.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reg.togglePortBRegister(0);
                reg.rb0Interrupt();
                update(lstArr, reg);
            }

        });


        rb1Butt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reg.togglePortBRegister(1);
                update(lstArr, reg);
            }

        });

        rb2Butt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reg.togglePortBRegister(2);
                update(lstArr, reg);
            }

        });

        rb3Butt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reg.togglePortBRegister(3);
                update(lstArr, reg);
            }

        });

        rb4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reg.togglePortBRegister(4);
                update(lstArr, reg);
            }

        });

        rb5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reg.togglePortBRegister(5);

                update(lstArr, reg);
            }

        });

        rb6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reg.togglePortBRegister(6);

                update(lstArr, reg);
            }

        });

        rb7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reg.togglePortBRegister(7);

                update(lstArr, reg);
            }

        });

        //RBIE Radio
        rbportchange.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reg.toggleIntconRegister(3);
                update(lstArr, reg);
            }
        });

        //INTE Radio
        inteButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reg.toggleIntconRegister(4);
                update(lstArr, reg);
            }
        });
        //TOIE Radio
        t0ieButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reg.toggleIntconRegister(5);
                update(lstArr, reg);
            }
        });


        //RA


        //RB1:7 Interrupts

        rb1Butt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reg.rbInterrupt(0);
                update(lstArr, reg);
            }
        });

        rb2Butt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reg.rbInterrupt(1);
                update(lstArr, reg);
            }
        });

        rb3Butt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reg.rbInterrupt(2);
                update(lstArr, reg);
            }
        });

        rb4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reg.rbInterrupt(3);
                update(lstArr, reg);
            }
        });
        rb5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reg.rbInterrupt(4);
                update(lstArr, reg);
            }
        });
        rb6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reg.rbInterrupt(5);
                update(lstArr, reg);
            }
        });
        rb7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reg.rbInterrupt(7);
                update(lstArr, reg);
            }
        });

        gieButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reg.toggleIntconRegister(7);
                update(lstArr, reg);
            }
        });


        cFlagToggleRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reg.toogleCFlag();
                update(lstArr, reg);
            }
        });
    }

    private void updateStack() {
        stack0.setText(Integer.toString(reg.getFromStack_Register(0)));
    }

    private void updateLabel(Register reg) {

        /** Special **/

        tmr0Label.setText(Integer.toString(reg.getTmr0()));

        //Runtime Display
        runtimeLabel.setText((reg.getTmr0() * timePerCycle) + " µs");

        //WREG Display
        wreglabel.setText(String.valueOf(String.format("0x%02X", reg.getWorking_Register())));

        //FSR Display
        fsrlabel.setText(String.valueOf(String.format("0x%02X", reg.getFromFileRegister(4, 1))));

        //PCL Label
        pcllabel.setText(String.valueOf(reg.getProgramm_Counter()));

        /**INTCON**/

        intconAmount.setText(String.valueOf(reg.getIntcon()));

        //GIE
        int gie = ((reg.getIntcon() & 0b1000_0000) >> 7);
        gieLabel.setText(String.valueOf(gie));

        //EEIE
        eeieLabel.setText("0");

        //T0IE
        int toie = ((reg.getIntcon() & 0b0010_0000) >> 5);
        t0ieLabel.setText(String.valueOf(toie));

        //INTE
        inteLabel.setText(String.valueOf((reg.getIntcon() & 0b0001_0000) >> 4));

        //INTE
        rbieLabel.setText(String.valueOf((reg.getIntcon() & 0b0000_1000) >> 3));

        //T0IF
        t0ifLabel.setText(String.valueOf((reg.getIntcon() & 0b0000_0100) >> 2));

        //INTF
        intfLabel.setText(String.valueOf((reg.getIntcon() & 0b0000_0010) >> 1));

        //RBIF
        rbifLabel.setText(String.valueOf((reg.getIntcon() & 0b0000_0001)));

        /** STACK **/

        //Stack
        stack0.setText(Integer.toString(reg.getFromStack_Register(0)));

        /** STATUS **/

        statusAmmount.setText(Integer.toString(reg.getStatus_Register()));

        //Carry
        carryLabel.setText(Integer.toString(reg.getStatus_Register() & 0b0000_0001));

        //DC
        dcLabel.setText(Integer.toString((reg.getStatus_Register() & 0b0000_0010) >> 1));

        //Zero
        zeroLabel.setText(Integer.toString((reg.getStatus_Register() & 0b0000_0100) >> 2));

        //Power Down
        pdLabel.setText(Integer.toString((reg.getStatus_Register() & 0b0000_1000) >> 3));

        //TO
        toLabel.setText(Integer.toString((reg.getStatus_Register() & 0b0001_0000) >> 4));

        //RP0
        int rp0Val = (reg.getStatus_Register() & 0b0010_0000) >> 5;
        rp0Label.setText(Integer.toString((rp0Val)));

        //RP1
        rp1Label.setText(Integer.toString((reg.getStatus_Register() & 0b0100_0000) >> 6));

        //IRP

        /** OPTION **/

        optionAmmount.setText(Integer.toString(reg.getOption_Register()));

        //PS0
        ps0Label.setText(Integer.toString((reg.getOption_Register() & 0b0000_0001)));

        //PS1
        ps1Label.setText(Integer.toString((reg.getOption_Register() & 0b0000_0010) >> 1));

        //PS2
        ps2Label.setText(Integer.toString((reg.getOption_Register() & 0b0000_0100) >> 2));

        //PSA
        psaLabel.setText(Integer.toString((reg.getOption_Register() & 0b0000_1000) >> 3));

        //T0SE
        t0seLabel.setText(Integer.toString((reg.getOption_Register() & 0b0001_0000) >> 4));

        //T0CS
        t0csLabel.setText(Integer.toString((reg.getOption_Register() & 0b0010_0000) >> 5));

        //INETDG
        inetdgLabel.setText(Integer.toString((reg.getOption_Register() & 0b0100_0000) >> 6));

        //RBPU
        rbpuLabel.setText(Integer.toString((reg.getOption_Register() & 0b1000_0000) >> 7));

        /**  RA TRIS  **/


        //ra0
        ra0Label.setText(Integer.toString((reg.getTrisa() & 0b0000_0001)));

        //ra1
        ra1LAbel.setText(Integer.toString((reg.getTrisa() & 0b0000_0010) >> 1));

        //ra2
        ra2Label.setText(Integer.toString((reg.getTrisa() & 0b0000_0100) >> 2));

        //ra3
        ra3Label.setText(Integer.toString((reg.getTrisa() & 0b0000_1000) >> 3));

        //ra4
        ra4Label.setText(Integer.toString((reg.getTrisa() & 0b0001_0000) >> 4));

        //ra5
        ra5LAbel.setText(Integer.toString((reg.getTrisa() & 0b0010_0000) >> 5));

        //ra6
        ra6Label.setText(Integer.toString((reg.getTrisa() & 0b0100_0000) >> 6));

        //ra7
        ra7Label.setText(Integer.toString((reg.getTrisa() & 0b1000_0000) >> 7));

        pa0.setText(Integer.toString(((reg.getPorta() & 0b0000_0001))));
        pa1.setText(Integer.toString(((reg.getPorta() & 0b0000_0010)) >> 1));
        pa2.setText(Integer.toString(((reg.getPorta() & 0b0000_0100)) >> 2));
        pa3.setText(Integer.toString(((reg.getPorta() & 0b0000_1000)) >> 3));
        pa4.setText(Integer.toString(((reg.getPorta() & 0b0001_0000)) >> 4));
        pa5.setText(Integer.toString(((reg.getPorta() & 0b0010_0000)) >> 5));
        pa6.setText(Integer.toString(((reg.getPorta() & 0b0100_0000)) >> 6));
        pa7.setText(Integer.toString(((reg.getPorta() & 0b1000_0000)) >> 7));


        /**  RB TRIS  **/

        //ra0
        rb0Label.setText(Integer.toString((reg.getTrisb() & 0b0000_0001)));

        //ra1
        rb1Label.setText(Integer.toString((reg.getTrisb() & 0b0000_0010) >> 1));

        //ra2
        rb2Label.setText(Integer.toString((reg.getTrisb() & 0b0000_0100) >> 2));

        //ra3
        rb3LAbel.setText(Integer.toString((reg.getTrisb() & 0b0000_1000) >> 3));

        //ra4
        rb4Label.setText(Integer.toString((reg.getTrisb() & 0b0001_0000) >> 4));

        //ra5
        rb5Label.setText(Integer.toString((reg.getTrisb() & 0b0010_0000) >> 5));

        //ra6
        rb6Label.setText(Integer.toString((reg.getTrisb() & 0b0100_0000) >> 6));

        //ra7
        rb7Label.setText(Integer.toString((reg.getTrisb() & 0b1000_0000) >> 7));

        pb0.setText(Integer.toString((reg.getPortb() & 0b0000_0001)));
        pb1.setText(Integer.toString(((reg.getPortb() & 0b0000_0010)) >> 1));
        pb2.setText(Integer.toString(((reg.getPortb() & 0b0000_0100)) >> 2));
        pb3.setText(Integer.toString(((reg.getPortb() & 0b0000_1000)) >> 3));
        pb4.setText(Integer.toString(((reg.getPortb() & 0b0001_0000)) >> 4));
        pb5.setText(Integer.toString(((reg.getPortb() & 0b0010_0000)) >> 5));
        pb6.setText(Integer.toString(((reg.getPortb() & 0b0100_0000)) >> 6));
        pb7.setText(Integer.toString(((reg.getPortb() & 0b1000_0000)) >> 7));

    }

    private void calcCycleTime(double clockFreq) {
        timePerCycle = (double) 4000000 / clockFreq;

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

    private void update(String[] lstArr, Register reg) {
        updateLST(lstArr);
        fsrPanel.setText(reg.printRegister(reg.buildArray(reg.getRam_Bank1(), 13, 10)));
        updateLabel(reg);
        updateStack();
    }

    private void initView(Register reg, String[] lstArr) {
        runtimeLabel.setText(String.valueOf(0));
        wreglabel.setText(String.valueOf(0));
        fsrlabel.setText(String.valueOf(0));
        pcllabel.setText(String.valueOf(0));
        t0ifLabel.setText(String.valueOf(0));
        intfLabel.setText(String.valueOf(0));
        rbifLabel.setText(String.valueOf(0));
        gieLabel.setText(String.valueOf(0));
        rbieLabel.setText(String.valueOf(0));
        inteLabel.setText(String.valueOf(0));
        t0ieLabel.setText(String.valueOf(0));
        eeieLabel.setText(String.valueOf(0));
        carryLabel.setText(String.valueOf(0));
        dcLabel.setText(String.valueOf(0));
        zeroLabel.setText(String.valueOf(0));
        pdLabel.setText(String.valueOf(0));
        toLabel.setText(String.valueOf(0));
        rp0Label.setText(String.valueOf(0));
        rp1Label.setText(String.valueOf(0));
        irpLabel.setText(String.valueOf(0));
        ps0Label.setText(String.valueOf(0));
        ps1Label.setText(String.valueOf(0));
        ps2Label.setText(String.valueOf(0));
        psaLabel.setText(String.valueOf(0));
        t0seLabel.setText(String.valueOf(0));
        t0csLabel.setText(String.valueOf(0));
        inetdgLabel.setText(String.valueOf(0));
        rbpuLabel.setText(String.valueOf(0));
        stack0.setText(String.valueOf(0));
        tmr0Label.setText("0");
        ra0Label.setText(String.valueOf(0));
        ra1LAbel.setText(String.valueOf(0));
        ra2Label.setText(String.valueOf(0));
        ra3Label.setText(String.valueOf(0));
        ra4Label.setText(String.valueOf(0));
        ra5LAbel.setText(String.valueOf(0));
        ra6Label.setText(String.valueOf(0));
        ra7Label.setText(String.valueOf(0));
        rb0Label.setText(String.valueOf(0));
        rb1Label.setText(String.valueOf(0));
        rb2Label.setText(String.valueOf(0));
        rb3LAbel.setText(String.valueOf(0));
        rb4Label.setText(String.valueOf(0));
        rb5Label.setText(String.valueOf(0));
        rb6Label.setText(String.valueOf(0));
        rb7Label.setText(String.valueOf(0));
        lstLabel0.setText("No Operation ");
        lstLabel1.setText("No Operation ");
        lstLabel2.setText("No Operation ");
        lstLabel3.setText("No Operation ");
        pa0.setText("0");
        pa1.setText("0");
        pa2.setText("0");
        pa3.setText("0");
        pa4.setText("0");
        pa5.setText("0");
        pa6.setText("0");
        pa7.setText("0");
        pb0.setText("0");
        pb1.setText("0");
        pb2.setText("0");
        pb3.setText("0");
        pb4.setText("0");
        pb5.setText("0");
        pb6.setText("0");
        pb7.setText("0");

        intconAmount.setText("0");
        statusAmmount.setText("0");
        optionAmmount.setText("0");

        reg.resetRegisters();
        updateLST(lstArr);

    }

    private String[][] initFSR(Register reg) {
        //FSR
        int[] fsrIntArr = new int[130];
        int[] ram_Bank0 = reg.getRam_Bank1();
        for (int i = 0; i < 128; i++) {
            fsrIntArr[i] = ram_Bank0[i];
        }
        fsrIntArr[128] = 0;
        fsrIntArr[129] = 0;
        fsrArr = new String[13][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 13; j++) {
                fsrArr[j][i] = Integer.toString(fsrIntArr[i]);
            }
        }

        return fsrArr;


    }

    private void initialize() {

        runningSimulation = new Simulator(reg);
        pathToLST = runningSimulation.setPath(lst);
        runningSimulation.setOpCodes();
        lstArr = psr.cleanUpArray(psr.readCommands(pathToLST));

    }

    private DefaultTableModel updateFSR() {
        String[] column = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        int[] fsrIntArr = new int[130];
        int[] ram_Bank1 = reg.getRam_Bank1();
        for (int i = 0; i < 128; i++) {
            fsrIntArr[i] = ram_Bank1[i];
        }
        fsrIntArr[128] = 0;
        fsrIntArr[129] = 0;

        fsrModel = new DefaultTableModel();
        fsrArr = new String[13][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 13; j++) {
                fsrArr[j][i] = Integer.toString(fsrIntArr[i]);
            }
        }
        fsrModel.setDataVector(fsrArr, column);
        return fsrModel;
    }

    public static void main(String[] args) {
        //reg = new Register();
        SimulatorGUI sg = new SimulatorGUI();
        JFrame frame = new JFrame("Simulator-PIC16F84");
        frame.setContentPane(new SimulatorGUI().root);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(1250, 800);
        frame.setVisible(true);


    }

    private void createUIComponents() {
        initFSR(reg);
        String[] column = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        fileregisterTable = new JTable(fsrArr, column);
    }
}