package data_structure.linkedlist;

/**
 * Created by monster_zzq on 2016/8/7.
 * 单向链表表Java实现
 *
 */
public class MyLinkList {

    /**
     * 头节点
     * 用于保存首个节点信息
     */
    private Node head;

    /**
     * 当前节点 用于保存
     */
    private Node currNode;

    /**
     * 构造器 用于初始化链表的头部信息
     */
    public MyLinkList() {
        head = null;
    }

    private int size;

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    /**
     * 添加
     * 采用的是头插法
     *
     * @param data
     */
    public void insert(int data) {
        //构造当前的
        Node node = new Node();
        node.next = head;
        head = node;
    }


    /**
     * 指定位置插入
     * @param index
     * @param data
     */
    public void insert(int index ,int data){
        if(index > size || index <0){
            return;
        }
        if(index == 0){
            head.data = data;
            return;
        }
        int count = 0;
        Node temp = head;
        while (temp.next != null) {
            count++;
            if (count == index) {
               temp.next.data =data;
            }
            temp = temp.next;
            size++;
        }

    }


    /**
     * 尾插法
     *
     * @param data
     */
    public void insertEnd(int data) {
        if (head == null) {
            Node node = new Node(data);
            head = node;
            currNode = node;
            size++;
            return;
        }
        Node node = new Node(data);
        currNode.next = node;
        currNode = node;
        size++;
    }

    /**
     * 获取某个位置的值
     * @param index
     * @return
     */
    public int get(int index) {
        if (head == null) {
            return -1;
        }
        if (index == 0) {
            return head.data;
        }
        int count = 0;
        Node element = new Node();
        Node temp = head;
        while (temp.next != null) {
            count++;
            if (count == index) {
                element.data = temp.next.data;
            }
            temp = temp.next;
        }
        return element.data;
    }


    /**
     * 删除某个位置元素
     * @param index
     */
    public void del(int index){
        if(index<0 || index> size){
            return;
        }
        if(index == 0){
            head = head.next;
            size--;
            return;
        }
        int count = 0;
        Node element;
        Node temp = head;
        while (temp.next != null) {
            count++;
            if (count == index) {
                element = temp.next.next;
                temp.next = element;
            }
            temp = temp.next;
        }
    }
}

/**
 *
 * 节点类
 * 用于保存节点信息
 *
 */
class Node {
    
    int data;//数据域
    Node next; //指针域

    Node() {
    }

    /**
     * 构造器用于初始化 节点类
     * @param data
     */
    Node(int data) {
        this.data = data;
        next = null;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }


    public static void main(String[] args) {
        MyLinkList myLinkList = new MyLinkList();
        myLinkList.insertEnd(3);
        myLinkList.insertEnd(4);
        myLinkList.insertEnd(63);
        myLinkList.insert(0,5);
       // myLinkList.del(1);
        // myLinkList.insert(5);
        System.out.println(myLinkList.get(0));
        System.out.println(myLinkList.get(1));

    }
}