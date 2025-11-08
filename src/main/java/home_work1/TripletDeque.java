package home_work1;

import java.util.*;
import java.util.Collection;

public class TripletDeque<E> implements Deque<E>, Containerable {

    private static class Container<T> {
        Object[] data;
        Container<T> next;
        Container<T> prev;
        int count;

        Container(int capacity) {
            data = new Object[capacity];
            count = 0;
        }

        void addFirst(T element) {
            for (int i = count; i > 0; i--) {
                data[i] = data[i-1];
            }
            data[0] = element;
            count++;
        }

        void addLast(T element) {
            data[count] = element;
            count++;
        }

        T removeFirst() {
            if (count == 0) {
                throw new NoSuchElementException();
            }
            T element = (T) data[0];
            for (int i = 0; i < count - 1; i++) {
                data[i] = data[i+1];
            }
            data[count-1] = null;
            count--;
            return element;
        }

        T removeLast() {
            if (count == 0) {
                throw new NoSuchElementException();
            }
            T element = (T) data[count-1];
            data[count-1] = null;
            count--;
            return element;
        }

        T getFirst() {
            if (count == 0) {
                throw new NoSuchElementException();
            }
            return (T) data[0];
        }

        T getLast() {
            if (count == 0) {
                throw new NoSuchElementException();
            }
            return (T) data[count-1];
        }

        boolean isFull() {
            return count == data.length;
        }

        boolean isEmpty() {
            return count == 0;
        }

        int findElement(Object o) {
            for (int i = 0; i < count; i++) {
                if (Objects.equals(o, data[i])) {
                    return i;
                }
            }
            return -1;
        }

        T removeAt(int index) {
            T element = (T) data[index];
            for (int i = index; i < count - 1; i++) {
                data[i] = data[i+1];
            }
            data[count-1] = null;
            count--;
            return element;
        }
    }

    private Container<E> firstContainer;
    private Container<E> lastContainer;
    private int containerCapacity;
    private int maxSize;
    private int size;

    public TripletDeque() {
        this(1000, 5);
    }

    public TripletDeque(int maxSize) {
        this(maxSize, 5);
    }

    public TripletDeque(int maxSize, int containerCapacity) {
        this.maxSize = maxSize;
        this.containerCapacity = containerCapacity;
        this.size = 0;
        firstContainer = new Container<>(containerCapacity);
        lastContainer = firstContainer;
    }

    @Override
    public Object[] getContainerByIndex(int index) {
        Container<E> current = firstContainer;
        for (int i = 0; i < index; i++) {
            if (current == null) {
                return null;
            }
            current = current.next;
        }
        if (current != null) {
            return current.data;
        } else {
            return null;
        }
    }

    @Override
    public void addFirst(E e) {
        if (e == null) {
            throw new NullPointerException();
        }
        if (size >= maxSize) {
            throw new IllegalStateException("Deque is full");
        }

        if (firstContainer.isFull()) {
            Container<E> newContainer = new Container<>(containerCapacity);
            newContainer.next = firstContainer;
            firstContainer.prev = newContainer;
            firstContainer = newContainer;
        }

        firstContainer.addFirst(e);
        size++;
    }

    @Override
    public void addLast(E e) {
        if (e == null) {
            throw new NullPointerException();
        }
        if (size >= maxSize) {
            throw new IllegalStateException("Очередь заполнена!");
        }

        if (lastContainer.isFull()) {
            Container<E> newContainer = new Container<>(containerCapacity);
            newContainer.prev = lastContainer;
            lastContainer.next = newContainer;
            lastContainer = newContainer;
        }

        lastContainer.addLast(e);
        size++;
    }

