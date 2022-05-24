package microchess;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


// this driver class takes care of GUI, this is the primary class of this microchess
public class Driver implements ActionListener{
	
	public static boolean started = false;
	public static int row = 5;
    public static int column = 4;
    public boolean isWhiteTurn = true;
	
	JFrame f = new JFrame("Microchess"); // create an empty window
	
	JPanel battleField = new JPanel(new GridLayout(row, column));	// create a battle field
	
	// areas on a battlefield
	JButton aa = new JButton();
	JButton ab = new JButton();
	JButton ac = new JButton();
	JButton ad = new JButton();
	JButton ba = new JButton();
	JButton bb = new JButton();
	JButton bc = new JButton();
	JButton bd = new JButton();
	JButton ca = new JButton();
	JButton cb = new JButton();
	JButton cc = new JButton();
	JButton cd = new JButton();
	JButton da = new JButton();
	JButton db = new JButton();
	JButton dc = new JButton();
	JButton dd = new JButton();
	JButton ea = new JButton();
	JButton eb = new JButton();
	JButton ec = new JButton();
	JButton ed = new JButton();
	
	JButton[][] buttons= {{aa,ab,ac,ad},
						  {ba,bb,bc,bd},
						  {ca,cb,cc,cd},
						  {da,db,dc,dd},
						  {ea,eb,ec,ed}};
	
	
	JPanel textField = new JPanel();	// create a first text field
	JLabel text = new JLabel();  // text label
	JButton start = new JButton("START"); // start button
	
	// pieces
	BlackKing blackKing = new BlackKing();
	WhiteKing whiteKing = new WhiteKing();
	WhitePawn whitePawn = new WhitePawn();
	BlackPawn blackPawn = new BlackPawn();
	WhiteRunner whiteRunner = new WhiteRunner();
	BlackRunner blackRunner = new BlackRunner();
	WhiteJumper whiteJumper = new WhiteJumper();
	BlackJumper blackJumper = new BlackJumper();
	WhiteTower whiteTower = new WhiteTower();
	BlackTower blackTower = new BlackTower();
	
	
	// pieces and piecesIcons' index corresponds to each other
	Figure[] whitePieces = {whiteKing,whitePawn,whiteRunner,whiteJumper,whiteTower};
	Figure[] blackPieces = {blackKing,blackPawn,blackRunner,blackJumper,blackTower};
		
	
	// store highlighted areas
	ArrayList<JButton> highlightedArea = new ArrayList<JButton>();
	
	
	JFrame frame;
	JButton whiteRunnerPromo;
	JButton whiteJumperPromo;
	JButton whiteTowerPromo;
	
	JButton blackRunnerPromo;
	JButton blackJumperPromo;
	JButton blackTowerPromo;
	

