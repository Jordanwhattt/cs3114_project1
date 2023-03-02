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
    //stores all the node's statuses
    private boolean[] visited;
    //stores distance
    private int[] distances;
    
    // stores adjacent nodes
    private ArrayList<ArrayList<int[]>> adjLists = new ArrayList<ArrayList<int[]>>();
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
        // creates adjacency list
        for(int i = 0; i < n; i++) {
            adjLists.add(new ArrayList<>());
        }
        
        //  finds the neighbors to each node, and the weight, and adds that to adjacency list
        for(int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            int w = edge[2];
            adjLists.get(u-1).add(new int[] {v,w});
            adjLists.get(v-1).add(new int[] {u,w});
        }
        
        // creates distance array and visited array
        visited = new boolean[n];
        distances = new int[n];
        for(int i = 0; i < n;i++) {
            distances[i] = Integer.MAX_VALUE;
            visited[i] = false;
        }
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
        
        // distance from original source to source will always be 0
        distances[source-1] = 0;
        visited[source-1] = true;
        
        // If the node has no neightbors, the distance will be -1.
        for(int d = 0; d < distances.length;d++) {
            if (adjLists.get(d).size() == 0) {
                distances[d] = -1;
                visited[d] = true;
            }
        }
        
        // Priority queue with nodes directly next to source node
        ArrayList<int[]> priority_queue = adjLists.get(source-1);
        
        // loops while priority queue has elements
        while(priority_queue.size() > 0) {
            
            // finds minimum value and node that produced minimum value
            int v = priority_queue.get(0)[0];
            int min_value = priority_queue.get(0)[1];
            int index = 0;
            // loop that finds the min value and min node
            for(int i = 1; i < priority_queue.size();i++) {
                int node_value = priority_queue.get(i)[1];
                if(min_value > node_value) {
                    min_value = node_value;
                    v = priority_queue.get(i)[0];
                    index = i;
                }
            }
            // removes that edge from the queue
            priority_queue.remove(priority_queue.get(index));
            
            // runs if minimum node has not been visited
            if(visited[v-1] == false) {
                // minimum node has now been visited
                visited[v-1] = true;
                // distance of node is set
                distances[v-1] = min_value;
                // finds neighbors of v
                ArrayList<int[]> v_neighbors = adjLists.get(v-1);
                for(int i = 0; i < v_neighbors.size(); i++) {
                    // if the neighbors have not been visited, add the edge to priority_queue
                    // and set the new weight of the edge
                    if(visited[v_neighbors.get(i)[0]-1] == false) {
                        int distance = v_neighbors.get(i)[1];
                        v_neighbors.get(i)[1] = distances[v-1] + distance;
                        priority_queue.add(v_neighbors.get(i));
                    }
                }
            }
        }
        // return distance array
        return distances;
    }
}