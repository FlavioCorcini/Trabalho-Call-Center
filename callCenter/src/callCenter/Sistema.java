package callCenter;

import java.util.Random;

import callCenter.entity.*;
import callCenter.fila.Fila;

public class Sistema {

	private static final Sistema instancia = new Sistema();
	
	Random random = new Random();
	
	//Atributos do sistema
	
	private int tempoAtual; // tempo do programa "agora". (minutos);
	private int tempSimulacao; // tempo máximo que o programa funciona, 12h(720min)(se hover cliente sendo atendido ele continua funcionando)
	private int quantMaxAtendent; // número máximo de atendentes (trabalhando ao mesmo tempo);
	private int tempMaxEspera; //tempo máximo que um cliente pode esperar na fila;
	
		//Probabilidade
	private float probNovoCl; //probabilidade de um novo cliente 
	
		//Filas de Clientes
	
	Fila<Cliente> filaCliente = new Fila<Cliente>(); //Fila padrão de clientes;
	Fila<Cliente> filaClPreferencial = new Fila<Cliente>(); //Fila preferencial de clientes;
	
	 	//Fila Box
	Fila<Box> boxesAtivos = new Fila<Box>(); // lista de boxes em funcionamento;
	
		//Fila Atendente
	Fila<Atendente> filaAtendentes =new Fila<Atendente>(); //Fila de atendentes que irão para os boxes
	Fila<Atendente> filaAtendentesAtiv =new Fila<Atendente>(); //Fila de atendentes em trabalho
	
	
	//construtor (privado)
	private Sistema() {
	}
	
	
	//Pegar instancia
	public static Sistema getInstancia() {
		return instancia;
	}
	
	//metodos
		//função principal (inicia a simulação)
	public void iniciar() {
		
		tempoAtual = 0; 
		tempSimulacao = 720;
		
		tempMaxEspera = 15;
		
		probNovoCl = 30;
		
		//box inicial
		Atendente novoAtendente = new Atendente();
		
		Box novoBox = new Box(novoAtendente);
		
		
		do {	//loop principal, cada repetição equivale a um minuto
			if (tempoAtual<tempSimulacao) {
				
				// Chance de chegada de um novo cliente
				if (prob(probNovoCl)) { 
					Cliente novoCliente = new Cliente();
					
					int aux = random.nextInt(3);
					if (aux<3) { // 75% de chance do cliente ser da fila normal 
						filaCliente.add(novoCliente);						
					}else {
						filaClPreferencial.add(novoCliente);
					}
				}
				
				//chance de abrir um box
//				if (condition) {
//					
//				}
			}
			
			tempoAtual++;
		} while (tempoAtual<tempSimulacao || filaCliente.getInicio()!=null ||
				 filaClPreferencial.getInicio()!=null);
	}
	
	private boolean prob(float porcentagem) {
		
		 float aux = random.nextFloat(100);// aux recebe um número aleatóro de 1 a 100
		 
		 if (aux>porcentagem) { // Se aux estiver fora do renge de porcentagem (for maior) retorna falso, se não, retorna verdadeiro
			return false;
		}else{
			return true;
		} 
	}
	
}
