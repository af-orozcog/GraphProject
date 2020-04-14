package model.data_structures;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class KruskalTest {
	Graph<Integer,Integer,Double> grafoNumerico;
	
	KruskalMST<Integer,Integer> caminos;
	
	public void setUp0() {
		grafoNumerico = new Graph<Integer,Integer,Double>();
	}
	
	/**
	 * Se verifica que el camino y el peso del arbol sean los correctos.
	 */
	@Test
	public void weightEdgeTest() {
		setUp0();
		for(int i = 0; i < 9; i++) {
			grafoNumerico.addVertex(i, null);
		}
		grafoNumerico.addEdge(0, 1, 4.0);
		grafoNumerico.addEdge(0, 7, 8.0);
		grafoNumerico.addEdge(1, 7, 11.0);
		grafoNumerico.addEdge(1, 2, 8.0);
		grafoNumerico.addEdge(2, 3, 7.0);
		grafoNumerico.addEdge(3, 4, 9.0);
		grafoNumerico.addEdge(4, 5, 10.0);
		grafoNumerico.addEdge(5, 6, 2.0);
		grafoNumerico.addEdge(6, 7, 1.0);
		grafoNumerico.addEdge(7, 8, 7.0);
		grafoNumerico.addEdge(6, 8, 6.0);
		grafoNumerico.addEdge(8, 2, 2.0);
		grafoNumerico.addEdge(5, 2, 4.0);
		
		
		caminos = new KruskalMST<Integer,Integer>(grafoNumerico);
		
		for(Edge<Double> edg: caminos.edges()) {
			System.out.println(edg.either() + " -- " + edg.other(edg.either()));
		}
		assertEquals(37.0, caminos.weight(),0);
	}
}
