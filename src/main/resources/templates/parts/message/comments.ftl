<form method="post" action="/messages/${message.getId()}/addComment">
    <div class="row mt-3">
        <div class="col-10 pr-0">
            <textarea name="text" class="form-control shadow-none
                                    ${(invalids?? && invalids.textError??)?string('is-invalid', '')}"
                      id="TextArea" rows="2" placeholder="Введите комментарий"
                      autocomplete="off"><#if invalids?? && invalidComment??>${invalidComment.getText()}</#if></textarea>
            <#if invalids?? && invalids.textError??>
                <div class="invalid-feedback">
                    ${invalids.textError}
                </div>
            </#if>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        </div>
        <div class="col-2">
            <button class="btn btn-outline-${message.getColor()?lower_case} btn-block" style="height: 100%"
                    type="submit">Отправить
            </button>
        </div>
    </div>
</form>

<#if comments.content??><h5 class="mt-3" id="commentBLock">Комментарии: </h5></#if>
<#list comments.content as comment>
    <div class="card p-3 mt-2">
        <div class="media">
            <#if comment.getUser().getProfile_pic()??>
                <img src="/img/thumbs/${comment.getUser().getProfile_pic()}" class="mr-3 rounded-circle">
            <#else>
                <img src="/static/img/avatar.svg" id="imageResource"
                     class="rounded-circle" height="40px" width="40px">
            </#if>
            <div class="media-body ml-2">
                <h5 class="mt-0"><a
                            href="/${comment.getUser().getUsername()}/profile">${comment.getUser().getUsername()}</a>
                </h5>
                ${comment.getText()}
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