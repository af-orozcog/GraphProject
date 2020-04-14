package controller;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;

import com.opencsv.CSVReader;
import com.teamdev.jxmaps.LatLng;

import model.data_structures.*;
import model.vo.*;
import view.MapVisual;
import view.MovingViolationsManagerView;

public class Controller {
	public static String[] meses = {"January", "February", "March", "April", "May", 
			"August", "September", "November", "December"};

	private static LatLng max = new LatLng(38.9852065,-76.2653718);
	private static LatLng min = new LatLng(38.766235,-78.284922);

	// Componente vista (consola)
	private MovingViolationsManagerView view;

	//DONE Definir los atributos de estructuras de datos del modelo del mundo del proyecto
	private Graph<Long, VertexContent, Double> mapa;

	private Graph<Long, VertexContent, Double> aproximacion;

	private Graph<Long, VertexContent, Double> grafoPunto2;

	/**
	 * Metodo constructor
	 */
	public Controller()
	{
		view = new MovingViolationsManagerView();
	}

	/**
	 * Metodo encargado de ejecutar los  requerimientos segun la opcion indicada por el usuario
	 */
	public void run(){

		long startTime;
		long endTime;
		long duration;

		Scanner sc = new Scanner(System.in);
		boolean fin = false;


		while(!fin){
			view.printMenu();

			int option = sc.nextInt();
			long idVertice1 = 0;
			long idVertice2 = 0;


			switch(option){

			case 0:
				String RutaArchivo = "";
				view.printMessage("Escoger el grafo a cargar: (1) Downtown  o (2)Ciudad Completa.");
				int ruta = sc.nextInt();
				startTime = System.currentTimeMillis();
				if(ruta == 1) {
					try {
						RutaArchivo = "./data/jsonOpenCompleto.json"; //DONE Dar la ruta del archivo de Downtown
						loadJSON(RutaArchivo);
					} 
					catch (Exception e) {
						RutaArchivo = "./data/jsonOpen.json";
						try {
							loadJSON(RutaArchivo);
						} catch (Exception e1) {
							e1.printStackTrace();
							System.err.println("Error en lectura de JSON");
						}
						loadCVS();
						saveJSON(RutaArchivo);
					}
				}
				else {
					RutaArchivo = "./data/finalGraph.json"; //DONE Dar la ruta del archivo de la ciudad completa
					try {
						loadJSON(RutaArchivo);
					} catch (Exception e) {
						System.err.println("Error en lectura de JSON");
					}
				}
				endTime = System.currentTimeMillis();
				duration = endTime - startTime;
				view.printMessage("Tiempo del requerimiento: " + duration + " milisegundos");
				// DONE Informar el total de vértices y el total de arcos que definen el grafo cargado
				view.printReq0(mapa);
				break;



			case 1:

				view.printMessage("Ingrese El id del primer vertice (Ej. 287864): ");
				idVertice1 = sc.nextLong();
				view.printMessage("Ingrese El id del segundo vertice (Ej. 234662): ");
				idVertice2 = sc.nextLong();


				startTime = System.currentTimeMillis();
				caminoCostoMinimoA1(idVertice1, idVertice2);
				endTime = System.currentTimeMillis();
				duration = endTime - startTime;
				view.printMessage("Tiempo del requerimiento: " + duration + " milisegundos");

				/* 
				TODO Consola: Mostrar el camino a seguir con sus vértices (Id, Ubicación Geográfica),
				el costo mínimo (menor cantidad de infracciones), y la distancia estimada (en Km).

				TODO Google Maps: Mostrar el camino resultante en Google Maps 
				(incluyendo la ubicación de inicio y la ubicación de destino).
				 */
				break;

			case 2:
				view.printMessage("2A. Consultar los N v�rtices con mayor n�mero de infracciones. Ingrese el valor de N: ");
				int n = sc.nextInt();

				startTime = System.currentTimeMillis();
				mayorNumeroVerticesA2(n);
				endTime = System.currentTimeMillis();
				duration = endTime - startTime;
				view.printMessage("Tiempo del requerimiento: " + duration + " milisegundos");
				/* 
				TODO Consola: Mostrar la informacion de los n vertices 
				(su identificador, su ubicación (latitud, longitud), y el total de infracciones) 
				Mostra el número de componentes conectadas (subgrafos) y los  identificadores de sus vertices 

				TODO Google Maps: Marcar la localización de los vértices resultantes en un mapa en
				Google Maps usando un color 1. Destacar la componente conectada más grande (con
				más vértices) usando un color 2. 
				 */
				break;

			case 3:			

				view.printMessage("Ingrese El id del primer vertice (Ej. 287864): ");
				idVertice1 = sc.nextLong();
				view.printMessage("Ingrese El id del segundo vertice (Ej. 234662): ");
				idVertice2 = sc.nextLong();

				startTime = System.currentTimeMillis();
				caminoLongitudMinimoaB1(idVertice1, idVertice2);
				endTime = System.currentTimeMillis();
				duration = endTime - startTime;
				view.printMessage("Tiempo del requerimiento: " + duration + " milisegundos");

				/*
				   DONE Consola: Mostrar  el camino a seguir, informando
					el total de vértices, sus vértices (Id, Ubicación Geográfica) y la distancia estimada (en Km).

				   DONE Google Maps: Mostre el camino resultante en Google Maps (incluyendo la
					ubicación de inicio y la ubicación de destino).
				 */
				break;

			case 4:		
				view.printMessage("Ingrese la longitud minima (Ej. -77,054806): ");
				double lonMin = sc.nextDouble();
				view.printMessage("Ingrese la longitud m�xima (Ej. -77,018894): ");
				double lonMax = sc.nextDouble();

				//double lonMin = -77.054806; double lonMax = -77.018894;

				view.printMessage("Ingrese la latitud minima (Ej. 38,893351): ");
				double latMin = sc.nextDouble();
				view.printMessage("Ingrese la latitud m�xima (Ej. 38,919144): ");
				double latMax = sc.nextDouble();

				//double latMin = 38.893351; double latMax = 38.919144;

				view.printMessage("Ingrese el n�mero de columnas");
				int columnas = sc.nextInt();
				view.printMessage("Ingrese el n�mero de filas");
				int filas = sc.nextInt();


				startTime = System.currentTimeMillis();
				definirCuadriculaB2(lonMin,lonMax,latMin,latMax,columnas,filas);
				endTime = System.currentTimeMillis();
				duration = endTime - startTime;
				view.printMessage("Tiempo del requerimiento: " + duration + " milisegundos");

				/*
				   DONE Consola: Mostrar el número de vértices en el grafo
					resultado de la aproximación. Mostar el identificador y la ubicación geográfica de cada
					uno de estos vértices. 

				   DONE Google Maps: Marcar las ubicaciones de los vértices resultantes de la
					aproximación de la cuadrícula en Google Maps.
				 */

				break;

			case 5:

				startTime = System.currentTimeMillis();
				arbolMSTKruskalC1();
				endTime = System.currentTimeMillis();
				duration = endTime - startTime;
				view.printMessage("Tiempo del requerimiento: " + duration + " milisegundos");
				/*
				   TODO Consola: Mostrar los vértices (identificadores), los arcos incluidos (Id vértice inicial e Id vértice
					final), y el costo total (distancia en Km) del árbol.

				   TODO Google Maps: Mostrar el árbol generado resultante en Google Maps: sus vértices y sus arcos.
				 */

				break;

			case 6:

				startTime = System.currentTimeMillis();
				arbolMSTPrimC2();
				endTime = System.currentTimeMillis();
				duration = endTime - startTime;
				view.printMessage("Tiempo del requerimiento: " + duration + " milisegundos");
				/*
				   TODO Consola: Mostrar los vértices (identificadores), los arcos incluidos (Id vértice inicial e Id vértice
				 	final), y el costo total (distancia en Km) del árbol.

				   TODO Google Maps: Mostrar el árbol generado resultante en Google Maps: sus vértices y sus arcos.
				 */
				break;

			case 7:

				startTime = System.currentTimeMillis();
				caminoCostoMinimoDijkstraC3();
				endTime = System.currentTimeMillis();
				duration = endTime - startTime;
				view.printMessage("Tiempo del requerimiento: " + duration + " milisegundos");
				/*
				   TODO Consola: Mostrar de cada camino resultante: su secuencia de vértices (identificadores) y su costo (distancia en Km).

				   TODO Google Maps: Mostrar los caminos de costo mínimo en Google Maps: sus vértices
					y sus arcos. Destaque el camino más largo (en distancia) usando un color diferente
				 */
				break;

			case 8:
				view.printMessage("Ingrese El id del primer vertice (Ej. 901839): ");
				idVertice1 = sc.nextInt();
				view.printMessage("Ingrese El id del segundo vertice (Ej. 901839): ");
				idVertice2 = sc.nextInt();

				startTime = System.currentTimeMillis();
				caminoMasCortoC4(idVertice1, idVertice2);
				endTime = System.currentTimeMillis();
				duration = endTime - startTime;
				view.printMessage("Tiempo del requerimiento: " + duration + " milisegundos");
				/*
				   TODO Consola: Mostrar del camino resultante: su secuencia de vértices (identificadores), 
				   el total de infracciones y la distancia calculada (en Km).

				   TODO Google Maps: Mostrar  el camino resultante en Google Maps: sus vértices y sus arcos.	  */
				break;

			case 9: 	
				fin = true;
				sc.close();
				break;
			}
		}
	}


