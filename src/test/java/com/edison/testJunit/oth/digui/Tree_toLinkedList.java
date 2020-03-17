package com.edison.testJunit.oth.digui;
/**
 * 二叉树数据结构TreeNode可用来表示单向链表（其中left置空，right为下一个链表节点）。
 * 实现一个方法，把二叉搜索树转换为单向链表，要求值的顺序保持不变，转换操作应是原址的，
 * 也就是在原始的二叉搜索树上直接修改。
 *
 * 返回转换后的单向链表的头节点。
 *
 * 示例：
 *Given
 *          1
 *         / \
 *        2   5
 *       / \   \
 *      3   4   6

 * 　　The flattened tree should look like:
 *    1
 *     \
 *      2
 *       \
 *        3
 *         \
 *          4
 *           \
 *            5
 *             \
 *
 * 从第一层出发 f(A)
 *      左节点处理成一个链表f(AL),返回头结点作为新的头结点，再返回尾节点，将其右子节点连接A,
 *      右节点f(AR),A的右节点连接f(AR)返回的头结点
 *      返回f(AL)的头结点和f(AR)的尾节点*/
public class Tree_toLinkedList {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
    /**返回的左节点作为头结点，右节点为尾节点*/
    public static  TreeNode convertBiNode(TreeNode root) {
        TreeNode res=new TreeNode(-1);
        res.left=res.right=null;//头结点和尾节点
        TreeNode leftNode,rightNode;

        if(root.left==null) {//左节点为空
            res.left=root;
        }else{//左节点不为空
            leftNode=convertBiNode(root.left);
            res.left=leftNode.left; //左边链的头结点作为整个返回的头结点
            leftNode.right.right=root;//左边链的尾节点连接root节点
            res.right=root; //root作为整个返回的尾节点
        }

        if(root.right==null) {
            //不处理
        }else {
            rightNode=convertBiNode(root.left);
            root.right=rightNode.left;//root重新连接右节点链表
            res.right=rightNode.right;
        }

        return res;
    }

    public static void main(String[] args) {
        TreeNode n11=new TreeNode(1);//第一层为5的节点
        TreeNode n22=new TreeNode(2);
        TreeNode n25=new TreeNode(5);
        TreeNode n33=new TreeNode(3);//第三次第一个为1的节点
        TreeNode n34=new TreeNode(4);//第三次第二个为1的节点
        TreeNode n36=new TreeNode(6);

        n11.left=n22;n11.right=n25;
        n22.left=n33;n22.right=n34;
        n25.right=n36;

        TreeNode newTreeNode=convertBiNode(n11);
        while(newTreeNode.right!=null)
            System.out.println(newTreeNode.right.val);
    }
}
