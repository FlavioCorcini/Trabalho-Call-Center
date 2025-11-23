package callCenter.entity;

import java.util.Random;

public class Atendente {
	private static long contadorId = 0;
	static String nomes[] = {"Marcelo","Luciano","Marcia","Lucas","Rutyele","Bruno","Elder","Viviane","Odilon"};
	Random random = new Random();

	//atributos
	private long id;
	private String nome;
	private int temp; // armazena quando o atendente entrou no box para trabalhar ou quando começou o descanso

	//construtor
	public Atendente(){
		this.id = contadorId++; // id auto Incremento
		this.nome = gerarNome();
	}

	//metodos 
	
	private String gerarNome(){
		int aletorio = random.nextInt(8);
		return nomes[aletorio];
	}
	
	//GETs SETs
	public long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public int getTemp() {
		return temp;
	}

	public void setTemp(int temp) {
		this.temp = temp;
	}
	
	
	
}
