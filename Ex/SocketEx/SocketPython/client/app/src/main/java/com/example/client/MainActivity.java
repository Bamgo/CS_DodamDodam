package com.example.client;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    Socket socket;  // 클라이언트의 소켓 선언

    DataInputStream is;
    DataOutputStream os;

    String ip;
    String port;

    TextView text_msg;  // 서버로부터 받은 메세지 보여주는 Textview. 여기서는 온도 센서 값을 보여줌
    EditText edit_msg;  // 서버로 전송할 메시지 작성하는 EditText
    EditText edit_ip;  // 서버의 IP를 작성할 수 있는 EditText
    EditText edit_port;  // 포트 번호 입력 칸
    Button btn_connect;  // 접속할 때 누르는 버튼
    String msg = "";  // 초기에 아무것도 없는 텍스트
    boolean isConnected = true;  // 접속 여부

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edit_ip = findViewById(R.id.ip);  // 요소들 찾아오기
        edit_port = findViewById(R.id.port);
        btn_connect = findViewById(R.id.connect);
        edit_msg = findViewById(R.id.msg);
        text_msg = findViewById(R.id.chatting);
    }

    // 클라이언트 소켓 열고 서버 소켓에 접속
    public void ClientSocketOpen(View view){
        new Thread(new Runnable(){
            @Override
            public void run(){
                try {
                    ip= edit_ip.getText().toString();  // IP 주소가 작성되어 있는 EditText에서 서버 IP 얻어오기
                    port = edit_port.getText().toString();
                    if(ip.isEmpty() || port.isEmpty()){
                        MainActivity.this.runOnUiThread(new Runnable(){
                            public void run(){
                                Toast.makeText(MainActivity.this, "IP주소와 포트번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else{
                        // 서버와 연결하는 소켓 생성
                        socket = new Socket(InetAddress.getByName(ip), Integer.parseInt(port));
                        // 여기까지 예외 발생 안 하면 소켓 연결 성공
                        is = new DataInputStream(socket.getInputStream()); // 서버와 메세지를 주고받을 통로 구척
                        os = new DataOutputStream(socket.getOutputStream());

                        MainActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(MainActivity.this, "Connected With Server", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (IOException e){
                    e.printStackTrace();
                }
                // 서버와 접속이 끊길 때까지 무한반복 하면서 서버의 메세지 수신
                while(isConnected){
                    try{
                        msg = is.readUTF();  // 서버에서 메시지가 오면 이를 UTF 형식으로 읽어서 String 형태로 리턴
                        runOnUiThread(new Runnable(){  // 별도의 thread가 main thread에게 UI 작업을 요정하는 메소드
                            @Override
                            public void run(){
                                text_msg.setText("[RECV]" + msg);
                            }
                        });
                    } catch(IOException e){
                        e.printStackTrace();
                    }
                } // while문
            } // run method
        }).start();  // thread 실행
    }
    public void SendMessage(View view){
        if(os == null) return;  // 서버와 연결되어 있지 않다면 전송 불가

        new Thread(new Runnable(){  // 네트워크 작업이므로 Thread 생성
            @Override
            public void run(){
                // 서버로 보낼 메세지 EditTest로부터 얻어오기
                String msg = edit_msg.getText().toString();
                try{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String msg = edit_msg.getText().toString();
                            text_msg.setText("[SENT]" + msg);
                        }
                    });
                    os.writeUTF(msg);  // 서버로 메세지 보내기.
                    os.flush();  // 다음 메세지 전송을 위해 연결통로의 버퍼 지우기
                } catch (IOException e){
                    e.printStackTrace();
                }
            } // run method
        }).start();  // thread 실행
    }
}
