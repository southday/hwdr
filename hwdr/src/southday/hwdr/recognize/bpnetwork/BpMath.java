package southday.hwdr.recognize.bpnetwork;

import java.util.Random;

/**
 * BP神经网络中所用到的一些数学方法支持类，所有方法为静态static
 *
 */
public class BpMath {
    private final static Random _random = new Random();
    /**
     * 用于对输出层进行阀值化，使每个输出节点的值要么是0，要么是1
     */
    public final static double out_threshold = 0.86;
    /**
     * 返回一个指定范围内的（伪）随机数 range = upper - lower
     * @param lower 下界
     * @param upper 上界
     * @return 指定范围的随机数
     */
    public static synchronized double getBoundedRandom(double lower, double upper) {
        if (lower > upper) {
            throw new IllegalArgumentException("参数非法");
        }
        return (_random.nextDouble() * (upper - lower) + lower);
    }
    
    /**
     * @return 一个double类型的随机数
     */
    public static synchronized double getRandomDouble() {
        return _random.nextDouble();
    }
    
    /**
     * 将：<p>模型目标输出值集合</p> 
     * 或者<p>神经网络计算出来的输出层值的集合</p>
     * 阀值化，使二者能进行比较，并且满足最后的输出要求
     * @param threshold 阀值
     * @param result 输出层值集合 或者 模型目标输出值集合
     * @return 经过阀值化后的值集合
     */
    public static synchronized int[] thresholdArray(double threshold, double[] result) {
        int[] thresholdArr = new int[result.length];
        
        for (int i = 0; i < thresholdArr.length; i++) {
            thresholdArr[i] = result[i] > threshold ? 1 : 0;
        }
        return thresholdArr;
    }
}
