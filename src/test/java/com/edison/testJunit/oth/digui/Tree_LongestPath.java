package com.edison.testJunit.oth.digui;
/**
 * 给定一个二叉树，找到最长的路径，这个路径中的每个节点具有相同值。 这条路径可以经过也可以不经过根节点。
 * 注意：两个节点之间的路径长度由它们之间的边数表示。
 * 示例 1:
 * 输入:
 *               5
 *              / \
 *             4   5
 *            / \   \
 *           1   1   5
 * 输出:2
 * 示例 2:
 * 输入:
 *               1
 *              / \
 *             4   5
 *            / \   \
 *           4   4   5
 * 输出:
 *
 * 2
 * 注意: 给定的二叉树不超过10000个结点。 树的高度不超过1000。
 *
 * 分析递归内容：
 * 节点A, f(A)
 *     如果AL(左节点)==A ,Anum(数量)=Anum+1+f(AL)
 *     如果AR(右节点)==A,Anum(数量)=Anum+1+f(AR)
 *     都不满足，则比较Anum和全局变量的大小LONGEST,然后返回0
 *     */
public class Tree_LongestPath {
    private static int LONGEST=1;//最长路径
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public static  int longestUnivaluePath(TreeNode root) {
        int num=0;
        if(root.left!=null && root.val==root.left.val)
            num=1+longestUnivaluePath(root.left);

        if(root.right!=null && root.val==root.right.val)
            num=num+1+longestUnivaluePath(root.right);
        if(num>LONGEST)
            LONGEST=num;
        return num;
    }

    public static void main(String[] args) {
        /***             5
         *              / \
         *             4   5
         *            / \   \
         *           1   1   5*/
        TreeNode n15=new TreeNode(5);//第一层为5的节点
        TreeNode n24=new TreeNode(4);
        TreeNode n25=new TreeNode(5);
        TreeNode n31=new TreeNode(1);//第三次第一个为1的节点
        TreeNode n312=new TreeNode(1);//第三次第二个为1的节点
        TreeNode n35=new TreeNode(5);

        n15.left=n24;n15.right=n25;
        n24.left=n31;n24.right=n312;
        n25.left=n35;

        longestUnivaluePath(n15);
        System.out.println(LONGEST); //2

        /***             5
         *              / \
         *             5   5
         *            / \   \
         *           1   5   5*/
        TreeNode n151=new TreeNode(5);//第一层为5的节点
        TreeNode n241=new TreeNode(5);
        TreeNode n251=new TreeNode(5);
        TreeNode n311=new TreeNode(1);//第三次第一个为1的节点
        TreeNode n3121=new TreeNode(5);//第三次第二个为1的节点
        TreeNode n351=new TreeNode(5);

        n151.left=n241;n151.right=n251;
        n241.left=n311;n241.right=n3121;
        n251.left=n351;

        longestUnivaluePath(n151);
        System.out.println(LONGEST); //2
    }
}
