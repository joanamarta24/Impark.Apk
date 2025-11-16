package com.example.imparkapk.data.dao.converters.menager

import android.content.Context
import android.content.SharedPreferences
import com.example.imparkapk.data.dao.remote.api.dto.ClienteDto
import com.example.imparkapk.data.dao.remote.api.dto.usuario.DonoDto
import com.google.gson.Gson

class UsuarioStateManager(private val context: Context) {
    companion object {
        private const val PREFS_NAME = "UserStatePrefs"
        private const val KEY_USER_TYPE = "user_type"
        private const val KEY_USER_DATA = "user_data"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
        private const val KEY_LAST_LOGIN = "last_login"

        //TIPOS DE USUÁRIO
        const val USER_TYPE_CLIENTE = "CLIENTE"
        const val USER_TYPE_DONO = "DONO"
        const val USER_TYPE_NOME = "NOME"
    }

    private val prefs: SharedPreferences by lazy {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }
    private val gson = Gson()

    //SALVAR ESTADO DO CLIENTE

    fun saveClienteState(cliente: ClienteDto, token: String? = null) {
        val editor = prefs.edit()
        editor.putString(KEY_USER_TYPE, USER_TYPE_CLIENTE)
        editor.putString(KEY_USER_DATA, gson.toJson(cliente))
        editor.putBoolean(KEY_IS_LOGGED_IN, true)
        token?.let { editor.putString(KEY_ACCESS_TOKEN, it) }
        editor.apply()
    }
    //SALVAR ESTADO DO DONO

    fun saveDonoState(dono: DonoDto, token: String? = null) {
        val editor = prefs.edit()
        editor.putString(KEY_USER_TYPE, USER_TYPE_DONO)
        editor.putString(KEY_USER_DATA, gson.toJson(dono))
        editor.putBoolean(KEY_IS_LOGGED_IN, true)
        token?.let { editor.putString(KEY_ACCESS_TOKEN, it) }
        editor.apply()

    }

    // Obter Cliente atual
    fun getCurrentCliente(): ClienteDto? {
        return if (getUserType() == USER_TYPE_CLIENTE) {
            val userJson = prefs.getString(KEY_USER_DATA, null)
            userJson?.let { gson.fromJson(it, ClienteDto::class.java) }
        } else {
            null
        }
    }

    // Obter Dono atual
    fun getCurrentDono(): DonoDto? {
        return if (getUserType() == USER_TYPE_DONO) {
            val userJson = prefs.getString(KEY_USER_DATA, null)
            userJson?.let { gson.fromJson(it, DonoDto::class.java) }
        } else {
            null
        }
    }
    //Obter tipo de usuário atual
    fun getUserType(): String {
        return prefs.getString(KEY_USER_TYPE, USER_TYPE_NOME) ?: USER_TYPE_NOME
    }
    // Verificar se está logado
    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false)
    }
    // Verificar se é Cliente
    fun isCliente(): Boolean{
        return getUserType() == USER_TYPE_CLIENTE && isLoggedIn()
    }
    //Verificar se é Dono
    fun isDono(): Boolean{
        return getUserType() == USER_TYPE_DONO && isLoggedIn()
    }
    //Obter token de acesso
    fun getAccessToken(): String? {
        return prefs.getString(KEY_ACCESS_TOKEN, null)

    }
    //Atualizar dados do usário
    fun updateClienteData(cliente: ClienteDto){
        if (isCliente()){
            prefs.edit().putString(KEY_USER_DATA, gson.toJson(cliente)).apply()
        }
    }
    fun updateDonoData(dono: DonoDto){
        if (isDono()){
            prefs.edit().putString(KEY_USER_DATA, gson.toJson(dono)).apply()

        }
    }
    // Logout

    fun logout(){
        val editor = prefs.edit()
        editor.remove(KEY_USER_TYPE)
        editor.remove(KEY_USER_DATA)
        editor.remove(KEY_IS_LOGGED_IN)
        editor.remove(KEY_ACCESS_TOKEN)
        editor.remove(KEY_REFRESH_TOKEN)
        editor.apply()

    }

    // Obter ID do usuário atual

    fun getCurrentUserId(): Long? {
        return when (getUserType()) {
            USER_TYPE_CLIENTE -> getCurrentCliente()?.id
            USER_TYPE_DONO -> getCurrentDono()?.id
            else -> null
        }
    }
    // Obter email do usuário atual

    fun getCurrentUserEmail(): String? {
        return when (getUserType()) {
            USER_TYPE_CLIENTE -> getCurrentCliente()?.email
            USER_TYPE_DONO -> getCurrentDono()?.email
            else -> null
        }
    }
    // Verificar se é a primeira vez do usuário

    fun isFirstTimeUser(): Boolean {
        return !prefs.contains(KEY_LAST_LOGIN)
    }
    // Limpar dados sensíveis (mas manter preferências)

    fun clearSensitiveData(){
        val editor = prefs.edit()
        editor.remove(KEY_ACCESS_TOKEN)
        editor.remove(KEY_REFRESH_TOKEN)
        editor.remove(KEY_USER_DATA)
        editor.apply()
    }
    // Salvar refresh token

    fun saveRefreshToken(refreshToken: String) {
        prefs.edit().putString(KEY_REFRESH_TOKEN, refreshToken).apply()
    }

    // Obter refresh token

    fun getRefreshToken(): String? {
        return prefs.getString(KEY_REFRESH_TOKEN, null)
    }

    // Verificar se o token está expirado (exemplo simples)

    fun isTokenExpired(): Boolean {
        val lastLogin = prefs.getLong(KEY_LAST_LOGIN, 0)
        val currentTime = System.currentTimeMillis()

        // Considerar expirado após 24 horas (ajustar conforme necessidade)
        return (currentTime - lastLogin) > 24 * 60 * 60 * 1000
    }

    // Obter tempo desde o último login

    fun getTimeSinceLastLogin(): Long {
        val lastLogin = prefs.getLong(KEY_LAST_LOGIN, 0)
        return System.currentTimeMillis() - lastLogin
    }
}

