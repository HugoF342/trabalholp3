package dao;

import java.time.LocalDate;
import model.Locacao;
import model.Cliente;
import model.Veiculo;
import util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocacaoDAO {

    public void salvar(Locacao locacao) {
        String sql = "INSERT INTO locacao (clientes_id, veiculos_id, data_retirada, data_prevista_devolucao, valor) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, locacao.getCliente().getId());
            stmt.setInt(2, locacao.getVeiculo().getId());
            stmt.setDate(3, Date.valueOf(locacao.getDataRetirada()));
            stmt.setDate(4, Date.valueOf(locacao.getDataPrevistaDevolucao()));
            stmt.setDouble(5, locacao.getValorAcordado());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Locacao> buscarTodos() {
        List<Locacao> locacoes = new ArrayList<>();

        String sql =
                "SELECT l.*, " +
                        "c.id as cliente_id, c.nome as cliente_nome, c.cpf, c.cnh, c.endereco, c.telefone, c.email, " +
                        "v.id as veiculo_id, v.marca, v.modelo, v.placa, v.ano, v.cor, v.categoria, v.quilometragem, v.status " +
                        "FROM locacao l " +
                        "JOIN clientes c ON l.clientes_id = c.id " +
                        "JOIN veiculos v ON l.veiculos_id = v.id";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("cliente_id"));
                cliente.setNome(rs.getString("cliente_nome"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setCnh(rs.getString("cnh"));
                cliente.setEndereco(rs.getString("endereco"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setEmail(rs.getString("email"));

                Veiculo veiculo = new Veiculo();
                veiculo.setId(rs.getInt("veiculo_id"));
                veiculo.setMarca(rs.getString("marca"));
                veiculo.setModelo(rs.getString("modelo"));
                veiculo.setPlaca(rs.getString("placa"));
                veiculo.setAno(rs.getInt("ano"));
                veiculo.setCor(rs.getString("cor"));
                veiculo.setCategoria(rs.getString("categoria"));
                veiculo.setQuilometragem(rs.getInt("quilometragem"));
                veiculo.setStatus(rs.getString("status"));

                Locacao locacao = new Locacao();
                locacao.setId(rs.getInt("id"));
                locacao.setCliente(cliente);
                locacao.setVeiculo(veiculo);
                locacao.setDataRetirada(rs.getDate("data_retirada").toLocalDate());
                locacao.setDataPrevistaDevolucao(rs.getDate("data_prevista_devolucao").toLocalDate());

                // Valores que podem ser nulos no banco
                locacao.setValorAcordado(rs.getDouble("valor"));
                locacao.setValorMultas(rs.getObject("valor_multas") != null ? rs.getDouble("valor_multas") : 0.0);
                locacao.setValorTotal(rs.getObject("valor_total") != null ? rs.getDouble("valor_total") : 0.0);

                if (rs.getDate("data_devolucao") != null) {
                    locacao.setDataDevolucao(rs.getDate("data_devolucao").toLocalDate());
                }

                locacoes.add(locacao);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return locacoes;
    }

    public void finalizarDevolucao(int idVeiculo, LocalDate dataDevolucao, double multas, int novaKm, double valorTotal) {
        String selectSql = "SELECT valor FROM locacao WHERE veiculos_id = ? AND data_devolucao IS NULL";
        String updateLocacaoSql = "UPDATE locacao SET data_devolucao = ?, valor_multas = ?, valor_total = ? WHERE veiculos_id = ? AND data_devolucao IS NULL";
        String updateVeiculoSql = "UPDATE veiculos SET quilometragem = ?, status = 'Disponível' WHERE id = ?";

        Connection conn = null;
        try {
            conn = Conexao.getConnection();
            conn.setAutoCommit(false); // Iniciar transação

            double valorAcordado = 0.0;

            try (PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {
                selectStmt.setInt(1, idVeiculo);
                try (ResultSet rs = selectStmt.executeQuery()) {
                    if (rs.next()) {
                        valorAcordado = rs.getDouble("valor");
                    }
                }
            }

            double valorFinal = valorAcordado + multas;

            try (PreparedStatement updateLocacaoStmt = conn.prepareStatement(updateLocacaoSql)) {
                updateLocacaoStmt.setDate(1, Date.valueOf(dataDevolucao));
                updateLocacaoStmt.setDouble(2, multas);
                updateLocacaoStmt.setDouble(3, valorFinal);
                updateLocacaoStmt.setInt(4, idVeiculo);
                updateLocacaoStmt.executeUpdate();
            }

            try (PreparedStatement updateVeiculoStmt = conn.prepareStatement(updateVeiculoSql)) {
                updateVeiculoStmt.setInt(1, novaKm);
                updateVeiculoStmt.setInt(2, idVeiculo);
                updateVeiculoStmt.executeUpdate();
            }

            conn.commit(); // Confirma a transação

        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback(); // Reverte transação em caso de erro
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
