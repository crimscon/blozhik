<#import "parts/bootstrap.ftl" as e>
<#import "parts/message/message.ftl" as msg>

<@e.main true messageSend??>
    <@msg.detail message author comments />
</@e.main>