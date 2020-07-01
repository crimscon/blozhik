<#import "/parts/bootstrap.ftl" as e>
<#import "/parts/navbar.ftl" as nav>
<#import "/parts/login.ftl" as l>


<@e.main>
<@nav.bar>
<ul class="navbar-nav mr-auto">
    <li class="nav-item active">
        <a class="nav-link">Home <span class="sr-only">(current)</span></a>
    </li>
    <li class="nav-item active">
        <a class="nav-link" href="/user">User list</a>
    </li>
    <#if user??>
    <li class="nav-item float-right">
        <a class="nav-link" href="#">Link</a>
    </li>
</#if>
</ul>

<@l.logout />
</@nav.bar>

<div class="container">
    <div class="row">
        <div class="col-sm-12 col-md-12 col-lg-10 offset-lg-1">
            <form method="post" action="add" class="form-group">
                <div class="card mt-2">
                    <div class="card-header">
                        <input required type="text" name="title" placeholder="Введите заголовок" class="form-control"
                               autocomplete="off" maxlength="300">
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    </div>
                    <div class="card-body">
                        <blockquote class="blockquote">
                        <textarea required name="text" placeholder="Введите текст"
                                  class="form-control textarea-autosize"
                                  id="textareaExample"
                                  autocomplete="off" maxlength="500"></textarea>
                        </blockquote>
                        <button class="btn btn-outline-primary float-right btn-block" type="submit">Отправить</button>
                    </div>
                </div>
            </form>
            <#list messages as message>
            <div class="card mt-2">
                <div class="card-header">
                    ${message.title}
                </div>
                <div class="card-body">
                    <blockquote class="blockquote mb-0">
                        ${message.text}
                    </blockquote>
                </div>
                <div class="card-footer text-muted">

                    <div class="btn-toolbar justify-content-between" role="toolbar"
                         aria-label="Toolbar with button groups">
                        <a href="${message.author.id}/profile">
                            <div class="btn-group" role="group" aria-label="First group">
                                <div class="input-group-prepend">
                                    <div class="input-group-text" id="btnGroupAddon2">${message.author.username}</div>
                                </div>
                            </div>
                        </a>
                        <div class="btn-group" role="group" aria-label="Second group">
                            <form method="get" action="${message.id}/delete">
                                <button type="submit" class="btn btn-outline-secondary"><i class="fas fa-trash-alt"></i></button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>


                <#else>
                <div class="card mt-2">
                    <div class="card-body">
                        <blockquote class="blockquote mb-0 text-center">
                            Нет собщений
                        </blockquote>
                    </div>
                </div>
            </#list>
        </div>
    </div>
<div class="mb-3"></div>
</@e.main>