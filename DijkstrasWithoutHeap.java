package prj1;
import java.util.ArrayList;
/**
 * The implementation of Dijkstras shortest path algorithm by using a simple
 * linear search to find the unvisited node with the minimum distance estimate
 * 
 * @author Matthew Byun (matthewb20)
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
    
    public static boolean areSame(boolean arr[])
    {
       for (int i=0; i<arr.length; i++) {
           if (!arr[i]) {
                return true;
           }
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
        int d_length = distances.length;
        // distance from original source to source will always be 0
        distances[source-1] = 0;
        visited[source-1] = true;
        // Priority queue with nodes directly next to source node
        ArrayList<int[]> priority_queue = adjLists.get(source-1);
        // loops while priority queue has elements
        while(priority_queue.size()>0 && areSame(visited)) {
            // finds minimum value and node th3t produced minimum value
            int v[] = priority_queue.get(0);
            int min_value = v[1];
            // loop that finds the min value and min node
            for(int i = 0; i < priority_queue.size()-1;i++) {
                int[] node_value = priority_queue.get(i+1);
                if(min_value > node_value[1]) {
                    min_value = node_value[1];
                    v = node_value;
                }
            }
            // removes that edge from the queue
            priority_queue.remove(v);
            // runs if minimum node has not been visited
            if(!visited[v[0]-1]) {
                // minimum node has now been visited
                visited[v[0]-1] = true;
                // distance of node is set
                distances[v[0]-1] = min_value;
                // finds neighbors of v
                ArrayList<int[]> v_neighbors = adjLists.get(v[0]-1);
                for(int i = 0; i < v_neighbors.size(); i++) {
                    // if the neighbors have not been visited, add the edge to priority_queue
                    // and set the new weight of the edge
                    int current_v[] = v_neighbors.get(i);
                    if(!visited[current_v[0]-1]) {
                        int distance = current_v[1];
                        current_v[1] = distances[v[0]-1] + distance;
                        priority_queue.add(current_v);
                    }
                }
            }
        }
        // If the node has no neightbors, the distance will be -1.
        for(int d = 0; d < d_length;d++) {
            if (distances[d] == Integer.MAX_VALUE) {
                distances[d] = -1;  
            }
        }
        // return distance array
        return distances;
    }
}