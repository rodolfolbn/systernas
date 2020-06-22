package br.edu.ufcg.lsd.seghidro.cisternas.entities.input;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import br.edu.ufcg.lsd.seghidro.cisternas.entities.Parametros;
import br.edu.ufcg.lsd.seghidro.cisternas.exceptions.CisternasMissingFileException;
import br.edu.ufcg.lsd.seghidro.cisternas.exceptions.ParametrosReaderException;
import br.edu.ufcg.lsd.seghidro.cisternasbalhid.entities.Coordenadas;
import junit.framework.TestCase;

/**
 * Classe responsável por realizar os testes de leitura do arquivo de parâmetros.
 * 
 * @author Sávio Canuto de Oliveira Sousa.
 * @author Magno Jefferson
 * @since 21/01/2009.
 *
 */
public class ParametrosReaderTest extends TestCase {

	ParametrosReader parametrosReader;

	public ParametrosReaderTest(String name) throws FileNotFoundException, CisternasMissingFileException {
		super(name);
		parametrosReader = new ParametrosReader("testes/01/parametros.par");
	}

	protected void setUp() throws Exception {
		super.setUp();
	}
	
//	!LATITUDE;LONGITUDE;DESCRIÇÃO;ACAP;CAPACIDADE;DEM;PERDAS
//	-36.3200;-7.2400;Jatobá-Patos;40,6;16;15;0,75
//	-6.6700;-36.0000;Barragem-da-Farinha;4,15;18;25;0,55
	
	/**
	 * Método que testa a leitura do arquivo de parametros.
	 * @throws Exception 
	 */
	public void testGetParametros() throws Exception {
		
		Coordenadas coordenadas = new Coordenadas();
		coordenadas.setCoordX(new Double("-7.24"));
		coordenadas.setCoordY(new Double("-36.32"));
		
		Parametros parametros = new Parametros();
		parametros.setAreaCapitacao(new Double("40.6"));
		parametros.setCapacidade(new Double("16.0"));
//parametros.setDemanda(new Double("0.1"));
		parametros.setDescricao("Cisterna");
		parametros.setPerdas(new Double("0.75"));
		
		Map<Coordenadas, Parametros> map = parametrosReader.getParametros();
		
		assertEquals(parametros.getAreaCapitacao(), map.get(coordenadas).getAreaCapitacao());
		assertEquals(parametros.getCapacidade(), map.get(coordenadas).getCapacidade());
		assertEquals(parametros.getDemanda(), map.get(coordenadas).getDemanda());
		assertEquals(parametros.getDescricao(), map.get(coordenadas).getDescricao());
		assertEquals(parametros.getPerdas(), map.get(coordenadas).getPerdas());
	}
	
}
