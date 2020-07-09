<#include "security.ftl">

<#macro message messages>
<div class="card-columns">
<#list messages as message>
    <#if message.filename?? && message.title != "" && message.announce != "">
        <div class="card border-${message.color}">
                <img src="/img/${message.filename}" class="card-img-top">
                <h5 class="card-header alert-${message.color}">${message.title}</h5>
                <div class="card-body">
                    <p class="card-text">${message.announce}</p>
                </div>
        </div>

        <div class="modal fade" id="${message.id}" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalCenterTitle">${message.title}</h5>
                    </div>
                    <div class="modal-body">
                        <p>${message.text}</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary">Save changes</button>
                    </div>
                </div>
            </div>
        </div>
    <#elseif message.filename?? && message.title != "">
    <div class="card bg-${message.color}">
        <img src="/img/${message.filename}" class="card-img-top">
        <h5 class="card-header alert-${message.color}">${message.title}</h5>
    </div>
    <#elseif message.filename?? && message.announce != "">
        <div class="card border-${message.color}">
            <img src="/img/${message.filename}" class="card-img-top">
            <blockquote class="blockquote mb-0 card-body">
                <p>${message.announce}</p>
            </blockquote>
        </div>
    <#elseif message.announce != "" && message.title == "">
        <div class="card p-3 border-${message.color}">
            <blockquote class="blockquote mb-0 card-body">
                <p>${message.announce}</p>
            </blockquote>
        </div>
    <#elseif message.title != "" && message.announce != "">
        <div class="card border-${message.color}">
            <h5 class="card-header alert-${message.color}">${message.title}</h5>
            <div class="card-body">
                <p class="card-text">${message.announce}</p>
            </div>
        </div>
    <#elseif message.filename??>
        <div class="card">
            <img src="/img/${message.filename}" class="card-img-top">
        </div>
    </#if>
<!--    <div class="card p-3">-->
<!--        <blockquote class="blockquote mb-0 card-body">-->
<!--            <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer posuere erat a ante.</p>-->
<!--            <footer class="blockquote-footer">-->
<!--                <small class="text-muted">-->
<!--                    Someone famous in <cite title="Source Title">Source Title</cite>-->
<!--                </small>-->
<!--            </footer>-->
<!--        </blockquote>-->
<!--    </div>-->

<!--<div class="row row-cols-1 row-cols-md-3">-->
<!--    <div class="col mb-4">-->
<!--        <div class="card">-->

<!--        <div class="card-body">-->

<!--        </div>-->

<!--        <div class="card-footer">-->
<!--            <div class="btn-toolbar justify-content-between" role="toolbar"-->
<!--                 aria-label="Toolbar with button groups">-->
<!--                <a href="${message.author.username}/profile">-->
<!--                    <div class="btn-group" role="group" aria-label="First group">-->
<!--                        <div class="input-group-prepend">-->
<!--                            <div class="input-group-text" id="btnGroupAddon2">${message.author.username}</div>-->
<!--                        </div>-->
<!--                    </div>-->
<!--                </a>-->
<!--                <#if name == message.author.username || isAdmin>-->
<!--                <div class="btn-group" role="group" aria-label="Second group">-->
<!--                    <form method="get" action="../../${message.id}/delete">-->
<!--                        <button type="button" data-toggle="modal" data-target="#modalDelete"-->
<!--                                class="btn btn-outline-secondary"><i class="fas fa-trash-alt"></i>-->
<!--                        </button>-->


<!--                        <div class="modal fade" id="modalDelete" tabindex="-1" role="dialog"-->
<!--                             aria-labelledby="exampleModalLabel" aria-hidden="true">-->
<!--                            <div class="modal-dialog modal-dialog-centered">-->
<!--                                <div class="modal-content">-->
<!--                                    <div class="modal-header">-->
<!--                                        <h5 class="modal-title" id="exampleModalLabel">Удаление сообщения</h5>-->
<!--                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">-->
<!--                                            <span aria-hidden="true">&times;</span>-->
<!--                                        </button>-->
<!--                                    </div>-->
<!--                                    <div class="modal-body">-->
<!--                                        <p>Вы действительно хотите удалить сообщение?</p>-->
<!--                                    </div>-->
<!--                                    <div class="modal-footer">-->
<!--                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Отмена-->
<!--                                        </button>-->
<!--                                        <button type="submit" class="btn btn-primary">Удалить</button>-->
<!--                                    </div>-->
<!--                                </div>-->
<!--                            </div>-->
<!--                        </div>-->

<!--                    </form>-->
<!--                </div>-->
<!--            </#if>-->
<!--        </div>-->
<!--    </div>-->
<!--</div>-->
<!--</div>-->

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
</#macro>