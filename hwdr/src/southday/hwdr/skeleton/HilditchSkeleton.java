package southday.hwdr.skeleton;

import java.awt.image.BufferedImage;

import southday.hwdr.util.RgbUtil;

/**
 * 基于Hilditch【希尔迪奇】算法的细化器，学自：
 * <p>http://www.cnblogs.com/xiaotie/archive/2010/08/12/1797760.html</p>
 * <p> x4 x3 x2 </p>
 * <p> x5 p  x1 </p>
 * <p> x6 x7 x8 </p>
 * @author southday
 *
 */
public class HilditchSkeleton implements Skeleton {
    /**
     * 存储了图像中对应坐标像素rgb值经过归一化(0或1)的值，
     * <p> rgbs[x][y] = 1 代表前景色-黑色 </p>
     * <p> rgbs[x][y] = 0 代表背景色-白色 </p>
     * <p> rgbs[x][y] = -1 代表被标记，此时与前景色等价</p>
     */
    private int[][] rgbs; 
    
    public HilditchSkeleton() {}
    
    /**
     * 计算当前点p的8连通联结数，计算公式如下：
     * <p>(~x7 - ~x7 * ~x8 * ~x1) + sigma(~xk - ~xk * ~x(k+1) * ~x(k+2), k = {1, 3, 5} </p>
     * <p> ~x 表示取点x的反色，即当x表示前景色(值为1)时, ~x表示背景色(值为0), 相反也如此 </p>
     * @param x p的横坐标
     * @param y p的纵坐标
     * @return p的8连通联结数
     */
    private int eight_connected_num(int x, int y) {
        /*
         * 考虑到rgbs[][]中某些点会被标记为-1，和算法中要求的"非1即0"不符合，
         * 所以需要做一些变换：
         *  x 的值为 0, -1 时 表示 背景色，特征: <= 0
         *  x 的值为 1 时表示 前景色， 特征: > 0
         * 这样以来，通过与0的比较即可实现对x取反色，且符合算法要求的"非1即0"
         */
        int x1 = rgbs[x + 1][y] > 0 ? 0 : 1;
        int x2 = rgbs[x + 1][y - 1] > 0 ? 0 : 1;
        int x3 = rgbs[x][y - 1] > 0 ? 0 : 1;
        int x4 = rgbs[x - 1][y - 1] > 0 ? 0 : 1;
        int x5 = rgbs[x - 1][y] > 0 ? 0 : 1;
        int x6 = rgbs[x - 1][y + 1] > 0 ? 0 : 1;
        int x7 = rgbs[x][y + 1] > 0 ? 0 : 1;
        int x8 = rgbs[x + 1][y + 1] > 0 ? 0 : 1;
        
        int sum = x1 - x1 * x2 * x3;
        sum += x3 - x3 * x4 * x5;
        sum += x5 - x5 * x6 * x7;
        sum += x7 - x7 * x8 * x1;
        
        return sum;
    }
    
    @Override
    public BufferedImage skeleton(BufferedImage bi) {
        // TODO Auto-generated method stub
        
        // 初始化rgbs
        rgbs = SkeletonUtil.init_rgbs(bi);
        
        boolean isModified = true;
        int temp = 0;
        do {
            isModified = false;
            // 为了避免角标越界，从(1, 1)开始到(width - 2, heigth - 2)结束
            for (int x = 1; x < rgbs.length - 1; x++) {
                for (int y = 1; y < rgbs[0].length - 1; y++) {
                    if (rgbs[x][y] == 1) {
                        /*
                         * (1) 条件1：x1, x3, x5, x7 不全部为1（前景色）
                         * 若全部为1，且把点p(x,y)删除，图像就会出现空心
                         */
                        if (
                            rgbs[x + 1][y] == 1 &&    // x1
                            rgbs[x][y - 1] == 1 &&    // x3
                            rgbs[x - 1][y] == 1 &&    // x5
                            rgbs[x][y + 1] == 1        // x7
                        ) continue;
                        
                        /*
                         * (2) 条件2：x1~x8中至少有2个为1
                         * 若只有1个为1，则是线段端点；若没有为1的，则是孤立点
                         */
                        if (SkeletonUtil.black_neighbour_num(rgbs, x, y) < 2)
                            continue;
                        
                        // (3) 条件3：点p的8连通联结数为1
                        if (eight_connected_num(x, y) != 1) continue;
                        
                        // (4) 条件4：假设x3已经标记为删除（即标记为-1），那么当x3被标记后，p的8连通联结数为1
                        temp = rgbs[x][y - 1];
                        rgbs[x][y - 1] = -1; // 标记x3
                        if (eight_connected_num(x, y) != 1) {
                            rgbs[x][y - 1] = temp; // 还原x3值
                            continue;
                        }
                        rgbs[x][y - 1] = temp; // 还原x3值
                        
                        // (5) 条件5：假设x5已经标记为删除（即标记为-1），那么当x5被标记后，p的8连通联结数为1
                        temp = rgbs[x - 1][y];
                        rgbs[x - 1][y] = -1; // 标记x5
                        if (eight_connected_num(x, y) != 1) {
                            rgbs[x - 1][y] = temp; // 还原x5值
                            continue;
                        }
                        rgbs[x - 1][y] = temp; // 还原x5值
                        
                        // (x, y)做标记，等到【循环】结束后将被标记的像素变成背景色
                        rgbs[x][y] = -1;
                        // 只要还有被修改的就需要继续迭代，直到再也没有点被标记
                        isModified = true; 
                    } // if
                } // for y
            }// for x
            
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
