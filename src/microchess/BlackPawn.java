package microchess;

import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

// this is a black pawn piece
public class BlackPawn extends Figure {

	@Override
	public int[][] coveredArea(int x, int y) {
		int area[][];
		if (y == 1) {
			area =new int[][] {{x, y+1},{x,y+2}};
		}
		else {
			area =new int[][] {{x, y+1}};			
		}
		return area;
	}

	@Override
	public ImageIcon setImage(JButton button) throws IOException {
		Image img = ImageIO.read(getClass().getResource("/image/black_pawn.png")); 
		Image resizedImg = img.getScaledInstance(80, 80, Image.SCALE_SMOOTH); // scale it the smooth way  
		icon = new ImageIcon(resizedImg); 
		button.setIcon(icon);
		return icon;
	}

}
