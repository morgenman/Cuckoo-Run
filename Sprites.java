import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprites {
	private Image			s1, s2, s3;
	private static Image	cl1, cl2, csplat;
	private int				spritec	= 0;
	private int				spritelooper;

	public Sprites() {
		try { // add images
			s1 = ImageIO.read(CuckooRun.class.getResource("sprite1.png"));
			s2 = ImageIO.read(CuckooRun.class.getResource("sprite2.png"));
			s3 = ImageIO.read(CuckooRun.class.getResource("sprite3.png"));

			cl1 = ImageIO.read(CuckooRun.class.getResource("cl1.png"));
			cl2 = ImageIO.read(CuckooRun.class.getResource("cl2.png"));

			csplat = ImageIO.read(CuckooRun.class.getResource("csplat.png"));
		}

		catch (IOException e1) {
			System.out.println("Error, images not in root folder");
		}
	}

	public static Image cuckoo(int state) {
		// BufferedImage enemy = new BufferedImage(40, 40, BufferedImage.TYPE_INT_ARGB_PRE);
		// Graphics g = enemy.getGraphics();
		switch (state) {
		case 0:
			return cl1;
		default:
			return cl2;
		}

		// return enemy;
	}

	public Image powerup() {
		BufferedImage powerup = new BufferedImage(400, 40, BufferedImage.TYPE_INT_ARGB_PRE);
		Graphics g = powerup.getGraphics();
		g.drawImage(csplat, 180, 0, null);
		return powerup;
	}

	public void reset() {
		spritec = 30;
	}

	public void jump() {
		spritec = 35;
	}

	public void land() {
		if (spritec >= 35) spritec = 0;
	}

	public void splat() {
		spritec = 30;
	}

	public Image link() {
		spritec++;

		if (spritec >= 35) return s2;
		else if (spritec >= 30) return s3;
		if (spritec < 10) return s1;
		else {
			if (spritec == 20) spritec = 0;
			return s2;

		}
	}
}
