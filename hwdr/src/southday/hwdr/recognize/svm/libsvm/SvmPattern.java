package southday.hwdr.recognize.svm.libsvm;

import southday.hwdr.recognize.Pattern;

/**
 * 经过特征和的样本表示，基于LibSVM样本格式：
 * <p>
 * svm_feature_sample = label index:value index:value ...
 * </p>
 * <p>
 * svm_node = index:value
 * </p>
 * @author southday
 *
 */
public class SvmPattern implements Pattern {
    /**
     * 样本类别标签
     */
    public int label;
    /**
     * i:第i个特征的值 --> index_i:value_i
     */
    public svm_node[] svm_nodes;
}
