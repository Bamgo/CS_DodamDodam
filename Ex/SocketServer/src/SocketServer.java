import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
 
 
 
public class SocketServer implements Runnable {
    public static final int ServerPort = 3000;
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
 
                    //client data 수신
                    
                    //소켓에서 넘오는 stream 형태의 문자를 얻은 후 읽어 들어서  bufferstream 형태로 in 에 저장.                                                                                           
                    BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    //in에 저장된 데이터를 String 형태로 변환 후 읽어들어서 String에 저장
                    String str = in.readLine();
                    System.out.println("Received: '" + str + "'");
                    //client에 다시 전송
                    PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
                    out.println("Server Received : '" + str + "'");
 
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
 
        Thread ServerThread = new Thread(new SocketServer());//Thread로 실행
        ServerThread.start();//서버 실행
 
    }
 
}
