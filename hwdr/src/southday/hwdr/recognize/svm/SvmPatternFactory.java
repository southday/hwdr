package southday.hwdr.recognize.svm;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import southday.hwdr.recognize.FeatureExtractor;
import southday.hwdr.recognize.PatternFactory;
import southday.hwdr.recognize.fextractor.CoarseMeshFeatureExtractor;
import southday.hwdr.recognize.svm.libsvm.SvmPattern;
import southday.hwdr.recognize.svm.libsvm.svm_node;

/**
 * SVM模型工厂
 * @author southday
 *
 */
public class SvmPatternFactory extends PatternFactory<svm_node[], SvmPattern> {
    
    public SvmPatternFactory() {
        this(new CoarseMeshFeatureExtractor());
    }
    
    /**
     * constructor
     * @param fex
     */
    public SvmPatternFactory(FeatureExtractor<double[]> fex) {
        super(fex);
    }
    
    /**
     * 返回一个特征化的样本（基于LibSVM样本格式）
     * @param sample
     * @return 特征化样本[label, svm_nodes[]]
     * @throws IOException
     */
    @Override
    public SvmPattern produce_sample(File sample) throws IOException {
        BufferedImage img = ImageIO.read(sample);
        SvmPattern svm_pattern = new SvmPattern();
        
        // 标签
        svm_pattern.label = SvmPatternFactory.what_label(sample);
        // 初始化svm_pattern，并且填充nodes
        svm_pattern.svm_nodes = produce_inputmodel(img);
        
        return svm_pattern;
    }
    
    /**
     * 生成图像预测(识别)中所需的输入值类型(模型),比如：
     * <p>LibSVM中的支持向量机输入模型为：svm_node[]</p>
     * @param bi
     * @return
     * @throws IOException
     */
    @Override
    public svm_node[] produce_inputmodel(BufferedImage bi) throws IOException {
        double feaVector[] = fex.extract(bi);
        svm_node[] svm_nodes = new svm_node[feaVector.length];
        
        for (int i = 0; i < feaVector.length; i++) {
            svm_nodes[i] = new svm_node();
            svm_nodes[i].index = i + 1;
            svm_nodes[i].value = feaVector[i];
        }
        return svm_nodes;
    }
    
    /**
     * 返回样本标签
     * @param sample
     * @return
     */
    private static int what_label(File sample) {
        // 取sample名称的第一个字符（与文件名耦合）
        return Integer.parseInt(sample.getName().substring(0, 1));
    }
    
    /**
     * 根据LibSVM样本的格式将svm_fea_samp转换为相应格式的String,
     * 以便写入文件
     * @param svm_fea_samp 特征化样本
     * @return 符合LibSVM样本格式的String
     */
    public static String format(SvmPattern svm_fea_samp) {
        StringBuilder sb = new StringBuilder();
        
        sb.append(svm_fea_samp.label + " "); // 写入label
        // 写入特征值
        for (int i = 0; i < svm_fea_samp.svm_nodes.length; i++) {
            // value等于0的时候可以跳过不写
            if (svm_fea_samp.svm_nodes[i].value != 0.0) {
                sb.append(svm_fea_samp.svm_nodes[i].index + ":"
                        + svm_fea_samp.svm_nodes[i].value + " ");
            }
        }
        return sb.toString();
    }
    
}
