package southday.hwdr.testpkg.binaryz;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import southday.hwdr.binaryz.OstuBinaryz;

public class OstuBinaryzationTest {
    
    public static void main(String[] args) throws IOException {
        String recpath = "F:\\Handwritten_Digits_Recognition\\binaryzation\\光照不均匀\\tup9\\";
        OstuBinaryz ostub = new OstuBinaryz();
        
        long startTime = System.currentTimeMillis();

        BufferedImage bi = ImageIO.read(new File(recpath + "phone10.png"));
        ImageIO.write(ostub.binaryzation(bi), "png", new File(recpath + "ostu_bin_phone10.png"));
        
        long endTime = System.currentTimeMillis();
        
        System.out.println("over! Time = " + (endTime - startTime) + "ms");
    }
}
