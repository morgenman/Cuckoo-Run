import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Timer;

public class Enemies extends Thread {
	private BufferedImage	cuckoos;
	private int				n;
	public cuckoo			c1, c2, c3, c4, c5;
	private Thread			t2;
	private Timer			timer;

	Enemies(int w, int h) {
		n = 0;
		w -= 1;
		h -= 250;
		cuckoos = new BufferedImage(w, h + 40, BufferedImage.TYPE_INT_ARGB);
		c1 = new cuckoo(w, h);
		c2 = new cuckoo(w, h);
		c3 = new cuckoo(w, h);
		c4 = new cuckoo(w, h);
		c5 = new cuckoo(w, h);
	}

	@Override
	public void run() {

		timer = new Timer(20, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				paint();
				collide();
			}
		});
		timer.start();

	}

	public Timer getTimer() {
		return timer;
	}

	public void collide() {
		int y = CuckooRun.getMain().getY1();
		int x = CuckooRun.getMain().getX1();
		cuckoo[] collection = { c1, c2, c3, c4, c5 };
		for (cuckoo i : collection) {
			CuckooRun.getMain();
			if (!i.collided && Canvas.range(x, x + 50, i.x, i.x + 40)) {
				if (Canvas.range(y, y + 50, i.y, i.y + 40)) {

					CuckooRun.getMain().getPlayer().setLivesleft(CuckooRun.getMain().getPlayer().getLivesleft() - 1);
					i.collided = true;
				}
			}
		}

	}

	public void startNow() {
		// System.out.println("Starting " + threadname);
		if (t2 == null) {
			t2 = new Thread(this, "enemies");
			t2.start();
		}
	}

	@SuppressWarnings("deprecation")
	public void stopNow() {
		t2.stop();
	}

	private void paint() {
		cuckoos = new BufferedImage(cuckoos.getWidth(), cuckoos.getHeight(), BufferedImage.TYPE_INT_ARGB);
		c1.move();
		c2.move();
		c3.move();
		c4.move();
		c5.move();
		if (n == 31) n = 0;
		if (n <= 15) {
			cuckoos.getGraphics().drawImage(Sprites.cuckoo(0), c1.x, c1.y, null);
			cuckoos.getGraphics().drawImage(Sprites.cuckoo(1), c2.x, c2.y, null);
			cuckoos.getGraphics().drawImage(Sprites.cuckoo(0), c3.x, c3.y, null);
			cuckoos.getGraphics().drawImage(Sprites.cuckoo(1), c4.x, c4.y, null);
			cuckoos.getGraphics().drawImage(Sprites.cuckoo(0), c5.x, c5.y, null);
			n++;
		}
		else if (n <= 30) {
			cuckoos.getGraphics().drawImage(Sprites.cuckoo(1), c1.x, c1.y, null);
			cuckoos.getGraphics().drawImage(Sprites.cuckoo(0), c2.x, c2.y, null);
			cuckoos.getGraphics().drawImage(Sprites.cuckoo(1), c3.x, c3.y, null);
			cuckoos.getGraphics().drawImage(Sprites.cuckoo(0), c4.x, c4.y, null);
			cuckoos.getGraphics().drawImage(Sprites.cuckoo(1), c5.x, c5.y, null);
			n++;
		}

	}

	public BufferedImage getCuckoos() {

		return cuckoos;
	}

}

class cuckoo {
	int		x, x_, y;
	int		dx			= -15;
	int		dy			= 0;
	int		n2			= 0;
	int		w, h;
	int		n3			= 1;
	boolean	collided	= false;

	cuckoo(int w, int h) {
		this.w = w;
		this.h = h;
		update();
	}

	private void update() {
		y = (int) (Math.random() * h);
		x = (int) (w + Math.random() * w * Math.random() * 40);
		x_ = x;
		collided = false;
	}

	void move() {

		if (x < -40) update();
		n2 += 1;
		switch (n2) {
		case 0:
		case 1:
		case 2:
		case 3:
			dy = -n3;
			break;
		case 4:
		case 5:
		case 6:
		case 7:
			dy = +n3;
			break;
		case 8:
		case 9:
		case 10:
		case 11:
		case 12:
		case 13:
		case 14:
		case 15:
			dy = -n3;
			n2 = 0;
			break;
		}

		x += dx;
		y += dy;
	}

}
