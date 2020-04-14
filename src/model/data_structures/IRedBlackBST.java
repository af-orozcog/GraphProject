package model.data_structures;

import java.util.Iterator;

/**
 * Inteface del arbol binario RedBlack
 */
public interface IRedBlackBST<V,K extends Comparable<K>> {

	/**
	 * Retornar el número de parejas [Llave,Valor] del árbol
	 * @return El número de parejas [Llave,Valor] del árbol
	 */
	public int size();
	
	/**
	 * Informa si el árbol es vacío
	 * @return Si el árbol es vacío
	 */
	public boolean isEmpty();
	
	/**
	 * Retorna el valor V asociado a la llave key dada. 
	 * Si la llave no se encuentra se retorna el valor null
	 * @param key Llave del valor a buscar
	 * @return El valor V asociado a la llave key dada.
	 */
	public V get(K key);
	
	/**
	 * Retorna la altura del camino desde la raiz para llegar a la llave key (si la llave existe). 
	 * Retorna valor –1 si la llave No existe.
	 * @param key Llave donde se busca saber la altura.
	 * @return La altura del camino desde la raiz para llegar a la llave key (si la llave existe).
	 */
	int getHeight(K key);
	
	/**
	 * Indica si la llave key se encuentra en el árbol
	 * @param key Llave a buscar
	 * @return Si la llave key se encuentra en el árbol
	 */
	boolean contains(K key);
	
	/**
	 * Inserta la pareja [key, val] en el árbol respetando el balanceo RedBlack. 
	 * Si la llave ya existe se reemplaza el valor. 
	 * @param key Llave de la pareja a insertar.
	 * @param val Valor de la pareja a insertar.
	 * @throws Exception Si la llave key o el valor val es null
	 */
	void put(K key, V val) throws Exception;
	
	/**
	 * Retorna la altura del árbol definida como la altura de la rama más alta 
	 * (aquella que tenga mayor número de enlaces desde la raíz a una hoja).
	 * @return La altura del árbol definida como la altura de la rama más alta
	 */
	int height();
	
	/**
	 * Retorna la llave más pequeña del árbol. Valor null si árbol vacío
	 * @return La llave más pequeña del árbol.
	 */
	K min();

	/**
	 * Retorna la llave más grande del árbol. Valor null si árbol vacío
	 * @return La llave más grande del árbol.
	 */
	K max();
	
	/**
	 * Valida si el árbol es Binario Ordenado y está balanceado RojoNegro a la izquierda. 
	 * Hay que validar que:
	 * (a) La llave de cada nodo sea mayor que cualquiera de su subárbol izquierdo.
	 * (b) La llave de cada nodo sea menor que cualquiera de su subárbol derecho.
	 * (c) Un nodo NO puede tener enlace rojo a su hijo derecho.
	 * (d) No puede haber dos enlaces rojos consecutivos a la izquierda. 
	 * Es decir, un nodo NO puede tener un enlace rojo a su hijo izquierdo 
	 * y su hijo izquierdo NO puede tener enlace rojo a su hijo izquierdo.
	 * (e) Todas las ramas tienen el mismo número de enlaces negros.
	 * @return Si el árbol es Binario Ordenado y está balanceado RojoNegro a la izquierda.
	 */
	boolean check();
	
	/**
	 * Retorna todas llaves del árbol como un iterador
	 * @return Un iterador de todas las llaves.
	 */
	Iterator<K> keys();
	
	/**
	 * Retorna todos los valores V en el árbol que estén asociados al rango de llaves dado.
	 * Por eficiencia, debe intentarse No recorrer todo el árbol.
	 * @param init Valor mínimo para la llave.
	 * @param end Valor máximo para la llave.
	 * @return Un iterador de todos los valores V que tengan llaves en el rango dado por parámetro
	 */
	Iterator<V> valuesInRange(K init, K end);
	
	/**
	 * Retorna todas las llaves K en el árbol que se encuentran en el rango de llaves dado.
	 * Por eficiencia, debe intentarse No recorrer todo el árbol.
	 * @param init Valor mínimo para la llave.
	 * @param end Valor máximo para la llave.
	 * @return Un iterador de todos los valores V que tengan llaves en el rango dado por parámetro
	 */
	Iterator<K> keysInRange(K init, K end);
}
