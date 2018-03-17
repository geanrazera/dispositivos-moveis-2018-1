package br.grupointegrado.tads.buscadorgithub

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_buscar){
            buscarNoGithub()
        }
        return super.onOptionsItemSelected(item)
    }

    fun buscarNoGithub(){
        val buscaGitHub = et_busca.text.toString()
        val urlBuscaGithub = NetworkUtils.construirUrl(buscaGitHub)
        tv_url.text = urlBuscaGithub.toString()
    }
}
