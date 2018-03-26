package southday.hwdr.recognize.bpnetwork;

/**
 * BP神经网络中输入层的节点类
 *
 */
public class InputNode extends AbstractNode {

    /**
     * Eclipse 自动生成的类序列号
     */
    private static final long serialVersionUID = -9020890049730904050L;

    /**
     * 设置输入节点的值
     */
    public void setValue(double val) {
//        if (val <= 0.0 || val >= 1.0) {
        if (val < 0.0 || val > 4.0) {
            throw new IllegalArgumentException("参数非法");
        }
        value = val;
    }
}
