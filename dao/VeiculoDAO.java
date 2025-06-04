package dao;

import model.Veiculo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VeiculoDAO {

    public void inserir(Veiculo veiculo) {
        String sql = "INSERT INTO veiculos (marca, modelo, placa, ano, cor, categoria, quilometragem, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, veiculo.getMarca());
            stmt.setString(2, veiculo.getModelo());
            stmt.setString(3, veiculo.getPlaca());
            stmt.setInt(4, veiculo.getAno());
            stmt.setString(5, veiculo.getCor());
            stmt.setString(6, veiculo.getCategoria());
            stmt.setInt(7, veiculo.getQuilometragem());
            stmt.setString(8, veiculo.getStatus());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao inserir veículo: " + e.getMessage());
        }
    }

    public List<Veiculo> buscarTodos() {
        List<Veiculo> lista = new ArrayList<>();
        String sql = "SELECT * FROM veiculos";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Veiculo v = new Veiculo();
                v.setId(rs.getInt("id"));
                v.setMarca(rs.getString("marca"));
                v.setModelo(rs.getString("modelo"));
                v.setPlaca(rs.getString("placa"));
                v.setAno(rs.getInt("ano"));
                v.setCor(rs.getString("cor"));
                v.setCategoria(rs.getString("categoria"));
                v.setQuilometragem(rs.getInt("quilometragem"));
                v.setStatus(rs.getString("status"));

                lista.add(v);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar veículos: " + e.getMessage());
        }

        return lista;
    }

    public List<Veiculo> buscarDisponiveis() {
        List<Veiculo> disponiveis = new ArrayList<>();
        String sql = "SELECT * FROM veiculos WHERE status = 'Disponível'";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Veiculo v = new Veiculo();
                v.setId(rs.getInt("id"));
                v.setMarca(rs.getString("marca"));
                v.setModelo(rs.getString("modelo"));
                v.setPlaca(rs.getString("placa"));
                v.setAno(rs.getInt("ano"));
                v.setCor(rs.getString("cor"));
                v.setCategoria(rs.getString("categoria"));
                v.setQuilometragem(rs.getInt("quilometragem"));
                v.setStatus(rs.getString("status"));

                disponiveis.add(v);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar veículos disponíveis: " + e.getMessage());
        }

        return disponiveis;
    }

    public void atualizar(Veiculo veiculo) {
        String sql = "UPDATE veiculos SET marca = ?, modelo = ?, placa = ?, ano = ?, cor = ?, categoria = ?, quilometragem = ?, status = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, veiculo.getMarca());
            stmt.setString(2, veiculo.getModelo());
            stmt.setString(3, veiculo.getPlaca());
            stmt.setInt(4, veiculo.getAno());
            stmt.setString(5, veiculo.getCor());
            stmt.setString(6, veiculo.getCategoria());
            stmt.setInt(7, veiculo.getQuilometragem());
            stmt.setString(8, veiculo.getStatus());
            stmt.setInt(9, veiculo.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar veículo: " + e.getMessage());
        }
    }

    //  NOVO MÉTODO: soma quilometragem rodada à atual e atualiza status
    public void atualizarQuilometragemSomando(int veiculoId, int quilometragemRodada) {
        String selectSql = "SELECT quilometragem FROM veiculos WHERE id = ?";
        String updateSql = "UPDATE veiculos SET quilometragem = ?, status = 'Disponível' WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement selectStmt = conn.prepareStatement(selectSql);
             PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {

            // Buscar quilometragem atual
            selectStmt.setInt(1, veiculoId);
            ResultSet rs = selectStmt.executeQuery();

            if (rs.next()) {
                int atual = rs.getInt("quilometragem");
                int novaQuilometragem = atual + quilometragemRodada;

                // Atualizar com nova quilometragem somada
                updateStmt.setInt(1, novaQuilometragem);
                updateStmt.setInt(2, veiculoId);
                updateStmt.executeUpdate();
            }

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar quilometragem do veículo: " + e.getMessage());
        }
    }
    public int buscarQuilometragemPorId(int id) {
        String sql = "SELECT quilometragem FROM veiculos WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("quilometragem");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar quilometragem: " + e.getMessage());
        }
        return 0;
    }

}
