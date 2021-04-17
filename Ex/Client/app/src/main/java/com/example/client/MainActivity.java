package com.example.client;

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
    private String ip = "192.168.137.106"; // 서버의 IP 주소
    private int port = 3000; // PORT번호를 꼭 맞추어 주어야한다.
    EditText et;
    TextView msgTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHandler = new Handler();
        et = (EditText) findViewById(R.id.EditText01);
        Button btn = (Button) findViewById(R.id.Button01);
        msgTV = (TextView)findViewById(R.id.chatTV);
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (et.getText().toString() != null || !et.getText().toString().equals("")) {
                    ConnectThread th =new ConnectThread();
                    th.start();

                }
            }
        });
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
                //소켓 생성
                InetAddress serverAddr = InetAddress.getByName(ip);
                socket =  new Socket(serverAddr,port);
                //입력 메시지
                String sndMsg = et.getText().toString();
                Log.d("=============", sndMsg);
                //데이터 전송
                PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())),true);
                out.println(sndMsg);
                //데이터 수신
                BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String read = input.readLine();
                //화면 출력
                mHandler.post(new msgUpdate(read));
                Log.d("=============", read);
                socket.close();//사용이 끝난뒤 꼭 닫아주어야한다.
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
            msgTV.setText(msgTV.getText().toString() + msg + "\n");
        }
    };
}

