package southday.hwdr.testpkg.zoom;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import southday.hwdr.util.ImageSquaring;
import southday.hwdr.zoom.BilinearZoomer;

public class BilinearZoomerTest {
    public static void main(String[] args) throws IOException {
        String path = null; // 要测试需要自己填写路径
        BilinearZoomer _zoomer = new BilinearZoomer();
        
        BufferedImage bi = ImageIO.read(new File(path));
        long startTime = System.currentTimeMillis();
        BufferedImage square_img = ImageSquaring.squaring(bi);
        ImageIO.write(_zoomer.zoom(square_img, 20, 20, BufferedImage.TYPE_BYTE_BINARY), 
                "bmp", new File(path + "xxx"));
        long endTime = System.currentTimeMillis();
        System.out.println("over, spend time = " + (endTime - startTime) + "ms");
    }
}
