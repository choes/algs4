import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size;
    private Item[] items;
    private int idx;

    public RandomizedQueue() {
        size = 0;
        resize(1);
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

        if (idx < 0) {
            resize(2 * size);
        }

        size++;
        items[idx] = item;
        idx = -1;
        for (int i = items.length - 1; i >= 0; i--) {
            if (items[i] == null) {
                idx = i;
                break;
            }
        }
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("no more item");
        }

        int randomIdx = getRandomIdx();
        Item item = items[randomIdx];
        items[randomIdx] = null;
        size--;
        idx = randomIdx;

        return item;
    }

    // return a random item (but not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("no more item");
        }

        return items[getRandomIdx()];
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
            Item item = items[allIdx[pos]];
            pos++;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private void resize(int max) {
        Item[] temp = (Item[])new Object[max];
        for (int i = 0; i < size; i++) {
            if (items[i] != null) {
                temp[i] = items[i];
            }
        }

        idx = size;
        for (int i = size; i < max; i++) {
            temp[i] = null;
        }

        items = temp;
    }

    private int[] getAllIdx() {
        int []allIdx = new int[size];
        for (int i = 0, j = 0; i < items.length && j < size; i++) {
            if (items[i] != null) {
                allIdx[j] = i;
                j++;
            }
        }

        return allIdx;
    }

    private int getRandomIdx() {
        return getUniformIdx()[0];
    }

    private int[] getUniformIdx() {
        int []allIdx = getAllIdx();
        StdRandom.shuffle(allIdx);
        return allIdx;
    }

    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue();
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
    }
}
