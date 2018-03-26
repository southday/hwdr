package southday.hwdr.skeleton;

import java.awt.image.BufferedImage;

/**
 * 细化器(骨架化)
 * @author southday
 *
 */
public interface Skeleton {
    /**
     * 细化
     * @param bi
     * @return
     */
    public abstract BufferedImage skeleton(BufferedImage bi);
}
