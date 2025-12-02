import androidx.room.*
import com.example.imparkapk.data.dao.local.dao.entity.EstatisticaMarca
import com.example.imparkapk.data.dao.local.dao.entity.EstatisticaTipo
import com.example.imparkapk.data.dao.local.dao.entity.NovosVeiculosPorMes
import com.example.imparkapk.data.dao.local.dao.entity.VeiculoEntity
import com.example.imparkapk.data.dao.local.dao.entity.VeiculoEstatisticasResult
import com.example.imparkapk.data.dao.model.VeiculoEstatisticas
import kotlinx.coroutines.flow.Flow

@Dao
interface VeiculoDao {

    // ========== OPERAÇÕES CRUD BÁSICAS ==========

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserirVeiculo(veiculo: VeiculoEntity): Long

    @Update
    suspend fun atualizarVeiculo(veiculo: VeiculoEntity): Int

    @Delete
    suspend fun deletarVeiculo(veiculo: VeiculoEntity): Int

    @Query("DELETE FROM veiculos WHERE id = :id")
    suspend fun deletarVeiculoPorId(id: String): Int

    @Query("UPDATE veiculos SET ativo = 0 WHERE id = :id")
    suspend fun desativarVeiculo(id: String): Int

    @Query("UPDATE veiculos SET ativo = 1 WHERE id = :id")
    suspend fun ativarVeiculo(id: String): Int

    // ========== CONSULTAS POR ID ==========

    @Query("SELECT * FROM veiculos WHERE id = :id")
    suspend fun obterVeiculoPorId(id: String): VeiculoEntity?

    @Query("SELECT * FROM veiculos WHERE id = :id")
    fun observarVeiculoPorId(id: String): Flow<VeiculoEntity?>

    @Query("SELECT * FROM veiculos WHERE usuario_id = :usuarioId AND placa = :placa")
    suspend fun obterVeiculoPorUsuarioEPLaca(usuarioId: String, placa: String): VeiculoEntity?

    // ========== CONSULTAS POR USUÁRIO ==========

    @Query("SELECT * FROM veiculos WHERE usuario_id = :usuarioId ORDER BY data_cadastro DESC")
    suspend fun obterVeiculosPorUsuario(usuarioId: String): List<VeiculoEntity>

    @Query("SELECT * FROM veiculos WHERE usuario_id = :usuarioId ORDER BY data_cadastro DESC")
    fun observarVeiculosPorUsuario(usuarioId: String): Flow<List<VeiculoEntity>>

    @Query("SELECT * FROM veiculos WHERE usuario_id = :usuarioId AND ativo = 1 ORDER BY data_cadastro DESC")
    suspend fun obterVeiculosAtivosPorUsuario(usuarioId: String): List<VeiculoEntity>

    @Query("SELECT * FROM veiculos WHERE usuario_id = :usuarioId AND ativo = 1 ORDER BY data_cadastro DESC")
    fun observarVeiculosAtivosPorUsuario(usuarioId: String): Flow<List<VeiculoEntity>>

    // ========== CONSULTAS POR PLACA ==========

    @Query("SELECT * FROM veiculos WHERE placa = :placa")
    suspend fun obterVeiculoPorPlaca(placa: String): VeiculoEntity?

    @Query("SELECT * FROM veiculos WHERE placa LIKE '%' || :placa || '%' ORDER BY placa")
    suspend fun buscarVeiculosPorPlaca(placa: String): List<VeiculoEntity>

    @Query("SELECT * FROM veiculos WHERE placa IN (:placas)")
    suspend fun obterVeiculosPorPlacas(placas: List<String>): List<VeiculoEntity>

    // ========== CONSULTAS POR ESTACIONAMENTO ==========

    @Query(
        """
        SELECT v.* FROM veiculos v
        INNER JOIN acessos a ON v.id = a.veiculo_id
        WHERE a.estacionamento_id = :estacionamentoId
        AND a.status = 'ATIVO'
        GROUP BY v.id
    """
    )
    suspend fun obterVeiculosAtivosNoEstacionamento(estacionamentoId: String): List<VeiculoEntity>

