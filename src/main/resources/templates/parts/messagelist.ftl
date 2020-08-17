<div class="card-columns">
    <#list messages as message>
        <div class="card my-3">
            <#--Есле есть поле filename то добавляем картинку
            ?? - приводим к булевому типу-->
            <#if message.filename??>
                <img src="/img/${message.filename}" class="card-img-top">
            </#if>
            <div class="m-2">
                <span>${message.text}</span>
                <i>${message.tag}</i>
            </div>
            <div class="card-footer text-muted">
                ${message.authorName}
            </div>
        </div>
    <#else>
        No messages...
    </#list>
</div>