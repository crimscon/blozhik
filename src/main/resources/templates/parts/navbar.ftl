<#import "login.ftl" as l>
<#include "security.ftl">

<nav class="navbar sticky-top navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand" href="/">
            <img src="../../static/img/logo.png" height="45px" alt="" loading="lazy">
        </a>
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
            </li>
            <#if isAdmin>
                <li class="nav-item">
                    <a class="nav-link" href="/users">Список пользователей</a>
                </li>
            </#if>
        </ul>

        <#if user??>
            <div class="dropdown">
                <button class="btn btn-secondary bg-transparent border-0 dropdown-toggle" type="button"
                        id="profileDropdown" data-toggle="dropdown"
                        aria-haspopup="true" aria-expanded="false">
                    <#if user.getPicture()??>
                        <img src="/img/thumbs/${user.getPicture()}"
                             class="rounded-circle ml-2"
                             alt="...">
                    <#else>
                        <img src="/static/img/avatar.svg" id="imageResource"
                             class="border-none rounded-circle"
                             style="background-color: #cacaca!important" height="40px" width="40px" alt="...">
                    </#if>
                </button>
                <div class="dropdown-menu dropdown-menu-right" aria-labelledby="profileDropdown">
                    <a class="dropdown-item p-3 px-lg-4 py-lg-1" href="/${user.getUsername()}/profile">Профиль</a>
                    <a class="dropdown-item p-3 px-lg-4 py-lg-1 border-bottom" href="/${user.getUsername()}/edit">Настройки</a>
                    <@l.logout />
                </div>
            </div>

        </#if>
    </div>
</nav>
