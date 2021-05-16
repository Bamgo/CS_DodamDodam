package Socket;

import java.io.IOException;
import java.io.OutputStream;

import static Socket.SocketServer.*;

public class SerialWriter implements Runnable {
    OutputStream out;

    public SerialWriter(OutputStream out) {
        this.out = out;
    }

    public void run() {
        try {
            int c = 0;
            while (true) { // ����ؼ� �Ƶ��̳� �ø��� ����Ϳ� �� ������
            	c = data;  // SocketServer���� �ȵ���̵� �ۿ������� �о�� ��
            	out.write(c);  // �ø��� ����� ���� �� ��������
                System.out.println("Post !!  " + c);
                Thread.sleep(2000);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}