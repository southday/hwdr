package southday.hwdr.recognize.bpnetwork;

import java.util.ArrayList;

/**
 * Node 的抽象父类，定义了不同Node(Input/Hidden/Output)的共有属性和方法
 *
 */
public abstract class AbstractNode extends AbstractArcNode {
    /**
     * Eclipse 自动生成的类序列ID
     */
    private static final long serialVersionUID = 3926512961588761256L;

    /**
     * 该节点的error值，用于更新weight
     */
    double error;
    
    /**
     * 该节点的值value，如果在Output层或者Hidden层则等价于该节点的输出值
     */
    double value;
    
    /**
     * 该节点的输入弧 Input arcs
     */
    ArrayList<Arc> inputArcs = new ArrayList<Arc>();
    
    /**
     * 该节点的输出弧 Output arcs
     */
    ArrayList<Arc> outputArcs = new ArrayList<Arc>();
    
    public double getError() {
        return error;
    }
    
    public void setError(double err) {
        error = err;
    }
    
    /**
     * 对于Hidden层和Output层，value为该节点的输出值；
     * 对于Input层，value为该节点的输入值
     * @return 该节点的值
     */
    public double getValue() {
        return value;
    }
    
    /**
     * 当前节点通过弧(arc)连接另一个节点(destNode)
     * @param destNode 目标节点
     * @param arc 连接弧
     */
    public void connect(AbstractNode destNode, Arc arc) {
        outputArcs.add(arc); // 在该节点的输出弧集合中加入arc
        destNode.inputArcs.add(arc); // 在目标节点的输入弧集合中加入arc
        arc.setInputNode(this); // 设置弧的输入节点为调用对象
        arc.setOutputNode(destNode); // 设置弧的输出的节点为目标节点destNode
    }
}
