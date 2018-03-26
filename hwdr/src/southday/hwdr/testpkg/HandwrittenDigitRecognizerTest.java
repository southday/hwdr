package southday.hwdr.testpkg;

import java.io.IOException;

import southday.hwdr.HandwrittenDigitRecognizer;

public class HandwrittenDigitRecognizerTest {
    public static void main(String[] args) throws IOException {
        String picture_name = "F:\\Handwritten_Digits_Recognition\\result\\tup4.png";
        String model_name = "F:\\Handwritten_Digits_Recognition\\recognition\\svm\\svmmodel\\20x20_mnist_imgs_v0.model";

        HandwrittenDigitRecognizer _rec = new HandwrittenDigitRecognizer(model_name);
        long startTime = System.currentTimeMillis();
        String result = _rec.toString(picture_name);
        long endTime = System.currentTimeMillis();
        System.out.println("result = " + result + ", spend time = " + (endTime - startTime) + "ms");
    }
}
