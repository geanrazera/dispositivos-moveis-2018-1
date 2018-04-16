package br.grupointegrado.tads.trabmetas

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //criarListView
            listView = findViewById(R.id.lv_lista)

        //menuListView
            listView?.setOnCreateContextMenuListener(View.OnCreateContextMenuListener
            { contextMenu, view, contextMenuInfo -> menuInflater.inflate(R.menu.menu, contextMenu) })

    }

    var listView : ListView? = null
    var lista = ArrayList<Meta>()
    var pos = 0
    var estado = 0


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.item_add) {
            estado = 1
            inserir(lista.size)
            limparCampos()
        }

        if (item?.itemId == R.id.item_edit) {
            inserir(pos)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {

        val menuList = item?.getMenuInfo() as AdapterView.AdapterContextMenuInfo
        pos = menuList.position

        if (item?.itemId == R.id.item_edit) {
            estado = 2
            editar()
        }

        if (item?.itemId == R.id.item_del) {
            remover()
        }

        if (item?.itemId == R.id.item_encerrar) {
            encerrar()
        }

        return super.onContextItemSelected(item)
    }




    fun listar() {
        listView?.setAdapter(ArrayAdapter<Meta>(this, android.R.layout.simple_list_item_1, lista))
    }

    fun inserir(numero : Int){
            val meta = Meta(et_titulo.text.toString(),
                    et_desc.text.toString(), et_dt_limit.text.toString())

        if (estado == 1) {
            lista.add(numero, meta)
            estado = 0
        } else {
            lista[numero] = meta
        }

        listar()
        limparCampos()

    }

    fun editar() {
        et_titulo.append(lista[pos].titulo)
        et_desc.append(lista[pos].descricao)
        et_dt_limit.append(lista[pos].limite)
    }

    fun remover() {
        lista.removeAt(pos)
        listar()
        limparCampos()
    }

    fun limparCampos() {
        et_titulo.setText("")
        et_desc.setText("")
        et_dt_limit.setText("")
    }

    fun encerrar(){
        lista[pos].situacao = "Finalizado"
        listar()
        limparCampos()
    }
}
