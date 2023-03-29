public class ArrayList<T extends Comparable<T>> implements List<T> {
    private T[] arrayList;
    private boolean isSorted = true;
    private int numEle;
    private int nextEmpty;
    public ArrayList() {
        arrayList = (T[]) new Comparable[2];
        nextEmpty = 0;
        numEle = 0;
    }

    private void extendListSize(T[] oldList) {
        T[] newList = (T[])new Comparable[oldList.length * 2];
        for (int i = 0; i < oldList.length; i++) {
            newList[i] = oldList[i];
        }
        oldList = newList;
    }

    @Override
    public boolean add(T element) {
        if (element.equals(null)) {
            return false;
        }
        if (nextEmpty == arrayList.length) {
            this.extendListSize(this.arrayList);
        }
        arrayList[nextEmpty] = element;
        if (nextEmpty != 0) {
            isSorted = arrayList[nextEmpty].compareTo(arrayList[nextEmpty - 1]) >= 1;
        }
        nextEmpty++;
        numEle ++;
        return true;
    }

    @Override
    public boolean add(int index, T element) {
        if (element.equals(null) && index > nextEmpty) {
            return false;
        }
        if (nextEmpty == arrayList.length) {
            this.extendListSize(this.arrayList);
        }
        for (int i = nextEmpty; i > index; i--) {
            arrayList[i] = arrayList[i - 1];
        }
        arrayList[index] = element;
        if (arrayList[index - 1].compareTo(arrayList[index]) > 0 || (arrayList[index + 1] != null && arrayList[index + 1].compareTo(arrayList[index]) < 0)) {
            isSorted = false;
        }
        nextEmpty ++;
        return true;
    }

    @Override
    public void clear() {
        for (int i = 0; i < arrayList.length; i++) {
            arrayList[i] = null;
        }
        isSorted = true;
        nextEmpty = 0;
        numEle = 0;
    }

    public T get(int index) {
        if (index < arrayList.length) {
            return arrayList[index];
        }
        return null;
    }

    @Override
    public int indexOf(T element) {
        if (isSorted) {
            // TODO
        } else {
            for(int i = 0; i < arrayList.length; i++) {
                if (arrayList[i].equals(element)) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        if (nextEmpty == 0) {
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
        if (!isSorted) {
            // TODO
            isSorted = true;
        }
    }

    @Override
    public T remove(int index) {
        T remEle = null;
        if (index < nextEmpty) {
            remEle = arrayList[index];
            for (int i = index; i < nextEmpty; i++) {
                arrayList[i] = arrayList[i + 1];
            }
            nextEmpty --;
        }
        if (!isSorted) {
            for (int i = 0; i < nextEmpty - 1; i++) {
                if (arrayList[i].compareTo(arrayList[i + 1]) > 0) {
                    isSorted = false;
                    break;
                }
            }
        }
        return remEle;
    }

    @Override
    public boolean isSorted() {
        return isSorted;
    }
}
