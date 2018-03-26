package southday.hwdr.testpkg.segment;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import southday.hwdr.segment.VerticalProjectionSlicer;

public class VerticalProSlicerTest {
    public static void main(String[] args) throws IOException {
        String path = "F:\\Handwritten_Digits_Recognition\\segement\\非粘连字符\\tup1";
        VerticalProjectionSlicer vps = new VerticalProjectionSlicer();
        BufferedImage bi = ImageIO.read(new File(path + "ostu_bin_phone3.png"));
        long startTime = System.currentTimeMillis();
        ArrayList<BufferedImage> sub_imgs = vps.segment(bi);
        int count = 0;
        for (BufferedImage sub_img : sub_imgs) {
            ImageIO.write(sub_img, "png", new File((count++) + "_vpslicer_ostu_bin_phone3.png"));
        }
        long endTime = System.currentTimeMillis();
        System.out.println("over, spend time = " + (endTime - startTime) + "ms");
        
//        // 获取投影图像
//        BufferedImage shadow_img = vps.shadowImage(bi);
//        ImageIO.write(shadow_img, "jpg", new File(path + "vpslicer_shadow_ostu_bin_phone4.jpg"));
//        System.out.println("shadow_img, over");
    }        
}
