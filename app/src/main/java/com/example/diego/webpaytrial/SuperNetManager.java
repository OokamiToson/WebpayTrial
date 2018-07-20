package com.example.diego.webpaytrial;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;

public class SuperNetManager {

    ServerSocket serverSocket;
    int portReturn = 8888;
    int portFinal = 8899;

    public void initReturnServer(){

    }

    public void closeReturnServer(){

    }

    //This code was stolen from the internet c:
    private String getIpAddress(){
        String ip = "";
        try {
            Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (enumNetworkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = enumNetworkInterfaces
                        .nextElement();
                Enumeration<InetAddress> enumInetAddress = networkInterface
                        .getInetAddresses();
                while (enumInetAddress.hasMoreElements()) {
                    InetAddress inetAddress = enumInetAddress.nextElement();

                    if (inetAddress.isSiteLocalAddress()) {
                        ip += inetAddress.getHostAddress() + "\n";
                    }
                }
            }
        } catch (Exception ex) {
            // TODO Auto-generated catch block
            ex.printStackTrace();
            ip += "Something Wrong! " + ex.toString() + "\n";
        }

        return ip;
    }

    private class ReturnServerThread extends Thread {

        public ReturnServerThread(){}

        public void run(){
            Socket socket = null;
            String response = "";
            String aux;
            try {
                serverSocket = new ServerSocket(portReturn);
                while (true) {
                    socket = serverSocket.accept();
                    BufferedReader bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    while((aux = bf.readLine()) != null) {
                        response += aux;
                    }

                    socket.close();
                }
            }
            catch (Exception ex) {

            }
        }
    }
}