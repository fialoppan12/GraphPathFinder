package Tillampningsprogram;
import java.awt.*;
import javax.swing.*;

public class Draw extends JPanel {

	private static final long serialVersionUID = 1L;
	private ImageIcon bild;
	
	public Draw(String filepath) {
		bild = new ImageIcon(filepath);
		setPreferredSize(new Dimension(bild.getIconWidth(), bild.getIconHeight()));
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bild.getImage(), 0, 0,this);
		
	}
	
	

}
