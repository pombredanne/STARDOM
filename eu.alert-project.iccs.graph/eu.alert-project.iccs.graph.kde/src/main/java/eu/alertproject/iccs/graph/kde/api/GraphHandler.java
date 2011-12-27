package eu.alertproject.iccs.graph.kde.api;

import edu.uci.ics.jung.algorithms.filters.EdgePredicateFilter;
import edu.uci.ics.jung.algorithms.filters.VertexPredicateFilter;
import edu.uci.ics.jung.algorithms.scoring.BetweennessCentrality;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import org.apache.commons.collections15.Predicate;
import org.apache.commons.collections15.TransformerUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: fotis
 * Date: 27/12/11
 * Time: 19:35
 */
public class GraphHandler {

    private Logger logger = LoggerFactory.getLogger(GraphHandler.class);
    private Map<String,Number> weights =null;
    private BetweennessCentrality<Integer,String> bc;


    public Graph<Integer, String> create(File inputFile,File outputFile){
       
        Graph<Integer,String> graph = null;
        
        LineIterator it = null;

        int max = 106000;
        BufferedWriter fw = null;
        OutputStreamWriter osw = null;
        FileOutputStream fos = null;
        try {
            it = FileUtils.lineIterator(inputFile, "UTF-8");


            Integer previousFile = null;
            Integer previousAuthor = null;

            graph = new UndirectedSparseGraph<Integer, String>();
            weights = new HashMap<String, Number>();

            while (it.hasNext() || max > 0) {
                String next = String.valueOf(it.next());


                String[] split = next.split(",");

                Integer fileId = Integer.valueOf(split[0]);
                Integer authorId = Integer.valueOf(split[1]);

                if (previousFile != null && previousFile.equals(fileId)) {


                    String edge = previousFile + "_" + previousAuthor + "_" + authorId;

                    //already added
                    if (!graph.containsEdge(edge)) {
                        graph.addEdge(edge, previousAuthor, authorId);
                        weights.put(edge, 1);
                    } else {
                        weights.put(edge, weights.get(edge).intValue() + 1);
                    }

                    previousAuthor = authorId;

                } else {

                    previousAuthor = authorId;
                    previousFile = fileId;
                }

                max--;


            }



            logger.trace("void main(args) Vertex Count: {} ", graph.getVertexCount());

            bc = new BetweennessCentrality<Integer, String>(graph, TransformerUtils.mapTransformer(weights));
            logger.trace("void main(args) Betweeness calculated");


            fos = new FileOutputStream(outputFile);
            osw = new OutputStreamWriter(fos, "UTF-8");
            fw = new BufferedWriter(osw);

            for (Integer vertex : graph.getVertices()) {
                fw.write(vertex + "\t" + bc.getVertexScore(vertex) + "\n");
            }


            logger.trace("void main(args) File created {}",outputFile);



        } catch (IOException e) {
            logger.error("IO Error ", e);
        } catch (NumberFormatException e) {
            logger.error("Wrong data", e);
        } finally {
            LineIterator.closeQuietly(it);
            IOUtils.closeQuietly(fw);
            IOUtils.closeQuietly(osw);
            IOUtils.closeQuietly(fos);

        }
        
        return graph;

    }

    public Map<String, Number> getWeights() {
        return weights;
    }

    public BetweennessCentrality<Integer, String> getBc() {
        return bc;
    }
}
