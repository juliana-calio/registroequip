package controller;

import facade.EquipamentoFacade;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.inject.Named;
import model.Equipamento;
import model.Registro;
import util.EquipamentoDataModel;
import util.RegistroDataModel;


@Named(value = "equipamentoController")
@SessionScoped
public class EquipamentoController implements Serializable {
    
    public EquipamentoController() {
        
    }

    //Equipamento atual----------------------------------------------------
    private Equipamento equipamento;
    
    public Equipamento getEquipamento() {
        
        if(equipamento == null){
            equipamento = new Equipamento();
        }
        
        return equipamento;
    }

    public void setEquipamento(Equipamento equipamento) {
        this.equipamento = equipamento;
    }
    
    private String ativo;

    public String getAtivo() {
        return ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }
    
    
    
    //Data Model--------------------------------------------------------
    
    private EquipamentoDataModel dataModel;

    public EquipamentoDataModel getDataModel() {
        
        if (dataModel == null) {
            dataModel = new EquipamentoDataModel(this.listarTodas());
        }
        
        return dataModel;
    }

    public void setDataModel(EquipamentoDataModel dataModel) {
        this.dataModel = dataModel;
    }
    
    
    
    
    @EJB
    private EquipamentoFacade equipamentoFacade;

    
    //---------------------------------------------------CRUD-------------------------------------------------------
    private List<Equipamento> listarTodas() {
        return equipamentoFacade.findAll();

    }

    
    public void salvarNoBanco() {

        try {
            equipamentoFacade.save(equipamento);
            JsfUtil.addSuccessMessage("Equipamento " + equipamento.getNome() + " criado com sucesso!");
            equipamento= null;
            dataModel = null;
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Ocorreu um erro de persistência");

        }

    }

    public Equipamento buscar(Long id) {

        return equipamentoFacade.find(id);
    }
    
    
    public void editar() {
        try {
            equipamentoFacade.edit(equipamento);
            JsfUtil.addSuccessMessage("Docente editado com sucesso!");
            //equipamento= null;
            dataModel = null;
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Ocorreu um erro de persistência, não foi possível editar o docente: " + e.getMessage());

        }
    }

    public void delete() {
        //equipamento= (Equipamento) dataModel.getRowData();
        try {
            
            equipamento.setRegistros(null);
            equipamentoFacade.merge(equipamento);
            equipamentoFacade.remove(equipamento);
            equipamento= null;
            JsfUtil.addSuccessMessage("Equipamento Deletado");
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Ocorreu um erro de persistência");
        }

        registros = null;
        equipamento = null;
        //recriarModelo();
    }
    
    
    
    public SelectItem[] getItemsAvaiableSelectOne() {
        return JsfUtil.getSelectItems(equipamentoFacade.findAll(), true);
    }

    public void recriarModelo() {
    
        dataModel = null;

    }
    
    public String prepareEdit() {
        equipamento = (Equipamento) dataModel.getRowData();
        return "/Cadastro/editEquipamento";
    }
    
    public String prepareView(){
        
        equipamento = (Equipamento) dataModel.getRowData();
        
        //regDataModel = new RegistroDataModel(equipamento.getRegistros());
        
        return "/registro";
    }
    
    public void cadastrarRegistro(){
        
        
        registros = equipamento.getRegistros();
        registro.setLocal(equipamento.getPosto());
        registros.add(registro);
        
        equipamento.setRegistros(registros);
        equipamentoFacade.edit(equipamento);
        registro = null;
        
    }
    
    public void deletarRegistro(){
        
        //equipamento= (Equipamento) dataModel.getRowData();
        registro = (Registro) regDataModel.getRowData();
        
        registros.remove(registro);
        equipamento.setRegistros(registros);
        equipamentoFacade.merge(equipamento);
        registro = null;
        
        
        
    }
    
    public void buscarEquipamento(){
        
        List<Equipamento> equipamentos = equipamentoFacade.buscarPorAtivo(ativo);
        if(equipamentos.size() > 0){
            equipamento = equipamentos.get(0);
        }
        
        registros = equipamento.getRegistros();
        regDataModel = new RegistroDataModel(registros);
        ativo = "";
        
    }
    
    
    //--------------------------------------------------------------------------------------------------------------

    public void cadastrarEquipamentos() {

        String[] palavras;
        String nomeEquipamento;
        String posto = null;
        String numAtivo;
        String eixo;
        String curso;

        try {

            try (BufferedReader lerArq = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\Juliana\\Desktop\\Lista.txt"), "UTF-8"))) {
                String linha = lerArq.readLine(); //cabeçalho
                
               while (linha != null) {
//                linha = linha.replace("\"", "");
                    
                    if(linha.contains("POSTO")){
                        palavras = linha.split("POSTO: ");
                        posto = palavras[1];
                    }
                    
                    else{
                        nomeEquipamento = linha;
                        numAtivo = lerArq.readLine();
                        
                        equipamento = new Equipamento();
                        equipamento.setNome(nomeEquipamento);
                        equipamento.setNumAtivo(numAtivo);
                        equipamento.setPosto(posto);
                        
                        equipamentoFacade.save(equipamento);
                        
                    }
                
                    linha = lerArq.readLine();
                }
            } //cabeçalho //cabeçalho

        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
        }

        equipamento = new Equipamento();
        recriarModelo();

        JsfUtil.addSuccessMessage("Cadastro de disciplinas realizado com sucesso");

    }
    
    //Registro------------------------------------------------------------------------------------------
    
    private Registro registro;
    
    private Registro registroSelecionado;
    
    private List<Registro> registros;
    
    private List<Registro> registrosFiltrados;
    
    private RegistroDataModel regDataModel;

    public RegistroDataModel getRegDataModel() {
        return regDataModel;
    }

    public void setRegDataModel(RegistroDataModel regDataModel) {
        this.regDataModel = regDataModel;
    }
    
    
    
    //@EJB
    //private RegistroFacade registroFacade;

    public Registro getRegistro() {
        
        if(registro == null){
            registro = new Registro();
        }
        
        return registro;
    }

    public void setRegistro(Registro registro) {
        this.registro = registro;
    }

    public List<Registro> getRegistros() {
        return registros;
    }

    public void setRegistros(List<Registro> registros) {
        this.registros = registros;
    }

    public List<Registro> getRegistrosFiltrados() {
        return registrosFiltrados;
    }

    public void setRegistrosFiltrados(List<Registro> registrosFiltrados) {
        this.registrosFiltrados = registrosFiltrados;
    }

    public Registro getRegistroSelecionado() {
        return registroSelecionado;
    }

    public void setRegistroSelecionado(Registro registroSelecionado) {
        this.registroSelecionado = registroSelecionado;
    }

    
    
    
    
    
    
    
    
    
 
}