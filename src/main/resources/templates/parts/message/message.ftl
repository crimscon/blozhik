<#macro detail message author>
    <div class="col-lg-12">

    <!-- Title -->
    <#if message.title??>
        <h1 class="mt-4">${message.title}</h1>
    </#if>
    <!-- Author -->
    <p class="lead">
        <div class="container">
            <div class="row">
                <#if message.author.profile_pic??>
                    <article>
                        <div class="photo">
                            <img class="personPhoto" alt="" src="/img/${author.profile_pic}">
                        </div>
                    </article>
                </#if>
                <a href="../../${message.author.username}/profile">${author.username}</a>
            </div>
        </div>
    </p>

    <hr>

    <!-- Date/Time -->
    <p>Posted on January 1, 2019 at 12:00 PM</p>

    <hr>

    <#if message.filename??>
        <img class="img-fluid rounded" src="/img/${message.filename}" alt="">

        <hr>
    </#if>

    <#if message.text??>
        <p>${message.text}</p>
    </#if>
</#macro>