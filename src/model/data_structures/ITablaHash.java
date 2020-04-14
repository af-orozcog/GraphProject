package model.data_structures;

import java.util.Iterator;

public interface ITablaHash<K extends Comparable<K>, V>  {
	
	/**
	 * Método que retorna el número de elementos de la tabla hash
	 * @return el número de elementos de la tabla hash.
	 */
	public int getSize();
	
	/**
	 * Método que adiciona un nuevo elemento por llave y valor
	 * post: se ha adicionado un nuevo elemento al HashTable 
	 */
	public void put(K key, V val);
	
	/**
	 * Método que devuelve el elemento asociado con la llave pasada por parametro.
	 * @param Key la llave asociada a un valor, Key != null
	 * @return el elemento asociado con la llave pasada por parametro.
	 */
	public V get(K key);
	
	/**
	 * Método que borra el elemento asociado con la llave pasada por parametro y la llave
	 * post: se ha borrado la llave y el elemento asociado con la llave.
	 * @param Key la llave asociada a un valor. Key != null
	 * @return el valor que se borro asociado a la llave pasada por parametro.
	 */
	public V delete(K key);
	
	/**
	 * Método que devuelve un iterador a las llaves que se encuentran en HashTable
	 * @return un iterador a las llaves que se encuentran en HashTable
	 */
	public Iterator<K> keys();
	
	/**
	 * Método que retorna si la tabla contiene un elemento con esa llave
	 * @param key La llave a buscar
	 * @return Si la tabla contiene un valor asociado a esa llave
	 */
	public boolean contains(K key);

	/**
	 * Retorna el tamaño del arreglo
	 * @return El tamaño del arreglo
	 */
	public int getM();
	
	/**
	 * Retorna el número de rehash
	 * @return El número de rehash
	 */
	public int getRehash();
	
	/**
	 * 
	 * @return
	 */
	public boolean isEmpty();
}
