package Tillampningsprogram;
import javax.swing.*;
import java.awt.*;

public class nod extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private int xPos;
	private int yPos;
	private String namn;
	private boolean selected=false;
	Font f = new Font("Helvetica", Font.PLAIN, 14);
	FontMetrics fm = getFontMetrics(f);
	
	Dimension d = new Dimension(50,50);
	
	public nod(String namn) {
		this.namn=namn;
	}
	
	
	public nod(int x, int y, String namn){
		this.namn=namn;
		xPos=x;
		yPos=y;
		setBounds(x,y, 50, 60);
		setPreferredSize(d);
		setMaximumSize(d);
		setMinimumSize(d);
		setOpaque(false);
		
		
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
			g.setFont(f);
			int w = fm.stringWidth(namn);
			setBounds(xPos,yPos,w,fm.getHeight()+5);
			if (w<10)
				setBounds(xPos,yPos,10,fm.getHeight()+5);
		
		if (selected) {
			g.setColor(Color.GREEN);
			g.fillOval(0, 0, 10, 10);
			g.setColor(Color.BLACK);
			g.drawString(namn, 0,20);
		} else {
		g.setColor(Color.RED);
		g.fillOval(0, 0, 10, 10);
		g.setColor(Color.BLACK);
		g.drawString(namn, 0,20);
		}
	}
	
	public void setSelected() {
		selected=!selected;
		repaint();
	}
	
	public boolean isSelected() {
		return selected;
	}
	
	public String getNamn() {
		return namn;
	}
	
	@Override
	public String toString() {
		return namn;
	}
	
	@Override
	public int hashCode() {
		return xPos*10000+yPos;
	}
	
	
	public boolean equals(nod s) {
		return this.getNamn().equals(s.getNamn());
	}

}
