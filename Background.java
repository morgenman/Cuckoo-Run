import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Timer;

public class Background extends Thread {
	private Thread	t;
	private String	threadname;
	private Image	bg, bgc, clouds;
	private Image	s1, s2, s3, s4, s5, s6, sun;
	private Image	hf, he, hfg;
	private Image	bgscaled, bgcscaled, cloudsscl;
	private int		n	= 0, m = 0;
	private int		width, widthc;
	private int		ww, wh;
	private int		sc	= -1;
	private Image	temp;

	Background() {
		try { // add images
			bg = ImageIO.read(CuckooRun.class.getResource("bg.png"));
			bgc = ImageIO.read(CuckooRun.class.getResource("cloudbg.png"));
			clouds = ImageIO.read(CuckooRun.class.getResource("clouds.png"));
			s1 = ImageIO.read(CuckooRun.class.getResource("sunhappy.png"));
			s2 = ImageIO.read(CuckooRun.class.getResource("sunhappy2.png"));
			s3 = ImageIO.read(CuckooRun.class.getResource("sunhappy3.png"));
			s4 = ImageIO.read(CuckooRun.class.getResource("sunhappy4.png"));
			s5 = ImageIO.read(CuckooRun.class.getResource("sunhappy5.png"));
			s6 = ImageIO.read(CuckooRun.class.getResource("sunwince.png"));
			hf = ImageIO.read(CuckooRun.class.getResource("hfull.png"));
			hfg = ImageIO.read(CuckooRun.class.getResource("hfullG.png"));
			he = ImageIO.read(CuckooRun.class.getResource("hempty.png"));
		}

		catch (IOException e1) {
			System.out.println("Error, images not in root folder");
		}
		threadname = "bg";
	}

	@Override
	public void run() {

		Timer timer = new Timer(80, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				temp = new BufferedImage(ww, wh, BufferedImage.TYPE_INT_RGB);
				getBg(temp.getGraphics());

			}
		});
		timer.start();

	}

	public void startNow() {
		// System.out.println("Starting " + threadname);
		if (t == null) {
			t = new Thread(this, threadname);
			t.start();
		}
	}

	public Image getBG() {
		// System.out.println(temp.getWidth(null) + "x" + temp.getHeight(null));
		return temp;
	}

	public void update(int x, int y) {
		width = y * bg.getWidth(null) / bg.getHeight(null);
		widthc = y * clouds.getWidth(null) / clouds.getHeight(null);
		ww = x;
		wh = y;
		bgscaled = bg.getScaledInstance(width, wh, Image.SCALE_DEFAULT);
		bgcscaled = bgc.getScaledInstance(width, wh, Image.SCALE_DEFAULT);
		cloudsscl = clouds.getScaledInstance(widthc, wh, Image.SCALE_DEFAULT);
	}

	public void getBg(Graphics g) {
		// long t1 = System.nanoTime();
		// Image temp = new BufferedImage(ww, wh, BufferedImage.TYPE_INT_ARGB);
		g.drawImage(bgscaled, n / 2, 0, null);
		g.drawImage(bgscaled, n / 2 + width, 0, null);
		g.drawImage(bgscaled, n / 2 + 2 * width, 0, null);
		getSun();
		g.drawImage(sun, ww - 200, 50, null);
		g.drawImage(bgcscaled, n / 2, 0, null);
		g.drawImage(bgcscaled, n / 2 + width, 0, null);
		// g.drawImage(bgcscaled, n / 2 + 2 * width, 0, null);
		g.drawImage(cloudsscl, (int) (m / (1)), 0, null);
		g.drawImage(cloudsscl, (int) (m / (1) + widthc), 0, null);
		switch (CuckooRun.getMain().getPlayer().getLivesleft()) {
		case 0:
			g.drawImage(he, 15, 15, null);
			g.drawImage(he, 85, 15, null);
			g.drawImage(he, 155, 15, null);
			break;
		case 1:
			g.drawImage(hf, 15, 15, null);
			g.drawImage(he, 85, 15, null);
			g.drawImage(he, 155, 15, null);
			break;
		case 2:
			g.drawImage(hf, 15, 15, null);
			g.drawImage(hf, 85, 15, null);
			g.drawImage(he, 155, 15, null);
			break;
		case 3:
			g.drawImage(hf, 15, 15, null);
			g.drawImage(hf, 85, 15, null);
			g.drawImage(hf, 155, 15, null);
			break;
		default:
			g.drawImage(hfg, 15, 15, null);
			g.drawImage(hfg, 85, 15, null);
			g.drawImage(hfg, 155, 15, null);
		}

		// g.drawImage(cloudsscl, (int) (m / (1.75) + widthc / 2), 0, null);
		if (n / 2 == -width) n = 0;
		if (m / 1 == -widthc) m = 0;
		n--;
		m--;
		CuckooRun.getMain().setBg_(temp);
		// System.out.println(System.nanoTime() - t1);
		// return temp;
	}

	public void ouch() {
		sc = 20;
	}

	public void getSun() {
		sc++;
		switch (sc) {
		case 0:
			sun = s1;
			break;
		case 2:
			sun = s2;
			break;
		case 4:
			sun = s3;
			break;
		case 6:
			sun = s4;
			break;
		case 8:
			sc = -2;
			sun = s5;
			break;
		case 21:
			sun = s6;
			break;
		case 25:
			sc = -6;
		}

	}

}
