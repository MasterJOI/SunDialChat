<#include "security.ftl">
<#import "pager.ftl" as p>

<@p.pager url page />
<div class="card-columns">
    <#list page.content as message>
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
            <div class="card-footer text-muted container">
                <div class="row">
                    <#--Позволит вмдеть сообщения конкретного поользователя-->
                    <a class="col align-self-center" href="/user-messages/${message.author.id}">${message.authorName}</a>
                    <a class="col align-self-center" href="/messages/${message.id}/like">
                        <#--Отображаем значек лайка (изначально пустое)-->
                        <#if message.meLiked>
                        <i class="fas fa-heart"></i>
                        <#else>
                        <i class="far fa-heart"></i>
                        </#if>
                        <#--Количество лайков-->
                        ${message.likes}
                    </a>
                    <#--Открывает страницу для редактирования собственных сообщений-->
                    <#if message.author.id == currentUserId>
                        <a class="col btn btn-outline-dark btn-sm"
                           href="/user-messages/${message.author.id}?message=${message.id}">
                            Edit
                        </a>
                    </#if>
                </div>
            </div>
        </div>
    <#else>
        No messages...
    </#list>
</div>

<@p.pager url page />