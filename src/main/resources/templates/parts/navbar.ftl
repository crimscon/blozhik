<#import "login.ftl" as l>
<#include "security.ftl">

<nav class="navbar sticky-top navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand" href="/" style="padding-top:0">Бложик</a>

        <#-- TODO добавить поиск по постам в навбар или на главную -->

        <button class="navbar-toggler collapsed" type="button" data-toggle="collapse" data-target="#navbarColor01"
                aria-controls="navbarColor01" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarColor01">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                </li>
                <#if isAdmin>
                    <li class="nav-item">
                        <a class="nav-link" href="/users">Список пользователей</a>
                    </li>
                </#if>
            </ul>

            <@l.logout />
        </div>
    </div>
</nav>
