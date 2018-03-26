package southday.hwdr.zoom;

import java.awt.image.BufferedImage;

/**
 * 图像缩放器
 * @author southday
 *
 */
public interface Zoomer {
    
    /**
     * 将源图像缩放为目标图像大小
     * @param bi 源图像
     * @param destW 目标图像宽度
     * @param destH 目标图像高度
     * @param imgType 目标图像的类型（一般为：BufferedImage.TYPE_BYTE_BINARY、TYPE_INT_RGB、TYPE_INT_ARGB）
     * @return 缩放后的图像
     */
    public abstract BufferedImage zoom(BufferedImage bi, int destW, int destH, int imgType);
}
