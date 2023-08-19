<#-- @ftlvariable name="message" type="com.project.mvc.model.dto.MessageDto" -->
<#if message.getFilename()?? && message.getTitle() != "" && message.getText() != "">
    <div class="card border-${message.color?lower_case}">
        <img src="/img/thumbs/${message.filename}" class="card-img-top">
        <h4 class="card-header alert-${message.color?lower_case}">${message.getTitle()}</h4>
        <div class="card-body overflow-hidden">
            <#if convertedDateMessage??>
                <p class="card-text" style="font-size:1.3rem">${message.getText()}</p>
                <p class="card-text">
                    <time style="font-size:1rem" class="text-muted">Опубликовано ${convertedDateMessage?date}</time>
                </p>
            <#else>
                <p class="card-text">${message.getAnnounce()}</p>
                <a href="/messages/${message.id}" class="stretched-link"></a>
            </#if>
        </div>
        <#include "cardFooter.ftl">
    </div>
<#elseif message.getFilename()?? && message.getTitle() != "">
    <div class="card border-${message.color?lower_case}">
        <img src="/img/thumbs/${message.filename}" class="card-img-top">
        <h4 class="card-header alert-${message.color?lower_case}">${message.getTitle()}</h4>
        <#if convertedDateMessage??>
            <p class="card-text">
                <time style="font-size:1rem" class="text-muted">Опубликовано ${convertedDateMessage?date}</time>
            </p>
        <#else>
            <a href="/messages/${message.id}" class="stretched-link"></a>
        </#if>
        <#include "cardFooter.ftl">
    </div>
<#elseif message.getFilename()?? && message.getText() != "">
    <div class="card border-${message.color?lower_case}">
        <img src="/img/thumbs/${message.filename}" class="card-img-top">
        <blockquote class="blockquote mb-0 card-body overflow-hidden">
            <#if convertedDateMessage??>
                <p style="font-size:1.3rem">${message.getText()}</p>
                <p class="card-text">
                    <time style="font-size:1rem" class="text-muted">Опубликовано ${convertedDateMessage?date}</time>
                </p>
            <#else>
                <p>${message.getAnnounce()}</p>
                <a href="/messages/${message.id}" class="stretched-link"></a>
            </#if>
        </blockquote>
        <#include "cardFooter.ftl">
    </div>
<#elseif message.getText() != "" && message.getTitle() == "">
    <div class="card border-${message.color?lower_case}">
        <blockquote class="blockquote mb-0 card-body mt-3 mx-4 mb-0 overflow-hidden">
            <#if convertedDateMessage??>
                <p style="font-size:1.3rem">${message.getText()}</p>
                <p class="card-text">
                    <time style="font-size:1rem" class="text-muted">Опубликовано ${convertedDateMessage?date}</time>
                </p>
            <#else>
                <p>${message.getAnnounce()}</p>
                <a href="/messages/${message.id}" class="stretched-link"></a>
            </#if>
        </blockquote>
        <#include "cardFooter.ftl">
    </div>
<#elseif message.getTitle() != "" && message.getAnnounce() != "">
    <div class="card border-${message.color?lower_case}">
        <h4 class="card-header alert-${message.color?lower_case}">${message.getTitle()}</h4>
        <div class="card-body overflow-hidden">
            <#if convertedDateMessage??>
                <p class="card-text" style="font-size:1.3rem">${message.getText()}</p>
                <p class="card-text">
                    <time style="font-size:1rem" class="text-muted">Опубликовано ${convertedDateMessage?date}</time>
                </p>
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
            <p class="card-text m-2">
                <time style="font-size:1rem" class="text-muted">Опубликовано ${convertedDateMessage?date}</time>
            </p>
        <#else>
            <a href="/messages/${message.id}" class="stretched-link"></a>
        </#if>

        <#include "cardFooter.ftl">
    </div>
</#if>