    @Override
    public boolean offerFirst(E e) {
        if (e == null) {
            return false;
        }
        try {
            addFirst(e);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public boolean offerLast(E e) {
        if (e == null) {
            return false;
        }
        try {
            addLast(e);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public E removeFirst() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        E element = firstContainer.removeFirst();
        size--;
        if (firstContainer.isEmpty() && firstContainer.next != null) {
            firstContainer = firstContainer.next;
            firstContainer.prev = null;
        }
        return element;
    }

    @Override
    public E removeLast() {
        if (size == 0) {
            throw new NoSuchElementException();
        }

        E element = lastContainer.removeLast();
        size--;

        if (lastContainer.isEmpty() && lastContainer.prev != null) {
            lastContainer = lastContainer.prev;
            lastContainer.next = null;
        }

        return element;
    }

    @Override
    public E pollFirst() {
        if (size == 0) {
            return null;
        } else {
            return removeFirst();
        }
    }

    @Override
    public E pollLast() {
        if (size == 0) {
            return null;
        } else {
            return removeLast();
        }
    }

    @Override
    public E getFirst() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return firstContainer.getFirst();
    }

    @Override
    public E getLast() {
        if (size == 0) {
            throw new NoSuchElementException();
        }
        return lastContainer.getLast();
    }

    @Override
    public E peekFirst() {
        if (size == 0) {
            return null;
        } else {
            return firstContainer.getFirst();
        }
    }

    @Override
    public E peekLast() {
        if (size == 0) {
            return null;
        } else {
            return lastContainer.getLast();
        }
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        if (o == null) {
            return false;
        }

        Container<E> current = firstContainer;
        while (current != null) {
            int index = current.findElement(o);
            if (index != -1) {
                current.removeAt(index);
                size--;

                if (current.isEmpty()) {
                    removeContainer(current);
                }
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        if (o == null) {
            return false;
        }

        Container<E> current = lastContainer;
        while (current != null) {
            int index = current.findElement(o);
            if (index != -1) {
                current.removeAt(index);
                size--;

                if (current.isEmpty()) {
                    removeContainer(current);
                }
                return true;
            }
            current = current.prev;
        }
        return false;
    }

    private void removeContainer(Container<E> container) {
        if (container.prev != null) {
            container.prev.next = container.next;
        }
        if (container.next != null) {
            container.next.prev = container.prev;
        }
        if (container == firstContainer) {
            firstContainer = container.next;
        }
        if (container == lastContainer) {
            lastContainer = container.prev;
        }

        if (firstContainer == null) {
            firstContainer = new Container<>(containerCapacity);
            lastContainer = firstContainer;
        }
    }

    @Override
    public boolean add(E e) {
        addLast(e);
        return true;
    }

    @Override
    public boolean offer(E e) {
        return offerLast(e);
    }

    @Override
    public E remove() {
        return removeFirst();
    }

    @Override
    public E poll() {
        return pollFirst();
    }

    @Override
    public E element() {
        return getFirst();
    }

    @Override
    public E peek() {
        return peekFirst();
    }

    @Override
    public void push(E e) {
        addFirst(e);
    }

    @Override
    public E pop() {
        return removeFirst();
    }

    @Override
    public boolean remove(Object o) {
        return removeFirstOccurrence(o);
    }

    @Override
    public boolean contains(Object o) {
        if (o == null) {
            return false;
        }

        Container<E> current = firstContainer;
        while (current != null) {
            if (current.findElement(o) != -1) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Container<E> currentContainer = firstContainer;
            private int currentIndex = 0;
            private int elementsReturned = 0;

            @Override
            public boolean hasNext() {
                return elementsReturned < size;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                while (currentIndex >= currentContainer.count) {
                    currentContainer = currentContainer.next;
                    currentIndex = 0;
                    if (currentContainer == null) {
                        throw new NoSuchElementException();
                    }
                }

                E element = (E) currentContainer.data[currentIndex];
                currentIndex++;
                elementsReturned++;
                return element;
            }
        };
    }

    @Override
    public Iterator<E> descendingIterator() {
        throw new UnsupportedOperationException("Метод descendingIterator не реализован");
    }

    @Override
    public void clear() {
        firstContainer = new Container<>(containerCapacity);
        lastContainer = firstContainer;
        size = 0;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E e : c) {
            addLast(e);
        }
        return true;
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        Iterator<E> it = iterator();
        for (int i = 0; i < size; i++) {
            array[i] = it.next();
        }
        return array;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            a = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
        }

        Iterator<E> it = iterator();
        for (int i = 0; i < size; i++) {
            a[i] = (T) it.next();
        }

        if (a.length > size) {
            a[size] = null;
        }

        return a;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean changed = false;
        for (Object o : c) {
            while (remove(o)) {
                changed = true;
            }
        }
        return changed;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean changed = false;
        Iterator<E> it = iterator();
        while (it.hasNext()) {
            E element = it.next();
            if (!c.contains(element)) {
                it.remove();
                changed = true;
            }
        }
        return changed;
    }
}