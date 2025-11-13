package com.example.imparkapk.data.dao.converters.menager

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.example.imparkapk.UiState.UsuarioUiState
import com.example.imparktcc.model.Usuario
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.emptyList

class UsuarioStateManager (private val context: Context){
    companion object{
        private const val PREFS_NAME ="UserStatePrefs"
        private const val KEY_USER_TYPE ="user_type"
        private const val KEY_USER_DATA = "user_data"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
        private const val KEY_LAST_LOGIN = "last_login"

        //TIPOS DE USUÁRIO
    }
}