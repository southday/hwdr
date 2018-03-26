package southday.hwdr.recognize.bpnetwork;

/**
 * 神经网络中的弧Arc(输入节点/输出节点/权值)
 *
 */
public class Arc extends AbstractArcNode {
    /**
     * Eclipse 自动生成的类序列ID
     */
    private static final long serialVersionUID = 1455760609876116647L;

    /**
     * 该弧的权重weight
     */
    private double _weight;
    
    /**
     * 该弧输入节点InputNode
     */
    private AbstractNode _inNode;
    
    /**
     * 该弧的输出节点OutputNode
     */
    private AbstractNode _outNode;
    
    /**
     * 该弧先前的weight变化量
     */
    private double _delta;
    
    public void setInputNode(AbstractNode inNode) {
        _inNode = inNode;
    }
    
    public void setOutputNode(AbstractNode outNode) {
        _outNode = outNode;
    }
    
    public void setArcWeight(double weight) {
        _weight= weight;
    }
    
    public double getInputNodeValue() {
        return _inNode.getValue();
    }
    
    /**
     * @return _inNode.getValue() * _weidht
     */
    public double getWeightInputNodeValue() {
        return (_inNode.getValue() * _weight);
    }

    /**
     * @return _outNode.getError() * _weight
     */
    public double getWeightOutputNodeError() {
        return (_outNode.getError() * _weight);
    }
    
    /**
     * 根据权重变化量delta来更新weight;
     * 同时也将此次的变化量保存起来，作用于下次对weigth的更新
     * @param delta 权重变化量
     */
    public void updateWeight(double delta) {
        OutputNode on = (OutputNode) _outNode;
        _weight += delta + on.getMomentum() * _delta;
        _delta = delta;
    }
}
