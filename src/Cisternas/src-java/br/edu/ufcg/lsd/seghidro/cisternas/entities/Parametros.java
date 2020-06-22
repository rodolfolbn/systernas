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

import java.util.ArrayList;

import br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.Coordenadas;

/**
 * Classe que representa a entidade de parâmetros.
 * 
 * @author Sávio Canuto de Oliveira Sousa.
 *
 */
public class Parametros {
	
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
	public Parametros(Double areaCapitacao, Double capacidade,
			String descricao, Double perdas, ArrayList<String> demanda) {
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

	/**
	 * @return the demanda
	 */
	public ArrayList<String> getDemanda() {
		return demanda;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((areaCapitacao == null) ? 0 : areaCapitacao.hashCode());
		result = prime * result
				+ ((capacidade == null) ? 0 : capacidade.hashCode());
		result = prime * result + ((demanda == null) ? 0 : demanda.hashCode());
		result = prime * result
				+ ((descricao == null) ? 0 : descricao.hashCode());
		result = prime * result + ((perdas == null) ? 0 : perdas.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Parametros other = (Parametros) obj;
		if (areaCapitacao == null) {
			if (other.areaCapitacao != null)
				return false;
		} else if (!areaCapitacao.equals(other.areaCapitacao))
			return false;
		if (capacidade == null) {
			if (other.capacidade != null)
				return false;
		} else if (!capacidade.equals(other.capacidade))
			return false;
		if (demanda == null) {
			if (other.demanda != null)
				return false;
		} else if (!demanda.equals(other.demanda))
			return false;
		if (descricao == null) {
			if (other.descricao != null)
				return false;
		} else if (!descricao.equals(other.descricao))
			return false;
		if (perdas == null) {
			if (other.perdas != null)
				return false;
		} else if (!perdas.equals(other.perdas))
			return false;
		return true;
	}

}
