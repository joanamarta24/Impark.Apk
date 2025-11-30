enum class TipoVeiculo(val descricao: String) {
    CARRO("Carro"),
    MOTO("Moto"),
    CAMINHAO("Caminhão"),
    ONIBUS("Ônibus"),
    VAN("Van"),
    OUTROS("Outros");

    companion object {
        fun fromString(value: String): TipoVeiculo {
            return try {
                values().find {
                    it.descricao.equals(value, ignoreCase = true)
                } ?: OUTROS
            } catch (e: Exception) {
                OUTROS
            }
        }

        fun toMapString(veiculosPorTipo: Map<TipoVeiculo, Int>): Map<String, Int> {
            return veiculosPorTipo.mapKeys { it.key.descricao }
        }

        fun fromMapString(veiculosPorTipo: Map<String, Int>): Map<TipoVeiculo, Int> {
            return veiculosPorTipo.mapKeys { (key, _) ->
                fromString(key)
            }.filterKeys { it != OUTROS }
        }

        // Método para converter lista de strings para lista de TipoVeiculo
        fun fromStringList(strings: List<String>): List<TipoVeiculo> {
            return strings.map { fromString(it) }.filter { it != OUTROS }
        }

        // Método para converter lista de TipoVeiculo para lista de strings
        fun toStringList(tipos: List<TipoVeiculo>): List<String> {
            return tipos.map { it.descricao }
        }
    }
}