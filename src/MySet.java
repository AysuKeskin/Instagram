public class MySet<K> {
    private static final int initial_capacity = 100; // initial size of the array 100
    private static final double load_factor = 0.7; // load factor for resizing

    private Bucket<K>[] elements;
    private int size; // size of all elements

    public MySet() {
        elements = new Bucket[initial_capacity];
        size = 0;
        for (int i = 0; i < initial_capacity; i++) {
            elements[i] = new Bucket<>();
        }
    }
    public boolean contains(K key){
        return get(key);
    } // checks if the map has that element
    private int hash(K key){
        return Math.abs(key.hashCode() % elements.length);
    } // returns unique value based on key
    public void put(K key){ // puts the key value pair to a suitable spot
        int index = hash(key);
        if (elements[index].put(key) != -1)
            size++;
        if ((double) size / elements.length >= load_factor) {
            resize(); // resize the array
        }
    }
    public boolean get(K key) { // returns the value based on key
        int index = hash(key);
        return elements[index].get(key);
    }
    public void remove(K key) { // removes the key value pair with the given key
        int index = hash(key);
        int ifRemoved = elements[index].remove(key);
        if (ifRemoved != -1) { // if removing happened update the size
            size--;
        }
    }


    private static class Bucket<K>{ // bucket class to handle the collisions
        Entry<K> head;

        public Bucket() { //?
            head = null;
        }

        public boolean get(K key){ // iterates through elements until it finds the one with the correct key
            Entry<K> current = head;
            while (current != null) {
                if (current.key.equals(key)) {
                    return true;
                }
                current = current.next;
            }
            return false;
        }
        public int put(K key){ // puts the key value pair to the next available location
            Entry<K> current = head;
            while (current != null) {
                if (current.key == key) {
                    return -1; // it already exists
                }
                current = current.next;
            }
            Entry<K> newEntry = new Entry<>(key);
            newEntry.next = head;
            head = newEntry;
            return 1; // it has been put
        }
        public int remove(K key){ // removes the pair with the given key
            Entry<K> current = head;
            Entry<K> previous = null;
            while (current != null) {
                if (current.key.equals(key)) {
                    if (previous == null) // removes head
                        head = current.next;
                    else
                        previous.next = current.next;
                    return 1; // removing has been done
                }
                previous = current;
                current = current.next;
            }
            return -1; // key does not exists
        }
    }
    private static class Entry<K> { // class that is used by bucket class to handle the collisions
        K key;
        Entry<K> next;

        public Entry(K key) {
            this.key = key;
            this.next = null;
        }
    }

    private void resize() { // resizes the array to stop too many collisions
        Bucket<K>[] newElements = new Bucket[elements.length * 2]; // size is doubled
        for (int i = 0; i < newElements.length; i++) {
            newElements[i] = new Bucket<>();
        }

        for (Bucket<K> bucket : elements) {
            Entry<K> current = bucket.head;
            while (current != null) {
                int index = Math.abs(current.key.hashCode() % newElements.length);
                newElements[index].put(current.key);
                current = current.next;
            }
        }
        elements = newElements; // array is updated
    }
}
