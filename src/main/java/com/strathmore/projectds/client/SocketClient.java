package com.strathmore.projectds.client;

import com.strathmore.projectds.server.ServerProtocol;
import com.strathmore.projectds.server.SocketServer;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketClient {
    public void start(JTextArea textArea, JLabel errorLabel, JButton inputButton, JTextField inputTextField, JLabel serverOutput) {
        try (
                Socket server = new Socket(SocketServer.SERVER_HOST, SocketServer.SERVER_PORT);
                PrintWriter writer = new PrintWriter(server.getOutputStream(), true);
                BufferedReader reader = new BufferedReader(new InputStreamReader(server.getInputStream()))
        ) {
            ClientProtocol protocol = new ClientProtocol();
            ActionListener actionListener = e -> {
                String fromUser = protocol.setOutput(inputTextField, errorLabel, inputButton);
                if (fromUser != null) {
                    textArea.append("CLIENT: " + fromUser + "\n\n");
                    writer.println(fromUser);
                    inputTextField.setText("");
                }
                inputTextField.requestFocus();
            };
            inputButton.addActionListener(actionListener);
            String fromServer;
            while ((fromServer = reader.readLine()) != null) {
                if (fromServer.equals(String.valueOf(ServerProtocol.END))) {
                    serverOutput.setText("SERVER: Server Communication  was Successful and Server is Closing . . .\n");
                    textArea.append("SERVER: Server Communication  was Successful and Server is Closing . . .\n");
                    inputButton.setEnabled(false);
                    inputTextField.setEnabled(false);
                    inputTextField.setEditable(false);
                    inputButton.removeActionListener(actionListener);
                    break;
                }
                serverOutput.setText("SERVER: " + fromServer);
                if (protocol.state == ClientProtocol.THANKS)
                    serverOutput.setText("SERVER: Input a Unique Thank You Message to the Server:");
                textArea.append("SERVER: " + fromServer + "\n\n");
                textArea.moveCaretPosition(textArea.getText().length());

            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Communication was aborted due to: " + e.getMessage(), "From Client: Communication Unsuccessful!", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
