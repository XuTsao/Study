package com.queue;
/*
    1.队列是一个有序数组，可以用数组或者链表来实现
    2.遵循 先入先出 原则，即：先进入队列的数据，要先取出。后存入的要后取出
 */

import java.util.Scanner;

public class ArrayQueueDemo {
    public static void main(String[] args) {
        //测试
        //创建一个队列对象
        ArrayQueue queue = new ArrayQueue(3);   //创建数组队列queue 长度为3
        char key = ' '; //接受用户输入
        Scanner scanner = new Scanner(System.in);
        boolean loop = true;    //让菜单一直输出

        //输出一个菜单
        while(loop){
            System.out.println("s(show):显示队列");
            System.out.println("a(add):添加数据到队列");
            System.out.println("g(get):从队列中取数据");
            System.out.println("h(head):查看队列头的数据");
            System.out.println("e(exit):推出程序");

            key = scanner.next().charAt(0); //接收一个char类型的字符

            switch(key){
                case 's':   //s(show)
                    queue.showQueue();
                    break;

                case 'a':   //a(add)
                    System.out.println("输出一个数");
                    int value = scanner.nextInt();
                    queue.addQueue(value);
                    break;

                case 'g':   //g(get)
                    try{
                        int res = queue.getQueue();
                        System.out.printf("取出的数据是%d\n",res);
                    } catch (Exception e){
                        System.out.println(e.getMessage()); //e.getMessage():获得错误信息
                        System.out.println(e.toString());   //e.toString()：  获得异常种类和错误信息
                    }
                    break;

                case 'h':   //h(head)
                    try{
                        int res = queue.headQueue();
                        System.out.printf("输出队列头的数据是%d\n",res);
                    } catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;

                case 'e':   //e(exit)
                    scanner.close();
                    loop = false;
                    break;

                default:
                    break;
            }
        }
        System.out.println("程序推出");
    }
}

//使用数组模拟队列--编写一个ArrayQueue类
class ArrayQueue{
    private int maxSize;    //表示数组的最大容量
    private int front;  //队列头
    private int rear;   //队列尾
    private int[] arr;  //该数组用于存放数据模拟的队列

    //创建队列的一个构造器
    public ArrayQueue(int arrMaxSize){
        maxSize = arrMaxSize;
        arr = new int[maxSize];
        front = -1; //指向队列头部，分析出front是指向队列头的前一个位置
        rear = -1;  //指向队列尾部，指向队尾的数据（即：就是队列的最后一个数据）
        //front = -1 --> front先指向数组的第一个元素arr[0]的前一个，即，当front = 0 时对应的是a[0]
        //一开始队列为空，所以令rear = front = -1
    }

    //判断队列是否满
    public boolean isFull(){
        return rear == maxSize - 1;
    }

    //判断队列为空
    public boolean isEmpty(){
        return rear == front;
    }

    //添加数据到队列   a(add)
    public void addQueue(int n){
        //判断队列是否满
        if(isFull()){
            System.out.println("队列满");
//            throw new RuntimeException("队列满");
            return;     //这是不返回值,而是结束这个方法
        }
        rear++;     //若队列没有满，往里加数据，队列尾往后去一位
        arr[rear] = n;  //将数据存入数组
    }

    //获取队列数据，出队列，出队列之后，第二个变为第一个，第一个就被拿走了    g(get)
    public int getQueue(){
        //判断队列是否为空
        if(isEmpty()){
            //通过抛出异常
            throw new RuntimeException("队列空");  //因为此方法的返回值为int,所以无法通过return来结束方法，只有通过抛出异常来结束方法
        }
        front++;    //front后移(一开始front的值为-1,所以先让front++)
        return arr[front];
    }

    //显示队列的所有数据
    public void showQueue(){
        //遍历
        if(isEmpty()){
            System.out.println("队列是空的");
//            throw new RuntimeException("队列空");
            return;
        }
        for(int i = 0; i < arr.length; i++){
            System.out.printf("arr[%d]=%d\n",i,arr[i]);
        }
    }

    //显示队列的头数据，注意不是取数据
    public int headQueue(){
        //判断
        if (isEmpty()){
            throw new RuntimeException("队列空");
        }
        return arr[front+1];
    }
}

/*  问题分析
    1.目前数组使用一次后就不能使用，没有达到复用的效果
    2.将这个数组使用的算法，改进成一个环形的队列 用取模的方法来实现
 */