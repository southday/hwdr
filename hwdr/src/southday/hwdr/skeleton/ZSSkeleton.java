package southday.hwdr.skeleton;

import java.awt.image.BufferedImage;

import southday.hwdr.util.RgbUtil;

/**
 * 基于ZS算法的细化器
 * <p>p9 p2 p3</p>
 * <p>p8 p1 p4</p>
 * <p>p7 p6 p5</p>
 * @author southday
 *
 */
public class ZSSkeleton implements Skeleton {
    /**
     * 存储了图像中对应坐标像素rgb值经过归一化(0或1)的值，
     * <p> rgbs[x][y] = 1 代表前景色-黑色 </p>
     * <p> rgbs[x][y] = 0 代表背景色-白色 </p>
     * <p> rgbs[x][y] = -1 代表被标记，此时与前景色等价</p>
     */
    private int[][] rgbs; 
    
    /**
     * constructor
     */
    public ZSSkeleton() {}

    /**
     * 遍历p2,p3,...,p9,p2每2个像素组成的序列，如：
     * <p> <\p2,p3> <\p3,p4> ... <\p9,p2> </p>
     * <p> 计算满足<\前景色,背景色>序列的个数，在这里也等价于<\黑色,白色> </p>
     * @param rgbs 保存了待处理图片中所有坐标点对应的像素的rgb值
     * @param x 像素p1的横坐标x
     * @param y 像素p1的纵坐标y
     * @return 满足<\前景色,背景色>序列的个数
     */
    private int seq_neighbour_num(int x, int y) {
        int count = 0;
        
        // 为了避免越界，在扫描bi时，应该从(1,1)开始扫描,到(width-2, height-2)结束
        // 这里使用 != 1 是因为在细化过程中要对（可以去除的点）进行标记，而我是直接在rgbs上标记的，标记为 -1（和0是等价的）
        if (rgbs[x][y - 1] != 1 && rgbs[x + 1][y - 1] == 1) count++; // <p2,p3>
        if (rgbs[x + 1][y - 1] != 1 && rgbs[x + 1][y] == 1) count++; // <p3,p4>
        if (rgbs[x + 1][y] != 1 && rgbs[x + 1][y + 1] == 1) count++; // <p4,p5>
        if (rgbs[x + 1][y + 1] != 1 && rgbs[x][y + 1] == 1) count++; // <p5,p6>
        if (rgbs[x][y + 1] != 1 && rgbs[x - 1][y + 1] == 1) count++; // <p6,p7>
        if (rgbs[x - 1][y + 1] != 1 && rgbs[x - 1][y] == 1) count++; // <p7,p8>
        if (rgbs[x - 1][y] != 1 && rgbs[x - 1][y - 1] == 1) count++; // <p8,p9>
        if (rgbs[x - 1][y - 1] != 1 && rgbs[x][y - 1] == 1) count++; // <p9,p2>
        
        return count;
    }
    
    @Override
    public BufferedImage skeleton(BufferedImage bi) {
        // 初始化rgbs
        rgbs = SkeletonUtil.init_rgbs(bi);
        
        boolean isModified = true;
        do {
            isModified = false;
            // 为了避免角标越界，从(1, 1)开始到(width - 2, heigth - 2)结束
            for (int x = 1; x < rgbs.length - 1; x++) {
                for (int y = 1; y < rgbs[0].length - 1; y++) {
                    if (rgbs[x][y] == 1) {
                        
                        // (1) 条件1：2 <= B(p1) <= 6
                        // B(p1) 就是 black_neighbour_num(p1)
                        int black_nei_num = SkeletonUtil.black_neighbour_num(rgbs, x, y);
                        if (black_nei_num < 2 || black_nei_num > 6) continue;
                        
                        // (2) 条件2: A(p1) == 1
                        // A(p1) 就是 seq_neighbour_num(p1)
                        if (seq_neighbour_num(x, y) != 1) continue;
                        
                        /* (3)' 在满足(1)(2)的条件下，满足下面的其中一个条件即可
                         * a) p2 * p4 * p6 == 0 && p4 * p6 * p8 == 0 [→,↓]
                         * b) p2 * p4 * p8 == 0 && p2 * p6 * p8 == 0 [↑,←]
                         * 【注】：当3个值相乘等于-1时也算，因为-1是用来标记要删除的点，与背景色等价
                         */
                        if (
                            // !a)
                            (rgbs[x][y - 1] * rgbs[x + 1][y] * rgbs[x][y + 1] == 1 ||
                             rgbs[x + 1][y] * rgbs[x][y + 1] * rgbs[x - 1][y] == 1) 
                             && // !b)
                            (rgbs[x][y - 1] * rgbs[x + 1][y] * rgbs[x - 1][y] == 1 ||
                             rgbs[x][y - 1] * rgbs[x][y + 1] * rgbs[x - 1][y] == 1)
                        ) continue;
                        
                        // (x, y)做标记，等到【循环】结束后将被标记的像素变成背景色
                        rgbs[x][y] = -1;
                        // 只要还有被修改的就需要继续迭代，直到再也没有点被标记
                        isModified = true; 
                    } // if
                } // for y
            } // for x
        } while (isModified);
        
        // 将被标记的像素点变成背景色 white
        for (int x = 1; x < rgbs.length - 1; x++) {
            for (int y = 1; y < rgbs[0].length - 1; y++) {
                if (rgbs[x][y] == -1) {
                    bi.setRGB(x, y, RgbUtil.white);
                }
            }
        }
        return bi;
    }
}
