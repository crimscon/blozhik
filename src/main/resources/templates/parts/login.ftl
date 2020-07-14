<#include "security.ftl">

<#macro login path>

    <style>
        body {
            height: 100vh;
            display: -ms-flexbox;
            display: flex;
            -ms-flex-align: center;
            align-items: center;
            padding-top: 40px;
            background-color: #f5f5f5;
            padding-bottom: 40px;
        }
    </style>

    <form class="form-signin" action="${path}" method="post">
        <div class="text-center mb-4">
            <h1 class="h3 mb-3 font-weight-normal">Бложик</h1>
        </div>

        <div class="form-label-group">
            <input type="text" id="username" name="username" class="form-control" placeholder="Login"
                   required="required"
                   autofocus="autofocus" autocomplete="off">
            <label for="username"></label>
        </div>

        <div class="form-label-group">
            <input type="password" id="inputPassword" name="password" class="form-control" placeholder="Password"
                   required="required"
                   autocomplete="off">
            <label for="inputPassword"></label>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button class="btn btn-lg btn-primary btn-block" type="submit">
            Войти
        </button>
        <p class="text-center mt-2">Еще нет аккаунта? <a href="/registration">Зарегистрируйтесь!</a></p>
    </form>
</#macro>

<#macro register path>

    <style>
        body {
            height: 100vh;
            display: -ms-flexbox;
            display: flex;
            -ms-flex-align: center;
            align-items: center;
            padding-top: 40px;
            background-color: #f5f5f5;
            padding-bottom: 40px;
        }

    </style>

    <form class="form-signin" action="${path}" method="post">
        <div class="text-center mb-4">
            <h1 class="h3 mb-3 font-weight-normal">Бложик</h1>
        </div>

        <#if message??>
            <div class="alert alert-danger" role="alert">
                ${message}
            </div>
        </#if>
        <div class="form-label-group">
            <input type="text" id="username" name="username" class="form-control" placeholder="Login"
                   required="required"
                   autofocus="autofocus" autocomplete="off">
            <label for="username"></label>
        </div>

        <!--    <div class="form-label-group">-->
        <!--        <input type="email" id="email" name="email" class="form-control" placeholder="E-mail" required=""-->
        <!--               autofocus="autofocus" autocomplete="off">-->
        <!--        <label for="email"></label>-->
        <!--    </div>-->

        <div class="form-label-group">
            <input type="password" id="inputPassword" name="password" class="form-control" placeholder="Password"
                   required="required"
                   autocomplete="off">
            <label for="inputPassword"></label>
        </div>

        <div class="form-label-group">
            <input type="password" id="inputPassword2" name="password2" class="form-control"
                   placeholder="Repeat password" required="required"
                   autocomplete="off">
            <label for="inputPassword2"></label>
        </div>

        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button class="btn btn-lg btn-primary btn-block" type="submit">
            Зарегистрироваться
        </button>
        <p class="text-center mt-2">Уже есть аккаунт? <a href="/login">Войдите!</a></p>

    </form>
</#macro>

<#macro logout>
    <form action="/logout" method="post" class="form-inline">
        <a href="../../${name}/profile" class="btn btn-link text-light">${name}</a>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button class="btn btn-outline-light my-2 my-sm-0" type="submit"><i class="fas fa-sign-out-alt"></i></button>
    </form>
</#macro>