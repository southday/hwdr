package southday.hwdr.testpkg.binaryz;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import southday.hwdr.binaryz.BernsenBinaryz;

public class BernsenBinaryzationTest {
    public static void main(String[] args) throws Exception {
        String recpath = "F:\\Handwritten_Digits_Recognition\\binaryzation\\光照不均匀\\tup9\\";
        BernsenBinaryz bernsenb = new BernsenBinaryz();
        
        long startTime = System.currentTimeMillis();

        BufferedImage bi = ImageIO.read(new File(recpath + "phone10.png"));
        ImageIO.write(bernsenb.binaryzation(bi), "png", new File(recpath + "bernsen_bin_phone10.png"));
        
        long endTime = System.currentTimeMillis();
        
        System.out.println("over! Time = " + (endTime - startTime) + "ms");
    }
}
