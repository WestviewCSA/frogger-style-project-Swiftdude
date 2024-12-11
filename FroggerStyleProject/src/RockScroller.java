import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.net.URL;

public class RockScroller{
	private Image forward;
	private AffineTransform tx;
	
	int dir = 0; 					//0-forward, 1-backward, 2-left, 3-right
	int width, height;
	int x, y;						//position of the object
	int vx, vy;						//movement variables
	double scaleWidth = 1.0;		//change to scale image
	double scaleHeight = 1.0; 		//change to scale image

	public RockScroller() {
		//use random to get random rock
		forward 	= getImage("/imgs/"+"rock1.png"); //load the image for Tree


		//alter these
		width = 100;
		height = 100;
		x = 0;
		y = 0;
		vx = 0;
		vy = 0;
		
		tx = AffineTransform.getTranslateInstance(0, 0);
		
		init(x, y); 				//initialize the location of the image
									//use your variables
		
	}
	public RockScroller(int x, int y, int vx) {
		this();
		this.x = x;
		this.y = y;
		this.vx = vx;
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
	
	public void paint(Graphics g) {
		//these are the 2 lines of code needed draw an image on the screen
		Graphics2D g2 = (Graphics2D) g;
		
		x+=vx;
		y+=vy;	
		
		//for infinite scrolling - teleport to the other side once it leaves the other side
		
		if(x > 600) {
			x = -150;
		}
		if(x<-150) {
			x = 600;
		}
		
		init(x,y);
		
		g2.drawImage(forward, tx, null);
		
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
			URL imageURL = RockScroller.class.getResource(path);
			tempImage = Toolkit.getDefaultToolkit().getImage(imageURL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tempImage;
	}

}
