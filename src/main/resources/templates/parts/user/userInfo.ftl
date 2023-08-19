<div class="card mt-3 py-2 px-3">
    <div class="row no-gutters">
        <div class="col-lg-12 mb-2">
            <h4 class="align-middle text-center text-muted display-3">
                <#if profile.getPicture()??>
                    <img src="/img/${profile.getPicture()}" id="imageResource"
                         class="d-md d-lg-none rounded-circle" alt="${profile.getUsername()}" width="60px"
                         height="60px">
                <#else>
                    <img src="/static/img/avatar.svg" id="imageResource"
                         class="d-md d-lg-none rounded-circle" height="60px" width="60px"
                         alt="${profile.getUsername()}">
                </#if>
                ${profile.getUsername()} </h4>
            <#if isCurrentUser>
                <i class="far fa-edit d-md d-lg-none"
                   style="font-size: 0.9rem; color: lightslategray; position: absolute; top: 5px; right: 0"></i>
                <a href="edit" class="stretched-link d-md d-lg-none"></a>
            </#if>
        </div>
    </div>
    <#if !isCurrentUser>
        <div class="card-footer bg-transparent position-relative text-center d-md-block d-lg-none">
            <div class="row justify-content-between">
                <div class="col-12 p-3">
                    <#if !meSubscribe>
                        <a class="text-decoration-none stretched-link text-dark"
                           href="subscribe">
                            <i class="fas fa-user-alt"></i> Подписаться
                        </a>
                    <#else>
                        <a class="text-decoration-none stretched-link text-dark"
                           href="unsubscribe">
                            <i class="fas fa-user-alt-slash"></i> Отписаться
                        </a>
                    </#if>
                </div>
            </div>
        </div>
    </#if>
</div>

<div class="card-columns mt-3">
    <div class="card p-3">
        <div class="align-middle text-center text-muted">
            <h6>Подписки</h6>
            <a class="stretched-link text-decoration-none" href="subscriptions/list">${subscriptions}</a>
        </div>
    </div>
    <div class="card p-3">
        <div class="align-middle text-center text-muted">
            <h6>Подписчики</h6>
            <a class="stretched-link text-decoration-none" href="subscribers/list">${subscribers}</a>
        </div>
    </div>
    <div class="card p-3">
        <div class="align-middle text-center text-muted">
            <h6>Публикации</h6>
            <a class="stretched-link text-decoration-none" href="messages">${messagesCount}</a>
        </div>
    </div>
    <div class="card p-3">
        <div class="align-middle text-center text-muted">
            <h6>Понравившееся</h6>
            <a class="stretched-link text-decoration-none" href="liked">${likedCount}</a>
        </div>
    </div>
</div>

<div class="card mb-3">
    <#if profile.getUserProfile()??>
        <#if convertedDate??>
            <div class="row p-3">
                <div class="col-sm-6">
                    <div class="text-center align-middle border-right">Дата рождения</div>
                </div>
                <div class="col-sm-6">
                    <div class="text-secondary text-center align-middle">${convertedDate?date}</div>
                </div>
            </div>
        </#if>
        <#if profile.getUserProfile().getPhoneNumber()?? && profile.getUserProfile().getPhoneNumber() != ''>
            <div class="row p-3">
                <div class="col-sm-6">
                    <div class="text-center align-middle border-right">Номер телефона</div>
                </div>
                <div class="col-sm-6">
                    <div class="text-secondary text-center align-middle"><a class="text-secondary"
                                                                            href="tel:${profile.getUserProfile().getPhoneNumber()}">${profile.getUserProfile().getPhoneNumber()}</a>
                    </div>
                </div>
            </div>
        </#if>
        <#if profile.getEmail()??>
            <div class="row p-3">
                <div class="col-sm-6">
                    <div class="text-center align-middle border-right">Электронная почта</div>
                </div>
                <div class="col-sm-6">
                    <div class="text-secondary text-center align-middle"><a class="text-secondary"
                                                                            href="mailto:${profile.getEmail()}">${profile.getEmail()}</a>
                    </div>
                </div>
            </div>
        </#if>
        <#if profile.getUserProfile().getGender()??>
            <div class="row p-3">
                <div class="col-sm-6">
                    <div class="text-center align-middle border-right">Пол</div>
                </div>
                <div class="col-sm-6">
                    <div class="text-secondary text-center align-middle"><#if profile.getUserProfile().getGender() == "MALE">Мужской<#else>Женский</#if></div>
                </div>
            </div>
        </#if>
    <#else>
        <div class="row p-3">
            <div class="col-sm-12">
                <div class="text-muted align-middle text-center">Нет информации</div>
            </div>
        </div>
    </#if>
</div>