    @Query(
        """
        SELECT v.* FROM veiculos v
        INNER JOIN acessos a ON v.id = a.veiculo_id
        WHERE a.estacionamento_id = :estacionamentoId
        GROUP BY v.id
        ORDER BY COUNT(a.id) DESC
        LIMIT :limit
    """
    )
    suspend fun obterVeiculosFrequentesNoEstacionamento(
        estacionamentoId: String,
        limit: Int = 10
    ): List<VeiculoEntity>

    // ========== CONSULTAS POR STATUS ==========

    @Query("SELECT * FROM veiculos WHERE ativo = :ativo ORDER BY data_cadastro DESC")
    suspend fun obterVeiculosPorStatus(ativo: Boolean): List<VeiculoEntity>

    @Query("SELECT * FROM veiculos WHERE ativo = 1 ORDER BY data_cadastro DESC")
    suspend fun obterTodosVeiculosAtivos(): List<VeiculoEntity>

    @Query("SELECT * FROM veiculos WHERE ativo = 1 ORDER BY data_cadastro DESC")
    fun observarTodosVeiculosAtivos(): Flow<List<VeiculoEntity>>

    // ========== CONSULTAS POR TIPO/MODELO/MARCA ==========

    @Query("SELECT * FROM veiculos WHERE tipo = :tipo AND ativo = 1 ORDER BY modelo")
    suspend fun obterVeiculosPorTipo(tipo: String): List<VeiculoEntity>

    @Query("SELECT * FROM veiculos WHERE marca = :marca AND ativo = 1 ORDER BY modelo")
    suspend fun obterVeiculosPorMarca(marca: String): List<VeiculoEntity>

    @Query("SELECT * FROM veiculos WHERE modelo LIKE '%' || :modelo || '%' AND ativo = 1 ORDER BY modelo")
    suspend fun buscarVeiculosPorModelo(modelo: String): List<VeiculoEntity>

    @Query("SELECT * FROM veiculos WHERE cor = :cor AND ativo = 1 ORDER BY modelo")
    suspend fun obterVeiculosPorCor(cor: String): List<VeiculoEntity>

    // ========== CONSULTAS DE BUSCA ==========

    @Query(
        """
        SELECT * FROM veiculos 
        WHERE (placa LIKE '%' || :termo || '%' 
        OR modelo LIKE '%' || :termo || '%' 
        OR marca LIKE '%' || :termo || '%')
        AND ativo = 1
        ORDER BY 
            CASE WHEN placa LIKE :termo || '%' THEN 1
                 WHEN modelo LIKE :termo || '%' THEN 2
                 WHEN marca LIKE :termo || '%' THEN 3
                 ELSE 4
            END,
            placa
    """
    )
    suspend fun buscarVeiculos(termo: String): List<VeiculoEntity>

    // ========== CONSULTAS ESTATÍSTICAS ==========

    @Query("SELECT COUNT(*) FROM veiculos WHERE usuario_id = :usuarioId")
    suspend fun contarVeiculosPorUsuario(usuarioId: String): Int

    @Query("SELECT COUNT(*) FROM veiculos WHERE usuario_id = :usuarioId AND ativo = 1")
    suspend fun contarVeiculosAtivosPorUsuario(usuarioId: String): Int

    @Query("SELECT COUNT(*) FROM veiculos")
    suspend fun contarTotalVeiculos(): Int

    @Query("SELECT COUNT(*) FROM veiculos WHERE ativo = 1")
    suspend fun contarVeiculosAtivos(): Int

    @Query(
        """
        SELECT tipo, COUNT(*) as quantidade 
        FROM veiculos 
        WHERE ativo = 1
        GROUP BY tipo
        ORDER BY quantidade DESC
    """
    )
    suspend fun obterEstatisticasPorTipo(): List<EstatisticaTipo>

    @Query(
        """
        SELECT marca, COUNT(*) as quantidade 
        FROM veiculos 
        WHERE ativo = 1
        GROUP BY marca
        ORDER BY quantidade DESC
        LIMIT 10
    """
    )
    suspend fun obterTopMarcas(): List<EstatisticaMarca>

