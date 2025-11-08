package callCenter.fila;


public class Fila<T> {
	Celula<T> inicio = new Celula();
	Celula<T> fim= new Celula();
	int numItens;
	
	// costrutor
	public Fila() {
		inicio.prox = fim;
		numItens = 0;
	}
}
