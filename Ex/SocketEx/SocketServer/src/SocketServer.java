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
            
            ServerSocket serverSocket = new ServerSocket(ServerPort);//���ϻ���
            System.out.println("Connecting...");
            while (true) {
                //client ���� ���
                Socket client = serverSocket.accept(); //������ ���� ����
                System.out.println("Receiving...");
                try {
 
                    //client data ����
                    
                    //���Ͽ��� �ѿ��� stream ������ ���ڸ� ���� �� �о� ��  bufferstream ���·� in �� ����.                                                                                           
                    BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    //in�� ����� �����͸� String ���·� ��ȯ �� �о�� String�� ����
                    String str = in.readLine();
                    System.out.println("Received: '" + str + "'");
                    //client�� �ٽ� ����
                    PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);
                    out.println("Server Received : '" + str + "'");
 
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
 
        Thread ServerThread = new Thread(new SocketServer());//Thread�� ����
        ServerThread.start();//���� ����
 
    }
 
}
