package prj3;

import java.util.Arrays;
import java.util.Comparator;


public class KDTreev2 {
    
    Node root;

    public KDTreev2(Point[] points) {
        
        int l = points.length;
        Point[] px = new Point[l];
        Point[] py = new Point[l];
        Point[] pz = new Point[l];
        
        for(int i = 0; i < l; i++) {
            px[i] = points[i];
            py[i] = points[i];
            pz[i] = points[i];
        }
        
        Arrays.sort(px, (a, b) -> a.x - b.x);
        Arrays.sort(py, (a, b) -> a.y - b.y);
        Arrays.sort(pz, (a, b) -> a.z - b.z);

        this.root = buildTree(px, py, pz, 0);
        
    }
    
    
    
    
    
    public Node buildTree(Point[] px, Point[] py, Point[] pz, int depth) {
        
        int n = px.length;
        int axis = depth % 3;
        
        if(n == 0) {
            return null;
        }
        if(n == 1) {
            
            return new Node(new Point(px[0].x, px[0].y, px[0].z), depth);
           
            
        }
//        if(n == 2) {
//            Node v;
//            if(axis == 0) {
//                v = new Node(new Point(px[1].x, px[1].y, px[1].z), depth);
//                v.left = new Node(new Point(py[0].x, py[0].y, py[0].z), depth+1);
//                v.right = new Node(v.point, depth+1);
//            }
//            else if(axis == 1 ) {
//                v = new Node(new Point(py[1].x, py[1].y, py[1].z), depth);
//                v.left = new Node(new Point(pz[0].x, pz[0].y, pz[0].z), depth+1);
//                v.right = new Node(v.point, depth+1);
//            }
//            else {
//                v = new Node(new Point(pz[1].x, pz[1].y, pz[1].z), depth);
//                v.left = new Node(new Point(px[0].x, px[0].y, px[0].z), depth+1);
//                v.right = new Node(v.point, depth+1);
//            }
//            return v;
//            
//        }
        

        int l_length = 0;
        int mid_i =0;
        if(n % 2 == 0) {
            l_length = n / 2;
            mid_i = l_length - 1;
        }
        else {
            mid_i = n/2;
            l_length = mid_i + 1;
        }
        
        Point[] x_left = new Point[l_length];
        Point[] x_right = new Point[n - l_length];
        
        Point[] y_left = new Point[l_length];
        Point[] y_right = new Point[n - l_length];
        
        Point[] z_left = new Point[l_length];
        Point[] z_right = new Point[n - l_length];
        
        if(axis == 0) {

            Node v = new Node(new Point(px[mid_i].x, px[mid_i].y, px[mid_i].z), depth);
          
            int xleft_index = 0;
            int xright_index = 0;
            int yleft_index = 0;
            int zleft_index = 0;
            int yright_index = 0;
            int zright_index = 0;
            
            for(int i = 0; i < px.length; i++) {
                
                if(v.point.x >= px[i].x) {
                    x_left[xleft_index] = px[i];
                    xleft_index++;
                } else {
                    x_right[xright_index] = px[i];
                    xright_index++;
                }
           
                if(v.point.x >= py[i].x) {
                    y_left[yleft_index] = py[i];
                    yleft_index++;
                    
                } else  {
                    y_right[yright_index] = py[i];
                    yright_index++;
                }
                
                if(v.point.x >= pz[i].x) {
                    z_left[zleft_index] = pz[i];
                    zleft_index++;
                } else {
                    z_right[zright_index] = pz[i];
                    zright_index++;
                }
                    
            }

            
            v.left = buildTree(x_left, y_left, z_left, depth + 1);
            v.right = buildTree(x_right, y_right, z_right, depth + 1);
  
            
            return v;
        }
        else if(axis == 1) {

            Node v = new Node(new Point(py[mid_i].x, py[mid_i].y, py[mid_i].z), depth);
            
            int xleft_index = 0; 
            int zleft_index = 0;
            int yleft_index = 0;
            int yright_index = 0;
            int xright_index = 0;
            int zright_index = 0;
            
            for(int i = 0; i < py.length; i++) {
                
                if(v.point.y >= px[i].y) {
                    x_left[xleft_index] = px[i];
                    xleft_index++;
                } else {
                    x_right[xright_index] = px[i];
                    xright_index++;
                }
               
                
                if(v.point.y >= py[i].y) {
                    y_left[yleft_index] = py[i];
                    yleft_index++;
                } else {
                    y_right[yright_index] = py[i];
                    yright_index++;
                }
                
                if(v.point.y >= pz[i].y) {
                    z_left[zleft_index] = pz[i];
                    zleft_index++;
                } else {
                    z_right[zright_index] = pz[i];
                    zright_index++;
                }
            }

            v.left = buildTree(x_left, y_left, z_left, depth + 1);
            v.right = buildTree(x_right, y_right, z_right, depth + 1);
            return v;
        }
        else {
            
            Node v = new Node(new Point(pz[mid_i].x, pz[mid_i].y, pz[mid_i].z), depth);
            
            int xleft_index = 0; 
            int yleft_index = 0;
            int xright_index = 0;
            int yright_index = 0;
            int zright_index = 0;
            int zleft_index = 0;
            
            for(int i = 0; i < pz.length; i++) {

                if(v.point.z >= px[i].z) {
                    x_left[xleft_index] = px[i];
                    xleft_index++;
                } else {
                    x_right[xright_index] = px[i];
                    xright_index++;
                }
                
                if(v.point.z >= py[i].z) {
                    y_left[yleft_index] = py[i];
                    yleft_index++;
                } else  {
                    y_right[yright_index] = py[i];
                    yright_index++;
                }
                
                if(v.point.z >= pz[i].z) {
                    z_left[zleft_index] = pz[i];
                    zleft_index++;
                } else {
                    z_right[zright_index] = pz[i];
                    zright_index++;
                }
            }
            
            v.left = buildTree(x_left, y_left, z_left, depth + 1);
            v.right = buildTree(x_right, y_right, z_right, depth + 1);
            return v;
        }
        
        
        
    
    }
    
    
    
