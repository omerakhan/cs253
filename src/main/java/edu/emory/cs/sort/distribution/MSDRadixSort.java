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
package edu.emory.cs.sort.distribution;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class MSDRadixSort extends RadixSort {
    @Override
    public void sort(Integer[] array, int beginIndex, int endIndex) {
        int maxBit = getMaxBit(array, beginIndex, endIndex);
        List<Integer> arr = new ArrayList<>();
        arr.add(0, 0);
        for (int bit = maxBit-1; bit >=0; bit--) {
            int div = (int) Math.pow(10, bit);
            if(bit <= maxBit-1 && bit>0)
            {
                sort(array, beginIndex, endIndex, k -> k / div);
                for (int i = 1; i < array.length; i++) {
                    int x = array[i] / div;
                    int y = array[i - 1] / div;
                    if (x != y) {
                        arr.add(i);
                    }

                }
            }
            for(int i = 0;i<arr.size();i++)
            {
                if(i==arr.size()-1)
                {
                    sort(array, arr.get(i), endIndex, k -> k / div);
                }
                else
                {
                    sort(array, arr.get(i), arr.get(i+1), k -> k / div);
                }
            }
        }
    }

}
