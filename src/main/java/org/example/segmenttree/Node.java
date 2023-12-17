package org.example.segmenttree;

/**
 * 定义线段树节点
 */
public class Node {
    /**
     * 区间和 或 区间最大、最小值
     */
    int val;
    int left;
    int right;
    int add;

    public Node(int left, int right) {
        this.left = left;
        this.right = right;
    }
}
