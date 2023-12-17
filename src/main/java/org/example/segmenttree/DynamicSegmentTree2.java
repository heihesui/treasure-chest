package org.example.segmenttree;

/**
 * 动态开点线段树
 */
public class DynamicSegmentTree2 {
    DynamicSegmentTree2 left, right;
    int l, r, cnt;

    public DynamicSegmentTree2() {
        l = 1;
        r = (int) 1e9;
    }

    DynamicSegmentTree2(int l, int r) {
        this.l = l;
        this.r = r;
    }

    public void add(int L, int R) { // 为方便区分变量名，将递归中始终不变的入参改为大写（视作常量）
        if (cnt == r - l + 1) return; // 当前节点已被完整覆盖，无需执行任何操作
        if (L <= l && r <= R) { // 当前节点已被区间 [L,R] 完整覆盖，不再继续递归
            cnt = r - l + 1;
            return;
        }
        int mid = (l + r) / 2;
        if (left == null) left = new DynamicSegmentTree2(l, mid); // 动态开点
        if (right == null) right = new DynamicSegmentTree2(mid + 1, r); // 动态开点
        if (L <= mid) left.add(L, R);
        if (mid < R) right.add(L, R);
        cnt = left.cnt + right.cnt;
    }

    public int count() {
        return cnt;
    }
}
