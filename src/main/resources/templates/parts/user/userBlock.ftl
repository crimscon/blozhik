<div class="mt-3 d-none d-lg-block col-lg-4 pr-1">
    <div class="text-center">
        <figure class="figure figure-img img-thumbnail p-2" style="width: 100%">
            <#if profile.getPicture()??>
                <img src="/img/${profile.getPicture()}" id="imageResource"
                     class="rounded profilePic" alt="${profile.getUsername()}">
            <#else>
                <img src="/static/img/avatar.svg" id="imageResource"
                     class="rounded profilePic" alt="${profile.getUsername()}">
            </#if>
        </figure>
        <#if isCurrentUser>
            <div class="card <#if url == "/edit">bg-dark</#if>">
                <div class="row justify-content-between">
                    <div class="col-12 p-3">
                        <a class="text-decoration-none stretched-link <#if url == "/edit">text-light<#else>text-dark</#if>"
                           href="edit">
                            <i class="fas fa-user-edit"></i> Редактировать
                        </a>
                    </div>
                </div>
            </div>

            <form action="/logout" method="post">
                <button class="p-0 btn btn-link btn-block text-dark text-decoration-none" type="submit">
                    <div class="card mt-2">
                        <div class="row justify-content-between">
                            <div class="col-12 p-3">
                                <input name="_csrf" value="${_csrf.token}" hidden>
                                <i class="fas fa-sign-out-alt"></i> Выйти
                            </div>
                        </div>
                    </div>
                </button>
            </form>
        <#else>
            <#if !isCurrentUser>
                <div class="card <#if meSubscribe>bg-dark</#if>">
                    <div class="row justify-content-between">
                        <div class="col-12 p-3">
                            <#if !meSubscribe>
                                <a class="text-decoration-none stretched-link text-dark"
                                   href="subscribe">
                                    <i class="fas fa-user-alt"></i> Подписаться
                                </a>
                            <#else>
                                <a class="text-decoration-none stretched-link text-light"
                                   href="unsubscribe">
                                    <i class="fas fa-user-alt-slash"></i> Отписаться
                                </a>
                            </#if>
                        </div>
                    </div>
                </div>
            </#if>
        </#if>
    </div>
</div>