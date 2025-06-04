package controller;

import dao.ClienteDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import model.Cliente;

public class CadastroClienteController {

    @FXML private TextField txtNome;
    @FXML private TextField txtCpf;
    @FXML private TextField txtCnh;
    @FXML private TextField txtEndereco;
    @FXML private TextField txtTelefone;
    @FXML private TextField txtEmail;

    @FXML
    public void cadastrarCliente() {
        Cliente cliente = new Cliente(
                txtNome.getText(),
                txtCpf.getText(),
                txtCnh.getText(),
                txtEndereco.getText(),
                txtTelefone.getText(),
                txtEmail.getText()
        );

        ClienteDAO dao = new ClienteDAO();
        dao.inserir(cliente);

        limparCampos();


        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("Cliente cadastrado com sucesso!");
        alert.showAndWait();
    }

    private void limparCampos() {
        txtNome.clear();
        txtCpf.clear();
        txtCnh.clear();
        txtEndereco.clear();
        txtTelefone.clear();
        txtEmail.clear();
    }
}
