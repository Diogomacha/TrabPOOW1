<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="br.ufsm.csi.projetodiogopoow.model.Dirigente" %>
<%@ page import="br.ufsm.csi.projetodiogopoow.model.Time" %>
<%@ page import="br.ufsm.csi.projetodiogopoow.dao.TimeDAO" %>
<%
    HttpSession sessao = request.getSession(false);
    Dirigente dirigente = (Dirigente) sessao.getAttribute("dirigenteLogado");

    if (dirigente == null) {
        response.sendRedirect("/login");
        return;
    }

    Time time = TimeDAO.buscarPorDirigente(dirigente.getId());

    if (time == null) {
        response.sendRedirect("/erro");
        return;
    }
%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Cadastrar Jogador</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<!-- NAVBAR -->
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <div class="container-fluid px-4">
        <a class="navbar-brand" href="/login/home">Sistema de Dirigentes</a>
        <div class="d-flex ms-auto align-items-center text-white">
            <span class="me-3">Olá, <strong><%= dirigente.getNome() %></strong></span>
            <a href="/logout" class="btn btn-outline-light btn-sm">Sair</a>
        </div>
    </div>
</nav>

<!-- CONTEÚDO -->
<div class="container mt-5 d-flex justify-content-center">
    <div class="card shadow p-4 w-100" style="max-width: 600px;">
        <h2 class="mb-4 text-center">Cadastrar Novo Jogador</h2>

        <form action="/jogador" method="post">
            <input type="hidden" name="opcao" value="cadastrar">

            <div class="mb-3">
                <label for="nome" class="form-label">Nome do Jogador</label>
                <input type="text" class="form-control" id="nome" name="nome" required>
            </div>

            <div class="mb-3">
                <label for="posicao" class="form-label">Posição</label>
                <input type="text" class="form-control" id="posicao" name="posicao" required>
            </div>

            <div class="mb-3">
                <label for="idade" class="form-label">Idade</label>
                <input type="number" class="form-control" id="idade" name="idade" min="0" required>
            </div>

            <div class="mb-3">
                <label for="numero" class="form-label">Número da Camisa</label>
                <input type="number" class="form-control" id="numero" name="numero" min="0" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Time</label>
                <input type="text" class="form-control" value="<%= time.getNome() %>" readonly>
                <input type="hidden" name="timeId" value="<%= time.getId() %>">
            </div>

            <div class="mb-3">
                <label class="form-label">Dirigente</label>
                <input type="text" class="form-control" value="<%= dirigente.getNome() %>" readonly>
                <input type="hidden" name="dirigenteId" value="<%= dirigente.getId() %>">
            </div>

            <div class="d-flex justify-content-between mt-4">
                <a href="/login/home" class="btn btn-outline-secondary">← Voltar</a>
                <button type="submit" class="btn btn-primary">Cadastrar Jogador</button>
            </div>
        </form>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
