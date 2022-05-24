package microchess;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

// this is a white tower piece
public class WhiteTower extends Figure {

	@Override
	public int[][] coveredArea(int x, int y) {
		int [][] area = {{x,y-1},{x,y-2},{x,y-3},{x,y-4},
						{x+1,y},{x+2,y},{x+3,y},{x+4,y},
						{x,y+1},{x,y+2},{x,y+3},{x,y+4},
						{x-1,y},{x-2,y},{x-3,y},{x-4,y}};
		return area;
	}

	@Override
	public ImageIcon setImage(JButton button) throws IOException {
		Image img = ImageIO.read(getClass().getResource("/image/white_tower.png")); 
		Image resizedImg = img.getScaledInstance(80, 80, Image.SCALE_SMOOTH); // scale it the smooth way  
		icon = new ImageIcon(resizedImg); 
		button.setIcon(icon);
		return icon;
	}

}
