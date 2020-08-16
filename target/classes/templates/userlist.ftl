<#import "parts/common.ftl" as c>

<@c.page>
    <h2>List of users:</h2>

    <table>
        <thead>
        <tr>
            <th>Name</th>
            <th>Role</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <#list users as user>
            <tr>
                <td>${user.username}</td>
                <#--sep - все елементы из списка ролей будут перечислены через запятую пробел-->
                <td><#list user.roles as role>${role}<#sep>, </#list></td>
                <#--Ссылка на страницу с редактированием пользователя-->
                <td><a href="/user/${user.id}">Change</a></td>
            </tr>
        </#list>
        </tbody>

    </table>
</@c.page>