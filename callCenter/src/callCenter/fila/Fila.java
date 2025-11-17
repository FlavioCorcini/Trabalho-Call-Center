package callCenter.fila;


public class Fila<T> {
	
	private Celula<T> inicio;
	private Celula<T> fim;
	
	// -- costrutor
	public Fila() {
	}
	
	// -- metodos
	public T add(T item) {
		if (item == null) {
			throw new IllegalArgumentException("Erro: Não pode ser adicionado itens nulos");
		}
		
		Celula<T> novacell = new Celula<T>(item); //cria nova celula
		
		if (inicio == null) { // Se a lista estiver vazia 
			inicio = novacell; //início e fim recebem a nova celula
			fim = inicio;
			
		}else { //
			fim.prox = novacell;
			fim = novacell;			
		}
				
		return item;
	}

	
	public T pop() {
		if (inicio == null) {
			throw new NullPointerException("Erro: Lista vazia.");
		}
		
		T item = inicio.item;
		if (inicio == fim) {
			fim = null;
		}
		inicio = inicio.prox;
		
		
		return item;
	}
	
	// -- GETs
	public Celula<T> getInicio() {
		return inicio;
	}
	
	public Celula<T> getFim() {
		return fim;
	}
	
}
