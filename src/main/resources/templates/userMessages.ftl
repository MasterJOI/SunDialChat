<#import "parts/common.ftl" as c>
<@c.page>
<#--    Редактор будет отображаться только на собственной странице пользователя-->
        <#if editError??>
        <div class="alert alert-danger" role="alert">
            ${editError}
        </div>
    </#if>
    <#if isCurrentUser>
        <#include "parts/messageEdit.ftl"/>
    </#if>
    <#include "parts/messagelist.ftl"/>

</@c.page>