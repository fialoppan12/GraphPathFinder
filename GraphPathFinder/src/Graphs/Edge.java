package Graphs;


public class Edge<N>  {
	private int vikt;
	private N destination;
	private String namn; 
	
	protected Edge(N d, String n,int stracka) {
		if(stracka<0)
			throw new IllegalArgumentException("negativ vikt for edge");
		vikt=stracka;
		destination=d;
		namn=n;
	}
	
	public N getDestination(){
		return destination;
	}
	
	public int getWeight() {
		return vikt;
	}
	
	public void setWeight(int v) {
		vikt=v;
	}
	
	public String getName() {
		return namn;
	}
	
	public String toString() {
		String newLine = "\n";
		String s = "kan åka till "+destination+" på  "+vikt+" med " +namn;
		return newLine+s;
	}

}