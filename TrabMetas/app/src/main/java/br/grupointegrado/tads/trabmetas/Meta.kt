package br.grupointegrado.tads.trabmetas

/**
 * Created by Gean on 13/04/2018.
 */
data class Meta(var titulo: String, var descricao: String, var limite: String, var situacao : String = "Inicializado") {
    override fun toString(): String {
        return "Titulo: $titulo\nDescrição: $descricao\nDataLimite: $limite\nSituação: $situacao"
    }
}