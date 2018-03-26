package southday.hwdr.testpkg;

import java.io.File;

import southday.hwdr.recognize.PatternList;
import southday.hwdr.recognize.bpnetwork.BpPattern;
import southday.hwdr.recognize.bpnetwork.BpPatternFactory;

public class BpTrainDataGenerator {
    public static void main(String[] args) throws Exception {
//        getTrainData();
        testTrainData();
    }
    
    public static void getTrainData() throws Exception {
        long startTime = System.currentTimeMillis();
        String dir = "F:\\Handwritten_Digits_Recognition\\20x20-mnist-images\\";
        BpPatternFactory bppf = new BpPatternFactory();
        PatternList<BpPattern> plst = new PatternList<BpPattern>();
        
        for (int i = 0; i < 700; i++) {
            for (int j = 0; j < 10; j++) {
                File file = new File(dir + j + "_" + i + ".bmp");
//                File file = new File(dir + "0_" + i + ".bmp");
                plst.add(bppf.produce_sample(file));
            }
        }
        
        plst.writer(new File("F:\\Handwritten_Digits_Recognition\\BpNetworkTestFile\\" + "BpNetwork_modify_0_1.train"));
        long endTime = System.currentTimeMillis();
        System.out.println("over : " + (endTime - startTime));        
    }
    
    public static void testTrainData() throws Exception {
        PatternList<BpPattern> plst = new PatternList<BpPattern>();
        plst.reader(new File("F:\\Handwritten_Digits_Recognition\\BpNetworkTestFile\\BpNetwork_modify_0_1.train"));
        
        BpPattern bpp = plst.get(6999);
        double[] inputs = bpp.getInputs();
        double[] outputs = bpp.getOutputs();
        
        for (int i = 0; i < inputs.length; i++) {
            System.out.println("intputs " + i + " --> " + inputs[i]);
        }
        for (int i = 0; i < outputs.length; i++) {
            System.out.println("outputs " + i + " --> " + outputs[i]);
        }
    }
}
