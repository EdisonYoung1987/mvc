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
    public TreeNode convertBiNode(TreeNode root) {

    }
}
