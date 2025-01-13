import java.util.ArrayList;

class MaxHeap {
    private ArrayList<Post> heap;
    public MaxHeap(ArrayList<Post> posts) { // Build heap from given arraylist
        this.heap = new ArrayList<>(posts); // Copy input array to the heap

        // Start percolating down from the last non-leaf node
        for (int i = (heap.size() / 2) - 1; i >= 0; i--) {
            percolatedown(i);
        }
    }

    public Post deleteMax() { // method to delete maximum element and return it
        if (heap.isEmpty()) return null;

        Post maxPost = heap.get(0);
        // Set last element to 0 position than find right place for it using percolate down
        Post lastPost = heap.remove(heap.size() - 1);
        if (!heap.isEmpty()) {
            heap.set(0, lastPost);
            percolatedown(0);
        }
        return maxPost;
    }

    private void percolatedown(int index) {
        int largest = index;
        int left = 2 * index + 1; // index of left child
        int right = 2 * index + 2; // index of right child
        // compare with children, if one is larger update largest
        if (left < heap.size() && heap.get(left).compareTo(heap.get(largest)) > 0) {
            largest = left;
        }
        if (right < heap.size() && heap.get(right).compareTo(heap.get(largest)) > 0) {
            largest = right;
        }
        // swap to the right position
        if (largest != index) {
            swap(index, largest);
            percolatedown(largest);
        }
    }
    private void swap(int i, int j) { // method to swap i'th and j'th element
        Post temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }
    public boolean isEmpty(){return heap.isEmpty();}
}