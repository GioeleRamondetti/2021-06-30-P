package it.polito.tdp.genes.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.genes.db.GenesDao;

public class Model {
	private Graph<String,DefaultWeightedEdge> grafo;
	private GenesDao dao=new GenesDao();
	private int narchi;
	private int nvertici;
	private Map m;
	private int best;
	private List<String> bestpercorso;
	private int pesotot;
	
	public List<String> getLocalization() {
		return dao.getAlllocalisationString();
	}
	
	public int getNarchi() {
		return narchi;
	}

	public int getNvertici() {
		return nvertici;
	}

	public void creagrafo() {
		grafo=new SimpleWeightedGraph<String,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(grafo, dao.getAlllocalisationString());
		List<String> lista=new ArrayList<String>( dao.getAlllocalisationString());
		for(String s1 :lista) {
			for(String s2 :lista) {
				if(!s1.equals(s2) && dao.getpeso(s1, s2)>0) {
					grafo.addEdge(s1, s2);
					grafo.setEdgeWeight(grafo.getEdge(s1, s2), dao.getpeso(s1, s2));
				}
			}
		}
		this.narchi=grafo.edgeSet().size();
		this.nvertici=grafo.vertexSet().size();
	}
	
	public List<String> getpercorsomax(String sorgente){
		best=0;
		m= new HashMap<String,String>();
		m.put(sorgente, sorgente);
		List<String> parziale =new LinkedList<>();
		parziale.add(sorgente);
		cerca(parziale);
		return this.bestpercorso;
	}
	
	

	public int getBest() {
		return best;
	}

	

	private void cerca(List<String> parziale) {
		// condizione terminazione
		if(pesotot>best) {
			best=pesotot;
			bestpercorso=new ArrayList<String>(parziale);
		}
			
		
		// scorro i vicini dell'ultimo inserito ed esploro
		 for(String v :Graphs.neighborListOf(grafo, parziale.get(parziale.size()-1))) {
			 
				 // evito cicli
				if(!m.containsKey(v)) {
				 m.put(v, v);
				 this.pesotot= (int) (this.pesotot+ grafo.getEdgeWeight(grafo.getEdge(v, parziale.get(parziale.size()-1))));
				 parziale.add(v);
				 cerca(parziale);
				 m.remove(v);
				 parziale.remove(parziale.size()-1);
				 this.pesotot= (int) (this.pesotot- grafo.getEdgeWeight(grafo.getEdge(v, parziale.get(parziale.size()-1))));
				 
				}
				 
		 }
		 
		
	}

	public String fornisciStat(String value) {
		String s="";
		 for(String v :Graphs.neighborListOf(grafo,value)) {
			 s=s+v+" "+grafo.getEdgeWeight(grafo.getEdge(v, value))+"\n";
		 }
		return s;
	}
}