<#import "parts/bootstrap.ftl" as e>
<#import "parts/message/messageList.ftl" as m>
<#include "parts/security.ftl">

<@e.main true messageSend??>

    <style>
        @media (min-width: 1200px) {
            .card-columns {
                column-count: 3;
            }
        }
    </style>

    <div class="col-sm-12 col-md-12 col-lg-12">
        <@m.message page />
    </div>
</@e.main>