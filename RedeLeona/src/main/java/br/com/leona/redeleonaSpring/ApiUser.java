package br.com.leona.redeleonaSpring;

import java.util.List;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import br.com.leona.Model.Usuario;
import br.com.leona.Service.ServiceUser;
import br.com.leona.Validation.ValidationUsuario;

@RestController()
@RequestMapping("/rest/usuario2")
public class ApiUser {
    
    private ValidationUsuario validationUsuario;
    private ServiceUser serviceUsuario;
    Gson gs = new Gson(); 
    
    public ApiUser(){
        this.validationUsuario = new ValidationUsuario();    
        this.serviceUsuario = new ServiceUser();
    }
    
    
    @PostMapping(path ="/editarSenha", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> editarSenha(@RequestBody String data){
        Usuario login = gs.fromJson(data, Usuario.class);
        serviceUsuario.editarSenha(login);
        return new ResponseEntity<>("{\"status\":\"1\"}",HttpStatus.CREATED);        
        
    }
    
    @PostMapping(path ="/logarUsuario", produces=MediaType.APPLICATION_JSON_VALUE, consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> logarUsuario(@RequestBody String data){
    	
    	System.err.println(data);
        Usuario login = gs.fromJson(data,Usuario.class);
        String validaLogin = validationUsuario.validarLoginUsuario(login);
        
        if (!validaLogin.equals("")){
            //return Response.status(201).entity("{\"status\":\"1\",\"resposta\":\""+validaLogin+"\"}").build();
        	 return new ResponseEntity<>("{\"status\":\"1\",\"resposta\":\""+validaLogin+"\"}",HttpStatus.CREATED);    
        }else{
            Usuario usuario = serviceUsuario.logarUsuario(login);
            JSONObject objUsuario = new JSONObject(usuario);
            objUsuario.remove("senha");
            String userString = gs.toJson(objUsuario);
            if (usuario==null){
            	 return new ResponseEntity<>("{\"status\":\"1\",\"resposta\":\"E-mail/Senha não encontrados\"}"+validaLogin+"\"}",HttpStatus.CREATED);     
            }else{                
            	 return new ResponseEntity<>(userString,HttpStatus.CREATED);    
            }
        }
    }
    
    
    /*
    @POST
    @Path("/cadastrarUsuario")
    @Consumes(MediaType.APPLICATION_JSON)*/
    
    @PostMapping(path ="/cadastrarUsuario", produces=MediaType.APPLICATION_JSON_VALUE, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<String> cadastrarUsuario(@RequestBody String data){            
        Usuario usuario = gs.fromJson(data,Usuario.class);
        String respostaValidation = validationUsuario.validarCadastroUsuario(usuario);
        if (!respostaValidation.equals("")){
            //return Response.status(201).entity("{\"status\":\"1\",\"resposta\":\""+respostaValidation+"\"}").build();
        	return new ResponseEntity<>("{\"status\":\"1\",\"resposta\":\""+respostaValidation+"\"}",HttpStatus.CREATED);  
        }else{
            if (serviceUsuario.buscarUsuarioEmail(usuario.getEmail())!=null){
                 //return Response.status(201).entity("{\"status\":\"1\",\"resposta\":\"E-mail já está cadastrado\"}").build();
            	return new ResponseEntity<>("{\"status\":\"1\",\"resposta\":\"E-mail já está cadastrado\"}",HttpStatus.CREATED);  
            }else{
                serviceUsuario.cadastrarUsuario(usuario);
                //return Response.status(201).entity("{\"status\":\"0\",\"resposta\":\"Sucesso\"}").build();
                return new ResponseEntity<>("{\"status\":\"0\",\"resposta\":\"Sucesso\"}",HttpStatus.CREATED);  
            }            
        }        
    }
    
    /*
    @GET
    @Path("/buscarUsuarios")
     @Consumes(MediaType.APPLICATION_JSON)*/
    
    @GetMapping(path ="/buscarUsuarios", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> buscarUsuarios(){
        List<Usuario> listUsuarios = serviceUsuario.buscarUsuarios();          
        String listU = gs.toJson(listUsuarios);
        //return Response.status(201).entity(listU).build();
        return new ResponseEntity<>(listU,HttpStatus.CREATED);  

    }
    
    /*
    @GET
    @Path("/retornarNomeFoto")
     @Consumes(MediaType.APPLICATION_JSON)*/
    
    
    @GetMapping(path ="/retornarNomeFoto", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> retornarNomeFoto(){
        String ret = serviceUsuario.retornarNomeFoto();
        //return Response.status(201).entity("{\"status\":\"0\",\"resposta\":\""+ret+"\"}").build();
        return new ResponseEntity<>("{\"status\":\"0\",\"resposta\":\""+ret+"\"}",HttpStatus.CREATED);  
    }
    
     /*
    @GET
    @Path("/mudarStatusUsuario/{email}")
    */
    
    @GetMapping(path ="/mudarStatusUsuario/{email}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> mudarStatusUsuario(@PathVariable String email){
        if (!validationUsuario.validarEmail(email)){
            //return Response.status(201).entity("{\"status\":\"1\",\"resposta\":\"E-mail inválido\"}").build();
            return new ResponseEntity<>("{\"status\":\"1\",\"resposta\":\"E-mail inválido\"}",HttpStatus.CREATED);  
        }else{
            serviceUsuario.mudarStatusUsuario(email);
            //return Response.status(201).entity("{\"status\":\"0\",\"resposta\":\"Sucesso\"}").build();
            return new ResponseEntity<>("{\"status\":\"0\",\"resposta\":\"Sucesso\"}",HttpStatus.CREATED);  
        }        
    }
    
    /*
    @POST
    @Path("/editarMeusDados")
    @Consumes(MediaType.APPLICATION_JSON)*/
    
    
    @PostMapping
    public ResponseEntity<String> editarMeusDados(@RequestBody String data){
        Usuario usuario = gs.fromJson(data,Usuario.class);
        String valida = validationUsuario.validarEdicaoUsuario(usuario);
        if (!valida.equals("")){
            //return Response.status(201).entity("{\"status\":\"1\",\"resposta\":\""+valida+"\"}").build();
            return new ResponseEntity<>("{\"status\":\"1\",\"resposta\":\""+valida+"\"}",HttpStatus.CREATED);    
        }else{
            Usuario user = serviceUsuario.editarDadosUsuario(usuario);
            JSONObject objUsuario = new JSONObject(user);
            objUsuario.remove("senha");
            String userString = gs.toJson(objUsuario);
            //return Response.status(201).entity(userString).build();
            return new ResponseEntity<>(userString,HttpStatus.CREATED);  
        }        
    }
    
    /*
    @GET
    @Path("/recuperarSenha/{email}")*/
    @GetMapping(path ="/recuperarSenha/{email}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> recuperarSenha(@PathVariable String email){
        if (!validationUsuario.validarEmail(email)){
            //return Response.status(201).entity("{\"status\":\"1\",\"resposta\":\"Digite seu e-mail\"}").build(); 
            return new ResponseEntity<>("{\"status\":\"1\",\"resposta\":\"Digite seu e-mail\"}",HttpStatus.CREATED);
        }else{
            String resultado = serviceUsuario.recuperarSenha(email);
            //return Response.status(201).entity("{\"status\":\"0\",\"resposta\":\""+resultado+"\"}").build();
            return new ResponseEntity<>("{\"status\":\"0\",\"resposta\":\""+resultado+"\"}",HttpStatus.CREATED);
            
        }        
    }
 
}