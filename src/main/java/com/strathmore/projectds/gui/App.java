package com.strathmore.projectds.gui;

import com.strathmore.projectds.client.SocketClient;
import com.strathmore.projectds.server.SocketServer;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class App {
    private JPanel panelMain;
    private JLabel labelHeader;
    private JPanel panelInput;
    private JLabel labelClientInput;
    private JTextField fieldClientInput;
    private JButton buttonClientInput;
    private JPanel panelButton;
    private JLabel labelError;
    private JTextArea areaContent;
    private JScrollPane scrollArea;
    private JLabel labelServerOutput;

    public App() {
        fieldClientInput.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) buttonClientInput.doClick();
            }
        });
        new Thread(() -> new SocketServer().start()).start();
        new Thread(() -> new SocketClient().start(areaContent, labelError, buttonClientInput, fieldClientInput, labelServerOutput)).start();
    }

    public static void main(String[] args) {
        SocketServer.setLookAndFeel();
        JFrame mainFrame = new JFrame("Distributed Systems Project");
        App app = new App();
        mainFrame.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
        mainFrame.setContentPane(app.panelMain);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }
}
