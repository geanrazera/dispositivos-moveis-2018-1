package br.grupointegrado.tads.clima

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


/**
 * Created by Gean on 16/04/2018.
 */


class PrevisaoAdapter : RecyclerView.Adapter<PrevisaoAdapter.PrevisaoViewHolder> {

    private var dadosClima: Array<String?>?

    constructor(dadosClima: Array<String?>?){
        this.dadosClima = dadosClima
    }

    inner class PrevisaoViewHolder : RecyclerView.ViewHolder {

        val tvDadosPrevisao : TextView?

        constructor(itemView: View?): super(itemView){
            tvDadosPrevisao = itemView?.findViewById(R.id.tv_dados_previsao)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PrevisaoViewHolder {
        val previsaoListaItem = LayoutInflater.from(parent?.context).inflate(R.layout.previsao_lista_item, parent, false)
        val previsaoViewHolder = PrevisaoViewHolder(previsaoListaItem)

        return previsaoViewHolder
    }

    override fun onBindViewHolder(holder: PrevisaoViewHolder?, position: Int) {
        val numero = dadosClima?.get(position)
        holder?.tvDadosPrevisao?.text = numero.toString()
    }

    override fun getItemCount(): Int {
        if (dadosClima == null){
            return 0
        } else {
            return dadosClima!!.size
        }
    }

    fun setDadosClima(dados: Array<String?>?){
        dadosClima = dados
        notifyDataSetChanged()
    }


}