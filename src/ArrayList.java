
public class ArrayList<T extends Comparable<T>> implements List<T> {

    private T[] elements;
    private int count;
    private boolean isSorted;

    // Constructor initializes the ArrayList and sets isSorted to true.
    public ArrayList() {
        elements = (T[]) new Comparable[2];
        count = 0;
        isSorted = true;
    }

    // Checks if the ArrayList is sorted and returns the result.
    private boolean checkIsSorted() {
        boolean check = true;
        for (int k = 1; k < count; k++) {
            check &= elements[k].compareTo(elements[k - 1]) >= 0;
        }
        return check;
    }

    // Increases the size of the elements array when needed.
    private void grow() {
        T[] updatedElements = (T[]) new Comparable[elements.length * 2];
        for (int i = 0; i < count; i++) {
            updatedElements[i] = elements[i];
        }
        elements = updatedElements;
    }

    // Adds a value to the end of the ArrayList.
    public boolean add(T value) {
        if (value == null) {
            return false;
        }
        if (count == elements.length) {
            grow();
        }
        if (count > 0 && isSorted && value.compareTo(elements[count - 1]) < 0) {
            isSorted = false;
        }
        elements[count++] = value;
        isSorted = checkIsSorted();
        return true;
    }

    // Inserts a value at a specific position in the ArrayList.
    public boolean add(int position, T value) {
        if (value == null || position < 0 || position >= count) {
            return false;
        }
        if (count == elements.length) {
            grow();
        }

        for (int i = count; i > position; i--) {
            elements[i] = elements[i - 1];
        }
        elements[position] = value;
        count++;

        if (isSorted) {
            if ((position > 0 && value.compareTo(elements[position - 1]) < 0) ||
                    (position < count - 1 && value.compareTo(elements[position + 1]) > 0)) {
                isSorted = false;
            }
        }
        isSorted = checkIsSorted();
        return true;
    }


    // Clears the ArrayList and resets isSorted to true.
    public void clear() {
        elements = (T[]) new Comparable[2];
        count = 0;
        isSorted = true;
    }

    // Returns the element at a specific position.
    public T get(int position) {
        if (position < 0 || position >= count) {
            return null;
        }
        return elements[position];
    }

    // Finds the first index of a value in the ArrayList.
    public int indexOf(T value) {
        if (value == null) {
            return -1;
        }

        int index = -1;

        for (int i = 0; i < count; i++) {
            if (elements[i].equals(value)) {
                if (isSorted) {
                    if (i == 0 || !elements[i - 1].equals(value)) {
                        return i;
                    }
                } else {
                    return i;
                }
                index = i;
            }
        }

        return index;
    }

    // Checks if the ArrayList is empty.
    public boolean isEmpty() {
        return count == 0;
    }

    // Returns the number of elements in the ArrayList.
    public int size() {
        return count;
    }

    // Sorts the ArrayList using insertion sort.
    public void sort() {
        if (!isSorted) {
            for (int i = 1; i < count; i++) {
                T currentElement = elements[i];
                int prevIndex = i - 1;
                while (prevIndex >= 0 && elements[prevIndex].compareTo(currentElement) > 0) {
                    elements[prevIndex + 1] = elements[prevIndex];
                    prevIndex--;
                }
                elements[prevIndex + 1] = currentElement;
            }
            isSorted = true;
        }
    }

    // Removes the element at a specific position.
    public T remove(int position) {
        if (position < 0 || position >= count) {
            return null;
        }
        T removedElement = elements[position];

        for (int i = position; i < count - 1; i++) {
            elements[i] = elements[i + 1];
        }

        count--;

        if (isSorted && position > 1 && position < count) {
            T prevElement = elements[position - 1];
            T currentElement = elements[position];

            if (currentElement.compareTo(prevElement) < 0) {
                isSorted = false;
            }
        }
        isSorted = checkIsSorted();
        return removedElement;
    }

    // Removes all elements except those equal to the given value.
    public void equalTo(T value) {
        if (value == null) {
            count = 0;
            return;
        }
        int newCount = 0;
        if (isSorted) {
            int pos = indexOf(value);
            if (pos != -1) {
                while (pos < count && elements[pos].equals(value)) {
                    elements[newCount++] = elements[pos++];
                }
            }
        } else {
            for (int i = 0; i < count; i++) {
                if (elements[i].equals(value)) {
                    elements[newCount++] = elements[i];
                }
            }
        }
        count = newCount;
        isSorted = checkIsSorted();
    }

    // Reverses the order of elements in the ArrayList.
    public void reverse() {
        int leftIndex = 0;
        int rightIndex = count - 1;

        while (leftIndex < rightIndex) {
            T temp = elements[leftIndex];
            elements[leftIndex] = elements[rightIndex];
            elements[rightIndex] = temp;

            leftIndex++;
            rightIndex--;
        }

        if (count > 1) {
            isSorted = false;
        }
        isSorted = checkIsSorted();
    }

    // Merges the current ArrayList with another sorted list.
    public void merge(List<T> otherList) {
        if (otherList == null || otherList.isEmpty()) {
            return;
        }

        ArrayList<T> other = (ArrayList<T>) otherList;
        sort();
        other.sort();

        T[] newData = (T[]) new Comparable[count + other.size()];
        int indexThis = 0;
        int indexOther = 0;
        int indexNew = 0;

        while (indexThis < count && indexOther < other.size()) {
            if (elements[indexThis].compareTo(other.elements[indexOther]) <= 0) {
                newData[indexNew++] = elements[indexThis++];
            } else {
                newData[indexNew++] = other.elements[indexOther++];
            }
        }

        while (indexThis < count) {
            newData[indexNew++] = elements[indexThis++];
        }

        while (indexOther < other.size()) {
            newData[indexNew++] = other.elements[indexOther++];
        }

        elements = newData;
        count = count + other.size();
        isSorted = true;
    }

    // Rotates the ArrayList by a given number of positions.
    public boolean rotate(int n) {
        if (n <= 0 || count <= 1) {
            return false;
        }
        n %= count;

        int start = 0;
        int end = count - n - 1;
        while (start < end) {
            T temp = elements[start];
            elements[start++] = elements[end];
            elements[end--] = temp;
        }

        start = count - n;
        end = count - 1;
        while (start < end) {
            T temp = elements[start];
            elements[start++] = elements[end];
            elements[end--] = temp;
        }

        start = 0;
        end = count - 1;
        while (start < end) {
            T temp = elements[start];
            elements[start++] = elements[end];
            elements[end--] = temp;
        }
        if (count > 1) {
            isSorted = false;
        }
        isSorted = checkIsSorted();
        return true;
    }

    // Returns true if the ArrayList is sorted, false otherwise.
    public boolean isSorted() {
        return isSorted;
    }
}



















//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < nextEmpty; i++) {
//            sb.append(arrayList[i].toString());
//            if (i < nextEmpty - 1) {
//                sb.append("\n");
//            }
//        }
//        return sb.toString();
//    }




















