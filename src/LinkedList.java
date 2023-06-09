// Written by Tamojit Bera, bera0041 and Srinivas Preetham Addepalli, addep011

public class LinkedList<T extends Comparable<T>> implements List<T> {
    private Node<T> firstNode;                              // Points to the header node in the headed linked list
    private Node<T> currentNode;                            // Points to the node we are working with - Initialised to firstNode or firstNode.getNext() at the start of required methods
    private Node<T> lastNode;                               // Points to the last node in the List
    private boolean isSorted = true;
    private int numEle;                                     // Keeps track of number of elements in the list
    public LinkedList() {                                   // Constructor
        firstNode = new Node<T>(null);
        currentNode = firstNode;
        lastNode = firstNode;
        numEle = 0;
    }

    @Override
    public boolean add(T element) {
        if (element == null) {
            return false;
        }
        lastNode.setNext(new Node<T>(element, null));                           // Adds element to the end of the list
        numEle ++;
        if (numEle > 1 && isSorted) {                                                // Checks for isSorted only if list was previously sorted
            isSorted = lastNode.getData().compareTo(element) <= 0;                   // Compares previous lastNode to added element
        }
        lastNode = lastNode.getNext();                                               // Updates lastNode
        return true;
    }

    @Override
    public boolean add(int index, T element) {
        currentNode = firstNode;
        if (element == null || index > numEle) {
            return false;
        }
        int ind = 0;                                                                                    // Keeps track of index while traversing the list
        while (ind++ < index) {
            currentNode = currentNode.getNext();
        }
        currentNode.setNext(new Node<T>(element, currentNode.getNext()));                               // Adds element at given index

        numEle ++;

        if (numEle > 1 && isSorted) {                                                                   // Checks for isSorted only if list was previously sorted
            isSorted = (currentNode.getData() == null || currentNode.getData().compareTo(element) <= 0) && (currentNode.getNext().getNext() == null || element.compareTo(currentNode.getNext().getNext().getData()) <= 0);      // Compares added value with the previous and the next values (also takes care of null if added at first or last index)
        }

        if (lastNode.getNext() != null) {                                                               // Updates lastNode
            lastNode = lastNode.getNext();
        }
        return true;
    }

    @Override
    public void clear() {
        firstNode.setNext(null);                                            // Sets the header node to null to clear the list
        numEle = 0;                                                         // Accordingly changes the instance variables
        isSorted = true;
        lastNode = firstNode;
    }

    @Override
    public T get(int index) {
        currentNode = firstNode;
        if (index >= numEle || index < 0) {                                 // Returns null for an out-of-bounds index
            return null;
        }
        int ind = 0;                                                        // Keeps track of the index
        while (ind++ <= index && currentNode.getNext()!=null) {             // Traverses the list until index is reached
            currentNode = currentNode.getNext();
        }
        return (T) currentNode.getData();                                   // Returns the value at given index
    }

