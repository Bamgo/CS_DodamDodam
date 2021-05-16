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
            while (true) { // 계속해서 아두이노 시리얼 모니터에 값 보내기
            	c = data;  // SocketServer에서 안드로이드 앱에서부터 읽어온 값
            	out.write(c);  // 시리얼 통신을 통해 값 내보내기
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