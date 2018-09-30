package ru;

import java.util.*;

public class AnotherArrayList<T> implements List<T> {
    private T innerArray[];
    private int size = 0;
    private static final int DEFAULT_CAPACITY = 10;

    public AnotherArrayList() {
        innerArray = createNewInnerArray(DEFAULT_CAPACITY);
    }

    public AnotherArrayList(int capacity) {
        innerArray = createNewInnerArray(capacity);
    }

    private T[] createNewInnerArray(int n) {
        return (T[]) new Object[n];
    }

    public int size() {
        return size;
    }

    public boolean add(T element) {
        if (size == innerArray.length) {
            increaseSizeInnerArray();
        }
        innerArray[size++] = element;
        return true;
    }

    private void increaseSizeInnerArray() {
        T[] tmpInnerArray = (T[]) new Object[size * 2];
        System.arraycopy(innerArray, 0, tmpInnerArray, 0, size);
        innerArray = tmpInnerArray;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        size = 0;
        innerArray = createNewInnerArray(DEFAULT_CAPACITY);

    }

    public T get(int index) {
        return innerArray[index];
    }

    public boolean contains(Object o) {
        return indexOf(o) > -1;
    }

    public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++) {
                if (innerArray[i] == null) return i;
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (innerArray[i].equals(o)) return i;
            }
        }
        return -1;
    }

    public boolean addAll(Collection<? extends T> c) {
        T[] t = (T[]) c.toArray();
        if (t.length == 0) {
            return false;
        }
        if ((t.length + size) > innerArray.length) {
            T[] newInnerArray = createNewInnerArray(t.length + size);
            System.arraycopy(innerArray, 0, newInnerArray, 0, size);
            System.arraycopy(t, 0, newInnerArray, size, t.length);
            size = newInnerArray.length;
            innerArray = newInnerArray;
        } else {
            System.arraycopy(t, 0, innerArray, size, t.length);
            size = t.length + size;
        }
        return true;
    }

    public T[] toArray() {
        T[] arr = (T[]) new Object[size];
        System.arraycopy(innerArray, 0, arr, 0, size);
        return arr;
    }

    public void add(int index, T element) {
        throw new UnsupportedOperationException("This method has't implementation");
    }

    public void sort(Comparator<? super T> comparator) {
        Arrays.sort(innerArray, 0, size, comparator);
    }

    public Iterator<T> iterator() {
        throw new UnsupportedOperationException("This method has't implementation");
    }

    public <T1> T1[] toArray(T1[] a) {
        throw new UnsupportedOperationException("This method has't implementation");
    }

    public boolean remove(Object o) {
        throw new UnsupportedOperationException("This method has't implementation");
    }

    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException("This method has't implementation");
    }

    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException("This method has't implementation");
    }

    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("This method has't implementation");
    }

    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("This method has't implementation");
    }

    public T set(int index, T element) {
        Objects.checkIndex(index, size);
        T oldValue = innerArray[index];
        innerArray[index] = element;
        return oldValue;
    }

    public T remove(int index) {
        throw new UnsupportedOperationException("This method has't implementation");
    }

    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException("This method has't implementation");
    }

    public ListIterator<T> listIterator() {
        return new AnotherListIterator(0);
    }

    public ListIterator<T> listIterator(int index) {
        return new AnotherListIterator(index);
    }

    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("This method has't implementation");
    }

    private class AnotherListIterator implements ListIterator<T> {
        private int pointer;

        public AnotherListIterator(int i) {
            pointer = i;
        }

        @Override
        public boolean hasNext() {
            return pointer < size;
        }

        @Override
        public T next() {
            if (!hasNext()) throw new NoSuchElementException();
            return (T) innerArray[pointer++];
        }

        @Override
        public boolean hasPrevious() {
            return pointer - 1 != -1;
        }

        @Override
        public void set(T t) {
            if (!hasPrevious()) throw new IllegalStateException();
            innerArray[pointer - 1] = t;

        }

        @Override
        public T previous() {
            if (pointer == 0) throw new NoSuchElementException();
            return innerArray[--pointer];
        }

        @Override
        public int nextIndex() {
            throw new UnsupportedOperationException("This method has't implementation");
        }

        @Override
        public int previousIndex() {
            throw new UnsupportedOperationException("This method has't implementation");
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("This method has't implementation");
        }

        @Override
        public void add(T t) {
            throw new UnsupportedOperationException("This method has't implementation");
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
