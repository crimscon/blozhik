<#import "/parts/bootstrap.ftl" as e>
<#import "/parts/login.ftl" as l>

<@e.main>
Add new user
<#if message??>
    ${message}
</#if>
<@l.login "/registration" />
</@e.main>