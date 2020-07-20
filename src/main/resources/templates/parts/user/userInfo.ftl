<div class="card mt-3">
    <div class="row p-3">
        <div class="col-lg-12">
            <h4 class="align-middle text-center text-muted display-4">
            <#if profile.getProfile_pic()??>
                <img src="/img/${profile.getProfile_pic()}" id="imageResource"
                     class="d-md d-lg-none rounded-circle" alt="${profile.getUsername()}" width="60px"
                     height="60px">
                <a href="edit" class="stretched-link"></a>
            <#else>
                <img src="/static/img/avatar.svg" id="imageResource"
                     class="d-md d-lg-none rounded-circle" height="60px" width="60px"
                     alt="${profile.getUsername()}">
                <a href="edit" class="stretched-link"></a>
            </#if>
            ${profile.getUsername()}</h4>
        </div>

    </div>
</div>
<div class="card mt-3">
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
        <#if profile.getUserProfile().getPhoneNumber()??>
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
                    <div class="text-secondary text-center align-middle">${profile.getEmail()}</div>
                </div>
            </div>
        </#if>
        <#if profile.getUserProfile().getGender()??>
            <div class="row p-3">
                <div class="col-sm-6">
                    <div class="text-center align-middle border-right">Пол</div>
                </div>
                <div class="col-sm-6">
                    <div class="text-secondary text-center align-middle">${profile.getUserProfile().getGender()}</div>
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