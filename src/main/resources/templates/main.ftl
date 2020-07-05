<#import "/parts/bootstrap.ftl" as e>
<#import "/parts/message.ftl" as m>
<#include "/parts/security.ftl">

<@e.main true>

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
            <@m.message messages />
        </div>
    </div>
<div class="mb-3"></div>
</@e.main>