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
 * Classe que representa a entidade Precipitação.
 * 
 * @author Sávio Canuto de Oliveira Sousa.
 *
 */
public class Precipitacao {
	
	private static int contador = 0;
	
	/**
	 * Lista de precipitação.
	 * Map <Coordenada, Map < Data, Precipitacao > >
	 */
	private Map<Coordenadas, Map<String, Double>> prec;
	
	/**
	 * Construtor vazio.
	 */
	public Precipitacao(){
		prec = new HashMap<Coordenadas, Map<String,Double>>();
		this.contador++;
	}

	/**
	 * Construtor.
	 * @param ponto
	 * @param prec
	 * @param dataInicio
	 * @param dataFinal
	 */
	public Precipitacao(Map<Coordenadas, Map<String, Double>>  prec){
		this.prec  = prec;
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
		Precipitacao.contador = contador;
	}

	/**
	 * Recupera a lista de precipitações de todas as coordenadas.
	 * @return lista de precipitações.
	 */
	public Map<Coordenadas, Map<String, Double>> getPrec() {
		return prec;
	}

	/**
	 * Modifica a lista de precipitações de todas as coordenadas.
	 * @param prec
	 */
	public void setPrec(Map<Coordenadas, Map<String, Double>> prec) {
		this.prec = prec;
	}

	/**
	 * Método que recupera todas as precipitações de uma determinada coordenada.
	 * @return lista de precipitações de uma coordenada.
	 */
	public Map<String, Double> getAllPrec(Coordenadas ponto) {
		return this.prec.get(ponto);
	}

}
