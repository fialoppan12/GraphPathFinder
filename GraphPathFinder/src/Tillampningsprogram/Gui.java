package Tillampningsprogram;
import java.awt.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.awt.event.*;
import static javax.swing.JOptionPane.*;
import Graphs.*;
import java.util.List;
import java.io.File;

/*NEJ, JAG VET, DET HÄR GÅR KANSKE INTE HELT EFTER OBJEKTORIENTERADE PRINCIPER MEN JAG MÅSTE FAKTISKT PLUGGA TILL TENTAN NU. 
 * LOVAR ATT (FÖRSÖKA) DELA UPP ALLTING BÄTTRE PÅ LÖRDAG FÖRMIDDAG. <3 */

public class Gui extends JFrame {

	private static final long serialVersionUID = 1L;
	
	JFileChooser fc = new JFileChooser();
	FileNameExtensionFilter filter = new FileNameExtensionFilter("Gif and Jpeg", "gif", "jpg");
	JPanel center = new JPanel();
	Image image;
	Draw d =null;
	NodskaparLyss nodl = new NodskaparLyss();
	ListGraph<nod> l = new ListGraph<nod>();
	nod n1=null, n2=null;
	JTextArea visaForbindelser = new JTextArea();
	JList<Edge<nod>> j = new JList<Edge<nod>>();
	JList<String> jTest = new JList<String>();
	NodLyss nodlyssnare = new NodLyss();
	Cursor c = Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
	Cursor vanlig = Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR);
	
	
	Gui(){
		super("FisenMadGreatProgramOfDoomIquistitorLevelInfinityBAM");
		setLayout(new BorderLayout());
		
		j.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		fc.setFileFilter(filter);
		visaForbindelser.setEditable(false);
		
		//MENU 
		JPanel top = new JPanel();
		top.setLayout( new BorderLayout());
		
		JMenuBar menu = new JMenuBar();
		JMenuItem avsluta = new JMenuItem("Avsluta");
		avsluta.addActionListener(new avslutaLyss());
		JMenu arkiv = new JMenu("Arkiv");
		JMenuItem nytt = new JMenuItem("Nytt");
		nytt.addActionListener(new oppnaLyss());
		arkiv.add(nytt);
		arkiv.add(avsluta);
		
		hittaLyss hittaL = new hittaLyss();
		visafLyss visafL = new visafLyss();
		nypLyss nypL = new nypLyss();
		nyfLyss nyfL = new nyfLyss();
		andrafLyss andrafL = new andrafLyss();
		
		JMenu operationer = new JMenu("Operationer");
		
		JMenuItem hittaj = new JMenuItem("Hitta");
		hittaj.addActionListener(hittaL);
		JMenuItem visafj = new JMenuItem("Visa Förbindelse");
		visafj.addActionListener(visafL);
		JMenuItem nypj = new JMenuItem("Ny plats");
		nypj.addActionListener(nypL);
		JMenuItem nyfj = new JMenuItem("Ny förbindelse");
		nyfj.addActionListener(nyfL);
		JMenuItem andrafj = new JMenuItem("Ändra förbindelse");
		andrafj.addActionListener(andrafL);
		
		operationer.add(hittaj);
		operationer.add(visafj);
		operationer.add(nypj);
		operationer.add(nyfj);
		operationer.add(andrafj);
		menu.add(arkiv);
		menu.add(operationer);
		top.add(menu, BorderLayout.CENTER);
		
		
		//JPanel north och knappar
		JPanel north = new JPanel();
		north.setLayout(new BoxLayout(north, BoxLayout.X_AXIS));
		north.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		JButton hitta = new JButton("Hitta väg");
		hitta.addActionListener(hittaL);
		JButton visaf = new JButton("Visa förbindelse");
		visaf.addActionListener(visafL);
		JButton nyp = new JButton("Ny plats");
		nyp.addActionListener(nypL);
		JButton nyf = new JButton("Ny förbindelse");
		nyf.addActionListener(nyfL);
		JButton andraf = new JButton("Ändra förbindelse");
		andraf.addActionListener(andrafL);
		
		north.add(hitta);
		north.add(visaf);
		north.add(nyp);
		north.add(nyf);
		north.add(andraf);
		
		top.add(north, BorderLayout.SOUTH);
		
		add(top, BorderLayout.NORTH);

		pack();
		setLocation(300,300);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
	
	public class oppnaLyss implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			
			int returnVal = fc.showOpenDialog(Gui.this);		
		
			if (returnVal != JFileChooser.APPROVE_OPTION)
				return;
			
				File file = fc.getSelectedFile();
				String fil = file.getAbsolutePath();
				
				if (d!=null)
					remove(d);
				
				d = new Draw(fil);
				add(d, BorderLayout.CENTER);
				d.setLayout(null);
				l = new ListGraph<nod>();

				setResizable(false);
				
				validate();
				repaint();
				pack();
				
			
		}
	}
	

	//SKAPA NOD PLACERA PÅ KARTA(!) OCH ADD TO LISTGRAPH
	public class NodskaparLyss extends MouseAdapter {
		public void mouseClicked(MouseEvent mev) {
			//KÄNNS ALLMÄNT INTE OPTIMALT MEN LÅT GÅ :/
			String namn;
			nodJPanel panel = new nodJPanel();
			do {
			int svar = JOptionPane.showConfirmDialog(Gui.this, panel, "Vad heter platsen?", JOptionPane.OK_CANCEL_OPTION);
			namn = panel.getNamn();
			if (svar == 2 || svar == -1) {
				d.setCursor(vanlig);
				d.removeMouseListener(nodl);
				return;
			}
			if (panel.getNamn().length()==0) 
			JOptionPane.showMessageDialog(Gui.this, "skriv in ett namn!", "Fel!", ERROR_MESSAGE);

			} while (panel.getNamn().length()==0);
			int x = mev.getX();
			int y = mev.getY();
			
			// IF NOD ÄR UTANFÖR KARTA
			
			
			nod n = new nod(x,y, namn);
			n.addMouseListener(nodlyssnare);
			d.add(n);
			l.add(n);
			validate();
			repaint();
			d.removeMouseListener(nodl);
			d.setCursor(vanlig);
		}
	}
	
	//FÖR ATT MARKERA NODER SOM VALDA
	public class NodLyss extends MouseAdapter {
		public void mouseClicked(MouseEvent mev) {
			nod n = (nod)mev.getSource();
			
			if (n1==n) {
				n.setSelected();
				n1=null;
				return;
			}
			if (n2==n) {
				n.setSelected();
				n2=null;
				return;
			}
			
			if (n1==null||n2==null) {
				n.setSelected();
				if (n1==null) {
					n1=n;
				} else if (n2==null) {
					n2=n;
				}
			}

		}
	}
	

	public class hittaLyss implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			if (d==null) {
				JOptionPane.showMessageDialog(getRootFrame(), "börja med att lägga till karta! ", "fel", ERROR_MESSAGE);
				return;
			}
			if (n1==null||n2==null) {
				JOptionPane.showMessageDialog(getRootFrame(), "välj två städer", "fel", ERROR_MESSAGE);
				return;
			}
			try {
			hittaJPanel j = new hittaJPanel();
			JOptionPane.showMessageDialog(Gui.this, j, "väg", PLAIN_MESSAGE);
			} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(Gui.this, "det finns ingen väg", "ingo", PLAIN_MESSAGE);
		}
		}
		
	}
	
	public class visafLyss implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			if (d==null) {
				JOptionPane.showMessageDialog(getRootFrame(), "börja med att lägga till karta! ", "fel", ERROR_MESSAGE);
				return;
			}
			if (n1==null||n2==null) {
				JOptionPane.showMessageDialog(Gui.this, "Välj två städer", "fel", ERROR_MESSAGE);
				return;
			}
			
			if (l.getEdgesBetween(n1, n2).size()<1) {
				JOptionPane.showMessageDialog(Gui.this, "Det finns inga förbindelser mellan valda platser", "Sorry", PLAIN_MESSAGE);
				return;
			}
				
			visafJPanel s = new visafJPanel();
			
			visaForbindelser.setText("");
			
			for (int i=0;i<l.getEdgesBetween(n1,n2).size();i++) {
			visaForbindelser.append(l.getEdgesBetween(n1, n2).get(i).getName()+" tar "+l.getEdgesBetween(n1,n2).get(i).getWeight()+" abstrakta mätenheter\n");
			}
			
			
			JOptionPane.showMessageDialog(Gui.this, s, "Förbindelseer", INFORMATION_MESSAGE);
			
		}
		
	}
	
	
	
	public class nypLyss implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			try {
			d.addMouseListener(nodl);
			d.setCursor(c);

			} catch (NullPointerException e) {
				JOptionPane.showMessageDialog(getRootFrame(), "börja med att lägga till karta! ", "fel", ERROR_MESSAGE);
			}
		}
		
	}
	
	public class nyfLyss implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			if (d==null) {
				JOptionPane.showMessageDialog(Gui.this, "Börja med att lägga till karta!", "Fel!", ERROR_MESSAGE);
				return;
			}
			if (n1==null||n2==null) {
				JOptionPane.showMessageDialog(getRootFrame(), "välj två städer", "Fel!", ERROR_MESSAGE);
				return;
			}
			connectJPanel p = new connectJPanel();
			//KOLLA INPUT ORKAR INTE MER NU NOP <3
			for(;;) {
			try {
			do {
			int svar = JOptionPane.showConfirmDialog(Gui.this, p, "skapa förbindelse", JOptionPane.OK_CANCEL_OPTION);
			if (svar==2||svar==-1) 
				return;
			if (p.getNamn().length()==0) {
				JOptionPane.showMessageDialog(Gui.this, "Stadnamn måste vara mer än 0 bokstäver långt!", "Fel!", ERROR_MESSAGE);
			}
			for (Edge<nod> e : l.getEdgesBetween(n1,n2)) {
				if (e.getName().equals(p.getNamn())) {
					JOptionPane.showMessageDialog(Gui.this, "finns redan!", "det blir ingen j*vla förbindelse!", ERROR_MESSAGE);
					return;
				}
			}
			
			} while (p.getNamn().length()<1 || p.getVikt()==0);
						l.connect(n1,n2,p.getNamn(),p.getVikt());
						return;
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(Gui.this, "Nummer måste vara ett nummer", "nummer", ERROR_MESSAGE);
			}
			}
		}
		
	}
	
	public class andrafLyss implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			Edge<nod> e = null;
				if (d==null) {
					JOptionPane.showMessageDialog(getRootFrame(), "börja med att lägga till karta! ", "fel", ERROR_MESSAGE);
					return;
				}
			
				if (n1==null||n2==null) {
					JOptionPane.showMessageDialog(getRootFrame(), "välj två städer", "fel", ERROR_MESSAGE);
					return;
				}
				
				if (l.getEdgesBetween(n1,n2).size()<1) {
					JOptionPane.showMessageDialog(Gui.this, "det finns ingen förbindelse att ändra", "fel", PLAIN_MESSAGE);
					return;
				}
				
				if (l.getEdgesBetween(n1,n2).size()==1) {
					e = l.getEdgesBetween(n1,n2).get(0);
				} else if (l.getEdgesBetween(n1, n2).size()>1) {
				andrafJPanel panel = new andrafJPanel();
				int svar =JOptionPane.showConfirmDialog(Gui.this, panel, "välj förbindelse", OK_CANCEL_OPTION);
				if (svar==-1||svar==2) 
					return;
				
				String s = jTest.getSelectedValue();
				for (Edge<nod> df : l.getEdgesBetween(n1,n2)) {
					if (df.getName().equals(s))
						e=df;
				}
				
				

				}

				connectJPanel nyf = new connectJPanel();
				nyf.setNamnEditable(e.getName());
				int i=0;;
				for (;;) {
				try {
				do {
				int svar = JOptionPane.showConfirmDialog(Gui.this, nyf, "Ändra förbindelse", OK_CANCEL_OPTION);
				if (svar==-1||svar==2) 
					return;
				
				i = nyf.getVikt();
				} while (i==0);
				e.setWeight(i);
				
				
				// OH SNAP :P
				nod des = e.getDestination();
				String anma =e.getName();
				
				if (des==n1) {
					Edge<nod> r = l.getEdge(des,n2,anma);
					r.setWeight(i);
				} else if (des==n2) {
					Edge<nod> r = l.getEdge(des, n1, anma);
					r.setWeight(i);
				}
				
				return;

				} catch (NumberFormatException dsfe) { 
					JOptionPane.showMessageDialog(Gui.this, "Nummer måste vara ett nummer", "nummer", ERROR_MESSAGE);
				}
				}

		}
		
	}
	
	public class avslutaLyss implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			System.exit(0);
		}
	}
	

	//FÖR INPUT NAMN OCH VIKT TILL CONNECT-METHOD
	class connectJPanel extends JPanel {

		private static final long serialVersionUID = 1L;
		JTextField namn = new JTextField(5); 
		JTextField vikt = new JTextField(5);
		
		public connectJPanel() {
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			JLabel head = new JLabel("Förbindelse mellan "+n1.getNamn()+" och "+n2.getNamn());
			add(head);
			JPanel rad1 = new JPanel();
			rad1.add(new JLabel("Namn: "));
			rad1.add(namn);
			add(rad1);
			
			JPanel rad2 = new JPanel();
			rad2.add(new JLabel("Abstrakt mäthet: "));
			rad2.add(vikt);
			add(rad2);
		}
		
		public void setNamnEditable(String s) {
			namn.setText(s);
			namn.setEditable(false);
		}
		
		public String getNamn(){
			return namn.getText();
		}
		
		public int getVikt() {
			int v = Integer.parseInt(vikt.getText());
			return v;
		}
		
	}
	
	class nodJPanel extends JPanel {

		private static final long serialVersionUID = 1L;
		JTextField namn = new JTextField(10);
		public nodJPanel() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		JLabel namnn = new JLabel("Namn: ");
		add(namnn);
		add(namn);
		}
		
		public String getNamn() {
			return namn.getText();
		}
	}

	class visafJPanel extends JPanel {
		private static final long serialVersionUID = 1L;

		public visafJPanel() {
			setLayout(new BorderLayout());
			JLabel j = new JLabel("förbindelser mellan "+n2.getNamn()+" och "+n1.getNamn());
			add(j, BorderLayout.NORTH);
			add(new JScrollPane(visaForbindelser), BorderLayout.CENTER);
		}
	}
	
	
	class andrafJPanel extends JPanel {
		
		private static final long serialVersionUID = 1L;

		public andrafJPanel() {
			setLayout(new BorderLayout());
			JLabel la = new JLabel("välj förbindelse mellan "+n1.getNamn()+" och "+n2.getNamn());
			add(la, BorderLayout.NORTH);	
			
		
			String[]arr=new String[l.getEdgesBetween(n1,n2).size()];
			int index=0;
			
			for (Edge<nod> b : l.getEdgesBetween(n1,n2)) {
				arr[index]=b.getName()+" "+b.getWeight();
				index++;
			}
			
			
			jTest.setListData(arr);
			add(jTest, BorderLayout.CENTER);
			
		}
		
	}
	
	class hittaJPanel extends JPanel {
		private static final long serialVersionUID = 1L;

		public hittaJPanel() {
			setLayout(new BorderLayout());
			JLabel lab = new JLabel("Från "+n1.getNamn()+" till "+n2.getNamn());
			add(lab, BorderLayout.NORTH);
			JTextArea panel = new JTextArea();
	
			List<Edge<nod>> n = GraphMethods.shortestPath(l, n1, n2);
			int tot = 0;
			for (Edge<nod> nd : n) {
				panel.append(nd.getName()+" till "+nd.getDestination().getNamn()+" ("+nd.getWeight()+")\n");
				tot=tot+nd.getWeight();
				}
				panel.append("Totalt: "+tot);
				
			add(panel, BorderLayout.CENTER);
		}
	}

	public static void main(String[] args) {
		new Gui();
	}
}
