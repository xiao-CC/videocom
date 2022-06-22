package v1;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class Client {

    Socket socket;

    public Client() {
        try {
            socket=new Socket("127.0.0.1", 12345);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendImg() throws IOException {
        String path="E:/论文投稿/第二次投稿/提交图件/Fig8.jpg";
        File file=new File(path);
        BufferedImage image= ImageIO.read(file);
        int width=image.getWidth();
        int high=image.getHeight();
       /* 注意这个*4
        图片上的每个点是个int，但是传的时候传的是byte
        所以要改成byte传*/
        byte[] imageByte=new byte[width*high*4];
        int count=0;
        for (int i=0;i<width;i++){
            for (int j=0;j<high;j++){
                int pixel=image.getRGB(i,j);
                int b1 = pixel >> 24;
                int b2 = (pixel >> 16) & 0xFF;
                int b3 = (pixel >> 8) & 0xFF;
                int b4 = pixel & 0xFF;
                imageByte[count++]= (byte) b1;
                imageByte[count++]= (byte) b2;
                imageByte[count++]= (byte) b3;
                imageByte[count++]= (byte) b4;
            }
        }

        //先装进数组，再把数组发过去，避免在计算和IO间频繁切换。速度更快
        OutputStream os=socket.getOutputStream();
        DataOutputStream dataOut=new DataOutputStream(os);  //用底层输出流传更快
        dataOut.writeInt(width);
        dataOut.writeInt(high);
        dataOut.write(imageByte);
    }

    public static void main(String[] args) {
        Client client=new Client();
        try {
            client.sendImg();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
