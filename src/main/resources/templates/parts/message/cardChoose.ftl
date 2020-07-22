<#if message.getFilename()?? && message.getTitle() != "" && message.getText() != "">
    <div class="card border-${message.color?lower_case}">
        <img src="/img/thumbs/${message.filename}" class="card-img-top">
        <h5 class="card-header alert-${message.color?lower_case}">${message.getTitle()}</h5>
        <div class="card-body">
            <#if convertedDateMessage??>
                <p class="card-text">${message.getText()}</p>
                <p class="card-text"><small class="text-muted">Опубликовано ${convertedDateMessage?date}</small></p>
            <#else>
                <p class="card-text">${message.getAnnounce()}</p>
                <a href="/messages/${message.id}" class="stretched-link"></a>
            </#if>
        </div>
        <#include "cardFooter.ftl">
    </div>
<#elseif message.getFilename()?? && message.getTitle() != "">
    <div class="card bg-${message.color?lower_case}">
        <img src="/img/thumbs/${message.filename}" class="card-img-top">
        <h5 class="card-header alert-${message.color?lower_case}">${message.getTitle()}</h5>
        <#if convertedDateMessage??>
            <p class="card-text"><small class="text-muted">Опубликовано ${convertedDateMessage?date}</small></p>
        <#else>
            <a href="/messages/${message.id}" class="stretched-link"></a>
        </#if>
        <#include "cardFooter.ftl">
    </div>
<#elseif message.getFilename()?? && message.getText() != "">
    <div class="card border-${message.color?lower_case}">
        <img src="/img/thumbs/${message.filename}" class="card-img-top">
        <blockquote class="blockquote mb-0 card-body">
            <#if convertedDateMessage??>
                <p>${message.getText()}</p>
                <p class="card-text"><small class="text-muted">Опубликовано ${convertedDateMessage?date}</small></p>
            <#else>
                <p>${message.getAnnounce()}</p>
                <a href="/messages/${message.id}" class="stretched-link"></a>
            </#if>
        </blockquote>
        <#include "cardFooter.ftl">
    </div>
<#elseif message.getText() != "" && message.getTitle() == "">
    <div class="card border-${message.color?lower_case}">
        <blockquote class="blockquote mb-0 card-body mt-3 mx-4 mb-0">
            <#if convertedDateMessage??>
                <p>${message.getText()}</p>
                <p class="card-text"><small class="text-muted">Опубликовано ${convertedDateMessage?date}</small></p>
            <#else>
                <p>${message.getAnnounce()}</p>
                <a href="/messages/${message.id}" class="stretched-link"></a>
            </#if>
        </blockquote>
        <#include "cardFooter.ftl">
    </div>
<#elseif message.getTitle() != "" && message.getAnnounce() != "">
    <div class="card border-${message.color?lower_case}">
        <h5 class="card-header alert-${message.color?lower_case}">${message.getTitle()}</h5>
        <div class="card-body">
            <#if convertedDateMessage??>
                <p class="card-text">${message.getText()}</p>
                <p class="card-text"><small class="text-muted">Опубликовано ${convertedDateMessage?date}</small></p>
            <#else>
                <p class="card-text">${message.getAnnounce()}</p>
                <a href="/messages/${message.id}" class="stretched-link"></a>
            </#if>
        </div>
        <#include "cardFooter.ftl">
    </div>
<#elseif message.getFilename()??>
    <div class="card">
        <img src="/img/thumbs/${message.filename}" class="card-img-top">

        <#if convertedDateMessage??>
            <p class="card-text m-2"><small class="text-muted">Опубликовано ${convertedDateMessage?date}</small></p>
        <#else>
            <a href="/messages/${message.id}" class="stretched-link"></a>
        </#if>

        <#include "cardFooter.ftl">
    </div>
</#if>