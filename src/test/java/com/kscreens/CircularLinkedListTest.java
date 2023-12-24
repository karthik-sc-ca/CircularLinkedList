package com.kscreens;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CircularLinkedListTest {

    private CircularLinkedList<Integer> circularLinkedList;

    @BeforeEach
    void setUp() {
        circularLinkedList = new CircularLinkedList<>();
        circularLinkedList.add(1);
        circularLinkedList.add(2);
        circularLinkedList.add(3);
    }

    @Test
    void testAdd() {
        assertTrue(circularLinkedList.add(4));
        assertTrue(circularLinkedList.contains(4));
    }

    @Test
    void testRemove() {
        assertTrue(circularLinkedList.remove(2));
        assertFalse(circularLinkedList.contains(2));
    }

    @Test
    void testSize() {
        assertEquals(3, circularLinkedList.size());
    }

    @Test
    void testIsEmpty() {
        assertFalse(circularLinkedList.isEmpty());
        circularLinkedList.clear();
        assertTrue(circularLinkedList.isEmpty());
    }

    @Test
    void testContains() {
        assertTrue(circularLinkedList.contains(3));
        assertFalse(circularLinkedList.contains(4));
    }

    @Test
    void testToArray() {
        Object[] expectedArray = {1, 2, 3};
        Object[] array = circularLinkedList.toArray();
        assertArrayEquals(expectedArray, array);
    }

    @Test
    void testAddAll() {
        List<Integer> list = new ArrayList<>(Arrays.asList(4, 5));
        assertTrue(circularLinkedList.addAll(list));
        assertTrue(circularLinkedList.contains(4));
        assertTrue(circularLinkedList.contains(5));
    }

    @Test
    void testRemoveAll() {
        List<Integer> list = new ArrayList<>(Arrays.asList(2, 3, 4));
        assertTrue(circularLinkedList.removeAll(list));
        assertFalse(circularLinkedList.contains(2));
        assertFalse(circularLinkedList.contains(3));
    }

    @Test
    void testRetainAll() {
        List<Integer> list = new ArrayList<>(Arrays.asList(2, 3, 4));
        assertTrue(circularLinkedList.retainAll(list));
        assertTrue(circularLinkedList.contains(2));
        assertTrue(circularLinkedList.contains(3));
        assertFalse(circularLinkedList.contains(1));
    }

    @Test
    void testClear() {
        circularLinkedList.clear();
        assertTrue(circularLinkedList.isEmpty());
    }
}

