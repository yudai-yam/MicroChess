package microchess;

import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JButton;

// this class integrates all the pieces in this chess game
public abstract class Figure {

	private int x,y;
	
	public boolean selected = false;
	
	public ImageIcon icon;
	
	public abstract int[][] coveredArea(int x, int y);
	
	public abstract ImageIcon setImage(JButton button) throws IOException;
	// the images might be a bit blurry, depending on the screen you use
	
	
	public void setX(int x) {
        this.x = x;
    }
	public void setY(int y) {
        this.y = y;
    }
    public int getX() {
        return this.x; 
    }
    public int getY() {
        return this.y; 
    }
	
}
