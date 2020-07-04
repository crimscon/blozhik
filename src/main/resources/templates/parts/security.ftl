<#assign
session = Session.SPRING_SECURITY_CONTEXT??
>

<#if session>
<#assign
user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
name = user.getUsername()
isAdmin = user.isAdmin()
>
<#else>
<#assign
name = ""
isAdmin = false
>
</#if>