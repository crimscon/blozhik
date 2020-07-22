<#include "../security.ftl">
<#import "../pager.ftl" as p>


<#macro message page columns>

    <div class="card-columns mt-3" id="columns">

    <script defer>
        needColumn = ${columns};
        element = document.getElementById('columns');
        columnsJS = window.getComputedStyle(element).columnCount;
        let width = (window.innerWidth > 0) ? window.innerWidth : screen.width,
            height = (window.innerHeight > 0) ? window.innerHeight : screen.height,
            min = Math.min(width, height);

        // alert(min)

        if (min < 650) needColumn = 1
        else if (min >= 650 && min <= 768) needColumn = 2

        element.style.columnCount = needColumn;
    </script>

    <#list page.content as message>
        <#include "cardChoose.ftl">
    <#else>
        </div>
        <div class="card p-3 text-center">
            <blockquote class="blockquote mb-0 card-body">
                <p>Нет сообщений</p>
            </blockquote>
        </div>
    </#list>
    </div>

    <@p.pager url page />

</#macro>