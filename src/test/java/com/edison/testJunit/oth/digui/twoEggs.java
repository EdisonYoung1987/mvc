package com.edison.testJunit.oth.digui;

/***存在某T层高楼，在该高楼中存在这样的一层，在该层之下的所有楼层扔鸡蛋，鸡蛋摔到地上都不会碎，还可以继续扔；在该层及该层之上的所有楼层，
 * 扔下鸡蛋都会摔碎。
 目前手里有两个鸡蛋，怎样扔才能在最坏情况下，使得扔的次数最小。*/
public class twoEggs {
    static int T=100;//假设100层
    static int M=2; //假设两个鸡蛋
    public static void main(String[] args) {
        System.out.println(test(T,M));

    }

    /**layers-层数
     * eggs-鸡蛋数量*/
    static int test(int layers, int eggs){
        System.out.println(layers+" "+eggs);
        int res= layers;
        if(layers ==1)
            return 1;
        if(eggs ==1)
            return layers;//只有一个鸡蛋的时候肯定只能一层一层的试探
        for(int k = 1; k< layers; k++) {
            int i = test(k, eggs -1);//碎了
            int j = test(layers -k, eggs);//没碎

            res=Math.min(res,Math.max(i,j)+1);
        }

        return res;
    }
}
