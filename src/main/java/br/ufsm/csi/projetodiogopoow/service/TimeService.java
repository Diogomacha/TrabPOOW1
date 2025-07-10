package br.ufsm.csi.projetodiogopoow.service;

import br.ufsm.csi.projetodiogopoow.dao.TimeDAO;
import br.ufsm.csi.projetodiogopoow.model.Time;

import java.util.ArrayList;

public class TimeService {

    public static ArrayList<Time> listarTimes() {
        return TimeDAO.getTimes();
    }

    public static Time buscarPorId(int id) {
        return TimeDAO.buscarPorId(id);
    }

    public static Time buscarPorDirigente(int dirigenteId) {
        return TimeDAO.buscarPorDirigente(dirigenteId);
    }

    public static boolean cadastrar(Time time) {
        return TimeDAO.inserir(time) != null;
    }


    public static Time inserir(Time time) {
        return TimeDAO.inserir(time);
    }

    public static boolean alterar(Time time) {
        return TimeDAO.alterar(time);
    }

    public static boolean excluir(int id) {
        return TimeDAO.excluir(id);
    }
}
