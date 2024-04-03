package ru.mpei;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

public class TripletDeque <E> implements Deque<E>,Containerable {
    private Container<E> first;
    private Container<E> last;
    private int siz = 0;
    private int arraysiz;


    public TripletDeque(){
        Container<E> container = new Container<>(null, null, 5);
        this.first = container;
        this.last = container;
        this.arraysiz= 5;
    }

    public TripletDeque(int arraysiz){
        Container<E> zerocontainer = new Container<>(null, null, arraysiz);
        this.first = zerocontainer;
        this.last = zerocontainer;
        this.arraysiz = arraysiz;

    }
    public void conclusion(){
        boolean flag = true;
        System.out.println("Количество элементов в коллекции = " + siz);
        while (flag){
            if (first != null){
                System.out.println(first);
                first = first.next;
            }
            else {
                flag = false;
            }
        }
    }

    @Override
    public void addFirst(E e) {
        if (e == null) {
            throw new NullPointerException();
        } else if (siz >= 1000) {
            throw new IllegalStateException();
        }else {
            if (first.size() < 5) {
                Container<E> container = new Container<>(first.getNext(),null,arraysiz);
                container.add(e);
                container.addAll(first.getarray());
                if (first.equals(last)) {
                    last = container;
                }
                if (first.getNext() != null) {
                    first.getNext().setPrev(container);
                }
                first = container;
                siz++;
            } else {
                Container<E> container = new Container<>(first,null,arraysiz);
                first.setPrev(container);
                first = container;
                addFirst(e);
            }
        }
    }


    @Override
    public void addLast(E e) {
        if (e == null) {
            throw new NullPointerException();
        } else if (siz >= 1000) {
            throw new IllegalStateException();
        } else {
            if (last.size() < 5) {
                last.add(e);
                siz++;
            } else {
                Container<E> container = new Container<>(null, last, arraysiz);
                last.setNext(container);
                last = container;
                addLast(e);
            }
        }

    }

