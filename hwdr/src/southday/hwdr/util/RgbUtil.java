package southday.hwdr.util;

import java.awt.image.BufferedImage;

public class RgbUtil {
    /**
     * 白色
     */
    public static final int white = 0xffffffff;
    /**
     * 黑色
     */
    public static final int black = 0xff000000;
    /**
     * 红色
     */
    public static final int red = 0xffff0000;
    /**
     * 前景色上界：前景色一般为黑色，但是图片中像素不一定是纯黑色(0x00000000)，
     * 所以定义了一个上界，也就是说rgb小于这个值的像素被认为是黑色像素
     */
    public static final int foreground_color_upper = 0xff303030;
    
    public static boolean isBlack(int rgb) {
        return isBlack(rgb, foreground_color_upper);
    }
    
    /**
     * 判断像素rgb是否为黑色
     * @param rgb
     * @param black_threshold 决定rgb是否为黑色的阀值
     * @return 
     */
    public static boolean isBlack(int rgb, int black_threshold) {
        return rgb <= black_threshold ? true : false;
    }
    
    /**
     * 图像灰度化
     * @param bi 待灰度化的图像
     * @return 保存图像各像素灰度值的矩阵gray[][]
     */
    public static int[][] graying(BufferedImage bi) {
        int width = bi.getWidth();
        int height = bi.getHeight();
        
        int[][] gray = new int[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int argb = bi.getRGB(x, y);
                int r = (argb >> 16) & 0xff;
                int g = (argb >> 8) & 0xff;
                int b = argb & 0xff;
                int grayPixel = (int) ((b * 29 + g * 150 + r * 77 + 128) >> 8);
                gray[x][y] = grayPixel;
            }
        }
        return gray;
    }
    
    /**
     * 将图像bi各个位置的argb值分解为如下形式：
     * <p> argbs[x][y][0] 表示 alpha </p>
     * <p> argbs[x][y][1] 表示 red </p>
     * <p> argbs[x][y][2] 表示 green </p>
     * <p> argbs[x][y][3] 表示 blue </p>
     * @param bi
     * @return
     */
    public static int[][][] decompose_argbs(BufferedImage bi) {
        int width = bi.getWidth();
        int height = bi.getHeight();
        
        int[][][] argbs = new int[width][height][4];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int argb = bi.getRGB(x, y);
                argbs[x][y][0] = (argb >> 24) & 0xff; // alpha
                argbs[x][y][1] = (argb >> 16) & 0xff; // red
                argbs[x][y][2] = (argb >> 8) & 0xff; // green
                argbs[x][y][3] = argb & 0xff; //blue
            }
        }
        return argbs;
    }
    
    /**
     * 将分解得到的argbs[x][y][0~4]合并为rgbs[x][y]，即：
     * <p> 将argbs中的 alpha, red, green, blue 合并 </p> 
     * @param argbs
     * @return
     */
    public static int[][] combine_argbs(int[][][] argbs) {
        int width = argbs.length;
        int height = argbs[0].length;
        
        int[][] rgbs = new int[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                rgbs[x][y] = 
                    ((argbs[x][y][0] << 24) & 0xff000000) | 
                    ((argbs[x][y][1] << 16) & 0x00ff0000) | 
                    ((argbs[x][y][2] << 8) & 0x0000ff00) | 
                    ((argbs[x][y][3]) & 0x000000ff);
            }
        }
        return rgbs;
    }
}
