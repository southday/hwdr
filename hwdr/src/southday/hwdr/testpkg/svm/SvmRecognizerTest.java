package southday.hwdr.testpkg.svm;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import southday.hwdr.recognize.svm.SvmRecognizer;

public class SvmRecognizerTest {
    public static void main(String[] args) throws IOException {
        String name = "\\phone8\\8_phone8" + ".png";
        
        long startTime = System.currentTimeMillis();
        
        SvmRecognizer svmr = new SvmRecognizer("F:\\Handwritten_Digits_Recognition\\recognition\\svm\\svmmodel\\20x20_mnist_imgs_v0.model");
        BufferedImage bi = ImageIO.read(new File("F:\\Handwritten_Digits_Recognition\\result\\" + name));
        String result = svmr.predict(bi);
        
        long endTime = System.currentTimeMillis();
        System.out.println("over, result = " + result + ", spend time = " + (endTime - startTime) + "ms");
    }
}
