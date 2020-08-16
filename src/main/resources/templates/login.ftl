<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>
    <div class="mb-1"><h2>Sign In</h2></div>
    <#if error??>
    <#--  alert-danger - стиль из bootstrap  -->
        <div class="alert alert-danger" role="alert">Login or password are incorrect!</div>
    </#if>
    <#if Session?? && Session.SPRING_SECURITY_LAST_EXEPTION??>
        <div class="alert alert-danger" role="alert">
            ${SPRING_SECURITY_LAST_EXEPTION.message}
        </div>
    </#if>
    <#if message??>
        <div class="alert alert-${messageType}" role="alert">
            ${message}
        </div>
    </#if>
    <@l.login "/login" false/>
</@c.page>