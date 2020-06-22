package br.edu.ufcg.lsd.seghidro.cisternas.entities.input;

import java.io.FileNotFoundException;
import java.io.IOException;

import junit.framework.TestCase;
import br.edu.ufcg.lsd.seghidro.cisternas.exceptions.CisternasMissingFileException;
import br.edu.ufcg.lsd.seghidro.cisternas.exceptions.CisternasReaderException;

/**
 * Classe responsável por testar a leitura do arquivo de peso período.
 * 
 * @author Sávio Canuto de Oliveira Sousa.
 * @author Magno Jefferson
 * @since 21/01/2009.
 *
 */
public class PesoPeriodoReaderTest extends TestCase {
	
	PesoPeriodoReader pesoPeriodo;

	public PesoPeriodoReaderTest(String name) throws FileNotFoundException, CisternasMissingFileException {
		super(name);
		pesoPeriodo = new PesoPeriodoReader("testes/01/pesoPeriodo.pes");
	}

	protected void setUp() throws Exception {
		super.setUp();
	}
	
	/**
	 * Testa a leitura do período seco.
	 * @throws IOException
	 * @throws CisternasReaderException 
	 */
	public void testReadPeriodoSeco() throws IOException, CisternasReaderException {
		
		Double valor = 0.3;
		assertEquals(valor, pesoPeriodo.readPeriodoSeco());
	}
	
	/**
	 * Testa a leitura do período normal.
	 * @throws IOException
	 * @throws CisternasReaderException 
	 */
	public void testReadPeriodoNormal() throws IOException, CisternasReaderException {
		
		Double valor = 0.4;
		assertEquals(valor, pesoPeriodo.readPeriodoNormal());
	}
	
	/**
	 * Testa a leitura do período chuvoso.
	 * @throws IOException
	 * @throws CisternasReaderException 
	 */
	public void testReadPeriodoChuvoso() throws IOException, CisternasReaderException {
		
		Double valor = 0.3;
		assertEquals(valor, pesoPeriodo.readPeriodoChuvoso());
	}
	
	/**
	 * Testa a leitura do mês inicial chuvoso.
	 * @throws IOException
	 */
	public void testReadMesInicialChuvoso() throws IOException {
		String valor = "1";
		assertEquals(valor, pesoPeriodo.readMesInicialChuvoso());
	}
	
	/**
	 * Testa a leitura do mês final chuvoso.
	 * @throws IOException
	 */
	public void testReadMesFinalChuvoso() throws IOException {
		String valor = "12";
		assertEquals(valor, pesoPeriodo.readMesFinalChuvoso());
	}

}
