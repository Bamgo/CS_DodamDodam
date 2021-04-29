package com.example.client;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
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
    private String ip = "192.168.0.101"; // 서버의 IP 주소
    private int port = 3000; // PORT번호를 꼭 맞추어 주어야한다.
    EditText et;
    TextView msgTV;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHandler = new Handler();
        et = (EditText) findViewById(R.id.EditText01);
        Button btn = (Button) findViewById(R.id.Button01);
        msgTV = (TextView)findViewById(R.id.chatTV);
        ConnectThread th =new ConnectThread();
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
                while(true) {
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
                    System.out.println("받았음");
                    //client에 다시 전송
                    PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                    out.println("받았음, 회신");
                    //화면 출력
                    mHandler.post(new msgUpdate(read));
                    Log.d("=============", read);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }


    }
    // 받은 메시지 출력
    class msgUpdate implements Runnable {
        private String msg;
        public msgUpdate(String str) {
            this.msg = str;
        }
        public void run() {
            msgTV.setText(msg);
        }
    };
}
