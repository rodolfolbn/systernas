package acceptance;
import java.util.ArrayList;
import java.util.List;

import br.edu.ufcg.lsd.seghidro.cisternas.control.CisternasFacade;



import easyaccept.EasyAcceptFacade;

/**
 * EasyAccept test client.
 * @author Magno Queiroz
 */
public class TestClient {

	  public static void main(String[] args) throws Exception {
	        
	        List<String> files = new ArrayList<String>();
	        
	        //Add the test scripts
	        
	        files.add("testes\\EA\\US1_normal.txt");
	        
	        //files.add("testes\\EA\\US2_normal.txt");

	        //files.add("testes\\EA\\US1_variacao.txt");
	        
	        //O teste passa, mas demora um bocado.
	        //files.add("testes/EA/US2_variacao.txt");
	        
	        //Teste de execucao de varios pmhs
	        //files.add("testes\\EA\\US3_normal.txt");
	        
	        //files.add("testes\\EA\\US1_completo.txt");
	        
	        //TESTES COM RESULTADO DE CONSENSO
	        //files.add("testes\\EA\\US1_consenso.txt");
	        
	        /**
	         * OBS: Não inserir "detalhes" em execução completa ou com consenso, pois consenso vai sair
	         */
	        
	        //Instantiate your application facade
	        CisternasFacade theApplicationFacade = CisternasFacade.getInstance();
	        
	        //Instantiate the EasyAccept facade
	        EasyAcceptFacade eaFacade = new EasyAcceptFacade(theApplicationFacade, files);
	        eaFacade.executeTests();
	        
	        //Use the API
	        System.out.println(eaFacade.getCompleteResults());
	        System.out.println("The total of "+ eaFacade.getTotalNumberOfTests()+ " tests have been executed. "+ eaFacade.getTotalNumberOfNotPassedTests()+" failures occured.");
	        
	        System.out.println("Improve your development process with EasyAccept. Enjoy!");
	  }
}
