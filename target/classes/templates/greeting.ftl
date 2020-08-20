<#--Импортировали макрос-->
<#import "parts/common.ftl" as c>
<#include "parts/security.ftl">

<@c.page>
<h5>Hello <#if user??>${name}<#else>Guest</#if>, welcome to SunDialChat</h5>
    <div>This is a test project messenger:)</div>
</@c.page>
