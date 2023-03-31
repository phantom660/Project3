import java.util.Arrays;

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

    private void extendListSize() {
        T[] newList = (T[]) new Comparable[arrayList.length * 2];
        System.arraycopy(arrayList, 0, newList, 0, arrayList.length);
        arrayList = newList;
    }

    @Override
    public boolean add(T element) {
        if (element == null) {
            return false;
        }
        if (nextEmpty == arrayList.length) {
            extendListSize();
        }
        arrayList[nextEmpty] = element;
        if (nextEmpty != 0) {
            isSorted = arrayList[nextEmpty].compareTo(arrayList[nextEmpty - 1]) >= 1;
        }
        nextEmpty++;
        numEle++;
        return true;
    }

    @Override
    public boolean add(int index, T element) {
        if (element == null || index > nextEmpty) {
            return false;
        }
        if (nextEmpty == arrayList.length) {
            extendListSize();
        }
        System.arraycopy(arrayList, index, arrayList, index + 1, nextEmpty - index);
        arrayList[index] = element;
        if (arrayList[index - 1].compareTo(arrayList[index]) > 0
                || (arrayList[index + 1] != null && arrayList[index + 1].compareTo(arrayList[index]) < 0)) {
            isSorted = false;
        }
        nextEmpty++;
        numEle++;
        return true;
    }

    @Override
    public void clear() {
        arrayList = (T[]) new Comparable[2];
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
        for (int i = 0; i < arrayList.length; i++) {
            if (arrayList[i].equals(element)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return nextEmpty == 0;
    }

    @Override
    public int size() {
        return numEle;
    }

    @Override
    public void sort() {
        if (!isSorted) {
            Arrays.sort(arrayList, 0, nextEmpty);
            isSorted = true;
        }
    }

    @Override
    public T remove(int index) {
        if (index >= nextEmpty) {
            return null;
        }
        T removedElement = arrayList[index];
        System.arraycopy(arrayList, index + 1, arrayList, index, nextEmpty - index - 1);
        nextEmpty--;
        numEle--;
        isSorted = true;
        for (int i = 1; i < nextEmpty; i++) {
            if (arrayList[i - 1].compareTo(arrayList[i]) > 0) {
                isSorted = false;
                break;
            }
        }
        return removedElement;
    }

    @Override
    public void reverse() {
        for (int i = 0; i < nextEmpty / 2; i++) {
            T temp = arrayList[i];
            arrayList[i] = arrayList[nextEmpty - 1 - i];
            arrayList[nextEmpty - 1 - i] = temp;
        }
        isSorted = nextEmpty <= 1;
    }

    @Override
    public void merge(List<T> otherList) {
        if (otherList == null) {
            return;
        }
        ArrayList<T> other = (ArrayList<T>) otherList;
        sort();
        other.sort();

        T[] mergedArray = (T[]) new Comparable[numEle + other.size()];
        int i = 0, j = 0, k = 0;

        while (i < numEle && j < other.size()) {
            if (arrayList[i].compareTo(other.get(j)) <= 0) {
                mergedArray[k++] = arrayList[i++];
            } else {
                mergedArray[k++] = other.get(j++);
            }
        }

        while (i < numEle) {
            mergedArray[k++] = arrayList[i++];
        }

        while (j < other.size()) {
            mergedArray[k++] = other.get(j++);
        }

        arrayList = mergedArray;
        nextEmpty = numEle + other.size();
        numEle += other.size();
        isSorted = true;
    }

    @Override
    public boolean rotate(int n) {
        if (n <= 0 || nextEmpty <= 1) {
            return false;
        }

        n %= nextEmpty;
        reverse();
        T[] first = Arrays.copyOfRange(arrayList, 0, n);
        T[] second = Arrays.copyOfRange(arrayList, n, nextEmpty);
        System.arraycopy(second, 0, arrayList, 0, second.length);
        System.arraycopy(first, 0, arrayList, second.length, first.length);
        isSorted = false;

        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nextEmpty; i++) {
            sb.append(arrayList[i].toString());
            if (i < nextEmpty - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    @Override
    public boolean isSorted() {
        return isSorted;
    }
}
