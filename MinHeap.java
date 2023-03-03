package prj1;

/**
 * In this class, we implement the d-ary min-heap data structure
 * 
 * @author Enter your names here
 *
 */
public class MinHeap {
    // The parameter d in the d-ary min-heap
    private int d;

    // The array representation of your min-heap (It is not required to use
    // this)
    private HeapNode[] nodes;

    /**
     * Constructor
     * 
     * @param n:
     *            maximum number of elements in the min-heap at each time
     * @param d:
     *            parameter d in the d-ary min-heap
     */
    public MinHeap(int n, int d) {
        this.d = d;
        this.nodes = new HeapNode[n + 1];
        this.nodes[0] = new HeapNode(-1, -1);
    }


    /**
     * returns the Parent of the node at the location index
     * 
     * @param index
     * @return
     */
    public HeapNode getParent(int index) {
        return nodes[index / 2];
    }


    /**
     * returns the Child of the node at the location index
     * 
     * @param index,
     *            k
     *            k = 0: lowest magnitude child;
     *            k = d-1: highest magnitude child of parent
     * @return null if k >= d. only d-1 children in the heap.
     */
    public HeapNode getChild(int index, int k) {
        if (k < this.d) {
            return nodes[(index * 2) + k];
        }
        return null;
    }


    /**
     * This method returns the number of full levels in the heap.
     * 
     * @return the number of full levels in the heap
     */
    public int full_levels() {
        // TODO complete
        if (nodes.length - 1 == 0) {
            return 0;
        }
        else {
            int level = 1;
            int node_in_level = 1;
            for (int i = 1; i < 100; i++) {
                node_in_level = this.d * node_in_level;
                if (nodes.length - 1 <= node_in_level) {
                    return level;
                }
                level = level + 1;
            }
        }
        return -1;
    }


    /**
     * This method returns the number of nodes that fill the last level
     * in the heap
     * 
     * @return the number of nodes that fill up the last level in the heap
     */
    public int leftover_nodes() {
        // TODO complete
        if (nodes.length - 1 <= 0) {
            return 0;
        }

        else {
            int node_in_level = 1;
            int sum = 1;
            for (int i = 1; i < 100; i++) {
                node_in_level = this.d * node_in_level;
                if (nodes.length - 1 <= node_in_level) {
                    int leftover_nodes = nodes.length - sum - 1;
                    return leftover_nodes;
                }
                sum = sum + node_in_level;
            }
        }
        return -1;
    }

    


    /**
     * This method inserts a new element with "id" and "value" into the min-heap
     * 
     * @param id
     * @param value
     */
    public void insert(int id, int value) {
        // Creates an array with existing nodes plus new node at the end
        HeapNode new_node = new HeapNode(id, value);

        // get size of nodes
        int size = this.size();
        HeapNode[] temp;

        temp = new HeapNode[nodes.length + 1];
        int i = 1;
        while (i < nodes.length-1) {
            temp[i-1] = nodes[i];
            i++;
        }
        
        
        temp[size] = new_node;
        
        // parent-child comparison
        int index = size;
        while (index > 0) {
            if (temp[(index) / d ].getValue() > new_node.getValue()) {
                HeapNode tmp = temp[(index) / d];
                temp[(index) / d] = new_node;
                temp[index] = tmp;
            }
            index = (index) / d;
        }
        size++;


        for (int j = 1; j < size + 1; j++) {
            nodes[j] = temp[j - 1];
        }
    }


