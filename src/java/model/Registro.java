/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;

/**
 *
 * @author Juliana
 */
@Embeddable
public class Registro implements Serializable{

    
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataRealizacao;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataVencimento;
    
    private String local;
    
    private String numero;
    
    //Procedimentos realizados no aparelho
    private String descricao;
    
    //Se é preventiva ou calibracao
    private String tipo;

    
    public Date getDataRealizacao() {
        return dataRealizacao;
    }

    public void setDataRealizacao(Date dataRealizacao) {
        this.dataRealizacao = dataRealizacao;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    
   
    
    
    
}
