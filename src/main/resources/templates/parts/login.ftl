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

        <#if Session?? && Session.SPRING_SECURITY_LAST_EXCEPTION??>

            <div class="alert alert-danger mt-2">
                ${Session.SPRING_SECURITY_LAST_EXCEPTION.message}
            </div>

        </#if>

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
        <div class="form-label-group mb-3">
            <input type="text" id="username" name="username"
                   class="form-control ${(errors?? && errors.usernameError??)?string('is-invalid', '')}"
                   placeholder="Login"
                   autofocus="autofocus" autocomplete="off"
                   value="<#if user??>${user.getUsername()}</#if>">
            <#if errors?? && errors.usernameError??>
                <div class="invalid-feedback">
                    ${errors.usernameError}
                </div>
            </#if>
        </div>

        <div class="form-label-group mb-3">
            <input type="email" id="email" name="email"
                   class="form-control ${(errors?? && errors.emailError??)?string('is-invalid', '')}"
                   placeholder="E-mail"
                   autocomplete="off"
                   value="<#if user??>${user.getEmail()}</#if>">
            <#if errors?? && errors.emailError??>
                <div class="invalid-feedback">
                    ${errors.emailError}
                </div>
            </#if>
        </div>

        <div class="form-label-group mb-3">
            <input type="password" id="inputPassword" name="password"
                   class="form-control ${(errors?? && errors.passwordError??)?string('is-invalid', '')}"
                   placeholder="Password"
                   autocomplete="off">
            <#if errors?? && errors.passwordError??>
                <div class="invalid-feedback">
                    ${errors.passwordError}
                </div>
            </#if>
        </div>

        <div class="form-label-group mb-3">
            <input type="password" id="inputPasswordConfirm" name="passwordConfirm"
                   class="form-control ${(errors?? && errors.passwordError??)?string('is-invalid', '')}"
                   placeholder="Repeat password"
                   autocomplete="off">
            <#if errors?? && errors.passwordError??>
                <div class="invalid-feedback">
                    ${errors.passwordError}
                </div>
            </#if>
        </div>

        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button class="btn btn-lg btn-primary btn-block" type="submit">
            Зарегистрироваться
        </button>
        <p class="text-center mt-2">Уже есть аккаунт? <a href="/login">Войдите!</a></p>

    </form>
</#macro>

<#macro logout>
    <#if user??>
        <#if user.getProfile_pic()??>
            <a href="../../${user.getUsername()}/profile"><img src="/img/thumbs/${user.getProfile_pic()}"
                                                               class="d-none d-lg-block align-self-center img-fluid rounded-circle mr-2"
                                                               alt="..."></a>
        <#else>
            <a href="../../${user.getUsername()}/profile" class=""><img src="/static/img/avatar.svg" id="imageResource"
                                                                        class="d-none d-lg-block border-none rounded-circle mr-2"
                                                                        style="background-color: #cacaca!important"
                                                                        height="40px"
                                                                        width="40px"></a>
        </#if>
        <div class="btn-group d-md d-lg-none" role="group" aria-label="Buttons profile and logout">
            <form action="/logout" method="post">
                <a href="../../${user.getUsername()}/profile" class="btn btn-outline-light">${name}</a>
                <button class="btn btn-outline-light ml-2" type="submit">
                    <input name="_csrf" value="${_csrf.token}" hidden>
                    <i class="fas fa-sign-out-alt"></i>
                </button>
            </form>
        </div>
    <#else>
        <a href="../../login" class="btn btn-outline-light my-2 my-sm-0">Войти <i class="fas fa-sign-in-alt"></i></a>
    </#if>
</#macro>