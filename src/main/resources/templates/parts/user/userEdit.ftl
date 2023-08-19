<#macro userEdit profile>
    <form method="post" action="/${profile.getUsername()}/edit/profile" class="mt-3 position-relative"
          id="list-item-profile">
        <input type="hidden" value="${_csrf.token}" name="_csrf">
        <div class="form-group">
            <label for="dob">Дата рождения</label>

            <input type="date" class="form-control" id="dob" name="dateOfBirth"
                    <#if profile.getUserProfile()?? && convertedDate??>
                value="${convertedDate?date?iso_m("GMT+03")}"
                onchange="document.getElementById('saveProfile').removeAttribute('hidden')"
                    </#if>>

        </div>
        <div class="form-group">
            <label for="gender">Пол</label>
            <select class="custom-select" id="gender" name="gender"
                    oninput="document.getElementById('saveProfile').removeAttribute('hidden')">
                <#list genders as gender>
                    <option
                            <#if profile.getUserProfile()?? && profile.getUserProfile().getGender()??>
                                <#if profile.getUserProfile().getGender() == gender>
                                    selected
                                </#if>
                            </#if> value="${gender}"><#if gender == "MALE">Мужской<#else>Женский</#if></option>
                </#list>
            </select>
        </div>
        <button class="btn btn-sm btn-secondary" hidden id="saveProfile" type="submit">Сохранить</button>
    </form>

    <hr>

    <form method="post" action="/${profile.getUsername()}/edit/email" class="mt-3" id="list-item-email">
        <input type="hidden" value="${_csrf.token}" name="_csrf">
        <div class="form-group">
            <label for="newEmail">Электронная почта</label>
            <input type="email" required class="form-control" name="email" id="newEmail" aria-describedby="emailHelp"
                   oninput="document.getElementById('saveEmail').removeAttribute('hidden')">
            <small id="emailHelp" class="form-text text-muted">Старый адрес электронной
                почты:
                <#assign
                email = profile.getEmail()

                substringBegin = email?substring(0, 5)
                substringEnd = email?substring(email?length - 5, email?length)

                email = email?replace(substringBegin, "****")
                email = email?replace(substringEnd, "****")
                >
                <#if !springMacroRequestContext.requestUri?contains("/users/${profile.getId()}")>
                    ${email}
                <#else> ${profile.getEmail()}
                </#if>
            </small>
        </div>

        <button class="btn btn-sm btn-secondary" hidden id="saveEmail" type="submit">Сохранить</button>
    </form>

    <hr>

    <form method="post" action="/${profile.getUsername()}/edit/phone" class="mt-3" id="list-item-phone">
        <input type="hidden" value="${_csrf.token}" name="_csrf">
        <div class="form-group">
            <label for="newTel">Номер телефона</label>
            <input type="tel" required class="form-control" name="tel" id="newTel" aria-describedby="telHelp"
                   oninput="document.getElementById('savePhone').removeAttribute('hidden')">
            <small id="telHelp" class="form-text text-muted">Старый номер телефона:
                <#if profile.getUserProfile()?? && profile.getUserProfile().getPhoneNumber()?? && profile.getUserProfile().getPhoneNumber() != ''>
                    <#assign
                    phone = profile.getUserProfile().getPhoneNumber()

                    substring = phone?substring(phone?length - 4, phone?length)
                    phone = phone?replace(substring, "****")
                    >
                    <#if !springMacroRequestContext.requestUri?contains("/users/${profile.getId()}")>
                        ${phone}
                    <#else> ${profile.getUserProfile().getPhoneNumber()}
                    </#if>
                <#else>
                    не задан.
                </#if></small>
        </div>

        <button class="btn btn-sm btn-secondary" hidden id="savePhone" type="submit">Сохранить</button>
    </form>

    <hr>

    <form method="post"
          action="/${profile.getUsername()}/edit/picture?${_csrf.parameterName}=${_csrf.token}"
          enctype="multipart/form-data" class="mt-3" id="list-item-avatar">
        <input type="hidden" value="${_csrf.token}" name="_csrf">
        <div class="form-row mt-3">
            <div class="form-group">
                <#if profile.getPicture()??>
                    <a class="deletePicture" href="/${profile.getUsername()}/edit/picture/delete"><img
                                src="/img/thumbs/${profile.getPicture()}" class="rounded-circle mr-1" alt=".."></a>
                </#if>
            </div>
            <div class="form-group col">
                <div class="custom-file">
                    <input required accept=".jpg, .jpeg, .png" name="profile_pic" type="file"
                           class="custom-file-input" id="customFile"
                           oninput="document.getElementById('savePicture').removeAttribute('hidden')">
                    <label class="custom-file-label text-truncate" for="customFile">Выберите файл</label>
                </div>
            </div>
        </div>

        <button class="btn btn-sm btn-secondary" hidden id="savePicture" type="submit">Сохранить</button>
    </form>

    <hr>

    <form method="post" action="/${profile.getUsername()}/edit/password" id="list-item-pass">
        <input type="hidden" value="${_csrf.token}" class="mt-3" name="_csrf">
        <#if !springMacroRequestContext.requestUri?contains("/users/${profile.getId()}")>
            <div class="form-group">
                <label for="oldPassword">Старый пароль</label>
                <input type="password" required name="oldPassword" class="form-control" id="oldPassword"
                       autocomplete="off"
                       oninput="document.getElementById('savePassword').removeAttribute('hidden')">
            </div>
        </#if>
        <div class="form-row">
            <div class="form-group col">
                <label for="newPassword">Новый пароль</label>
                <input type="password" required name="newPassword" class="form-control" id="newPassword"
                       autocomplete="off"
                       oninput="document.getElementById('savePassword').removeAttribute('hidden')">
            </div>
            <#if !springMacroRequestContext.requestUri?contains("/users/${profile.getId()}")>
                <div class="form-group col">
                    <label for="newPasswordConfirm">Повторите пароль</label>
                    <input type="password" required name="newPasswordConfirm" class="form-control"
                           id="newPasswordConfirm"
                           autocomplete="off"
                           oninput="document.getElementById('savePassword').removeAttribute('hidden')">
                </div>
            </#if>
        </div>
        <button class="btn btn-sm btn-secondary mb-3" hidden id="savePassword" type="submit">Сохранить</button>
    </form>

</#macro>