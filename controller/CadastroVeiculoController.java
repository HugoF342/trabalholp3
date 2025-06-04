package controller;

import dao.VeiculoDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import model.Veiculo;

public class CadastroVeiculoController {

    @FXML private TextField txtMarca;
    @FXML private TextField txtModelo;
    @FXML private TextField txtPlaca;
    @FXML private TextField txtAno;
    @FXML private TextField txtCor;
    @FXML private TextField txtCategoria;
    @FXML private TextField txtQuilometragem;

    @FXML
    public void cadastrarVeiculo() {
        Veiculo veiculo = new Veiculo(
                txtMarca.getText(),
                txtModelo.getText(),
                txtPlaca.getText(),
                Integer.parseInt(txtAno.getText()),
                txtCor.getText(),
                txtCategoria.getText(),
                Integer.parseInt(txtQuilometragem.getText()),
                "Disponível" // status padrão fixo
        );

        VeiculoDAO dao = new VeiculoDAO();
        dao.inserir(veiculo);

        limparCampos();


        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("Veículo cadastrado com sucesso!");
        alert.showAndWait();
    }

    private void limparCampos() {
        txtMarca.clear();
        txtModelo.clear();
        txtPlaca.clear();
        txtAno.clear();
        txtCor.clear();
        txtCategoria.clear();
        txtQuilometragem.clear();
    }
}
