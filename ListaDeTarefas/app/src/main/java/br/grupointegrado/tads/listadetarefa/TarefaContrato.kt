package br.grupointegrado.tads.listadetarefa

import android.provider.BaseColumns

/**
 * Created by Gean on 25/06/2018.
 */
class TarefaContrato {


    internal object tarefa : BaseColumns{
        val TABELA = "tarefa"
        val COLUNA_TAREFA_ID = "tarefa_id"
        val COLUNA_TITULO = "titulo"
        val COLUNA_DESCRICAO = "descricao"
        val COLUNA_DATA_HORA = "data_hora"

    }

}