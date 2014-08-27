package com.falconraptor.utilities.logger;

import javax.swing.*;
import java.awt.*;

public class Console extends JFrame {
    private final String log = "[com.falconraptor.utilities.logger.Console.";
    private JTextArea text = new JTextArea();
    private int logitem = 0;
    private Thread updater;

    public Console() {
        super("Console");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setContentPane(setGUI());
        setLocationRelativeTo(null);
        setVisible(true);
        updater = new Thread(new ConsoleUpdater());
        updater.setName("Console Updater");
        setSize(200, 200);
        pack();
    }

    private JPanel setGUI() {
        JPanel p = new JPanel(new GridLayout(1, 1, 0, 0));
        p.add(text);
        text.setEditable(false);
        text.setColumns(100);
        text.setRows(25);
        text.setAutoscrolls(true);
        text.setFont(new Font("Times New Roman", Font.PLAIN, 8));
        return p;
    }

    private class ConsoleUpdater implements Runnable {
        @Override
        public void run() {
            for (int i = logitem; logitem < Logger.log.size(); logitem++) {
                text.setText(text.getText() + Logger.log.get(logitem) + "/n");
            }
            try {
                Thread.sleep(250);
            } catch (Exception e) {
                Logger.logERROR(log + "ConsoleUpdater.run] " + e);
            }
        }
    }
}
