package controller;

import dao.LocacaoDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Locacao;

import java.time.format.DateTimeFormatter;
import javafx.scene.control.cell.PropertyValueFactory;

public class RelatorioController {

    @FXML
    private TableView<Locacao> tabelaRelatorio;

    @FXML
    private TableColumn<Locacao, String> colCliente;

    @FXML
    private TableColumn<Locacao, String> colVeiculo;

    @FXML
    private TableColumn<Locacao, String> colDataInicio;

    @FXML
    private TableColumn<Locacao, String> colDataFim;

    @FXML
    private TableColumn<Locacao, Double> colValor;

    private LocacaoDAO locacaoDAO = new LocacaoDAO();

    @FXML
    public void initialize() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        colCliente.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCliente().getNome()));

        colVeiculo.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(
                        cellData.getValue().getVeiculo().getMarca() + " " +
                                cellData.getValue().getVeiculo().getModelo() + " - " +
                                cellData.getValue().getVeiculo().getPlaca()
                ));

        colDataInicio.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDataRetirada().format(formatter)));

        colDataFim.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDataPrevistaDevolucao().format(formatter)));

        colValor.setCellValueFactory(new PropertyValueFactory<>("valorAcordado"));

        ObservableList<Locacao> lista = FXCollections.observableArrayList(locacaoDAO.buscarTodos());
        tabelaRelatorio.setItems(lista);
    }
}
