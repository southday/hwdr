package southday.hwdr.testpkg;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import southday.hwdr.recognize.fextractor.CoarseMeshFeatureExtractor;
import southday.hwdr.util.RgbUtil;

public class CoarseMeshFeatureExtractorTest {
    public static void main(String[] args) throws IOException {
        File file = new File("F:\\Handwritten_Digits_Recognition\\BpNetworkTestFile\\1_169.bmp");
        BufferedImage bf = ImageIO.read(file);
        CoarseMeshFeatureExtractor cmfe = new CoarseMeshFeatureExtractor();
        double[] feaVector = cmfe.extract(bf);
        
        int bpiNum1 = 0;
        for (int i = 0; i < feaVector.length; i++) {
            System.out.println(i + " --> " + feaVector[i]);
            bpiNum1 += feaVector[i];
        }
        System.out.println("-----------black pi num = " + bpiNum1);
        
        int bpiNum2 = 0;
        for (int x = 0; x < 28; x++) {
            for (int y = 0; y < 28; y++) {
                if (RgbUtil.isBlack(bf.getRGB(x, y))) bpiNum2++;
            }
        }
        System.out.println("-----------black pi num = " + bpiNum2);
    }
}
