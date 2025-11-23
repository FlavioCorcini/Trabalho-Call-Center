package callCenter.fila;


public class Fila<T> {
	
	private Celula<T> inicio;
	private Celula<T> fim;
	private int totalDeItens;
	
	// -- costrutor
	public Fila() {
		totalDeItens = 0;
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
		
		totalDeItens++;
		return item;
	}

	
	public T pop() {
		if (inicio == null) {
			throw new NullPointerException("Erro: Lista vazia.");
		}
		
		T item = inicio.item;
		if (inicio == fim) {
			inicio = null;
		}else {
			inicio = inicio.prox;
		}
		
		totalDeItens--;
		return item;
	}
	public void decrementarTotal() {
	    this.totalDeItens--;
	}
	
	// -- GETs
	public Celula<T> getInicio() {
		return inicio;
	}
	
	public Celula<T> getFim() {
		return fim;
	}

	public void setInicio(Celula<T> inicio) {
		this.inicio = inicio;
	}

	public void setFim(Celula<T> fim) {
		this.fim = fim;
	}

	public int getTotalDeItens() {
		return totalDeItens;
	}
	
}
