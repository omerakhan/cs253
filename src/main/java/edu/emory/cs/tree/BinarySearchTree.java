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
package edu.emory.cs.tree;

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class BinarySearchTree<T extends Comparable<T>> extends AbstractBinarySearchTree<T,BinaryNode<T>>
{
    /**
     * @param key the key of this node.
     * @return a binary node with the specific key.
     */
    @Override
    public BinaryNode<T> createNode(T key)
    {
        return new BinaryNode<T>(key);
    }
}