	private void saveJSON(String rutaArchivo) {
		JSONArray grafo = new JSONArray();

		Iterator<Long> it1 = mapa.vertices();
		while(it1.hasNext()) {
			Long id1 = it1.next();	
			Coordinates coor = mapa.getInfoVertex(id1).coor;
			JSONObject vertice = new JSONObject();
			vertice.put("id", id1);
			vertice.put("lat", coor.lat);
			vertice.put("lon", coor.lon);

			Iterator<Long> it2 = mapa.adj(id1);
			JSONArray adj = new JSONArray();
			while(it2.hasNext()) {
				adj.add(it2.next().toString());
			}
			vertice.put("adj", adj);

			JSONArray infractions = new JSONArray();
			Iterator<Long> it3 = mapa.getInfoVertex(id1).infractions.iterator();
			while(it3.hasNext()) {
				infractions.add(it3.next().toString());
			}
			vertice.put("infractions", infractions);

			grafo.add(vertice);
		}
		try (FileWriter file = new FileWriter(rutaArchivo)) {

			file.write(grafo.toJSONString());
			file.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// DONE El tipo de retorno de los m�todos puede ajustarse seg�n la conveniencia


	/**
	 * Cargar el Grafo No Dirigido de la malla vial: Downtown o Ciudad Completa
	 * @param rutaArchivo 
	 */
	public void loadJSON(String rutaArchivo) throws Exception
	{
		Graph<Long,VertexContent,Double> copia = mapa;
		mapa = new Graph<Long, VertexContent, Double>();
		try {
			FileReader nm = new FileReader(rutaArchivo);
			JSONParser parser = new JSONParser();
			JSONArray array = (JSONArray) parser.parse(nm);
			for(Object o1 : array) {
				JSONObject vertice = (JSONObject) o1;
				Coordinates coor = new Coordinates(
						Double.parseDouble(vertice.get("lat").toString()), 
						Double.parseDouble(vertice.get("lon").toString()));

				VertexContent cont = new VertexContent(coor);

				JSONArray inf = (JSONArray) vertice.get("infractions");
				for(Object o2 : inf) {
					Long infra = Long.parseLong(o2.toString());
					cont.infractions.add(infra);
				}
				Long id = Long.parseLong(vertice.get("id").toString());
				mapa.addVertex(id, cont);

				JSONArray adj = (JSONArray) vertice.get("adj");
				for(Object o2 : adj) {
					Long ady = Long.parseLong(o2.toString());
					if(mapa.getInfoVertex(ady) != null) {
						mapa.addEdge(id, ady, difference(coor, mapa.getInfoVertex(ady).coor));
					}
				}

			}


		} catch (Exception e) {
			mapa = copia;
			throw e;
		}
	}

	/**
	 * 
	 */
	@SuppressWarnings("deprecation")
	public void loadCVS() {
		try {
			for(int i=0; i<12; i++) {
				CSVReader reader = new CSVReader(new FileReader("."+File.separator+"data"+File.separator+meses[i]+"_wgs84.csv"),';');
				String nextLine[];
				nextLine = reader.readNext();
				while((nextLine = reader.readNext()) != null) {
					int dif = 1;
					if(i == 0) {
						dif = 0;
					}
					Long id = Long.parseLong(nextLine[0 + dif]);
					Double lat = Double.parseDouble(nextLine[17 + dif].replace(',', '.'));
					Double lon = Double.parseDouble(nextLine[18 + dif].replace(',', '.'));

					Coordinates original = new Coordinates(lat, lon);
					Iterator<Long> it = mapa.vertices();
					Long idVer = it.next();

					Coordinates menor = mapa.getInfoVertex(idVer).coor;
					Double distancia = differenceShort(original, menor);
					while(it.hasNext()) {
						Long idComp = it.next();
						Coordinates comp = mapa.getInfoVertex(idComp).coor;
						Double compDis = difference(original, comp);
						if(compDis < distancia) {
							distancia = compDis;
							idVer = idComp;
						}
					}

					mapa.getInfoVertex(idVer).infractions.add(id);
				}
				reader.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param p1
	 * @param p2
	 * @return
	 */
	public Double difference(Coordinates p1, Coordinates p2) {
		Double x1 = p1.lat - p2.lat; x1 = x1*x1;
		Double y1 = p1.lon - p2.lon; y1 = y1*y1;
		final int R = 6371; // Radio de la tierra

		Double latDistance = toRad(p2.lat-p1.lat);
		Double lonDistance = toRad(p2.lon-p1.lon);

		Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + 
				Math.cos(toRad(p1.lat)) * Math.cos(toRad(p2.lat)) * 
				Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

		Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

		Double distance = R * c;
		return distance;
	}

	private static Double toRad(Double value) {
		return value * Math.PI / 180;
	}

	/**
	 * Crea y grafica el mapa 
	 * @param argumentos
	 */
	public void generarMapaGrafo(Graph<Long, VertexContent, Double> pGrafo,LatLng min,LatLng max,boolean pSinoVertices, Graph<Long, VertexContent, Double> pGrafoAdicional)
	{
		MapVisual x=new MapVisual(pGrafo,min,max,pSinoVertices, pGrafoAdicional);
		MapVisual.graficarMapa(x);
	}

	// DONE El tipo de retorno de los m�todos puede ajustarse seg�n la conveniencia
	/**
	 * Requerimiento 1A: Encontrar el camino de costo m�nimo para un viaje entre dos ubicaciones geogr�ficas.
	 * @param idVertice2 
	 * @param idVertice1 
	 */
	public void caminoCostoMinimoA1(long idVertice1, long idVertice2) {
		DijkstraInfractions<Long,VertexContent>camino = new DijkstraInfractions<Long,VertexContent>(mapa,idVertice1); 
		view.caminoCostoMinimoA1(mapa, camino, idVertice1, idVertice2);
		Graph<Long, VertexContent, Double> aGraficar = new Graph<Long, VertexContent, Double>();
		Iterator<Edge<Double>> aIterar = camino.pathTo(idVertice2).iterator();
		if(!aIterar.hasNext())
			return;
		Edge<Double> way = aIterar.next();
		Integer vertice1 = way.either();
		Integer vertice2 = way.other(vertice1);
		aGraficar.addVertex(mapa.translateInverse(vertice1), mapa.getInfoVertex(mapa.translateInverse(vertice1)));
		aGraficar.addVertex(mapa.translateInverse(vertice2), mapa.getInfoVertex(mapa.translateInverse(vertice2)));
		aGraficar.addEdge(mapa.translateInverse(vertice1), mapa.translateInverse(vertice2), way.getInfo());

		while(aIterar.hasNext()) {
			way = aIterar.next();
			vertice1 = vertice2;
			vertice2 = way.other(vertice1);
			aGraficar.addVertex(mapa.translateInverse(vertice2), mapa.getInfoVertex(mapa.translateInverse(vertice2)));
			aGraficar.addEdge(mapa.translateInverse(vertice1), mapa.translateInverse(vertice2), way.getInfo());
		}
		generarMapaGrafo(aGraficar, min, max, false, null);
	}

	// DONE El tipo de retorno de los m�todos puede ajustarse seg�n la conveniencia
	/**
	 * Requerimiento 2A: Determinar los n v�rtices con mayor n�mero de infracciones. Adicionalmente identificar las
	 * componentes conectadas (subgrafos) que se definan �nicamente entre estos n v�rtices
	 * @param  int n: numero de vertices con mayor numero de infracciones  
	 */
	public void mayorNumeroVerticesA2(int n) {
		mapa.createPQ();
		IndexMaxPQ<VertexContent> vertex = mapa.getPQ();
		ORArray<Long> ids = new ORArray<Long>();
		int i = 0;
		for(Integer inte : vertex){
			if(i == n)
				break;
			ids.add(mapa.translateInverse(inte));
			i++;
		}
		Graph<Long, VertexContent, Double> aGraficar = new Graph<Long, VertexContent, Double>();
		for(i = 0; i < ids.getSize(); i++){
			aGraficar.addVertex(ids.getElement(i), mapa.getInfoVertex(ids.getElement(i)));
			for(int j = i + 1; j < ids.getSize(); j++){
				aGraficar.addVertex(ids.getElement(j), mapa.getInfoVertex(ids.getElement(j)));
				if(mapa.getInfoArc(ids.getElement(i),ids.getElement(j)) != null){
					//System.out.println("tienen pareja");
					aGraficar.addEdge(ids.getElement(i), ids.getElement(j), mapa.getInfoArc(ids.getElement(i),ids.getElement(j)));
				}
			}
		}

		ConnectedComponents<Long, VertexContent, Double> componenteMasGrande = new ConnectedComponents<Long, VertexContent, Double>(aGraficar);
		Queue<Edge<Double>> componentes = componenteMasGrande.biggestConnectedComponent();
		System.out.println(componenteMasGrande.count());
		Graph<Long, VertexContent, Double> aGraficar2 = new Graph<Long, VertexContent, Double>();
		view.printmayorNumeroVerticesA2(ids,componenteMasGrande.count());
		for(Edge<Double> edg : componentes){	
			Integer vertice1 = edg.either();
			Integer vertice2 = edg.other(vertice1);

			aGraficar2.addVertex(aGraficar.translateInverse(vertice1), aGraficar.getInfoVertex(aGraficar.translateInverse(vertice1)));
			aGraficar2.addVertex(aGraficar.translateInverse(vertice2), aGraficar.getInfoVertex(aGraficar.translateInverse(vertice2)));
			aGraficar2.addEdge(aGraficar.translateInverse(vertice1), aGraficar.translateInverse(vertice2), edg.getInfo());
		}
		grafoPunto2 = aGraficar2;
		LatLng max = new LatLng(38.9852065,-76.2653718);
		LatLng min = new LatLng(38.766235,-78.284922);
		for(Edge<Double> edg: aGraficar2.edges()){
			System.out.println("primer: " + edg.either() + " segundo:" + edg.other(edg.either()));
		}
		generarMapaGrafo(aGraficar, min, max, true, aGraficar2);
	}


	// DONE El tipo de retorno de los m�todos puede ajustarse seg�n la conveniencia
	/**
	 * Requerimiento 1B: Encontrar el camino m�s corto para un viaje entre dos ubicaciones geogr�ficas 
	 * @param idVertice2 
	 * @param idVertice1 
	 */
	public void caminoLongitudMinimoaB1(Long idVertice1, Long idVertice2) {
		BFSPaths<Long, VertexContent, Double> bfs = new BFSPaths<Long, VertexContent, Double>(mapa, idVertice1);
		Iterable<Long> iterable = bfs.pathTo(idVertice2);

		Iterator<Long> it1 = iterable.iterator();
		Iterator<Long> it2 = iterable.iterator();

		view.printCaminoLongitudMinimoaB1(it1, mapa, idVertice1, idVertice2);

		if(it2.hasNext()){

			Graph<Long, VertexContent, Double> gTemp = new Graph<Long, VertexContent, Double>();

			Long idNuv = it2.next();
			VertexContent contNuv = mapa.getInfoVertex(idNuv);
			gTemp.addVertex(idNuv, contNuv);

			while(it2.hasNext()){
				Long idOrg = idNuv;
				idNuv = it2.next();
				contNuv = mapa.getInfoVertex(idNuv);

				gTemp.addVertex(idNuv, contNuv);
				gTemp.addEdge(idOrg, idNuv, mapa.getInfoArc(idOrg, idNuv));
			}

			generarMapaGrafo(gTemp, min, max, false, null);

		}
	}

	// DONE El tipo de retorno de los m�todos puede ajustarse seg�n la conveniencia
	/**
	 * Requerimiento 2B:  Definir una cuadricula regular de N columnas por M filas. que incluya las longitudes y latitudes dadas
	 * @param  lonMin: Longitud minima presente dentro de la cuadricula
	 * @param  lonMax: Longitud maxima presente dentro de la cuadricula
	 * @param  latMin: Latitud minima presente dentro de la cuadricula
	 * @param  latMax: Latitud maxima presente dentro de la cuadricula
	 * @param  columnas: Numero de columnas de la cuadricula
	 * @param  filas: Numero de filas de la cuadricula
	 */
	public void definirCuadriculaB2(double lonMin, double lonMax, double latMin, double latMax, int columnas,
			int filas) {
		// DONE Auto-generated method stub	
		HashTableSC<Long, Coordinates> vertices = new HashTableSC<Long, Coordinates>(columnas*filas);
		Double N = Math.abs(latMax - latMin)/(columnas-1);
		Double M = Math.abs(lonMax - lonMin)/(filas-1);
		for (int i = 0; i < columnas; i++) {
			for (int j = 0; j < filas; j++) {
				Coordinates original = new Coordinates(latMin + i*N, lonMin + j*M);

				Iterator<Long> it = mapa.vertices();
				Long idMenor = it.next();
				Coordinates menor = mapa.getInfoVertex(idMenor).coor;

				Double distancia = differenceShort(original, menor);

				while(it.hasNext()) {
					Long id = it.next();
					Coordinates comp = mapa.getInfoVertex(id).coor;
					Double compDis = differenceShort(original, comp);
					if(compDis < distancia) {
						menor = comp;
						distancia = compDis;
						idMenor = id;
					}
				}
				vertices.put(idMenor, menor);
			}
		}		
		view.printCuadriculaB2(vertices);
		Iterator<Long> it = vertices.keys();
		if(it.hasNext()){
			aproximacion = new Graph<Long, VertexContent, Double>();

			while(it.hasNext()){
				Long idNuv = it.next();
				VertexContent contNuv = new VertexContent(vertices.get(idNuv));
				aproximacion.addVertex(idNuv, contNuv);

			}

			LatLng max = new LatLng(latMax,lonMax);
			LatLng min = new LatLng(latMin,lonMin);
			generarMapaGrafo(aproximacion, min, max, true, null);
		}
	}

	public double differenceShort (Coordinates p1, Coordinates p2) {
		double x = p1.lat - p2.lat;
		double y = p1.lon - p2.lon;
		x *= x; y *= y;
		return Math.sqrt(x + y);
	}

	// DONE El tipo de retorno de los m�todos puede ajustarse seg�n la conveniencia
	/**
	 * Requerimiento 1C:  Calcular un �rbol de expansi�n m�nima (MST) con criterio distancia, utilizando el algoritmo de Kruskal.
	 */
	public void arbolMSTKruskalC1() {

		KruskalMST<Long,VertexContent> algoKrus = new KruskalMST<Long,VertexContent>(grafoPunto2);
		Iterable<Edge<Double>> edg1 = algoKrus.edges();
		Iterable<Edge<Double>> edg2 = algoKrus.edges();
		System.out.println("Los vertices y arcos del arbol");
		for(Edge<Double> ege : edg1){
			System.out.println("vertices del arbol en el mismo arco: " + grafoPunto2.translateInverse(ege.either()) + " --- " + grafoPunto2.translateInverse(ege.other(ege.either())));
		}
		System.out.println("El peso del arbol es de: " + algoKrus.weight());
		Graph<Long, VertexContent, Double> aGraficar2 = new Graph<Long, VertexContent, Double>();
		for(Edge<Double> ege : edg2){	
			Integer vertice1 = ege.either();
			Integer vertice2 = ege.other(vertice1);

			aGraficar2.addVertex(grafoPunto2.translateInverse(vertice1), grafoPunto2.getInfoVertex(grafoPunto2.translateInverse(vertice1)));
			aGraficar2.addVertex(grafoPunto2.translateInverse(vertice2), grafoPunto2.getInfoVertex(grafoPunto2.translateInverse(vertice2)));
			aGraficar2.addEdge(grafoPunto2.translateInverse(vertice1), grafoPunto2.translateInverse(vertice2), ege.getInfo());
		}
		LatLng max = new LatLng(38.9852065,-76.2653718);
		LatLng min = new LatLng(38.766235,-78.284922);
		generarMapaGrafo(aGraficar2, min, max, false, null);
	}

	// DONE El tipo de retorno de los m�todos puede ajustarse seg�n la conveniencia
	/**
	 * Requerimiento 2C: Calcular un �rbol de expansi�n m�nima (MST) con criterio distancia, utilizando el algoritmo de Prim. (REQ 2C)
	 */
	public void arbolMSTPrimC2() {
		// TODO Auto-generated method stub
		PrimMST<Long,VertexContent> algoPrim = new PrimMST<Long,VertexContent>(grafoPunto2);
		Iterable<Edge<Double>> edg1 = algoPrim.edges();
		Iterable<Edge<Double>> edg2 = algoPrim.edges();
		System.out.println("Los vertices y arcos del arbol");
		for(Edge<Double> ege : edg1){
			System.out.println("vertices del arbol en el mismo arco: " + grafoPunto2.translateInverse(ege.either()) + " --- " + grafoPunto2.translateInverse(ege.other(ege.either())));
		}
		System.out.println("El peso del arbol es de: " + algoPrim.weight());
		Graph<Long, VertexContent, Double> aGraficar2 = new Graph<Long, VertexContent, Double>();
		for(Edge<Double> ege : edg2){	
			Integer vertice1 = ege.either();
			Integer vertice2 = ege.other(vertice1);

			aGraficar2.addVertex(grafoPunto2.translateInverse(vertice1), grafoPunto2.getInfoVertex(grafoPunto2.translateInverse(vertice1)));
			aGraficar2.addVertex(grafoPunto2.translateInverse(vertice2), grafoPunto2.getInfoVertex(grafoPunto2.translateInverse(vertice2)));
			aGraficar2.addEdge(grafoPunto2.translateInverse(vertice1), grafoPunto2.translateInverse(vertice2), ege.getInfo());
		}
		LatLng max = new LatLng(38.9852065,-76.2653718);
		LatLng min = new LatLng(38.766235,-78.284922);
		generarMapaGrafo(aGraficar2, min, max, false, null);
	}

	// DONE El tipo de retorno de los m�todos puede ajustarse seg�n la conveniencia
	/**
	 * Requerimiento 3C: Calcular los caminos de costo m�nimo con criterio distancia que conecten los v�rtices resultado
	 * de la aproximaci�n de las ubicaciones de la cuadricula N x M encontrados en el punto 5.
	 */
	public void caminoCostoMinimoDijkstraC3() {
		if(aproximacion == null)
			return;

		view.printMessage("Informaci�n de cada camino resultante");

		Double mayor = -1.0;
		Iterable<Edge<Double>> mayorCamino = null;

		Iterator<Long> it1 = aproximacion.vertices();
		while (it1.hasNext()) {
			Long key1 = it1.next();
			Dijkstra<Long, VertexContent> dij = new Dijkstra<Long, VertexContent>(mapa, key1);

			Iterator<Long> it2 = aproximacion.vertices();
			Long key2 = it2.next();
			while(it2.hasNext() && key2!=key1) {
				key2 = it2.next();
			}

			while(it2.hasNext()) {
				key2 = it2.next();
				view.printMessage("");
				view.printMessage("Camino entre el v�rtice " + key1 + " (id) y el v�rtice " + key2 + " (id)" );
				view.printMessage("");

				Iterable<Edge<Double>> path = dij.pathTo(key2);

				if(path!= null) {
					Iterator<Edge<Double>> it3 = path.iterator();
					Double recorrido = 0.0;
					while(it3.hasNext()) {
						Edge<Double> edge = it3.next();
						Long v1 = mapa.translateInverse(edge.either()); 
						Long v2 = mapa.translateInverse(edge.other(edge.either()));

						view.printMessage(v1+ " -> " + v2 + " Distancia: " + edge.getInfo() + " Km");

						aproximacion.addEdge(v1, v2, edge.getInfo());
						recorrido += edge.getInfo();
					}
					view.printMessage("La distancia total del camino fue de " + recorrido + " Km");

					if(recorrido > mayor) {
						mayorCamino = path;
						mayor = recorrido;
					}
				}
				else {
					System.out.println("NO HAY CAMINO ENTRE LOS DOS V�RTICES");
				}
			}
		}

		Iterator<Edge<Double>> itMayor = mayorCamino.iterator();
		Graph<Long, VertexContent, Double> gTemp = new Graph<Long, VertexContent, Double>();

		while(itMayor.hasNext()) {
			Edge<Double> way = itMayor.next();
			Integer vertice1 = way.either();
			Integer vertice2 = way.other(vertice1);

			gTemp.addVertex(mapa.translateInverse(vertice1), mapa.getInfoVertex(mapa.translateInverse(vertice1)));
			gTemp.addVertex(mapa.translateInverse(vertice2), mapa.getInfoVertex(mapa.translateInverse(vertice2)));
			gTemp.addEdge(mapa.translateInverse(vertice1), mapa.translateInverse(vertice2), way.getInfo());
		}
		generarMapaGrafo(aproximacion, min, max, false, gTemp);

	}


	// DONE El tipo de retorno de los m�todos puede ajustarse seg�n la conveniencia
	/**
	 * Requerimiento 4C:Encontrar el camino m�s corto para un viaje entre dos ubicaciones geogr�ficas escogidas aleatoriamente al interior del grafo.
	 * @param idVertice2 
	 * @param idVertice1 
	 */
	public void caminoMasCortoC4(long idVertice1, long idVertice2) {
		Iterator<Long> it = mapa.vertices();
		while(it.hasNext()) {
			Long key = it.next();
			VertexContent contenido = mapa.getInfoVertex(key);
			contenido.infractions.add(-10000L);
		}
		Dijkstra<Long, VertexContent> dij = new Dijkstra<Long, VertexContent>(mapa, idVertice1);
		Iterable<Edge<Double>> iter = dij.pathTo(idVertice2);

		Iterator<Edge<Double>> it1 = iter.iterator();
		Iterator<Edge<Double>> it2 = iter.iterator();


		view.printEdges( mapa, it1, idVertice1, idVertice2);
		Graph<Long, VertexContent, Double> gTemp = new Graph<Long, VertexContent, Double>();

		while(it2.hasNext()){
			Edge<Double> edge = it2.next();
			Long id1 = mapa.translateInverse(edge.either());
			Long id2 = mapa.translateInverse(edge.other(edge.either()));

			gTemp.addVertex(id1, mapa.getInfoVertex(id1));
			gTemp.addVertex(id2, mapa.getInfoVertex(id2));
			gTemp.addEdge(id1, id2, mapa.getInfoArc(id1, id2));
		}

		generarMapaGrafo(gTemp, min, max, false, null);

	}

}
