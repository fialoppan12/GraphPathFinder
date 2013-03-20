package Tillampningsprogram;
import Graphs.*;
import java.util.ArrayList;

public class test {

	ListGraph<nod> l = new ListGraph<nod>();
	public test() {
		
		nod a = new nod("A");
		nod b = new nod("B");
		nod c = new nod("C");
		nod d = new nod("D");
		
		
		l.add(a);
		l.add(b);
		l.add(c);
		l.add(d);
	
		l.connect(a, b, "buss", 100);
		l.connect(a, d, "buss", 25);
		l.connect(b, c, "buss", 20);
		l.connect(c, d, "buss", 90);
		l.connect(d, b, "buss", 160);
		
		ArrayList<Edge<nod>> list = GraphMethods.shortestPath(l, a, b);
		
		for (Edge<nod> e :list) {
			System.out.println(e);
		}
	
	}
	
	public static void main(String[]args) {
		new test();
	}
	
}
