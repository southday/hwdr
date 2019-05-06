### 手写号码识别
使用Java实现了图像处理识别中的部分算法，内容包括：</br>

- 代码
    + 灰度化&二值化
        * Bernsen (伯恩森算法) [BernsenBinaryz.java](./hwdr/src/southday/hwdr/binaryz/BernsenBinaryz.java)
        * Ostu (大律法) [OstuBinaryz.java](./hwdr/src/southday/hwdr/binaryz/OstuBinaryz.java)
    + 字符分割
        * 滴水算法 [DropFallSlicer.java](./hwdr/src/southday/hwdr/segment/DropFallSlicer.java)
        * 垂直投影分割算法 [VerticalProjectionSlicer.java](./hwdr/src/southday/hwdr/segment/VerticalProjectionSlicer.java)
    + 字符细化
        * Hilditch (希尔迪奇算法) [HilditchSkeleton.java](./hwdr/src/southday/hwdr/skeleton/HilditchSkeleton.java)
        * ZS算法 [ZSSkeleton.java](./hwdr/src/southday/hwdr/skeleton/ZSSkeleton.java)
    + 字符缩放
        * Bilinear (双线性插值缩放算法) [BilinearZoomer.java](./hwdr/src/southday/hwdr/zoom/BilinearZoomer.java)
        * Bicubic (三线性插值缩放算法) [BicubicZoomer.java](./hwdr/src/southday/hwdr/zoom/BicubicZoomer.java)
    + 字符特征提取
        * 粗网格特征提取法 [CoarseMeshFeatureExtractor.java](./hwdr/src/southday/hwdr/recognize/fextractor/CoarseMeshFeatureExtractor.java)
    + 字符识别
        * SVM支持向量机 (使用libsvm) [SvmRecognizer.java](./hwdr/src/southday/hwdr/recognize/svm/SvmRecognizer.java)
    + 测试代码
- 测试与训练数据
    + USPS美国邮政服务手写数字识别库 [TestPackage.zip](./TestPackage.zip)

### 开发平台

- 开发工具：Eclipse Mars
- JDK：jdk 1.8.0
- Charset：UTF-8