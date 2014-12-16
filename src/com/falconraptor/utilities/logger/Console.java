package com.falconraptor.utilities.logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Console extends JFrame {
    private final String log = "[com.falconraptor.utilities.logger.Console.";
    public boolean closed = true;
    private JTextArea text = new JTextArea();
    private ArrayList<String> logtext = new ArrayList<>(0);

    public Console() {
        super("Console");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setContentPane(setGUI());
        setLocationRelativeTo(null);
        setVisible(false);
        setSize(200, 200);
        pack();
        addWindowListener(windowlisten());
    }

    @Override
    public void setVisible (boolean b) {
        closed = b;
        super.setVisible(b);
    }

    private WindowListener windowlisten() {
        return new WindowListener() {
            @Override
            public void windowOpened (WindowEvent e) {
            }
            @Override
            public void windowClosing (WindowEvent e) {
            }
            @Override
            public void windowClosed(WindowEvent e) {
                closed = true;
            }
            @Override
            public void windowIconified (WindowEvent e) {
            }
            @Override
            public void windowDeiconified (WindowEvent e) {
            }
            @Override
            public void windowActivated (WindowEvent e) {
            }
            @Override
            public void windowDeactivated (WindowEvent e) {
            }
        };
    }

    private JPanel setGUI() {
        JPanel p = new JPanel(new GridLayout(1, 1, 0, 0));
        p.add(text);
        text.setEditable(false);
        text.setColumns(50);
        text.setRows(15);
        text.setAutoscrolls(true);
        //text.setLineWrap(true);
        text.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        text.setText("");
        return p;
    }

    public void updateConsole(String obj) {
        if (closed) return;
        logtext.add(obj);
        text.setEditable(true);
        if (logtext.size() > 15) {
            while (logtext.size() > 15) {
                logtext.remove(0);
                text.setText("");
                for (String s : logtext) text.setText(text.getText() + s + System.getProperty("line.separator"));
            }
        } else if (logtext.size() >= 1)
            text.setText(text.getText() + logtext.get(logtext.size() - 1) + System.getProperty("line.separator"));
        text.setEditable(false);
    }
}
