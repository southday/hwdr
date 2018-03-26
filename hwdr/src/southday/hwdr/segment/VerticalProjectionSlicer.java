package southday.hwdr.segment;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import southday.hwdr.util.RgbUtil;

/**
 * 垂直投影分割器
 * @author southday
 *
 */
public class VerticalProjectionSlicer implements Slicer {
    /**
     * 保存垂直投影值的数组
     */
    private int[] vertical_shadow;
    
    public VerticalProjectionSlicer() {}
    
    /**
     * 获取图像的垂直投影值
     * @param bi
     * @return
     */
    private int[] yshadow(BufferedImage bi) {
        int width = bi.getWidth();
        int height = bi.getHeight();
        
        vertical_shadow = new int[width];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (RgbUtil.isBlack(bi.getRGB(x, y))) {
                    vertical_shadow[x]++;
                }
            }
        }
        return vertical_shadow;
    }
    
    @Override
    public ArrayList<BufferedImage> segment(BufferedImage bi) {
        int width = bi.getWidth();
        // 获取bi的垂直投影值
        yshadow(bi);
        
        // 保存切割后的子图片对象
        ArrayList<BufferedImage> sub_imgs = new ArrayList<BufferedImage>();
        int xleft = 0, xright = 0;
        BufferedImage img = null;
        for (int x = 0; x < width; x++) {
            if (vertical_shadow[x] != 0 && xleft == 0)
                xleft = x <= 0 ? 0 : x - 1; // 为了细化时不出现畸变
            else if (vertical_shadow[x] == 0 && xleft != 0) {
                xright = x >= width - 1 ? width - 1 : x + 1; // 为了细化时不出现畸变
                img = trim(bi, xleft, xright); // 除去上下的空白部分
                sub_imgs.add(img);
                xleft = xright = 0;
            }
        }
        return sub_imgs;
    }
    
    /**
     * 除去图片中上下的空白部分
     * @param bi
     * @param start_x
     * @param end_x
     * @return
     */
    private BufferedImage trim(BufferedImage bi, int start_x, int end_x) {
        int height = bi.getHeight();
        
        int start_y = 0, end_y = height;
        boolean flag = true;
        // 从上到下扫描第一个黑色像素点，获得start_y
        for (int y = 0; y < height; y++) {
            for (int x = start_x; x <= end_x; x++) {
                if (flag && RgbUtil.isBlack(bi.getRGB(x, y))) {
                    start_y = y <= 0 ? 0 : y - 1; // 为了细化时不出现畸变
                    flag = false;
                    break;
                }
            }
            if (!flag) break;
        }
        flag = true;
        // 从下到上扫描第一个黑色像素点，获得end_y
        for (int y = height - 1; y > 0; y--) {
            for (int x = start_x; x <= end_x; x++) {
                if (flag && RgbUtil.isBlack(bi.getRGB(x, y))) {
                    end_y = y >= height - 2 ? height - 1 : y + 2;
                    flag = false;
                    break;
                }
            }
            if (!flag) break;
        }
        return bi.getSubimage(start_x, start_y, end_x - start_x, end_y - start_y);
    }
    
    /**
     * 获取图片的垂直投影图
     * @param bi
     * @return
     */
    public BufferedImage shadowImage(BufferedImage bi) {
        int width = bi.getWidth();
        int height = bi.getHeight();
        
        // 获取bi的垂直投影值
        yshadow(bi);
        
        BufferedImage shadow_img = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                shadow_img.setRGB(x, y, RgbUtil.white);
            }
        }
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < vertical_shadow[x]; y++) {
                shadow_img.setRGB(x, y, RgbUtil.black);
            }
        }
        return shadow_img;
    }
}
