public class LinkedList<T extends Comparable<T>> implements List<T> {
    private Node firstNode;
    private Node currentNode;
    private boolean isSorted = true;
    private int numEle;
    public LinkedList() {
        firstNode = new Node(null);
        currentNode = firstNode;
        numEle = 0;
    }

    @Override
    public boolean add(T element) {
        if (element.equals(null)) {
            return false;
        }
        currentNode.setNext(new Node(element, null));
        numEle ++;
        if (numEle > 1) {
            isSorted = currentNode.getData().compareTo(currentNode.getNext().getData()) < 1;
        }
        currentNode = currentNode.getNext();
        return true;
    }

    @Override
    public boolean add(int index, T element) {
        if (element.equals(null) || index > numEle) {
            return false;
        }
        // TODO
        numEle ++;
        return true;
    }

    @Override
    public void clear() {

    }
}
