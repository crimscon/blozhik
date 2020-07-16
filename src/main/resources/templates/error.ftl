<#import "parts/bootstrap.ftl" as e>

<@e.main false>
    <div class="d-flex justify-content-center align-items-center" style="height:100vh;width: 100vw" id="main">
        <#if code??>
            <h1 class="mr-3 pr-3 align-top border-right inline-block align-content-center">${code}</h1>
            <div class="inline-block text-center align-middle">
                <h2 class="font-weight-normal lead" id="desc">Что-то пошло не так. </h2>
                <span><a href="/">Вернуться на главную?</a></span>
            </div>
        </#if>
    </div>
</@e.main>