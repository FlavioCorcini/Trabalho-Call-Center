package callCenter;

import java.util.Random;

import callCenter.entity.*;
import callCenter.fila.*;


public class Sistema {

	private static final Sistema instancia = new Sistema();

	Random random = new Random();

	// Atributos do sistema

	private int tempoAtual; // tempo do programa "agora". (minutos);
	private int tempSimulacao; // tempo máximo que o programa funciona, 12h(720min)(se hover cliente sendo
								// atendido ele continua funcionando)
	
//	private int quantMaxAtendent; // número máximo de atendentes (trabalhando ao mesmo tempo);
	
	private int tempMaxEspera; // tempo máximo que um cliente pode esperar na fila;
	private int qtdAtdMin; // quntidade minima de atendentes
	private int qtdAtdMax; // quantidade máxima de atendentes
	private int qtdBoxMin; // quntidade minima de boxes
	private int qtdBoxMax; // quantidade máxima de boxex
	
	private int numClientesAtend; // número total de clientes atendidos
	private int numClientesDesist; // número total de clientes que desistiram
	private int numCliPorNumBox; // num de clientes na fila para cada num de box (4 na fila = 1 box)
	
	private int contPrioritSeguidos; // ordem de qual fila vai ser chamada (0 - normal ; 1 e 2 - preferencial)

	// Probabilidade
	private int probNovoCl; // probabilidade de um novo cliente (30%)

	// Filas de Clientes

	Fila<Cliente> filaCliente = new Fila<Cliente>(); // Fila padrão de clientes;
	Fila<Cliente> filaClPreferencial = new Fila<Cliente>(); // Fila preferencial de clientes;

	// Fila Box
	Fila<Box> boxesAtivos = new Fila<Box>(); // lista de boxes em funcionamento;

	// Fila Atendente
	Fila<Atendente> filaAtendentes = new Fila<Atendente>(); // Fila de atendentes que livres
	Fila<Atendente> filaDescanso = new Fila<Atendente>();   // Atendentes descansando
	
	// construtor (privado)
	private Sistema() {
	}

	// Pegar instancia
	public static Sistema getInstancia() {
		return instancia;
	}

	// metodos

	public void iniciar() { // função principal (inicia a simulação)
		//temp (Padrão)
		tempSimulacao = 720;
		tempoAtual = 0;
		
		//atendente (padrão)
		qtdAtdMax = 10;
		qtdAtdMin = 5;
		
		//box (Padrão)
		qtdBoxMax = 5; 
		qtdBoxMin = 1;
		
		//cliente (Padrão)
		tempMaxEspera = 15; //15
		numClientesAtend = 0;
		numClientesDesist = 0;
		
		numCliPorNumBox = 10; //10

		probNovoCl = 70; // 70
		
		//lista de atendentes livres
		gerarAtendentes();

		// box e atendente inicial
		Atendente novoAtendente = new Atendente();

		Box novoBox = new Box(novoAtendente); // cria um novo box com o atendente
		boxesAtivos.add(novoBox);

		do { // loop principal, cada repetição equivale a um minuto
			

			if (tempoAtual < tempSimulacao) { // se o tempo de funcionamento ainda não se encerrou:
				
				// Chance de chegada de um novo cliente
				chegadaClienteFila();
			}
			
			// atualiza tempos (cliente em fila, atendentendimentos e atendentes)
			atualizaTemps();

			// abertura e fechamento de box
			gerenciarBox(numCliPorNumBox);
			
			// Box (Atendimento)
			atendimento();

			tempoAtual++;
		} while (tempoAtual < tempSimulacao || filaCliente.getInicio() != null
				|| filaClPreferencial.getInicio() != null);
		
		//Simulção finalizada 
		System.out.println("========================================");
	    System.out.println("SIMULAÇÃO ENCERRADA (Minuto " + tempoAtual + ")");
	    System.out.println("========================================");
	    System.out.println("Total de Clientes Atendidos: " + numClientesAtend);
	    System.out.println("Total de Clientes que Desistiram: " + numClientesDesist);
	
	}

	private void chegadaClienteFila() { // chance de chegar um novo cliente
		if (prob(probNovoCl)) {
			Cliente novoCliente = new Cliente();
			novoCliente.setChegadaNFila(tempoAtual);

			int aux = random.nextInt(4);
			if (aux < 3) { // 75% de chance do cliente ser da fila normal
				filaCliente.add(novoCliente);
			} else { // 25% de chance do cliente ser da fila preferencial
				filaClPreferencial.add(novoCliente);
			}
			 //System.out.println("novo cliente: "+novoCliente.getChegadaNFila());
		}
	}

