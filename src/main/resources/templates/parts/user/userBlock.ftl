<div class="mt-3 col-sm-12 col-md-12 col-lg-4 pr-1">
    <div class="text-center">
        <figure class="figure figure-img img-thumbnail p-2" style="width: 100%">
            <#if profile.getProfile_pic()??>
                <img src="/img/${profile.getProfile_pic()}" id="imageResource"
                     class="rounded profilePic" alt="${profile.getUsername()}">
            <#else>
                <img src="/static/img/avatar.svg" id="imageResource"
                     class="rounded profilePic" alt="${profile.getUsername()}">
            </#if>
            <#if isCurrentUser>
                <a href="edit" class="btn alert-dark border-light btn-block mt-2 mb-2 <#if url == "/edit">active</#if>">
                    Редактировать</a>
            </#if>
        </figure>
    </div>
    <div class="list-group">

    </div>
</div>