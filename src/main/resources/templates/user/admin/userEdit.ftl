<#import "../../parts/bootstrap.ftl" as e>
<#import "../../parts/user/userEdit.ftl" as u>
<#include "../../parts/security.ftl">

<@e.main true messageSend??>

    <div class="col-sm-12 col-md-12 col-lg-4">
        <form method="post" action="/users/${profile.getId()}/roles" class="mt-3">
            <input type="hidden" value="${_csrf.token}" name="_csrf">
            <div class="accordion" id="roles">
                <#list roles as role>
                    <div class="card">
                        <div class="card-header" id="${role}Heading">
                            <h2 class="mb-0">
                                <div class="form-check pl-0">
                                    <button class="btn btn-link text-left" type="button" data-toggle="collapse"
                                            data-target="#${role}Collapse" style="width: 100%" aria-expanded="true"
                                            aria-controls="${role}Collapse">
                                        <#if role == "ADMIN">
                                            Администратор
                                        <#elseif role == "USER">
                                            Пользователь
                                        </#if>
                                    </button>
                                    <input style="margin-top: 1rem"
                                           class="form-check-input" ${profile.roles?seq_contains(role)?string("checked", "")}
                                           name="${role}" type="checkbox"
                                           oninput="document.getElementById('saveRoles').removeAttribute('hidden')">
                                </div>
                            </h2>
                        </div>

                        <div id="${role}Collapse" class="collapse" aria-labelledby="${role}Heading"
                             data-parent="#roles">
                            <div class="card-body">
                                <#if role == "ADMIN">
                                    <ul class="list-group list-group-flush">
                                        <li class="list-group-item">Редактирование и удаление профилей</li>
                                        <li class="list-group-item">Редактирование и удаление всех сообщений</li>
                                    </ul>
                                <#elseif role == "USER">
                                    <ul class="list-group list-group-flush">
                                        <li class="list-group-item">Добавление постов</li>
                                        <li class="list-group-item">Удаление и редактирование собственных постов</li>
                                        <li class="list-group-item">Лайки</li>
                                        <li class="list-group-item">Комментарии</li>
                                        <li class="list-group-item">Подписки и подписчики</li>
                                        <li class="list-group-item">Редактирование профиля</li>
                                        <li class="list-group-item">Прикрепление картинок</li>
                                    </ul>
                                </#if>
                            </div>
                        </div>
                    </div>
                </#list>
            </div>
            <button class="btn btn-sm btn-block mt-2 btn-secondary" hidden id="saveRoles" type="submit">Сохранить
            </button>
        </form>
    </div>
    <div class="col-sm-12 col-md-12 col-lg-8">
        <@u.userEdit profile />
    </div>


</@e.main>