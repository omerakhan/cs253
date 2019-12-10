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
package edu.emory.cs.trie.autocomplete;

import edu.emory.cs.trie.Trie;
import edu.emory.cs.trie.TrieNode;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class AutocompleteKhanExtraCredit extends Autocomplete<List<String>> {
    public AutocompleteKhanExtraCredit(String dict_file, int max) {
        super(dict_file, max);
    }

    int counter = 0;

    @Override
    //EVERY NODE SHOULD BE LIST<STRING>
    public List<String> getCandidates(String prefix) {
        // TODO: must be modified

        prefix = prefix.trim();
        //want to start by creating a new list of strings
        List<String> result = new ArrayList<String>();
        //now we want to search for the prefix in the trie and set it to our node
        TrieNode<List<String>> prefixNode = find(prefix);
        //if the prefix is not in our trie, then the node will be null so we would return an empty list
        if (prefixNode == null) {
            //System.out.println("runs");
            return result;
        }


        //if it is not null, we want to find all the children of that prefix by traversing the tree
        //so we have to create a traverse function
        traversal(prefix, prefixNode, result);

        //we have to sort the list length-wise and alphabetically
        //nice efficient method from stackoverflow to sort the list
        Collections.sort(result, new Alphabetical());

        //now that the list is sorted make sure that most recently selected candidates are first
        //possible option includes checking if the node has a value first, otherwise null case
        //and then removing all of the values we get from node.getValue() and then adding them all back in
        //to the first index, would this work though?
        //think it would because it would shift down all the values since arraylist
        //use removeall function which would remove all occurences from result of the values from prefixnode
        //then add them all back in to the beginning of the result list
        //also takes care of no duplicate candidates case along w further down


        if (prefixNode.hasValue()) {
            result.removeAll(prefixNode.getValue());
            //printArrayFunc(result);
            result.addAll(0, prefixNode.getValue());
            //System.out.println(prefix + " " + counter);
            //printArrayFunc(result);

            if(counter == 1)
            {
                result.remove(prefix);
                //System.out.println("ran");
            }

        }




        //now we need to make sure the list only returns the getMax() values
        //start at getMax value, and remove everything above that
        List<String> getMaxResult = new ArrayList<String>();
        for (int i = 0; i < getMax(); i++) {

            if (i < result.size())
            {
                getMaxResult.add(result.get(i)); //make sure i<result.size() or else we will get error
                //System.out.println("rannn");
            }

            else
                break;
        }


        return getMaxResult; //return the new result list
/*
        if (prefix.equals("sh"))
            return List.of("she", "ship", "shell");

        return List.of("dummy", "candidate");*/
    }

    public void printArrayFunc(List<String> s) {
        for (int i = 0; i < s.size(); i++) {
            System.out.print(s.get(i) + " ");
        }
    }

    public void traversal(String prefix, TrieNode<List<String>> prefixNode, List<String> result) {
        //if the node we hit is in an endstate then that means that we found a full-word so add that to our list
        if (prefixNode.isEndState())
            result.add(prefix);

        //otherwise we need to call the getChildrenMap function from TrieNode to find all the other words
        //we want all the words but to handle duplicates we need to use a set
        //looking at the map functions from github, which one to use?
        //try keySet- Returns a Set view of the keys contained in this map

        //want to call it for each new character added so for loop
        for (Character additionalLetter : prefixNode.getChildrenMap().keySet()) {
            //recursive call to keep traversing with each additional letter added onto prefix
            traversal(prefix + additionalLetter, prefixNode.getChildrenMap().get(additionalLetter), result);
            //check if it ends on full word, should add it after words
        }
    }

    @Override
    //FROM CLASS NOTES:
    // SHOULD NEVER RETURN NULL, HAVE TO FIX THAT CASE
    //EVERY NODE SHOULD BE LIST<STRING>
    //go to office hours to figure out what to do instead of nullpointer case
    public void pickCandidate(String prefix, String candidate) {
        // TODO: must be filled
        prefix = prefix.trim();
        candidate = candidate.trim();
        List<String> result = new ArrayList<String>(); //new list
        TrieNode<List<String>> prefixNode = find(prefix); //node with find prefix
        if(candidate.startsWith(prefix))
        {
            //if the candidate startswithprefix but the prefix isn't in the trie then we add them both in
            if(find(prefix) == null)
            {
                put(prefix, null);
                prefixNode=find(prefix);
                put(candidate, null);
            }
            put(candidate,null); //put candidate into trie
            //check to see if the node already has a value and contains the word we're looking for
            //avoids null problem with hasValue
            //if it does then we should remove that word from our node so there aren't duplicates
            //we want to add it to the beginning because it was just picked
            //if the node doesnt have a value then we can just add it to our list
            //then set the value of the node to the list which would preserve our oder
            if(prefixNode.hasValue() && prefixNode.getValue().contains(candidate))
            {
                //takes care of duplicate case
                prefixNode.getValue().remove(candidate); //remove that from our node<list<string>
                return;
            }
            else if(prefixNode.hasValue()) //if it already has a value, then add it to the beginning of the list
            {
                prefixNode.getValue().add(0, candidate);
            }
            //else just add it to the beginning of the result list and set the prefixnode value
            //set prefixnode value to the result list
            else
            {
                result.add(0, candidate);
                prefixNode.setValue(result);}
        }
        else
        {
            put(candidate, null);
            put(prefix, null);
            prefixNode = find(prefix);
            result.add(0, candidate);
            counter = 1;
            prefixNode.setValue(result);
        }



    }

    //comparator code that along with Collections.sort, sorts it using length and alphabetical
    class Alphabetical implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            if (o1.length() > o2.length()) {
                return 1;
            } else if (o1.length() < o2.length()) {
                return -1;
            }
            return o1.compareTo(o2);
        }
    }
}