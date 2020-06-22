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

package br.edu.ufcg.lsd.seghidro.cisternas.entities.input;

import java.io.FileNotFoundException;
import java.util.GregorianCalendar;

import br.edu.ufcg.lsd.seghidro.cisternas.util.Parser;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.Coordenadas;

import junit.framework.TestCase;

/**
 * Teste da classe de leitura do arquivo de PMH.
 * 
 * @author Sávio Canuto de Oliveira Sousa.
 *
 */
public class PrecipitacaoReaderTest extends TestCase {

	PrecipitacaoReader precipitacao;

	private final static String line = "            -36.3200             -7.2400   1000 1911-01-02 00:00:00            0            -";

	public PrecipitacaoReaderTest(String name) throws FileNotFoundException {
		super(name);
		precipitacao = new PrecipitacaoReader();
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	/**
	 * Testa o método leData da classe precipitação reader.
	 */
	public void testLerData() {

		GregorianCalendar dataTest = new GregorianCalendar(1911, 0, 2);
		GregorianCalendar dataArquivo = Parser.string2Calendar(precipitacao.lerData(this.line));

		assertEquals(dataTest.get(dataTest.DAY_OF_MONTH), dataArquivo
				.get(dataArquivo.DAY_OF_MONTH));
		assertEquals(dataTest.get(dataTest.MONTH), dataArquivo
				.get(dataArquivo.MONTH));
		assertEquals(dataTest.get(dataTest.YEAR), dataArquivo
				.get(dataArquivo.YEAR));
	}

	/**
	 * Testa o método lerPrecipitacao da classe precipitação reader.
	 */
	public void testLerPrecipitacao() {

		double precip = 0;
		assertEquals(precip, precipitacao.lerPrecipitacao(this.line));
	}

	/**
	 * Testa o método lerPonto da classe precipitação reader.
	 */
	public void testLerPonto() {

		Coordenadas ponto = new Coordenadas(-36.3200, -7.2400);
		assertEquals(ponto, precipitacao.lerPonto(line));
	}

}
