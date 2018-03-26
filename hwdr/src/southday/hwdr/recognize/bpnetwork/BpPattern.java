package southday.hwdr.recognize.bpnetwork;

import java.io.Serializable;

import southday.hwdr.recognize.Pattern;

/**
 * BP神经网络的训练模型
 * @author southday
 *
 */
public class BpPattern implements Pattern, Serializable {
    /**
     * Eclipse 自动生成的类序列号
     */
    private static final long serialVersionUID = 3597380217345812284L;

    /**
     * 模型输入值
     */
    private double[] _input;
    
    /**
     * 模型输出值(目标输出值)
     */
    private double[] _output;
    
    /**
     * 该模型是否经过训练
     */
    private boolean _trained = false;
    
    /**
     * 构造器
     * @param inputs 模型输入值
     * @param outputs 模型的输出值(目标值)
     */
    public BpPattern(double[] inputs, double[] outputs) {
        _input = inputs;
        _output = outputs;
    }
    
    public void setInputs(double[] inputs) {
        _input = inputs;
    }
    
    public double[] getInputs() {
        return _input;
    }
    
    public void setOutputs(double[] outputs) {
        _output = outputs;
    }
    
    public double[] getOutputs() {
        return _output;
    }
    
    public boolean isTrained() {
        return _trained;
    }
    
    public void setTrained(boolean arg) {
        _trained = arg;
    }
    
}
