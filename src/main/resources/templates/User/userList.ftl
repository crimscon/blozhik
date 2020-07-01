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

List of users

<table>
    <thead>
    <tr>
        <th>Name</th>
        <th>Role</th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <#list users as user>
    <tr>
        <td>${user.username}</td>
        <td><#list user.roles as role>${role}<#sep>, </#list></td>
        <td><a href="/user/${user.id}">edit</a></td>
    </tr>
    </#list>
    </tbody>
</table>
</@e.main>