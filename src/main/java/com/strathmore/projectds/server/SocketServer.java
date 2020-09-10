package com.strathmore.projectds.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(ServerProtocol.SERVER_PORT);
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
            System.err.println(e.getMessage());
        }
    }
}
