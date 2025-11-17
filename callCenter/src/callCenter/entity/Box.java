package callCenter.entity;

public class Box {
	private static long contadorId = 0;
	
	//atributos
	private long id;
	private Atendente atendente;
	private Cliente clienteSendAtend;
	
	//O tempo de atendimento de cada cliente deve variar de 3 a 10 minutos. 
	private int tempAtendimentoMax; //3 min.
	private int tempAtendimentoMin; //10 min.
	private int tempAtendimento; // no momento
	
	
	
	int numClientesAtend;
	
	
	// construtores
	public Box(){
		this.id = contadorId++;
		this.numClientesAtend = 0;
	}
	public Box(Atendente atendente){
		this.id = contadorId++;
		this.atendente = atendente;
		this.numClientesAtend = 0;
	}
	
	//metodos
	
	
	//GETs SETs
	public Atendente getAtendente() {
		return atendente;
	}
	
	public void setAtendente(Atendente atendente) {
		this.atendente = atendente;
	}
	
	public Cliente getClienteSendAtend() {
		return clienteSendAtend;
	}
	
	public void setClienteSendAtend(Cliente clienteSendAtend) {
		this.clienteSendAtend = clienteSendAtend;
		numClientesAtend++;
	}
	
	public long getId() {
		return id;
	}
	
	public int getTempAtendimentoMax() {
		return tempAtendimentoMax;
	}
	
	public void setTempAtendimentoMax(int tempAtendimentoMax) {
		this.tempAtendimentoMax = tempAtendimentoMax;
	}
	
	public int getTempAtendimentoMin() {
		return tempAtendimentoMin;
	}
	
	public void setTempAtendimentoMin(int tempAtendimentoMin) {
		this.tempAtendimentoMin = tempAtendimentoMin;
	}
	
	public int getTempAtendimento() {
		return tempAtendimento;
	}
	
	public void setTempAtendimento(int tempAtendimento) {
		this.tempAtendimento = tempAtendimento;
	}
	
	public int getNumClientesAtend() {
		return numClientesAtend;
	}
	
	
}
