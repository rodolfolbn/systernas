package br.edu.ufcg.lsd.seghidro.cisternas.entities.input;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Executa todos os testes de Cisternas.
 * 
 * @author Sávio Canuto de Oliveira Sousa.
 *
 */
public class AllTests {
	
	public static Test suite() {
		
		TestSuite suite = new TestSuite("Testes da Aplicação Cisternas");
		suite.addTestSuite(PrecipitacaoReaderTest.class);
		suite.addTestSuite(PesoPeriodoReaderTest.class);
		suite.addTestSuite(CenariosReaderTest.class);
		suite.addTestSuite(ParametrosReaderTest.class);

		return suite;
	}

}
