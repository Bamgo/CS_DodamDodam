package Socket;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import static Socket.SerialReader.a;

import java.io.*;
 
 
public class SocketServer implements Runnable {
    public static final int ServerPort = 3000;
    Scanner sc = new Scanner(System.in);

    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(ServerPort);//���ϻ���
            System.out.println("Connecting...");
            while (true) {
                //client ���� ���
                Socket client = serverSocket.accept(); //������ ���� ����
                System.out.println("Receiving...");
                try {
                	
                	
                    //������ ����
                    PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())),true);
                    String msg = a;
                    System.out.println(a);
                    out.println(msg);
                    //������ ����
                    BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    String read = input.readLine(); 
                    System.out.println(read);
                } catch (Exception e) {//������ ���۰��������� �������
                    System.out.println("Error");
                    e.printStackTrace();
                } finally {//���� ���� ����
                    client.close();
                    System.out.println("Done.");
                }
            }
 
        } catch (Exception e) {//���� ���������� �������
            System.out.println("S: Error");
            e.printStackTrace();
        }
 
    }
 
    public static void main(String[] args) {
    	
    	try {
			(new Serial()).connect("COM3");
		} catch (Exception e) {
			e.printStackTrace();
		}
        Thread ServerThread = new Thread(new SocketServer());//Thread�� ����
        ServerThread.start();//���� ����
 
    }
 
}