	private void atendimento() {// realiza as verificações para atendimento
		Celula<Box> cellBox = boxesAtivos.getInicio(); // pega a primeira celula de box

		
		while (cellBox != null) {// Varre todos os boxes para ver quem está livre
			Box box = cellBox.item;
			
			if (box.getClienteSendAtend() == null && box.getAtendente() != null) {// Se o box está livre (sem cliente) e tem um atendente
				Cliente clienteSelecionado = null;

				// Se atendeu menos de 2 Prioritários seguidos E tem gente na Prioritária
				if (contPrioritSeguidos < 2 && filaClPreferencial.getInicio() != null) {
					clienteSelecionado = filaClPreferencial.pop();
					contPrioritSeguidos++;
				}
				// Se chegou a hora de um Comum (ou fila Prioritária vazia) E tem gente na Comum
				else if (filaCliente.getInicio() != null) {
					clienteSelecionado = filaCliente.pop();
					contPrioritSeguidos = 0; // Reseta contador após atender comum
				}
				// Era vez de um comum, mas fila comum está vazia, então atende prioritário se tiver
				else if (filaClPreferencial.getInicio() != null) {
					clienteSelecionado = filaClPreferencial.pop();
					contPrioritSeguidos++;
				}

				// Se selecionou alguém, inicia o atendimento
				if (clienteSelecionado != null) {
					box.iniciarAtendimento(clienteSelecionado);
					//System.out.println("atendendo "+ clienteSelecionado.getNome());
					numClientesAtend++;
				}
			}
			cellBox = cellBox.prox;
		}
	}

	private void atualizaTemps() {
		// Atualizar Boxes (reduzir tempo de atendimento em cada box com cliente)
	    Celula<Box> cellBox = boxesAtivos.getInicio();
	    while (cellBox != null) {
	    	
	        cellBox.item.passarTempo();
	        
	        cellBox = cellBox.prox;
	    }
	    
	    // Filas de clientes
	    verificarDesistencias(filaClPreferencial);
	    verificarDesistencias(filaCliente);
	    
	    // Filas atendentes
	    atualiTempsAtend();
	}

	private void atualiTempsAtend() {
	    // Verificar quem já terminou o descanso
	    Fila<Atendente> aindaDescansando = new Fila<Atendente>();
	    
	    
	    // Processa todos que estão na fila de descanso
	    while (filaDescanso.getInicio() != null) {
	        Atendente atendenteD = filaDescanso.pop();
	        
	        int tempoDescansado = tempoAtual - atendenteD.getTemp();
	        
	        if (tempoDescansado >= 60) { // 60 min de descanso 
	            // Descanso acabou, volta pra fila de disponíveis
	            filaAtendentes.add(atendenteD);
	        } else {
	            // Ainda não deu o tempo, volta pra fila de descanso
	            aindaDescansando.add(atendenteD);
	        }
	    }
	    filaDescanso = aindaDescansando; // Atualiza a fila principal

	    // Verificar quem estourou o tempo de trabalho (3h)
	    Celula<Box> cellBox = boxesAtivos.getInicio();

	    while (cellBox != null) {
	        Box box = cellBox.item;
	        Atendente atendente = box.getAtendente();

	        if (atendente != null) {
	            int tempoTrabalhado = tempoAtual - atendente.getTemp();

	            if (tempoTrabalhado >= 180) { // 180 min (3h) de trabalho 
	                // Manda atendente cansado para o descanso
	            	//System.out.println("descanso: "+ atendente.getNome());
	                atendente.setTemp(tempoAtual);
	                filaDescanso.add(atendente);
	                
	                // Tenta substituir imediatamente
	                if (filaAtendentes.getInicio() != null) {
	                    Atendente novo = filaAtendentes.pop();
	                    novo.setTemp(tempoAtual); // Marca hora que começou
	                    box.setAtendente(novo);
	                } else {
	                    // Se não tem substituto, o box fica vazio (inativo temporariamente)
	                    box.setAtendente(null);
	                }
	            }
	        } else {
	            // Se o box está vazio, tenta colocar alguém que esteja livre
	            if (filaAtendentes.getInicio() != null) {
	                Atendente novo = filaAtendentes.pop();
	                novo.setTemp(tempoAtual);
	                box.setAtendente(novo);
	            }
	        }
	        
	        cellBox = cellBox.prox;
	    }
	}
	
