<#macro pager url page>

    <#if page.getTotalPages() gt 7>
        <#assign
        totalPages = page.getTotalPages()
        pageNumber = page.getNumber() + 1

        previous = (pageNumber > 1)?then(-4, 0)
        next = (pageNumber != totalPages)?then(-5, 0)

        head = (pageNumber > 4)?then([-2, previous, -1], [previous, 1, 2, 3])
        tail = (pageNumber < totalPages - 3)?then([-1, next, -3], [totalPages - 2, totalPages - 1, totalPages, next])
        bodyBefore = (pageNumber > 4 && pageNumber < totalPages - 1)?then([pageNumber - 2, pageNumber - 1],[])
        bodyAfter = (pageNumber > 2 && pageNumber < totalPages - 3)?then([pageNumber + 1, pageNumber + 2],[])

        body = head + bodyBefore + (pageNumber > 3 && pageNumber < totalPages - 2)?then([pageNumber], []) +  bodyAfter + tail
        >
    <#else>
        <#assign
        body = 1..page.getTotalPages()
        >
    </#if>

    <#if page.getTotalPages() gt 1>
        <nav aria-label="Page navigation">

            <#assign
            currentPage = page.getNumber()
            >
            <ul class="pagination justify-content-center">
                <#if page.getTotalPages() lt 7 && page.getNumber() + 1 gt 1>
                    <li class="page-item">
                        <a class="page-link" href="${url}?page=${currentPage - 1}&size=${page.getSize()}">
                            <i class="fas fa-chevron-left"></i>
                        </a>
                    </li>
                </#if>
                <#list body as p>
                    <#if (p - 1) == page.getNumber()>
                        <li class="page-item active">
                            <div class="page-link">${p}</div>
                        </li>
                    <#elseif p == 0>

                    <#elseif p == -1>
                        <li class="page-item disabled">
                            <div class="page-link">...</div>
                        </li>
                    <#elseif p == -2>
                        <li class="page-item">
                            <a href="${url}?page=0&size=${page.getSize()}" class="page-link">
                                <i class="fas fa-angle-double-left"></i>
                            </a>
                        </li>
                    <#elseif p == -3>
                        <li class="page-item">
                            <a href="${url}?page=${page.getTotalPages() - 1}&size=${page.getSize()}" class="page-link">
                                <i class="fas fa-angle-double-right"></i>
                            </a>
                        </li>
                    <#elseif p == -4>
                        <li class="page-item">
                            <a class="page-link" href="${url}?page=${currentPage - 1}&size=${page.getSize()}">
                                <i class="fas fa-chevron-left"></i>
                            </a>
                        </li>
                    <#elseif p == -5>
                        <li class="page-item">
                            <a class="page-link" href="${url}?page=${currentPage + 1}&size=${page.getSize()}">
                                <i class="fas fa-chevron-right"></i>
                            </a>
                        </li>
                    <#else>
                        <li class="page-item"><a class="page-link"
                                                 href="${url}?page=${p - 1}&size=${page.getSize()}">${p}</a></li>
                    </#if>
                </#list>
                <#if page.getTotalPages() lt 7 && page.getNumber() + 1 != page.getTotalPages()>
                    <li class="page-item">
                        <a class="page-link" href="${url}?page=${currentPage + 1}&size=${page.getSize()}">
                            <i class="fas fa-chevron-right"></i>
                        </a>
                    </li>
                </#if>
            </ul>
        </nav>
    </#if>

</#macro>