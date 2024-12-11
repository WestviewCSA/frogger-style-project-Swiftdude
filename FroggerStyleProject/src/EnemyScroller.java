import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class EnemyScroller{
	private Image down, left, right; 	
	private AffineTransform tx;
	
	int dir = 0; 					//0-forward, 1-backward, 2-left, 3-right
	int width, height;
	int x, y;						//position of the object
	int vx, vy;						//movement variables
	double scaleWidth = .17;		//change to scale image
	double scaleHeight = .17; 		//change to scale image

	public EnemyScroller() {
		down 	= getImage("/imgs/"+"sworddown.png"); //load the image for Tree
		left 	= getImage("/imgs/"+"swordleft.png"); //load the image for Tree
		right 	= getImage("/imgs/"+"swordright.png"); //load the image for Tree

		//alter these
		width = 0;
		height = 0;
		x = 0;
		y = 0;
		vx = 0;
		vy = 0;
		
		tx = AffineTransform.getTranslateInstance(0, 0);
		
		init(x, y); 				//initialize the location of the image
									//use your variables
		
	}
	
	public EnemyScroller(int x, int y, int vx, int vy) {
		this();
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
		if(vy == 0) {
			width = 110;
			height = 24;
		} else {
			width = 24;
			height = 110;
		}
		
	}
	
	public boolean collided(Knight knight) {
		Rectangle main = new Rectangle(
				knight.getX(),
				knight.getY(),
				knight.getWidth(),
				knight.getHeight()
				);
		
		Rectangle thisObject = new Rectangle(x, y, width, height);
		if(main.intersects(thisObject)) {
			return true;
		}
		return false;
	}

	public void paint(Graphics g, int dir) {
		//these are the 2 lines of code needed draw an image on the screen
		Graphics2D g2 = (Graphics2D) g;
		
		x+=vx;
		y+=vy;	
		
		init(x,y);
		
		switch(dir) {
		case 0://down
			g2.drawImage(down, tx, null);
			break;
		case 1://left
			g2.drawImage(left, tx, null);
			break;
		case 2://right
			g2.drawImage(right, tx, null);
			break;
		}
		
		if(x > 600) {
			x = -150;
		}
		if(x<-150) {
			x = 600;
		}
		if(y>850) {
			y=-150;
		}
		
		if(Frame.debugging) {
			g.setColor(Color.red);
			g.drawRect(x, y, width, height);
		}

	}
	
	private void init(double a, double b) {
		tx.setToTranslation(a, b);
		tx.scale(scaleWidth, scaleHeight);
	}

	private Image getImage(String path) {
		Image tempImage = null;
		try {
			URL imageURL = EnemyScroller.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}

}
