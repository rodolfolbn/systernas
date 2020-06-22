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
 * Entidade responsável por representar a data de início do deficit e a 
 * quantidade de dias consecutivos que ocorreram depois da data.
 * 
 * @author Sávio Canuto de Oliveira Sousa.
 *
 */
public class Deficit {
	
	private String dataInicialDeficit;
	
	private Integer diasConsecutivos;
	
	private static int contador = 0;

	/**
	 * Construtor.
	 * @param dataInicialDeficit
	 * @param diasConsecutivos
	 */
	public Deficit(String dataInicialDeficit, Integer diasConsecutivos) {
		this.dataInicialDeficit = dataInicialDeficit;
		this.diasConsecutivos = diasConsecutivos;
		this.contador++;
	}
	
	/**
	 * Construtor vazio.
	 */
	public Deficit() {
		this.dataInicialDeficit = "";
		this.diasConsecutivos = 0;
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
		Deficit.contador = contador;
	}

	/**
	 * @return the dataInicialDeficit
	 */
	public String getDataInicialDeficit() {
		return dataInicialDeficit;
	}

	/**
	 * @param dataInicialDeficit the dataInicialDeficit to set
	 */
	public void setDataInicialDeficit(String dataInicialDeficit) {
		this.dataInicialDeficit = dataInicialDeficit;
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

	/**
	 * Método que incrementa a variável de dias consecutivos.
	 * @return quantidade de dias já com incremento.
	 */
	public Integer incrementaDias() {
		Integer number = getDiasConsecutivos();
		number++;
		return number;
	}
	
	

}
