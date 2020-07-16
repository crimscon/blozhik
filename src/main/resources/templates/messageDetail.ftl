<#import "parts/bootstrap.ftl" as e>
<#import "parts/message/message.ftl" as msg>
<#include "parts/security.ftl">

<@e.main true>

    <#include "parts/message/messageSend.ftl">

    <#-- TODO ЗАЙМИСЬ ЭТИМ МОДУЛЕМ -->
    <@msg.detail message author />

</@e.main>