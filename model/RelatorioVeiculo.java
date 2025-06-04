package model;

public class RelatorioVeiculo {
    private int id;
    private String marca;
    private String modelo;
    private String placa;
    private int ano;
    private String cor;
    private String categoria;
    private int quilometragem;
    private String status;
    private String dataPrevistaDevolucao;
    private double valorAcordado;

    // Construtor original (sem valorAcordado)
    public RelatorioVeiculo(int id, String marca, String modelo, String placa, int ano,
                            String cor, String categoria, int quilometragem,
                            String status, String dataPrevistaDevolucao) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.placa = placa;
        this.ano = ano;
        this.cor = cor;
        this.categoria = categoria;
        this.quilometragem = quilometragem;
        this.status = status;
        this.dataPrevistaDevolucao = dataPrevistaDevolucao;
        this.valorAcordado = 0.0; // padrão zero
    }

    // Novo construtor incluindo valorAcordado
    public RelatorioVeiculo(int id, String marca, String modelo, String placa, int ano,
                            String cor, String categoria, int quilometragem,
                            String status, String dataPrevistaDevolucao,
                            double valorAcordado) {
        this(id, marca, modelo, placa, ano, cor, categoria, quilometragem, status, dataPrevistaDevolucao);
        this.valorAcordado = valorAcordado;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public String getPlaca() {
        return placa;
    }

    public int getAno() {
        return ano;
    }

    public String getCor() {
        return cor;
    }

    public String getCategoria() {
        return categoria;
    }

    public int getQuilometragem() {
        return quilometragem;
    }

    public String getStatus() {
        return status;
    }

    public String getDataPrevistaDevolucao() {
        return dataPrevistaDevolucao;
    }

    public double getValorAcordado() {
        return valorAcordado;
    }

    // Setters
    public void setQuilometragem(int quilometragem) {
        this.quilometragem = quilometragem;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDataPrevistaDevolucao(String dataPrevistaDevolucao) {
        this.dataPrevistaDevolucao = dataPrevistaDevolucao;
    }

    public void setValorAcordado(double valorAcordado) {
        this.valorAcordado = valorAcordado;
    }

    // toString para exibição no ComboBox
    @Override
    public String toString() {
        return marca + " " + modelo + " - " + placa;
    }
}
