package v1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class LinkWait implements Runnable{

    private ServerSocket serSocket;
    private ArrayList<Socket> sockets;

    public LinkWait(ServerSocket serSocket) {
        this.serSocket = serSocket;
    }

    @Override
    public void run() {
        while (true){
            try {
                Socket socket=serSocket.accept();
                sockets.add(socket);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
