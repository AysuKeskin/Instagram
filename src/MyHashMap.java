public class MyHashMap<K, V> {
    private static final int initial_capacity = 128; // initial size of the array 100
    private static final double load_factor = 0.7; // load factor for resizing

    private Bucket<K, V>[] elements;
    private int size; // size of all elements

    public MyHashMap() {
        elements = new Bucket[initial_capacity];
        size = 0;
        for (int i = 0; i < initial_capacity; i++) {
            elements[i] = new Bucket<>();
        }
    }
    public boolean contains(K key){
        return get(key) != null;
    } // checks if the map has that element
    private int hash(K key){
        return Math.abs(key.hashCode() % elements.length);
    } // returns unique value based on key
    public void put(K key, V value){ // puts the key value pair to a suitable spot
        int index = hash(key);
        if (elements[index].put(key,value) != -1)
            size++;
        if ((double) size / elements.length >= load_factor) {
            resize(); // resize the array
        }
    }
    public V get(K key) { // returns the value based on key
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


    private static class Bucket<K, V>{ // bucket class to handle the collisions
        Entry<K, V> head;

        public Bucket() { //?
            head = null;
        }

        public V get(K key){ // iterates through elements until it finds the one with the correct key
            Entry<K, V> current = head;
            while (current != null) {
                if (current.key.equals(key)) {
                    return current.value;
                }
                current = current.next;
            }
            return null;
        }
        public int put(K key, V value){ // puts the key value pair to the next available location
            Entry<K, V> current = head;
            while (current != null) {
                if (current.key == key) {
                    return -1; // it already exists
                }
                current = current.next;
            }
            Entry<K, V> newEntry = new Entry<>(key, value);
            newEntry.next = head;
            head = newEntry;
            return 1; // it has been put
        }
        public int remove(K key){ // removes the pair with the given key
            Entry<K, V> current = head;
            Entry<K, V> previous = null;
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
    private static class Entry<K, V> { // class that is used by bucket class to handle the collisions
        K key;
        V value;
        Entry<K, V> next;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    private void resize() { // resizes the array to stop too many collisions
        Bucket<K, V>[] newElements = new Bucket[elements.length * 2]; // size is doubled
        for (int i = 0; i < newElements.length; i++) {
            newElements[i] = new Bucket<>();
        }

        for (Bucket<K, V> bucket : elements) {
            Entry<K, V> current = bucket.head;
            while (current != null) {
                int index = Math.abs(current.key.hashCode() % newElements.length);
                newElements[index].put(current.key, current.value);
                current = current.next;
            }
        }
        elements = newElements; // array is updated
    }
}
