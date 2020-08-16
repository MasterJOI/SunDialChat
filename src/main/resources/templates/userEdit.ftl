<#import "parts/common.ftl" as c>

<@c.page>
    <h2>User Editor</h2>
    <form action="/user" method="post">
        <input type="text" value="${user.username}" name="username">
        <#list  roles as role>
            <div>
                <#--Чтобы менять роль пишем ${user.roles?seq_contains(role) - булевый метод, преобразуем в строку(checked - выбран или ничего)-->
                <label><input type="checkbox" name="${role}" ${user.roles?seq_contains(role)?string("checked", "")}>${role}</label>
            </div>
        </#list>
        <input type="hidden" value="${user.id}" name="userId">
        <input type="hidden" value="${_csrf.token}" name="_csrf">

        <button type="submit" class="btn btn-primary bg-dark border-dark">Save</button>
    </form>
</@c.page>