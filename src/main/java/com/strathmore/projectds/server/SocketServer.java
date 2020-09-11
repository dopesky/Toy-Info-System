package com.strathmore.projectds.server;

import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.PlasticXPLookAndFeel;
import com.jgoodies.looks.plastic.theme.ExperienceBlue;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    public static final int SERVER_PORT = 5941;
    public static final String SERVER_HOST = "localhost";

    public void start() {
        setLookAndFeel();
        try {
            ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
            Socket client = serverSocket.accept();
            try (PrintWriter writer = new PrintWriter(client.getOutputStream(), true);
                 BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()))) {
                String inputLine, outputLine;
                ServerProtocol protocol = new ServerProtocol();
                outputLine = protocol.getOutput(null);
                writer.println(outputLine);

                while ((inputLine = reader.readLine()) != null) {
                    outputLine = protocol.getOutput(inputLine);
                    writer.println(outputLine);
                    if (outputLine.equals(String.valueOf(ServerProtocol.END))) break;
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Communication was aborted due to: " + e.getMessage(), "From Server: Communication Unsuccessful!", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void setLookAndFeel() {
        try {
            PlasticLookAndFeel windowsLookAndFeel = new PlasticXPLookAndFeel();
            PlasticLookAndFeel.set3DEnabled(true);
            PlasticLookAndFeel.setCurrentTheme(new ExperienceBlue());
            UIManager.setLookAndFeel(windowsLookAndFeel);
        } catch (UnsupportedLookAndFeelException ignored) {
        }
    }
}
