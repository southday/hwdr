package southday.hwdr.recognize.bpnetwork;

/**
 * 为每一个弧和节点提供唯一的ID
 *
 */
public class SequenceIdGenerator {
    
    /**
     * 定义记录ID的变量，初始值为1
     */
    private static int _id = 1;
    
    /**
     * 每一个弧和节点都应该有一个唯一的ID
     * @return 唯一ID
     */
    public static synchronized int getId() {
        return _id++;
    }
}
