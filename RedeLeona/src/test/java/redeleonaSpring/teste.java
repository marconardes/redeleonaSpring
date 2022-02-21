package redeleonaSpring;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import br.com.leona.Service.ServiceEstacao;
import br.com.leona.redeleonaSpring.ApiEstacao;

class teste {

	@Test
	void test() {
		
		  ServiceEstacao serviceEstacao = new ServiceEstacao();
		  
		 System.out.println(serviceEstacao.retornarEstacoes());
		  
		  
		  
	}

}
