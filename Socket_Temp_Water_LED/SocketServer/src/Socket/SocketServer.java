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
 
 
public class SocketServer implements Runnable {  // 메인 코드 (소켓 서버 열고 데이터 전송)
    public static final int ServerPort = 3000;
    Scanner sc = new Scanner(System.in);
    public static String read = "0";
    public static int data = 0;

    @Override
    public void run() {
        try {  
            ServerSocket serverSocket = new ServerSocket(ServerPort);//소켓생성
            System.out.println("Connecting...");
            while (true) {
                //client 접속 대기
                Socket client = serverSocket.accept(); //데이터 전송 감지
                //System.out.println("Receiving...");
                try {
                    //데이터 전송
                    PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())),true);
                    String msg = a;  // SerialReader에서 읽어온 센서 값 저장
                    System.out.println(a);
                    out.println(msg);  // 센서 값 애플리케이션으로 내보내기
                    //데이터 수신
                    BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    read = input.readLine(); // 안드로이드 앱에서 버튼이 눌렸을때 나오는 값을 읽어오기
                    if(read.equals("1")) {  // 시리얼 통신으로 아스키 코드 값을 보내야 하므로 1은 49, 0은 48로 변환
                    	data = 49;
                    }else {
                    	data = 48;
                    }
                    Thread.sleep(2000);
                    System.out.println(read);
                } catch (Exception e) {//데이터 전송과정에서의 에러출력
                    System.out.println("Error");
                    e.printStackTrace();
                } finally {//소켓 연결 종료
                    client.close();
                    //System.out.println("Done.");
                }
            }
 
        } catch (Exception e) {//연결 과정에서의 에러출력
            System.out.println("S: Error");
            e.printStackTrace();
        }
 
    }
 
    public static void main(String[] args) {
    	
    	try {
			(new Serial()).connect("COM3");  // 시리얼 포트
		} catch (Exception e) {
			e.printStackTrace();
		}
        Thread ServerThread = new Thread(new SocketServer());//Thread로 실행
        ServerThread.start();//서버 실행
 
    }
 
}
