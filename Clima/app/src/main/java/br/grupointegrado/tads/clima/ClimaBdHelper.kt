package br.grupointegrado.tads.clima

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

/**
 * Created by Gean on 20/06/2018.
 */
class ClimaBdHelper : SQLiteOpenHelper{

    companion object {
        val BANCO_DE_DADOS = "clima.db";
        val VERSAO = 2
    }

    constructor(context: Context) : super(context, BANCO_DE_DADOS, null, VERSAO) {
    }

    override fun onCreate(db: SQLiteDatabase) {
        val Clima = ClimaContrato.clima;
        val CREATE_TABLE_CLIMA = """
            CREATE TABLE ${Clima.TABELA} (
                ${BaseColumns._ID}         INTEGER PRIMARY KEY AUTOINCREMENT,
                ${Clima.COLUNA_DATA_HORA}  INTEGER NOT NULL,
                ${Clima.COLUNA_CLIMA_ID}   INTEGER NOT NULL,
                ${Clima.COLUNA_MIN_TEMP}   REAL NOT NULL,
                ${Clima.COLUNA_MAX_TEMP}   REAL NOT NULL,
                ${Clima.COLUNA_UMIDADE}    REAL NOT NULL,
                ${Clima.COLUNA_PRESSAO}    REAL NOT NULL,
                ${Clima.COLUNA_VEL_VENTO}  REAL NOT NULL,
                ${Clima.COLUNA_GRAUS}      REAL NOT NULL,
                UNIQUE (${Clima.COLUNA_DATA_HORA}) ON CONFLICT REPLACE
            );
        """
        println(CREATE_TABLE_CLIMA)
        db.execSQL(CREATE_TABLE_CLIMA)
    }

    override fun onUpgrade(db: SQLiteDatabase, versaoAterior: Int, novaVersao: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${ClimaContrato.clima.TABELA}")
        onCreate(db)
    }
}