    @Override
    public boolean offerFirst(E e) {
        int flag = siz;
        try {
            addFirst(e);
        } catch (IllegalStateException k){
            return false;
        }
        if (flag != siz){
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public boolean offerLast(E e) {
        int flag = siz;
        try {
            addLast(e);
        } catch (IllegalStateException k) {
            return false;
        }
        if (flag != siz) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public E removeFirst() {
        if (siz == 0) {
            throw new NoSuchElementException();
        }
        E e = first.get(0);
        if (first.size() == 1 && first.getNext() != null) {
            first.getNext().setPrev(null);
            first = first.getNext();
        } else {
            Container<E> container = new Container<>(first.getNext(), null, arraysiz);
            for (int i = 1; i < first.size(); i++) {
                container.add(first.get(i));
            }
            if (first.getNext() == null) {
                last = container;
            } else {
                first.getNext().setPrev(container);
            }
            first = container;
        }
        siz--;
        return e;
    }


    @Override
    public E removeLast() {
        if (siz == 0) {
            throw new NoSuchElementException();
        }
        E e = last.get(last.size() - 1);
        if (last.size() == 1 && last.getPrev() != null) {
            last.getPrev().setNext(null);
            last = last.getPrev();
        } else {
            Container<E> container = new Container<>(null, last.getPrev(),arraysiz);
            for (int i = 0; i < last.size() - 1; i++) {
                container.add(last.get(i));
            }
            if (last.getPrev() == null) {
                first = container;
            } else {
                last.getPrev().setNext(container);
            }
            last = container;
        }
        siz--;
        return e;
    }

    @Override
    public E pollFirst() {
        int flag = siz;
        try {
            removeFirst();
        } catch (NoSuchElementException k) {
            return null;
        }
        if (flag != siz) {
            return removeFirst();
        } else {
            return null;
        }
    }


    @Override
    public E pollLast() {
        int flag = siz;
        try {
            removeLast();
        } catch (NoSuchElementException k) {
            return null;
        }
        if (flag != siz) {
            return removeLast();
        } else {
            return null;
        }
    }

    @Override
    public E getFirst() {
        if (first!=null){
            int j =0;
            for (int i = 0; i <= 4; i++) {
                if (first.array[i] != null){
                    return (E) first.array[i];
                } else{
                    j++;
                }
            }
            if (j>=4){

                if (first.next == null){
                    throw new NoSuchElementException();
                } else{
                    for (int i = 0; i <= 4; i++) {
                        if (first.next.array[i] != null){
                            return (E) first.next.array[i];
                        }
                    }
                }
            }
        } else {
            throw new NoSuchElementException();
        }
        return null;
    }

    @Override
    public E getLast() {
        if (last!=null){
            int j =0;
            for (int i = 4; i >= 0; i--) {
                if (last.array[i] != null){
                    return (E) last.array[i];
                } else{
                    j++;
                }
            }
            if (j>=4){
                if (last.prev == null){
                    throw new NoSuchElementException();
                } else{
                    for (int i = 4; i >= 0; i--) {
                        if (last.prev.array[i] != null){
                            return (E) last.prev.array[i];

                        }
                    }
                }
            }
        } else {
            throw new NoSuchElementException();
        }
        return null;
    }

    @Override
    public E peekFirst() {
        try {
            return getFirst();
        } catch (NoSuchElementException k) {
            return null;
        }
    }

    @Override
    public E peekLast() {
        try {
            return getLast();
        } catch (NoSuchElementException k) {
            return null;
        }
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        if (isEmpty()) {
            return false;
        }
        if (o == null) {
            throw new NullPointerException();
        }
        boolean flag = true;
        Container<E> container = new Container<>(null, null,siz);
        for (Container<E> e = first; e != null; e = e.getNext()) {
            for (E t : e.getarray()) {
                if (o.equals(t) && flag) {
                    flag = false;
                    siz--;
                } else {
                    container.add(t);
                }
            }
        }
        overwriteElements(container);
        if (last.size() == 0) {
            Container<E> previous = last.getPrev();
            previous.setNext(null);
            last.setPrev(null);
            last = previous;
        }
        return !flag;

    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        if (siz == 0) {
            return false;
        }
        if (o == null) {
            throw new NullPointerException();
        } else {
            boolean flag = true;
            Container<E> container = new Container<>(null, null, siz);
            for (Container<E> e = last; e != null; e = e.getPrev()) {
                for (int i = e.size() - 1; i > -1; i--) {
                    if (o.equals(e.getarray()[i]) && flag) {
                        flag = false;
                        siz--;
                    } else {
                        container.add(e.getarray()[i]);
                    }
                }
            }
            overwriteElements(container);
            if (last.size() == 0) {
                Container<E> previous = last.getPrev();
                previous.setNext(null);
                last.setPrev(null);
                last = previous;
            }
            return !flag;
        }
    }

    @Override
    public boolean add(E e) {
        int flag = siz;
        addLast(e);
        if (siz != flag){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean offer(E e) {
        int flag = siz;
        offerLast(e);
        if (siz != flag){
            return true;
        } else {
            return false;
        }
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
    public boolean addAll(Collection<? extends  E> c) {
        for (E e: c) {
            addLast(e);
        }
        return true;
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
            throw new NullPointerException();
        } else{
            for (Container<E> e = first; e != null; e = e.getNext()) {
                for (Object obj : e.getarray()) {
                    if (o.equals(obj)) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    @Override
    public int size() {
        return siz;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            int iterslider = 0;
            Container<E> container = first;

            @Override
            public boolean hasNext() {
                if (iterslider < container.size()) {
                    return true;
                }
                if (iterslider == container.size()) {
                    return container.getNext() != null;
                }
                return false;
            }

            @Override
            public E next() {
                if (iterslider == container.size()) {
                    if (container.getNext() != null) {
                        iterslider -= container.size();
                        container = container.getNext();
                        return next();
                    } else {
                        throw new NoSuchElementException();
                    }
                } else {
                    iterslider++;
                    return container.get(iterslider-1);
                }
            }
        };
    }

    @Override
    public Iterator<E> descendingIterator() {
        throw new UnsupportedOperationException();
    }

    private void overwriteElements(Container<E> container) {
        int j = 0;
        for (Container<E> e = first; e != null; e = e.getNext()) {
            for (int i = 0; i < e.size(); i++) {
                if (i + j < container.size()) {
                    e.getarray()[i] = container.get(i + j);
                } else {
                    e.getarray()[i] = null;
                }
            }
            j += e.size();
        }
    }
    @Override
    public Object[] getContainerByIndex(int cIndex) {
        int i = 0;
        for (Container<E> e = first; e != null; e = e.getNext()) {
            if (i == cIndex) {
                return e.getarray();
            }
            i++;
        }
        return null;
    }
    @Override
    public boolean isEmpty() {
        return siz == 0;
    }

    //TODO Не реализованные!!!

    @Override
    public void clear() {

    }

    @Override
    public boolean retainAll(Collection c) {
        return false;
    }
    @Override
    public boolean removeAll(Collection c) {
        return false;
    }
    @Override
    public boolean containsAll(Collection c) {
        return false;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public Object[] toArray(Object[] a) {
        return new Object[0];
    }
    //TODO Обёртка, перенести в отдельный класс
    @Data
    private class Container<E> {

        private Container<E> next;
        private Container<E> prev;
        private Object[] array;

        public Container(Container<E> next, Container<E> prev, int arraysiz) {
            this.next = next;
            this.prev = prev;
            this.array = new Object[arraysiz];
        }
        public E get(int i) {
            return (E) array[i];
        }
        public void addAll(E[] elements) {
            for (E e : elements) {
                add(e);
            }
        }
        public int size() {
            int size = 0;
            for (Object o : array) {
                if (o != null) {
                    size++;
                }
            }
            return size;
        }
        public E[] getarray() {
            return (E[]) array;
        }
        public void add(Object o) {
            if (o != null) {
                int i = 0;
                boolean flag = true;
                while (flag) {
                    if (array[i] == null) {
                        array[i] = o;
                        flag = false;
                    }
                    i++;
                }
            }
        }
        @Override
        public String toString() {
            return "ArrayWrap " +
                    "array = " + Arrays.toString(array) +
                    ' ';
        }
    }
}
