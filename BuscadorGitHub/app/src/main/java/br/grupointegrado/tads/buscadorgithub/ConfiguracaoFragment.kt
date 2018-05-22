package br.grupointegrado.tads.buscadorgithub

import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat

/**
 * Created by Gean on 11/05/2018.
 */
class ConfiguracaoFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootkey: String?){
        addPreferencesFromResource(R.xml.pref_github)
    }
}