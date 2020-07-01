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

<div class="container">
    <div class="row">
        <div class="col-sm-12 col-md-12 col-lg-10 offset-lg-1">
            ${user.username}
    </div>
</div>
<div class="mb-3"></div>
</@e.main>