package br.ufsm.csi.projetodiogopoow.service;

import br.ufsm.csi.projetodiogopoow.dao.DirigenteDAO;
import br.ufsm.csi.projetodiogopoow.model.Dirigente;

public class DirigenteService {

    private DirigenteDAO dao = new DirigenteDAO();

    public boolean autenticar(String email, String senha) {
        Dirigente dirigente = dao.buscarPorEmail(email);
        if (dirigente != null) {
            return dirigente.getSenha().equals(senha);
        }
        return false;
    }

    public Dirigente buscarPorEmail(String email) {
        return dao.buscarPorEmail(email);
    }


    public void inserir(Dirigente dirigente) {
        dao.inserir(dirigente);
    }


    public void alterar(Dirigente dirigente) {
        dao.alterar(dirigente);
    }


    public void excluir(int id) {
        dao.excluir(id);
    }

}
