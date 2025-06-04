package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;

public class LoginController {
    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtSenha;
    @FXML private Label lblMensagem;

    @FXML
    private void entrar() {
        String usuario = txtUsuario.getText();
        String senha = txtSenha.getText();

        if (usuario.equals("admin") && senha.equals("1234")) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/menu_inicial.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setTitle("Menu Inicial");
                stage.setScene(new Scene(root));
                stage.show();

                // Fecha a janela de login
                Stage loginStage = (Stage) txtUsuario.getScene().getWindow();
                loginStage.close();
            } catch (Exception e) {
                lblMensagem.setText("Erro ao abrir o menu.");
                e.printStackTrace();
            }
        } else {
            lblMensagem.setText("Usuário ou senha inválidos.");
        }
    }
}
