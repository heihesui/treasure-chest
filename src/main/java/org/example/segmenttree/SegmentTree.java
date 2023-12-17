package org.example.segmenttree;

public class SegmentTree {
    int[] nums;
    Node[] tree;

    public SegmentTree(int[] nums) {
        this.nums = nums;
        //这里是考虑到建立二叉树的最坏情况
        tree = new Node[nums.length * 4];
        build(1, 1, nums.length);
    }

    /**
     * 建树
     *
     * @param pos   当前节点编号
     * @param left  当前节点区间下限
     * @param right 当前节点区间上限
     */
    private void build(int pos, int left, int right) {
        //创建节点
        tree[pos] = new Node(left, right);
        if (left == right) {
            tree[pos].val = nums[left - 1];
            return;
        }
        int mid = left + right >> 1;
        build(pos << 1, left, mid);
        build(pos << 1 | 1, mid + 1, right);
        pushUp(pos);
    }

    /**
     * 用于向上回溯时修改父节点的值
     *
     * @param pos
     */
    private void pushUp(int pos) {
        tree[pos].val = tree[pos << 1].val + tree[pos << 1 | 1].val;
    }

    /**
     * 修改单节点的值
     *
     * @param pos    当前节点编号
     * @param numPos 需要修改的区间中值的位置
     * @param val    修改后的值
     */
    private void update(int pos, int numPos, int val) {
        //找到该数值所在线段树中的叶子节点
        if (tree[pos].left == numPos && tree[pos].right == numPos) {
            tree[pos].val = val;
            return;
        }
        //如果不是当前节点，那么需要判断是去左或者右节点
        int mid = tree[pos].left + tree[pos].right >> 1;
        if (numPos <= mid) {
            update(pos << 1, numPos, val);
        } else {
            update(pos << 1 | 1, numPos, val);
        }
        //叶子节点的值修改完了，需要回溯更新所有相关父节点的值
        pushUp(pos);
    }

    public int query(int pos, int left, int right) {
        //如果当前节点包含被上下限包含，直接返回
        if (left <= tree[pos].left && right >= tree[pos].right) {
            return tree[pos].val;
        }
        int res = 0;
        int mid = tree[pos].left + tree[pos].right >> 1;
        if (mid <= left) {
            res += query(pos << 1, left, right);
        }
        if (right > mid) {
            res += query(pos << 1 | 1, left, right);
        }
        return res;
    }
}
