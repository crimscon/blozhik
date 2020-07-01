<#import "/parts/bootstrap.ftl" as e>
<#import "/parts/navbar.ftl" as nav>
<#import "/parts/login.ftl" as l>


<@e.main>
<@nav.bar>
<ul class="navbar-nav mr-auto">
    <li class="nav-item active">
        <a class="nav-link">Home <span class="sr-only">(current)</span></a>
    </li>
    <#if user??>
    <li class="nav-item float-right">
        <a class="nav-link" href="#">Link</a>
    </li>
</#if>
</ul>

<@l.logout />
</@nav.bar>

User editor

<form action="/user" method="post">
    <input type="text" name="username" value="${user.username}">
    <#list roles as role>
    <div>
        <label><input type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>${role}</label>
    </div>
</#list>
<input type="hidden" value="${user.id}" name="userId">
<input type="hidden" value="${_csrf.token}" name="_csrf">
<button type="submit">Save</button>
</form>
</@e.main>