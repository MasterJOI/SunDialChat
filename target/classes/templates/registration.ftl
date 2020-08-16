<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>
    <div class="mb-1"><h2>Sign Up</h2></div>
    ${message!} <!--Если присутствует поле message, то показываем его-->
    <@l.login "/registration" true/>
</@c.page>