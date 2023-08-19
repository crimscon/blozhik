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
        <div class="text-center card my-3 p-3 sticky">
            <form method="post" action="/messages/feed">
                <#if messageType == 'subs'>
                    <button class="stretched-link text-decoration-none text-secondary btn btn-block" type="submit">
                        Перейти во все сообщения
                    </button>
                <#elseif  messageType == 'all'>
                    <button class="stretched-link text-decoration-none text-secondary btn btn-block" type="submit">
                        Перейти в мою ленту
                    </button>
                </#if>
            </form>
        </div>

        <@m.message page />
    </div>
</@e.main>