    @Override
    public int indexOf(T element) {
        currentNode = firstNode;
        if (element != null && !isEmpty()) {
            int index = 0;
            while (currentNode.getNext() != null) {                                         // Traverses through the list to find the given element
                if (element.equals(currentNode.getNext().getData())) {
                    return index;                                                           // Returns the first index at which element is found (if found)
                } else {
                    if (isSorted) {
                        if (currentNode.getNext().getData().compareTo(element) > 0) {       // If list is sorted and a value greater than the element is encountered, the traversal stops and -1 is returned
                            return -1;
                        }
                    }
                    currentNode = currentNode.getNext();
                    index ++;
                }
            }
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        if (lastNode.equals(firstNode)) {                  // if lastNode points to firstNode, which is the header node, the linked list is empty
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return numEle;
    }                   // Returns the number of elements in the linked list (excluding the header node)

    @Override
    public void sort() {
        if (!isSorted && (numEle != 0 || numEle != 1)) {                                            // Sorts list only if not already sorted
            currentNode = firstNode.getNext();
            Node<T> sorted = new Node<>(null);
            while (currentNode != null) {                                                           // Takes all values in the linked list, adds them to the new list in a sorted manner and then sets the current list to the sorted list
                T data = currentNode.getData();                                                     // Stores the data of the current node to compare to previous nodes
                Node<T> ptr, trailer;
                ptr = sorted.getNext();
                trailer = sorted;
                while (ptr != null && ptr.getData().compareTo(data) < 0) {                          // Traverses through the previous nodes to find where to insert the data. Loop breaks at point of insertion
                    trailer = ptr;
                    ptr = ptr.getNext();
                }

                trailer.setNext(new Node<T> (data, ptr));                                           // Inserts the new node at the point where the loop breaks, i.e. at correct position of insertion. Inserts at 0th index if list is null
                currentNode = currentNode.getNext();
            }
            firstNode = sorted;                                                                     // Sets this list to the sorted list (header of this list to header of sorted list)
        }
        if (lastNode.getNext() != null) {                                                           // Sets the last node
            while (lastNode.getNext() != null) {
                lastNode = lastNode.getNext();
            }
        }
        isSorted = true;
    }

    @Override
    public T remove(int index) {
        currentNode = firstNode;
        if (index >= numEle || index < 0) {                                                         // Fall through case
            return null;
        }
        int ind = 0;
        while (ind++ < index) {                                                                     // Iterates to the node to be removed
            currentNode = currentNode.getNext();
        }
        T removedEle = (T) currentNode.getNext().getData();                                         // Gets to-be removed node
        currentNode.setNext(currentNode.getNext().getNext());                                       // Skips the to-be removed node to remove it
        numEle --;                                                                                  // Reduces the counter for number of elements in the list

        lastNode = firstNode;
        while (lastNode.getNext() != null) {                                                        // Updates lastNode
            lastNode = lastNode.getNext();
        }

        isSorted = true;
        if (numEle > 1) {
            currentNode = firstNode.getNext();
            while (isSorted && currentNode.getNext() != null) {
                isSorted = currentNode.getData().compareTo(currentNode.getNext().getData()) <= 0;   // Updates isSorted
                currentNode = currentNode.getNext();
            }
        }
        return removedEle;
    }

    @Override
    public void equalTo(T element) {
        if (element != null) {
            currentNode = firstNode;
            while (currentNode.getNext() != null) {                                                 // Iterates through the list and removes nodes whose values are not equal to element
                if (!currentNode.getNext().getData().equals(element)) {
                    currentNode.setNext(currentNode.getNext().getNext());
                    numEle --;
                } else {
                    currentNode = currentNode.getNext();                                            // Doesn't remove nodes whose values are equal to element
                }
                lastNode = currentNode;
            }
            isSorted = true;                                                                        // Updates isSorted
        }
    }

    @Override
    public void reverse() {
        if (numEle != 0 || numEle != 1) {
            currentNode = firstNode.getNext();
            lastNode = firstNode.getNext();                                                         // Updates lastNode
            Node<T> prevNode = null;
            while (currentNode != null) {
                Node<T> temp = currentNode;                                                         // Stores a copy of current pointer in temp to reverse its direction
                currentNode = currentNode.getNext();                                                // Moves ahead current pointer
                temp.setNext(prevNode);                                                             // Reverses the direction of the temp
                prevNode = temp;                                                                    // Updates previous node
            }
            firstNode.setNext(prevNode);

            isSorted = true;
            currentNode = firstNode.getNext();
            while (isSorted && currentNode.getNext() != null) {                                     // Updates isSorted
                isSorted = currentNode.getData().compareTo(currentNode.getNext().getData()) <= 0;
                currentNode = currentNode.getNext();
            }
        }
    }

    @Override
    public void merge(List<T> otherList) {
        if (otherList != null) {
            LinkedList<T> other = (LinkedList<T>) otherList;
            Node<T> mergedNode = new Node<T> (null);                                        // First Node of the merged linked list
            Node<T> ptr = mergedNode;                                                            // Points to the last node of the linked list, where new elements are added
            int counter = 0;                                                                     // Keeps track of the number of elements added to the merged list
            this.sort();
            other.sort();
            while (this.get(0) != null || other.get(0) != null) {                               // Keeps looping until both lists are empty
                T addEle;
                if (this.get(0) == null) {                                                      // If this list if empty, keeps adding the other list to the merged list
                    addEle = other.remove(0);
                } else if (other.get(0) == null) {                                              // If the other list if empty, keeps adding this list to the merged list
                    addEle = this.remove(0);
                } else if (this.get(0).compareTo(other.get(0)) <= 0) {                          // If none are empty, compares the values of both the lists and adds the smaller one to the merged list
                    addEle = this.remove(0);
                } else {
                    addEle = other.remove(0);
                }
                ptr.setNext(new Node<T> (addEle, null));
                ptr = ptr.getNext();
                counter ++;
            }
            firstNode = mergedNode;                                                                 // Sets the current linked list to the merged one
            isSorted = true;
            numEle = counter;

            lastNode = firstNode;
            while (lastNode.getNext() != null) {                                                    // Sets the last node
                lastNode = lastNode.getNext();
            }
        }
    }

    @Override
    public boolean rotate(int n) {
        currentNode = firstNode;
        if (n <= 0 || numEle <= 1 || n >= numEle) {                                             // Fall through case
            return false;
        }
        while (n++ < numEle) {
            currentNode = currentNode.getNext();                                                // Sets currentNode to the last node of the rotated list
        }
        lastNode.setNext(firstNode.getNext());                                                  // Updates lastNode
        firstNode.setNext(currentNode.getNext());                                               // Sets firstNode to the node just after the lastNode (Bringing rotated nodes to the front of the list)
        currentNode.setNext(null);                                                              // Sets lastNode's next pointer to null (to really make it the last node)

        isSorted = true;
        currentNode = firstNode.getNext();
        while (isSorted && currentNode.getNext() != null) {                                     // Updates isSorted
            isSorted = currentNode.getData().compareTo(currentNode.getNext().getData()) <= 0;
            currentNode = currentNode.getNext();
        }

        while (lastNode.getNext() != null) {                                                    // Updates lastNode
            lastNode = lastNode.getNext();
        }

        return true;
    }

    @Override
    public String toString() {
        currentNode = firstNode;
        String retStr = "";
        while (currentNode.getNext() != null) {                     // Creates a string representation of the linked list, with each element in a new line
            currentNode = currentNode.getNext();
            retStr = retStr + currentNode.getData() + "\n";
        }
        return retStr;
    }

    @Override
    public boolean isSorted() {                     // Returns isSorted
        return isSorted;
    }
}
