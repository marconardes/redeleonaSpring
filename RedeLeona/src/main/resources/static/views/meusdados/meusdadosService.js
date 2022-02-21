app.service('meusdadosService', function ($http) {
        return{
            editarMeusDados: function(objeto){
                return $http.post('/rest/usuario/editarMeusDados',objeto);
            }
        }
    })