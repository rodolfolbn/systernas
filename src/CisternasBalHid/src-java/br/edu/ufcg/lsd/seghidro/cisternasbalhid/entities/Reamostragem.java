/*
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities;

/**
 * Classe que representa a entidade de reamostragem da previsão de
 * consenso.
 * 
 * @author Sávio Canuto de Oliveira Sousa.
 * @since 17/01/2009.
 *
 */
public class Reamostragem {
	
	private String ano;
	
	private Double somatorioPrecipitacao;
	
	private String classificacao;
	
	private Double deficit;
	
	private Double garantia;
	
	private Integer diasConsecutivos;
	
	private static int contador = 0;

	/**
	 * Construtor vazio.
	 */
	public Reamostragem() {
		this.contador++;
	}
	/**
	 * Construtor.
	 * @param ano
	 * @param somatorioPrecipitacao
	 * @param classificacao
	 * @param deficit
	 * @param diasConsecutivos
	 */
	public Reamostragem(String ano, Double somatorioPrecipitacao,
			String classificacao, Double deficit, Double garantia, Integer diasConsecutivos) {
		this.ano = ano;
		this.somatorioPrecipitacao = somatorioPrecipitacao;
		this.classificacao = classificacao;
		this.deficit = deficit;
		this.garantia = garantia;
		this.diasConsecutivos = diasConsecutivos;
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
		Reamostragem.contador = contador;
	}
	/**
	 * @return the garantia
	 */
	public Double getGarantia() {
		return garantia;
	}
	/**
	 * @param garantia the garantia to set
	 */
	public void setGarantia(Double garantia) {
		this.garantia = garantia;
	}
	/**
	 * @return the ano
	 */
	public String getAno() {
		return ano;
	}

	/**
	 * @param ano the ano to set
	 */
	public void setAno(String ano) {
		this.ano = ano;
	}

	/**
	 * @return the somatorioPrecipitacao
	 */
	public Double getSomatorioPrecipitacao() {
		return somatorioPrecipitacao;
	}

	/**
	 * @param somatorioPrecipitacao the somatorioPrecipitacao to set
	 */
	public void setSomatorioPrecipitacao(Double somatorioPrecipitacao) {
		this.somatorioPrecipitacao = somatorioPrecipitacao;
	}

	/**
	 * @return the classificacao
	 */
	public String getClassificacao() {
		return classificacao;
	}

	/**
	 * @param classificacao the classificacao to set
	 */
	public void setClassificacao(String classificacao) {
		this.classificacao = classificacao;
	}

	/**
	 * @return the deficit
	 */
	public Double getDeficit() {
		return deficit;
	}

	/**
	 * @param deficit the deficit to set
	 */
	public void setDeficit(Double deficit) {
		this.deficit = deficit;
	}

	/**
	 * @return the diasConsecutivos
	 */
	public Integer getDiasConsecutivos() {
		return diasConsecutivos;
	}

	/**
	 * @param diasConsecutivos the diasConsecutivos to set
	 */
	public void setDiasConsecutivos(Integer diasConsecutivos) {
		this.diasConsecutivos = diasConsecutivos;
	}
	
}
