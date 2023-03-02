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
    //stores all the node's statuses
    private boolean[] visited;
    //stores distance
    private int[] distances;
    
    // stores adjacent nodes
    private ArrayList<MinHeap> adjLists = new ArrayList<MinHeap>();
    
    MinHeap queue;
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

        distances = new int[n];
        visited = new boolean[n];
        int[] prev = new int[n];
        
        for(int u = 0; u < n; u++) {
            distances[u] = Integer.MAX_VALUE;
            visited[u] = false;
        }
        
        for(int i = 0; i < n; i++) {
            adjLists.add(new MinHeap(n, 2));
        }
        
        
        for(int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            int w = edge[2];
            adjLists.get(u-1).insert(v,w);
            adjLists.get(v-1).insert(u,w);
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
        queue = adjLists.get(source-1);

        visited[source-1] = true;
        this.distances[source-1] = 0;
        queue.insert(source, 0);
        int[] uv = new int[2];
        while(queue.getHeap().length > 0) {
            
            uv = queue.extractMin();
            int v = uv[0];
            int w = uv[1];
            if(!visited[v-1]) {
                visited[v-1] = true;
                distances[v-1] = w;
                for(int u_prime = 0; u_prime < adjLists.get(v).size(); u_prime++) {
                    if(!visited[u_prime]) {
                        int distance = adjLists.get(u_prime).extractMin()[1];
                        adjLists.get(u_prime).extractMin()[1] = this.distances[v-1] + distance;
                        queue.decreaseKey(v, w);
                    }
                }
            }     
        }
        
        
        
        
        return distances;
    }

}
