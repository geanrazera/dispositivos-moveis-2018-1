package br.grupointegrado.tads.clima

import android.app.LoaderManager
import android.content.AsyncTaskLoader
import android.content.Intent
import android.content.Loader
import android.content.SharedPreferences
import android.net.Uri
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import br.grupointegrado.tads.clima.dados.ClimaPreferencias
import br.grupointegrado.tads.clima.util.JsonUtils
import br.grupointegrado.tads.clima.util.NetworkUtils

import kotlinx.android.synthetic.main.activity_main.*
import java.net.URL

class MainActivity : AppCompatActivity(),PrevisaoAdapter.PrevisaoItemClickListener,
        LoaderManager.LoaderCallbacks<Array<String?>?>,
        SharedPreferences.OnSharedPreferenceChangeListener {

    companion object {
        val DADOS_PREVISAO_LOADER = 1000
        var PREFERENCIAS_FORAM_ALTERADAS = false
    }

    var previsaoAdapter: PrevisaoAdapter? = (null)

//        val dadosClimaFicticios = listOf("Hoje - Céu limpo - 17°C / 15°C",
//                "Amanhã - Nublado - 19°C / 15°C",
//                "Quinta - Chuvoso - 30°C / 11°C",
//                "Sexta - Chuva com raios - 21°C / 9°C",
//                "Sábado - Chuva com raios - 16°C / 7°C",
//                "Domingo - Chuvoso - 16°C / 8°C",
//                "Segunda - Parcialmente nublado - 15°C / 10°C",
//                "Ter, Mai 24 - Vai curintia - 16°C / 18°C",
//                "Qua, Mai 25 - Nublado - 19°C / 15°C",
//                "Qui, Mai 26 - Tempestade - 30°C / 11°C",
//                "Sex, Mai 27 - Furacão - 21°C / 9°C",
//                "Sáb, Mai 28 - Meteóro - 16°C / 7°C",
//                "Dom, Mai 29 - Apocalipse - 16°C / 8°C",
//                "Seg, Mai 30 - Pós-apocalipse - 15°C / 10°C")

    inner class  BuscarClimaTask : AsyncTask <URL, Void, String> () {
        override fun onPreExecute() {
            exibirProgressBar()
        }

        override fun doInBackground(vararg params: URL?): String? {
            try {
                var url = params[0]
                val result =
                        NetworkUtils.obterRespostaDaUrlHttp(url!!)
                return result
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(result: String?) {
            if (result == null) {
                exibirMensagemErro()
            }else{
//                json(result)
                val retorno = JsonUtils.getSimplesStringsDeClimaDoJson(applicationContext, result)
                previsaoAdapter?.setDadosClima(retorno)
                exibirResultado()

            }
        }

//        fun json (result : String?){
//
//            val json = JSONObject(result)
//            val lista = json.getJSONArray("list")
//            for (i in 0 until lista.length()){
//                val lista1 = lista.getJSONObject(i)
//                val dataLong = lista1.getString("dt")
//                val data = converterData (dataLong)
//                val main = lista1.getJSONObject("main")
//                val temp = main.getString("temp")
//                val humidity = main.getString("humidity")
//                val clima = lista1.getJSONArray("weather")
//                val clima1 = clima.getJSONObject(0)
//                val desc = clima1.getString("description")
//
//                dados_clima.append("Data $data \n" +
//                                    "Temperatura $temp \n" +
//                                    "Umidade $humidity \n" +
//                                    "Clima $desc \n\n\n")
//            }
//      }

//        fun converterData(data: String):CharSequence?{
//            val dataHoraMilisegundos: Long = (java.lang.Long.valueOf(data)) * 1000
//            val dataHora = Date(dataHoraMilisegundos)
//            return DateFormat.format("dd/MM/yyyy HH:mm", dataHora)
//        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // preencher o TextView com a lista

//      for (clima in dadosClimaFicticios){
//          dados_clima.append("$clima \n\n\n");
//      }

        previsaoAdapter = PrevisaoAdapter(null)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        rv_clima.adapter = previsaoAdapter
        rv_clima.layoutManager = layoutManager


        supportLoaderManager.initLoader(DADOS_PREVISAO_LOADER, null, this)

        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this)

        //carregarDadosDoClima()
    }


    override fun onDestroy() {
        super.onDestroy()

        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Array<String?>?> {
        val loader = object : AsyncTaskLoader<Array<String?>?>(this) {

            var dadosPrevisao: Array<String?>? = null

            override fun onStartLoading() {
                if (dadosPrevisao != null) {
                    deliverResult(dadosPrevisao);
                } else {
                    exibirProgressBar()
                    forceLoad()
                }
            }

            override fun loadInBackground(): Array<String?>? {
                try {
                    val localizacao = ClimaPreferencias
                            .getLocalizacaoSalva(this@MainActivity)

                    val url = NetworkUtils.construirUrl(localizacao)

                    if (url != null) {
                        val resultado = NetworkUtils.obterRespostaDaUrlHttp(url)
                        val dadosClima = JsonUtils
                                .getSimplesStringsDeClimaDoJson(this@MainActivity,
                                        resultado!!)
                        return dadosClima
                    }
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
                return null
            }

            override fun deliverResult(data: Array<String?>?) {
                super.deliverResult(data)
                dadosPrevisao = data
            }
        }
        return loader
    }

    override fun onLoadFinished(loader: Loader<Array<String?>?>?,
                                dadosClima: Array<String?>?) {
        previsaoAdapter?.setDadosClima(dadosClima)
        if (dadosClima != null) {
            exibirResultado()
        } else {
            exibirMensagemErro()
        }
    }

    override fun onLoaderReset(loader: Loader<Array<String?>?>?) {
    }

    override fun onItemClick(index: Int) {
        val previsao = previsaoAdapter!!.getDadosClima()!!.get(index)

        val intentDetalhes = Intent(this, DetalhesActivity::class.java)
        intentDetalhes.putExtra(DetalhesActivity.DADOS_PREVISAO, previsao)

        startActivity(intentDetalhes)
    }



    fun carregarDadosDoClima(){
        val localizacao = ClimaPreferencias.getLocalizacaoSalva(this)
        val url = NetworkUtils.construirUrl(localizacao)
        BuscarClimaTask().execute(url)
    }

    override fun onCreateOptionsMenu(menu : Menu?): Boolean {
        menuInflater.inflate(R.menu.clima, menu)
        return true
    }

//    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
//        if (item?.itemId == R.id.acao_atualizar) {
//            dados_clima.text = ""
//            carregarDadosDoClima()
//            return true
//        }
//        return super.onOptionsItemSelected(item)
//    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId === R.id.acao_atualizar) {
            supportLoaderManager.restartLoader(DADOS_PREVISAO_LOADER, null, this)
            return true
        }
        if (item?.itemId === R.id.acao_mapa) {
            abrirMapa()
            return true
        }
        if (item?.itemId === R.id.acao_configuracao) {
            abrirConfiguracao()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun abrirConfiguracao() {
        val intent = Intent(this, ConfiguracaoActivity::class.java)
        startActivity(intent)
    }

     fun exibirResultado(){
        rv_clima.visibility = View.VISIBLE
        mensagem_erro.visibility = View.INVISIBLE
        pb_aguarde.visibility = View.INVISIBLE

    }

    fun exibirMensagemErro(){
        rv_clima.visibility = View.INVISIBLE
        mensagem_erro.visibility = View.VISIBLE
        pb_aguarde.visibility = View.INVISIBLE
    }

    fun exibirProgressBar(){
        rv_clima.visibility = View.INVISIBLE
        mensagem_erro.visibility = View.INVISIBLE
        pb_aguarde.visibility = View.VISIBLE
    }


    fun abrirMapa() {
        val addressString = ClimaPreferencias.getLocalizacaoSalva(this)
        val uriGeo = Uri.parse("geo:0,0?q=$addressString")

        val intentMapa = Intent(Intent.ACTION_VIEW)
        intentMapa.data = uriGeo

        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intentMapa)
        }
    }

    override fun onStart() {
        super.onStart()

        if (PREFERENCIAS_FORAM_ALTERADAS) {
            supportLoaderManager.restartLoader(DADOS_PREVISAO_LOADER, null, this)
            PREFERENCIAS_FORAM_ALTERADAS = false
        }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?,
                                           key: String?) {
        PREFERENCIAS_FORAM_ALTERADAS = true
    }
}
