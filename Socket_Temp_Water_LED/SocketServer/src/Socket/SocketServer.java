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

import static Socket.SerialReader.*;

import java.io.*;
 
 
public class SocketServer implements Runnable {  // ���� �ڵ� (���� ���� ���� ������ ����)
    public static final int ServerPort = 3000;
    Scanner sc = new Scanner(System.in);
    public static String read = "0";
    public static int data = 0;

    @Override
    public void run() {
        try {  
            ServerSocket serverSocket = new ServerSocket(ServerPort);//���ϻ���
            System.out.println("Connecting...");
            while (true) {
                //client ���� ���
                Socket client = serverSocket.accept(); //������ ���� ����
                //System.out.println("Receiving...");
                try {
                    //������ ����
                    PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())),true);
                    String msg = a;  // SerialReader���� �о�� ���� �� ����
                    System.out.println(a);
                    out.println(msg);  // ���� �� ���ø����̼����� ��������
                    //������ ����
                    BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    read = input.readLine(); // �ȵ���̵� �ۿ��� ��ư�� �������� ������ ���� �о����
                    if(read.equals("1")) {  // �ø��� ������� �ƽ�Ű �ڵ� ���� ������ �ϹǷ� 1�� 49, 0�� 48�� ��ȯ
                    	data = 49;
                    }else {
                    	data = 48;
                    }
                    Thread.sleep(2000);
                    System.out.println(read);
                } catch (Exception e) {//������ ���۰��������� �������
                    System.out.println("Error");
                    e.printStackTrace();
                } finally {//���� ���� ����
                    client.close();
                    //System.out.println("Done.");
                }
            }
 
        } catch (Exception e) {//���� ���������� �������
            System.out.println("S: Error");
            e.printStackTrace();
        }
 
    }
 
    public static void main(String[] args) {
    	
    	try {
			(new Serial()).connect("COM3");  // �ø��� ��Ʈ
		} catch (Exception e) {
			e.printStackTrace();
		}
        Thread ServerThread = new Thread(new SocketServer());//Thread�� ����
        ServerThread.start();//���� ����
 
    }
 
}
