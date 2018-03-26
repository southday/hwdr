package southday.hwdr.segment;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import southday.hwdr.util.RgbUtil;
/**
 * 基于"滴水算法"的针对2字符粘连的分割器
 * @author southday
 *
 */
public class DropFallSlicer implements Slicer {
    /**
     * 为了避免水滴在凹平面左右不停移动，设置toRight标志；
     * 当cantoRight = false时，表示不能向右移动
     */
    private boolean cantoRight = true;
    /**
     * 图像中的二维坐标点类型(x, y)
     * @author coco
     *
     */
    private class Point {
        public int x;
        public int y;
        
        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    
    public DropFallSlicer() {}
    
    /**
     *  基于"滴水算法"的针对2字符粘连的分割
     */
    @Override
    public ArrayList<BufferedImage> segment(BufferedImage bi) {
        // TODO Auto-generated method stub
        int width = bi.getWidth();
        int height = bi.getHeight();
        
        Point cpos = searchStartPoint(bi);
        // 寻找最后的滴落点，也就是分割点
        do {
            cpos = nextpos(bi, cpos);
        } while(cpos.y != -1);
        
        // 保存切割后的子图片对象
        ArrayList<BufferedImage> sub_imgs = new ArrayList<BufferedImage>();
        // 根据水滴的最终滴落点来分割粘连字符
        BufferedImage subImg = bi.getSubimage(0, 0, cpos.x, height);
        sub_imgs.add(subImg);
        subImg = bi.getSubimage(cpos.x, 0, width - cpos.x, height);
        sub_imgs.add(subImg);
        
        return sub_imgs;
    }

    /**
     * 返回标记有滴水路径的图像
     * @param bi
     * @param save_path
     * @return
     * @throws IOException
     */
    public BufferedImage drawDropPath(BufferedImage bi, File save_path) throws IOException {
        Point cpos = searchStartPoint(bi);
        // 寻找最后的滴落点，也就是分割点
        do {
            bi.setRGB(cpos.x, cpos.y, RgbUtil.red); // 画出滴水路径
            cpos = nextpos(bi, cpos);
        } while(cpos.y != -1);
        // 写入保存位置
        ImageIO.write(bi, "png", save_path); // 这个格式应该是动态获取的... 
        return bi;
    }
    
    /**
     * 寻找水滴滴落的起始位置
     * @param bi
     * @return
     */
    private Point searchStartPoint(BufferedImage bi) {
        int width = bi.getWidth();
        int height = bi.getHeight();
        
        // 初始化滴落起始位置
        Point spos = new Point(0, 0);
        /*
         * 初始位置点p满足的条件：
         * (1) 点p是白色像素： cur_rgb = white
         * (2) 点p的前一个像素是黑色像素: pre_rgb = black
         * (3) 在该行中(纵坐标不变的情况下)点p【之后(不是直接后继)】存在黑色像素: behinds_rgb = black
         */
        int xstart = -1, ystart = -1;
        for (int y = 0; y < height; y++) {
            xstart = -1;
            for (int x = 1; x < width; x++) {
                int pre_rgb = bi.getRGB(x - 1, y);
                int cur_rgb = bi.getRGB(x, y);
                if (xstart == -1 && RgbUtil.isBlack(pre_rgb) && !RgbUtil.isBlack(cur_rgb)) {
                    xstart = x;
                    ystart = y;
                }
                if (xstart != -1 && RgbUtil.isBlack(cur_rgb)) {
                    spos.x = xstart;
                    spos.y = ystart;
                    break;
                }
            }
            if (spos.x == xstart) {
                break;
            }
        }
        return spos;
    }
    
    /**
     * 返回水滴滴落的下一个位置
     * @param bi
     * @param cpos 当前位置
     * @return 
     */
    private Point nextpos(BufferedImage bi, Point cpos) {
        int xcur = cpos.x;
        int ycur = cpos.y;
        
        // 因为目前进行分割都是基于x坐标的，即决定分割点的是x坐标，所以可以利用y坐标来判断滴落结束的标志
        if (xcur == 0|| ycur == 0 || xcur == bi.getWidth() - 1 || ycur == bi.getHeight() - 1) {
            cpos.y = -1;
            return cpos;
        }
        
        if (!RgbUtil.isBlack(bi.getRGB(xcur, ycur + 1))) { // n3
            cpos.y++;
        } else if (!RgbUtil.isBlack(bi.getRGB(xcur + 1, ycur + 1))) { // n4
            cpos.x++;    cpos.y++;
        } else if (!RgbUtil.isBlack(bi.getRGB(xcur - 1, ycur + 1))) { // n2
            cpos.x--;    cpos.y++;                    
        } else if (cantoRight && !RgbUtil.isBlack(bi.getRGB(xcur + 1, ycur))) { // n5
            cpos.x++;                                    
        } else if (!RgbUtil.isBlack(bi.getRGB(xcur - 1, ycur))) { // n1
            cpos.x--;                                                
            cantoRight = false;
        } else { // n3
            cpos.y++;
            cantoRight = true;
        }
        
        return cpos;
    }
}
