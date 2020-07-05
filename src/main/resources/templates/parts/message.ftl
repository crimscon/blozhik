<#include "security.ftl">

<#macro message messages>
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
            <a href="${message.author.username}/profile">
                <div class="btn-group" role="group" aria-label="First group">
                    <div class="input-group-prepend">
                        <div class="input-group-text" id="btnGroupAddon2">${message.author.username}</div>
                    </div>
                </div>
            </a>
            <#if name == message.author.username || isAdmin>
            <div class="btn-group" role="group" aria-label="Second group">
                <form method="get" action="../../${message.id}/delete">
                    <button type="submit" class="btn btn-outline-secondary"><i class="fas fa-trash-alt"></i>
                    </button>
                </form>
            </div>
        </#if>
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
</#macro>