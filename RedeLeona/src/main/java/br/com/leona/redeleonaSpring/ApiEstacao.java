package br.com.leona.redeleonaSpring;

import java.util.List;

import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import br.com.leona.Model.Estacao;
import br.com.leona.Service.ServiceEstacao;
import br.com.leona.Validation.ValidationEstacao;

@RestController()
@RequestMapping("/rest/estacao")
public class ApiEstacao {
	
    Gson gs = new Gson();
    private ValidationEstacao validationEstacao;
    private ServiceEstacao serviceEstacao;
    
    public ApiEstacao() {
        this.validationEstacao = new ValidationEstacao();
        this.serviceEstacao = new ServiceEstacao();
    }
    
    
	@PostMapping(path ="/cadastrarEstacao", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> cadastrarEstacao(@RequestParam("data")  String data ) {
		Estacao estacao = gs.fromJson(data, Estacao.class);
        String valida = validationEstacao.validarCadastroEstacao(estacao);
        
        if (!valida.equals("")) {
        	
        	return new ResponseEntity<>("{\"status\":\"1\",\"resposta\":\"" + valida + "\"}",HttpStatus.CREATED);
        }
        
        else {
            serviceEstacao.cadastrarEstacao(estacao);
            return new ResponseEntity<>("{\"status\":\"0\",\"resposta\":\"" + valida + "\"}",HttpStatus.CREATED);

        }
        
	}
	
	@GetMapping(path = "/retornarEstacoes",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> retornarEstacoes() {
		
        List<Estacao> listEstacoes = serviceEstacao.retornarEstacoes();
        String lista = gs.toJson(listEstacoes);
                
        return new ResponseEntity<>(lista,HttpStatus.CREATED);
    }
	

	@GetMapping(path = "/statusEstacao/{nome}",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> statusEstacao(@PathVariable String nome) {
        String resposta = serviceEstacao.retornarStatusEstacao(nome);        
        
        return new ResponseEntity<>("{\"status\":\"0\",\"resposta\":\""+resposta+"\"}",HttpStatus.CREATED);
    }
	
	
	@PostMapping(path ="/excluirEstacao", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> excluirEstacao(@RequestParam("data") String data) {
        Estacao estacao = gs.fromJson(data, Estacao.class);
        serviceEstacao.excluirEstacao(estacao.getId());
    
        
        return new ResponseEntity<>("{\"status\":\"0\",\"resposta\":\"Sucesso\"}",HttpStatus.CREATED);
    }

	
	
	@PostMapping(path ="/editarEstacao", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> editarEstacao(@RequestParam("data") String data) {
        Estacao estacao = gs.fromJson(data, Estacao.class);
        String valida = validationEstacao.validarEdicaoEstacao(estacao.getNomeContato());
        if (!valida.equals("")) {
            return new ResponseEntity<>("{\"status\":\"0\",\"resposta\":\"Sucesso\"}",HttpStatus.CREATED);
        } else {
            serviceEstacao.editarEstacao(estacao);
            return new ResponseEntity<>("{\"status\":\"0\",\"resposta\":\"Sucesso\"}",HttpStatus.CREATED);
        }

	}
}
