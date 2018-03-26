package southday.hwdr.testpkg.skeleton;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import southday.hwdr.skeleton.ZSSkeleton;

public class ZSSkeletonTest {
    public static void main(String[] args) throws IOException {
        String path = "F:\\Handwritten_Digits_Recognition\\other\\";
        ZSSkeleton _skeleton = new ZSSkeleton();
        
        BufferedImage bi = ImageIO.read(new File(path + "bilinear_1_vpslicer_ostu_bin_phone3_v1.png"));
        long startTime = System.currentTimeMillis();
        ImageIO.write(_skeleton.skeleton(bi), "png", new File(path + "zsskeleon_bilinear_1_vpslicer_ostu_bin_phone3_v1.png"));
        long endTime = System.currentTimeMillis();
        System.out.println("over, spend time = " + (endTime - startTime) + "ms");
    }
    
    public static void test_one(int num) throws IOException {
        String path = "F:\\Handwritten_Digits_Recognition\\skeleton\\" + num + "\\";
        ZSSkeleton _skeleton = new ZSSkeleton();
        
        BufferedImage bi = ImageIO.read(new File(path + num + "_vpslicer_ostu_bin_phone3_v1.png"));
        long startTime = System.currentTimeMillis();
        ImageIO.write(_skeleton.skeleton(bi), "png", new File(path + "zsskeleon_" + num + "_vpslicer_ostu_bin_phone3_v1.png"));
        long endTime = System.currentTimeMillis();
        System.out.println("over, spend time = " + (endTime - startTime) + "ms");
    }
}
