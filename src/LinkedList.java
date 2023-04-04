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
            isSorted = lastNode.getData().compareTo(lastNode.getNext().getData()) < 1;
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
            isSorted = (currentNode.getData()== null || currentNode.getData().compareTo(currentNode.getNext().getData()) < 1) && (currentNode.getNext().getNext() == null || currentNode.getNext().getData().compareTo(currentNode.getNext().getNext().getData()) > 1);
        }
        numEle ++;
        return true;
    }

    @Override
    public void clear() {
        firstNode.setNext(null);
        numEle = 0;
        isSorted = true;
        currentNode = firstNode;
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
        if (element != null) {
            int index = 0;
            while (currentNode.getNext() != null) {
                if (element.equals(currentNode.getNext().getData())) {
                    return index;
                } else {
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
        if (index >= numEle) {
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
        // TODO
    }

    @Override
    public void reverse() {
        // TODO
    }

    @Override
    public void merge(List<T> otherList) {
        // TODO
    }

    @Override
    public boolean rotate(int n) {
        // TODO
        return true;
    }

    @Override
    public String toString() {
        currentNode = firstNode;
        String retStr = "";
        while (currentNode.getNext() != null) {
            currentNode = currentNode.getNext();
            retStr = retStr + currentNode.getData() + "/n";
        }
        return retStr;
    }

    @Override
    public boolean isSorted() {
        return isSorted;
    }
}
