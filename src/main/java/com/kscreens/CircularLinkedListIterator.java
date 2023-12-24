package com.kscreens;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class CircularLinkedListIterator<T> implements Iterator<T>
{
    CircularLinkedList<T> cll;
    CircularLinkedList<T>.Node iterHead;
    private long expectedModCount;
    boolean hasIterated = false;
    boolean hasCalledNext = false;

    @Override
    public boolean hasNext()
    {
        if (cll.head == null)
        {
            return false;
        }

        return !hasIterated;

    }

    @Override
    public T next()
    {
        if (cll.modCount != expectedModCount)
        {
            throw new ConcurrentModificationException("CircularLinkedList has been modified");
        }

        if (iterHead == null)
        {
            return null;
        }
        if (hasIterated)
        {
            return null;
        }

        T item = iterHead.item;

        iterHead = iterHead.next;

        hasCalledNext = true;

        if (iterHead == cll.head)
        {
            hasIterated = true;
        }

        return item;
    }

    @Override
    public void remove()
    {
        if (!hasCalledNext)
        {
            throw new IllegalStateException("remove() called before next()");
        }

        if (cll.modCount != expectedModCount) {
            throw new ConcurrentModificationException("CircularLinkedList has been modified");
        }

        if (iterHead == null) {
            throw new IllegalStateException("Iterator has reached the end");
        }

        if (cll.head == null) {
            throw new IllegalStateException("Circular Linked List is empty");
        }


        CircularLinkedList<T>.Node cur = cll.head;
        CircularLinkedList<T>.Node prev = null;

        while (cur.next != iterHead) {
            prev = cur;
            cur = cur.next;
        }

        if (prev == null) {
            // delete head
            cll.remove();
            return;
        }

        prev.next = iterHead.next; // Skip iterHead, effectively removing it from the list
        iterHead = iterHead.next; // Move iterHead to the next element after removal
        ++cll.modCount;
        expectedModCount = cll.modCount;
    }

    public CircularLinkedListIterator(CircularLinkedList<T> cll)
    {
        super();
        this.cll = cll;
        iterHead = cll.head;
        expectedModCount = cll.modCount;
    }
}
