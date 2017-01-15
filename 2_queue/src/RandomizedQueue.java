import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private class RandomIterator implements Iterator<Item> {

        private RandomizedQueue<Item> queue;
        private int[] order;
        private int index = 0;

        public RandomIterator(RandomizedQueue<Item> queue) {
            this.queue = queue;
            order = new int[queue.size()];
            for (int i = 0; i < order.length; i++) {
                order[i] = i;
            }
            StdRandom.shuffle(order);
        }

        @Override
        public boolean hasNext() {
            return order.length > index;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return queue.queue[order[index++]];
        }
    }

    private Item[] queue;
    private int queueSize = 0;

    public RandomizedQueue() {
        queue = newQueue(2);
    }                 // construct an empty randomized queue


    public boolean isEmpty() {
        return queueSize <= 0;
    }               // is the queue empty?

    public int size() {
        return queueSize;
    }             // return the number of items on the queue

    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException();
        enlargeQueue();
        queue[queueSize] = item;
        queueSize++;
    }          // add the item

    public Item dequeue() {
        if (queueSize == 0) throw new NoSuchElementException();
        int randomIndex = StdRandom.uniform(queueSize);
        Item result = queue[randomIndex];
        queue[randomIndex] = queue[queueSize - 1];
        queueSize--;
        compressQueue();
        return result;
    }        // remove and return a random item

    public Item sample() {
        if (queueSize == 0) throw new NoSuchElementException();
        return queue[StdRandom.uniform(queueSize)];
    }      // return (but do not remove) a random item

    public Iterator<Item> iterator() {
        return new RandomIterator(this);
    }    // return an independent iterator over items in random order

    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        rq.enqueue(1);
        rq.enqueue(2);
        rq.enqueue(3);
        rq.enqueue(4);
        rq.enqueue(5);
        Iterator<Integer> first = rq.iterator();
        Iterator<Integer> second = rq.iterator();
        while (first.hasNext()) {
            System.out.println(first.next());
        }

        System.out.println("next:");

        while (second.hasNext()) {
            System.out.println(second.next());
        }

    }  // unit testing

    private void enlargeQueue() {
        if (queue.length == queueSize) {
            queue = newQueue(queueSize * 2, queue);
        }
    }

    private Item[] newQueue(int size) {
        return (Item[]) new Object[size];
    }

    private Item[] newQueue(int newQueueSize, Item[] oldQueue) {
        Item[] newQueue = newQueue(newQueueSize);
        int bound = newQueueSize < oldQueue.length ? newQueueSize : oldQueue.length;
        for (int i = 0; i < bound; i++) {
            newQueue[i] = oldQueue[i];
        }
        return newQueue;
    }

    private void compressQueue() {
        if (queueSize != 0 && queue.length > queueSize * 4) {
            queue = newQueue(queue.length / 2, queue);
        }
    }
}
