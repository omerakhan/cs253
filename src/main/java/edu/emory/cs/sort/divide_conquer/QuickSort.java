/**
 * Copyright 2014, Emory University
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.emory.cs.sort.divide_conquer;

import edu.emory.cs.sort.AbstractSort;

import java.util.Comparator;

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class QuickSort<T extends Comparable<T>> extends AbstractSort<T> {
    public QuickSort() {
        this(Comparator.naturalOrder());
    }

    public QuickSort(Comparator<T> comparator) {
        super(comparator);
    }



    public void sort(T[] array, int beginIndex, int endIndex) {
        // at least one key in the range
        if (beginIndex >= endIndex) return;


        int pivotIndex = partition(array, beginIndex, endIndex);
        // sort left partition
        sort(array, beginIndex, pivotIndex);
        // sort right partition
        sort(array, pivotIndex + 1, endIndex);
    }

    protected void InsertionSort(T[] array, int beginIndex, int endIndex, final int h) {
        int begin_h = beginIndex + h;

        for (int i = begin_h; i < endIndex; i++)
            for (int j = i; j >= begin_h && compareTo(array, j, j - h) < 0; j -= h)
                swap(array, j, j - h);
    }


    protected int partition(T[] array, int beginIndex, int endIndex) {
        int fst = beginIndex, snd = endIndex;

        if(endIndex-beginIndex<10)
        {
            InsertionSort(array,beginIndex,endIndex,1);
        }

        while (true) {
            // Find where endIndex > fst > pivot
            while (++fst < endIndex && compareTo(array, beginIndex, fst) >= 0);
            // Find where beginIndex < snd < pivot
            while (--snd > beginIndex && compareTo(array, beginIndex, snd) <= 0);
            // pointers crossed
            if (fst >= snd) break;
            // exchange
            swap(array, fst, snd);
        }

        swap(array, beginIndex, snd);
        return snd;
    }
}