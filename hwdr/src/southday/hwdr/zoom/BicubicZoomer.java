package southday.hwdr.zoom;

import java.awt.image.BufferedImage;

import southday.hwdr.util.RgbUtil;

/**
 * 基于双立方插值算法的缩放器，主要学自：
 * <p> http://blog.csdn.net/jia20003/article/details/40020775 </p>
 * <p> 目标图像(x,y)经过反向变换后映射到源图像(i+u,j+v)，其中i,j为正整数，u,v为(0,1)之间的小数 </p>
 * <p> 根据公式：D(x,y) = S(i+u,j+v) = sigmaS(i+m,j+n)R(u-m)R(v-n)，其中m,n为[-1,2]的整数 </p>
 * <p> R(x) 为插值核函数，常见的有： 三角取值、Bell分布表达式、B样条曲线表达式等 <p>
 * @author southday
 *
 */
public class BicubicZoomer implements Zoomer {
    /**
     * 基于三角取值的插值核函数
     */
    public static final int interp_type_triangle = 1;
    /**
     * 基于Bell分布采样的插值核函数
     */
    public static final int interp_type_bell = 2;
    /**
     * 基于B样条曲线采样的插值核函数
     */
    public static final int interp_type_bspline = 3;
    /**
     * 基于catmull-Rom样条曲线的插值核函数
     */
    public static final int interp_type_catmullRom = 4;
    /**
     * 基于sin(x*Pi)/x 函数逼近的插值核函数
     */
    public static final int interp_type_sinCloser = 5;
    /**
     * 插值核函数类型
     */
    private int interp_type = interp_type_sinCloser;
    
    public BicubicZoomer() {}
    
    @Override
    public BufferedImage zoom(BufferedImage bi, int destW, int destH, int imgType) {
        // TODO Auto-generated method stub
        int srcW = bi.getWidth();
        int srcH = bi.getHeight();
        
        // 获取src_argbs
        int[][][] src_argbs = RgbUtil.decompose_argbs(bi);
        // 创建dest-argbs
        int[][][] dest_argbs = new int[destW][destH][4];
        
        // x方向上的比例，和y方向上的比例，这里的坐标轴：左上角为(0,0)，x轴正方向水平向右，y轴正方向竖直向下
        float x_ratio = ((float) (srcW)) / ((float) (destW));
        float y_ratio = ((float) (srcH)) / ((float) (destH));
        
        for (int x = 0; x < destW; x++) {
            double src_x = ((float) x) * x_ratio;
            // src_x 的整数部分
            int src_xinteger = (int) Math.floor(src_x);
            // src_x 的小数部分
            double src_xdecimal = src_x - (double) src_xinteger;
            
            for (int y = 0; y < destH; y++) {
                double src_y = ((float) y) * y_ratio;
                // src_y 的整数部分
                int src_yinteger = (int) Math.floor(src_y);
                // src_y 的小数部分
                double src_ydecimal = src_y - (double) src_yinteger;
                        
                // 遍历16个临近点，根据插值核函数R(x)求得D(x,y)的值
                for (int m = -1; m < 3; m++) {
                    for (int n = -1; n < 3; n++) {
                        double rx = 0.0d; // rx = R(src_xdecimal - m)
                        double ry = 0.0d; // ry = R(src_ydecimal - n)
                        
                        rx = interpCalc((double) (src_xdecimal - m), interp_type);
                        ry = interpCalc((double) (src_ydecimal - n), interp_type);
                        
                        int clip_x = ZoomerMath.clip(src_xinteger + m, srcW - 1, 0);
                        int clip_y = ZoomerMath.clip(src_yinteger + n, srcH - 1, 0);
                        
                        dest_argbs[x][y][0] += (int) (src_argbs[clip_x][clip_y][0] * rx * ry); // alpha
                        dest_argbs[x][y][1] += (int) (src_argbs[clip_x][clip_y][1] * rx * ry); // red
                        dest_argbs[x][y][2] += (int) (src_argbs[clip_x][clip_y][2] * rx * ry); // green
                        dest_argbs[x][y][3] += (int) (src_argbs[clip_x][clip_y][3] * rx * ry); // blue
                    } // for n
                } // for m
                dest_argbs[x][y][0] = saturate(dest_argbs[x][y][0]); // alpha 饱和到 [0, 255]
                dest_argbs[x][y][1] = saturate(dest_argbs[x][y][1]); // red 饱和到 [0, 255]
                dest_argbs[x][y][2] = saturate(dest_argbs[x][y][2]); // green 饱和到 [0, 255]
                dest_argbs[x][y][3] = saturate(dest_argbs[x][y][3]); // blue 饱和到 [0, 255]
            } // for y
        } // for x
        
        BufferedImage zoom_img = new BufferedImage(destW, destH, imgType);
        int[][] dest_rgbs = RgbUtil.combine_argbs(dest_argbs);
        for (int x = 0; x < destW; x++) {
            for (int y = 0; y < destH; y++) {
                zoom_img.setRGB(x, y, dest_rgbs[x][y]);
            }
        }
        return zoom_img;
    }
    
    /**
     * 将value饱和到[0, 255]范围内
     * @param value
     * @return
     */
    private static int saturate(int value) {
        return value > 255 ? 255 : value < 0 ? 0 : value;
    }
    
    public void setInterpType(int interpType) {
        interp_type = interpType;
    }
    
    private static double interpCalc(double x, int select) {
        switch (select) {
        case interp_type_triangle:
            return ZoomerMath.triangleInterp(x);
        case interp_type_bell:
            return ZoomerMath.bellInterp(x);
        case interp_type_bspline:
            return ZoomerMath.bsplineInterp(x);
        case interp_type_sinCloser:
            return ZoomerMath.sinCloserInterp(x);
        case interp_type_catmullRom:
            return ZoomerMath.catmullRomInterp(x);
        default:
            return ZoomerMath.sinCloserInterp(x);    
        }
    }
}
