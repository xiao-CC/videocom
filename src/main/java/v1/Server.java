package v1;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket serSocket;
    Socket socket;
    UI ui;

    public Server() {
        try {
            serSocket=new ServerSocket(12345);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ui=new UI("服务端");
    }

    public void link(){
        try {
            socket=serSocket.accept();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(){
        byte[] imageByte;
        int width;
        int high;
        try {
            InputStream is=socket.getInputStream();
            DataInputStream dataInt=new DataInputStream(is);
            width=dataInt.readInt();
            high=dataInt.readInt();
            imageByte=new byte[width*high*4];
            //这个收图片的方法，奇奇怪怪的
            dataInt.readFully(imageByte);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ui.draw(imageByte,width,high);
    }

    public static void main(String[] args) {
        Server server=new Server();
        server.link();
        server.draw();
    }

}
