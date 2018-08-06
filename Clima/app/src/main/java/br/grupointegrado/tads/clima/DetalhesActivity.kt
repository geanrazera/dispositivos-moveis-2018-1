package br.grupointegrado.tads.clima

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_detalhes.*

class DetalhesActivity : AppCompatActivity() {


    companion object {
        val DADOS_PREVISAO = "DADOS_PREVISAO"
    }

    var previsao: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes)
            if (intent.hasExtra(DADOS_PREVISAO)) {
                previsao = intent.getStringExtra(DADOS_PREVISAO)
                tv_exibir_previsao.text = previsao
            }
    }
}
