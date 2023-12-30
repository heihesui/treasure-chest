package org.example.segmenttree;

import java.util.Arrays;

/**
 * 范围修改线段树的值
 */
class ModifyIntervalSegmentTree {

    private final Node[] tree;
    private final int[] arr;

    public ModifyIntervalSegmentTree(int[] arr) {
        tree = new Node[arr.length << 2];
        this.arr = Arrays.copyOf(arr, arr.length);
        buildTree(0, arr.length - 1, 0);
    }

    //从数组left->right，每个数据加上val
    public void updateTree(int left, int right, int val) {
        updateTree(left, right, val, 0);
    }

    //查询数组left->right累加和1
    public int queryTree(int left, int right) {
        return queryTree(left, right, 0);
    }

    /**
     * @param left  左区间
     * @param right 有区间
     * @param node  tree数组的下标
     */
    private void buildTree(int left, int right, int node) {
        tree[node] = new Node(left, right);
        if (left == right) {
            tree[node].sum = arr[left];
            return;
        }
        int mid = (left + right) >> 1;
        int leftNode = (node << 1) + 1, rightNode = leftNode + 1;
        buildTree(left, mid, leftNode);
        buildTree(mid + 1, right, rightNode);
        tree[node].sum = (tree[leftNode].sum + tree[rightNode].sum);
    }

    private void updateTree(int left, int right, int val, int node) {
        if (left <= tree[node].l && right >= tree[node].r) {
            tree[node].sum += (tree[node].r - tree[node].l + 1) * val;
            tree[node].tag += val;
            return;
        }
        if (tree[node].tag != 0) pushDown(node);
        int mid = (tree[node].l + tree[node].r) >> 1;
        int leftNode = (node << 1) + 1, rightNode = leftNode + 1;
        if (right <= mid) {
            updateTree(left, right, val, leftNode);
        } else if (left > mid) {
            updateTree(left, right, val, rightNode);
        } else {
            updateTree(left, right, val, leftNode);
            updateTree(left, right, val, rightNode);
        }
        tree[node].sum = (tree[leftNode].sum + tree[rightNode].sum);
    }

    private void pushDown(int node) {
        long val = tree[node].tag;
        int mid = (tree[node].l + tree[node].r) >> 1;
        int leftNode = (node << 1) + 1, rightNode = leftNode + 1;
        tree[leftNode].sum += (mid - tree[node].l + 1) * val;
        tree[rightNode].sum += (tree[node].r - mid) * val;
        tree[leftNode].tag += val;
        tree[rightNode].tag += val;
        tree[node].tag = 0;
    }

    private int queryTree(int left, int right, int node) {
        if (left <= tree[node].l && right >= tree[node].r) return tree[node].sum;
        if (tree[node].tag != 0) pushDown(node);
        int mid = (tree[node].l + tree[node].r) >> 1;
        int leftNode = (node << 1) + 1, rightNode = leftNode + 1;
        if (right <= mid) {
            return queryTree(left, right, leftNode);
        } else if (left > mid) {
            return queryTree(left, right, rightNode);
        }
        return queryTree(left, right, leftNode) + queryTree(left, right, rightNode);
    }

    static class Node {
        int l, r;
        int sum, tag;

        public Node(int l, int r) {
            this.l = l;
            this.r = r;
        }
    }
}
