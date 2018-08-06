package br.grupointegrado.tads.listadetarefa

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import br.grupointegrado.tads.listadetarefa.dados.BdFake
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_tarefa.*

class MainActivity : AppCompatActivity() {

    private var tarefasAdapter: TarefasAdapter? = null
    private var database : SQLiteDatabase? = null

//    companion object {
//        val CADASTRAR_TAREFA = 1001
//    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv_clientes.layoutManager = LinearLayoutManager(this)


        val cursor = getTodasTarefas();

        tarefasAdapter = TarefasAdapter(cursor)
        rv_clientes.adapter = tarefasAdapter

        val itemTouch = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                val id = viewHolder.itemView.tag as Long
                removerTarefa(id)
                tarefasAdapter!!.atualizarCursor(getTodasTarefas())
            }

        })

        itemTouch.attachToRecyclerView(rv_clientes)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_adicionar) {
            adicionar(rv_clientes)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun getTodasTarefas(): Cursor {
        return database!!.query(
                TarefaContrato.tarefa.TABELA,
                null,
                null,
                null,
                null,
                null,
                TarefaContrato.tarefa.COLUNA_DATA_HORA)
    }

    fun adicionar(view : View) {
        if(ed_descricao.text.isEmpty() || ed_titulo.text.isEmpty()){
            return
        }

        val descricao = ed_descricao.text.toString()
        val titulo = ed_titulo.text.toString()


        adicionarNovaTarefa(descricao, titulo)

        tarefasAdapter!!.atualizarCursor(getTodasTarefas())
        ed_descricao.text.clear()
        ed_titulo.text.clear()
        ed_descricao.clearFocus()
    }

    private fun adicionarNovaTarefa(descricao : String, titulo : String) : Long {
        val tarefa = ContentValues()
        tarefa.put(TarefaContrato.tarefa.COLUNA_DESCRICAO, descricao)
        tarefa.put(TarefaContrato.tarefa.COLUNA_TITULO, titulo)

        return database!!.insert(
                TarefaContrato.tarefa.TABELA,
                null,
                tarefa
        )
    }

    private fun removerTarefa(id: Long): Boolean {
        val tabela = TarefaContrato.tarefa.TABELA
        val where = "${BaseColumns._ID} = ?"
        val argumentos = arrayOf(id.toString())

        val removidos = database!!.delete(tabela, where, argumentos)

        return removidos > 0
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (CADASTRAR_TAREFA == requestCode && resultCode == Activity.RESULT_OK) {
//            tarefasAdapter!!.atualizarCursor(getTodasTarefas())
//        }
//        super.onActivityResult(requestCode, resultCode, data)
//    }

}
