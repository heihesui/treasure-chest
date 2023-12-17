package org.example.segmenttree;

/**
 * 范围修改线段树的值
 */
public class ModifyIntervalSegmentTree {

    int[] nums;
    Node[] tree;

    public ModifyIntervalSegmentTree(int[] nums) {
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

    private void pushDown(int pos) {
        if (tree[pos].left == tree[pos].right || tree[pos].add == 0) {
            return;
        }
        int add = tree[pos].add;
        tree[pos << 1].val = add * (tree[pos << 1].right - tree[pos << 1].left + 1);
        tree[pos << 1 | 1].val = add * (tree[pos << 1 | 1].right - tree[pos << 1].left + 1);
        tree[pos].add = 0;
        tree[pos << 1].add += add;
        tree[pos << 1 | 1].add += add;
    }

    /**
     * 修改单节点的值
     *
     * @param pos   当前节点编号
     * @param left  要修改的上线
     * @param right 要修改的下限
     * @param val   修改后的值
     */
    private void update(int pos, int left, int right, int val) {
        if (tree[pos].left <= left && tree[pos].right >= right) {
            tree[pos].val = val * (right - left + 1);
            tree[pos].add = val;
            return;
        }
        pushDown(val);
        int mid = tree[pos].left + tree[pos].right << 1;
        if (mid >= left) {
            update(pos << 1, left, right, val);
        }
        if (mid < right) {
            update(pos << 1 | 1, left, right, val);
        }
        pushUp(pos);
    }

    public int query(int pos, int left, int right) {
        //如果当前节点包含被上下限包含，直接返回
        if (left <= tree[pos].left && right >= tree[pos].right) {
            return tree[pos].val;
        }
        pushDown(pos);
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
