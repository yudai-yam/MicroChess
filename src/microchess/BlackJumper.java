package microchess;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

// this is a black jumper piece
public class BlackJumper extends Figure {

	@Override
	public int[][] coveredArea(int x, int y) {
		int [][] area = {{x-2, y-1},{x-1,y-2},{x+1,y-2},{x+2,y-1},
						{x+2,y+1},{x+1,y+2},{x-1,y+2},{x-2,y+1}};
		return area;
	}

	@Override
	public ImageIcon setImage(JButton button) throws IOException {
		Image img = ImageIO.read(getClass().getResource("/image/black_jumper.png")); 
		Image resizedImg = img.getScaledInstance(80, 80, Image.SCALE_SMOOTH); // scale it the smooth way  
		icon = new ImageIcon(resizedImg); 
		button.setIcon(icon);
		return icon;
	}

}
