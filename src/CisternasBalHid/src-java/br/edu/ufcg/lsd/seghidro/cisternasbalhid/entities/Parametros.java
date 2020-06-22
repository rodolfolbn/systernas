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

import java.util.ArrayList;

/**
 * Classe que representa a entidade de parâmetros.
 * 
 * @author Sávio Canuto de Oliveira Sousa.
 * @since 07/01/2009
 */
public class Parametros {
	
	private Coordenadas coordenadas;
	
	private String descricao;
	
	private Double areaCapitacao;
	
	private Double capacidade;
	
	private Double perdas;
	
	private ArrayList<String> demanda;
	
	private static int contador = 0;

	/**
	 * @param areaCapitacao
	 * @param capacidade
	 * @param descricao
	 * @param perdas
	 */
	public Parametros(Coordenadas coordenadas, Double areaCapitacao, Double capacidade,
			String descricao, Double perdas, ArrayList<String> demanda) {
		this.coordenadas = coordenadas;
		this.areaCapitacao = areaCapitacao;
		this.capacidade = capacidade;
		this.descricao = descricao;
		this.perdas = perdas;
		this.demanda = demanda;
		this.contador++;
	}
	
	/**
	 * Construtor vazio.
	 */
	public Parametros() {
		this.coordenadas = new Coordenadas();
		this.descricao = "";
		this.areaCapitacao = 0.0;
		this.capacidade = 0.0;
		this.perdas = 0.0;
		this.demanda = null;
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
		Parametros.contador = contador;
	}

	public Coordenadas getCoordenadas() {
		return coordenadas;
	}

	public void setCoordenadas(Coordenadas coordenadas) {
		this.coordenadas = coordenadas;
	}

	/**
	 * @return the demanda
	 */
	public ArrayList<String> getDemanda() {
		return demanda;
	}

	/**
	 * @param demanda the demanda to set
	 */
	public void setDemanda(Double valorDemanda) {
		this.demanda.add(0, valorDemanda+"");
	}

	/**
	 * @param demanda the demanda to set
	 */
	public void setDemanda(ArrayList<String> demanda) {
		this.demanda = demanda;
	}
	/**
	 * @return the descricao
	 */
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @param descricao the descricao to set
	 */
	public void setDescricao(String descricao) {
		this.descricao = descricao;
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

	/**
	 * @return the capacidade
	 */
	public Double getCapacidade() {
		return capacidade;
	}

	/**
	 * @param capacidade the capacidade to set
	 */
	public void setCapacidade(Double capacidade) {
		this.capacidade = capacidade;
	}

	/**
	 * @return the perdas
	 */
	public Double getPerdas() {
		return perdas;
	}

	/**
	 * @param perdas the perdas to set
	 */
	public void setPerdas(Double perdas) {
		this.perdas = perdas;
	}

}
