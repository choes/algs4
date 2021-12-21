import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node front;
    private Node back;
    private int size;

    private class Node {
        Item item;
        Node prev;
        Node next;
    }

    public Deque() {
        front = null;
        back = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("not add a null item");
        }

        Node node = new Node();
        node.item = item;
        node.next = front;
        node.prev = null;

        if (front != null) {
            front.prev = node;
        }

        front = node;

        size++;
        if (size == 1) {
            back = front;
        }
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("not add a null item");
        }

        Node node = new Node();
        node.item = item;
        node.prev = back;
        node.next = null;
        if (back != null) {
            back.next = node;
        }
        back = node;

        size++;
        if (size == 1) {
            front = back;
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("no more item");
        }

        Item item = front.item;
        size--;
        if (size == 0) {
            front = null;
            back = null;
        } else {
            front = front.next;
            front.prev = null;
        }

        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("no more item");
        }

        Item item = back.item;
        size--;
        if (size == 0) {
            front = null;
            back = null;
        } else {
            back = back.prev;
            back.next = null;
        }

        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new QueueIterator();
    }

    private class QueueIterator implements Iterator<Item> {
        private Node current = front;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        Deque<String> queue = new Deque();
        queue.addLast("sheng");
        queue.removeFirst();
        queue.addFirst("yang");
        queue.addFirst("choes");
        queue.addLast("young");
        queue.removeFirst();
        queue.removeLast();

        System.out.println("queue " + (queue.isEmpty() ? "is" : "is not") + " empty, size: " + queue.size());
        for (String str : queue) {
            System.out.println(str);
        }
    }
}