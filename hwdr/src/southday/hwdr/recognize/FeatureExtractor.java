package southday.hwdr.recognize;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 特征提取器
 * @author southday
 *
 */
public interface FeatureExtractor<T> {
    
    /**
     * 提取特征,
     * <T> 不应该只有int[], double[] 等基本数据类型构成的集合，
     * 应该可以有其他自定义类型特征，然后通过“装饰模式”定义相关子类，
     * 再进行特征提取 --> 2015/12/7 0:12
     * 
     * @param bi
     * @throws IOException
     * @return 特征值
     */
    public abstract T extract(BufferedImage bi) throws IOException;
}
