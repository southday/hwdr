package southday.hwdr.binaryz;

import java.awt.image.BufferedImage;

import southday.hwdr.util.RgbUtil;

/**
 * 基于Bernsen算法的二值化工具
 * @author southday
 *
 */
public class BernsenBinaryz implements Binaryz {
    /**
     * 灰度值矩阵
     */
    private int[][] gray;
    /**
     * wsize决定了窗口大小
     * <p>窗口大小为 : (2 * wsize + 1) * (2 * wsize + 1) </p>
     */
    public int wsize = 1;
    /**
     * 窗口内所有像素灰度值的最大值减去最小值的差的阀值：
     * <p> d_vaule = threshold about : max_gray - min_gray </p>
     */
    public int d_value = 15;
    /**
     * 整张图片灰度值的全局阀值
     */
    public int global_threshold = 128;
    /**
     * 用来保存窗口内灰度值的最大最小值
     */
    private static MaxMinGray max_min_gray = new MaxMinGray();
    
    public BernsenBinaryz() {}
    
    /**
     * 用来记录窗口内灰度值的最大值和最小值
     * @author coco
     *
     */
    private static class MaxMinGray {
        public int max_gray;
        public int min_gray;
    }
    
    /**
     * constructor
     * @param wsize 决定窗口大小，窗口大小为：(2 * wsize + 1) * (2 * wsize + 1)
     * @param d_value 窗口内所有像素灰度值的最大值减去最小值的差
     * @param global_threshold 整张图片灰度值的全局阀值
     */
    public BernsenBinaryz(int wsize, int d_value, int global_threshold) {
        this.wsize = wsize;
        this.d_value = d_value;
        this.global_threshold = global_threshold;
    }
    
    @Override
    public BufferedImage binaryzation(BufferedImage bi) {
        int width = bi.getWidth();
        int height = bi.getHeight();
        
        // 获取图像灰度值
        gray = RgbUtil.graying(bi);
        BufferedImage binBufImg = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
        
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                // 获取最大最小灰度值
                max_min_grays(x, y, width, height);
                int avg_gray = (max_min_gray.max_gray + max_min_gray.min_gray) >> 1;
            
                if (max_min_gray.max_gray - max_min_gray.min_gray > d_value) {
                    // 当前点的灰度值阀值为 T = agv_gray
                    gray[x][y] = gray[x][y] > avg_gray ? RgbUtil.white : RgbUtil.black;
                } else {
                    // 平均灰度值avg_gray与全局阀值global_threshold比较
                    gray[x][y] = avg_gray > global_threshold ? RgbUtil.white : RgbUtil.black;
                }
                binBufImg.setRGB(x, y, gray[x][y]);
            }
        }
        return binBufImg;
    }
    
    /**
     * 计算窗口内所有像素灰度值的最大值和最小值
     * @param x 窗口中心点p的横坐标x
     * @param y 窗口中心点p的纵坐标y
     * @param width 图像宽度
     * @param height 图像高度
     * @return 保存max_gray和min_gray的内部类对象
     */
    private MaxMinGray max_min_grays(int x, int y, int width, int height) {
        max_min_gray.max_gray = 0;
        max_min_gray.min_gray = 255;
        
        int xleft = (x - wsize) < 0 ? x : (x - wsize);
        int xright = (x + wsize) >= width ? x : (x + wsize);
        int yup = (y - wsize) < 0 ? y : (y - wsize);
        int ydown = (y + wsize) >= height ? y : (y + wsize);
        
        for (int i = xleft; i <= xright; i++) {
            for (int j = yup; j <= ydown; j++) {
                int grayPi = gray[i][j];
                if (grayPi > max_min_gray.max_gray) {
                    max_min_gray.max_gray = grayPi;
                } 
                if (grayPi < max_min_gray.min_gray) {
                    max_min_gray.min_gray = grayPi;
                }
            }
        }
        return max_min_gray;
    }
}
