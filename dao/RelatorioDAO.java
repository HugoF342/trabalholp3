package dao;

import model.RelatorioVeiculo;
import util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RelatorioDAO {

    // Metodo para buscar todos os veículos para o relatório, sem duplicação
    public List<RelatorioVeiculo> buscarRelatorioVeiculos() {
        List<RelatorioVeiculo> lista = new ArrayList<>();

        String sql =
                "SELECT v.id, v.marca, v.modelo, v.placa, v.ano, v.cor, " +
                        "v.categoria, v.quilometragem, v.status, " +
                        "l.data_prevista_devolucao " +
                        "FROM veiculos v " +
                        "LEFT JOIN locacao l ON v.id = l.veiculos_id AND l.data_devolucao IS NULL " +
                        "WHERE l.id IS NULL OR l.id = (" +
                        "    SELECT MAX(id) FROM locacao WHERE veiculos_id = v.id AND data_devolucao IS NULL" +
                        ")";

        try (Connection conn = Conexao.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                RelatorioVeiculo rv = new RelatorioVeiculo(
                        rs.getInt("id"),
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getString("placa"),
                        rs.getInt("ano"),
                        rs.getString("cor"),
                        rs.getString("categoria"),
                        rs.getInt("quilometragem"),
                        rs.getString("status"),
                        rs.getString("data_prevista_devolucao")
                );
                lista.add(rv);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // Metodo para buscar veículos alugados, incluindo valor_acordado
    public List<RelatorioVeiculo> buscarVeiculosAlugados() {
        List<RelatorioVeiculo> veiculos = new ArrayList<>();
        String sql =
                "SELECT v.id, v.marca, v.modelo, v.placa, v.ano, v.cor, " +
                        "v.categoria, v.quilometragem, v.status, " +
                        "l.data_prevista_devolucao, l.valor " +
                        "FROM veiculos v " +
                        "JOIN locacao l ON v.id = l.veiculos_id " +
                        "WHERE v.status = 'alugado'";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                RelatorioVeiculo veiculo = new RelatorioVeiculo(
                        rs.getInt("id"),
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getString("placa"),
                        rs.getInt("ano"),
                        rs.getString("cor"),
                        rs.getString("categoria"),
                        rs.getInt("quilometragem"),
                        rs.getString("status"),
                        rs.getString("data_prevista_devolucao"),
                        rs.getDouble("valor")
                );
                veiculos.add(veiculo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return veiculos;
    }
}
