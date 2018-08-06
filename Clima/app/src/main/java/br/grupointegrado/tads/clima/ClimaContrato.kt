package br.grupointegrado.tads.clima

import android.net.Uri
import android.provider.BaseColumns

/**
 * Created by Gean on 15/06/2018.
 */
class ClimaContrato {

    val AUTORIDADE = "br.grupointegrado.tads.clima.dados.ClimaContentProvider"
    val URI_BASE = Uri.parse("content://$AUTORIDADE")
    val URI_CLIMA = "clima"

    internal object clima : BaseColumns {

        val TABELA = "clima"
        val COLUNA_DATA_HORA = "data_hora"
        val COLUNA_CLIMA_ID = "clima_id"
        val COLUNA_MIN_TEMP = "min_temp"
        val COLUNA_MAX_TEMP = "max_temp"
        val COLUNA_UMIDADE = "umidade"
        val COLUNA_PRESSAO = "pressao"
        val COLUNA_VEL_VENTO = "vel_vendo"
        val COLUNA_GRAUS = "graus"

    }
}