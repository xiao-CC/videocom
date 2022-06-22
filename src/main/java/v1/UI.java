package v1;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class UI extends JFrame{

    public UI(String title){
        this.setTitle(title);
        this.setSize(600,600);
        this.setLayout(null);
        this.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void draw(byte[] imgByte,int w,int h){
        //可以直接new一个图片缓存，直接往里装。
        BufferedImage buffIma=new BufferedImage(w,h,BufferedImage.TYPE_3BYTE_BGR);
        int count=0;
        for (int i=0;i<w;i++){
            for (int j=0;j<h;j++){
                int pix=((imgByte[count++]&0xFF)<<24)|
                        ((imgByte[count++]&0xFF)<<16)|
                        ((imgByte[count++]&0xFF)<<8)|
                        (imgByte[count++]&0xFF);
                buffIma.setRGB(i,j,pix);
            }
        }
        Graphics g=this.getGraphics();
        System.out.println(buffIma.getWidth());
        g.drawImage(buffIma,0,0,null);
    }
}
