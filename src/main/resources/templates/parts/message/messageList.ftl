<#include "../security.ftl">
<#import "../pager.ftl" as p>


<#macro message page>

    <#include "messageSend.ftl">

    <div class="card-columns mt-3">
    <#list page.content as message>
        <#if message.filename?? && message.title != "" && message.announce != "">
            <div class="card border-${message.color?lower_case}">
                <img src="/img/thumbs/${message.filename}" class="card-img-top">
                <h5 class="card-header alert-${message.color?lower_case}">${message.title}</h5>
                <div class="card-body">
                    <p class="card-text">${message.announce}</p>
                </div>
                <div class="card-footer bg-transparent">
                    <a href="../../${message.author.username}/profile" style="transform: none"
                       class="badge badge-${message.color?lower_case}">${message.author.username}</a>
                    <a href="/messages/${message.id}" class="stretched-link"></a>
                </div>
            </div>
        <#elseif message.filename?? && message.title != "">
            <div class="card bg-${message.color?lower_case}">
                <img src="/img/thumbs/${message.filename}" class="card-img-top">
                <h5 class="card-header alert-${message.color?lower_case}">${message.title}</h5>
                <div class="card-footer bg-transparent">
                    <a href="../../${message.author.username}/profile"
                       class="badge badge-${message.color?lower_case}">${message.author.username}</a>
                    <a href="/messages/${message.id}" class="stretched-link"></a>
                </div>
            </div>
        <#elseif message.filename?? && message.announce != "">
            <div class="card border-${message.color?lower_case}">
                <img src="/img/thumbs/${message.filename}" class="card-img-top">
                <blockquote class="blockquote mb-0 card-body">
                    <p>${message.announce}</p>
                </blockquote>
                <div class="card-footer bg-transparent">
                    <a href="../../${message.author.username}/profile"
                       class="badge badge-${message.color?lower_case}">${message.author.username}</a>
                    <a href="/messages/${message.id}" class="stretched-link"></a>
                </div>
            </div>
        <#elseif message.announce != "" && message.title == "">
            <div class="card p-3 border-${message.color?lower_case}">
                <blockquote class="blockquote mb-0 card-body">
                    <p>${message.announce}</p>
                </blockquote>
                <div class="card-footer bg-transparent">
                    <a href="../../${message.author.username}/profile"
                       class="badge badge-${message.color?lower_case}">${message.author.username}</a>
                    <a href="/messages/${message.id}" class="stretched-link"></a>
                </div>
            </div>
        <#elseif message.title != "" && message.announce != "">
            <div class="card border-${message.color?lower_case}">
                <h5 class="card-header alert-${message.color?lower_case}">${message.title}</h5>
                <div class="card-body">
                    <p class="card-text">${message.announce}</p>
                </div>
                <div class="card-footer bg-transparent">
                    <a href="../../${message.author.username}/profile"
                       class="badge badge-${message.color?lower_case}">${message.author.username}</a>
                    <a href="/messages/${message.id}" class="stretched-link"></a>
                </div>
            </div>
        <#elseif message.filename??>
            <div class="card">
                <img src="/img/thumbs/${message.filename}" class="card-img-top">
                <div class="card-footer bg-transparent">
                    <a href="../../${message.author.username}/profile"
                       class="badge badge-${message.color?lower_case}">${message.author.username}</a>
                    <a href="/messages/${message.id}" class="stretched-link"></a>
                </div>
            </div>
        </#if>
    <#else>
        </div>
        <div class="card p-3 text-center">
            <blockquote class="blockquote mb-0 card-body">
                <p>Нет сообщений</p>
            </blockquote>
        </div>
    </#list>
    </div>

    <@p.pager url page />

</#macro>