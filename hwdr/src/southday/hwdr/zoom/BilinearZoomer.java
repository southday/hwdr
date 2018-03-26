package southday.hwdr.zoom;

import java.awt.image.BufferedImage;

import southday.hwdr.util.RgbUtil;

/**
 * 基于双线性插值算法的缩放器，学自：
 * <p> http://blog.csdn.net/jia20003/article/details/6915185 </p>
 * <p> 源图片为(Sw*Sh)像素，目标图片为(Dw*Dh)像素 </p>
 * <p> 根据最邻近插值算法可以求出源像素点坐标
 *         (x,y)：Sx = Dx * (Sw / Dw)， Sy = Dy * (Sh / Dh)，不同的是(x,y)均为浮点数 </p>
 * <p> 目标像素点的值D(x,y)可由公式：
 * <p> D(x,y) = 
 *         S(ltp_x,ltp_y)*ltp_weight + S(lbp_x,lbp_y)*lbp_weight + 
 *            S(rtp_x,rtp_y)*rtp_weight + S(rbp_x,rbp_y)*rbp_weight 算出，
 * 其中ltp（左上角）, lbp（左下角）, rtp（右上角）, rbp（右下角）为距离源像素点(x,y)最近的四个像素点
 * </p>
 * 
 * @author southday
 *
 */
public class BilinearZoomer implements Zoomer {
    
    public BilinearZoomer() {}

    @Override
    public BufferedImage zoom(BufferedImage bi, int destW, int destH, int imgType) {
        // TODO Auto-generated method stub
        int srcW = bi.getWidth();
        int srcH = bi.getHeight();

        // 获取src_argbs
        int[][][] src_argbs = RgbUtil.decompose_argbs(bi);
        // 创建dest_argbs
        int[][][] dest_argbs = new int[destW][destH][4];
        
        // x方向上的比例，和y方向上的比例，这里的坐标轴：左上角为(0,0)，x轴正方向水平向右，y轴正方向竖直向下
        float x_ratio = ((float) (srcW)) / ((float) (destW));
        float y_ratio = ((float) (srcH)) / ((float) (destH));

        for (int x = 0; x < destW; x++) {
            double src_x = ((float) x) * x_ratio;
            /*
             * ltp : left top point
             * ltp_x 为距离src(x,y)最近的4个点中的最左上角的点的横坐标
             */
            int ltp_x = (int) Math.floor(src_x);
            
            // src_xdecimal 为经过运算得到的源像素点src(x,y)横坐标的小数部分
            double src_xdecimal = src_x - (double) ltp_x;
            
            for (int y = 0; y < destH; y++) {
                double src_y = ((float) y) * y_ratio;
                // ltp_y 同理
                int ltp_y = (int) Math.floor(src_y);
                // src_ydecimal 同理
                double src_ydecimal = src_y - (double) ltp_y;
                
                /*
                 * ltp: left top point 左上角点
                 * lbp: left bottom point 左下角点
                 * rtp: right top point 右上角点
                 * rbp: right bottom point 右下角点
                 */
                double ltp_weight = (1.0d - src_xdecimal) * (1.0d - src_ydecimal);
                double lbp_weight = src_xdecimal * (1.0d - src_ydecimal);
                double rtp_weight = (1.0d - src_xdecimal) * src_ydecimal;
                double rbp_weight = src_xdecimal * src_ydecimal;
                
                int clip_ltp_x = ZoomerMath.clip(ltp_x, srcW - 1, 0);
                int clip_ltp_y = ZoomerMath.clip(ltp_y, srcH - 1, 0);
                int clip_rtp_x = ZoomerMath.clip(ltp_x + 1, srcW - 1, 0); // rtp 与 ltp 共用y，rbp 与 rtp 共用x
                int clip_lbp_y = ZoomerMath.clip(ltp_y + 1, srcH - 1, 0); // lbp 与 ltp 共用x，rbp 与 lbp 共用y
                
                dest_argbs[x][y][0] = // alpha
                        (int) (src_argbs[clip_ltp_x][clip_ltp_y][0] * ltp_weight) + // ltp_alpha
                        (int) (src_argbs[clip_ltp_x][clip_lbp_y][0] * lbp_weight) + // lbp_alpha
                        (int) (src_argbs[clip_rtp_x][clip_ltp_y][0] * rtp_weight) + // rtp_alpha
                        (int) (src_argbs[clip_rtp_x][clip_lbp_y][0] * rbp_weight);  // rbp_alpha
                
                dest_argbs[x][y][1] = // red
                        (int) (src_argbs[clip_ltp_x][clip_ltp_y][1] * ltp_weight) + // ltp_red
                        (int) (src_argbs[clip_ltp_x][clip_lbp_y][1] * lbp_weight) + // lbp_red
                        (int) (src_argbs[clip_rtp_x][clip_ltp_y][1] * rtp_weight) + // rtp_red
                        (int) (src_argbs[clip_rtp_x][clip_lbp_y][1] * rbp_weight);  // rbp_red
                
                dest_argbs[x][y][2] = // green
                        (int) (src_argbs[clip_ltp_x][clip_ltp_y][2] * ltp_weight) + // ltp_green
                        (int) (src_argbs[clip_ltp_x][clip_lbp_y][2] * lbp_weight) + // lbp_green
                        (int) (src_argbs[clip_rtp_x][clip_ltp_y][2] * rtp_weight) + // rtp_green
                        (int) (src_argbs[clip_rtp_x][clip_lbp_y][2] * rbp_weight);  // rbp_green
                
                dest_argbs[x][y][3] = // blue
                        (int) (src_argbs[clip_ltp_x][clip_ltp_y][3] * ltp_weight) + // ltp_blue
                        (int) (src_argbs[clip_ltp_x][clip_lbp_y][3] * lbp_weight) + // lbp_blue
                        (int) (src_argbs[clip_rtp_x][clip_ltp_y][3] * rtp_weight) + // rtp_blue
                        (int) (src_argbs[clip_rtp_x][clip_lbp_y][3] * rbp_weight);  // rbp_blue
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
    
}
