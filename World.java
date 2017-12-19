import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.Timer;

public class World extends Thread {
	private ArrayList<Slice> worldfloor;

	/**
	 * @return the worldfloor
	 */
	public ArrayList<Slice> getWorldfloor() {
		return worldfloor;
	}

	private Image			brick, brickL, brickLR, brickR, platform, temp;
	private Thread			t;
	private int				lastHeight;
	private int				lastpHeight;
	private BufferedImage	img			= new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_ARGB_PRE);
	private int				sliceWidth;
	private int				width;
	private Boolean			firstrun	= true;
	private int				gapsinarow;
	private Boolean			add			= true;
	private Timer			timer;
	private Boolean			enemy		= false;
	private Boolean			powerup		= false;
	private Boolean			test		= false;

	public Timer getTimer() {
		return timer;
	}

	private int i_ = 0;

	World(int width) {
		firstrun = true;
		add = true;
		img = new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_ARGB_PRE);
		lastHeight = 3;
		lastpHeight = 5;
		sliceWidth = 400;
		this.width = width;

		worldfloor = new ArrayList<Slice>();
		for (int i = 0; i < width / sliceWidth; i++) {
			worldfloor.add(i, generate_slice(i));
		}
		try { // add images
			brick = ImageIO.read(CuckooRun.class.getResource("brick1.png"));
			brickL = ImageIO.read(CuckooRun.class.getResource("brick1L.png"));
			brickLR = ImageIO.read(CuckooRun.class.getResource("brick1LR.png"));
			brickR = ImageIO.read(CuckooRun.class.getResource("brick1R.png"));
			platform = ImageIO.read(CuckooRun.class.getResource("platform.png"));

		}

		catch (Exception e1) {
		}
		worldfloor.get(0).setHeight(8);
		worldfloor.get(3).setHasEnemy(true);

	}

	public void saddSlices() {
		add = true;
	}

	public void addSlices() {
		int ws = worldfloor.size();
		for (int i = 0; i < 20; i++) {
			worldfloor.add(i + ws, generate_slice(i + worldfloor.size()));
		}
		firstrun = true;
		gen();
		add = false;

	}

	private Slice generate_slice(int i) {
		int height;
		int pheight = 0;
		if (lastHeight <= 8) height = (int) ((lastHeight - 2) + Math.random() * 4);
		else height = lastHeight;
		if ((int) (Math.random() * 2) == (1) && i > 4) height = -5;
		Slice temp;
		if (height < 0) gapsinarow++;
		if (gapsinarow > 0 && height > 0) {
			gapsinarow = 0;
		}
		else if (gapsinarow > 1) {
			if (lastpHeight <= 8) pheight = (int) ((lastpHeight - 2) + Math.random() * 4) + 4;
			else pheight = lastpHeight - (int) (Math.random() * 2 + 1);
		}
		else if ((int) (Math.random() * 5) == ((int) Math.random() * 5)) {
			pheight = (int) ((lastHeight + 3) + Math.random() * 2) + 2;
		}
		if ((int) (Math.random() * 5) == ((int) Math.random() * 5)) {
			enemy = true;
		}
		else enemy = false;
		if ((int) (Math.random() * 5) == ((int) Math.random() * 5)) {
			powerup = true;
		}
		else powerup = false;
		lastpHeight = pheight;
		// if (height < 0) pheight = 0;
		if (pheight != 0) temp = new Slice(height, enemy, true, pheight, powerup);
		else temp = new Slice(height, enemy, false, 0, powerup);
		return temp;
	}

	public Slice position(int locationx) {
		// System.out.println((int) -((locationx - CuckooRun.getMain().getX_()) / sliceWidth));

		return worldfloor.get((int) -((locationx - CuckooRun.getMain().getX_()) / sliceWidth));
		// return null;
	}

	public int brickeroo(int i) {
		// 0 for no sides 1 for left 2 for left and right and 3 for just right
		int hl, h, hr;
		if (i != 0) hl = worldfloor.get(i - 1).height;
		else hl = worldfloor.get(i).height;
		h = worldfloor.get(i).height;
		if (i < worldfloor.size() - 1) hr = worldfloor.get(i + 1).height;
		else hr = worldfloor.get(i).height;
		if (hl < h && h <= hr) return 1;
		else if (hl < h && h > hr) return 2;
		else if (h > hr) return 3;
		else return 0;
	}

	public BufferedImage getlevel() {

		// img = g1.drawImage(img, 0, 0, null);
		return img;
	}

	public void reset() {
		worldfloor = new ArrayList<Slice>();
		for (int i = 0; i < 4000 / sliceWidth; i++) {
			worldfloor.add(i, generate_slice(i));
		}
		// i_ = 0;
		gen();
	}

	public void gen() {
		Graphics2D g1;
		int n = 0;
		img = new BufferedImage(worldfloor.size() * 400, 900, BufferedImage.TYPE_INT_ARGB_PRE);
		g1 = img.createGraphics();

		// System.out.println("Width: " + width + "\nN = " + (CuckooRun.getMain().getY_() -
		// CuckooRun.getMain().getN())/ 400);
		if (i_ > 0) i_ -= 7;
		for (; i_ < worldfloor.size(); i_++) {
			// g1.setColor(new Color((float) Math.random(), (float) Math.random(), (float) Math.random()));

			// g1.fillRect(n, 640 - (worldfloor[i].height * 50), sliceWidth, worldfloor[i].height * 50);
			n = i_ * sliceWidth;
			switch (brickeroo(i_)) {
			case 0:
				g1.drawImage(brick, n, 900 - (worldfloor.get(i_).height * 50), null);
				break;
			case 1:
				g1.drawImage(brickL, n, 900 - (worldfloor.get(i_).height * 50), null);
				break;
			case 2:
				g1.drawImage(brickLR, n, 900 - (worldfloor.get(i_).height * 50), null);
				break;
			case 3:
				g1.drawImage(brickR, n, 900 - (worldfloor.get(i_).height * 50), null);
				break;
			}
			g1.drawImage(platform, n, 900 - (worldfloor.get(i_).pheight * 50), null);

			if (worldfloor.get(i_).powerup) g1.drawImage(CuckooRun.getMain().getSprite().powerup(), n, 900 - (worldfloor
			        .get(i_).height * 50) - 120, null);
			n += sliceWidth;
		}
	}

	public void startNow() {
		// System.out.println("Starting " + threadname);
		if (t == null) {
			t = new Thread(this, "World");
			t.start();
		}
	}

	@Override
	public void run() {
		timer = new Timer(50, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (add) addSlices();

			}
		});
		timer.start();

	}

	public boolean powerup(int n) {
		CuckooRun.getMain();
		if (!worldfloor.get((int) -((n - CuckooRun.getMain().getX_()) / sliceWidth)).getHitPowerup()) {
			if (Canvas.range((n - CuckooRun.getMain().getX_()) - (((n - CuckooRun.getMain().getX_()) / sliceWidth) - 1)
			        * sliceWidth, 170, 230) && worldfloor.get((int) -((n - CuckooRun.getMain().getX_())
			                / sliceWidth)).powerup) {
				worldfloor.get((int) -((n - CuckooRun.getMain().getX_()) / sliceWidth)).setHitPowerup(true);
				CuckooRun.getMain().getPlayer().setLivesleft(CuckooRun.getMain().getPlayer().getLivesleft() + 1);
			}
		}
		// if (worldfloor.get((int) -((n - CuckooRun.getMain().getX_()) / sliceWidth)).powerup&&(n -
		// CuckooRun.getMain().getX_())-==);
		return false;
	}
}
