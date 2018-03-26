package southday.hwdr.segment;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * 分割器
 * @author southday
 *
 */
public interface Slicer {
    /**
     * 图像分割，将手写号码分割成若干个子图片对象
     * @param bi 被分割的对象
     * @return 保存子图片对象的数组表
     */
    public abstract ArrayList<BufferedImage> segment(BufferedImage bi);
}
