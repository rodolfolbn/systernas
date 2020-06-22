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

package br.edu.ufcg.lsd.seghidro.cisternas.entities;

import java.math.BigDecimal;

/**
 * Classe responsável por represertar a entidade de peso do período.
 * 
 * @author Sávio Canuto de Oliveira Sousa.
 * @since 14/01/2009.
 * 
 */
public class PesoPeriodo {

	/** Variável do mês inicial chuvoso. */
	private String mesInicialChuvoso;

	/** Variável do mês final chuvoso. */
	private String mesFinalChuvoso;

	/** Variável de período seco. */
	private Double periodoSeco;

	/** Variável de período normal. */
	private Double periodoNormal;

	/** Variável de período chuvoso. */
	private Double periodoChuvoso;
	
	private static int contator = 0;

	/**
	 * Construtor.
	 * @param dataInicio
	 * @param dataFinal
	 * @param periodoSeco
	 * @param periodoNormal
	 * @param periodoChuvoso
	 */
	public PesoPeriodo(String dataInicio, String dataFinal,
			Double periodoSeco, Double periodoNormal,
			Double periodoChuvoso) {
		this.mesInicialChuvoso = dataInicio;
		this.mesFinalChuvoso = dataFinal;
		this.periodoSeco = periodoSeco;
		this.periodoNormal = periodoNormal;
		this.periodoChuvoso = periodoChuvoso;
		this.contator++;
	}

	/** 
	 * Construtor vazio.
	 */
	public PesoPeriodo() {
		this.mesInicialChuvoso = "";
		this.mesFinalChuvoso = "";
		this.periodoChuvoso = 0.0;
		this.periodoNormal = 0.0;
		this.periodoSeco = 0.0;
		this.contator++;
	}

	/**
	 * @return the contator
	 */
	public static int getContator() {
		return contator;
	}

	/**
	 * @param contator the contator to set
	 */
	public static void setContator(int contator) {
		PesoPeriodo.contator = contator;
	}

	/**
	 * Recupera o período seco.
	 * @return período seco.
	 */
	public Double getPeriodoSeco() {
		return periodoSeco;
	}

	/**
	 * Modifica o período seco.
	 * @param periodoSeco
	 */
	public void setPeriodoSeco(Double periodoSeco) {
		this.periodoSeco = periodoSeco;
	}

	/**
	 * Recupera o período normal.
	 * @return período normal.
	 */
	public Double getPeriodoNormal() {
		return periodoNormal;
	}

	/**
	 * Modifica o período normal.
	 * @param periodoNormal
	 */
	public void setPeriodoNormal(Double periodoNormal) {
		this.periodoNormal = periodoNormal;
	}

	/**
	 * Recupera o período chuvoso.
	 * @return período chuvoso
	 */
	public Double getPeriodoChuvoso() {
		return periodoChuvoso;
	}

	/**
	 * Modifica o período chuvoso.
	 * @param periodoChuvoso
	 */
	public void setPeriodoChuvoso(Double periodoChuvoso) {
		this.periodoChuvoso = periodoChuvoso;
	}
	
	/**
	 * Recupera o mês inicial chuvoso.
	 * @return mês inicial chuvoso.
	 */
	public String getMesInicialChuvoso() {
		return mesInicialChuvoso;
	}

	/**
	 * Modifica o mês inicial chuvoso.
	 * @param mesInicialChuvoso
	 */
	public void setMesInicialChuvoso(String mesInicialChuvoso) {
		this.mesInicialChuvoso = mesInicialChuvoso;
	}

	/**
	 * Recupera o mês final chuvoso.
	 * @return Mês final chuvoso
	 */
	public String getMesFinalChuvoso() {
		return mesFinalChuvoso;
	}

	/**
	 * Modifica o mês final chuvoso.
	 * @param mesFinalChuvoso
	 */
	public void setMesFinalChuvoso(String mesFinalChuvoso) {
		this.mesFinalChuvoso = mesFinalChuvoso;
	}

	/**
	 * Método que modifica todos os atributos desse objeto.
	 * @param dataInicial
	 * @param dataFinal
	 * @param periodoSeco
	 * @param periodoNormal
	 * @param periodoChuvoso
	 */
	public void setAll(String dataInicial, String dataFinal,
			Double periodoSeco, Double periodoNormal,
			Double periodoChuvoso) {
		this.mesInicialChuvoso = dataInicial;
		this.mesFinalChuvoso = dataFinal;
		this.periodoChuvoso = periodoChuvoso;
		this.periodoNormal = periodoNormal;
		this.periodoSeco = periodoSeco;
	}

}
