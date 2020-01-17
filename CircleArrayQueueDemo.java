package com.queue;

import java.util.Scanner;

public class CircleArrayQueueDemo {
    public static void main(String[] args) {
        System.out.println("测试数组模拟环形队列");

        //创建一个队列对象
        //测试
        CircleArray queue = new CircleArray(3);
        char key = ' '; //接受用户输入
        Scanner scanner = new Scanner(System.in);
        boolean loop = true;
        //输出一个菜单
        while(loop){
            System.out.println("s(show):显示队列");
            System.out.println("a(add):添加数据到队列");
            System.out.println("g(get):从队列中取数据");
            System.out.println("h(head):查看队列头的数据");
            System.out.println("e(exit):推出程序");

            key = scanner.next().charAt(0); //接收一个char类型的字符

            switch(key){
                case 's':
                    queue.showQueue();
                    break;

                case 'a':
                    System.out.println("输出一个数");
                    int value = scanner.nextInt();
                    queue.addQueue(value);
                    break;

                case 'g':
                    try{
                        int res = queue.getQueue();
                        System.out.printf("取出的数据是%d\n",res);
                    } catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;

                case 'h':
                    try{
                        int res = queue.headQueue();
                        System.out.printf("输出队列头的数据是%d\n",res);
                    } catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;

                case 'e':
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

class CircleArray{
    private int maxSize;    //表示数组的最大容量

    //front的含义做一个调整：front指向队列的第一个元素，也就是说arr[front]就是队列的第一个元素
    //front的初始值 = 0
    private int front;

    //rear变量的含义做一个调整：rear指向队列的最后一个元素的后一个位置。因为希望空出一个空间作为约定()
    //rear的初始值 = 0
    private int rear;

    private int arr[];


    public CircleArray(int arrMaxSize){
        maxSize = arrMaxSize;
        arr = new int[maxSize];
    }

    //判断队列是否满
    public boolean isFull(){
        return (rear + 1) % maxSize == front;
    }

    //判断队列为空
    public boolean isEmpty(){
        return rear == front;
    }

    //添加数据到队列
    public void addQueue(int n){
        //判断队列是否满
        if(isFull()){
            throw new RuntimeException("队列满");
        }
        //直接将数据加入
        arr[rear] = n;
        //将rear后移，这里必须考虑取模
        rear = ( rear + 1) % maxSize;
    }

    //获取队列数据，出队列，出队列之后，第二个变为第一个，第一个就被拿走了
    public int getQueue(){
        //判断队列是否为空
        if(isEmpty()){
            //通过抛出异常
            throw new RuntimeException("队列空");
        }
        //这里需要分析出front是指向队列的第一个元素
        //先把front对应的值保存到一个临时的变量，再将front后移(考虑取模)，再将临时保存的变量返回
        int value = arr[front];
        front = (front + 1) % maxSize;
        return value;
    }

    //显示队列的所有数据
    public void showQueue(){
        //遍历
        if(isEmpty()){
            System.out.println("队列是空的");
//            throw new RuntimeException("队列空");
            return;
        }
        for(int i = front; i < front + size(); i++){
            System.out.printf("arr[%d]=%d\n", i % maxSize,arr[i % maxSize]);  //i可能超过maxSize,因此取模
        }
    }

    //求出当前队列有效数据的个数
    public int size(){
        return (rear + maxSize - front) % maxSize;
    }

    //显示队列的头数据，注意不是取数据
    public int headQueue(){
        //判断
        if (isEmpty()){
            throw new RuntimeException("队列空");
        }
        return arr[front];  //front 指向队列的第一个元素
    }
}


    //当队列满时，条件是(rear + 1) % maxSize = front[满]
    //队列为空的条件：rear == front 空
    //队列中有效数据的个数： (rear + maxSize - front) % maxSize