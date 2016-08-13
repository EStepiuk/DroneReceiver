package ua.stepiukyevhen;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class DroneReceiver {

    private static final int PORT = 4444;

    public static void main(String[] args) {
        Socket receiverSocket = null;
        ObjectInputStream receiverInStream = null;
        File servoblaster = new File("/dev/servoblaster");
        FileOutputStream servoblasterOutputStream = null;
        BufferedWriter writer = null;

        try {
            receiverSocket = new ServerSocket(PORT).accept();
            receiverInStream = new ObjectInputStream(receiverSocket.getInputStream());
            servoblasterOutputStream = new FileOutputStream(servoblaster);
            writer = new BufferedWriter(new OutputStreamWriter(servoblasterOutputStream));
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            if (receiverInStream != null && writer != null) {
                try {
                    int[] typr = (int[]) receiverInStream.readObject();
                    for (int i = 0; i < typr.length; i++) {
                        writer.write(i + "=" + typr[i]);
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } else {
                break;
            }
        }

        try {
            receiverInStream.close();
            receiverSocket.close();
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }

    }
}
