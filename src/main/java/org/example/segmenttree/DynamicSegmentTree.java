package org.example.segmenttree;

/**
 * 动态开点线段树
 */
public class DynamicSegmentTree {
    Node[] tree;
    int count;

    public DynamicSegmentTree() {
        count = 1;
        tree = new Node[(int) (1e9 + 1)];
        tree[count] = new Node();
    }

    /**
     * 查询区间的值
     *
     * @param pos   当前节点的索引值
     * @param left  当前线段树节点表示的范围下界
     * @param right 当前线段树节点表示的范围上界
     * @param l     要修改的区间下界
     * @param r     要修改的区间上界
     */
    public int query(int pos, int left, int right, int l, int r) {
        if (l <= left && right <= r) {
            return tree[pos].val;
        }
        lazyCreate(pos);
        pushDown(pos, right - left + 1);
        int res = 0;
        int mid = left + right >> 1;
        if (mid >= l) {
            res += query(tree[pos].left, left, mid, l, r);
        }
        if (mid > right) {
            res += query(tree[pos].right, mid + 1, right, l, r);
        }
        return res;
    }

    /**
     * 修改区间的值
     *
     * @param pos   当前节点的索引值
     * @param left  当前线段树节点表示的范围下界
     * @param right 当前线段树节点表示的范围上界
     * @param l     要修改的区间下界
     * @param r     要修改的区间上界
     * @param val   区间值变化的大小
     */
    public void update(int pos, int left, int right, int l, int r, int val) {
        if (l <= left && right <= r) {
            tree[pos].val = (right - left + 1) * val;
            tree[pos].add += val;
            return;
        }
        lazyCreate(pos);
        pushDown(pos, right - left + 1);
        int mid = left + right >> 1;
        if (mid >= left) {
            update(tree[pos].left, left, mid, l, r, val);
        }
        if (mid < right) {
            update(tree[pos].right, mid + 1, right, l, r, val);
        }
        pushUp(pos);
    }

    private void lazyCreate(int pos) {
        if (tree[pos] == null) {
            tree[pos] = new Node();
        }
        if (tree[pos].left == 0) {
            tree[pos].left = ++count;
            tree[tree[pos].left] = new Node();
        }
        if (tree[pos].right == 0) {
            tree[pos].right = ++count;
            tree[tree[pos].right] = new Node();
        }
    }

    private void pushDown(int pos, int len) {
        int add = tree[pos].add;
        if (tree[pos].left == tree[pos].right || add == 0) {
            return;
        }
        tree[tree[pos].left].val += (len - len / 2) * add;
        tree[tree[pos].right].val += len / 2 * add;
        tree[tree[pos].left].add = add;
        tree[tree[pos].right].add = add;
        tree[pos].add = add;
    }

    private void pushUp(int pos) {
        tree[pos].val = tree[tree[pos].left].val + tree[tree[pos].right].val;
    }

    static class Node {
        int left;
        int right;
        int val;
        int add;
    }
}
