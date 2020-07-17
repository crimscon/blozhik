<#import "../parts/bootstrap.ftl" as e>
<#import "../parts/message/messageList.ftl" as m>
<#include "../parts/security.ftl">

<@e.main true messageSend??>
    <#include "../parts/user/userBlock.ftl">
    <div class="col-sm-12 col-md-12 col-lg-8">
        <#include "../parts/user/userNav.ftl">
        <@m.message page />
    </div>
</@e.main>