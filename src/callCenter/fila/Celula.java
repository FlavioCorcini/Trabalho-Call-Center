package callCenter.fila;

public class Celula <T> {
	T item;
	Celula<T> prox;
	
	// construtor
	public Celula() {
	}
	public Celula(T item) {
		this.item = item;
	}
}
