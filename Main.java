package com.company;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;

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
            FileInputStream fileInputStream = new FileInputStream("C:/Users/Admin/Pictures/vietnam.bmp");
            FileOutputStream fileOutputStream = new FileOutputStream("C:/Users/Admin/Pictures/ketqua.bmp");

            int[] bOffBits = new int[4];
            int[] bWidth =  new int[4];
            int[] bHeight =  new int[4];
            int[] bPlanes = new int[4];
            // Skip Header 10 byte
            fileInputStream.skip(10);

            // Read Address Data Start
            for (int i = 0 ; i< 4 ; i ++){
                bOffBits[i] = fileInputStream.read();
//                System.out.println(bOffBits[i]);
            }
            // Skip Size off header

            fileInputStream.skip(4);

            // Read Width
            for (int i = 0 ; i < 4 ; i ++){
                bWidth[i] = fileInputStream.read();
//                System.out.println(bWidth[i]);
            }

            // Read Height
            for (int i = 0 ; i < bHeight.length ; i ++){
                bHeight[i] = fileInputStream.read();
//                System.out.println(bHeight[i]);
            }

            image.setiOffBits(arrayByteToInt(bOffBits));
            image.setiWidth(arrayByteToInt(bWidth));
            image.setiHeight(arrayByteToInt(bHeight));
            image.setiPadding(image.getiWidth() % 4);

            System.out.println(image.getiOffBits());
            System.out.println(image.getiHeight());
            System.out.println(image.getiWidth());
            System.out.println(image.getiPadding());

            changeColor(image,"C:/Users/Admin/Pictures/vietnam.bmp",fileOutputStream);

        }catch (IOException ex){
            ex.printStackTrace();
        }

    }


    public static int convertByteToInt(byte[] bytes){
        ByteBuffer bbf = ByteBuffer.wrap(bytes);
        return bbf.getInt();
    }

    private static int arrayByteToInt (int[] bytes) {
        int result = 0;
        int base = 1;
        for (int i = 0; i< bytes.length; i++) {
            if (i>0) base *= 256;
            result += bytes[i]*base;
        }
        return result;
    }

    public static void changeColor(ImageBmp imp, String url, FileOutputStream output) throws IOException {
       FileInputStream input = new FileInputStream(url);


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
                red = input.read() ;

                // Neeu mau do vuot troi
                if (red > blue && red > green) {
                    index = (double)(256 - red)/80 + 1.8;
                    if(red/blue >= index || red/green >= index){
                        int temp = red;
                        red = blue;
                        blue = temp;
                    }
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
}
