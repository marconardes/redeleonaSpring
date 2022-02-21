app.service('usuarioService', function ($http) {
        return{
            buscarUsuarios: function(){
                return $http.get('/rest/usuario2/buscarUsuarios');
            },
            mudarStatusUsuario: function(email){
                return $http.get('/rest/usuario2/mudarStatusUsuario/'+email);
            },
            editarSenha: function(objeto){
                return $http.post('/rest/usuario2/editarSenha', objeto);
            },
            buscarNomeFoto: function(){
                return $http.get('/rest/usuario2/retornarNomeFoto');
            }
        }
    })