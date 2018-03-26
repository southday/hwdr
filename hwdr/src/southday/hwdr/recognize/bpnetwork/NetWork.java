package southday.hwdr.recognize.bpnetwork;

import java.io.Serializable;

/**
 * BP神经网络的网络模型
 *
 */
public class NetWork implements Serializable {

    /**
     * Eclipse 自动生成的类序列号
     */
    private static final long serialVersionUID = -2473072398826618195L;
    
    /**
     * 网络中弧的集合
     */
    private final Arc[] _arcs;
    
    /**
     * 网络中输入层节点的集合
     */
    private final InputNode[] _inputs;
    
    /**
     * 网络中隐藏层节点的集合
     */
    private final HiddenNode[] _hiddens;
    
    /**
     * 网络中输出层节点的集合
     */
    private final OutputNode[] _outputs;
    
    /**
     * 构造3层BP神经网络模型，创建并连接各 弧 和 节点
     * @param inputNum 输入层节点的数目
     * @param hiddenNum 隐藏层节点的数目
     * @param outputNum 输出层节点的数目
     * @param learningRate 学习速率
     * @param momentum 权值变化动量
     */
    public NetWork(int inputNum, int hiddenNum, int outputNum, double learningRate, double momentum) {
        _inputs = new InputNode[inputNum];
        for (int i = 0; i < _inputs.length; i++) {
            _inputs[i] = new InputNode();
        }
        
        _hiddens = new HiddenNode[hiddenNum];
        for (int i = 0; i < _hiddens.length; i++) {
            _hiddens[i] = new HiddenNode(learningRate, momentum);
        }
        
        _outputs = new OutputNode[outputNum];
        for (int i = 0; i < _outputs.length; i++) {
            _outputs[i] = new OutputNode(learningRate, momentum);
        }
        
        _arcs = new Arc[(inputNum * hiddenNum) + (hiddenNum * outputNum)];
        for (int i = 0; i < _arcs.length; i++) {
            _arcs[i] = new Arc();
            _arcs[i].setArcWeight(BpMath.getRandomDouble()); // 初始化权重(随机值)
        }
        
        int arc_index = 0;
        for (int i = 0; i < _inputs.length; i++) { // 连接输入层节点和隐藏层节点
            for (int j = 0; j < _hiddens.length; j++) {
                _inputs[i].connect(_hiddens[j], _arcs[arc_index++]);
            }
        }
        
        for (int i = 0; i < _hiddens.length; i++) { // 连接隐藏层节点和输出层节点
            for (int j = 0; j < _outputs.length; j++) {
                _hiddens[i].connect(_outputs[j], _arcs[arc_index++]);
            }
        }
    }
    
    public InputNode[] getInputNodes() {
        return _inputs;
    }
    
    public HiddenNode[] getHiddenNode() {
        return _hiddens;
    }
    
    public OutputNode[] getOutputNode() {
        return _outputs;
    }
    
    public Arc[] getArcs() {
        return _arcs;
    }
    
    /**
     * 输入值经过网络运行得到输出结果
     * @param input 输入层节点的值
     * @return 输出层节点的值
     */
    public double[] runNetWork(double[] input) {
        for (int i = 0; i < _inputs.length; i++) {
            _inputs[i].setValue(input[i]);
//            System.out.println("_inputs[" + i + "] = " + _inputs[i].getValue());
        }
        
        for (int i = 0; i < _hiddens.length; i++) {
            _hiddens[i].runNode();
//            System.out.println("_hiddens[" + i + "] = " + _hiddens[i].getValue());
        }
        
        for (int i = 0; i < _outputs.length; i++) {
            _outputs[i].runNode();
//            System.out.println("_outputs[" + i + "] = " + _outputs[i].getValue());
        }
        
        double[] result = new double[_outputs.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = _outputs[i].getValue();
        }
        
        return result;
    }
    
    /**
     * 根据目标值训练网络;
     * 外部调用trainNetWork()，返回result，如果还不满足结束条件，则可以再次调该函数进行训练
     * @param target 输出层节点的目标值
     * @return 输出层节点的值
     */
    public void trainNetWork(double[] target) { // return value : double[] --> void
        for (int i = 0; i < _outputs.length; i++) {
            _outputs[i].setError(target[i]);
        }
        
        for (int i = 0; i < _outputs.length; i++) {
            _outputs[i].trainNode();
        }
        
        for (int i = 0; i < _hiddens.length; i++) {
            _hiddens[i].trainNode();
        }
    }
}
