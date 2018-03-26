package southday.hwdr.recognize.bpnetwork;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import southday.hwdr.recognize.FeatureExtractor;
import southday.hwdr.recognize.Recognizer;
import southday.hwdr.recognize.fextractor.CoarseMeshFeatureExtractor;

/**
 * 基于Bp神经网络的识别器
 * @author southday
 *
 */
public class BpNetRecognizer implements Recognizer {
    /**
     * Bp神经网络模型工厂
     */
    private BpPatternFactory bpp_factory = null;
    /**
     * Bp网络(已经过训练)
     */
    private NetWork bp_net = null;
    
    public BpNetRecognizer(NetWork bp_net) {
        this(bp_net, new CoarseMeshFeatureExtractor());
    }
    
    public BpNetRecognizer(NetWork bp_net, FeatureExtractor<double[]> fex) {
        bpp_factory = new BpPatternFactory(fex);
        this.bp_net = bp_net;
    }
    
    public BpNetRecognizer(String bp_net_file_name) 
            throws IOException, FileNotFoundException, ClassNotFoundException {
        this(bp_net_file_name, new CoarseMeshFeatureExtractor());
    }
    
    public BpNetRecognizer(String bp_net_file_name, FeatureExtractor<double[]> fex) 
            throws IOException, FileNotFoundException, ClassNotFoundException {
        bpp_factory = new BpPatternFactory(fex);
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(bp_net_file_name)));
        bp_net = (NetWork) ois.readObject();
        ois.close();
    }
    
    @Override
    public String predict(BufferedImage bi) throws IOException {
        // TODO Auto-generated method stub
        
        double[] input = bpp_factory.produce_inputmodel(bi);
        int out[] = BpMath.thresholdArray(BpMath.out_threshold, bp_net.runNetWork(input));
        String result = getResult(out);
        return result;
    }
    
    private String getResult(int[] out) {
        for (int i = 0; i < out.length; i++) {
            if (out[i] == 1) {
                return String.valueOf(i);
            }
        }
        return "-1"; // 若返回-1代表识别失败
    }
    
    @Override
    public void setFeatureExtractor(FeatureExtractor<double[]> fex) {
        // TODO Auto-generated method stub
        
        bpp_factory.setFeatureExtractor(fex);
    }

}
