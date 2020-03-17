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
 *          5
 *         / \
 *        3   6
 *       / \   \
 *      2   4   8

 * 　　The flattened tree should look like:
 *    2
 *     \
 *      3
 *       \
 *        4
 *         \
 *          5
 *           \
 *            6
 *             \
 *              8
 * 从第一层出发 f(A)
 *      左节点处理成一个链表f(AL),返回头结点作为新的头结点，再返回尾节点，将其右子节点连接A,
 *      右节点f(AR),A的右节点连接f(AR)返回的头结点
 *      返回f(AL)的头结点和f(AR)的尾节点
 *
 * 左节点处理完成后要清除。*/
public class Tree_toLinkedList {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
    /**返回的节点的左子节点作为头结点，右子节点为尾节点;
     * 可以认为返回的是两个指针，一个指向链表头，一个指向链表尾*/
    public static  TreeNode convertBiNode(TreeNode root) {
        TreeNode first=new TreeNode(-1);//头尾指针
        TreeNode last=new TreeNode(-1);
        TreeNode result=new TreeNode(-1);

        if(root.left==null) {//左节点为空
            first=root;
        }else{//左节点不为空
            TreeNode leftNode=convertBiNode(root.left);

            root.left=null; //处理完成后需要清理左节点
            first=leftNode.left; //左边链的头结点作为整个返回的头结点
            leftNode.right.right=root;//左边链的尾节点连接root节点
            last=root; //root作为整个返回的尾节点
        }
//        System.out.println(first.val+" "+last.val);

        if(root.right==null) {
            last=root;
        }else {
            TreeNode  rightNode=convertBiNode(root.right);
            last.right=rightNode.left;//root重新连接右节点链表
            last=rightNode.right;
        }

        result.left=first;
        result.right=last;
        return result;
    }

    public static void main(String[] args) {
        TreeNode n11=new TreeNode(5);//第一层为5的节点
        TreeNode n22=new TreeNode(3);
        TreeNode n25=new TreeNode(6);
        TreeNode n33=new TreeNode(2);//第三次第一个为1的节点
        TreeNode n34=new TreeNode(4);//第三次第二个为1的节点
        TreeNode n36=new TreeNode(8);

        n11.left=n22;n11.right=n25;
        n22.left=n33;n22.right=n34;
        n25.right=n36;

        TreeNode newTreeNode=convertBiNode(n11);
        TreeNode temp=newTreeNode.left;

        while(temp!=null){
            System.out.println(temp.val);
            temp=temp.right;
        }
    }
}
