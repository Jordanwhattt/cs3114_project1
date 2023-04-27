package prj3;


public class Point {
    public int x, y, z;
    
    public Point(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public int getZ() {
        return this.z;
    }
    
    
    public double distance(Point q) {
        return Math.sqrt(Math.pow(q.x, 2) + Math.pow(q.y, 2) + Math.pow(q.z, 2));
    }
    

}