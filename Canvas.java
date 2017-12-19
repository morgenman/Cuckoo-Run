import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

public class Canvas extends JComponent {
	private int					x_, y_;
	private int					x1, y1, x1_, y1_, yc, xc;
	private double				vy;
	private int					floor;
	private double				gravity;
	private static final long	serialVersionUID	= 1L;
	private BufferedImage		level;
	private Boolean				firstrun			= true;
	private int					n					= 0;
	private World				world;
	private int					dn;
	boolean						stop				= false;
	private int					wallh;
	private int					wallhn;
	private int					wallph;
	private int					wallhn_;
	private Boolean				splat				= false;
	private int					j					= 0;
	private Background			bg;
	private Image				bg_;
	private long				timeend;
	private Player				player;
	private int					splatc				= 0;
	private Timer				timer;
	BufferedImage				temp12;
	private BufferedImage		gameo;
	private BufferedImage		i3;
	private long				starttime;
	private String				time;
	private Sprites				sprite;
	private Enemies				layer;
	boolean						powerup;

	/**
	 * @return the powerup
	 */
	public boolean isPowerup() {
		return powerup;
	}

	/**
	 * @param powerup
	 *            the powerup to set
	 */
	public void setPowerup(boolean powerup) {
		this.powerup = powerup;
	}

	/**
	 * @return the bg_
	 */
	public Image getBg_() {
		return bg_;
	}

	/**
	 * @param bg_
	 *            the bg_ to set
	 */
	public void setBg_(Image bg_) {
		this.bg_ = bg_;
	}

