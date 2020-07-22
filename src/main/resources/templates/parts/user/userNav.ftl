<div class="card mt-3">
    <nav class="nav nav-pills flex-column flex-sm-row">
        <a class="flex-sm-fill text-sm-center nav-link <#if url == "/profile">active bg-dark<#else>text-dark</#if>"
           href="profile">Главная</a>
        <a class="flex-sm-fill text-sm-center nav-link <#if url == "/messages">active bg-dark<#else>text-dark</#if>"
           href="messages">Сообщения</a>
        <#if isCurrentUser>
            <a class="flex-sm-fill d-md d-lg-none text-sm-center nav-link <#if url == "/edit">active bg-dark<#else>text-dark</#if>"
               href="edit">Редактировать</a>
            <a class="flex-sm-fill d-md d-lg-none text-sm-center nav-link <#if url == "/liked">active bg-dark<#else>text-dark</#if>"
               href="liked">Понравившееся</a>
        </#if>
    </nav>
</div>