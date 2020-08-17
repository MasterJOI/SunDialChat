<#import "parts/common.ftl" as c>
<@c.page>
<#--    Редактор будет отображаться только на собственной странице пользователя-->
    <#if isCurrentUser>
        <#include "parts/messageEdit.ftl"/>
    </#if>
    <#include "parts/messagelist.ftl"/>

</@c.page>