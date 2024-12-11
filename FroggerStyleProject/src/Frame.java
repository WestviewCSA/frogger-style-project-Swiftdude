import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Frame extends JPanel implements ActionListener, MouseListener, KeyListener {
	
	public static boolean debugging = false;
	
	//font
	Font timeFont = new Font("Courier", Font.BOLD, 40);
	
	
	
	
	SimpleAudioPlayer backgroundMusic = new SimpleAudioPlayer("scifi.wav", false);
//	Music soundBang = new Music("bang.wav", false);
//	Music soundHaha = new Music("haha.wav", false);
	
	
	//Background
	RockBg[] rock1 = new RockBg[6];
	RockBg[] rock2 = new RockBg[6];
	RockBg[] rock3 = new RockBg[6];
	RockBg[] rock4 = new RockBg[6];
	RockBg[] rock5 = new RockBg[6];
	RockBg[] rock6 = new RockBg[6];
	RockBg[] rock7 = new RockBg[6];
	EndArrow arrow = new EndArrow();
	
	//Win screen
	WinScreen winscreen = new WinScreen();
	//Lose screen
	LoseScreen losescreen = new LoseScreen();
	
	//Lava
	Lava[] lava1 = new Lava[6];
	Lava[] lava2 = new Lava[6];
	
	//Rideable "logs"
	ArrayList<RockScroller> row1 = new ArrayList<RockScroller>();
	RockScroller[] row2 = new RockScroller[3];
	
	//Swords flying across the screen
	EnemyScroller[] sword1 = new EnemyScroller[3];
	EnemyScroller[] sword2 = new EnemyScroller[3];
	EnemyScroller[] sword3 = new EnemyScroller[2];
	EnemyScroller[] sword4 = new EnemyScroller[2];
	EnemyScroller[] sword5 = new EnemyScroller[2];
	EnemyScroller[] sword6 = new EnemyScroller[3];
	
	Knight knight = new Knight(275, 710);
	
	//frame width/height
	int width = 600;
	int height = 850;	
	int lives = 3;
	boolean playable = true;
	//not riding by default
	boolean riding = false;
	boolean restartReady = false;
	boolean finished = false;
	boolean lost = false;

	public void paint(Graphics g) {
		super.paintComponent(g);
		if(finished) {
			if(lost) {
				losescreen.paint(g);
			}else {
				winscreen.paint(g);
			}
			g.setFont(timeFont);
			g.setColor(Color.white);
			g.drawString("Press Space to Restart", 10, 785);
		}

		//Win/lose
		if(lives <= 0) {
			playable=false;
			restartReady = true;
			finished = true;
			lost = true;
		}
		if(knight.y < 0) {
			restartReady = true;
			playable = false;
			finished = true;
			lost = false;
		}

		if(playable) {
			riding = false;
			//border checking
			if(knight.x < 0) {
				knight.x = 0;
			}
			if(knight.x > 540) {
				knight.x = 540;
			}
			if(knight.y > 710) {
				knight.y = 710;
			}
			
			//paint bg
			for(RockBg obj : rock1) {
				obj.paint(g);
			}
			for(RockBg obj : rock2) {
				obj.paint(g);
			}
			for(RockBg obj : rock3) {
				obj.paint(g);
			}
			for(RockBg obj : rock4) {
				obj.paint(g);
			}
			for(RockBg obj : rock5) {
				obj.paint(g);
			}
			for(RockBg obj : rock6) {
				obj.paint(g);
			}
			for(RockBg obj : rock7) {
				obj.paint(g);
			}
			for(Lava obj : lava1) {
				obj.paint(g);
			}
			for(Lava obj : lava2) {
				obj.paint(g);
			}
			
			
			//scrollers
			for(RockScroller obj : row1) {
				obj.paint(g);
			}
			for(RockScroller obj : row2) {
				obj.paint(g);
			}
			for(EnemyScroller obj : sword1) {
				obj.paint(g, 1);
			}
			for(EnemyScroller obj : sword2) {
			obj.paint(g, 2);
			}
			for(EnemyScroller obj : sword3) {
				obj.paint(g, 0);
			}
			for(EnemyScroller obj : sword4) {
				obj.paint(g, 0);
			}
			for(EnemyScroller obj : sword5) {
				obj.paint(g, 0);
			}
			for(EnemyScroller obj : sword6) {
				obj.paint(g, 2);
			}

			
			//collision
			
			for(EnemyScroller obj : sword1) {
				if(obj.collided(knight)) {
					knight.x = 275;
					knight.y = 710;
					lives--;
				}
			}
			for(EnemyScroller obj : sword2) {
				if(obj.collided(knight)) {
					knight.x = 275;
					knight.y = 710;
					lives--;
				}
			}
			for(EnemyScroller obj : sword3) {
				if(obj.collided(knight)) {
					knight.x = 275;
					knight.y = 710;
					lives--;
				}
			}
			for(EnemyScroller obj : sword4) {
				if(obj.collided(knight)) {
					knight.x = 275;
					knight.y = 710;
					lives--;
				}
			}
			for(EnemyScroller obj : sword5) {
				if(obj.collided(knight)) {
					knight.x = 275;
					knight.y = 710;
					lives--;
				}
			}
			for(RockScroller obj : row1) {
				if(obj.collided(knight)) {
					knight.vx = obj.vx;
					riding = true;
				}
			}
			for(RockScroller obj : row2) {
				if(obj.collided(knight)) {
					knight.vx = obj.vx;
					riding = true;
				}
			}
			
			
			//Lava
			for(Lava obj : lava1) {
				if(obj.collided(knight) && !riding) {
					knight.x = 275;
					knight.y = 710;
					lives--;
				}
			}
			for(Lava obj : lava2) {
				if(obj.collided(knight) && !riding) {
					knight.x = 275;
					knight.y = 710;
					lives--;
				}
			}
			
			//not riding
			if(!riding) {
				knight.vx = 0;
			}
			arrow.paint(g);
			knight.paint(g);
			//Draw lives
			g.setFont(timeFont);
			g.setColor(Color.white);
			g.drawString("Lives: " + lives, 10, 785);
		}
		
		
	}
	
	public static void main(String[] arg) {
		Frame f = new Frame();
		
	}
	
	public Frame() {
		JFrame f = new JFrame("Duck Hunt");
		f.setSize(new Dimension(width, height));
		f.setBackground(Color.white);
		f.add(this);
		f.setResizable(false);
 		f.addMouseListener(this);
		f.addKeyListener(this);
	
		//backgroundMusic.play();

	
		//setup any 1D array here
		
		
		//Scrollers
		for(int i = 0; i < 3; i++) {
			this.row1.add(new RockScroller(i*200, 300, 2));
		}
		for(int i = 0; i < row2.length; i++) {
			row2[i] = new RockScroller(i*200, 200, -2);
		}
		for(int i = 0; i < sword1.length; i++) {
			sword1[i] = new EnemyScroller(i*300, 538, -4, 0);
		}
		for(int i = 0; i < sword2.length; i++) {
			sword2[i] = new EnemyScroller(i*300, 638, 3, 0);
		}
		for(int i = 0; i < sword6.length; i++) {
			sword6[i] = new EnemyScroller(i*300, 138, 4, 0);
		}
		
		//swords going down
		for(int i = 0; i < sword3.length; i++) {
			sword3[i] = new EnemyScroller(100, i*500, 0, 2);
		}
		for(int i = 0; i < sword4.length; i++) {
			sword4[i] = new EnemyScroller(210, i*500-100, 0, 4);
		}
		for(int i = 0; i < sword5.length; i++) {
			sword5[i] = new EnemyScroller(500, i*500-300, 0, 3);
		}
		
		//bg
		for(int i = 0; i < rock1.length; i++) {
			rock1[i] = new RockBg(i*100, 0);
		}
		for(int i = 0; i < rock2.length; i++) {
			rock2[i] = new RockBg(i*100, 100);
		}
		for(int i = 0; i < rock3.length; i++) {
			rock3[i] = new RockBg(i*100, 400);
		}
		for(int i = 0; i < rock4.length; i++) {
			rock4[i] = new RockBg(i*100, 500);
		}
		for(int i = 0; i < rock5.length; i++) {
			rock5[i] = new RockBg(i*100, 600);
		}
		for(int i = 0; i < rock6.length; i++) {
			rock6[i] = new RockBg(i*100, 700);
		}
		for(int i = 0; i < rock7.length; i++) {
			rock7[i] = new RockBg(i*100, 800);
		}
		for(int i = 0; i < lava1.length; i++) {
			lava1[i] = new Lava(i*100, 200);
		}
		for(int i = 0; i < lava2.length; i++) {
			lava2[i] = new Lava(i*100, 300);
		}
		
		//the cursor image must be outside of the src folder
		//you will need to import a couple of classes to make it fully 
		//functional! use eclipse quick-fixes
		setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
				new ImageIcon("torch.png").getImage(),
				new Point(0,0),"custom cursor"));	
		
		
		Timer t = new Timer(16, this);
		t.start();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
	
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent m) {
		
	
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getKeyCode()==87) {
			//move up
			knight.move(0);
		}else if(arg0.getKeyCode()==83) {
			//move down
			knight.move(1);
		}else if(arg0.getKeyCode()==65) {
			//move down
			knight.move(2);
		}else if(arg0.getKeyCode()==68) {
			//move down
			knight.move(3);
		}else if(arg0.getKeyCode()==32&&restartReady) {
			playable = true;
			restartReady = false;
			lives = 3;
			knight.x = 275;
			knight.y = 710;
			
		}
		
		
		
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
