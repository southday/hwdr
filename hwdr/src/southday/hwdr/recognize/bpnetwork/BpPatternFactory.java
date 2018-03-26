package southday.hwdr.recognize.bpnetwork;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import southday.hwdr.recognize.FeatureExtractor;
import southday.hwdr.recognize.PatternFactory;
import southday.hwdr.recognize.fextractor.CoarseMeshFeatureExtractor;

/**
 * Bp神经网络模型工厂
 * @author southday
 *
 */
public class BpPatternFactory extends PatternFactory<double[], BpPattern> {

    public BpPatternFactory() {
        this(new CoarseMeshFeatureExtractor());        
    }
    
    /**
     * Constructor
     * @param fex 特征提取器
     */
    public BpPatternFactory(FeatureExtractor<double[]> fex) {
        super(fex);
    }
    
    /**
     * 生成Bp神经网络的训练和测试样本
     * @param sample
     * @return
     * @throws IOException
     */
    @Override
    public BpPattern produce_sample(File sample) throws IOException {
        // TODO Auto-generated method stub
        
        BufferedImage img = ImageIO.read(sample);
        double[] inputs = produce_inputmodel(img);
        double[] outputs = toOutputs(sample.getName());
        return new BpPattern(inputs, outputs);
    }
    
    /**
     * 生成图像预测(识别)中所需的输入值类型(模型),比如：
     * <p>Bp神经网络中的输入模型为：double[]</p>
     * @param bi
     * @return
     * @throws IOException
     */
    @Override
    public double[] produce_inputmodel(BufferedImage bi) throws IOException {
        return fex.extract(bi);
    }
    
    private double[] toOutputs(String fileName) {
        double[] outputs = new double[10];
        int value = Integer.parseInt(fileName.substring(0, 1));
        for (int i = 0; i < 10; i++) {
            outputs[i] = (i == value) ? 1 : 0;
        }
        return outputs;
    }
    
}
