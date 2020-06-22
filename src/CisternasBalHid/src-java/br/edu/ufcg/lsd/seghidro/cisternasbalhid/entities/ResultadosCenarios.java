package br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities;

/**
 * Classe responsável por representar os valores de probabilidade quando se 
 * executa a aplicação com cenários.
 * 
 * @author Sávio Canuto de Oliveira Sousa.
 * @since 21/01/2009.
 *
 */
public class ResultadosCenarios {
	
	private Double demanda;
	
	private Double volume;
	
	private Double areaCapitacao;
	
	private static int contador = 0;

	/**
	 * Construtor vazio.
	 */
	public ResultadosCenarios() {
		this.contador++;
	}
	
	/**
	 * Construtor.
	 * @param demanda
	 * @param volume
	 * @param areaCapitacao
	 */
	public ResultadosCenarios(Double demanda, Double volume,
			Double areaCapitacao) {
		this.demanda = demanda;
		this.volume = volume;
		this.areaCapitacao = areaCapitacao;
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
		ResultadosCenarios.contador = contador;
	}

	/**
	 * @return the demanda
	 */
	public Double getDemanda() {
		return demanda;
	}

	/**
	 * @param demanda the demanda to set
	 */
	public void setDemanda(Double demanda) {
		this.demanda = demanda;
	}

	/**
	 * @return the volume
	 */
	public Double getVolume() {
		return volume;
	}

	/**
	 * @param volume the volume to set
	 */
	public void setVolume(Double volume) {
		this.volume = volume;
	}

	/**
	 * @return the areaCapitacao
	 */
	public Double getAreaCapitacao() {
		return areaCapitacao;
	}

	/**
	 * @param areaCapitacao the areaCapitacao to set
	 */
	public void setAreaCapitacao(Double areaCapitacao) {
		this.areaCapitacao = areaCapitacao;
	}
	

}
