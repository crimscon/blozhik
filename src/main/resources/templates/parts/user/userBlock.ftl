<div class="mt-3 col-sm-12 col-md-12 col-lg-4 pr-1">
    <div class="text-center">
        <figure class="figure figure-img img-thumbnail p-2" style="width: 100%">
            <#if profile.getProfile_pic()??>
                <img src="/img/${profile.getProfile_pic()}" id="imageResource"
                     class="rounded profilePic" alt="${profile.getUsername()}">
            <#else>
                <svg class="bd-placeholder-img img-fluid rounded-circle" width="150" height="150"
                     xmlns="http://www.w3.org/2000/svg" preserveAspectRatio="xMidYMid slice"
                     focusable="false"
                     role="img"><title></title>
                    <rect width="100%" height="100%" fill="#868e96"></rect>
                    <text x="50%" y="50%" fill="#dee2e6" dy=".3em"></text>
                </svg>
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