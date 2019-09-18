package edu.emory.cs.queue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class TernaryHeap<T extends Comparable<T>> extends AbstractPriorityQueue<T> {
    public List<T> keys;

    public TernaryHeap(Comparator<T> comparator) {
        super(comparator);
        keys = new ArrayList<>();
        keys.add(null);    // initialize the first item as null
    }

    public TernaryHeap() {
        this(Comparator.naturalOrder());
    }

    @Override
    public int size() {
        return keys.size() - 1;
    }

    @Override
    public void add(T key) {
        keys.add(key);
        swim(size());
    }

    @Override
    public T remove() {
        if (isEmpty()) return null;
        Collections.swap(keys, 1, size());
        T max = keys.remove(size());
        sink(1);
        return max;
    }


    //Check over with TA
    public void swim(int k) {
        while (1 < k && comparator.compare(keys.get(((k+3)-2)/3), keys.get(k)) < 0) {
            Collections.swap(keys, ((k+3)-2)/3, k);
            k = ((k+3)-2)/3;
        }
    }



    public void sink(int k) {
        for (int i=3*k; i<=size(); k=i,i*=3)
        {
            if(i>1 && keys.get(i)!=null && comparator.compare(keys.get(i), keys.get(i-1)) <=0)
            {
                i--;
                if(i>1 && i<size()-1 && keys.get(i)!=null && comparator.compare(keys.get(i), keys.get(i+2))<=0)
                {
                    i+=2;
                }
            }
            else if(i>1 && i<size() && keys.get(i)!=null && comparator.compare(keys.get(i), keys.get(i+1))<=0)
            {
                i++;
            }

            if (comparator.compare(keys.get(k), keys.get(i)) >= 0)
            {
                break;
            }

            Collections.swap(keys, k, i);


        }

    }
}
