package br.grupointegrado.tads.usandointents

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_enviar.setOnClickListener{
            //Toast.makeText(this, et_mensagem.text.toString(), Toast.LENGTH_LONG).show()
            val segundaActivity = SegundaActivity::class.java
            val intent = Intent(this, segundaActivity)

            val mensagem = et_mensagem.text.toString()
            intent.putExtra(Intent.EXTRA_TEXT, mensagem)

            startActivity(intent)


        }
    }

    fun abrirSite(view: View) {
        val acao = Intent.ACTION_VIEW;
        val dado = Uri.parse("http://www.grupointegrado.br")
        val intentImplicito = Intent(acao, dado)

        //verifica se a ação pode ser atendida
        if (intentImplicito.resolveActivity(packageManager) != null) {
            startActivity(intentImplicito)
        }
    }

    fun abrirMapa(view: View) {
        val endereco = "Av. Irmãos Pereira, 670 - Centro, Campo Mourão - PR"

        //construindo URI
        val builder = Uri.Builder()
                .scheme("geo")
                .path("0,0")
                .appendQueryParameter("q", endereco)

        val uriEndereco = builder.build()
        val intent = Intent(Intent.ACTION_VIEW, uriEndereco)

        //verifica se a ação pode ser  atendida
        if (intent.resolveActivity(packageManager) != null){
            startActivity(intent)
        }
    }




}
