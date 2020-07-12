<#import "parts/bootstrap.ftl" as e>
<#import "parts/message.ftl" as m>
<#include "parts/security.ftl">

<@e.main true>

    <div class="container">
    <div class="row">
        <div class="col-sm-12 col-md-12 col-lg-12">
            <@m.message page />
        </div>
    </div>

</@e.main>