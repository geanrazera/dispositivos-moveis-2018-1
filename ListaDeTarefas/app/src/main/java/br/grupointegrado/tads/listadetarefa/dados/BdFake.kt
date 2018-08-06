package br.grupointegrado.tads.listadetarefa.dados

import android.content.ContentValues
import java.util.*

object BdFake {

    val COLUNA_ID = "_id"
    val COLUNA_TITULO = "titulo"
    val COLUNA_DESCRICAO = "descricao"
    val COLUNA_DATA_HORA = "data_hora"

    private var ultimoId = 0L

//    private val database: MutableList<ContentValues> = mutableListOf()
    private val database: MutableList<ContentValues> = mutableListOf()

    fun insert(values: ContentValues): Long {
        ultimoId++
        values.put(COLUNA_ID, ultimoId)
        values.put(COLUNA_DATA_HORA, Date().time)
        database.add(values)
        return ultimoId
    }

    fun delete(id: Long): Boolean {
        val value = database.find { value ->
            value.getAsLong(COLUNA_ID) == id
        }
        if (value != null) {
            return database.remove(value)
        }
        return false
    }

    fun query(): MutableList<ContentValues> {
        return database
    }

    fun query(id: Long): ContentValues? {
        val value = database.find { value ->
            value.getAsLong(COLUNA_ID) == id
        }
        return value
    }

}