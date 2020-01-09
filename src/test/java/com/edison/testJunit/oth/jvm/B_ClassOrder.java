package com.edison.testJunit.oth.jvm;

/**类启动顺序
 * **类的生命周期：加载->验证->准备->解析->类初始化->(new创建)使用->卸载；
 *  * 其中准备阶段是静态变量赋值默认值(0或null，final除外)
 *  * 类初始化时按照静态变量或静态代码块的顺序处理的，init()方法
 *  * 赋值顺序：父类静态变量赋值->自身静态变量赋值->父类成员变量赋值和父类块赋值
 *  * ->父类构造函数->自身成员变量赋值和块赋值->自身构造函数；
 *  * 但是这里，因为有个引用自身的类静态变量赋值为自身对象，所以类对象的创建
 *  * 反而提到了其他类静态变量初始化前面，所以实例初始化不一定要在类初始化结束后才开始*/
public class B_ClassOrder {
    public static void main(String[] args){
        staticFunction();
    }
    //准备阶段 st=null ，类初始化阶段第一个执行
    static B_ClassOrder st = new B_ClassOrder();//这里，将对象初始化提到了类初始化之前！！！

    static{//类初始化阶段 第二个执行
        System.out.println("1");
    }

    {//对象初始化阶段第一个
        System.out.println("2");
    }

    B_ClassOrder(){ //对象初始化阶段，第三个
        System.out.println("3");
        System.out.println("a="+a+",b="+b);
    }

    public static void staticFunction(){
        System.out.println("4");
    }

    int a=110; //对象始化前执行，第二个；因为不是静态变量，所以准备阶段是不管的
    static int b =112; //准备阶段为b=0，类初始化后b=112 ，第三个执行.除非final修饰的才会在准备阶段就是指定值
}
