<#import "../parts/bootstrap.ftl" as e>
<#import "../parts/message/messageList.ftl" as m>

<#include "../parts/security.ftl">

<@e.main true>

    <#if currentUser.getUserProfile()??>
        <#assign
        userPhone = currentUser.getUserProfile().getPhoneNumber()
        userSex = ""
        >
    <#else>
        <#assign
        userPhone = ""
        userDoB = ""
        userSex = ""
        >
    </#if>

    <div class="mt-3 col-sm-12 col-md-12 col-lg-4">
        <div class="text-center">
            <figure class="figure">
                <#if currentUser.profile_pic??>
                    <img src="/img/${currentUser.getProfile_pic()}" id="imageresource"
                         class="figure-img img-thumbnail rounded-circle" alt="${currentUser.getUsername()}">
                <#else>
                    <svg class="bd-placeholder-img figure-img img-fluid rounded-circle" width="150" height="150"
                         xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="xMidYMid slice"
                         focusable="false"
                         role="img"><title></title>
                        <rect width="100%" height="100%" fill="#868e96"></rect>
                        <text x="50%" y="50%" fill="#dee2e6" dy=".3em"></text>
                    </svg>
                </#if>

                <figcaption class="figure-caption">${currentUser.getUsername()}</figcaption>
            </figure>
        </div>
        <div class="list-group">
            <#if name == currentUser.getUsername()>
                <a href="/${user.getUsername()}/profile/edit" class="list-group-item list-group-item-action">Редактировать
                    профиль</a>
            </#if>
            <a href="/${currentUser.getUsername()}/profile/messages" class="list-group-item list-group-item-action">Сообщения
                пользователя</a>
        </div>
    </div>
    <div class="col-sm-12 col-md-12 col-lg-8">
        <#if page??>
            <@m.message page />
        <#elseif name == currentUser.getUsername()>
            <form method="post"
                  action="/${user.getUsername()}/profile?${_csrf.parameterName}=${_csrf.token}"
                  enctype="multipart/form-data" class="mt-3">
                <div class="form-row">
                    <div class="form-group col-md-6">
                        <label for="username">Username</label>
                        <input type="text" name="username" class="form-control" value="${user.getUsername()}"
                               id="username">
                    </div>
                    <div class="form-group col-md-6">
                        <label for="password">Password</label>
                        <input type="password" class="form-control" id="password" name="password"
                               value="${user.getPassword()}">
                    </div>
                    <input type="hidden" value="${user.getId()}" name="userId">
                </div>
                <div class="form-row">
                    <div class="form-group col-md-4">
                        <label for="phoneNumber">Phone</label>
                        <input type="tel" name="phoneNumber" class="form-control tel" value="${userPhone}"
                               id="phone">
                    </div>
                    <div class="form-group col-md-4">
                        <label for="dob">Date of birth</label>
                        <#if currentUser.getUserProfile().getDateOfBirth()??>
                            <input type="date" class="form-control" id="dob" name="dateofBirth"
                                   value="${currentUser.getUserProfile().getDateOfBirth()?date?iso_m("GMT+03")}">
                        <#else>
                            <input type="date" class="form-control" id="dob" name="dateofBirth"
                                   >
                        </#if>
                    </div>
                    <#-- TODO доделать пол!! -->
                    <#--                    <div class="custom-control custom-switch">-->
                    <#--                        <input type="checkbox" class="custom-control-input" id="customSwitch1">-->
                    <#--                        <label class="custom-control-label" for="customSwitch1">Toggle this switch element</label>-->
                    <#--                    </div>-->
                </div>
                <div class="input-group mb-3">
                    <div class="custom-file">
                        <input accept=".jpg, .jpeg, .png" name="profile_pic" type="file"
                               class="custom-file-input" id="customFile">
                        <label class="custom-file-label text-truncate" for="customFile">Choose file</label>
                    </div>
                </div>
                <input type="hidden" value="${_csrf.token}" name="_csrf">
                <button class="btn btn-primary btn-lg" type="submit">Save</button>
            </form>
        </#if>
    </div>

</@e.main>