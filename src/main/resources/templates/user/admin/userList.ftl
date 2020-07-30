<#import "../../parts/bootstrap.ftl" as e>

<@e.main true messageSend??>
    <div class="col-12">
        <form method="get" action="/users">
            <div class="input-group mt-3">
                <div class="input-group-prepend">
                    <span class="input-group-text" id="basic-addon1">@</span>
                </div>
                <input type="text" class="form-control" placeholder="Username" aria-label="Username"
                       aria-describedby="basic-addon1"
                       name="filter" value="${filter?ifExists}">
                <div class="input-group-append">
                    <button class="btn btn-outline-secondary" type="submit" id="button-addon1">Search</button>
                </div>
            </div>
        </form>

        <table class="table table-striped mt-2">
            <thead>
            <tr>
                <th scope="col">ID</th>
                <th scope="col">Username</th>
                <th scope="col">Roles</th>
                <th scope="col">Edit</th>
                <th scope="col">Delete</th>
            </tr>
            </thead>
            <tbody>
            <#list users as user>
                <tr>
                    <th scope="row">${user.id}</th>
                    <td><a href="${user.username}/profile">${user.username}</a></td>
                    <td><#list user.roles as role>${role}<#sep>, </#list></td>
                    <td><a class="btn btn-sm btn-primary" href="/users/${user.id}">Edit</a></td>
                    <td>
                        <form action="/users/${user.id}/delete" method="post">
                            <input type="hidden" value="${_csrf.token}" name="_csrf">
                            <button class="btn btn-sm btn-danger" type="submit">Delete</button>
                        </form>
                    </td>
                    <!--                <td>-->
                    <!--                    <form action="delete" method="post">-->
                    <!--                        <input hidden name="userId" value="">-->
                    <!--
                   -->
                    <!--                    </form>-->
                    <!--                </td>-->
                </tr>
            <#else>
                <tr>
                    <th scope="row"></th>
                    <td></td>
                    <td></td>
                    <td></td>
                    <!--                <td>-->
                    <!--                    <form action="delete" method="post">-->
                    <!--                        <input hidden name="userId" value="${user.id}">-->
                    <!--                        <button type="submit">Delete</button>-->
                    <!--                    </form>-->
                    <!--                </td>-->
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</@e.main>