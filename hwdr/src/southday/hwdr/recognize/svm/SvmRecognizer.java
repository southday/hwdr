package southday.hwdr.recognize.svm;

import java.awt.image.BufferedImage;
import java.io.IOException;

import southday.hwdr.recognize.FeatureExtractor;
import southday.hwdr.recognize.Recognizer;
import southday.hwdr.recognize.fextractor.CoarseMeshFeatureExtractor;
import southday.hwdr.recognize.svm.libsvm.svm;
import southday.hwdr.recognize.svm.libsvm.svm_model;
import southday.hwdr.recognize.svm.libsvm.svm_node;

/**
 * 基于SVM的识别器
 * @author southday
 *
 */
public class SvmRecognizer implements Recognizer {
    /**
     * SVM模型工厂
     */
    private SvmPatternFactory svmp_factory = null;
    /**
     * 经过训练得出的SVM模型(用于识别)
     */
    private svm_model svm_model = null;
    
    public SvmRecognizer(String model_file_name) throws IOException {
        this(svm.svm_load_model(model_file_name), new CoarseMeshFeatureExtractor());
    }
    
    public SvmRecognizer(svm_model svm_model) {
        this(svm_model, new CoarseMeshFeatureExtractor());
    }
    
    public SvmRecognizer(String model_file_name, FeatureExtractor<double[]> fex) throws IOException {
        this(svm.svm_load_model(model_file_name), fex);
    }
    
    /**
     * constructor
     * @param svm_model
     * @param fex 特征提取器
     */
    public SvmRecognizer(svm_model svm_model, FeatureExtractor<double[]> fex) {
        this.svm_model = svm_model;
        this.svmp_factory = new SvmPatternFactory(fex);
    }
    
    @Override
    public String predict(BufferedImage bi) throws IOException {
        // TODO Auto-generated method stub
        
        svm_node[] svm_nodes = svmp_factory.produce_inputmodel(bi);
        String result = String.valueOf((int) svm.svm_predict(svm_model, svm_nodes));
        return result;
    }

    @Override
    public void setFeatureExtractor(FeatureExtractor<double[]> fex) {
        svmp_factory.setFeatureExtractor(fex);
    }
}
