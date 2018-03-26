package southday.hwdr.recognize;

import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 识别器
 * @author southday
 *
 */
public interface Recognizer {
    
    /**
     * 对进行过相应处理后的图片进行预测(识别)
     * @param bi
     * @return 预测结果(0~9)
     * @throws IOException
     */
    public abstract String predict(BufferedImage bi) throws IOException;
    
    public abstract void setFeatureExtractor(FeatureExtractor<double[]> fex);
}
