package model;

import java.time.LocalDate;

public class RelatorioDevolucao {
    private String marca;
    private String modelo;
    private String placa;
    private String nomeCliente;
    private String cpfCliente;
    private double valorMulta;
    private LocalDate dataPrevista;
    private LocalDate dataReal;
    private double valorTotal;  // novo campo

    // Construtor atualizado com valorTotal
    public RelatorioDevolucao(String marca, String modelo, String placa, String nomeCliente, String cpfCliente,
                              double valorMulta, LocalDate dataPrevista, LocalDate dataReal, double valorTotal) {
        this.marca = marca;
        this.modelo = modelo;
        this.placa = placa;
        this.nomeCliente = nomeCliente;
        this.cpfCliente = cpfCliente;
        this.valorMulta = valorMulta;
        this.dataPrevista = dataPrevista;
        this.dataReal = dataReal;
        this.valorTotal = valorTotal;
    }

    // Getters
    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public String getPlaca() { return placa; }
    public String getNomeCliente() { return nomeCliente; }
    public String getCpfCliente() { return cpfCliente; }
    public double getValorMulta() { return valorMulta; }
    public LocalDate getDataPrevista() { return dataPrevista; }
    public LocalDate getDataReal() { return dataReal; }
    public double getValorTotal() { return valorTotal; }  // getter novo
}
