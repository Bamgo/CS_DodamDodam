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
            ServerSocket serverSocket = new ServerSocket(ServerPort);//소켓생성
            System.out.println("Connecting...");
            while (true) {
                //client 접속 대기
                Socket client = serverSocket.accept(); //데이터 전송 감지
                System.out.println("Receiving...");
                try {
                	
                	
                    //데이터 전송
                    PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())),true);
                    String msg = a;
                    System.out.println(a);
                    out.println(msg);
                    //데이터 수신
                    BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    String read = input.readLine(); 
                    System.out.println(read);
                } catch (Exception e) {//데이터 전송과정에서의 에러출력
                    System.out.println("Error");
                    e.printStackTrace();
                } finally {//소켓 연결 종료
                    client.close();
                    System.out.println("Done.");
                }
            }
 
        } catch (Exception e) {//연결 과정에서의 에러출력
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
        Thread ServerThread = new Thread(new SocketServer());//Thread로 실행
        ServerThread.start();//서버 실행
 
    }
 
}
