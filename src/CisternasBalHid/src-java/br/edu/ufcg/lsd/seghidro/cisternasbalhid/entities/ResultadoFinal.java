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

import java.util.HashMap;
import java.util.Map;

/**
 * Classe responsável por representar os resultados gerados pelos métodos de cálculos.
 * 
 * @author Sávio Canuto de Oliveira Sousa.
 * @since 19/01/2009.
 *
 */
public class ResultadoFinal {
	
	/**
	 * Lista de resultados do balanço hídrico para uma determinada coordenada.
	 */
	private Map<Coordenadas, ResultadosBalHid> resultadoBalHid;
	
	/**
	 * Lista de resultados da frequência estimada para uma determinada coordenada.
	 */
	private Map<Coordenadas, Map<String, Double>> resultadoFrequenciaEstimada;
	
	private static int contador = 0;

	/**
	 * Construtor.
	 * @param resultadoBalHid
	 * @param resultadoFrequenciaEstimada
	 */
	public ResultadoFinal(
			Map<Coordenadas, ResultadosBalHid> resultadoBalHid,
			Map<Coordenadas, Map<String, Double>> resultadoFrequenciaEstimada) {
		
		this.resultadoBalHid = resultadoBalHid;
		this.resultadoFrequenciaEstimada = resultadoFrequenciaEstimada;
		this.contador++;
	}
	
	/**
	 * Construtor vazio
	 */
	public ResultadoFinal() {
		this.resultadoBalHid = new HashMap<Coordenadas, ResultadosBalHid>();
		this.resultadoFrequenciaEstimada = new HashMap<Coordenadas, Map<String,Double>>();
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
		ResultadoFinal.contador = contador;
	}

	/**
	 * @return the resultadoBalHid
	 */
	public Map<Coordenadas, ResultadosBalHid> getResultadoBalHid() {
		return resultadoBalHid;
	}

	/**
	 * @param resultadoBalHid the resultadoBalHid to set
	 */
	public void setResultadoBalHid(
			Map<Coordenadas, ResultadosBalHid> resultadoBalHid) {
		this.resultadoBalHid = resultadoBalHid;
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
