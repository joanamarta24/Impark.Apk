import com.example.imparkapk.data.dao.model.enus.StatusReserva

data class ReservaUiState(
    // Dados da reserva
    val id: String = "",
    val idUsuario: String = "",
    val idEstabelecimento: String = "",
    val nomeEstabelecimento: String = "",
    val dataReserva: String = "",
    val horaReserva: String = "",
    val numeroPessoas: Int = 1,
    val status: StatusReserva = StatusReserva.PENDENTE,
    val observacoes: String = "",
    val mesa: String = "",

    // Estado da UI
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null,
    val isDataValid: Boolean = false,

    // Validações de campos
    val dataError: String? = null,
    val horaError: String? = null,
    val pessoasError: String? = null,

    // Navegação
    val shouldNavigateToConfirmacao: Boolean = false,
    val shouldNavigateToDetalhes: Boolean = false,

    // Data e hora como objetos
    val selectedDate: Long? = null,
    val selectedTime: Long? = null
)