    /**
     * This method extracts the min value of the heap
     * 
     * @return an array consisting of two integer elements: id of the minimum
     *         element and the value of the minimum element
     * 
     *         So for example, if the minimum element has id = 5 and value = 1,
     *         you should return the array [5, 1]
     */
    public int[] extractMin() {
        // The minimum value should always be the first element in the heap
        // array
        
        int n = this.size();

        //Swap first and last positions
        HeapNode extracted_node = nodes[1];
        this.nodes[1] = this.nodes[n];
        this.nodes[n] = extracted_node;
       

        // Swap first and last elements in array, delete the last element
        int last_index = 0;
        for(int i = 1; i < nodes.length; i++) {
            if(nodes[i] != null) {
                last_index = i;
            }
        }

        HeapNode[] temp = new HeapNode[nodes.length];
        for (int i = 0; i < last_index; i++) {
            temp[i] = this.nodes[i];
        }

 
        // Check if new parent is greater than its children. If it is, swap
        // parent with the child with smaller value.
        // Else, do nothing.
        HeapNode swapped_node = temp[1];
        int index = 1;
        
   
        
        while (index < last_index - 1) {
            // Finds index of new node
            for (int i = 1; i < last_index; i++) {
                if (temp[i] == swapped_node) {
                    index = i;
                }
            }
        
        
            int children = 0;
            for(int i = 1; i < d; i++) {
                if(temp[(index)* d - 2 + i] != null && index*d-2+i < last_index) {
                    children++;
                }
            }
            
            if(children == 0) {
                break;
            }
            
            //Find the smallest Child of index
            int min_child = Integer.MAX_VALUE;
            int min_child_index = -1;
            for(int child = 0; child < children; child++) {
                if(temp[(index) * d - 2 + child].getValue() < min_child) {
                    min_child = temp[(index)* d - 2 + child].getValue();
                    min_child_index = child;
                }
            }
            
            
            //Swap the parent and the smallest child if the parent is larger than the smallest child
            if (swapped_node.getValue() > min_child) {
                HeapNode temp_node = temp[index];
                temp[index] = temp[(index-1) * d + 2 + min_child_index];
                temp[(index-1) * d + 2 + min_child_index]= temp_node;
                index = (index-1)*d+2+min_child_index;
            } else {
                break;
            }
        }
        
        for(int i = last_index; i < temp.length; i++) {
            temp[i] = null;
        }
           
        nodes = temp;
        int[] returned_array = {extracted_node.getId(), extracted_node.getValue()};
        return returned_array;
    
    }


    /**
     * This method takes an id and a new value newValue for the corresponding
     * node, and updates the data structure accordingly
     * 
     * @param id
     * @param newValue
     */
    public void decreaseKey(int id, int newValue) {
        int index = -1;
        for (int i = 1; i < nodes.length; i++) {
            if (nodes[i].getId() == id) {
                index = i;
            }

        }

        // update new value

        int parent = (index - 1) / d + 1;
        nodes[index].setValue(newValue);
        while (index > 1 && nodes[index].getValue() < nodes[parent]
            .getValue()) {

            HeapNode temp = nodes[index];
            nodes[index] = nodes[parent];
            nodes[parent] = temp;

            // updates parent

            index = parent;
            parent = (index - 1) / d + 1;
        }
    }


    /**
     * This method returns the array representation of heap
     * 
     * @return the array representation of heap
     */
    public int[] getHeap() {
        int[] heap = new int[this.size() + 1];
        heap[0] = Integer.MIN_VALUE;
        for (int i = 1; i < this.size() + 1; i++) {
            if(nodes[i] != null) {
                heap[i] = nodes[i].getValue();
            }
        }
        return heap;
    }
    
    
    public int size() {
        int size=0;
        for(int i = 1; i < nodes.length; i++) {
            if(nodes[i] != null) {
                size++;
            }
        }
        return size;
    }
    
    
    
    public boolean isEmpty() {
        if(this.size() == 0) {
            return true;
        }
        return false;
    }


    /**
     * the toString method that returns a string with the values of the heap in
     * the array representation.
     * This method can help you find the issues of your code when you want to
     * debug.
     * 
     * @return string form of the array representation of heap
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < this.nodes.length; i++) {
            if (nodes[i] != null) {
                sb.append(nodes[i].getValue());
                sb.append(' ');
            }
        }
        return sb.toString();
    }

}