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
 * Classe responsável por representar os resultados quando executa a aplicação
 * de forma completa: consenso e cenários.
 * 
 * @author Sávio Canuto de Oliveira Sousa.
 * @since 03/02/2009.
 *
 */
public class ResultadoFinalCompleto {
	
	/**
	 * Lista de resultados do balanço hídrico caso seja executado por consenso.
	 */
	private Map<Coordenadas, Map<String, ResultadosBalHid>> mapResultadosBalHidConsenso;
	
	/**
	 * Lista de resultados da ponderação dos dias consecutivos obtidos pela 
	 * execução por consenso.
	 */
	private Map<Coordenadas, Double> mapPonderacaoDiasConsecutivosConsenso;
	
	/**
	 * Lista com os resultados finais da frequência estimada.
	 */
	private Map<Coordenadas, Map<String, Double>> mapResultadoFrequencia;
	
	private static int contador = 0;

	/**
	 * Construtor vazio.
	 */
	public ResultadoFinalCompleto() {
		this.mapResultadoFrequencia = new HashMap<Coordenadas, Map<String,Double>>();
		this.mapPonderacaoDiasConsecutivosConsenso = new HashMap<Coordenadas, Double>();
		this.mapResultadosBalHidConsenso = new HashMap<Coordenadas, Map<String,ResultadosBalHid>>();
		this.contador++;
	}

	/**
	 * Construtor.
	 * @param mapResultadosBalHidConsenso
	 * @param mapPonderacaoDiasConsecutivosConsenso
	 * @param mapResultadoFrequencia
	 */
	public ResultadoFinalCompleto(
			Map<Coordenadas, Map<String, ResultadosBalHid>> mapResultadosBalHidConsenso,
			Map<Coordenadas, Double> mapPonderacaoDiasConsecutivosConsenso,
			Map<Coordenadas, Map<String, Double>> mapResultadoFrequencia) {
		this.mapResultadosBalHidConsenso = mapResultadosBalHidConsenso;
		this.mapPonderacaoDiasConsecutivosConsenso = mapPonderacaoDiasConsecutivosConsenso;
		this.mapResultadoFrequencia = mapResultadoFrequencia;
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
		ResultadoFinalCompleto.contador = contador;
	}

	/**
	 * @return the mapResultadosBalHidConsenso
	 */
	public Map<Coordenadas, Map<String, ResultadosBalHid>> getMapResultadosBalHidConsenso() {
		return mapResultadosBalHidConsenso;
	}

	/**
	 * @param mapResultadosBalHidConsenso the mapResultadosBalHidConsenso to set
	 */
	public void setMapResultadosBalHidConsenso(
			Map<Coordenadas, Map<String, ResultadosBalHid>> mapResultadosBalHidConsenso) {
		this.mapResultadosBalHidConsenso = mapResultadosBalHidConsenso;
	}

	/**
	 * @return the mapPonderacaoDiasConsecutivosConsenso
	 */
	public Map<Coordenadas, Double> getMapPonderacaoDiasConsecutivosConsenso() {
		return mapPonderacaoDiasConsecutivosConsenso;
	}

	/**
	 * @param mapPonderacaoDiasConsecutivosConsenso the mapPonderacaoDiasConsecutivosConsenso to set
	 */
	public void setMapPonderacaoDiasConsecutivosConsenso(
			Map<Coordenadas, Double> mapPonderacaoDiasConsecutivosConsenso) {
		this.mapPonderacaoDiasConsecutivosConsenso = mapPonderacaoDiasConsecutivosConsenso;
	}

	/**
	 * @return the mapResultadoFrequencia
	 */
	public Map<Coordenadas, Map<String, Double>> getMapResultadoFrequencia() {
		return mapResultadoFrequencia;
	}

	/**
	 * @param mapResultadoFrequencia the mapResultadoFrequencia to set
	 */
	public void setMapResultadoFrequencia(
			Map<Coordenadas, Map<String, Double>> mapResultadoFrequencia) {
		this.mapResultadoFrequencia = mapResultadoFrequencia;
	}
	
}