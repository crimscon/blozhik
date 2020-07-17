<div class="card mt-3">
    <div class="row p-3">
        <div class="col-sm-12">
            <h4 class="align-middle text-center text-muted display-4">${profile.getUsername()}</h4>
        </div>
    </div>
</div>
<div class="card mt-3">
    <#if profile.getUserProfile()??>
        <#if profile.getUserProfile().getDateOfBirth()??>
            <div class="row p-3">
                <div class="col-sm-6">
                    <div class="text-center align-middle border-right">Дата рождения</div>
                </div>
                <div class="col-sm-6">
                    <div class="text-secondary text-center align-middle">${profile.getUserProfile().getDateOfBirth()}</div>
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
    <#--        <#if profile.getUserProfile().getEmail()??>-->
    <#--            <div class="row p-3">-->
    <#--                <div class="col-sm-6">-->
    <#--                    <div class="text-center align-middle border-right">Электронная почта</div>-->
    <#--                </div>-->
    <#--                <div class="col-sm-6">-->
    <#--                    <div class="text-secondary text-center align-middle">${profile.getUserProfile().getEmail()}</div>-->
    <#--                </div>-->
    <#--            </div>-->
    <#--        </#if>-->
    <#--        <#if profile.getUserProfile().getGender()??>-->
    <#--            <div class="row p-3">-->
    <#--                <div class="col-sm-6">-->
    <#--                    <div class="text-center align-middle border-right">Пол</div>-->
    <#--                </div>-->
    <#--                <div class="col-sm-6">-->
    <#--                    <div class="text-secondary text-center align-middle">${profile.getUserProfile().getGender()}</div>-->
    <#--                </div>-->
    <#--            </div>-->
    <#--        </#if>-->
    <#else>
        <div class="row p-3">
            <div class="col-sm-12">
                <div class="text-muted align-middle text-center border-right">Нет информации</div>
            </div>
        </div>
    </#if>
</div>