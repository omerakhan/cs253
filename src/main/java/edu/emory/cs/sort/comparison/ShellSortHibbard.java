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
package edu.emory.cs.sort.comparison;

import java.util.Collections;
import java.util.Comparator;

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class ShellSortHibbard<T extends Comparable<T>> extends ShellSort<T> {
    public ShellSortHibbard() {
        this(Comparator.naturalOrder());
    }

    public ShellSortHibbard(Comparator<T> comparator) {
        this(comparator, 1000);
    }

    public ShellSortHibbard(Comparator<T> comparator, int n) {
        super(comparator, n);
    }

    @Override
    protected void populateSequence(int n) {
        n /= 3;
        //System.out.println("This is n "+n);
        for (int t = sequence.size() + 1; ; t++) {
            int h = (int) ((Math.pow(2, t) - 1));
            if (h <= n)
            {
                sequence.add(h);
                //System.out.println(sequence);
            }
            else break;
        }
    }

    @Override
    protected int getSequenceStartIndex(int n) {
        int index = Collections.binarySearch(sequence, n / 3);
        if (index < 0) index = -(index + 1);
        if (index == sequence.size()) index--;
        return index;
    }
}