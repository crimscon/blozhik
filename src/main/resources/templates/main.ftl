<#import "/parts/bootstrap.ftl" as e>
<#import "/parts/navbar.ftl" as nav>
<#import "/parts/login.ftl" as l>


<@e.main>
<@nav.bar>

<li class="nav-item active">
    <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
</li>
<li class="nav-item">
    <a class="nav-link" href="#">Link</a>
</li>

<div>
    <@l.logout />
</div>
</@nav.bar>

<div class="container">
    <div class="row">
        <div class="col-sm-12 col-md-12 col-lg-10 offset-lg-1">
            <form method="post" action="add" class="form-group">
                <div class="card mt-1">
                    <div class="card-header">
                        <input required type="text" name="title" placeholder="Введите заголовок" class="form-control"
                               autocomplete="off" maxlength="300">
                        <input type="hidden" name="_csrf" value="${_csrf.token}" />
                    </div>
                    <div class="card-body">
                        <blockquote class="blockquote">
                        <textarea required name="text" placeholder="Введите текст"
                                  class="form-control textarea-autosize"
                                  id="textareaExample"
                                  autocomplete="off" maxlength="500" rows="1"></textarea>
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
                    ${message.author.username}
                <form method="post" action="delete">
                    <input name="id" value="${message.id}" hidden>
                    <button class="btn btn-outline-primary float-right" type="submit">Удалить</button>
                </form>
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
</@e.main>