package southday.hwdr.testpkg.svm;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import southday.hwdr.recognize.svm.SvmPatternFactory;
import southday.hwdr.recognize.svm.libsvm.SvmPattern;

public class SvmFeatureSampleTest {
    public static void main(String[] args) throws Exception {
//        generate_featureSamples();
        generate_testdata();
    }
    
    /**
     * 生成训练样本数据
     * @throws IOException
     */
    public static void generate_featureSamples() throws Exception {
        SvmPatternFactory svm_fsg = new SvmPatternFactory();
//        File dir = new File("F:\\Handwritten_Digits_Recognition\\20x20-mnist-images-test\\");
        String dir = "F:\\Handwritten_Digits_Recognition\\20x20-mnist-images-test\\";
        FileWriter fw = new FileWriter(new File("F:\\Handwritten_Digits_Recognition\\recognition\\svm\\svmtest\\20x20_mnist_imgs_v0.t"));
        BufferedWriter bfw = new BufferedWriter(fw);
        
//        File[] files = dir.listFiles();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 800; i++) {
            for (int j = 0; j < 10; j++) {
                File sample = new File(dir + j + "_" + i + ".bmp");
                SvmPattern svm_fea_samp = svm_fsg.produce_sample(sample);
                String str_sample = SvmPatternFactory.format(svm_fea_samp);
                bfw.write(str_sample);
                bfw.newLine();
            }
        }
//        for (File sample : files) {
//            SvmPattern svm_fea_samp = svm_fsg.produce_sample(sample);
//            String str_sample = SvmPatternFactory.format(svm_fea_samp);
//            bfw.write(str_sample);
//            bfw.newLine();
//        }
        bfw.close();
        long endTime = System.currentTimeMillis();
        System.out.println("over : " + (endTime - startTime) + "ms");        
    }
    
    public static void generate_testdata() throws Exception {
        SvmPatternFactory svm_fsg = new SvmPatternFactory();
        File dir = new File("F:\\Handwritten_Digits_Recognition\\20x20-mnist-images-test\\");
        File[] samples = dir.listFiles();
        
        FileWriter fw = new FileWriter(new File("F:\\Handwritten_Digits_Recognition\\svm\\svmtest\\20x20_mnist_imgs_v0.t"));
        BufferedWriter bfw = new BufferedWriter(fw);
        
        long startTime = System.currentTimeMillis();
        for (File sample : samples) {
            SvmPattern svm_fea_samp = svm_fsg.produce_sample(sample);
            String str_sample = SvmPatternFactory.format(svm_fea_samp);
            bfw.write(str_sample);
            bfw.newLine();            
        }

        bfw.close();
        long endTime = System.currentTimeMillis();
        System.out.println("over : " + (endTime - startTime) + "ms");        
    }
}
