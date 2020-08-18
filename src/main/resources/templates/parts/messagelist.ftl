<#include "security.ftl">

<div class="card-columns">
    <#list messages as message>
        <div class="card my-3">
            <#--Есле есть поле filename то добавляем картинку
            ?? - приводим к булевому типу-->
            <#if message.filename??>
                <img src="/img/${message.filename}" class="card-img-top">
            </#if>
            <div class="m-2">
                <span>${message.text}</span><br/>

                <i>#${message.tag}</i>
            </div>
            <div class="card-footer text-muted">
                <#--Позволит вмдеть сообщения конкретного поользователя-->
                <a href="/user-messages/${message.author.id}">${message.authorName}</a>
                <#--Открывает страницу для редактирования собственных сообщений-->
                <#if message.author.id == currentUserId>
                <a class="btn btn-outline-dark btn-sm" href="/user-messages/${message.author.id}?message=${message.id}">
                    Edit
                </a>
                </#if>
            </div>
        </div>
    <#else>
        No messages...
    </#list>
</div>