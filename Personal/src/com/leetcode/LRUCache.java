package com.leetcode;

import java.util.Map;
import java.util.HashMap;

class LRUCache {    // Least Recently Used evicted when running out of space.

    private final Map<Integer, Node> cache;
    private final Node head;
    private final Node tail;
    private final int capacity;

    public static class Node {
        int key, value;
        Node previous, next;
        Node(int k, int v) {
            key=k;
            value=v;
        }
    }

    public LRUCache(int capacity) {
        cache = new HashMap<>(capacity);
        head = new Node(Integer.MIN_VALUE, Integer.MIN_VALUE);
        tail = new Node(Integer.MIN_VALUE, Integer.MIN_VALUE);
        head.next=tail;
        tail.previous = head;
        this.capacity = capacity;
    }

    public int get(int key) {
        final Node node = cache.get(key);
        if (node != null) {
            moveToFront(node);
            return node.value;
        }
        return -1;
    }

    public void put(int key, int value) {
        final Node node = cache.get(key);

        if (node != null) {
            node.value=value;
            moveToFront(node);
            return;
        }
        final Node newNode = new Node(key, value);
        cache.put(key, newNode);
        addToFront(newNode);

        if (cache.size() > capacity) {
            final Node lruNode = removeLRU();
            cache.remove(lruNode.key);
        }

    }

    private void moveToFront(final Node node) {
        remove(node);
        addToFront(node);
    }

    private void addToFront(final Node node) {
        final Node first = head.next;
        node.previous = head;
        node.next = first;
        head.next = node;
        first.previous = node;
    }

    private void remove(final Node node) {
        final Node previousNode = node.previous;
        final Node nextNode = node.next;
        previousNode.next = nextNode;
        nextNode.previous = previousNode;
        node.previous = null;
        node.next = null;
        //  "node" should get GC'ed
    }

    private Node removeLRU() {
        final Node lru = tail.previous;
        remove(lru);
        return lru;
    }
}
