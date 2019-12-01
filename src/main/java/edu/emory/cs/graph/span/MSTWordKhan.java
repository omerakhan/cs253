package edu.emory.cs.graph.span;

import edu.emory.cs.graph.Graph;
import edu.emory.cs.graph.path.Dijkstra;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MSTWordKhan extends MSTWord {

    //we're going to create a matrix that holds the cosine distance to each vector
    //500 total words

    public MSTWordKhan(InputStream in) {
        super(in);
    }

    @Override
    protected Graph createGraph() {

        //create a new graph
        Graph wordvector = new Graph(vertices.size());
        //formula for cosine similarity is dot product/magnitude
        for(int i = 0;i<vertices.size();i++)
        {
            for(int j = 0;j<vertices.size();j++)
            {
                double dotProduct = 0;
                double firstVectorMag = 0;
                double secondVectorMag = 0;
                for(int k = 0;k<vertices.get(i).getVector().length;k++)
                {
                    dotProduct+= (vertices.get(i).getVector()[k] * vertices.get(j).getVector()[k]);
                    firstVectorMag+= vertices.get(i).getVector()[k] * vertices.get(i).getVector()[k] ;
                    secondVectorMag+= vertices.get(j).getVector()[k] * vertices.get(j).getVector()[k]  ;
                }

                firstVectorMag = Math.sqrt(firstVectorMag);
                secondVectorMag = Math.sqrt(secondVectorMag);
                wordvector.setUndirectedEdge(i,j,(1 - (dotProduct/(firstVectorMag*secondVectorMag))));
            }
        }
        return wordvector;
    }

    @Override
    public SpanningTree getMinimumSpanningTree() {
        // TODO: to be filled
        MSTPrim mst = new MSTPrim();
        return mst.getMinimumSpanningTree(createGraph());
    }

    @Override
    public List<String> getShortestPath(int source, int target) {
        Dijkstra d = new Dijkstra();
        List<String> pathList = new ArrayList<String>();
        Integer[] result = d.getShortestPath(createGraph(),source,target);
        for(int i = source;source<target;source++)
        {
            pathList.add(vertices.get(source).getWord());

        }
        pathList.add(vertices.get(target).getWord());
        return pathList;
    }

    static public void main(String[] args) throws Exception {
        final String INPUT_FILE = "src/main/resources/word_vectors.txt";
        final String OUTPUT_FILE = "src/main/resources/word_vectors.dot";
        MSTWord mst = new MSTWordKhan(new FileInputStream(INPUT_FILE));
        SpanningTree tree = mst.getMinimumSpanningTree();
        List<String> result = mst.getShortestPath(0,2);
        for(int i = 0;i<result.size();i++)
        {
            System.out.println(result.get(i) + " ");
        }
        mst.printSpanningTree(new FileOutputStream(OUTPUT_FILE), tree);
        System.out.println(tree.getTotalWeight());
    }
}