	public Canvas() {
		try { // add images

			gameo = ImageIO.read(CuckooRun.class.getResource("gameover.png"));
		}

		catch (IOException e1) {
			System.out.println("Error, images not in root folder");
		}
		setDoubleBuffered(true);
		center();

		x1 = x_;
		n = getWidth();
		player = new Player();
		world = new World(4000);
		y1 = 0;
		vy = 0;
		gravity = .5;
		setFocusable(true);
		sprite = new Sprites();
		bg = new Background();

		timer = new Timer(9, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				generateLevel();
				// //*////*//System.out.println("Gen: " + (System.nanoTime() - t1));

				if (!splat) center();
				// //*////*//System.out.println("Cen: " + (System.nanoTime() - t1));

				if (getWidth() > 0) {

					update();
					// //*////*//System.out.println("Upd: " + (System.nanoTime() - t1));

					repaint();

					// //*////*//System.out.println("Rpt: " + (System.nanoTime() - t1));
				}
			}
		});

		timer.start();
	}

	public void endgame() {

		timer.stop(); // stop main loop
		splat = false;
		i3 = new BufferedImage(1920, 1080, BufferedImage.TYPE_INT_RGB);
		Graphics2D g3 = i3.createGraphics(); // to draw game over to
		g3.setColor(Color.black);
		g3.fillRect(0, 0, 1920, 1080);
		g3.drawImage(gameo, (x_ - gameo.getWidth(null) / 4), (y_ - gameo.getHeight(null) / 2) / 2, gameo.getWidth(null)
		        / 2, gameo.getHeight(null) / 2, null); // center
		g3.setColor(new Color(50, 50, 50));
		g3.setFont(new Font("Impact", Font.PLAIN, 150));
		FontMetrics fontMetrics = g3.getFontMetrics();
		fontMetrics = g3.getFontMetrics();
		g3.drawString("Time: " + Long.toString(timeend) + "s", getWidth() / 2 - fontMetrics.stringWidth("Time: " + Long
		        .toString(timeend) + "s") / 2, y_ - 20);

		repaint();

	}

	public void newgame() {
		timer.stop();
		layer.getTimer().stop();
		layer.stopNow();
		layer = new Enemies(getWidth(), getHeight());
		// layer.startNow();
		world.getTimer().stop();
		// world.reset();
		world = new World(4000);
		firstrun = true;
		y_ = 0;
		x_ = getWidth() / 2;
		splat = false;
		sprite.reset();
		// System.out.println("yello");
		setDoubleBuffered(true);
		center();
		x1 = x_;
		y1 = 0;
		vy = 0;
		gravity = .5;
		setFocusable(true);
		timer.start();
	}

	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @param player
	 *            the player to set
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		Dimension size = getSize();
		long t2 = System.nanoTime();
		g.drawImage(bg_, 0, 0, null);

		// *////*//System.out.println(">>BG: " + (System.nanoTime() - t2));
		try {
			temp12 = world.getlevel().getSubimage(-n, 0, getWidth(), 900);
			t2 = System.nanoTime();
		}
		catch (Exception e) {
		}
		g.drawImage(toCompatibleImage(temp12), 0, y_ - 900, null);

		// *////*//System.out.println(">>DRA: " + (System.nanoTime() - t2));
		// if (splat) g.setColor(Color.magenta);
		// else g.setColor(Color.WHITE);
		g.drawImage(sprite.link(), x1, y1, null);
		g.setColor(Color.DARK_GRAY);
		if (!splat) time = Float.toString((float) (System.currentTimeMillis() - starttime) / 1000);
		g.setFont(new Font("Arial Black", Font.PLAIN, 58));
		g.drawString(time, size.width / 2 - 75, 75);
		g.setColor(Color.WHITE);
		g.drawString(time, size.width / 2 - 80, 70);
		if (i3 != null) g.drawImage(i3, 0, 0, null);
		g.setColor(Color.WHITE);
		if (layer.getCuckoos() != null) g.drawImage(layer.getCuckoos(), 0, 0, getWidth() - 1, getHeight() - 250, null);
		// //*////*//System.out.println(">Pnt: " + (System.nanoTime() - t1));

	}

	private static final GraphicsConfiguration GFX_CONFIG = GraphicsEnvironment.getLocalGraphicsEnvironment()
	        .getDefaultScreenDevice().getDefaultConfiguration();

	public static BufferedImage toCompatibleImage(final BufferedImage image) {
		/*
		 * if image is already compatible and optimized for current system settings, simply return it
		 */
		if (image.getColorModel().equals(GFX_CONFIG.getColorModel())) { return image; }

		// image is not optimized, so create a new image that is
		final BufferedImage new_image = GFX_CONFIG.createCompatibleImage(image.getWidth(), image.getHeight(), image
		        .getTransparency());

		// get the graphics context of the new image to draw the old image on
		final Graphics2D g2d = (Graphics2D) new_image.getGraphics();

		// actually draw the image and dispose of context no longer needed
		g2d.drawImage(image, 0, 0, null);
		g2d.dispose();

		// return the new optimized image
		return new_image;
	}

	/**
	 * @return the bg
	 */
	public Background getBg() {
		return bg;
	}

	/**
	 * @return the world
	 */
	public World getWorld() {
		return world;
	}

	/**
	 * @return the n
	 */
	public int getN() {
		return n;
	}

	/**
	 * @param n
	 *            the n to set
	 */
	public void setN(int n) {
		this.n = n;
	}

	/**
	 * @return the x_
	 */
	public int getX_() {
		return x_;
	}

	private void update() {

		vy += gravity;
		y1 += vy;
		if (onGround()) {
			j = 0;
			gravity = 0;
			vy = 0;
			y1 = wallh - 50;
			dn = 6;

		}
		else if (onPlatform()) {
			j = 0;
			gravity = 0;
			vy = 0;
			y1 = wallph - 50;
			dn = 6;

		}
		else if (onGroundn()) {
			j = 0;
			gravity = 0;
			vy = 0;
			y1 = wallhn - 50;
			dn = 6;
		}
		else {
			gravity = .5;
			dn = 6;
		}
		splat();
		y1_ = y1;
		n -= dn;
		// //*////*//System.out.println("Update: " + (System.nanoTime() - t1));
	}

	/**
	 * @return the x1
	 */
	public int getX1() {
		return x1;
	}

	/**
	 * @return the y1_
	 */
	public int getY1() {
		return y1;
	}

	public Boolean onGround() {
		if (y1 == wallh - 50 || (range(wallh - 50, y1_, y1) && y1 > wallh - 50)) {
			sprite.land();
			return true;
		}
		else return false;
	}

	public Boolean onGroundn() {
		if (y1 == wallhn - 50 || (range(wallhn - 50, y1_, y1) && y1 > wallhn - 50)) {
			sprite.land();
			return true;
		}
		else return false;
	}

	public Boolean onPlatform() {
		if ((vy > 0) && (y1 == wallph - 50 || (range(wallph - 50, y1_, y1) && y1 > wallph - 50))) {
			sprite.land();
			return true;
		}
		return false;
	}

	public static boolean range(int x, int min, int max) {
		return x > min && x < max;
	}

	public static boolean range(int x, int x2, int min, int max) {
		return (x > min && x < max) || (x2 > min && x2 < max);
	}

	public void splat() {
		if (y1 + 50 > wallhn) splat = true;
		else if (y1 > y_) {
			splat = true;
		}
		if (splat) {
			if ((System.currentTimeMillis() - starttime) / 1000 > timeend) timeend = (System.currentTimeMillis()
			        - starttime) / 1000;
			sprite.splat();
			bg.ouch();
			dn = 0;
			vy = 0;
			if (splatc == 0) player.setLivesleft(player.getLivesleft() - 1);
			else if (splatc == 100) {
				splatc = -1;
				if (!(player.getLivesleft() <= 0)) newgame();
				else endgame();

				n = 0;
				// timer.start();
			}
			// System.out.println(splatc);
			splatc++;
		}
	}

	/**
	 * @return the sprite
	 */
	public Sprites getSprite() {
		return sprite;
	}

	public int getY_() {
		return y_;
	}

	private void generateLevel() {
		if (x_ != 0 && getHeight() != 0 && firstrun == true) {
			x1 = x_;
			layer = new Enemies(getWidth(), getHeight());
			layer.startNow();
			bg.update(x_ * 2, y_);
			firstrun = false;
			bg.startNow();
			world.startNow();
			j++;
			starttime = System.currentTimeMillis();
		}

	}

	private void center() { // center everything
		// long
		// Dimension size = getSize();
		x_ = getWidth() / 2;
		y_ = getHeight();
		x1 = x_;

		try {
			wallh = (y_ - world.position(n).height * 50);
			if (range(y1, y1 + 50, wallh - 130, wallh - 60)) powerup = world.powerup(n);
			if (world.position(n).hasPlatform) wallph = (y_ - world.position(n).pheight * 50);
			else wallph = -5;
			wallhn = (y_ - world.position(n - 50).height * 50);
			if (world.getWorldfloor().size() < (y_ - n) / 400 + 5) {
				world.saddSlices();
			}

		}
		catch (Exception e) {
			// System.out.println("n=" + n);
		}

		// //*////*//System.out.println("Center: " + (System.nanoTime() - t1));
	}

	private void character() {

	}

	public void jump() {
		j++;
		if (j < 3) {
			vy = -15.0;
			// y1 -= 5;
			gravity = .5;

			sprite.jump();
		}
	}

}
