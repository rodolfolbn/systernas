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
 * Classe que representa a lista de parâmetros que são lidos do arquivo de 
 * parâmetros.
 * 
 * @author Sávio Canuto de Oliveira Sousa.
 *
 */
public class ColecaoParametros {
	
	private ArrayList<Parametros> listaParametros;
	
	private static int contador = 0;
	
	/**
	 * Construtor vazio.
	 */
	public ColecaoParametros() {
		this.contador++;
	}

	/**
	 * @param listaParametros
	 */
	public ColecaoParametros(ArrayList<Parametros> listaParametros) {
		this.listaParametros = listaParametros;
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
		ColecaoParametros.contador = contador;
	}

	/**
	 * @return the listaParametros
	 */
	public ArrayList<Parametros> getListaParametros() {
		return listaParametros;
	}

	/**
	 * @param listaParametros the listaParametros to set
	 */
	public void setListaParametros(ArrayList<Parametros> listaParametros) {
		this.listaParametros = listaParametros;
	}

}
