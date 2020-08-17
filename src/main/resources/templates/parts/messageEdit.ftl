<a class="btn btn-primary bg-dark border-dark" data-toggle="collapse" href="#collapseExample" role="button"
   aria-expanded="false"
   aria-controls="collapseExample">
    Message editor
</a>
<div class="collapse <#if message??>show</#if>" id="collapseExample">
    <div class="form-group mt-3">
        <form method="post" enctype="multipart/form-data">
            <div class="form-group">
                <#-- Если есть textError - будет true, то строкой выводим 'is-invalid', иначе ничего-->
                <input type="text" class="form-control ${(textError??)?string('is-invalid', '')}"
                       value="<#if message??>${message.text}</#if>" name="text" placeholder="Write your message"/>
                <#if textError??>
                    <div class="invalid-feedback">
                        ${textError}
                    </div>
                </#if>
            </div>
            <div class="form-group">
                <input type="text" class="form-control ${(tagError??)?string('is-invalid', '')}"
                       value="<#if message??>${message.tag}</#if>" name="tag" placeholder="Tag"/>
                <#if tagError??>
                    <div class="invalid-feedback">
                        ${tagError}
                    </div>
                </#if>
            </div>
            <div class="custom-file">
                <input type="file" name="file" id="customFile">
                <label class="custom-file-label" for="customFile">Choose file</label>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <#--Если есть сообщение, то добавляем к нему id что бы потом делать запросы на его редактирование-->
            <input type="hidden" name="id" value="<#if message??>${message.id}</#if>"/>
            <div class="form-group mt-3">
                <button type="submit" class="btn btn-primary bg-dark border-dark">Save message</button>
            </div>
        </form>
    </div>
</div>