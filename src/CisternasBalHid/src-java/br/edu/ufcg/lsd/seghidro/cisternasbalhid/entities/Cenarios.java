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
 * Classe responsável por representar a entidade de cenários estatísticos.
 * 
 * @author Sávio Canuto de Oliveira Sousa.
 * @since 20/01/2009.
 *
 */
public class Cenarios {
	
	/** Lista de demandas. */
	private ArrayList<Double> demanda;
	
	/** Lista de volumes iniciais. */
	private ArrayList<Double> volume;

	/** Lista de vazões. */
	private ArrayList<Double> areaCaptacao;
	
	private static int contador = 0;

	/**
	 * Construtor vazio.
	 */
	public Cenarios() {
		this.contador++;
	}
	
	/**
	 * Construtor.
	 * @param volume
	 * @param demanda
	 * @param areaCaptacao
	 */
	public Cenarios(ArrayList<Double> demanda, ArrayList<Double> volume, 
			ArrayList<Double> areaCaptacao) {
		
		this.volume = volume;
		this.demanda = demanda;
		this.areaCaptacao = areaCaptacao;
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
		Cenarios.contador = contador;
	}

	/**
	 * @return the volume
	 */
	public ArrayList<Double> getVolume() {
		return volume;
	}

	/**
	 * @param volume the volume to set
	 */
	public void setVolume(ArrayList<Double> volume) {
		this.volume = volume;
	}

	/**
	 * @return the demanda
	 */
	public ArrayList<Double> getDemanda() {
		return demanda;
	}

	/**
	 * @param demanda the demanda to set
	 */
	public void setDemanda(ArrayList<Double> demanda) {
		this.demanda = demanda;
	}

	/**
	 * @return the areaCaptacao
	 */
	public ArrayList<Double> getAreaCaptacao() {
		return areaCaptacao;
	}

	/**
	 * @param areaCaptacao the areaCaptacao to set
	 */
	public void setAreaCaptacao(ArrayList<Double> areaCaptacao) {
		this.areaCaptacao = areaCaptacao;
	}
	
	
}
