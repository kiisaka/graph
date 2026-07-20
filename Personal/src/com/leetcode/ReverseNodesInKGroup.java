package com.leetcode;

/**
 * Definition for singly-linked list.
 * public class ReverseNodesInKGroup.ListNode {
 *     int val;
 *     ReverseNodesInKGroup.ListNode next;
 *     ReverseNodesInKGroup.ListNode() {}
 *     ReverseNodesInKGroup.ListNode(int val) { this.val = val; }
 *     ReverseNodesInKGroup.ListNode(int val, ReverseNodesInKGroup.ListNode next) { this.val = val; this.next = next; }
 * }
 */
public class ReverseNodesInKGroup {

    public ListNode reverseKGroup(ListNode head, int k) {

        if (head == null || k <= 1) {
            return head;
        }

        final ListNode dummy = new ListNode(0, head);
        ListNode groupPrev = dummy;

        while (true) {
            // Find the final node in the next group of k nodes.
            ListNode kth = getKthNode(groupPrev, k);
            // Fewer than k nodes remain.
            if (kth == null) {
                break;
            }

            final ListNode groupNext = kth.next;
            /*
             * Reverse the current group.
             *
             * Initializing prev to groupNext automatically connects
             * the end of the reversed group to the remaining list.
             */

            ListNode prev = groupNext;
            ListNode current = groupPrev.next;

            while (current != groupNext) {
                ListNode next = current.next;
                current.next = prev;
                prev = current;
                current = next;
            }

            /*
             * Before reversal:
             * groupPrev -> oldGroupStart -> ... -> kth -> groupNext
             *
             * After reversal:
             * groupPrev -> kth -> ... -> oldGroupStart -> groupNext
             */

            final ListNode oldGroupStart = groupPrev.next;
            groupPrev.next = kth;
            groupPrev = oldGroupStart;
        }

        return dummy.next;
    }

    private ListNode getKthNode(ListNode start, int k) {
        ListNode current = start;
        while (current != null && k > 0) {
            current = current.next;
            k--;
        }

        return current;

    }
}