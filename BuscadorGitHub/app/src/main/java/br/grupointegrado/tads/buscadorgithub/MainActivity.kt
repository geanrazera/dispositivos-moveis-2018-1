package br.grupointegrado.tads.buscadorgithub

import android.content.Intent
import android.support.v4.app.LoaderManager
import android.content.Loader
import android.content.SharedPreferences
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.preference.PreferenceManager
import android.support.v4.content.AsyncTaskLoader
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebChromeClient
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<String>, SharedPreferences.OnSharedPreferenceChangeListener {
    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String?) {
        if (key == getString(R.string.pref_exibir_url)) {
            val exibirUrl = sharedPreferences.getBoolean(key, resources.getBoolean(R.bool.pref_exibir_url_padrao))

            tv_url.visibility = if (exibirUrl) View.VISIBLE else View.GONE
        }else if (key == getString(R.string.pref_cor_fundo)) {
            val corFundo = sharedPreferences.getString(getString(R.string.pref_cor_fundo), getString(R.string.pref_cor_fundo_padrao))
            val backgroundColor = selecionaCorDeFundo(corFundo)
            window.decorView.setBackgroundColor(backgroundColor)
        }

    }

    companion object {
        var URL_BUSCA = "URL_BUSCA"
        val BUSCA_GITHUB_LOADER_ID = 1000
    }

    var cacheResultado: String? = null

    override fun onLoaderReset(loader: android.support.v4.content.Loader<String>?) {
    }

    override fun onLoadFinished(loader: android.support.v4.content.Loader<String>?, result: String?) {
        if (result != null) {
            tv_github_resultado.text = result
            exibirResultado()
        } else {
            exibirMensagemErro()
        }
    }

    override fun onCreateLoader(id: Int, parametros: Bundle?): android.support.v4.content.Loader<String> {
        val loader = object : AsyncTaskLoader<String>(this) {

            override fun onStartLoading() {
                super.onStartLoading()
                if (parametros == null) {
                    return
                }
                exibirProgressBar()
                if (cacheResultado != null) {
                    deliverResult(cacheResultado)
                }
                forceLoad()
            }

            override fun loadInBackground(): String? {
                try {
                    var urlBusca = parametros?.getString(URL_BUSCA)
                    val url = URL(urlBusca)
                    val resultado = NetworkUtils.obterRespostaDaUrlHtpp(url!!)
                    return resultado
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
                return null
            }

            override fun deliverResult(resultado: String?) {
                super.deliverResult(resultado)
                cacheResultado = resultado
            }

        }
        return loader
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        exercicioJson()
        supportLoaderManager.initLoader(BUSCA_GITHUB_LOADER_ID, null, this)
        et_busca.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                cacheResultado = null
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("CONTEUDO_TEXTVIEW")) {
                val conteudoTextView = savedInstanceState.getString("CONTEUDO_TEXTVIEW")
                tv_url.text = conteudoTextView
            }
        }

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)

        val exibirUrl = sharedPreferences.getBoolean(getString(R.string.pref_exibir_url), resources.getBoolean(R.bool.pref_exibir_url_padrao))
        if (!exibirUrl) {
            tv_url.visibility = View.GONE //View.INVISIBLE
        }

        val corFundo = sharedPreferences.getString(getString(R.string.pref_cor_fundo), getString(R.string.pref_cor_fundo_padrao))
        val backgroundColor = selecionaCorDeFundo(corFundo)
        window.decorView.setBackgroundColor(backgroundColor)
    }

    override fun onDestroy() {
        super.onDestroy()
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_buscar) {
            buscarNoGithub()
        }

        if (item?.itemId == R.id.action_configuracoes) {
            val intent = Intent(this, ConfiguracaoActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        imprimir("onSaveInstanceState")
        val conteudoTextView = tv_url.text.toString()
        outState?.putString("CONTEUDO_TEXTVIEW", conteudoTextView)
    }

    fun buscarNoGithub() {
        val buscaGitHub = et_busca.text.toString()
        val urlBuscaGithub = NetworkUtils.construirUrl(buscaGitHub)
        tv_url.text = urlBuscaGithub.toString()

//        if (urlBuscaGithub != null) {
//            val resultado = NetworkUtils.obterRespostaDaUrlHtpp(urlBuscaGithub)
//            tv_github_resultado.text = resultado;
//        }

        if (urlBuscaGithub != null) {
//            val task = GithubBuscaTask()
//            task.execute(urlBuscaGithub)

            val parametros = Bundle()
            parametros.putString("URL_BUSCA", urlBuscaGithub.toString())
            supportLoaderManager.restartLoader(BUSCA_GITHUB_LOADER_ID, parametros, this)
        }
    }

//    inner class GithubBuscaTask : AsyncTask<URL, Void, String>() {
//
//        override fun onPreExecute() {
//            exibirProgressBar()
//        }
//
//        override fun doInBackground(vararg params: URL?): String? {
//            try {
//                var url = params[0]
//                val result =
//                        NetworkUtils.obterRespostaDaUrlHtpp(url!!)
//                return result
//            } catch (ex: Exception) {
//                ex.printStackTrace()
//            }
//            return null;
//        }
//
//        override fun onPostExecute(result: String?) {
//            if (result != null) {
//                tv_github_resultado.text = result
//                exibirResultado()
//            } else {
//                exibirMensagemErro()
//            }
//        }
//    }

    fun exibirResultado() {
        tv_github_resultado.visibility = View.VISIBLE
        tv_mensagem_erro.visibility = View.INVISIBLE
        pb_aguarde.visibility = View.INVISIBLE
    }

    fun exibirMensagemErro() {
        tv_mensagem_erro.visibility = View.VISIBLE
        pb_aguarde.visibility = View.INVISIBLE
        tv_github_resultado.visibility = View.INVISIBLE
    }

    fun exibirProgressBar() {
        pb_aguarde.visibility = View.VISIBLE
        tv_github_resultado.visibility = View.INVISIBLE
        tv_mensagem_erro.visibility = View.INVISIBLE
    }

    fun exercicioJson() {
        var dadosJson = """
            {
                "temperatura":{
                    "minima":11.34,
                    "maxima":19.01
                },
                "clima":{
                    "id":801,
                    "condicao": "Nuvens",
                    "descricao": "Poucas nuvens"
                },
                "pressao":1023.51,
                "umidade":87
            }
            """

        val objetoPrevisao = JSONObject(dadosJson)
        val clima = objetoPrevisao.getJSONObject("clima")
        val condicao = clima.getString("condicao")
        val pressao = objetoPrevisao.getDouble("pressao")

        Log.d("exercicioJson", "$condicao -> $pressao")
    }

    fun imprimir(mensagem: String) {
        Log.d("Ciclo-de-vida", mensagem)
        tv_mensagem_erro.append("$mensagem\n")
    }

    fun selecionaCorDeFundo(corFundo: String): Int {
        return when (corFundo) {
            getString(R.string.pref_cor_fundo_branco_valor) -> ContextCompat.getColor(this, R.color.fundoBranco)
            getString(R.string.pref_cor_fundo_verde_valor) -> ContextCompat.getColor(this, R.color.fundoVerde)
            getString(R.string.pref_cor_fundo_azul_valor) -> ContextCompat.getColor(this, R.color.fundoAzul)
            else -> ContextCompat.getColor(this, R.color.fundoBranco)
        }
    }
}