	// constructor method
	public Driver() throws IOException {
		f.setBounds(0, 0, 400, 600);
		f.getContentPane().setLayout(null);
		
		battleField.setBounds(0,0,388,465);
			
		paintField();
		
	    f.add(battleField);
	   
	    // add text field to the window
		textField.setLayout(null);
		textField.setBackground(Color.white);
		textField.setBounds(0, 465, 385, 100);
		textField.setBorder(BorderFactory.createLineBorder(Color.black, 5));
		f.add(textField);
		
		// add text to the text field
		text.setText("Welcome!");
		text.setForeground(Color.black);
		text.setFont(new Font("Serif", Font.BOLD, 40));
		text.setBounds(70, 23, 300, 50);
		textField.add(text);
		
		// add a button to the text field
		start.setBounds(270, 35, 85, 30);
		start.setBackground(Color.white);
		start.setForeground(Color.black);
		start.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		textField.add(start);

		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true); // make it visible
		
	}

	// main method, waits for the click to start
	public static void main(String[] args) throws IOException {
		Driver driver = new Driver();
		
		driver.start.addActionListener(driver);
		
		// wait until the start button is clicked
		while(started == false){
		    try {
		       Thread.sleep(200);
		    } catch(InterruptedException e) {
		    	System.out.println("Click the button to start");
		    }
		}
		// the code below will be executed after clicking the start button
			driver.game();
	}
	
	// check if it is checkmate, or if a king is killed
	public void checkmateChecker() {
		if (whiteKing.getX() == -10) {
			JOptionPane.showMessageDialog(null,
				    "Chekmate! Player Black Won!",
				    "Checkmate",
				    JOptionPane.PLAIN_MESSAGE);
		}
		if (blackKing.getX() == -10) {
			JOptionPane.showMessageDialog(null,
				    "Chekmate! Player White Won!",
				    "Checkmate",
				    JOptionPane.PLAIN_MESSAGE);
		}
	}
	
	// highlight the area where a piece can move
	public void highlighter(int[][] coveredArea, Figure selectedPiece, boolean isWhite) {
		// when other areas have been highlighted right before, clear the highlighted history
		if (highlightedArea.isEmpty() == false) {
			highlightedArea.clear();
		}
		
		boolean pieceDetected = false;
		
		for (int i=0; i<coveredArea.length; i++) {
			int x = coveredArea[i][0];
			int y = coveredArea[i][1];
			boolean ownPieceOn = false;
			boolean opponentPieceOn = false;

			// if the area exists in the battle field 
			// and if user's own piece are not on the area where user wants a piece to move
			// -> highlight
			if (x>=0 && x<=3 && y>=0 && y<=4) {
				// if there is a user's own piece in the attack range, do not highlight the place
				if (isWhite) {
					for(int j=0; j<whitePieces.length; j++) {
				        if (whitePieces[j].getX()==x && whitePieces[j].getY()==y) {
				        	ownPieceOn = true;
				        }
					}
					if (selectedPiece.getClass().equals(whitePawn.getClass())) {
						
						for(int j=0; j<blackPieces.length; j++) {
					        if (blackPieces[j].getX()==x && blackPieces[j].getY()==y) {
					        	opponentPieceOn = true;
					        }
						}
						
						// if there is a piece on either right front or left front, highlight
						int leftFrontX = whitePawn.getX()-1;
						int leftFrontY = whitePawn.getY()-1;
						int rightFrontX = whitePawn.getX()+1;
						int rightFrontY = whitePawn.getY()-1;
						if (leftFrontX>=0 && leftFrontX<=3 && leftFrontY>=0 && leftFrontY<=4) {
							for(int j=0; j<blackPieces.length; j++) {
						        if (blackPieces[j].getX()==leftFrontX && blackPieces[j].getY()==leftFrontY) {
						        	buttons[leftFrontY][leftFrontX].setBackground(Color.yellow);
									highlightedArea.add(buttons[leftFrontY][leftFrontX]);
						        }
							}
						}
						if (rightFrontX>=0 && rightFrontX<=3 && rightFrontY>=0 && rightFrontY<=4) {
							for(int j=0; j<blackPieces.length; j++) {
						        if (blackPieces[j].getX()==rightFrontX && blackPieces[j].getY()==rightFrontY) {
						        	buttons[rightFrontY][rightFrontX].setBackground(Color.yellow);
									highlightedArea.add(buttons[rightFrontY][rightFrontX]);
						        }
							}
						}
						
						if (!ownPieceOn && !opponentPieceOn) {
				        	buttons[y][x].setBackground(Color.yellow);
							highlightedArea.add(buttons[y][x]);
						}
						// if it is the first move and there is a piece in front -> do not highlight the 2nd section
						else {
							break;
						}
					}
					else if (selectedPiece.getClass().equals(whiteRunner.getClass())) {
						// don't highlight the rest of the diagonal line when a piece is detected on the line
						if (pieceDetected == true) {
							if (i%3 == 0) {
								pieceDetected = false;
							}
							else {
								continue;
							}
						}
						
						for(int j=0; j<blackPieces.length; j++) {
					        if (blackPieces[j].getX()==x && blackPieces[j].getY()==y) {
					        	opponentPieceOn = true;
					        }
						}
						if (!ownPieceOn) {
				        	buttons[y][x].setBackground(Color.yellow);
							highlightedArea.add(buttons[y][x]);
						}
						// runners cannot jump another piece
						if (ownPieceOn || opponentPieceOn) {
							pieceDetected = true;
						}
					}
					else if (selectedPiece.getClass().equals(whiteTower.getClass())) {
						// don't highlight the rest of the diagonal line when a piece is detected on the line
						if (pieceDetected == true) {
							if (i%4 == 0) {
								pieceDetected = false;
							}
							else {
								continue;
							}
						}
						
						for(int j=0; j<blackPieces.length; j++) {
					        if (blackPieces[j].getX()==x && blackPieces[j].getY()==y) {
					        	opponentPieceOn = true;
					        }
						}
						if (!ownPieceOn) {
				        	buttons[y][x].setBackground(Color.yellow);
							highlightedArea.add(buttons[y][x]);
						}
						// runners cannot jump another piece
						if (ownPieceOn || opponentPieceOn) {
							pieceDetected = true;
						}
					}
					else {
						if (!ownPieceOn) {
				        	buttons[y][x].setBackground(Color.yellow);
							highlightedArea.add(buttons[y][x]);
						}
					}
				}
				else {
					for(int j=0; j<blackPieces.length; j++) {
				        if (blackPieces[j].getX()==x && blackPieces[j].getY()==y) {
				        	ownPieceOn = true;
				        }
					}
					if (selectedPiece.getClass().equals(blackPawn.getClass())) {
						for(int j=0; j<whitePieces.length; j++) {
					        if (whitePieces[j].getX()==x && whitePieces[j].getY()==y) {
					        	opponentPieceOn = true;
					        }
						}
						
						// if there is a piece on either right front or left front, highlight
						int leftFrontX = blackPawn.getX()+1;
						int leftFrontY = blackPawn.getY()+1;
						int rightFrontX = blackPawn.getX()-1;
						int rightFrontY = blackPawn.getY()+1;
						if (leftFrontX>=0 && leftFrontX<=3 && leftFrontY>=0 && leftFrontY<=4) {
							for(int j=0; j<whitePieces.length; j++) {
						        if (whitePieces[j].getX()==leftFrontX && whitePieces[j].getY()==leftFrontY) {
						        	buttons[leftFrontY][leftFrontX].setBackground(Color.yellow);
									highlightedArea.add(buttons[leftFrontY][leftFrontX]);
						        }
							}
						}
						if (rightFrontX>=0 && rightFrontX<=3 && rightFrontY>=0 && rightFrontY<=4) {
							for(int j=0; j<whitePieces.length; j++) {
						        if (whitePieces[j].getX()==rightFrontX && whitePieces[j].getY()==rightFrontY) {
						        	buttons[rightFrontY][rightFrontX].setBackground(Color.yellow);
									highlightedArea.add(buttons[rightFrontY][rightFrontX]);
						        }
							}
						}
						
						if (!ownPieceOn && !opponentPieceOn) {
				        	buttons[y][x].setBackground(Color.yellow);
							highlightedArea.add(buttons[y][x]);
						}
						// if it is the first move and there is a piece in front -> do not highlight the 2nd section
						else {
							break;
						}
					}
					else if (selectedPiece.getClass().equals(blackRunner.getClass())) {
						// don't highlight the rest of the diagonal line when a pieces detected on the line
						if (pieceDetected == true) {
							if (i%3 == 0) {
								pieceDetected = false;
							}
							else {
								continue;
							}
						}
						
						for(int j=0; j<whitePieces.length; j++) {
					        if (whitePieces[j].getX()==x && whitePieces[j].getY()==y) {
					        	opponentPieceOn = true;
					        }
						}
						if (!ownPieceOn) {
				        	buttons[y][x].setBackground(Color.yellow);
							highlightedArea.add(buttons[y][x]);
						}
						// runners cannot jump another piece
						if (ownPieceOn || opponentPieceOn) {
							pieceDetected = true;
						}
					}
					else if (selectedPiece.getClass().equals(blackTower.getClass())) {
						// don't highlight the rest of the diagonal line when a pieces detected on the line
						if (pieceDetected == true) {
							if (i%4 == 0) {
								pieceDetected = false;
							}
							else {
								continue;
							}
						}
						
						for(int j=0; j<whitePieces.length; j++) {
					        if (whitePieces[j].getX()==x && whitePieces[j].getY()==y) {
					        	opponentPieceOn = true;
					        }
						}
						if (!ownPieceOn) {
				        	buttons[y][x].setBackground(Color.yellow);
							highlightedArea.add(buttons[y][x]);
						}
						// runners cannot jump another piece
						if (ownPieceOn || opponentPieceOn) {
							pieceDetected = true;
						}
					}
					else {
						if (!ownPieceOn) {
				        	buttons[y][x].setBackground(Color.yellow);
							highlightedArea.add(buttons[y][x]);
						}
					}
				}
			}
		}
	}
	
	// set up the chess field
	public void paintField() {
		boolean white = true;
		for (int x=0; x<row; x++) {
			 for (int y=0; y<column; y++) {
				 if (white) {
					buttons[x][y].setBackground(Color.white);
				 }
				 else {
					buttons[x][y].setBackground(Color.gray);
				 }
				 buttons[x][y].setBorderPainted(false);
				 buttons[x][y].setFocusPainted(false);
				 battleField.add(buttons[x][y]);
				 white=!white;
			 }
			 white=!white;
		}
	}
	
	// this is an initial setup of the game board being executed after the start button is clicked
	public void game() throws IOException {

		// make buttons on the game field work
		for (int x=0; x<row; x++) {
			 for (int y=0; y<column; y++) {
				 buttons[x][y].addActionListener(this);
			 }
		}
		
		// put pieces on the battle field
		blackKing.setImage(aa);
		blackKing.setX(0);
		blackKing.setY(0);
		
		whiteKing.setImage(ed);
		whiteKing.setX(3);
		whiteKing.setY(4);
		
		whitePawn.setImage(dd);
		whitePawn.setX(3);
		whitePawn.setY(3);
		
		blackPawn.setImage(ba);
		blackPawn.setX(0);
		blackPawn.setY(1);
		
		whiteRunner.setImage(eb);
		whiteRunner.setX(1);
		whiteRunner.setY(4);
		
		blackRunner.setImage(ac);
		blackRunner.setX(2);
		blackRunner.setY(0);
		
		whiteJumper.setImage(ec);
		whiteJumper.setX(2);
		whiteJumper.setY(4);
		
		blackJumper.setImage(ab);
		blackJumper.setX(1);
		blackJumper.setY(0);
		
		whiteTower.setImage(ea);
		whiteTower.setX(0);
		whiteTower.setY(4);
		
		blackTower.setImage(ad);
		blackTower.setX(3);
		blackTower.setY(0);
		
		// display player white's turn
		text.setText("Player white's turn");
		text.setForeground(Color.black);
		text.setFont(new Font("Serif", Font.BOLD, 32));

	}

	
	@Override
	// when a button is clicked, it acts accordingly
	public void actionPerformed(ActionEvent e) {
		// start game when the start button is clicked
		if (e.getSource() == start) {
			started = true;
			start.setVisible(false);
		}
		
		// pawn promotion

		else if (e.getSource() == whiteRunnerPromo) {
			// kill itself, put a new piece, and replace everything 
			int x = whitePawn.getX();
			int y = whitePawn.getY();
			buttons[y][x].setIcon(whiteRunner.icon);
			whitePawn.setX(-20);
			whitePawn.setY(-20);
			WhiteRunner promotedWhiteRunner = new WhiteRunner();
			promotedWhiteRunner.icon = whiteRunner.icon;
			// replace
			whitePieces[1] = promotedWhiteRunner;
			promotedWhiteRunner.setX(x);
			promotedWhiteRunner.setY(y);
						
			frame.dispose();
		}
		else if (e.getSource() == whiteJumperPromo) {
			// kill itself, put a new piece, and replace everything 
			int x = whitePawn.getX();
			int y = whitePawn.getY();
			buttons[y][x].setIcon(whiteJumper.icon);
			whitePawn.setX(-20);
			whitePawn.setY(-20);
			WhiteJumper promotedWhiteJumper = new WhiteJumper();
			promotedWhiteJumper.icon = whiteJumper.icon;
			// replace
			whitePieces[1] = promotedWhiteJumper;
			promotedWhiteJumper.setX(x);
			promotedWhiteJumper.setY(y);
						
			frame.dispose();
		}
		else if (e.getSource() == whiteTowerPromo) {
			// kill itself, put a new piece, and replace everything 
			int x = whitePawn.getX();
			int y = whitePawn.getY();
			buttons[y][x].setIcon(whiteTower.icon);
			whitePawn.setX(-20);
			whitePawn.setY(-20);
			WhiteTower promotedWhiteTower = new WhiteTower();
			promotedWhiteTower.icon = whiteTower.icon;
			// replace
			whitePieces[1] = promotedWhiteTower;
			promotedWhiteTower.setX(x);
			promotedWhiteTower.setY(y);
						
			frame.dispose();
		}
		else if (e.getSource() == blackRunnerPromo) {
			// kill itself, put a new piece, and replace everything 
			int x = blackPawn.getX();
			int y = blackPawn.getY();
			buttons[y][x].setIcon(blackRunner.icon);
			blackPawn.setX(-20);
			blackPawn.setY(-20);
			BlackRunner promotedBlackRunner = new BlackRunner();
			promotedBlackRunner.icon = blackRunner.icon;
			// replace
			blackPieces[1] = promotedBlackRunner;
			promotedBlackRunner.setX(x);
			promotedBlackRunner.setY(y);
						
			frame.dispose();
		}
		else if (e.getSource() == blackJumperPromo) {
			// kill itself, put a new piece, and replace everything 
			int x = blackPawn.getX();
			int y = blackPawn.getY();
			buttons[y][x].setIcon(blackJumper.icon);
			blackPawn.setX(-20);
			blackPawn.setY(-20);
			BlackJumper promotedBlackJumper = new BlackJumper();
			promotedBlackJumper.icon = blackJumper.icon;
			// replace
			blackPieces[1] = promotedBlackJumper;
			promotedBlackJumper.setX(x);
			promotedBlackJumper.setY(y);
						
			frame.dispose();
		}
		else if (e.getSource() == blackTowerPromo) {
			// kill itself, put a new piece, and replace everything 
			int x = blackPawn.getX();
			int y = blackPawn.getY();
			buttons[y][x].setIcon(blackTower.icon);
			blackPawn.setX(-20);
			blackPawn.setY(-20);
			BlackTower promotedBlackTower = new BlackTower();
			promotedBlackTower.icon = blackTower.icon;
			// replace
			blackPieces[1] = promotedBlackTower;
			promotedBlackTower.setX(x);
			promotedBlackTower.setY(y);
						
			frame.dispose();
		}
		
		else { 
			// look for the place clicked on the field
			for(int i=0; i<buttons.length; i++) {
		        for(int j=0; j<buttons[i].length; j++) {
		        	if (e.getSource() == buttons[i][j]) {
		        		// check if this place contains a piece
		        		boolean pieceOn = false;
		        		boolean secondClick = false;
		        		boolean isWhite = false;
		        		Figure selectedPiece = null;
		        		
		        		for (int k=0; k<blackPieces.length; k++) {
	        				if (blackPieces[k].getX() == j && blackPieces[k].getY() == i) {
	        					pieceOn = true;
	        					selectedPiece = blackPieces[k];
	        					if (selectedPiece.selected == true) {
	        						secondClick = true;
	        					}
	        					else {
		        					selectedPiece.selected = true;	        						
	        					}
	        					break;
	        				}
		        		}
		        		
		        		for (int k=0; k<whitePieces.length; k++) {
	        				if (whitePieces[k].getX() == j && whitePieces[k].getY() == i) {
	        					pieceOn = true;
	        					isWhite = true;
	        					selectedPiece = whitePieces[k];
	        					if (selectedPiece.selected == true) {
	        						secondClick = true;
	        					}
	        					else {
		        					selectedPiece.selected = true;	        						
	        					}
	        					break;
	        				}
	        			}
		    
		        		// check if the clicked area has been highlighted
		        		boolean highlighted = false;
		        		if (highlightedArea.contains(buttons[i][j])) {
		        			highlighted = true;
		        		}
		        		
		        		// act accordingly
		        		
		        		if (highlighted == true) {
	        				// kill the piece
		        			if (pieceOn) {
		        				if (isWhite && !isWhiteTurn || !isWhite && isWhiteTurn) {
			        				selectedPiece.setX(-10);
			        				selectedPiece.setY(-10);
				        			buttons[i][j].setIcon(null);
		        				}
		        				selectedPiece.selected = false;
		        			}
		        			// identify the moving piece
		        			Figure movingPiece = null;
		        			boolean movingPieceIsWhite = false;
		        			
		        			for (int n=0; n<blackPieces.length; n++) {
		        				if (blackPieces[n].selected == true) {
		        					movingPiece = blackPieces[n];
		        					break;
		        				}
		        			}
		        		
		        			for (int m=0; m<whitePieces.length; m++) {
		        				if (whitePieces[m].selected == true) {
		        					movingPiece = whitePieces[m];
		        					movingPieceIsWhite = true;
		        					break;
		        				}
		        			}
		        		        			
		        			// move = delete the moving piece from the original place and setting the new icon to the selected place
		        			if (movingPieceIsWhite && isWhiteTurn || !movingPieceIsWhite && !isWhiteTurn) {
		        				buttons[movingPiece.getY()][movingPiece.getX()].setIcon(null);
			        			buttons[i][j].setIcon(movingPiece.icon);
			        			
			        			movingPiece.setX(j);
			        			movingPiece.setY(i);
			        			
			        			// dealing with pawn's characteristics
			        			if (movingPiece.equals(whitePawn)) {
			        				if (i==0) {
			        					// pawn promotion
										try {
											frame = new JFrame("Pawn Promotion");
											frame.setLayout(new GridLayout(1,3));
											
											whiteRunnerPromo = new JButton();
											whiteRunnerPromo.addActionListener(this);
											whiteRunnerPromo.setBackground(Color.gray);
											whiteRunnerPromo.setFocusPainted(false);
											whiteRunnerPromo.setIcon(whiteRunner.setImage(whiteRunnerPromo));
											
											whiteJumperPromo = new JButton();
											whiteJumperPromo.addActionListener(this);
											whiteJumperPromo.setBackground(Color.gray);
											whiteJumperPromo.setFocusPainted(false);
											whiteJumperPromo.setIcon(whiteJumper.setImage(whiteJumperPromo));
											
											whiteTowerPromo = new JButton();
											whiteTowerPromo.addActionListener(this);
											whiteTowerPromo.setBackground(Color.gray);
											whiteTowerPromo.setFocusPainted(false);
											whiteTowerPromo.setIcon(whiteTower.setImage(whiteTowerPromo));
											
											frame.add(whiteTowerPromo);
											frame.add(whiteJumperPromo);
											frame.add(whiteRunnerPromo);
											frame.setBounds(500, 200, 500, 175);
											frame.setVisible(true); // make it visible
											
										} catch (IOException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
			        				}
			        			}
			        			else if (movingPiece.equals(blackPawn)) {
			        				if (i==4) {
			        					// pawn promotion
										try {
											frame = new JFrame("Pawn Promotion");
											frame.setLayout(new GridLayout(1,3));
											
											blackRunnerPromo = new JButton();
											blackRunnerPromo.addActionListener(this);
											blackRunnerPromo.setBackground(Color.white);
											blackRunnerPromo.setFocusPainted(false);
											blackRunnerPromo.setIcon(blackRunner.setImage(blackRunnerPromo));
											
											blackJumperPromo = new JButton();
											blackJumperPromo.addActionListener(this);
											blackJumperPromo.setBackground(Color.white);
											blackJumperPromo.setFocusPainted(false);
											blackJumperPromo.setIcon(blackJumper.setImage(blackJumperPromo));
											
											blackTowerPromo = new JButton();
											blackTowerPromo.addActionListener(this);
											blackTowerPromo.setBackground(Color.white);
											blackTowerPromo.setFocusPainted(false);
											blackTowerPromo.setIcon(blackTower.setImage(blackTowerPromo));
											
											frame.add(blackTowerPromo);
											frame.add(blackJumperPromo);
											frame.add(blackRunnerPromo);
											frame.setBounds(500, 200, 500, 175);
											frame.setVisible(true); // make it visible
											
										} catch (IOException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
			        				}
			        			}
			        			
			        			// check if it is checkmate
			        			checkmateChecker();
			        			
			        			// change the text
			        			if (isWhiteTurn) {
			        				text.setText("Player black's turn");
			        			}
			        			else {
			        				text.setText("Player white's turn");
			        			}
			        			text.setForeground(Color.black);
			        			text.setFont(new Font("Serif", Font.BOLD, 32));
			        			isWhiteTurn = !isWhiteTurn;
		        			}
		        			else {
		        				JOptionPane.showMessageDialog(null,
		        					    "It's not your turn yet.",
		        					    "Error",
		        					    JOptionPane.ERROR_MESSAGE);
		        			}
		        			
		        			highlightedArea.clear();
		        			paintField();
			        		movingPiece.selected = false;
		        		}
		        		
		        		else if (pieceOn == true && highlighted == false) {
		        			if (secondClick == false) {
		    					 //when another piece has been selected already, undo them
		        				for (int k=0; k<whitePieces.length; k++) {
		        					if (whitePieces[k].selected == true && !selectedPiece.equals(whitePieces[k])) {
		        						whitePieces[k].selected = false;
		        						paintField();
		        					}
		        				}
		        				for (int k=0; k<blackPieces.length; k++) {
		        					if (blackPieces[k].selected == true && !selectedPiece.equals(blackPieces[k])) {
		        						blackPieces[k].selected = false;
		        						paintField();
		        					}
		        				}

		        				highlighter(selectedPiece.coveredArea(selectedPiece.getX(), selectedPiece.getY()), selectedPiece, isWhite);
		        			}
		        			if (secondClick == true) {
		        				highlightedArea.clear();
		        				paintField();
		        				selectedPiece.selected = false;
		        			}
		        		}
		        		
		        		else if (pieceOn == false && highlighted == false) {
		        		}
		        		else {
		        			System.out.println("Something is wrong");
		        		}
		        	}
		        }
		    }
		}
	}
}