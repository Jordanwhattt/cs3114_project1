package prj1;

import java.util.ArrayList;

/**
 * 
 * The implementation of Dijkstras shortest path algorithm by using
 * min-heaps
 * 
 * @author Enter your names here
 */
public class DijkstrasWithHeap {
    // stores all the node's statuses
    private boolean[] visited;
    private int n;

    // stores adjacent nodes
    private ArrayList<MinHeap> adjLists = new ArrayList<MinHeap>();

    int[] dist_array;

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
    public DijkstrasWithHeap(int n, int[][] edges) {
        this.n = n;
        visited = new boolean[n];
        int[] prev = new int[n];

        for (int u = 0; u < n; u++) {
            visited[u] = false;
        }
        
        for (int i = 0; i <= n; i++) {
            adjLists.add(new MinHeap(n, 2));
        }

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            int w = edge[2];
            adjLists.get(u).insert(v, w);
            adjLists.get(v).insert(u, w);
            dist_array = new int[n];
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
        
        visited[source-1] = true;
        for(int i = 0; i < this.n; i++) {
            if(i == source - 1) {
                dist_array[i] = 0;
            }else {
                dist_array[i] = Integer.MAX_VALUE;
            }    
        }

        MinHeap Q = adjLists.get(source);

        while(!Q.isEmpty()) {
            
            int[] min_node = Q.extractMin(); 
            int v = min_node[0];
            int w = min_node[1];
            
            if(!visited[v-1]) {
                visited[v-1] = true;
                dist_array[v-1] = dist_array[source-1] + w;
                
                MinHeap neighbors = adjLists.get(v);
                int[] key_node;
                
                
                
                for(int i = 0; i < neighbors.size(); i++ ) {
                    key_node = neighbors.extractMin();
                    if(!visited[key_node[0] - 1]) {
                        int distance = key_node[1];
                        Q.insert(key_node[0], key_node[1] + dist_array[v-1]);
                        Q.decreaseKey(key_node[0],  distance + dist_array[v-1]);
                    }
                }
     
            }

        }
        
        for(int i = 0; i < visited.length; i++) {
            if(!visited[i]) {
                dist_array[i] = -1;
            }
        }
                

        return dist_array;
    }

}
