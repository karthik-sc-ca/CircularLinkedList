package com.kscreens;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class CircularLinkedListIteratorTest {

    private CircularLinkedList<Integer> circularLinkedList;

    @BeforeEach
    void setUp() {
        circularLinkedList = new CircularLinkedList<>();
        circularLinkedList.add(1);
        circularLinkedList.add(2);
        circularLinkedList.add(3);
    }

    @Test
    void testIteratorHasNext() {
        Iterator<Integer> iterator = new CircularLinkedListIterator<>(circularLinkedList);
        assertTrue(iterator.hasNext());

        circularLinkedList.clear();
        iterator = new CircularLinkedListIterator<>(circularLinkedList);
        assertFalse(iterator.hasNext());
    }

    @Test
    void testIteratorNext() {
        Iterator<Integer> iterator = new CircularLinkedListIterator<>(circularLinkedList);
        assertEquals(1, iterator.next());
        assertEquals(2, iterator.next());
        assertEquals(3, iterator.next());
        assertNull(iterator.next()); // Beyond the end of the list

        circularLinkedList.clear();
        iterator = new CircularLinkedListIterator<>(circularLinkedList);
        assertNull(iterator.next());
    }

    @Test
    void testIteratorRemove() {
        Iterator<Integer> iterator = new CircularLinkedListIterator<>(circularLinkedList);
        assertThrows(IllegalStateException.class, iterator::remove); // Remove called before next()

        iterator.next(); // Move to the first element

        iterator.remove();
        assertEquals(2, circularLinkedList.size());
        assertFalse(circularLinkedList.contains(1));

        iterator.next(); // Move to the second element
        iterator.remove();
        assertEquals(1, circularLinkedList.size());
        assertFalse(circularLinkedList.contains(2));

        iterator.next(); // Move to the last element
        iterator.remove();
        assertEquals(0, circularLinkedList.size());
        assertFalse(iterator.hasNext());

        assertThrows(IllegalStateException.class, iterator::remove); // Remove called after the end of the list

        // Concurrent modification test
        circularLinkedList.add(4);
        assertThrows(ConcurrentModificationException.class, iterator::next);
    }
    @Test
    void testConcurrentModificationException() {
        CircularLinkedList<Integer> circularLinkedList = new CircularLinkedList<>();
        circularLinkedList.add(1);
        circularLinkedList.add(2);
        circularLinkedList.add(3);

        CircularLinkedListIterator<Integer> iterator = (CircularLinkedListIterator<Integer>) circularLinkedList.iterator();

        // Modifying the list outside the iterator
        circularLinkedList.add(4);

        // Attempting to iterate through the list using the iterator
        assertThrows(ConcurrentModificationException.class, iterator::next);
    }
}
