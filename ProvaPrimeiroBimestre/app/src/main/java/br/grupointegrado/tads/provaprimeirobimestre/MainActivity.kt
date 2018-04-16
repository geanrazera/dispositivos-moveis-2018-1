package br.grupointegrado.tads.provaprimeirobimestre

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    data class Paralelepipedo(var base: String, var altura: String, var profundidade: String) {
    }

    var area : Float = 0F
    var volume : Float = 0F


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(et_base.text.isEmpty() || et_altura.text.isEmpty() || et_profundidade.text.isEmpty()){
            dispararAlerta()
        }else{
            calcular()
        }
        return super.onOptionsItemSelected(item)
    }


    fun calcular(){
            var p = Paralelepipedo(et_base.text.toString(),
                    et_altura.text.toString(), et_profundidade.text.toString())

            area = ((p.base.toFloat() * p.altura.toFloat()))
            volume = ((p.base.toFloat() * p.altura.toFloat() * p.profundidade.toFloat()))

            limparCampos()
            setarCampos()
    }

    fun limparCampos() {
        et_base.setText("")
        et_altura.setText("")
        et_profundidade.setText("")
    }

    fun setarCampos() {
        tv_area.setText("Area: " + area.toString())
        tv_volume.setText("Volume: " + volume.toString())
        tv_alerta.setText("")
    }

    fun dispararAlerta() {
        tv_alerta.setText("Informe todos os numeros!!!")
        tv_area.setText("")
        tv_volume.setText("")
    }
}
