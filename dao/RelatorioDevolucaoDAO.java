package dao;

import util.Conexao;
import model.RelatorioDevolucao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RelatorioDevolucaoDAO {

    public static List<RelatorioDevolucao> buscarRelatorioDevolucoes() {
        List<RelatorioDevolucao> lista = new ArrayList<>();

        String sql = "SELECT v.marca, v.modelo, v.placa, c.nome, c.cpf, l.valor_multas, " +
                "l.data_prevista_devolucao, l.data_devolucao, l.valor_total " +
                "FROM locacao l " +
                "JOIN veiculos v ON l.veiculos_id = v.id " +
                "JOIN clientes c ON l.clientes_id = c.id " +
                "WHERE l.data_devolucao IS NOT NULL";

        try (Connection conn = Conexao.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                RelatorioDevolucao relatorio = new RelatorioDevolucao(
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getString("placa"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getDouble("valor_multas"),
                        rs.getDate("data_prevista_devolucao").toLocalDate(),
                        rs.getDate("data_devolucao").toLocalDate(),
                        rs.getDouble("valor_total")
                );
                lista.add(relatorio);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
