package southday.hwdr.binaryz;

import java.awt.image.BufferedImage;

import southday.hwdr.util.RgbUtil;

/**
 * 基于Ostu算法的二值化工具
 * @author southday
 *
 */
public class OstuBinaryz implements Binaryz {
    /**
     * 灰度值矩阵
     */
    private int[][] gray = null;
    
    public OstuBinaryz() {}
    
    @Override
    public BufferedImage binaryzation(BufferedImage bi) {
        int width = bi.getWidth();
        int height = bi.getHeight();
        
        // 图像灰度化
        gray = RgbUtil.graying(bi);
        // 获取二值化阀值
        int threshold = ostu(this.gray, width, height);
        BufferedImage binBufImg = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                gray[x][y] = (gray[x][y] > threshold) ? RgbUtil.white : RgbUtil.black;    
                binBufImg.setRGB(x, y, gray[x][y]);
            }
        }
        return binBufImg;
    }
    
    // 图像二值化(基于灰图像)，使用ostu算法来选取最优阀值threshold，使得两类之间差别最大
    private int ostu(int[][] gray, int width, int height) {
        // 使用灰度图像讲颜色空间从1600W+变成了256种，进行了有损压缩(变换)
        int[] histData  = new int[width * height]; // 计数不同灰度值出现的个数,该灰度值作为角标
        
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int red = gray[x][y] & 0xff; // int(4bytes) 0x000000ff, 只取最后8bit值
                histData[red]++;
            }
        }
        
        int total = width * height;
        float sum = 0; // 所有 像素的  灰度值值 乘以 出现次数的值的总和
        for (int t = 0; t < 256; t++) {
            sum += t * histData[t];
        }
        
        // 由阀值threshold来区别前景(所需信息)和背景(干扰信息)
        float sumB = 0;
        int wB = 0; // 背景权重
        int wF = 0; // 前景权重
        
        float disMax = 0; // 前景与背景两类间距离的最大值
        int threshold = 0; // 阀值
        
        for (int t = 0; t < 256; t++) {
            if ((wB += histData[t]) == 0) continue;
            if ((wF = total - wB) == 0) break;
            
            sumB += (float) (t * histData[t]);
            
            float mB = sumB / wB; // 背景平均值
            float mF = (sum - sumB) / wF; // 前景平均值
            
            // 计算类间距离(前景与背景两类的距离)
            float disBF = (float) wB * (float) wF * (mB - mF) * (mB - mF);
            /*
             * 图像的总平均灰度为：u=w0*u0+w1*u1。
             * 前景和背景图象的方差：g=w0*(u0-u)*(u0-u)+w1*(u1-u)*(u1-u)=w0*w1*(u0-u1)*(u0-u1)
             */
            
            if (disBF > disMax) {
                disMax = disBF;
                threshold = t; // 两类间距离最大时阀值最优
            }
        } // for
        return threshold;
    }
}
