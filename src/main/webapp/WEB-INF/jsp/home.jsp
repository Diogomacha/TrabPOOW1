<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="br.ufsm.csi.projetodiogopoow.model.Dirigente" %>
<%
    Dirigente dirigente = (Dirigente) session.getAttribute("dirigenteLogado");

    if (dirigente == null) {
        response.sendRedirect("/login");
        return;
    }
%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8" />
    <title>Dashboard - Dirigente</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" />
</head>
<body class="bg-light">


<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <div class="container-fluid px-4">
        <a class="navbar-brand" href="/login/home">Sistema de Dirigentes</a>
        <div class="d-flex ms-auto align-items-center text-white">
            <span class="me-3">Olá, <strong><%= dirigente.getNome() %></strong></span>
            <a href="/logout" class="btn btn-outline-light btn-sm">Sair</a>
        </div>
    </div>
</nav>


<div class="container d-flex flex-column align-items-center justify-content-center" style="min-height: 85vh;">
    <div class="card shadow p-5 w-100" style="max-width: 600px;">
        <h2 class="mb-4 text-center">Painel do Dirigente</h2>

        <div class="d-grid gap-3">
            <a href="/time/cadastrar" class="btn btn-primary btn-lg">Cadastrar Time</a>
            <a href="/jogador/cadastrar" class="btn btn-success btn-lg">Cadastrar Jogador</a>
            <a href="/dirigente/perfil" class="btn btn-info btn-lg text-white">Ver Perfil</a>
            <a href="/time/meu-time" class="btn btn-warning btn-lg">Meu Time</a>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.0-beta1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
