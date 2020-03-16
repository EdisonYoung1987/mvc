package com.edison.testJunit.oth.digui;
/**
 * 第一只猴子先起来，它把桃分成了相等的五堆，多出一只。于是，它吃掉了一个，拿走了一堆；
 * 第二只猴子起来一看，只有四堆桃。于是把四堆合在一起，分成相等的五堆，又多出一个。于是，它也吃掉了一个，拿走了一堆；
 * ......其他几只猴子也都是 这样分的。
 *
 * 问：这堆桃至少有多少个？
 *
 * 假设N个，N%5==1
 * 第一次剩下 A==N-1-N/5个
 * 第二次剩下 A1==(N-1-N/5) -1 -(N-1-N/5)/5
 * 第三次剩下 A2==A1-1-A1/5
 *
 * 条件： 每次桃子数量%5==1
 *
 * 最终条件：最后一只猴子时返回总数*/
public class FiveMonkeys {
    private static int TOTAL=6; //假设初始值为6，满足第一次条件
    private static int MONKEYS=5;
    private static  int divide(int monkeys,int left){
        System.out.println(monkeys+"  "+left+"  "+TOTAL);
        if(left % 5 != 1 ) {
            TOTAL+=5; //从6开始，因为-1后整除5，所以每次加5
            return divide(MONKEYS,TOTAL);
        }
        if(monkeys==1 )
            return TOTAL;
        return divide(monkeys-1,left-1-left/5);
    }

    public static void main(String[] args) {
        System.out.println(divide(MONKEYS,TOTAL));
    }
}
