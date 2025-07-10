<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="br.ufsm.csi.projetodiogopoow.model.Dirigente, br.ufsm.csi.projetodiogopoow.model.Time, br.ufsm.csi.projetodiogopoow.model.Jogador" %>
<%@ page import="br.ufsm.csi.projetodiogopoow.dao.TimeDAO, br.ufsm.csi.projetodiogopoow.dao.JogadorDAO" %>
<%@ page session="true" %>
<%@ page import="java.util.List" %>
<%
    Dirigente dirigente = (Dirigente) session.getAttribute("dirigenteLogado");
    if (dirigente == null) {
        response.sendRedirect("/index");
        return;
    }

    Time time = TimeDAO.buscarPorDirigente(dirigente.getId());
    List<Jogador> jogadores = time != null ? JogadorDAO.buscarPorTimeId(time.getId()) : null;
%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Meu Time</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<nav class="navbar navbar-expand-lg navbar-dark bg-primary mb-4">
    <div class="container">
        <a class="navbar-brand" href="/login/home">Sistema de Dirigentes</a>
        <div class="d-flex">
            <a href="/logout" class="btn btn-outline-light">Sair</a>
        </div>
    </div>
</nav>

<div class="container">
    <h2 class="text-center mb-4">Meu Time</h2>

    <% if (time != null) { %>
    <div class="row">

        <div class="col-md-5">
            <div class="card shadow rounded mb-4">
                <div class="card-body">
                    <p><strong>Nome:</strong> <%= time.getNome() %></p>
                    <p><strong>Cidade:</strong> <%= time.getCidade() %></p>
                    <p><strong>Dirigente:</strong> <%= dirigente.getNome() %></p>

                    <div class="d-grid gap-2 mt-4">
                        <button class="btn btn-warning" data-bs-toggle="modal" data-bs-target="#editarModal">Alterar</button>

                        <a href="/time/excluir/<%= time.getId() %>" class="btn btn-danger"
                           onclick="return confirm('Tem certeza que deseja excluir seu time?');">
                            Excluir
                        </a>

                        <a href="/login/home" class="btn btn-secondary">Voltar</a>
                    </div>
                </div>
            </div>

            <div class="d-grid">
                <a href="/jogador/cadastrar" class="btn btn-success">Cadastrar Jogador</a>
            </div>
        </div>

        <div class="col-md-7">
            <div class="card shadow rounded">
                <div class="card-body">
                    <h5 class="card-title">Jogadores do Time</h5>

                    <% if (jogadores != null && !jogadores.isEmpty()) { %>
                    <table class="table table-bordered table-striped mt-3">
                        <thead class="table-light">
                        <tr>
                            <th>Nome</th>
                            <th>Posição</th>
                            <th>Idade</th>
                            <th>Número</th>
                            <th>Ações</th>
                        </tr>
                        </thead>
                        <tbody>
                        <% for (Jogador j : jogadores) { %>
                        <tr>
                            <td><%= j.getNome() %></td>
                            <td><%= j.getPosicao() %></td>
                            <td><%= j.getIdade() %></td>
                            <td><%= j.getNumero() %></td>
                            <td>
                                <button class="btn btn-sm btn-warning" data-bs-toggle="modal" data-bs-target="#editarJogadorModal_<%= j.getId() %>">Editar</button>
                                <form action="/jogador" method="post" class="d-inline" onsubmit="return confirm('Deseja excluir este jogador?');">
                                    <input type="hidden" name="opcao" value="excluir">
                                    <input type="hidden" name="id" value="<%= j.getId() %>">
                                    <button class="btn btn-sm btn-danger" type="submit">Excluir</button>
                                </form>
                            </td>
                        </tr>
                        <% } %>
                        </tbody>
                    </table>
                    <% } else { %>
                    <p class="text-muted mt-3">Nenhum jogador cadastrado ainda.</p>
                    <% } %>
                </div>
            </div>
        </div>
    </div>


    <% for (Jogador j : jogadores) { %>
    <div class="modal fade" id="editarJogadorModal_<%= j.getId() %>" tabindex="-1" aria-labelledby="editarJogadorModalLabel_<%= j.getId() %>" aria-hidden="true">
        <div class="modal-dialog">
            <form action="/jogador" method="post" class="modal-content">
                <input type="hidden" name="opcao" value="alterar">
                <input type="hidden" name="id" value="<%= j.getId() %>">
                <input type="hidden" name="timeId" value="<%= j.getTimeId() %>">

                <div class="modal-header">
                    <h5 class="modal-title" id="editarJogadorModalLabel_<%= j.getId() %>">Editar Jogador</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Fechar"></button>
                </div>
                <div class="modal-body">
                    <div class="mb-3">
                        <label class="form-label">Nome</label>
                        <input type="text" class="form-control" name="nome" value="<%= j.getNome() %>" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Posição</label>
                        <input type="text" class="form-control" name="posicao" value="<%= j.getPosicao() %>" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Idade</label>
                        <input type="number" class="form-control" name="idade" value="<%= j.getIdade() %>" required>
                    </div>
                    <div class="mb-3">
                        <label class="form-label">Número</label>
                        <input type="number" class="form-control" name="numero" value="<%= j.getNumero() %>" required>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                    <button type="submit" class="btn btn-success">Salvar Alterações</button>
                </div>
            </form>
        </div>
    </div>
    <% } %>


    <div class="modal fade" id="editarModal" tabindex="-1" aria-labelledby="editarModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <form action="/time" method="post" class="modal-content">
                <input type="hidden" name="opcao" value="alterar">
                <input type="hidden" name="id" value="<%= time.getId() %>">

                <div class="modal-header">
                    <h5 class="modal-title" id="editarModalLabel">Editar Time</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Fechar"></button>
                </div>
                <div class="modal-body">
                    <div class="mb-3">
                        <label for="nome" class="form-label">Nome</label>
                        <input type="text" class="form-control" name="nome" id="nome" value="<%= time.getNome() %>" required>
                    </div>
                    <div class="mb-3">
                        <label for="cidade" class="form-label">Cidade</label>
                        <input type="text" class="form-control" name="cidade" id="cidade" value="<%= time.getCidade() %>" required>
                    </div>
                    <input type="hidden" name="dirigenteId" value="<%= dirigente.getId() %>">
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                    <button type="submit" class="btn btn-success">Salvar Alterações</button>
                </div>
            </form>
        </div>
    </div>

    <% } else { %>
    <div class="alert alert-info text-center">Você ainda não tem um time cadastrado.</div>
    <div class="text-center mt-3">
        <a href="/login/home" class="btn btn-secondary">Voltar</a>
    </div>
    <% } %>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
