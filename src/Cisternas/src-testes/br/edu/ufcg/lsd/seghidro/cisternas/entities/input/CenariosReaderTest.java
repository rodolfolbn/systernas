package br.edu.ufcg.lsd.seghidro.cisternas.entities.input;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import junit.framework.TestCase;

import br.edu.ufcg.lsd.seghidro.cisternas.exceptions.CisternasMissingFileException;
import br.edu.ufcg.lsd.seghidro.cisternas.exceptions.CisternasReaderException;

/**
 * Classe responsável por testar a leitura do arquivo de cenários.
 * 
 * @author Sávio Canuto de Oliveira Sousa.
 * @author Magno Jefferson
 * @since 21/01/2009.
 *
 */
public class CenariosReaderTest extends TestCase {
	
	CenariosReader pesoPeriodo;

	public CenariosReaderTest(String name) throws FileNotFoundException, CisternasMissingFileException {
		super(name);
		pesoPeriodo = new CenariosReader("testes/01/cenarios.cen");
	}

	protected void setUp() throws Exception {
		super.setUp();
	}
	
//	Demanda=0,0;1,0;0,2
//	Volume=5,0;15,0;3,0
//	AreaCaptacao=100,00

	/**
	 * Método que testa a leitura dos dados de demanda.	
	 * @throws CisternasMissingFileException 
	 * @throws IOException 
	 * @throws CisternasReaderException 
	 */
	public void testReadCenario() throws CisternasMissingFileException, CisternasReaderException, IOException {
		
		// demanda
		ArrayList<Double> listaDemanda = new ArrayList<Double>();
		listaDemanda.add(0.0);
		listaDemanda.add(1.0);
		listaDemanda.add(0.2);
		
		assertEquals(listaDemanda, pesoPeriodo.getCenario().getDemanda());
		
		// volume
		ArrayList<Double> listaVolume = new ArrayList<Double>();
		listaVolume.add(5.0);
		listaVolume.add(15.0);
		listaVolume.add(3.0);
		
		assertEquals(listaVolume, pesoPeriodo.getCenario().getVolume());
		
		// area de captação
		//Double valor = 100.0;
		
		ArrayList<Double> listaAreaCaptacao = new ArrayList<Double>();
		listaAreaCaptacao.add(5.0);
		listaAreaCaptacao.add(15.0);
		listaAreaCaptacao.add(2.0);
		
		assertEquals(listaAreaCaptacao, pesoPeriodo.getCenario().getAreaCaptacao());
	}
	
}
