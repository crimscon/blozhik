<#import "../pager.ftl" as p>
<#if comments.content??><h5 class="mt-3 mb-2" id="commentBlock">Комментарии: </h5></#if>

<form method="post" action="/message/${message.getId()}/addComment">
    <div class="row mb-3">
        <div class="col-8 col-lg-11 pr-0">
            <textarea name="text" class="form-control shadow-none
                                    ${(invalids?? && invalids.textError??)?string('is-invalid', '')}"
                      id="TextArea" rows="5" placeholder="Введите комментарий"
                      autocomplete="off"><#if invalids?? && invalidComment??>${invalidComment.getText()}</#if></textarea>
            <#if invalids?? && invalids.textError??>
                <div class="invalid-feedback">
                    ${invalids.textError}
                </div>
            </#if>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        </div>
        <div class="col-4 col-lg-1">
            <button class="btn btn-${message.getColor()?lower_case} btn-block p-0" style="height: 100%"
                    type="submit"><i class="fas fa-arrow-circle-right"></i>
            </button>
        </div>
    </div>
</form>

<#list comments.content as comment>
    <div class="card px-3 pt-3 pb-1 mb-2 border-${message.getColor()?lower_case}">
        <div class="media">
            <a href="/${comment.getUser().getUsername()}/profile" class="align-self-start">
                <#if comment.getUser().getProfile_pic()??>
                    <img src="/img/thumbs/${comment.getUser().getProfile_pic()}"
                         class="figure-img img-fluid rounded-circle">
                <#else>
                    <img src="/static/img/avatar.svg" id="imageResource"
                         class="align-self-center figure-img img-fluid rounded-circle" height="40px" width="40px">
                </#if>
            </a>
            <div class="media-body ml-3">
                <a class="text-decoration-none" href=/${comment.getUser().getUsername()}/profile"><h5 class="mt-0 text-muted">${comment.getUser().getUsername()}</h5></a>
                <p style="font-size:1.1rem"> ${comment.getText()}</p>
            </div>
        </div>
    </div>
<#else>
    <div class="card mt-3 p-3 text-center">
        <blockquote class="blockquote mb-0 card-body">
            <p>Нет комментариев</p>
        </blockquote>
    </div>
</#list>

<@p.pager url comments />