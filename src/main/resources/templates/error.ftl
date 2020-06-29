<#import "/parts/bootstrap.ftl" as e>

<@e.main>
<div class="d-flex justify-content-center align-items-center" id="main">
    <#if code??>
    <h1 class="mr-3 pr-3 align-top border-right inline-block align-content-center">${code}</h1>
    <div class="inline-block align-middle">
        <h2 class="font-weight-normal lead" id="desc">Something went wrong.</h2>
    </div>
    </#if>
</div>
</@e.main>