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
 * Classe responsável por representar os resultados do balanço hídrico.
 * 
 * @author Sávio Canuto de Oliveira Sousa.
 * @since 12/01/2009.
 *
 */
public class ResultadosBalHid {

	private String descricao;
	
	private String periodoInicial;
	
	private String periodoFinal;
	
	private Double garantia;
	
	private Double deficit;
	
	private Deficit def;
	
	private static int contador = 0;
	
	private ArrayList<String> detalhesLista;
	
	/**
	 * Construtor vazio.
	 */
	public ResultadosBalHid() {
		this.descricao = "";
		this.periodoFinal = "";
		this.periodoInicial = "";
		this.garantia = 0.0;
		this.deficit = 0.0;
		this.def = new Deficit();
		this.contador++;
		detalhesLista = null;
	}
	
	/**
	 * @param periodoInicial
	 * @param periodoFinal
	 * @param garantia
	 * @param deficit
	 */
	public ResultadosBalHid(String descricao, String periodoInicial, String periodoFinal,
			Double garantia, Double deficit, Deficit def, ArrayList<String> detalhesLista) {
		
		this.descricao = descricao;
		this.periodoInicial = periodoInicial;
		this.periodoFinal = periodoFinal;
		this.garantia = garantia;
		this.deficit = deficit;
		this.def = def;
		this.contador++;
		this.detalhesLista = detalhesLista;
	}
	
	public ArrayList<String> getDetalhesLista() {
		return detalhesLista;
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
		ResultadosBalHid.contador = contador;
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
	 * @return the def
	 */
	public Deficit getDef() {
		return def;
	}

	/**
	 * @param def the def to set
	 */
	public void setDef(Deficit def) {
		this.def = def;
	}

	/**
	 * @return the periodoInicial
	 */
	public String getPeriodoInicial() {
		return periodoInicial;
	}

	/**
	 * @param periodoInicial the periodoInicial to set
	 */
	public void setPeriodoInicial(String periodoInicial) {
		this.periodoInicial = periodoInicial;
	}

	/**
	 * @return the periodoFinal
	 */
	public String getPeriodoFinal() {
		return periodoFinal;
	}

	/**
	 * @param periodoFinal the periodoFinal to set
	 */
	public void setPeriodoFinal(String periodoFinal) {
		this.periodoFinal = periodoFinal;
	}

	/**
	 * @return the garantia
	 */
	public Double getGarantia() {
		return garantia;
	}

	/**
	 * @param garantia the garantia to set
	 */
	public void setGarantia(Double garantia) {
		this.garantia = garantia;
	}

	/**
	 * @return the deficit
	 */
	public Double getDeficit() {
		return deficit;
	}

	/**
	 * @param deficit the deficit to set
	 */
	public void setDeficit(Double deficit) {
		this.deficit = deficit;
	}

}
