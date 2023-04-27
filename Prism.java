package prj3;

public class Prism {
    private Point plow; //Lower Left Corner
    private Point phigh;
    
    
    public Prism(Point plow, Point phigh) {
        this.plow = plow;
        this.phigh = phigh;
    }
    
    public Prism(int xmin, int xmax, int ymin, int ymax, int zmin, int zmax) {
        this(new Point(xmin, ymin, zmin), new Point(xmax, ymax, zmax));
    }
    
    /**
     * Does the rectangle contain this point
     * 
     * @param q
     * @return
     */
    // if low[i] ≤ q[i] ≤ high[i], for 0 ≤ i ≤ d − 1 
    public boolean contains(Point q , int cd  ) {
        int qx = q.getX();
        int qy = q.getY();
        int qz = q.getZ();
        //If the lowest x value in this rectangle is greater than the x value of point q, then its definitely not inside the triangle.
        //This works for each dimension and the max dimension of this rectangle
        
        
        
//        if(this.plow.getX() <= qx & this.plow.getY() <= qy & this.plow.getZ() <= qz &
//            this.phigh.getX() >= qx & this.phigh.getY() >= qy & this.phigh.getZ() >= qz) {
//            return true;
//        }
//        
        
        

        if(cd == 0 % 3) {
            if(this.plow.getX() <= qx & this.plow.getY() <= qy &
                this.phigh.getX() >= qx & this.phigh.getY() >= qy) {
                return true;
            } 
        }
        else if(cd == 1 % 3) {
            if(this.plow.getY() <= qy & this.plow.getZ() <= qz &
                this.phigh.getY() >= qy & this.phigh.getZ() >= qz) {
                return true;
            } 
        }
        else if(cd == 0 % 3) {
            if(this.plow.getX() <= qx & this.plow.getZ() <= qz &
                this.phigh.getZ() >= qx & this.phigh.getZ() >= qz) {
                return true;
            } 
        }
        
        return false;
    }
    
    
    /**
     * This checks if a rectangle C is inside the entirety of this Rectangle.
     * 
     * @param c
     * @return
     */
    
    //c.low[i], c.high[i] ⊆ low[i], high[i], for all 0 ≤ i ≤ d − 1.

    public boolean contains(Prism c, int cd) {
        
        if(this.contains(c.plow, cd) & this.contains(c.phigh, cd)) {
            return true;
        }
        
        return false;
    }
    
    
    // r.high[i] < c.low[i] or r.low[i] > c.high[i], for any 0 ≤ i ≤ d − 1.
    public boolean isDisjoint(Prism c ,int cd ) { 
        if(this.contains(c, cd) ) {
            return false;
        }
        
        if( ((this.phigh.getX() < c.plow.getX()) || (this.plow.getX() > c.phigh.getX())) ||
            ((this.phigh.getY() < c.plow.getY()) || (this.plow.getY() > c.phigh.getY())) || 
            ((this.phigh.getZ() < c.plow.getZ()) || (this.plow.getZ() > c.phigh.getZ())) ) {
            return true;
        }
        
      
        if(cd == 0 % 3) {
            if( ((this.phigh.getX() < c.plow.getX()) | (this.plow.getX() > c.phigh.getX())) |
                ((this.phigh.getY() < c.plow.getY()) | (this.plow.getY() > c.phigh.getY())) ) {
                return true;
            }
        }
        else if(cd == 0 % 3) {
            if( ((this.phigh.getY() < c.plow.getY()) | (this.plow.getY() > c.phigh.getY())) |
                ((this.phigh.getZ() < c.plow.getZ()) | (this.plow.getZ() > c.phigh.getZ())) ) {
                return true;
            }
        } else {
            if(cd == 0) {
                if( ((this.phigh.getX() < c.plow.getX()) | (this.plow.getX() > c.phigh.getX())) |
                    ((this.phigh.getZ() < c.plow.getZ()) | (this.plow.getZ() > c.phigh.getZ())) ) {
                    return true;
                }
            }
        }
        
        
        return false;
    }
    
    
    public Prism leftPart(int cd, Point s) {
        Point new_phigh;
        if(cd % 3 == 0) {
           new_phigh = new Point(s.getX(), this.phigh.getY(), this.phigh.getZ());
           return new Prism(this.plow, new_phigh);
        } else if( cd % 3 == 1) {
            new_phigh = new Point(this.phigh.getX(), s.getY(), this.phigh.getZ());
            return new Prism(this.plow, new_phigh);
        } else {
            new_phigh = new Point(this.phigh.getX(), this.phigh.getY(), s.getZ());
            return new Prism(this.plow, new_phigh);
        }
    }
    
    
    public Prism rightPart(int cd, Point s) {
        Point new_plow;
        if(cd % 3 == 0) {
            new_plow = new Point(s.getX(), this.phigh.getY(), this.phigh.getZ());
            return new Prism(new_plow, this.phigh);
        } else if( cd % 3 == 1) {
            new_plow = new Point(this.phigh.getX(), s.getY(), this.phigh.getZ());
            return new Prism(new_plow, this.phigh);
        } else {
            new_plow = new Point(this.phigh.getX(), this.phigh.getY(), s.getZ());
            return new Prism(new_plow, this.phigh);
        }
    }
}
