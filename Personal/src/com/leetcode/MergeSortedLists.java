package com.leetcode;

import java.util.Comparator;
import java.util.PriorityQueue;

public class MergeSortedLists {
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }

        final PriorityQueue<ListNode> minHeap =
                new PriorityQueue<>(Comparator.comparingInt(node -> node.val));

        // Add the first node from each nonempty list.
        for (final ListNode head : lists) {
            if (head != null) {
                minHeap.offer(head);
            }
        }

        final ListNode dummy = new ListNode(0);
        ListNode tail = dummy;

        while (!minHeap.isEmpty()) {
            final ListNode smallest = minHeap.poll();

            tail.next = smallest;
            tail = smallest;

            if (smallest.next != null) {
                minHeap.offer(smallest.next);
            }
        }

        return dummy.next;
    }
}
