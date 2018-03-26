package southday.hwdr.recognize.bpnetwork;

import java.util.Iterator;

/**
 * BP神经网络中隐藏层的节点类
 *
 */
public class HiddenNode extends OutputNode {

    /**
     * 
     */
    private static final long serialVersionUID = 3770512962052485662L;

    /**
     * 构造函数
     * @param learningRate 学习速率
     * @param momentum （动量）为了克服产生局部最小值的情况而设置的变量
     */
    public HiddenNode(double learningRate, double momentum) {
        super(learningRate, momentum);
        // TODO Auto-generated constructor stub
    }
    
    /**
     * 通过对激活函数Sigmoid求导，计算输出节点的error，用于更新weight
     * @return error
     */
    private double computeError() {
        double total = 0.0;
        
        Iterator<Arc> it = outputArcs.iterator();
        while (it.hasNext()) {
            Arc arc = it.next();
            total += arc.getWeightOutputNodeError();
        }
        return (value * (1.0 - value) * total);
    }
    
    // 其实和父类的是一样的，根本不用复写吧，只不过调用父类的trainNode()时，调用里面的computeError()时是父类的还是子类的
    @Override 
    public void trainNode() {
        error = computeError();
        
        Iterator<Arc> it = inputArcs.iterator();
        while (it.hasNext()) {
            Arc arc = it.next();
            double delta = learningRate * error * arc.getInputNodeValue();
            arc.updateWeight(delta);
        }
    }
}
