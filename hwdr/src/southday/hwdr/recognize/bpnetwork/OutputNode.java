package southday.hwdr.recognize.bpnetwork;

import java.util.Iterator;

/**
 * BP神经网络中输出层的节点类
 *
 */
public class OutputNode extends AbstractNode {

    /**
     * Eclipse 自动生成的类序列号
     */
    private static final long serialVersionUID = -4173103446471422283L;

    /**
     * 学习速率learningRate， 决定者学习效果的好坏；
     * 太大则可能会达不到最优值，太小则在梯度下降时很慢；
     */
    double learningRate;
    
    /**
     * momentum（动量）为了克服产生局部最小值的情况而设置的变量
     */
    double momentum;
    
    /**
     * 构造函数
     * @param learningRate 学习速率
     * @param momentum （动量）为了克服产生局部最小值的情况而设置的变量
     */
    public OutputNode(double learningRate, double momentum) {
        this.learningRate = learningRate;
        this.momentum = momentum;
    }
    
    /**
     * 通过对该节点的所有输入节点value与对应的weight乘积的求和来更新该节点value
     */
    public void runNode() {
        double total = 0.0;
        
        Iterator<Arc> it = inputArcs.iterator();
        while (it.hasNext()) {
            Arc arc = it.next();
            total += arc.getWeightInputNodeValue();
        }
        value = sigmoidTransfer(total);
    }
    
    /**
     * 基于error来更新该节点所有输入弧的weight
     */
    public void trainNode() {
        error = computeError(); // 这里需要重新计算error，是根据输出目标值target来计算的
        
        Iterator<Arc> it = inputArcs.iterator();
        while (it.hasNext()) {
            Arc arc = it.next();
            double delta = learningRate * error * arc.getInputNodeValue();
            arc.updateWeight(delta);
        }
    }
    
    /**
     * 激活函数Sigmoid，在输入和输出神经元间建立非线性关系，返回值范围 (0.0, 1.0)
     * @param value 当前节点的值(输出值)
     * @return 返回值范围 0.0 < result < 1.0
     */
    private double sigmoidTransfer(double value) {
        return (1.0 / (1.0 + Math.exp(-value)));
    }
    
    /**
     * 通过对激活函数Sigmoid求导，计算输出节点的error，用于更新weight
     * @return error
     */
    private double computeError() {
        // 在我看过的资料中，应该是 return value * (1.0 - value) * (target - value); // targer为目标输出值
        // 其实对于输出节点，可以将其error 初始化为 target
        return value * (1.0 - value) * (error - value);
    }
    
    public double getLearningRate() {
        return learningRate;
    }
    
    public double getMomentum() {
        return momentum;
    }
}
