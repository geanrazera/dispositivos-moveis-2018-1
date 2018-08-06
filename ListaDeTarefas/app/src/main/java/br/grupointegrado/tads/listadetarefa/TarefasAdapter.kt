package br.grupointegrado.tads.listadetarefa

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import android.support.v7.widget.RecyclerView
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.grupointegrado.tads.listadetarefa.dados.BdFake
import java.util.*

class TarefasAdapter : RecyclerView.Adapter<TarefasAdapter.TarefaViewHolder> {

    private var cursor: Cursor

    constructor(cursor : Cursor){
        this.cursor = cursor
    }

    fun atualizarCursor(cursor: Cursor){
        this.cursor.close()
        this.cursor = cursor
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarefaViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.tarefa_list_item, parent, false)
        return TarefaViewHolder(view)
    }

    override fun onBindViewHolder(holder: TarefaViewHolder, position: Int) {
        if (!cursor.moveToPosition(position)){
            return
        }

        val id = cursor.getLong(cursor.getColumnIndex(BaseColumns._ID))
        val colunaTitulo = cursor.getColumnIndex(TarefaContrato.tarefa.COLUNA_TITULO)
        val titulo = cursor.getString(colunaTitulo)
        val colunaDescricao = cursor.getColumnIndex(TarefaContrato.tarefa.COLUNA_DESCRICAO)
        val descricao = cursor.getInt(colunaDescricao)

        holder.itemView.tag = id
        holder.tvTitulo.text = titulo
        holder.tvDescricao.text = titulo.toString()
    }

    override fun getItemCount(): Int {
        return cursor.count
    }

    inner class TarefaViewHolder : RecyclerView.ViewHolder {

        var tvTitulo: TextView
        var tvDescricao: TextView

        constructor(itemView: View) : super(itemView) {
            tvTitulo = itemView.findViewById(R.id.tv_titulo)
            tvDescricao = itemView.findViewById(R.id.tv_descricao)
        }
    }

//    private var list: MutableList<ContentValues>
//
//    constructor(list: MutableList<ContentValues>) {
//        this.list = list
//    }
//
//    fun atualizar(list: MutableList<ContentValues>) {
//        this.list = list
//        notifyDataSetChanged()
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClienteViewHolder {
//        val inflater = LayoutInflater.from(parent.context)
//        val view = inflater.inflate(R.layout.tarefa_list_item, parent, false)
//        return ClienteViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ClienteViewHolder, position: Int) {
//        val value = this.list.get(position)
//
//        val id = value.getAsLong(BdFake.COLUNA_ID)
//        val titulo = value.getAsString(BdFake.COLUNA_TITULO)
//        val descricao = value.getAsString(BdFake.COLUNA_DESCRICAO)
//        val data = value.getAsLong(BdFake.COLUNA_DATA_HORA)
//
//        holder.itemView.tag = id
//
//        holder.tvId.text = id.toString()
//        holder.tvTitulo.text = titulo
//        holder.tvDescricao.text = descricao
//        holder.tvData.text = DateFormat.format("dd/MM/yyyy 'às' HH:mm", Date(data))
//    }
//
//    override fun getItemCount(): Int {
//        return list.size
//    }
//
//    inner class ClienteViewHolder : RecyclerView.ViewHolder {
//
//        var tvId: TextView
//        var tvTitulo: TextView
//        var tvDescricao: TextView
//        var tvData: TextView
//
//        constructor(itemView: View) : super(itemView) {
//            tvId = itemView.findViewById(R.id.tv_id)
//            tvTitulo = itemView.findViewById(R.id.tv_titulo)
//            tvDescricao = itemView.findViewById(R.id.tv_descricao)
//            tvData = itemView.findViewById(R.id.tv_data)
//        }
//    }
}
