package controller;

import dao.RelatorioDevolucaoDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.RelatorioDevolucao;

import java.net.URL;
import java.util.ResourceBundle;

public class RelatorioDevolucaoController implements Initializable {

    @FXML private TableView<RelatorioDevolucao> tabelaRelatorio;
    @FXML private TableColumn<RelatorioDevolucao, String> colunaMarca;
    @FXML private TableColumn<RelatorioDevolucao, String> colunaModelo;
    @FXML private TableColumn<RelatorioDevolucao, String> colunaPlaca;
    @FXML private TableColumn<RelatorioDevolucao, String> colunaNomeCliente;
    @FXML private TableColumn<RelatorioDevolucao, String> colunaCpfCliente;
    @FXML private TableColumn<RelatorioDevolucao, Double> colunaMultas;
    @FXML private TableColumn<RelatorioDevolucao, String> colunaDataPrevista;
    @FXML private TableColumn<RelatorioDevolucao, String> colunaDataReal;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colunaMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colunaModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        colunaPlaca.setCellValueFactory(new PropertyValueFactory<>("placa"));
        colunaNomeCliente.setCellValueFactory(new PropertyValueFactory<>("nomeCliente"));
        colunaCpfCliente.setCellValueFactory(new PropertyValueFactory<>("cpfCliente"));
        colunaMultas.setCellValueFactory(new PropertyValueFactory<>("valorMulta"));
        colunaDataPrevista.setCellValueFactory(new PropertyValueFactory<>("dataPrevista"));
        colunaDataReal.setCellValueFactory(new PropertyValueFactory<>("dataReal"));

        tabelaRelatorio.getItems().addAll(RelatorioDevolucaoDAO.buscarRelatorioDevolucoes());
    }
}
