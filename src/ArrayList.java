public class ArrayList<T extends Comparable<T>> implements List<T> {
    private T[] arrayList;
    private boolean isSorted = true;
    int nextEmpty;
    public ArrayList() {
        arrayList = (T[]) new Comparable[2];
        nextEmpty = 0;
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
        if (element == null) {
            return false;
        }
        if (nextEmpty == arrayList.length) {
            this.extendListSize(this.arrayList);
        }
        arrayList[nextEmpty] = element;
        if (nextEmpty != 0) {
            isSorted = (arrayList[nextEmpty].compareTo(arrayList[nextEmpty - 1])) >= 1;
        }
        nextEmpty ++;
        return true;
    }

    @Override
    public boolean add(int index, T element) {
        if (element == null) {
            return false;
        }
        if (nextEmpty == arrayList.length) {
            this.extendListSize(this.arrayList);
        }
        if (index < arrayList.length) {
            arrayList[index] = element;
            for (int i = nextEmpty; i <= arrayList.length) {
                if (arrayList[i] == null) {
                    nextEmpty = i;
                    return true;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    @Override
    public void clear() {
        for (int i = 0; i < arrayList.length; i++) {
            arrayList[i] = null;
        }
        isSorted = true;
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
        if 
    }
}
