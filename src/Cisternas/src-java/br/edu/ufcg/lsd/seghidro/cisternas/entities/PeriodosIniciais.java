package br.edu.ufcg.lsd.seghidro.cisternas.entities;

import br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.Coordenadas;

/**
 * Classe que representa os períodos iniciais para cada coordenada.
 * 
 * @author Sávio Canuto de Oliveira Sousa.
 * @since 12/01/2009.
 *
 */
public class PeriodosIniciais {
	
	private String dataInicial;
	
	private String dataFinal;
	
	private Coordenadas coordenada;
	
	private static int contador = 0;

	/**
	 * Construtor vazio.
	 */
	public PeriodosIniciais() {
		this.contador++;
	}
	/**
	 * Construtor.
	 * @param dataInicial
	 * @param dataFinal
	 * @param coordenada
	 */
	public PeriodosIniciais(Coordenadas coordenada, String dataInicial, 
			String dataFinal) {
		this.dataInicial = dataInicial;
		this.dataFinal = dataFinal;
		this.coordenada = coordenada;
		this.contador++;
	}

	/**
	 * @return the contador
	 */
	public static int getContador() {
		return contador;
	}
	/**
	 * @param contador the contador to set
	 */
	public static void setContador(int contador) {
		PeriodosIniciais.contador = contador;
	}
	/**
	 * @return the dataInicial
	 */
	public String getDataInicial() {
		return dataInicial;
	}

	/**
	 * @param dataInicial the dataInicial to set
	 */
	public void setDataInicial(String dataInicial) {
		this.dataInicial = dataInicial;
	}

	/**
	 * @return the dataFinal
	 */
	public String getDataFinal() {
		return dataFinal;
	}

	/**
	 * @param dataFinal the dataFinal to set
	 */
	public void setDataFinal(String dataFinal) {
		this.dataFinal = dataFinal;
	}

	/**
	 * @return the coordenada
	 */
	public Coordenadas getCoordenada() {
		return coordenada;
	}

	/**
	 * @param coordenada the coordenada to set
	 */
	public void setCoordenada(Coordenadas coordenada) {
		this.coordenada = coordenada;
	}

}