	private void verificarDesistencias(Fila<Cliente> filaAnalizada) {	
		
	    Celula<Cliente> atual = filaAnalizada.getInicio();
	    Celula<Cliente> anterior = null;

	    while (atual != null) {
	        int tempoEspera = tempoAtual - atual.item.getChegadaNFila();

	        if (tempoEspera > tempMaxEspera) {
	            System.out.println("Minuto " + tempoAtual + ": Cliente desistiu - " + atual.item.getNome());
	            numClientesDesist++; 
	            
	            // Lógica de remoção
	            if (atual == filaAnalizada.getInicio()) {
	                filaAnalizada.pop();
	                atual = filaAnalizada.getInicio(); 
	                anterior = null;
	                continue; 
	            } else {
	                // Se for do meio ou fim
	                anterior.prox = atual.prox;
	                
	                if (anterior.prox == null) {
	                    filaAnalizada.setFim(anterior);
	                }
	                
	                filaAnalizada.decrementarTotal();
	                
	                atual = anterior.prox; 
	                continue; 
	            }
	        }
	        anterior = atual;
	        atual = atual.prox;
	    }
	}
	
	private void gerenciarBox(int n) {
	    // Calcula quantos clientes existem nas filas
	    int totalClientes = filaClPreferencial.getTotalDeItens() + filaCliente.getTotalDeItens();
	    
	    // Define a meta de boxes ativos
	    // Ex: 15 clientes / 5 n = 3 boxes. (+1 para garantir minimo de 1 sempre que houver cliente)
	    int boxesNecessarios = (totalClientes / n) + 1;

	    // Respeita os limites (QTDBOXMIN e QTDBOXMAX)
	    if (boxesNecessarios > qtdBoxMax) boxesNecessarios = qtdBoxMax;
	    if (boxesNecessarios < qtdBoxMin) boxesNecessarios = qtdBoxMin;

	    int qtdAtual = boxesAtivos.getTotalDeItens();

	    // Abrir novos boxes
	    
	    // Enquanto tivermos menos boxes que o necessário E houver atendentes livres
	    while (qtdAtual < boxesNecessarios && filaAtendentes.getInicio() != null) {
	        
	        Atendente atendenteLivre = filaAtendentes.pop(); // Pega atendente livre
	        atendenteLivre.setTemp(tempoAtual); // Marca hora que começou
	        
	        Box novoBox = new Box(atendenteLivre);
	        boxesAtivos.add(novoBox);
	        System.out.println("novo box aberto" + tempoAtual);
	        qtdAtual++; // Atualiza contador local
	    }

	    // Fechar boxes exedentes
	    
	    // Se tiver mais boxes do que a demanda pede
	    if (qtdAtual > boxesNecessarios) {
	        
	        // tenta remover boxes VAZIOS.
	        
	        int tentativas = boxesAtivos.getTotalDeItens();
	        
	        // verifica o da frente, se vazio remove, se ocupado joga pro final.
	        while (tentativas > 0 && boxesAtivos.getTotalDeItens() > boxesNecessarios) {
	            Box boxCandidato = boxesAtivos.pop(); // Tira do início
	            
	            if (boxCandidato.getClienteSendAtend() == null) {//se o Box está vazio, pode fechar.
	                
	                // Devolve o atendente para a fila de livres 
	                Atendente atd = boxCandidato.getAtendente();
	                if (atd != null) {
	                    filaAtendentes.add(atd); 
	                }
	                System.out.println("Box fechado" + tempoAtual);
	                // O box é descartado (não volta pra fila boxesAtivos)
	            } else {
	                // FALHA: Box está ocupado atendendo alguém.
	                boxesAtivos.add(boxCandidato); // Devolve para o final da fila
	            }
	            
	            tentativas--;
	        }
	    }
	}
	
	private void gerarAtendentes() {
		int numAtententes = random.nextInt(qtdAtdMin,qtdAtdMax);
		while (numAtententes>0) {
			Atendente novoAntendente = new Atendente();
			filaAtendentes.add(novoAntendente);
			numAtententes--;
		}
	}

	private boolean prob(int porcentagem) { // *melhorar nome da função*
		return random.nextInt(100) < porcentagem;
	}

}
