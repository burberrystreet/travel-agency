import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.*;

public class AirMap extends JPanel implements ActionListener{ 
	public ActionListener l;

	public AirMap() {
		this.setPreferredSize(new Dimension(1350,900));
		AirportPoint.popPoints();
		this.setBackground(Color.BLACK);
	}

	public void paintComponent(Graphics gr) {
		super.paintComponent(gr);
		gr.setColor(Color.GRAY);
		for (AirportPoint a : AirportPoint.returnPoints()) {
			gr.fillOval((int) a.getX(), (int) a.getY(), 10, 10);
		}
		gr.setColor(Color.WHITE);
		for (AirportPoint a : AirportPoint.returnPoints()) {
			gr.drawOval((int) a.getX(), (int) a.getY(), 10, 10);
		}

	}


	/**public static void main(String[] args) throws FileNotFoundException {
		Airport.setAirports();
		AirportPoint.popPoints();
		JFrame frame = new JFrame("flight map");
		AirMap m = new AirMap();
		m.setPreferredSize(new Dimension(1650,700));
		frame.add(m);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
		frame.pack();
		frame.setVisible(true);
		for (AirportPoint a : AirportPoint.returnPoints()) {
			JButton b = new JButton(a.getName());
			b.setPreferredSize(new Dimension(100,100));
			b.setAlignmentX((float) a.x);
			b.setAlignmentY((float) a.y);
			m.add(b);
			b.addActionListener(m);
		}

		}**/


	@Override
	public void actionPerformed(ActionEvent e) {

	}
}
