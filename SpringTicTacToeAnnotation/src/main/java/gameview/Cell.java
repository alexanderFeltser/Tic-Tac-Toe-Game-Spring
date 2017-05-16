package gameview;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Point;

public class Cell extends JPanel {

	/**
	 *
	 */
	private static final long serialVersionUID = 14556L;
	private final int row;
	private final int column;

	// private View view;

	public Cell(Point point, String iconFilename, View view) {
		row = point.x;
		column = point.y;
		this.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel label = new JLabel(scaleImage(new ImageIcon(iconFilename), 40));
		label.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// System.out.println("Cell(" + row + "," + column +
				// ") clicked!");
				view.onCellPressed(new Point(row, column));

			}
		});
		this.add(label);
	}

	public ImageIcon scaleImage(ImageIcon icon, int cellSize) {
		int h = cellSize;
		int w = cellSize;
		int nw = icon.getIconWidth();
		int nh = icon.getIconHeight();

		if (icon.getIconWidth() > w) {
			nw = w;
			nh = (nw * icon.getIconHeight()) / icon.getIconWidth();
		}
		if (nh > h) {
			nh = h;
			nw = (icon.getIconWidth() * nh) / icon.getIconHeight();
		}
		return new ImageIcon(icon.getImage().getScaledInstance(nw, nh, Image.SCALE_DEFAULT));
	}

	public static void main(String[] args) throws IOException {
		GameProperties prop = new GameProperties();
		prop.getxIconFileName();
		JFrame frame = new JFrame();
		frame.setLayout(new BorderLayout());

		// frame.add(new Cell(prop.getoIconFileName()), BorderLayout.SOUTH);
		// frame.add(new Cell(new Point(1, 1), prop.getxIconFileName()),
		// BorderLayout.SOUTH);

		frame.pack();
		frame.setVisible(true);
	}
}
