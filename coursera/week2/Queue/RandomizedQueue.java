import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size;
    private Node front;

    private class Node {
        Item item;
        Node next;
    }

    public RandomizedQueue() {
        size = 0;
        front = null;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("not enqueue a null item");
        }

        Node node = new Node();
        node.item = item;
        node.next = front;
        front = node;
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        return sample(true);
    }

    // return a random item (but not remove it)
    public Item sample() {
        return sample(false);
    }

    private Item sample(boolean isRemove) {
        if (isEmpty()) {
            throw new NoSuchElementException("no more item");
        }

        int randomIdx = StdRandom.uniform(size);
        Node prevNode = getPrevNode(randomIdx);
        Node curNode = (prevNode == null) ? front : prevNode.next;
        Item item = curNode.item;
        if (isRemove) {
            if (prevNode == null) {
                front = (size == 1) ? null : front.next;
            } else {
                prevNode.next = curNode.next;
            }
            size--;
        }

        return item;
    }

    private Node getPrevNode(int idx) {
        Node prev = null;
        Node cur = front;
        for (int i = 0; i <= idx; i++) {
            if (i != idx) {
                prev = cur;
                cur = cur.next;
            }
        }

        return prev;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new QueueIterator();
    }

    private class QueueIterator implements Iterator<Item> {
        int pos = 0;
        int[] allIdx = getUniformIdx();

        public boolean hasNext() {
            return pos < size;
        }

        public Item next() {
            if (hasNext()) {
                int idx = allIdx[pos];
                Node prev = getPrevNode(idx);
                pos++;
                return (prev == null) ? front.item : prev.next.item;
            } else {
                throw new NoSuchElementException("no more item");
            }
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private int[] getUniformIdx() {
        int []allIdx = new int[size];
        for (int i = 0; i < size; i++)  {
            allIdx[i] = i;
        }

        if (size > 0) {
            StdRandom.shuffle(allIdx);
        }

        return allIdx;
    }

    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        queue.enqueue("sheng");
        queue.enqueue("choes");
        queue.enqueue("young");
        queue.dequeue();
        queue.enqueue("hello");
        queue.enqueue("world");
        queue.dequeue();

        System.out.println("queue " + (queue.isEmpty() ? "is" : "is not") + " empty, size: " + queue.size());
        for (String str : queue) {
            System.out.println(str);
        }

        System.out.println("--------------");
        for (String str : queue) {
            System.out.println(str);
        }
    }
}
