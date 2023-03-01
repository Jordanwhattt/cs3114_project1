package prj1;
import java.util.ArrayList;
/**
 * The implementation of Dijkstras shortest path algorithm by using a simple
 * linear search to find the unvisited node with the minimum distance estimate
 * 
 * @author Enter your names here
 * @version 1.1
 */
public class DijkstrasWithoutHeap {
    //stores edges
    private int[][] nodes;
    //stores all the nodes that have been visited
    private ArrayList<int[]> visited;
    //stores distance
    private int[] distances;
    private ArrayList<int[]> unvisited;
    /**
     * Constructor of the class
     * 
     * @param n:
     *            number of nodes of the graph
     * @param edges:
     *            the set of edges of the graph. Each row of 'edges' is in the
     *            form of [u, v, w], which means that there is an edge between u
     *            and v with weight w. So edges[i][0] and edges[i][1] are the
     *            end-points of the i-th edge and edges[i][2] is its weight
     */
    public DijkstrasWithoutHeap(int n, int[][] edges) {
        // TODO complete
        visited = new ArrayList<int[]>();
        unvisited = new ArrayList<int[]>();
        nodes = new int[n][3];
        for(int i = 0; i < n; i++) {
            nodes[i] = edges[i];
            nodes[i][0] = edges[i][0];
            nodes[i][1] = edges[i][1];
            nodes[i][2] = edges[i][2];
            unvisited.add(nodes[i]);
        }
        
        // Creates visited array
        
        // creates distance array
        distances = new int[n];
        for(int i = 0; i < distances.length;i++) {
            distances[i] = Integer.MAX_VALUE;
        }
    }
    
    /**
     * This method checks to see if the node has already been visited. It uses the distance array, as an unvisited node will have
     * a distance value of Integer.MAXVALUE
     * @param nodeIndex
     * 
     * @return returns true if not visited
     *         returns false if visited
     */
    private boolean visitNode(int nodeIndex) {
        if(distances[nodeIndex] == Integer.MAX_VALUE) {
            return true;
        }
        return false;
    }


    /**
     * This method computes and returns the distances of all nodes of the graph
     * from the source node
     * 
     * @param source
     * 
     * @return an array containing the distances of the nodes of the graph from
     *         source. Element i of the returned array represents the distance
     *         of node i from the source
     */
    public int[] run(int source) {
        // source vertex/node
        
        // distance from original source to source will always be 0
        distances[source-1] = 0;
        visited.add(nodes[source-1]);
        unvisited.remove(nodes[source-1]);
        // Value of the node being used for distance
        int key = 0;
        int min = 0;
        int min_node = 0;
     // Finds the minimum distance from source to each node
        
            // Finds nodes directly next to source
        while(unvisited.size() > 0) {
            ArrayList<int[]> adjacent_nodes = new ArrayList<int[]>();
            int length = -1;
            for(int k = 0; k < nodes.length; k++) {
                // Checks if node is directly next to source and checks if its been visited yet
                if((nodes[k][0] == source && (this.visitNode(nodes[k][1]-1))|| (nodes[k][1] == source && (this.visitNode(nodes[k][0]-1))))) {
                    adjacent_nodes.add(nodes[k]);
                    visited.add(nodes[k]);
                    unvisited.remove(nodes[k]);
                    length++;
                    // This is to keep the source in "u"
                    if(adjacent_nodes.get(length)[1] == source) {
                        int temp = adjacent_nodes.get(length)[0];
                        adjacent_nodes.get(length)[0] = adjacent_nodes.get(length)[1];
                        adjacent_nodes.get(length)[1] = temp;
                    }
                    
                    // In the first iteration of the algorithm, the distance of nodes directly next to the source
                    // will be the minimum distance
                    if(key == 0) {
                        distances[adjacent_nodes.get(length)[1]-1] = adjacent_nodes.get(length)[2];
                    }
                    
                    min = adjacent_nodes.get(0)[2] + key;
                    min_node = adjacent_nodes.get(0)[1];
                    if(min > adjacent_nodes.get(length)[2] + key && length>0) {
                        min = adjacent_nodes.get(length)[2] + key;
                        min_node = adjacent_nodes.get(length)[1];
                    }
                }
            }
                // Moves loop onto the next node
            source = min_node;
            distances[min_node-1] = min;
            key = min;
            // This is to find the minimum distance to get from source to node
        }
        // Any value that is still Integer.MAXVALUE in the distance array means that the node is not connected
        for(int d = 0; d < distances.length;d++) {
            if (distances[d] == Integer.MAX_VALUE) {
                distances[d] = -1;
            }
        }
        return distances;
    }
}