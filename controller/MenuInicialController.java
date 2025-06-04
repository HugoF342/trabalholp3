package controller;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class MenuInicialController {

    public void abrirCadastroCliente(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/cadastro_cliente.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Cadastro de Cliente");
            stage.setScene(new Scene(root, 400, 400));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void abrirCadastroVeiculo(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/cadastro_veiculo.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Cadastro de Veículo");
            stage.setScene(new Scene(root, 400, 500));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirTelaLocacao(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/locacao.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Locação de Veículo");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirRelatorio() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/relatorio_locacoes.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Relatório de Locações");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirRelatorioVeiculos() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/relatorio_veiculos.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Relatório de Veículos");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirDevolucaoPagamento() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/devolucao_pagamento.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Devolução e Pagamento");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void abrirRelatorioDevolucoes() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/relatorio_devolucao.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Relatório de Devoluções");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
