package view;

import java.util.Iterator;

import model.data_structures.DijkstraInfractions;
import model.data_structures.Edge;
import model.data_structures.Graph;
import model.data_structures.HashTableSC;
import model.data_structures.ORArray;
import model.data_structures.Pair;
import model.data_structures.VertexContent;
import model.vo.Coordinates;

public class MovingViolationsManagerView {

	public void printMenu() {
		System.out.println("---------ISIS 1206 - Estructuras de datos----------");
		System.out.println("---------------------Proyecto 3----------------------");
		System.out.println("0. Cargar el Grafo No Dirigido (grafo m√°s grande) de la malla vial de la ciudad completa de Washington D.C.");
		
		System.out.println("1. Encontrar el camino de costo m√≠nimo para un viaje entre  dos vertices.. (REQ 1A)");
		System.out.println("2. Determinar los n v√©rtices con mayor n√∫mero de infracciones y sus componentes conectadas (REQ 2A)");
		
		System.out.println("3. Encontrar el camino m√°s corto para un viaje entre  dos vertices. (REQ 1B)");		
		System.out.println("4. Definir una cuadricula regular de N columnas por M filas.  (REQ 2B)"); 
		
		System.out.println("5. Calcular un √°rbol de expansi√≥n m√≠nima (MST) con criterio distancia, utilizando el algoritmo de Kruskal (REQ 1C)");
		System.out.println("6. Calcular un √°rbol de expansi√≥n m√≠nima (MST) con criterio distancia, utilizando el algoritmo de Prim. (REQ 2C)");
		System.out.println("7. Calcular los caminos de costo m√≠nimo con criterio distancia que conecten los v√©rtices resultado "
				+ "de la aproximaci√≥n de las ubicaciones de la cuadricula N x M encontrados en el punto 5. (REQ 3C)");
		System.out.println("8. Encontrar el camino m√°s corto para un viaje entre dos vertices. (REQ 4C)");
		
		System.out.println("9. Salir");
		System.out.println("Digite el numero de opcion para ejecutar la tarea, luego presione enter: (Ej., 1):");
		
	}

	public void printMessage(String string) {
		System.out.println(string);
		
	}
	
	public void printReq0(Graph<Long, VertexContent, Double> pMap) {
		System.out.println("VÈrtices: " + pMap.V());
		System.out.println("Arcos: " + pMap.E());
	}

	public void caminoCostoMinimoA1(Graph<Long, VertexContent, Double> mapa, DijkstraInfractions<Long,VertexContent> caminos, Long idVertice1,Long idVertice2) {
		Iterator<Edge<Double>> ways = caminos.pathTo(idVertice2).iterator();
		if(!ways.hasNext())
			return;
		Edge<Double> way = ways.next();
		int counter = 1;
		Double total = 0.0;
		VertexContent content = mapa.getInfoVertex(mapa.translateInverse(way.either()));
		System.out.println("Camino con menos infracciones entre el vÈrtice " + idVertice1 + "y el vÈrtice " + idVertice2 );
		System.out.println(counter + ". " + "id1: "+ mapa.translateInverse(way.either()) +" ubicaciÛn geogr·fica: " + " lat: " + content.coor.lat + " lon: " + content.coor.lon); 
		counter++;
		content = mapa.getInfoVertex(mapa.translateInverse(way.other(way.either())));
		
		Double arco = way.getInfo();
		total += arco;
		
		Long idTemp = mapa.translateInverse(way.either());
		Coordinates coorTemp = content.coor;
		
		System.out.println("->" + counter + ". " + "id1: "+ mapa.translateInverse(way.other(way.either())) +" ubicaciÛn geogr·fica: " + " lat: " + content.coor.lat + " lon: " + content.coor.lon);
		
		while(ways.hasNext()) {
			counter++;
			way = ways.next();
			content = mapa.getInfoVertex(mapa.translateInverse(way.other(way.either())));
			
			arco = way.getInfo();
			total += arco;
			
			System.out.println("->" + counter + ". " + "id1: "+ mapa.translateInverse(way.other(way.either())) +" ubicaciÛn geogr·fica: " + " lat: " + content.coor.lat + " lon: " + content.coor.lon); 
		}
		System.out.println("Distancia estimada: " + total + " km");
		
		System.out.println("->" + counter + ". " + "id1: "+ idTemp +" ubicaciÛn geogr·fica: " + " lat: " + coorTemp.lat + " lon: " + coorTemp.lon);

	}
	
	public void printmayorNumeroVerticesA2(ORArray<Long> ids, int val){
		System.out.println("los n vertices con m·s infracciones son los siguientes:");
		for(Long lo: ids){
			System.out.println("ID del vertice: " + lo);
		}
		System.out.println("connected: "+ val);
	}
	
	
	public void printCaminoLongitudMinimoaB1(Iterator<Long> it, Graph<Long, VertexContent, Double> mapa, Long idVertice1, Long idVertice2) {
		if(!it.hasNext())
			return;
		int cont = 1; Double total = 0.0;
		Long key = it.next();
		VertexContent content = mapa.getInfoVertex(key);
		
		System.out.println("Camino m·s corto entre el vÈrtice " + idVertice1 + "y el vÈrtice " + idVertice2 );
		System.out.println(cont + ". " + "id1: "+ key +" ubicaciÛn geogr·fica : " + content.coor.lat + " lon: " + content.coor.lon); 
		while(it.hasNext()) {
			cont++;
			Long keyant = key;
			key = it.next();
			content = mapa.getInfoVertex(key);
			
			Double arco = mapa.getInfoArc(key, keyant);
			total += arco;
			
			System.out.println("->" + cont + ". " + "id1: "+ key +" ubicaciÛn geogr·fica: " + " lat: " + content.coor.lat + " lon: " + content.coor.lon); 			
		}
		System.out.println("Distancia estimada: " + total + " km");
		
	}
	
	public void printEdges(Graph<Long, VertexContent, Double> grafo, Iterator<Edge<Double>> it, Long id1, Long id2) {
		System.out.println("Camino m·s corto entre el vÈrtice " + id1 + "y el vÈrtice " + id2 );
		
		int cont = 1; int infra = 0; int dist = 0;
		while(it.hasNext()) {
			cont++;
			Edge<Double> edge = it.next();
			System.out.println(edge.either() + "(id) " + "->" + edge.other(edge.either()) + " (id)" + " Distancia: " + edge.getInfo() + " Km");
			infra += grafo.getInfoVertex(grafo.translateInverse(edge.either())).infractions.getSize();
			dist+=edge.getInfo();
		}
		infra -= cont;
		System.out.println("Total de infracciones: " + infra + " Distancia total: " + dist);
	}

	public void printCuadriculaB2(HashTableSC<Long, Coordinates> vertices) {
		System.out.println("Resultado de la aproximaciÛn: ");
		System.out.println("idVertice - Ubicacion: lat, lon");
		Iterator<Long> it = vertices.keys();
		while(it.hasNext()){
			Long pTemp = it.next();
			Coordinates coords = vertices.get(pTemp);
			System.out.println( pTemp + " - UbicaciÛn:" + coords.lat + "," + coords.lon);
		}
	}
	
}