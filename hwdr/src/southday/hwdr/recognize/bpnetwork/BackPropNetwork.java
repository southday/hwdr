package southday.hwdr.recognize.bpnetwork;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import southday.hwdr.recognize.PatternList;

/**
 * from : http://sourceforge.net/projects/backprop1/
 */
public class BackPropNetwork {
    private NetWork _netWork;
    
    /**
     * BP神经网络类，基于个网络参数的构造函数
     * @param inputNum 输入层节点的数目
     * @param hiddenNum 隐藏层节点的数目
     * @param outputNum 输出层节点的数目
     * @param learningRate 学习速率
     * @param momentum 权值变化动量
     */
    public BackPropNetwork(int inputNum, int hiddenNum, int outputNum, double learningRate, double momentum) {
        _netWork = new NetWork(inputNum, hiddenNum, outputNum, learningRate, momentum);
    }
    
    /**
     * BP神经网络类，基于文件流的构造函数；读取已经保存在file中的网络
     * @param file 保存NetWork的文件
     * @throws IOException
     * @throws FileNotFoundException
     * @throws ClassNotFoundException
     */
    public BackPropNetwork(File file) throws IOException, FileNotFoundException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
        _netWork = (NetWork) ois.readObject();
        ois.close();
    }
    
    /**
     * 运行神经网络，根据输入值计算出输出结果
     * @param input 输入层节点值的集合
     * @return 输出层节点值的集合
     */
    public double[] runNetWork(double[] input) {
        return _netWork.runNetWork(input);
    }
    
    public int trainNetWork(PatternList<BpPattern> pls, double errorRate, int maxCycle, double threshold) throws IOException {
        int success = 0, maxSuccess = 0;
        int limit = pls.size();
        System.out.println("limit = " + limit);
        int count = 0, counter = 0, cc = 0;
        boolean whileFlag = true;
        
        double err = 0.0;
        if (errorRate <= 0.0) {
            errorRate = 0.001;
        }
        
        do {
            success = 0;
            
            for (int i = 0; i < limit; i++) {
                BpPattern pattern = pls.get(i);
                
                double[] result = _netWork.runNetWork(pattern.getInputs()); // 神经网络输出层值的集合
                _netWork.trainNetWork(pattern.getOutputs()); // 训练
                int[] truth = BpMath.thresholdArray(threshold, result); // 阀值化输出层值集合
                int[] target = BpMath.thresholdArray(threshold, pattern.getOutputs()); // 阀值化模型目标输出值
                                
                pattern.setTrained(true);
                for (int j = 0; j < truth.length; j++) {
                    if (truth[j] != target[j]) {
                        pattern.setTrained(false);
                    }
                }
                
                if (pattern.isTrained()) {
                    success++;
                }
            }
            
            if (success > maxSuccess) {
                maxSuccess = success;
            }
            
            if ((++counter % 100) == 0) {
                System.out.println((cc++) + " maxSuccess: " + maxSuccess + "   errorRate: " + (double) (limit - success) / limit);
                counter = 0;
            }
//            whileFlag = ((limit - success) / limit <= errorRate || ++count > maxCycle) ? false : true;
            err = (double)(limit - success) / limit;
            if (err <= errorRate) {
                whileFlag = false;
                System.out.println(" is errorRate, err = " + err);
            }
            if (++count > maxCycle) {
                whileFlag = false;
                System.out.println(" is cycle, count = " + count);
            }
        } while(whileFlag);
        
        return maxSuccess;
    }
    
    /**
     * 保存网络
     * @param file 将要保存网络的文件
     * @throws IOException
     * @throws FileNotFoundException
     */
    public void saveNetWork(File file) throws IOException, FileNotFoundException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
        oos.writeObject(_netWork);
        oos.close();
    }
}
