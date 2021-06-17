/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.chat.libs;

import javax.swing.*;
import java.io.*;
import java.net.Socket;


/**
 * @author DELL
 */
public class ChatMessage {
    private Socket socket;
    private JTextPane txpMessageBoard;
    private PrintWriter out;
    private BufferedReader reader;


    //    private DataInputStream dis;
//    //private HashMap<String, JTextPane> chatWindows = new HashMap<String, JTextPane>();
//    private String username;


    /**
     * Insert a new message into chat pane.
     */

    public ChatMessage(Socket socket, JTextPane txpMessageBoard) throws IOException {
        this.socket = socket;
        this.txpMessageBoard = txpMessageBoard;
        out = new PrintWriter(socket.getOutputStream());
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        receive();
    }

    private void receive() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        String line = reader.readLine();
                        if (line != null) {
                            txpMessageBoard.setText(txpMessageBoard.getText() + "\n" + line);
                        }
                    } catch (IOException exception) {

                    }

                }


            }
        };
        thread.start();

    }


    public void send(String msg) {
        String current = txpMessageBoard.getText();
        txpMessageBoard.setText(current + "\nSend: " + msg);
        out.println(msg);
        out.flush();
    }

    public void sendFile() {
        // File file=new File();
    }

    private void close() {//su dung dong luong
        try {
            out.close();
            reader.close();
            socket.close();
        } catch (IOException exception) {

        }
    }




}



