package callCenter.entity;

import java.util.Random;

public class Box {
	private static long contadorId = 0;
	Random random = new Random();

	// atributos
	private long id;
	private Atendente atendente;
	private Cliente clienteSendAtend; // Cliente sendo atendido

	// O tempo de atendimento de cada cliente deve variar de 3 a 10 minutos.
	private int tempAtendimentoMax; // 10 min.
	private int tempAtendimentoMin; // 3 min.
	private int tempAtendimento; // tempo que o atendimento vai demorar (valor de 3 a 10)

	// construtores
	public Box() {
		this.id = contadorId++; // id auto Incremento
		tempAtendimentoMax = 15;
		tempAtendimentoMin = 7;
	}

	public Box(Atendente atendente) {
		this.id = contadorId++; // id auto Incremento
		this.atendente = atendente;
	}

	// metodos
	public void atendimentoFinalizado() { // quando um cliente acaba de ser atendido
		if (clienteSendAtend != null) {
			//System.out.println("atendimentoFinalizado");
			this.clienteSendAtend = null;
		}
	}

	public void iniciarAtendimento(Cliente clienteSendAtend) {
		tempAtendimento = random.nextInt(tempAtendimentoMin, tempAtendimentoMax + 1);
		this.clienteSendAtend = clienteSendAtend;
	}

	public void passarTempo() {
		if (clienteSendAtend != null) {
			tempAtendimento--; // Diminui 1 minuto restante
			if (tempAtendimento <= 0) {
				atendimentoFinalizado(); // Libera o box
			}
		}
	}

	// GETs SETs
	public Atendente getAtendente() {
		return atendente;
	}

	public void setAtendente(Atendente atendente) {
		this.atendente = atendente;
	}

	public Cliente getClienteSendAtend() {
		return clienteSendAtend;
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

}
