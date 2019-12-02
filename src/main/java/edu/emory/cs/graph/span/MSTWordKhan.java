package edu.emory.cs.graph.span;

import edu.emory.cs.graph.Graph;
import edu.emory.cs.graph.path.Dijkstra;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MSTWordKhan extends MSTWord {

    public MSTWordKhan(InputStream in) {
        super(in);
    }

    @Override
    protected Graph createGraph() {

        //create a new graph with size of number of vertices
        Graph wordvector = new Graph(vertices.size());
        //iterate through vertices arraylist with two for loops
        for(int i = 0;i<vertices.size();i++)
        {
            for(int j = 0;j<vertices.size();j++)
            {
                //values to calculate cosine distance
                //reset to zero every time you calculate
                double dotProduct = 0;
                double firstVectorMag = 0;
                double secondVectorMag = 0;
                //iterate through values inside of each array, each vector value
                for(int k = 0;k<vertices.get(i).getVector().length;k++)
                {
                    //three values to calculate cosine similarity
                    dotProduct+= (vertices.get(i).getVector()[k] * vertices.get(j).getVector()[k]);
                    firstVectorMag+= vertices.get(i).getVector()[k] * vertices.get(i).getVector()[k] ;
                    secondVectorMag+= vertices.get(j).getVector()[k] * vertices.get(j).getVector()[k]  ;
                }
                //take square root of the magnitudes
                firstVectorMag = Math.sqrt(firstVectorMag);
                secondVectorMag = Math.sqrt(secondVectorMag);
                //set undirected edge for i and j to the cosine distance
                wordvector.setUndirectedEdge(i,j,(1 - (dotProduct/(firstVectorMag*secondVectorMag))));
            }
        }
        return wordvector;
    }

    @Override
    public SpanningTree getMinimumSpanningTree() {
        // TODO: to be filled
        MSTPrim mst = new MSTPrim();
        return mst.getMinimumSpanningTree(createGraph()); //use prim's algorithm to return min spanning tree
    }

    @Override
    public List<String> getShortestPath(int source, int target) {
        Dijkstra d = new Dijkstra();
        List<String> pathList = new ArrayList<String>();
        List<String> pathListWithoutDuplicates = new ArrayList<String>();

        //ensure that source and target don't go out of bonds
        if(source>=0 && source<500 && target > source && target>=0 && target<500)
        {
            //create an array that stores the shortest path
            Integer[] result = d.getShortestPath(createGraph(),source,target);
            for(int i = source;i<target-1;i++)
            {
                //don't add duplicates so don't add target word
                if(!vertices.get(result[i]).getWord().equals(vertices.get(target).getWord()))
                {
                    //add all the words to the return arraylist
                    pathList.add(vertices.get(result[i]).getWord());

                }
            }
            //remove duplicates
            pathListWithoutDuplicates = pathList.stream().distinct().collect(Collectors.toList());
            //work on removing all non-unique values from pathList
            //add target word
            pathListWithoutDuplicates.add(vertices.get(target).getWord());
        }

        return pathListWithoutDuplicates;
    }

    static public void main(String[] args) throws Exception {
        final String INPUT_FILE = "src/main/resources/word_vectors.txt";
        final String OUTPUT_FILE = "src/main/resources/word_vectors.dot";
        MSTWord mst = new MSTWordKhan(new FileInputStream(INPUT_FILE));
        SpanningTree tree = mst.getMinimumSpanningTree();
        List<String> result = mst.getShortestPath(0,5);
        for(int i = 0;i<result.size();i++)
        {
           System.out.println(result.get(i) + " ");
        }
        mst.printSpanningTree(new FileOutputStream(OUTPUT_FILE), tree);
        System.out.println(tree.getTotalWeight());
    }
}