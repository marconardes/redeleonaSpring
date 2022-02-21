package br.com.leona.Dao;

import br.com.leona.Model.Usuario;
import java.util.List;
import net.sf.esfinge.querybuilder.Repository;

public interface DaoUsuario extends Repository<Usuario> {
    
    List<Usuario> getUsuarioByEmailAndSenha(String email, String senha);
    
    List<Usuario> getUsuarioByEmail(String email);
    
}
