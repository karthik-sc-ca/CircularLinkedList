package com.kscreens;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 * Circular Linked List
 * @param <T>
 */
public class CircularLinkedList<T> implements Iterable<T>, Collection<T>
{
    class Node
    {
        T item;
        Node next;

        Node(T item)
        {
            this.item = item;
        }
    }

    Node head;

    // Inside CircularLinkedList class
    long modCount = 0; // modification count

    T remove()
    {
        if (head == null)
        {
            throw new NullPointerException();
        }

        if (head.next == head)
        {
            Node n = head;
            head = null;
            return n.item;
        }

        Node cur = head;
        while (cur.next != head)
        {
            cur = cur.next;
        }

        Node n = cur.next;
        head = cur.next = cur.next.next;
        return n.item;
    }

    @Override
    public Iterator<T> iterator()
    {
        return new CircularLinkedListIterator<>(this);
    }

    @Override
    public int size()
    {
        int sizeVal = 0;
        for (T ignored : this)
        {
            ++sizeVal;
        }
        return sizeVal;
    }

    @Override
    public boolean isEmpty()
    {
        return this.size() == 0;
    }

    @Override
    public boolean contains(Object o)
    {
        if (o == null)
        {
            return false; // null cannot be contained in the list
        }
        for (T item : this)
        {
            if (o.equals(item))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object[] toArray()
    {
        if (isEmpty()) {
            return new Object[0]; // Return an empty array if the list is empty
        }

        Object[] array = new Object[size()];
        int index = 0;
        for (T item : this) {
            array[index++] = item;
        }
        return array;
    }

    @Override
    public <E> E[] toArray(E[] a)
    {
        if (a == null)
        {
            throw new NullPointerException();
        }

        if (a.length < size())
        {
            return Arrays.copyOf(this.toArray(), size(), (Class<? extends E[]>) a.getClass());
        } else
        {
            System.arraycopy(this.toArray(), 0, a, 0, size());
            if (a.length > size())
            {
                a[size()] = null;
            }
            return a;
        }
    }

    @Override
    public boolean add(T item)
    {
        if(item == null)
        {
            throw new NullPointerException();
        }
        Node n = new Node(item);
        if (head == null)
        {
            head = n;
            head.next = head;
        } else
        {
            Node cur = head;
            while (cur.next != head)
            {
                cur = cur.next;
            }
            cur.next = n;
            n.next = head;
        }
        ++modCount;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (o == null)
        {
            throw new NullPointerException("Null value cannot be removed from the list.");
        }

        if (head == null)
        {
            return false; // List is empty
        }

        if (head.next == head)
        {
            if (head.item.equals(o)) {
                head = null; // Remove the only node in the list
                modCount++;
                return true;
            } else
            {
                return false; // Item not found in the single node
            }
        }

        Node cur = head.next;
        Node prev = head;

        do {
            if (cur.item.equals(o))
            {
                prev.next = cur.next; // Remove the current node
                if (cur == head) {
                    head = prev.next; // Update head if the removed node is the head
                }
                modCount++;
                return true;
            }
            prev = cur;
            cur = cur.next;
        } while (cur != head);

        return false; // Item not found in the list
    }


    @Override
    public boolean containsAll(Collection<?> c)
    {

        return c.stream().allMatch(this::contains);

    }

    @Override
    public boolean addAll(Collection<? extends T> c)
    {
        if(c == null)
        {
            throw new NullPointerException();
        }
        c.forEach(this::add);
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c)
    {
        if(c == null)
        {
            throw new NullPointerException();
        }
        c.forEach(this::remove);
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c)
    {
        if (c == null)
        {
            throw new NullPointerException();
        }

        boolean modified = false;
        Iterator<T> iterator = iterator();
        while (iterator.hasNext())
        {
            T item = iterator.next();
            if (!c.contains(item))
            {
                iterator.remove(); // Remove elements not present in collection c
                modified = true;
            }
        }
        return modified;
    }

    @Override
    public void clear()
    {
        if (head != null)
        {
            head.next = head;
        }
        head = null;

    }

}

