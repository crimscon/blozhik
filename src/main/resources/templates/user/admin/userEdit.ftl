<#import "../../parts/bootstrap.ftl" as e>

<@e.main true messageSend??>

    <form action="/users" class="mt-2" method="post">
        <div class="form-row">
            <div class="form-group col-md-6">
                <label for="username">Username</label>
                <input type="text" name="username" class="form-control" value="${user.username}" id="username">
            </div>
            <div class="form-group col-md-6">
                <label for="password">Password</label>
                <input type="password" class="form-control" id="password" name="password" value="${user.password?substring(0, 8)}">
            </div>
            <div class="form-row">
                <div class="form-group col-md-12">
                    <label>Roles</label>
                    <div class="btn-group-toggle" data-toggle="buttons">
                        <#list roles as role>
                            <label class="btn btn-outline-secondary ${user.roles?seq_contains(role)?string("active", "")}">
                                <input name="${role}"
                                       type="checkbox" ${user.roles?seq_contains(role)?string("checked", "")}> ${role}
                            </label>
                        </#list>
                    </div>
                    <input type="hidden" value="${user.id}" name="userId">
                    <input type="hidden" value="${_csrf.token}" name="_csrf">
                </div>
            </div>
        </div>
        <button class="btn btn-primary btn-lg btn-block" type="submit">Save</button>
    </form>
</@e.main>