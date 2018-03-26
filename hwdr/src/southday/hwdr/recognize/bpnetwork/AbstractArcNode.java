package southday.hwdr.recognize.bpnetwork;

import java.io.Serializable;

/**
 * Arc 和 Node 的抽象父类，定义了Arc和Node的共性方法与属性
 * @author southday
 *
 */
public abstract class AbstractArcNode implements Serializable {

    /**
     * Eclipse 自动生成的类序列ID
     */
    private static final long serialVersionUID = 3521432475494353360L;
    
    /**
     * Arc 或 Node 的ID，由SequenceIdGenerator生成
     */
    final int id = SequenceIdGenerator.getId();
    
    /**
     * 每一个弧和节点都有唯一一个对应的全局ID
     * @return Arc 或 Node 的ID
     */
    public int getId() {
        return id;
    }
}