    // ========== CONSULTAS DE VERIFICAÇÃO ==========

    @Query("SELECT EXISTS(SELECT 1 FROM veiculos WHERE placa = :placa)")
    suspend fun verificarPlacaExistente(placa: String): Boolean

    @Query("SELECT EXISTS(SELECT 1 FROM veiculos WHERE placa = :placa AND usuario_id = :usuarioId)")
    suspend fun verificarPlacaExistenteParaUsuario(placa: String, usuarioId: String): Boolean

    @Query("SELECT EXISTS(SELECT 1 FROM veiculos WHERE id = :id AND ativo = 1)")
    suspend fun verificarVeiculoAtivo(id: String): Boolean

    @Query(
        """
        SELECT EXISTS(
            SELECT 1 FROM veiculos v
            INNER JOIN acessos a ON v.id = a.veiculo_id
            WHERE v.id = :veiculoId 
            AND a.status = 'ATIVO'
        )
    """
    )
    suspend fun verificarVeiculoComAcessoAtivo(veiculoId: String): Boolean

    // ========== CONSULTAS PARA SINCRONIZAÇÃO ==========

    @Query("SELECT * FROM veiculos WHERE sincronizado = 0")
    suspend fun obterVeiculosNaoSincronizados(): List<VeiculoEntity>

    @Query("UPDATE veiculos SET sincronizado = 1 WHERE id = :id")
    suspend fun marcarVeiculoComoSincronizado(id: String): Int

    @Query("UPDATE veiculos SET sincronizado = 1 WHERE id IN (:ids)")
    suspend fun marcarVeiculosComoSincronizadosEmLote(ids: List<String>): Int

    @Query("SELECT * FROM veiculos WHERE data_atualizacao > :dataLimite")
    suspend fun obterVeiculosModificadosApos(dataLimite: Long): List<VeiculoEntity>

    // ========== OPERAÇÕES EM LOTE ==========

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserirVeiculosEmLote(veiculos: List<VeiculoEntity>): List<Long>

    @Update
    suspend fun atualizarVeiculosEmLote(veiculos: List<VeiculoEntity>): Int

    @Query("DELETE FROM veiculos WHERE id IN (:ids)")
    suspend fun deletarVeiculosEmLote(ids: List<String>): Int

    // ========== CONSULTAS PARA RELATÓRIOS ==========

    @Query(
        """
        SELECT 
            strftime('%Y-%m', data_cadastro/1000, 'unixepoch') as mes,
            COUNT(*) as novos_veiculos
        FROM veiculos 
        WHERE data_cadastro >= :dataInicio
        GROUP BY strftime('%Y-%m', data_cadastro/1000, 'unixepoch')
        ORDER BY mes
    """
    )
    suspend fun obterNovosVeiculosPorMes(dataInicio: Long): List<NovosVeiculosPorMes>

    @Query(
        """
        SELECT 
            v.*,
            COUNT(a.id) as total_acessos,
            SUM(COALESCE(a.valor, 0)) as total_gasto
        FROM veiculos v
        LEFT JOIN acessos a ON v.id = a.veiculo_id
        WHERE v.usuario_id = :usuarioId
        GROUP BY v.id
        ORDER BY total_acessos DESC
    """
    )
    // ERRADO - Faltando @Query
    suspend fun obterVeiculosComEstatisticasPorUsuario(usuarioId: String): List<VeiculoEstatisticas>

    // CORRETO - Com @Query
    @Query("""
    SELECT 
        veiculo_id as veiculoId,
        COUNT(*) as totalAcessos,
        SUM(COALESCE(valor, 0)) as totalGasto
    FROM acessos 
    WHERE usuario_id = :usuarioId
    GROUP BY veiculo_id
""")
    suspend fun obterEstatisticasVeiculosPorUsuario(usuarioId: String): List<VeiculoEstatisticasResult>

    @Transaction
    @Query("...")
    suspend fun obterVeiculosComEstatisticasCompletasPorUsuario(
        usuarioId: String
    ): List<VeiculoEstatisticas>
}