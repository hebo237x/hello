package data_structure.list;

/**
 * Created by monster_zzq on 2016/9/7.
 * 线性表数组类
 */
public class Vetor {

    // size 代表顺序表的容量 length代表顺序表的元素个数
    int size , length;

    private int[] data;

    //构造器
    public Vetor(int input_size){
        size = input_size;
        length = 0;
        data  = new int[size];
    }

    /**
     * 插入操作
     * @param loc 插入的位置
     * @param value 插入的值
     */
    public void insert(int loc , int value){
        //先判断loc
        if ( loc < 0 || loc > length ){
            return ;
        }
        if ( length >= size ){
            expand();
        }
        //将大于loc位置的元素向后移动一位
        for(int i = length ; i > loc ; i--){
            data[i] = data[i-1];
        }
        data[loc] = value;
        length++;
    }

    /**
     * 删除数组
     * @param index
     */
    public void remove ( int index ){
        if( index < 0 || index >= length ){
            return;
        }
        //将待删除的元素下标的后面元素移动到前面
        //
        for (int i = index + 1; i < length; ++i) {
            data[i - 1] = data[i];
        }
        //更新数组长度
        length = length - 1;
    }
    /**
     *
     * @param index
     * @return
     */
    public int  serachValue(int index){
        for ( int i = 0 ; i < length ; i++){
            if(i == index){
                return data[i];
            }
        }
        return -1;
    }

    /**
     * 扩容
     */
    void expand(){
        int[] old_data = data;
        size = size*2;
        data = new int[size];
        for (int i=0 ; i< length;i++){
            data[i] = old_data[i];
        }
    }

    void  print(){
        for(int i= 0 ;i < length ; i++){
            System.out.print(data[i] +" ");
        }
    }


    public static void main(String[] args) {
        Vetor vetor = new Vetor(10);
        vetor.insert(0,1);
        vetor.insert(1,2);
        System.out.println(vetor.length);
        for (int i = 0 ;i<vetor.length; i++){
            System.out.print(vetor.data[i]);
        }
    }

}
