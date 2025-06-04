package controller;

import dao.ClienteDAO;
import dao.VeiculoDAO;
import dao.LocacaoDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Cliente;
import model.Veiculo;
import model.Locacao;

import java.time.LocalDate;

public class LocacaoController {

    @FXML
    private ComboBox<Cliente> clienteComboBox;
    @FXML
    private ComboBox<Veiculo> veiculoComboBox;
    @FXML
    private DatePicker dataRetiradaPicker;
    @FXML
    private DatePicker dataDevolucaoPicker;
    @FXML
    private TextField valorTextField;

    private ClienteDAO clienteDAO = new ClienteDAO();
    private VeiculoDAO veiculoDAO = new VeiculoDAO();
    private LocacaoDAO locacaoDAO = new LocacaoDAO();

    @FXML
    public void initialize() {
        clienteComboBox.getItems().addAll(clienteDAO.buscarTodos());
        veiculoComboBox.getItems().addAll(veiculoDAO.buscarDisponiveis()); // Apenas veículos disponíveis
    }

    @FXML
    public void registrarLocacao() {
        Cliente cliente = clienteComboBox.getValue();
        Veiculo veiculo = veiculoComboBox.getValue();
        LocalDate retirada = dataRetiradaPicker.getValue();
        LocalDate devolucao = dataDevolucaoPicker.getValue();

        double valor = 0;
        try {
            valor = Double.parseDouble(valorTextField.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Valor inválido.");
            alert.showAndWait();
            return;
        }

        if (cliente != null && veiculo != null && retirada != null && devolucao != null) {
            Locacao locacao = new Locacao(0, cliente, veiculo, retirada, devolucao, valor);
            locacaoDAO.salvar(locacao);

            veiculo.setStatus("Alugado");
            veiculoDAO.atualizar(veiculo);

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Locação registrada com sucesso!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Preencha todos os campos.");
            alert.showAndWait();
        }
    }
}
