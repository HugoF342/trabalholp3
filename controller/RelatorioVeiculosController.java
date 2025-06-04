package controller;

import dao.RelatorioDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.RelatorioVeiculo;

import java.net.URL;
import java.util.ResourceBundle;

public class RelatorioVeiculosController implements Initializable {

    @FXML private TableView<RelatorioVeiculo> tabelaVeiculos;
    @FXML private TableColumn<RelatorioVeiculo, Integer> colId;
    @FXML private TableColumn<RelatorioVeiculo, String> colMarca;
    @FXML private TableColumn<RelatorioVeiculo, String> colModelo;
    @FXML private TableColumn<RelatorioVeiculo, String> colPlaca;
    @FXML private TableColumn<RelatorioVeiculo, Integer> colAno;
    @FXML private TableColumn<RelatorioVeiculo, String> colCor;
    @FXML private TableColumn<RelatorioVeiculo, String> colCategoria;
    @FXML private TableColumn<RelatorioVeiculo, Integer> colKm;
    @FXML private TableColumn<RelatorioVeiculo, String> colStatus;
    @FXML private TableColumn<RelatorioVeiculo, String> colDevolucao;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        colPlaca.setCellValueFactory(new PropertyValueFactory<>("placa"));
        colAno.setCellValueFactory(new PropertyValueFactory<>("ano"));
        colCor.setCellValueFactory(new PropertyValueFactory<>("cor"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colKm.setCellValueFactory(new PropertyValueFactory<>("quilometragem"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colDevolucao.setCellValueFactory(new PropertyValueFactory<>("dataPrevistaDevolucao")); // Corrigido

        carregarRelatorio();
    }

    private void carregarRelatorio() {
        tabelaVeiculos.getItems().clear(); // Evita duplicação

        RelatorioDAO dao = new RelatorioDAO();
        ObservableList<RelatorioVeiculo> lista = FXCollections.observableArrayList(dao.buscarRelatorioVeiculos());

        tabelaVeiculos.setItems(lista);
    }
}
