package ads.pi4.model;

import java.text.NumberFormat;
import java.util.Locale;

public class Produto {
    //Atributos
    private Integer enabled;
    private Integer id;
    private String codigo;
    private String nome;
    private String marca;
    private String imagem;
    private double preco_original;
    private double preco_venda;
    private int quantidade;
    private String categoria;
    private String descricao;

    public double getPrecoOriginal() {
        return preco_original;
    }
    
    public String getPrecoOriginalFormatado() {
        Locale ptBr = new Locale("pt", "BR");        
        return NumberFormat.getCurrencyInstance(ptBr).format(this.preco_original).replace("R$ ", "");
    }

    public void setPrecoOriginal(double preco_original) {
        this.preco_original = preco_original;
    }

    public double getPrecoVenda() {
        return preco_venda;
    }
    
    public String getPrecoVendaFormatado() {
        Locale ptBr = new Locale("pt", "BR");        
        return NumberFormat.getCurrencyInstance(ptBr).format(this.preco_venda).replace("R$ ", "");
    }

    public void setPrecoVenda(double preco_venda) {
        this.preco_venda = preco_venda;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
        
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public Integer getEnabled() {
        return enabled;
    }
    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }
    
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }    
    
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public int getQuantidade() {
        return quantidade;
    }   
}