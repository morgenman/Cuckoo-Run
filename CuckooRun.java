import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.ImageObserver;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class CuckooRun {
	private static Canvas main = new Canvas(); // main canvas

	public static Canvas getMain() {
		return main;
	}

	public static void main(String[] args) {

		System.setProperty("sun.java2d.opengl", "True");

		JFrame frame = new JFrame("The Wall");
		main.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				main.jump();
			}
		});

		frame.addComponentListener(new ComponentListener() {
			public void componentResized(ComponentEvent e) {
				main.getBg().update(main.getWidth(), main.getHeight());
			}

			@Override
			public void componentHidden(ComponentEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void componentMoved(ComponentEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void componentShown(ComponentEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		Action jumpy = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				main.jump();
			}
		};
		KeyStroke jumpk = KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0);
		frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(jumpk, "TestAction");
		frame.getRootPane().getActionMap().put("TestAction", jumpy);
		frame.setPreferredSize(new Dimension(960, 760));
		frame.getContentPane().add(main, BorderLayout.CENTER);

		frame.setSize(400, 300);
		frame.pack();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

}
