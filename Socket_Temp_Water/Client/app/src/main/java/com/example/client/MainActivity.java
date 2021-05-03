package com.example.client;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    private Handler mHandler;
    Socket socket;
    private String ip = "172.30.1.50"; // 서버의 IP 주소
    private int port = 3000; // PORT번호를 꼭 맞추어 주어야한다.
    EditText et;
    TextView Temp;
    TextView Water;
    TextView label1;
    TextView label2;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHandler = new Handler();
        et = (EditText) findViewById(R.id.EditText01);
        Button btn = (Button) findViewById(R.id.Button01);
        Temp = (TextView)findViewById(R.id.Temp);
        Water = (TextView)findViewById(R.id.Water);
        label1 = (TextView)findViewById(R.id.label1);
        label2 = (TextView)findViewById(R.id.label2);
        ConnectThread th =new ConnectThread();  // 접속하면 바로 연결
        th.start();
    }
    @Override
    protected void onStop() {
        super.onStop();
        try {
            socket.close();//종료시 소켓도 닫아주어야한다.
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    class ConnectThread extends Thread{//소켓통신을 위한 스레드
        public void run(){
            try{
                while(true) {  // 계속 반복
                    //소켓 생성
                    InetAddress serverAddr = InetAddress.getByName(ip);
                    socket = new Socket(serverAddr, port);
                    //입력 메시지
                    String sndMsg = et.getText().toString();
                    Log.d("=============", sndMsg);
                    //데이터 전송
                    //소켓에서 넘오는 stream 형태의 문자를 얻은 후 읽어 들어서  bufferstream 형태로 in 에 저장.
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    //in에 저장된 데이터를 String 형태로 변환 후 읽어들어서 String에 저장
                    String read = in.readLine();
                    int idx = read.indexOf("*");  // *를 기준으로 인덱스 찾음
                    String Temperature = read.substring(0, idx);  // 0번째부터 *까지의 문자열 추출
                    String Water = read.substring(idx+1);  // * 다음부터 끝까지 추출
                    System.out.println("Data get");
                    //client에 다시 전송
                    PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                    out.println("No Error");
                    //화면 출력
                    mHandler.post(new msgTempUpdate(Temperature));
                    mHandler.post(new msgWaterUpdate(Water));
                    Log.d("=============", read);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }


    }
    // 받은 메시지 출력
    class msgTempUpdate implements Runnable {
        private String msg;
        public msgTempUpdate(String str) {
            this.msg = str;
        }
        public void run() {
            Temp.setText(msg);
        }  // 한 값만 표시되게
    };

    class msgWaterUpdate implements Runnable {
        private String msg;
        public msgWaterUpdate(String str) {
            this.msg = str;
        }
        public void run() {
            Water.setText(msg);
        }  // 한 값만 표시되게
    };
}
