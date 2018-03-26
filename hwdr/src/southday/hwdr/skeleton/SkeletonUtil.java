package southday.hwdr.skeleton;

import java.awt.image.BufferedImage;

import southday.hwdr.util.RgbUtil;

public class SkeletonUtil {
    
    /**
     * 将图像bi各个位置的rgb通过某一阀值二值化为1（代表前景色）或0（代表背景色），
     * 将这些1和0保存在二维数组rgbs的相应位置上，并返回rgbs
     * @param bi
     * @return 
     */
    public static int[][] init_rgbs(BufferedImage bi) {
        int width = bi.getWidth();
        int height = bi.getHeight();
        
        int[][] rgbs = new int[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                // 将原rgb“二值化”为 <前景色，背景色>
                int rgb = bi.getRGB(x, y);
                rgbs[x][y] = RgbUtil.isBlack(rgb) ? 1 : 0;
            }
        }
        return rgbs;
    }
    
    /**
     * 统计像素点p(x,y)邻域(8邻域)中黑色像素点个数(前景色为黑色)
     * @param x 像素p的横坐标x
     * @param y 像素p的纵坐标y
     * @return p的8邻域中黑色像素点个数
     */
    public static int black_neighbour_num(int[][] rgbs, int x, int y) {
        int count = 0;
        
        // 为了避免越界，在扫描bi时，应该从(1,1)开始扫描,到(width-2, height-2)结束
        if (rgbs[x][y - 1] == 1) count++; // ↑
        if (rgbs[x + 1][y - 1] == 1) count++; // ↗
        if (rgbs[x + 1][y] == 1) count++; // →
        if (rgbs[x + 1][y + 1] == 1) count++; // ↘
        if (rgbs[x][y + 1] == 1) count++; // ↓
        if (rgbs[x - 1][y + 1] == 1) count++; // ↙
        if (rgbs[x - 1][y] == 1) count++; // ←
        if (rgbs[x - 1][y - 1] == 1) count++; // ↖
        
        return count;
    }
}
