/*
 * Copyright 2014, Emory University
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.emory.cs.sort.hybrid;

import edu.emory.cs.sort.AbstractSort;
import edu.emory.cs.sort.comparison.HeapSort;
import edu.emory.cs.sort.comparison.InsertionSort;
import edu.emory.cs.sort.comparison.ShellSortHibbard;
import edu.emory.cs.sort.comparison.ShellSortKnuth;
import edu.emory.cs.sort.divide_conquer.IntroSort;
import edu.emory.cs.sort.divide_conquer.MergeSort;
import edu.emory.cs.sort.divide_conquer.QuickSort;
import org.w3c.dom.Node;

import java.lang.reflect.Array;
import java.util.*;

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class HybridSortKhan<T extends Comparable<T>> implements HybridSort<T> {
    private AbstractSort<T> engineQuick;
    private AbstractSort<T> engineInsertion;
    private AbstractSort<T> engineMerge;
    private AbstractSort<T> engineHibbard;
    private AbstractSort<T> engineHeap;
    private AbstractSort<T> engineIntro;




    public HybridSortKhan() {
        engineQuick = new QuickSort<>();
        engineInsertion = new InsertionSort<>();
        engineMerge = new MergeSort<>();
        engineHibbard = new ShellSortKnuth<>();
        engineHeap = new HeapSort<>();
        engineIntro = new IntroSort<>(engineHibbard);
    }



    @Override
    @SuppressWarnings("unchecked")
    public T[] sort(T[][] input) {

        for (int k = 0; k < input.length; k++) {
            boolean sortedUp = true;
            boolean sortedDown = true;
            int asCounter = 0;
            T temp;
            T[] xy = input[k];
            int x = input[k].length-1;
            /*if(x>20)
            {
                x = input[k].length/2;
            }*/
            for (int i = 0; i<xy.length-1; i++) {
                if (xy[i].compareTo(xy[i + 1]) > 0) {
                    asCounter--;
                    sortedUp = false;
                }
                if (xy[i].compareTo(xy[i + 1]) < 0)
                {
                    asCounter++;
                    sortedDown = false;
                }
            }
            if(sortedUp) //if sorted ascendingly
            {
                continue;
            }
            if(sortedDown) //if sorted descendingly
            {
                for(int i = 0;i<input[k].length/2;i++)
                {
                    temp = input[k][i];
                    input[k][i] = input[k][input[k].length-1-i];
                    input[k][input[k].length-1-i] = temp;  //reverse array

                }
                continue;
            }

            else if(asCounter>=(0.63*x))
            {
                engineInsertion.sort(input[k]); //merge
                continue;
            }
            else if(asCounter <= (-0.75*x))
            {
                engineHibbard.sort(input[k]);  //knuth
                continue;
            }
            else
            {
                engineQuick.sort(input[k]); //quick

            }

        }
        //outside of for loop
        //merging arrays
        PriorityQueue<NodeClass<T>> tester = new PriorityQueue<NodeClass<T>>(); //create priority queue
        int total = 0;
        for(int i = 0;i<input.length;i++)
        {
            total+=input[i].length;             //keep track of total to determine output array size
            if(input[i].length>0)
            {
                tester.add(new NodeClass<>(i,0,input[i][0]));  //add first element of each array (smallest)
            }
        }
        T[] output = (T[])Array.newInstance(input[0][0].getClass(), total);
        for(int i = 0;!tester.isEmpty();i++)
        {
            NodeClass n = tester.poll();            //remove w/ poll
            output[i] = (T) n.value;
            int newIndex = n.i+1;
            if(newIndex<input[n.array].length)  //keep track of index
            {
                tester.add(new NodeClass<>(n.array,newIndex,input[n.array][newIndex]));
            }

        }

        return output;
    }
}//end of class


//helper class from textbook
//make sure compareTo method is implemented properly

class NodeClass<T extends Comparable<T>> implements Comparable<NodeClass<T>> {
    int array;
    int i;
    T value;

    public NodeClass(int array, int i, T value)
    {
        this.array = array;
        this.i = i;
        this.value = value;
    }


    @Override
    public int compareTo(NodeClass<T> n) {
        if(value.compareTo(n.value) > 0) return 1;
        if(value.compareTo(n.value)<0) return -1;
        return 0;
    }



}