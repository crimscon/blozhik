<#import "../parts/bootstrap.ftl" as e>
<#import "../parts/user/userEdit.ftl" as u>
<#include "../parts/security.ftl">

<@e.main true messageSend??>
    <#include "../parts/user/userBlock.ftl">
    <div class="col-sm-12 col-md-12 col-lg-8">
        <#include "../parts/user/userNav.ftl">
        <@u.userEdit profile />
    </div>
</@e.main>