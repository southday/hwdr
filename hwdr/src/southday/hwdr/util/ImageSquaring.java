package southday.hwdr.util;

import java.awt.image.BufferedImage;

/**
 * 使二值化图片正方形化
 * @author southday
 *
 */
public class ImageSquaring {
    /**
     * 将二值化图片正方形化 
     * @param bi
     * @return
     */
    public static BufferedImage squaring(BufferedImage bi) {
        int width = bi.getWidth();
        int height = bi.getHeight();
        
        int offset = 0, edgeLen = 0;
        if (height > width) {
            offset = (height - width) / 2;
            edgeLen = height;
        } else {
            offset = (width - height) / 2;
            edgeLen = width;
        }
        
        int end = edgeLen - 1 - offset;
        BufferedImage square_img = new BufferedImage(edgeLen, edgeLen, BufferedImage.TYPE_BYTE_BINARY);
        for (int x = 0; x < edgeLen; x++) {
            for (int y = 0; y < edgeLen; y++) {
                int rgb = RgbUtil.white; // 这样写会有多余的判断(edgeLen == height)，但是我懒得分开写，那样写起来不好看
                if (edgeLen == height && x >= offset && x < end) {
                    rgb = bi.getRGB(x - offset, y);
                } else if (edgeLen == width && y >= offset && y < end) {
                    rgb = bi.getRGB(x, y - offset);
                }
                square_img.setRGB(x, y, rgb);
            }
        }
        return square_img;
    }
}
