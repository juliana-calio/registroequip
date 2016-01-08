/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

/**
 *
 * @author Juliana
 */
@Entity
public class Equipamento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ID;

    private String nome;

    private String numAtivo;

    private String posto;

    @ElementCollection(targetClass = Registro.class, fetch=FetchType.EAGER)
    private List<Registro> registros;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNumAtivo() {
        return numAtivo;
    }

    public void setNumAtivo(String numAtivo) {
        this.numAtivo = numAtivo;
    }

    public String getPosto() {
        return posto;
    }

    public void setPosto(String posto) {
        this.posto = posto;
    }

    public List<Registro> getRegistros() {
        return registros;
    }

    public void setRegistros(List<Registro> registros) {
        this.registros = registros;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ID != null ? ID.hashCode() : 0);
        return hash;

    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof Equipamento)) {
            return false;
        }

        Equipamento other = (Equipamento) object;
        if ((this.ID == null && other.ID != null) || (this.ID != null && !(this.ID.equals(other.ID)))) {
            return false;
        }

        return true;

    }

    @Override
    public String toString() {
        return this.nome;
    }

}
