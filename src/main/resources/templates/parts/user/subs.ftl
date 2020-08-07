<div class="list-group">
    <#list subs as sub>
        <li class="list-group-item d-flex justify-content-between align-items-center">
            <div>${sub.getUsername()}</div>
            <a class="text-decoration-none text-secondary stretched-link"
               href="/${sub.getUsername()}/profile">
                <i class="fas fa-id-card"></i>
            </a>
        </li>
    </#list>
</div>


