public class LinkedList<T extends Comparable<T>> implements List<T> {
    private Node<T> firstNode;
    private Node<T> currentNode;
    private Node<T> lastNode;
    private boolean isSorted = true;
    private int numEle;
    public LinkedList() {
        firstNode = new Node(null);
        currentNode = firstNode;
        lastNode = firstNode;
        numEle = 0;
    }

    @Override
    public boolean add(T element) {
        if (element == null) {
            return false;
        }
        lastNode.setNext(new Node<T>(element, null));
        numEle ++;
        if (numEle > 1) {
            isSorted = lastNode.getData().compareTo(element) <= 0;
        }
        lastNode = lastNode.getNext();
        return true;
    }

    @Override
    public boolean add(int index, T element) {
        currentNode = firstNode;
        if (element == null || index > numEle) {
            return false;
        }
        int ind = 0;
        while (ind++ < index) {
            currentNode = currentNode.getNext();
        }
        currentNode.setNext(new Node<T>(element, currentNode.getNext()));
        if (numEle > 1) {
            isSorted = (currentNode.getData() == null || currentNode.getData().compareTo(element) <= 0) && (currentNode.getNext().getNext() == null || element.compareTo(currentNode.getNext().getNext().getData()) <= 0);
        }
        numEle ++;
        return true;
    }

    @Override
    public void clear() {
        firstNode.setNext(null);
        numEle = 0;
        isSorted = true;
    }

    @Override
    public T get(int index) {
        currentNode = firstNode;
        if (index >= numEle) {
            return null;
        }
        int ind = 0;
        while (ind++ <= index) {
            currentNode = currentNode.getNext();
        }
        return (T) currentNode.getData();
    }

    @Override
    public int indexOf(T element) {
        currentNode = firstNode;
        if (element != null && !isEmpty()) {
            int index = 0;
            while (currentNode.getNext() != null) {
                if (element.equals(currentNode.getNext().getData())) {
                    return index;
                } else {
//                    if (isSorted) {
//                        if (currentNode.getNext().getData().compareTo(element) > 0) {
//                            break;
//                        }
//                    }
                    currentNode = currentNode.getNext();
                    index ++;
                }
            }
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        if (firstNode.getNext() == null) {
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return numEle;
    }

    @Override
    public void sort() {
        // TODO
    }

    @Override
    public T remove(int index) {
        currentNode = firstNode;
        if (index >= numEle || index < 0) {
            return null;
        }
        int ind = 0;
        while (ind++ < index) {
            currentNode = currentNode.getNext();
        }
        T removedEle = (T) currentNode.getNext().getData();
        currentNode.setNext(currentNode.getNext().getNext());
        numEle --;
        return removedEle;
    }

    @Override
    public void equalTo(T element) {
        if (element != null) {
            currentNode = firstNode;
            while (currentNode.getNext() != null) {
                if (!currentNode.getNext().getData().equals(element)) {
                    currentNode.setNext(currentNode.getNext().getNext());
                    numEle --;
                } else {
                    currentNode = currentNode.getNext();
                }
            }
            isSorted = true;
        }
    }

    @Override
    public void reverse() {
        if (numEle != 0 || numEle != 1) {
            currentNode = firstNode.getNext();
            Node<T> newL = null;
            while (currentNode != null) {
                Node<T> temp = currentNode;
                currentNode = currentNode.getNext();
                temp.setNext(newL);
                newL = temp;
            }
            firstNode.setNext(newL);
            currentNode = firstNode.getNext();
            while (currentNode.getNext() != null) {
                isSorted = currentNode.getData().compareTo(currentNode.getNext().getData()) <= 0;
                currentNode = currentNode.getNext();
            }
        }
    }

    @Override
    public void merge(List<T> otherList) {
        // TODO
    }

    @Override
    public boolean rotate(int n) {
        currentNode = firstNode;
        if (n <= 0 || numEle <= 1) {
            return false;
        }
        while (n++ < numEle) {
            currentNode = currentNode.getNext();
        }
        lastNode.setNext(firstNode.getNext());
        firstNode.setNext(currentNode.getNext());
        currentNode.setNext(null);

        currentNode = firstNode.getNext();
        while (currentNode.getNext() != null) {
            isSorted = currentNode.getData().compareTo(currentNode.getNext().getData()) <= 0;
            currentNode = currentNode.getNext();
        }
        return true;
    }

    @Override
    public String toString() {
        currentNode = firstNode;
        String retStr = "";
        while (currentNode.getNext() != null) {
            currentNode = currentNode.getNext();
            retStr = retStr + currentNode.getData() + "\n";
        }
        return retStr;
    }

    @Override
    public boolean isSorted() {
        return isSorted;
    }

    public static void main(String[] args) {
        LinkedList<Integer> ll = new LinkedList<Integer>();
        ll.add(1);
        ll.add(2);
        ll.add(3);
        ll.add(4);
        ll.add(5);
        ll.add(6);
        ll.add(7);
        ll.add(8);
        ll.add(9);
        System.out.println(ll);
        ll.reverse();
        System.out.println(ll);
    }
}
