package br.grupointegrado.tads.buscadorgithub

import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.preference.ListPreference
import android.support.v7.preference.Preference
import android.support.v7.preference.PreferenceFragmentCompat

/**
 * Created by Gean on 11/05/2018.
 */
class ConfiguracaoFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

    fun atualizarPreferenceSummary (preference : Preference){
        if(preference is ListPreference){
            //cor fundo branco, cor fundo verde, cor fundo azul
            val corSelecionada = preferenceScreen.sharedPreferences.getString(preference.key, "")
            // 0, 1, etc
            val indexSelecionado = preference.findIndexOfValue(corSelecionada)
            ///branco, verde, etc
            val tituloSelecionado = preference.entries[indexSelecionado]
            preference.summary = tituloSelecionado
        }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        val preference = findPreference(key)
        if (preference != null){
            atualizarPreferenceSummary(preference)
        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootkey: String?){
        addPreferencesFromResource(R.xml.pref_github)

        for (i in 0 until preferenceScreen.preferenceCount){
            val preference = preferenceScreen.getPreference(i)
            atualizarPreferenceSummary(preference)
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }
}