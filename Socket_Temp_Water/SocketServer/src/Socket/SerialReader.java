package Socket;

import java.io.IOException;
import java.io.InputStream;

public class SerialReader implements Runnable {
    InputStream in;
    public static String a = "";

    public SerialReader(InputStream in) {
        this.in = in;
    }

    public void run() {
        byte[] buffer = new byte[1024];
        int len = -1;
        try {
            while ((len = this.in.read(buffer)) > -1) {
            	a = new String(buffer, 0, len);
                Thread.sleep(2000);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
}