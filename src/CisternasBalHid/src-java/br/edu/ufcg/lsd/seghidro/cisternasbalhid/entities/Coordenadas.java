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
 * Classe responsável por representar as coordenadas do arquivo de PMH.
 * 
 * @author Sávio Canuto de Oliveira Sousa.
 * 
 */
public class Coordenadas implements Comparable {

	/** Variável de coordenada X. */
	private double coordX;

	/** Variável de coordenada Y. */
	private double coordY;
	
	private static int contador = 0;

	/**
	 * Construtor.
	 * 
	 * @param coordX
	 * @param coordY
	 */
	public Coordenadas(double coordX, double coordY) {
		this.coordX = coordX;
		this.coordY = coordY;
		this.contador++;
	}

	/**
	 * Construtor vazio.
	 */
	public Coordenadas() {
		this.coordX = 0;
		this.coordY = 0;
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
		Coordenadas.contador = contador;
	}

	/**
	 * Recupera a coordenada X.
	 * 
	 * @return coordenada X.
	 */
	public double getCoordX() {
		return coordX;
	}

	/**
	 * Modifica a coordenada X.
	 * 
	 * @param coordX
	 */
	public void setCoordX(double coordX) {
		this.coordX = coordX;
	}

	/**
	 * Recupera a coodenada Y.
	 * 
	 * @return coordenada Y.
	 */
	public double getCoordY() {
		return coordY;
	}

	/**
	 * Modifica a coordenada Y
	 * 
	 * @param coordY
	 */
	public void setCoordY(double coordY) {
		this.coordY = coordY;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(coordX);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(coordY);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Coordenadas other = (Coordenadas) obj;
		if (Double.doubleToLongBits(coordX) != Double
				.doubleToLongBits(other.coordX))
			return false;
		if (Double.doubleToLongBits(coordY) != Double
				.doubleToLongBits(other.coordY))
			return false;
		return true;
	}

	public String toString() {
		return "coordX " + getCoordX() + " coordY " + getCoordY();
	}

	public int compareTo(Object o) {
		Coordenadas c = ((Coordenadas) o);
		if (c.getCoordX() > this.coordX) {
			return 1;
		} else if (c.getCoordX() < this.coordX) {
			return -1;
		}

		return 0;
	}

}
