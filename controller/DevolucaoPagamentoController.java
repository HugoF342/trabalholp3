package controller;

import dao.LocacaoDAO;
import dao.RelatorioDAO;
import dao.VeiculoDAO;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.RelatorioVeiculo;

public class DevolucaoPagamentoController {

    @FXML private ComboBox<RelatorioVeiculo> cbVeiculo;
    @FXML private DatePicker dpDataDevolucao;
    @FXML private TextField tfMultas;
    @FXML private TextField tfQuilometragem;
    @FXML private Label lblValorTotal;

    private ObservableList<RelatorioVeiculo> veiculosAlugados;
    private double valorAcordado = 0.0;

    @FXML
    public void initialize() {
        veiculosAlugados = FXCollections.observableArrayList(new RelatorioDAO().buscarVeiculosAlugados());
        cbVeiculo.setItems(veiculosAlugados);

        if (!veiculosAlugados.isEmpty()) {
            cbVeiculo.getSelectionModel().selectFirst();
            valorAcordado = cbVeiculo.getValue().getValorAcordado();
            atualizarValorTotal();
        }

        cbVeiculo.getSelectionModel().selectedItemProperty().addListener((obs, antigo, novo) -> {
            if (novo != null) {
                valorAcordado = novo.getValorAcordado();
                atualizarValorTotal();
            }
        });

        tfMultas.textProperty().addListener((obs, antigo, novo) -> atualizarValorTotal());

        // Exibir marca + modelo + placa + data prevista no ComboBox
        cbVeiculo.setCellFactory(lv -> new ListCell<RelatorioVeiculo>() {
            @Override
            protected void updateItem(RelatorioVeiculo item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getMarca() + " " + item.getModelo()
                            + " - " + item.getPlaca()
                            + " - Previsto: " + item.getDataPrevistaDevolucao());
                }
            }
        });

        cbVeiculo.setButtonCell(new ListCell<RelatorioVeiculo>() {
            @Override
            protected void updateItem(RelatorioVeiculo item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getMarca() + " " + item.getModelo()
                            + " - " + item.getPlaca()
                            + " - Previsto: " + item.getDataPrevistaDevolucao());
                }
            }
        });
    }

    private void atualizarValorTotal() {
        double multa = parseDoubleSafe(tfMultas.getText());
        double valorTotal = valorAcordado + multa;
        lblValorTotal.setText(String.format("R$ %.2f + R$ %.2f = R$ %.2f", valorAcordado, multa, valorTotal));
    }

    private double parseDoubleSafe(String input) {
        try {
            return input == null || input.isEmpty() ? 0.0 : Double.parseDouble(input);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private Integer parseIntegerSafe(String input) throws NumberFormatException {
        if (input == null || input.isEmpty()) {
            throw new NumberFormatException("Campo vazio");
        }
        return Integer.parseInt(input);
    }

    @FXML
    public void finalizarDevolucao() {
        RelatorioVeiculo veiculo = cbVeiculo.getValue();

        if (veiculo == null || dpDataDevolucao.getValue() == null || tfQuilometragem.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Preencha todos os campos obrigatórios.");
            return;
        }

        double multa;
        try {
            multa = parseDoubleSafe(tfMultas.getText());
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Valor de multa inválido.");
            return;
        }

        int quilometragemRodada;
        try {
            quilometragemRodada = parseIntegerSafe(tfQuilometragem.getText());
            if (quilometragemRodada < 0) throw new NumberFormatException("Quilometragem negativa");
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Quilometragem inválida.");
            return;
        }

        // Buscar a quilometragem atual do veículo no banco
        VeiculoDAO veiculoDAO = new VeiculoDAO();
        int quilometragemAtual = veiculoDAO.buscarQuilometragemPorId(veiculo.getId());

        // Somar quilometragem atual + rodada na devolução
        int quilometragemTotal = quilometragemAtual + quilometragemRodada;

        double valorTotal = valorAcordado + multa;

        new LocacaoDAO().finalizarDevolucao(
                veiculo.getId(),
                dpDataDevolucao.getValue(),
                multa,
                quilometragemTotal,
                valorTotal
        );

        showAlert(Alert.AlertType.INFORMATION, "Devolução finalizada com sucesso!");

        // Limpar os campos após finalização
        cbVeiculo.getSelectionModel().clearSelection();
        tfMultas.clear();
        tfQuilometragem.clear();
        lblValorTotal.setText("");
        dpDataDevolucao.setValue(null);
    }

    private void showAlert(Alert.AlertType tipo, String mensagem) {
        Alert alert = new Alert(tipo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
