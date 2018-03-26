package southday.hwdr.recognize;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 模型工厂
 * @author southday
 *
 * @param <V> 用于识别的输入值模型
 * @param <E> 用于训练和测试的样本模型
 */
public abstract class PatternFactory<V, E> {
    /**
     * 样品特征提取器
     */
    protected FeatureExtractor<double[]> fex = null;
    
    public PatternFactory(FeatureExtractor<double[]> fex) {
        this.fex = fex;
    }
    
    /**
     * 生成图像预测(识别)中所需的输入值类型(模型)
     * @param bi
     * @return
     * @throws IOException
     */
    public abstract V produce_inputmodel(BufferedImage bi) throws IOException;
    
    /**
     * 生成用于训练或测试的样本模型，包括输入模值型和目标值
     * @param sample
     * @return
     * @throws IOException
     */
    public abstract E produce_sample(File sample) throws IOException;
    
    public void setFeatureExtractor(FeatureExtractor<double[]> fex) {
        this.fex = fex;
    }
}
