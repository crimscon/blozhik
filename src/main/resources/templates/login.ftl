<#import "parts/bootstrap.ftl" as e>
<#import "parts/login.ftl" as l>

<@e.main false messageSend??>
    <@l.login "/login" />
</@e.main>