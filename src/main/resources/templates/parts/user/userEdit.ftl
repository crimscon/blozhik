<#macro userEdit profile>
    <form method="post"
          action="/${profile.getUsername()}/edit?${_csrf.parameterName}=${_csrf.token}"
          enctype="multipart/form-data" class="mt-3">
        <div class="form-row">
            <div class="form-group col-md-6">
                <label for="E-mail">E-mail</label>
                <input type="email" class="form-control" id="E-mail" name="email"
                       <#if profile.getEmail()??>value="${profile.getEmail()}</#if>">
            </div>
            <div class="form-group col-md-6">
                <label for="password">Password</label>
                <input type="password" class="form-control" id="password" name="password"
                       value="${profile.getPassword()}">
            </div>
        </div>
        <div class="form-row">
            <div class="form-group col-md-4">
                <label for="phoneNumber">Phone</label>
                <input type="tel" name="phoneNumber" class="form-control tel"
                        <#if profile.getUserProfile()??>
                            value="${profile.getUserProfile().getPhoneNumber()}"
                        </#if>
                       id="phone">
            </div>
            <div class="form-group col-md-4">
                <label for="dob">Date of birth</label>

                <input type="date" class="form-control" id="dob" name="dateOfBirth"
                        <#if profile.getUserProfile()?? && convertedDate??>
                    value="${convertedDate?date?iso_m("GMT+03")}"
                        </#if>>

            </div>
            <#-- TODO доделать пол!! -->
            <div class="form-group col-md-4">
                <label for="gender">Gender</label>
                <select class="custom-select" id="gender" name="gender">
                    <#list genders as gender>
                        <option
                                <#if profile.getUserProfile()?? && profile.getUserProfile().getGender()??>
                                    <#if profile.getUserProfile().getGender() == gender>
                                        selected
                                    </#if>
                                </#if> value="${gender}">${gender}</option>
                    </#list>
                </select>
            </div>
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
</#macro>