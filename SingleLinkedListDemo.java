package com.linkedlist;

public class SingleLinkedListDemo {
    public static void main(String[] args) {
        //测试
        //创建节点
        HeroNode hero1 = new HeroNode(1,"宋江","及时雨");
        HeroNode hero2 = new HeroNode(2,"卢俊义","玉麒麟");
        HeroNode hero3 = new HeroNode(3,"吴用","智多星");
        HeroNode hero4 = new HeroNode(4,"林冲","豹子头");

        //创建要给的链表
        SingleLinkedList singleLinkedList = new SingleLinkedList();

//        //加入
//        singleLinkedList.add(hero1);
//        singleLinkedList.add(hero2);
//        singleLinkedList.add(hero3);
//        singleLinkedList.add(hero4);
//        /*
//        若是加入重复的数据，如hero1,则会造成死循环
//        原因：hero1已经指向了hero2
//         */

        singleLinkedList.addByOrder(hero3);
        singleLinkedList.addByOrder(hero1);
        singleLinkedList.addByOrder(hero4);
        singleLinkedList.addByOrder(hero2);
        singleLinkedList.addByOrder(hero3);

        singleLinkedList.list();
        System.out.println();

        //测试修改节点的代码
        HeroNode newHeroNode = new HeroNode(2,"小卢","玉麒麟。。");
        singleLinkedList.update(newHeroNode);

        //显示一把
        singleLinkedList.list();
        System.out.println();

        //删除一个节点
        singleLinkedList.del(1);
        singleLinkedList.del(2);
        singleLinkedList.del(3);
        singleLinkedList.del(4);

        singleLinkedList.list();

        //查找一个节点
        singleLinkedList.addByOrder(hero1);
        singleLinkedList.find(1);
        singleLinkedList.find(5);

    }
}


//定义SingleLinkedList 管理我们的英雄
class SingleLinkedList{
    //初始化一个头节点，头节点不要动
    private HeroNode head = new HeroNode(0, "", "");

    //添加单向链表
    //当不考虑编号的顺序时
    // 1.找到当前链表的最后节点
    // 2.将最后的这个节点的next 指向新的节点
    public void add(HeroNode heroNode){
        //因为head节点不能动，因此我们需要一个辅助遍历 temp
        HeroNode temp = head;
        //遍历链表，找到最后
        while (true){
            //找到最后
            if (temp.next == null){
                break;
            }
            //没有找到最后,temp后移,查找哪个才是最后链表的最后一个
            temp = temp.next;
        }
        //当退出while循环时，temp就指向了链表的最后
        //将最后这个节点的next 指向新的节点
        temp.next = heroNode;
    }

    //第二种添加英雄的方式：在添加英雄时，根据排名将英雄插入到指定的位置（如果已经有这个排名，则添加失败并给出提示）
    public void addByOrder(HeroNode heroNode){
        //因为头节点不能动，因此我们仍然通过一个辅助指针（变量）来帮助找到添加的位置
        //因为单链表，我们找到的temp是位于添加位置的前一个结点，否则插入不了
        HeroNode temp = head;
        boolean flag = false;   //flag作为一个标识来验证该编号的英雄是否存在，默认为false
        while (true){
            if (temp.next == null){ //说明temp已经在链表的最后
                break;
            }
            if (temp.next.no > heroNode.no){    //位置找到，就在temp的后面插入
                break;
            }else if (temp.next.no == heroNode.no){
                flag = true;    //说明编号存在
                break;
            }
            temp = temp.next;   //后移，遍历当前链表
        }
        //判断当前flag的值
        if (flag){
            System.out.println("准备插入的英雄编号为"+heroNode.no+"已经存在了，不能加入");
        }else {
            //插入到链表中，temp的后面
            heroNode.next = temp.next;
            temp.next = heroNode;
        }
    }


    //查找节点
    public void find(int no){
        HeroNode temp = head;
        while (true){
            if (temp.next == null){
                System.out.println("没有找到");
                break;
            }else if (temp.next.no == no){
                System.out.println("找到了");
                break;
            }
            temp = temp.next;
        }
    }


    //修改节点的信息，根据no编号来修改，即no编号不能改
    //说明
    //1.根据newHeroNode 的 no 来修改即可
    public void update(HeroNode newHeroNode){
        //判断是否为空
        if (head.next == null){
            System.out.println("链表为空");
            return;
        }
        //找到一个修改的节点，即根据no编号
        //定义一个辅助变量
        HeroNode temp = head.next;
        boolean flag = false;   //表示是否找到该节点
        while (true){
            if (temp == null){  //链表已经遍历完
                break;
            }
            if (temp.no == newHeroNode.no){
                //找到了
                flag = true;
                break;
            }
            temp = temp.next;
        }
        //根据flag判断是否找到需要修改的节点
        if (flag){
            temp.name = newHeroNode.name;
            temp.nickname = newHeroNode.nickname;
        }else {
            //没有找到
            System.out.println("没有找到编号为"+newHeroNode.no+"的节点，不能修改");
        }
    }


    /*
        删除链表中一个节点的思路：
        1. 我们先找到需要删除的这个节点的前一个节点temp
        2. temp.next = temp.next.next
        3. 被删除的节点，将不会有其他引用指向，将会被垃圾回收机制回收
     */

    //删除节点
    //思路：
    //1. head节点不能动，因此需要一个temp辅助节点来找到待删除节点的前一个节点
    //2. 说明我们在比较时，是temp.next.no 和 需要删除的no比较
    public void del(int no){
        HeroNode temp = head;
        boolean flag = false;   //标志是否找到待删除的节点
        while (true){
            if (temp.next == null){ //已经到达链表的最后
                break;
            }
            if (temp.next.no == no){
                //找到了待删除节点的前一个节点
                flag = true;
                break;
            }
            temp = temp.next;   //temp后移实现遍历
        }
        if (flag){
            //找到，可以删除了
            temp.next = temp.next.next;
        }else {
            System.out.println("要删除的"+no+"不存在");
        }
    }


    //显示链表(遍历)
    public void list(){
        //判断链表是否为空
        if (head.next == null){
            System.out.println("链表为空");
            return;
        }
        //因为头节点不能动，因此我们需要一个辅助变量来遍历
        HeroNode temp = head.next;
        while (true){
            //判断是否到达链表的最后
            if (temp == null){
                break;
            }
            //输出节点信息
            System.out.println(temp);
            //一定要将temp后移，不然造成死循环，一定小心
            temp = temp.next;
        }
    }
}


//定义HeroNode,每个HeroNode 对象就是一个节点
class HeroNode{
    public int no;
    public String name; //英雄的名字
    public String nickname; //英雄的昵称
    public HeroNode next;   //指向下一个节点

    //构造器
    public HeroNode(int no, String name, String nickname){
        this.no = no;
        this.name = name;
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + no +
                ", name='" + name + "'" +
                ", nickname='" + nickname + "'" +
                '}';
    }
}