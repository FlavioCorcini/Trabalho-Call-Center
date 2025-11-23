package callCenter.fila;

public class Celula <T> {
	public T item;
	public Celula<T> prox;
	//public Celula<T> ant;
	
	// construtor
	public Celula() {
	}
	public Celula(T item) {
		this.item = item;
	}
	
}
