package southday.hwdr.recognize.fextractor;

import java.awt.image.BufferedImage;
import java.io.IOException;

import southday.hwdr.recognize.FeatureExtractor;
import southday.hwdr.util.RgbUtil;

/**
 * 粗网格特征提取器
 * @author southday
 *
 */
public class CoarseMeshFeatureExtractor implements FeatureExtractor<double[]> {
    /**
     * 定义网格大小，为正方形网格边长
     * <p>单位为：单位像素长度</p>
     */
    private int wsize = 2;
    /**
     * 特征向量
     */
    private double[] feaVector;
    
    public CoarseMeshFeatureExtractor() {}
    
    public CoarseMeshFeatureExtractor(int wsize) {
        this.wsize = wsize;
    }
    
    @Override
    public double[] extract(BufferedImage bi) throws IOException {
        // TODO Auto-generated method stub

        int width = bi.getWidth();
        int height = bi.getHeight();
        double bpiNum = 0;
        feaVector = new double[(width / wsize) * (height / wsize)];
        
        int x = 0, y = 0;
        for (int k = 0; k < feaVector.length; k++) {
            bpiNum = 0;
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    if (RgbUtil.isBlack(bi.getRGB(i + x, j + y))) bpiNum++;
                }
            }
            feaVector[k] = bpiNum;
            if (x == (width - 2)) { y += 2; x = 0; } 
            else { x += 2; }
        }
        return feaVector;
    }
    
    public int getWsize() {
        return wsize;
    }
    
    public void setWsize(int wsize) {
        this.wsize = wsize;
    }
}
