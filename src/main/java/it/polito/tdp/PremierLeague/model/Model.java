package it.polito.tdp.PremierLeague.model;
import java.util.List;

import org.jgrapht.*;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	PremierLeagueDAO dao;
	Graph<Player, DefaultWeightedEdge> grafo;
	
	public Model() {
		dao = new PremierLeagueDAO();
	}
	
	public void creagraf(Match m) {
		
		grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(this.grafo, dao.getVertex(m));
		
		for(Player p1 : this.grafo.vertexSet()) {
			for(Player p2 : this.grafo.vertexSet()) {
				if(p1.getTeam()!=p2.getTeam()) {
					double e = p1.getE() - p2.getE();
					if(e>0) {
						Graphs.addEdgeWithVertices(this.grafo, p1, p2, e);
					}
				}
			}
		}
	}
	
	public Player TopPlayer() {
		
		double max = 0;
		Player top = null;
		
		for(Player p : this.grafo.vertexSet()) {
			double c = 0;
			for(DefaultWeightedEdge e : this.grafo.outgoingEdgesOf(p)) {
				c += this.grafo.getEdgeWeight(e);
			}
			for(DefaultWeightedEdge e : this.grafo.incomingEdgesOf(p)) {
				c -= this.grafo.getEdgeWeight(e);
			}
			p.setCom(c);
			if(c>max) {
				max = c;
				top = p;
			}
		}
		
		return top;
	}

	public Graph<Player, DefaultWeightedEdge> getgrafo() {
		
		return this.grafo;
	}

	public List<Match> setCombo() {
		return dao.listAllMatches();
	}
	
}
