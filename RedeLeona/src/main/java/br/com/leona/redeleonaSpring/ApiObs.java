package br.com.leona.redeleonaSpring;

import java.io.StringReader;
import java.util.List;

import javax.json.Json;
import javax.json.JsonObject;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import br.com.leona.Model.Chat;
import br.com.leona.Model.LogObservacao;
import br.com.leona.Model.Observacao;
import br.com.leona.Service.ServiceLogObservacao;
import br.com.leona.Service.ServiceObservacao;
import br.com.leona.Validation.ValidationObservacao;

@RestController()
@RequestMapping("/rest/observacao2")
public class ApiObs {

    Gson gs = new Gson();
    ValidationObservacao validationObs;
    ServiceObservacao serviceObs;
    ServiceLogObservacao serviceLogObs;

    public ApiObs() {
        this.validationObs = new ValidationObservacao();
        this.serviceObs = new ServiceObservacao();
        this.serviceLogObs = new ServiceLogObservacao();
    }


    @PostMapping(path ="/cadastrarObservacao", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> cadastrarObservacao(String data) {
        Observacao obs = gs.fromJson(data, Observacao.class);
        String verifica = validationObs.validarCadastroObservacao(obs);
        if (!verifica.equals("")) {
            //return Response.status(201).entity("{\"status\":\"1\",\"resposta\":\"" + verifica + "\"}").build();
        	 return new ResponseEntity<>("{\"status\":\"0\",\"resposta\":\""+verifica+"\"}",HttpStatus.CREATED);
        } else {
            if (!serviceObs.cadastrarObservacao(obs)) {
                //return Response.status(201).entity("{\"status\":\"1\",\"resposta\":\"Erro! Tente novamente mais tarde\"}").build();
            	 return new ResponseEntity<>("{\"status\":\"1\",\"resposta\":\"Erro! Tente novamente mais tarde\"}",HttpStatus.CREATED);
            	 
            } else {
                //return Response.status(201).entity("{\"status\":\"0\",\"resposta\":\"Sucesso\"}").build();
            	return new ResponseEntity<>("{\"status\":\"0\",\"resposta\":\"Sucesso\"}",HttpStatus.CREATED);
            }
        }
    }
	/*
    @GET
    @Path("/retornarObservacoesRealizadas")*/
    
    @GetMapping(path ="/retornarObservacoesRealizadas", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> retornarObservacoesRealizadas() {
        List<Observacao> listO = serviceObs.retornarObservacoes();
        String lista = gs.toJson(listO);
        return new ResponseEntity<>(lista,HttpStatus.CREATED);
    }


    @GetMapping(path ="/retornarObservacoesAndamento", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> retornarObservacoesAndamento() {
        //return Response.status(201).entity("{\"status\":\"0\",\"resposta\":\"Sucesso\"}").build();
        
        return new ResponseEntity<>("{\"status\":\"0\",\"resposta\":\"Sucesso\"}",HttpStatus.CREATED);
    }

	/*
    @GET
    @Path("/retornarObservacoesFuturas")*/
    @GetMapping(path ="/retornarObservacoesFuturas", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> retornarObservacoesFuturas() {
        //return Response.status(201).entity("{\"status\":\"0\",\"resposta\":\"Sucesso\"}").build();
        return new ResponseEntity<>("{\"status\":\"0\",\"resposta\":\"Sucesso\"}",HttpStatus.CREATED);

    }

	/*
    @GET
    @Path("/buscarLogsSalvos/{id}")*/
    
    @GetMapping(path ="/buscarLogsSalvos/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> buscarLogsSalvos(String id) {
        List<LogObservacao> logObs = serviceLogObs.retornarLogObs(Integer.parseInt(id));
        String lista = gs.toJson(logObs);
        //return Response.status(201).entity(lista).build();
        return new ResponseEntity<>(lista,HttpStatus.CREATED);
    }

	/*
    @GET
    @Path("/buscarChatSalvo/{id}")*/
    @GetMapping(path ="/buscarChatSalvo/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> buscarChatSalvo( String id) {
        //montar chat!
        List<Chat> logObs = serviceLogObs.buscarChat(Integer.parseInt(id));
        String lista = gs.toJson(logObs);
        //return Response.status(201).entity(lista).build();
        return new ResponseEntity<>(lista,HttpStatus.CREATED);
    }


	/*
    @POST
    @Path("/enviarMensagemChat")
    @Consumes(MediaType.APPLICATION_JSON)*/
    
    @PostMapping(path ="/enviarMensagemChat", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> enviarMensagemChat(String data) {
        JsonObject jsonObject = Json.createReader(new StringReader(data)).readObject();

            if (jsonObject.getString("log").equals("False")) {
                Chat chat = gs.fromJson(data, Chat.class);
                serviceLogObs.salvarChat(chat);
            } else {
                serviceLogObs.salvarAcaoObs(data);
            }

        //return Response.status(201).entity("{\"status\":\"0\",\"resposta\":\"Sucesso\"}").build();
        return new ResponseEntity<>("{\"status\":\"0\",\"resposta\":\"Sucesso\"}",HttpStatus.CREATED);
    }
}