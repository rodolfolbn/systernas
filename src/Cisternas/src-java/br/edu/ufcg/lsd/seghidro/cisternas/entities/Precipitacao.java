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

import br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.Coordenadas;

/**
 * Classe responsável por representar o arquivo de PMH.
 * 
 * @author Sávio Canuto de Oliveira Sousa.
 *
 */
public class Precipitacao {
	
	private Coordenadas coordenada;
	
	private String data;
	
	private Double precipitacao;
	
	public static int contador = 0;

	/**
	 * Construtor vazio.
	 */
	public Precipitacao() {
		this.coordenada = new Coordenadas();
		this.data = "";
		this.precipitacao = 0.0;
		this.contador++;
	}
	/**
	 * @param coordenada
	 * @param data
	 * @param precipitacao
	 */
	public Precipitacao(Coordenadas coordenada, String data, Double precipitacao) {
		this.coordenada = coordenada;
		this.data = data;
		this.precipitacao = precipitacao;
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

	/**
	 * @return the data
	 */
	public String getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}

	/**
	 * @return the precipitacao
	 */
	public Double getPrecipitacao() {
		return precipitacao;
	}

	/**
	 * @param precipitacao the precipitacao to set
	 */
	public void setPrecipitacao(Double precipitacao) {
		this.precipitacao = precipitacao;
	}

}
