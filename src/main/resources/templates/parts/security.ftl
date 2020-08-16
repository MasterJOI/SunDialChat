<#--assign - позволяет определять перемнные в шаблоне freemarker
Spring security помешает freemarker в спец обьект который позволяет работать с контекстом из SpringSecurity-->
<#assign
    known = Session.SPRING_SECURITY_CONTEXT??
>

<#if known>
        <#assign
        <#--user - позволит работать непосредственно с обьектом пользователя в БД-->
            user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
            name = user.getUsername()
            isAdmin = user.isAdmin()
        >
<#else>
        <#assign
            name = "Guest"
            isAdmin = false
        >

</#if>