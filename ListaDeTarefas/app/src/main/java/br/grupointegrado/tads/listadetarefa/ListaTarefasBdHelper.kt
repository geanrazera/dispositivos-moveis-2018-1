package br.grupointegrado.tads.listadetarefa

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

/**
 * Created by Gean on 25/06/2018.
 */
class ListaTarefasBdHelper : SQLiteOpenHelper{

    companion object {
        val BANCO_DE_DADOS = "lista.db"
        val VERSAO = 1
    }

    constructor(context : Context) : super(context, BANCO_DE_DADOS, null, VERSAO){

    }

    override fun onCreate(db: SQLiteDatabase) {
        val Tarefa = TarefaContrato.tarefa;
        val CREATE_TABLE_LISTA = """
            CREATE TABLE ${Tarefa.TABELA} (
                ${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                ${Tarefa.COLUNA_TAREFA_ID} INTEGER NOT NULL,
                ${Tarefa.COLUNA_TITULO} TEXT NOT NULL,
                ${Tarefa.COLUNA_DESCRICAO} TEXT NOT NULL,
                ${Tarefa.COLUNA_DATA_HORA} TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            );
        """
        println(CREATE_TABLE_LISTA)
        db.execSQL(CREATE_TABLE_LISTA)
    }

    override fun onUpgrade(db: SQLiteDatabase, versaoAnterior: Int, novaVersao: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${TarefaContrato.tarefa.TABELA}")
        onCreate(db)
    }

}

