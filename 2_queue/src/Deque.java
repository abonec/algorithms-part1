import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private class QueueIterator implements Iterator<Item> {

        private Node prev;

        QueueIterator(Deque<Item> items) {
            prev = items.first;
        }

        @Override
        public boolean hasNext() {
            return prev != null;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = prev.val;
            prev = prev.prev;
            return item;
        }

    }

    private class Node {
        private Node next;
        private Node prev;
        private Item val;

        Node(Item item) {
            val = item;
        }
    }

    private Node first = null;
    private Node last = null;
    private int size = 0;

    public boolean isEmpty() {
        return first == null && last == null;
    }                 // is the deque empty?

    public int size() {
        return size;
    }                        // return the number of items on the deque

    public void addFirst(Item item) {
        checkNull(item);
        Node newNode = new Node(item);
        newNode.prev = first;
        if (newNode.prev != null) {
            newNode.prev.next = newNode;
        }
        first = newNode;
        if (last == null) {
            last = newNode;
        } else if (last.next == null) {
            last.next = newNode;
        }
        size++;
    }          // add the item to the front

    public void addLast(Item item) {
        checkNull(item);
        Node newNode = new Node(item);
        newNode.next = last;
        if (newNode.next != null) newNode.next.prev = newNode;
        last = newNode;
        if (first == null) {
            first = newNode;
        }
        size++;
    }           // add the item to the end

    public Item removeFirst() {
        checkEmpty();
        Node result = first;

        first = result.prev;
        if (first != null) first.next = null;

        if (last == result) last = null;

        size--;
        return result.val;
    }               // remove and return the item from the front

    public Item removeLast() {
        checkEmpty();
        Node result = last;

        last = result.next;
        if (last != null) last.prev = null;

        if (first == result) {
            first = null;
        }

        size--;
        return result.val;
    }             // remove and return the item from the end

    private void checkNull(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }
    }

    private void checkEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
    }

    public Iterator<Item> iterator() {
        return new QueueIterator(this);
    }         // return an iterator over items in order from front to end

    public static void main(String[] args) {
        Deque<Integer> d = new Deque<Integer>();

        d.addFirst(1);
        d.addFirst(2);
        d.addFirst(3);
        d.addLast(0);

        System.out.println(d.removeLast());
        System.out.println(d.removeLast());
        System.out.println(d.removeLast());
        System.out.println(d.removeLast());

        System.out.println("==============");
        System.out.println(d.isEmpty());
        System.out.println(d.size());
    }
}
