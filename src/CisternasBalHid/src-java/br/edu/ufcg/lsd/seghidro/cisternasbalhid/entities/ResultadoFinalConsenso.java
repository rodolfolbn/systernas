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

import java.util.Map;

/**
 * Classe responsável por representar a entidade de resultado final do balanço 
 * hídrico quando executado com consenso.
 * 
 * @author Sávio Canuto de Oliveira Sousa.
 *
 */
public class ResultadoFinalConsenso {
	
	private static int contador = 0;
	
	/**
	 * Lista de resultados do balanço hídrico para uma determinada coordenada.
	 */
	private Map<Coordenadas, Double> diasConsecutivos;
	
	/**
	 * Lista de resultados da frequência estimada para uma determinada coordenada.
	 */
	private Map<Coordenadas, Map<String, Double>> resultadoFrequenciaEstimada;

	
	/**
	 * Construtor vazio.
	 */
	public ResultadoFinalConsenso() {
		this.contador++;
	}
	
	
	/**
	 * Construtor
	 * @param resultadoBalHidConsenso
	 * @param resultadoFrequenciaEstimada
	 */
	public ResultadoFinalConsenso(
			Map<Coordenadas, Double> diasConsecutivos,
			Map<Coordenadas, Map<String, Double>> resultadoFrequenciaEstimada) {
		this.diasConsecutivos = diasConsecutivos;
		this.resultadoFrequenciaEstimada = resultadoFrequenciaEstimada;
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
		ResultadoFinalConsenso.contador = contador;
	}


	/**
	 * @return the diasConsecutivos
	 */
	public Map<Coordenadas, Double> getDiasConsecutivos() {
		return diasConsecutivos;
	}

	/**
	 * @param diasConsecutivos the diasConsecutivos to set
	 */
	public void setDiasConsecutivos(Map<Coordenadas, Double> diasConsecutivos) {
		this.diasConsecutivos = diasConsecutivos;
	}

	/**
	 * @return the resultadoFrequenciaEstimada
	 */
	public Map<Coordenadas, Map<String, Double>> getResultadoFrequenciaEstimada() {
		return resultadoFrequenciaEstimada;
	}

	/**
	 * @param resultadoFrequenciaEstimada the resultadoFrequenciaEstimada to set
	 */
	public void setResultadoFrequenciaEstimada(
			Map<Coordenadas, Map<String, Double>> resultadoFrequenciaEstimada) {
		this.resultadoFrequenciaEstimada = resultadoFrequenciaEstimada;
	}
	
	

}
