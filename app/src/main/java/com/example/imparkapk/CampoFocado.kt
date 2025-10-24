package com.example.imparktcc.ui.viewmodel


enum class CampoFocado {
    NONE,
    NOME,
    EMAIL,
    SENHA,
    CONFIRMAR_SENHA,
    TERMOS,
    POLITICA
}


fun CampoFocado?.isCampoTexto(): Boolean {
    return this == CampoFocado.NOME ||
            this == CampoFocado.EMAIL ||
            this == CampoFocado.SENHA ||
            this == CampoFocado.CONFIRMAR_SENHA
}

fun CampoFocado?.isCheckbox(): Boolean {
    return this == CampoFocado.TERMOS || this == CampoFocado.POLITICA
}

fun CampoFocado?.getOrdemTab(): Int {
    return when (this) {
        CampoFocado.NOME -> 0
        CampoFocado.EMAIL -> 1
        CampoFocado.SENHA -> 2
        CampoFocado.CONFIRMAR_SENHA -> 3
        CampoFocado.TERMOS -> 4
        CampoFocado.POLITICA -> 5
        CampoFocado.NONE -> -1
        null -> -1
    }
}

fun CampoFocado.getProximoCampo(): CampoFocado {
    return when (this) {
        CampoFocado.NOME -> CampoFocado.EMAIL
        CampoFocado.EMAIL -> CampoFocado.SENHA
        CampoFocado.SENHA -> CampoFocado.CONFIRMAR_SENHA
        CampoFocado.CONFIRMAR_SENHA -> CampoFocado.TERMOS
        CampoFocado.TERMOS -> CampoFocado.POLITICA
        CampoFocado.POLITICA -> CampoFocado.NONE
        CampoFocado.NONE -> CampoFocado.NONE
    }
}

fun CampoFocado.getCampoAnterior(): CampoFocado {
    return when (this) {
        CampoFocado.NOME -> CampoFocado.NONE
        CampoFocado.EMAIL -> CampoFocado.NOME
        CampoFocado.SENHA -> CampoFocado.EMAIL
        CampoFocado.CONFIRMAR_SENHA -> CampoFocado.SENHA
        CampoFocado.TERMOS -> CampoFocado.CONFIRMAR_SENHA
        CampoFocado.POLITICA -> CampoFocado.TERMOS
        CampoFocado.NONE -> CampoFocado.NONE
    }
}