package prj3;

import java.util.Arrays;

public class BST {
    
    public Node root;
    public int type;
    public int count;
    
    //1D Range Tree
    public BST() {
        root = null;
        this.type = 1;
        this.count = 0;
    }
    
    
    public BST(int type) {
        root = null;
        this.type = type;
    }
    
    /**
     * 
     * @param points
     * @param type: x = 0, y = 1, z = 2
     * @return
     */
    public Node buildTree(Point[] points) {
        int n = points.length;
        
        //If P contains one Point
        if(n == 1) {
            Node v = new Node(points[0]); //Createa leaf node storing this point and make it 
            return v;
        }
        
        Point[] p_left;
        Point[] p_right;
        Point mid;
        
        int median_index = n / 2;
        
        
        p_left = Arrays.copyOfRange(points, 0, median_index);
        p_right = Arrays.copyOfRange(points, median_index, n);


        Node v = new Node(points[median_index-1]);
        //v.setPy(points);

        //5. build v_left
        Node v_left = this.buildTree(p_left);
        //6. build v_right
        Node v_right = this.buildTree(p_right);
        
        //Make v_left the left child of v
        v.left = v_left;
        //Make v_right the right child of v
        v.right = v_right;    
        return v;
        
    }
    
    
    /**
     * query does not work yet. This should also be in 
     * @param min
     * @param max
     * @return
     */
    public int RangeQuery1D(int min, int max, int type) {
        
        Node v_split = findSplitNode(this, min, max);
        int split_value;
        int v_value;
        if(v_split.isLeaf()) { //Base Case 1
            
            split_value = returnSplitValue(v_split, type);
            
            if(split_value >= min & split_value <= max) {
                count++; //Report point
            }
            
        } else { //Case 2: Not a leaf
            Node v = v_split.left;
            v_value = returnSplitValue(v, type);
            
            while(!v.isLeaf()) {
                v_value = returnSplitValue(v, type);
                if(min <= v_value) {
                    reportSubtree(v.right, min, max);
                    v = v.left;
                }
                else {
                    v = v.right;
                }
            }
            
            if(v_value >= min & v_value <= max) {
                count++; //Report point
            }
            
            
            v = v_split.right;
            while(!v.isLeaf()) {
                split_value = returnSplitValue(v, type);
                
                if(max >= split_value) {
                    reportSubtree(v.left, min, max);
                    v = v.right;
                } 
                else {
                    v = v.left;
                }
            }
            if(v_value >= min & v_value <= max) {
                count++; //Report point
            }
        }
        
        return count;
    
    }
    
    
    private int returnSplitValue(Node v_split, int type2) {
        int split_value;
        if(type == 0 % 3) {
            split_value = v_split.point.getX();
        }
        else if(type == 1 % 3) {
            split_value = v_split.point.getY();
        }
        else  {
            split_value = v_split.point.getZ();
        }
        return split_value;
    }


    public Node findSplitNode(BST tree, int min, int max) {
        Node v = this.root;
        while(!v.isLeaf() & (max <= v.point.getX() | min >= v.point.getX())) {
            if(max <= v.point.getX()) {
                v = v.left;
            } else {
                v = v.right;
            }
        }
        return v;
    }
    

    
    
    public void reportSubtree(Node v, int min, int max) {
        if(v.isLeaf()) {
            if(v.point.getX() >= min & v.point.getX() <= max) {
                count++; //Report point
            }
        } else {
            reportSubtree(v.left, min, max);
            reportSubtree(v.right, min, max);
        }
    }
}