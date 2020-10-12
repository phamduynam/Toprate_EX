package com.company;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public  class Main {
    public static void main(String[] args) {
        /* BMP Struct
        * Header : 14 Byte
        * Info : 40 Byte
        * Color Palate: 4*x Byte
        * Data: to End
        * */

        // read image
        ImageBmp image = new ImageBmp();
        try {
            FileInputStream fileInputStream = new FileInputStream("baitap.bmp");
            FileOutputStream fileOutputStream = new FileOutputStream("ketqua.bmp");

            byte[] bOffBits = new byte[4];
            byte[] bWidth =  new byte[4];
            byte[] bHeight =  new byte[4];

            fileInputStream.skip(10);
            // Read header address
            fileInputStream.read(bOffBits);
            // Skip header size.
            fileInputStream.skip(4);
            // read Width
            fileInputStream.read(bWidth);
            // read height
            fileInputStream.read(bHeight);

//            system.out.println(arrays.tostring(boffbits));
//            system.out.println(bytearraytoleint(boffbits));
//
//            system.out.println(arrays.tostring(bwidth));
//            system.out.println(bytearraytoleint(bwidth));
//
//            system.out.println(arrays.tostring(bheight));
//            system.out.println(bytearraytoleint(bheight));

            image.setiOffBits(byteArrayToLeInt(bOffBits));
            image.setiWidth(byteArrayToLeInt(bWidth));
            image.setiHeight(byteArrayToLeInt(bHeight));
            image.setiPadding(image.getiWidth() % 4);

            changeColor(image,"baitap.bmp","ketqua.bmp");

            for (int i = 0 ; i <1000 ; i ++){
                System.out.println(fileInputStream.read());
            }

        }catch (IOException ex){
            ex.printStackTrace();
        }

    }


    public static void changeColor(ImageBmp imp, String urlInput, String urlOutput) throws IOException {
       FileInputStream input = new FileInputStream(urlInput);
       FileOutputStream output = new FileOutputStream(urlOutput);
        // Write Header + Info
        for (int i = 0; i < imp.getiOffBits(); i++) {
            int res = input.read();
            output.write(res);
        }

        // Write Data
        int blue,green,red;
        double index;
        for (int i = 0 ; i < imp.getiHeight(); i ++){
            for(int j = 0 ; j < imp.getiWidth(); j ++){
                blue = input.read() ;
                green = input.read() ;
                red = input.read();

                // Neeu mau do vuot troi
                if (red > blue && red > green) {

                       int temp = red;
                       red = blue;
                       blue = temp;

                }

                output.write(blue);
                output.write(green);
                output.write(red);
            }

            input.skip(imp.getiPadding());

            for(int k = 0 ; k < imp.getiPadding() ; k ++){
                output.write(0);
            }
        }
        output.close();
    }
    public static int byteArrayToLeInt(byte[] b) {
        final ByteBuffer bb = ByteBuffer.wrap(b);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        return bb.getInt();
    }
    public static byte[] leIntToByteArray(int i) {
        final ByteBuffer bb = ByteBuffer.allocate(Integer.SIZE / Byte.SIZE);
        bb.order(ByteOrder.LITTLE_ENDIAN);
        bb.putInt(i);
        return bb.array();
    }

    public static int convertByteToInt(byte b){
        return Byte.toUnsignedInt(b);
    }
}

