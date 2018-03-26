package southday.hwdr.recognize;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * 样品模型集合
 * @author southday
 *
 */
public class PatternList<E extends Pattern> {
    /**
     * 样品模型集合 ArrayList<E> _list
     */
    private ArrayList<E> _list = new ArrayList<E>();
    
    public PatternList() {}
    
    /**
     * 通过文件流读取模型来构造模型集合
     * @param file
     * @throws IOException
     * @throws FileNotFoundException
     * @throws ClassNotFoundException
     */
    public PatternList(File file) 
            throws IOException, FileNotFoundException, ClassNotFoundException {
        reader(file);
    }
    
    /**
     * 添加模型
     * @param pattern
     */
    public void add(E pattern) {
        _list.add(pattern);
    }
    
    /**
     * 获取模型集合的大小
     * @return 模型集合的元素个数
     */
    public int size() {
        return _list.size();
    }
    
    /**
     * 获取模型
     * @param index 索要角标
     * @return 索引指向的模型
     */
    public E get(int index) {
        return _list.get(index);
    }
    
    /**
     * 通过文件流读取模型
     * @param file
     * @throws IOException
     * @throws FileNotFoundException
     * @throws ClassNotFoundException
     */
    @SuppressWarnings("unchecked")
    public void reader(File file) 
            throws IOException, FileNotFoundException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
        _list = (ArrayList<E>) ois.readObject();
        ois.close();
    }
    
    /**
     * 通过文件流写入模型
     * @param file
     * @throws IOException
     * @throws FileNotFoundException
     * @throws ClassNotFoundException
     */
    public void writer(File file) 
            throws IOException, FileNotFoundException, ClassNotFoundException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
        oos.writeObject(_list);
        oos.close();
    }
}
