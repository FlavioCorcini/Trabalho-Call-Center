package callCenter.entity;

import java.util.Random;

public class Cliente {
	private static long contadorId = 0;
	static String nomes[] = {"Maria Luiza","Flávio","Rhuan","Jamaica","Carlos","Gabriel","Mariana","Geovana","Filipe"};
	static String sobrenomes[] = {"Pena","Corcini","Oliveira","Samora","Assis","Quintão"};
	
	//atributos
	private long id;
	private String nome;
	
	//construtor
	public Cliente(){
		this.id = contadorId++;
		this.nome = gerarNome();
		
		}
	
	
	//metodos
	String gerarNome() {
		Random random = new Random();
		
		int valor = random.nextInt(8);
		String nome	= nomes[valor];
		
		valor = random.nextInt(5);
		nome += sobrenomes[valor];
		
		valor = random.nextInt(5);
		nome += sobrenomes[valor];
		
		return nome;
		}



	//GETs
	public String getNome() {
		return nome;
	}

	public long getId() {
		return id;
	}
	
	
}