    public static void mergeSort(Point[] a, int n, int dimension) {
        if (n < 2) {
            return;
        }
        int mid = n / 2;
        Point[] l = new Point[mid];
        Point[] r = new Point[n - mid];

        for (int i = 0; i < mid; i++) {
            l[i] = a[i];
        }
        for (int i = mid; i < n; i++) {
            r[i - mid] = a[i];
        }
        mergeSort(l, mid, dimension);
        mergeSort(r, n - mid, dimension);

        merge(a, l, r, mid, n - mid, dimension);
    }
    
    /**
     * Merges the sub-arrays in a sorted manner
     * 
     * @return
     */
    public static void merge(
        Point[] a, Point[] l, Point[] r, int left, int right, int dimension) {
       
          int i = 0, j = 0, k = 0;
          while (i < left && j < right) {
              int l_val;
              int r_val;
              
              
              if(dimension % 3 == 0 ) {
                  l_val = l[i].getX();
                  r_val = r[j].getX();
              } else if(dimension % 3 == 1) {
                  l_val = l[i].getY();
                  r_val = r[j].getY();
              } else {
                  l_val = l[i].getZ();
                  r_val = r[j].getZ();
              }
              
              
              if (l_val <= r_val) {
                  a[k++] = l[i++];
              }
              else {
                  a[k++] = r[j++];
              }
          }
          while (i < left) {
              a[k++] = l[i++];
          }
          while (j < right) {
              a[k++] = r[j++];
          }
      }
    
    /**
     * Gets the median of the array
     * @param coords_arr
     * @return
     */
    private Point getMedian(Point[] coords_arr) {
        int n = coords_arr.length;
        return coords_arr[(n / 2) - 1]; 
    }
    
    
    public int rangeCount(Prism R, Node v, Prism cell) {
        if(v == null) {
            return 0;
        }
        else if(R.isDisjoint(cell, 0)) {
            return 0;
        }
        else if(R.contains(cell, 0)) {
            return v.py.length;
        }
        else {
            int count = 0;
            if (R.contains(v.point, count) && v.isLeaf()) // consider this point
                count += 1;
            count += rangeCount(R, v.left, cell.leftPart(v.depth, v.point));
            count += rangeCount(R, v.right, cell.rightPart(v.depth, v.point));
            return count;
        }
        
        
    }
    
}
