package prj3;

import java.util.Arrays;
import java.util.Comparator;

/**
 * The Range Search data structure
 * 
 * @author enter your names here
 *
 */

public class RangeSearch {


    /**
     * [x_1 y_1 z_1]; row(1) = p_1
     * [x_2 y_2 z_2]; row(2) = p_2
     * [ ... ]
     * [ ... ]
     * [ ... ]
     * [x_n y_n z_n] row(n) = p_n
     */
    private int[][] points;
    public Point[] Px;
    public Point[] Py;
    public Point[] Pz;
    BST x_tree;
    public Node root;
    private int n;
    public KDTreev2 kdtree1;

    /**
     * The constructor of the class
     * 
     * @param points
     *            a 2 dimensional array, each row has the format [x,y,z], so the
     *            i-th point has x coordinate points[i-1][0], y coordinate
     *            points[i-1][1], and z coordinate points[i-1][2]
     */

    public RangeSearch(int[][] points) {
        this.points = points;
        
        
        this.n = this.points.length; 
        
        Px = new Point[n]; // Array of Points. We will sort this by the x cooridnates. All the points will have the same entries but in different orders
        Py = new Point[n]; // Array of Points. We will sort this by the y cooridnates.
        Pz = new Point[n]; // Array of Points. We will sort this by the z cooridnates.
        
        //Sorts by the x coordinates.
        points = sortPointsBy(points, n, "x");
        for(int i = 0; i < n; i++) { //Adds "Point" elements to Px. 
            Px[i] = new Point(points[i][0], points[i][1],points[i][2]);
        }
        
        //Sort Y Array
        points = sortPointsBy(points, n, "y");
        for(int i = 0; i < n; i++) {
            Py[i] = new Point(points[i][0], points[i][1],points[i][2]);
        }
        
        //Sorts Z Array
        points = sortPointsBy(points, n, "z");
        for(int i = 0; i < n; i++) {
            Pz[i] = new Point(points[i][0], points[i][1],points[i][2]);
        }
        
        //1-D Trees
        x_tree = new BST(0); //Sorted by x coordinates
        BST y_tree = new BST(1);
        BST z_tree = new BST(2);
        x_tree.root = x_tree.buildTree(Px); //Builds a 1-d Binary search tree sorted by the X coordinates
        
        y_tree.root = y_tree.buildTree(Py);
        z_tree.root = z_tree.buildTree(Pz);
        
        kdtree1 = new KDTreev2(Px);
    }
        
        
        


    /**
     * Given the description of a rectangular prism in the 3 dimensional space,
     * this method returns the number points that lie inside or on the boundary
     * of the query range
     * 
     * @param xMin
     *            queries[
     * @param xMax
     * @param yMin
     * @param yMax
     * @param zMin
     * @param zMax
     * 
     * @return
     */
    public int query(
        int xMin,
        int xMax,
        int yMin,
        int yMax,
        int zMin,
        int zMax) {
        
        Point plow = new Point(xMin, yMin, zMin);
        Point phigh = new Point(xMax, yMax, zMax);
        Prism queryRange = new Prism(plow, phigh);

        Point min_point = new Point(Px[0].getX(), Py[0].getY(), Pz[0].getZ());
        Point max_point = new Point(Px[n - 1].getX(), Py[n - 1].getY(), Pz[n
            - 1].getZ());

        Prism bounding_box = new Prism(min_point, max_point); // ???????? IDK
                                                              // how to get this
        return kdtree1.rangeCount(queryRange, kdtree1.root, bounding_box);
        
    }


    /**
     * Sorts the points Matrix based on "0" = "x".
     * "1" = "Y"
     * "2" = "Z"
     * 
     * @param coords
     * @param n
     * @param sortBy
     * @return
     */
    public int[][] sortPointsBy(int[][] coords, int n,  String sortBy) {

        if(sortBy == "x") {
            Arrays.sort(coords, Comparator.comparingDouble(o -> o[0]));
        } else if(sortBy == "y") {
            Arrays.sort(coords, Comparator.comparingDouble(o -> o[1]));
        } else if(sortBy == "z") {
            Arrays.sort(coords, Comparator.comparingDouble(o -> o[2]));
        } else {
            return null; // The String sortBy must be "x" "y" or "z"
        }

        return coords; 
    }
    
    
    
    
    
    

}
