<#--Делаем макрос что б потом можно было ипользовать такой вывод для разных данных
url - страница с сообщениями page - значение из репозитория со спискоим сообщений-->
<#macro pager url page>
<#--freemarker плохо понимает знаки равенства поэтому (больше = gt, меньше = lt)-->
<#--Если страниц больше 7-->
    <#if page.getTotalPages() gt 7>
        <#assign
        <#--Задали в переменную сколько страниц-->
        totalPages = page.getTotalPages()
        <#--Определили на какой странице мы находимся-->
        pageNumber = page.getNumber() + 1

        <#--Какие страницы будем выводить-->
        <#--В начале если страниц больше 4, то выводим 1 и ... иначе первые три-->
        head = (pageNumber > 4)?then([1, -1], [1, 2, 3])
        <#--В хвосте проверяем если мы находимся на странице более чем 3 с конца то выводим ... и последнюю страницу иначе последние 3 -->
        tail = (pageNumber < totalPages - 3)?then([-1, totalPages], [totalPages - 2, totalPages - 1, totalPages])
        <#--В странице перед той где мы проверяем, если страниц больше 4 и мы находимся от конца более чем на 1 елемент, то выводим 2 елемента перед нашей страницей иначе ничего-->
        bodyBefore = (pageNumber > 4 && pageNumber < totalPages - 1)?then([pageNumber - 2, pageNumber - 1 ], [])
        <#--Для елементов отображаемых после, если страниц больше 2 и мы находимся от конца более чем на 3 елемента, то выводим 2 елемента после иначе ничего-->
        bodyAfter = (pageNumber > 2 && pageNumber < totalPages - 3)?then([pageNumber + 1, pageNumber + 2 ], [])
        <#-- + - операция конкатенации масивов-->
        body = head + bodyBefore + (pageNumber > 3 && pageNumber < totalPages - 2)?then([pageNumber], []) + bodyAfter + tail
        >
    <#--Иначе просто выводим их-->
    <#else>
        <#assign body = 1..page.getTotalPages()>
    </#if>
    <div class="container mt-3">
        <div class="row">
            <#--Для отображения страниц используем bootstrap-->
            <ul class="pagination col justify-content-center">
                <#--disabled - метка которая никуда не ведет(надпись "Pages")-->
                <li class="page-item disabled">
                    <a class="page-link" href="#" tabindex="-1">Pages</a>
                </li>
                <#--В списке из всех страниц-->
                <#list body as p>
                <#--Проверка если мы находимся на странице то ее номер будет неактивный и ссылка никуда не ведет-->
                    <#if (p - 1) == page.getNumber()>
                        <li class="page-item active">
                            <a class="page-link" href="#" tabindex="-1">${p}</a>
                        </li>
                    <#elseif p == -1>
                        <li class="page-item disabled">
                            <a class="page-link" href="#" tabindex="-1">...</a>
                        </li>
                    <#--Иначе кнопка активна ведет на url наш + page= номер заданой страницы -->
                    <#else>
                        <li class="page-item">
                            <a class="page-link" href="${url}?page=${p - 1}&size=${page.getSize()}"
                               tabindex="-1">${p}</a>
                        </li>
                    </#if>
                </#list>
            </ul>
            <#--Доаполнительный список с возможностью менять количество елементов на странице-->
            <ul class="pagination col justify-content-center">
                <li class="page-item disabled">
                    <a class="page-link" href="#" tabindex="-1">Elements on page</a>
                </li>
                <#list [5, 10, 25, 50] as c>
                <#--Size - количество елементов на странице-->
                    <#if c == page.getSize()>
                        <li class="page-item active">
                            <a class="page-link" href="#" tabindex="-1">${c}</a>
                        </li>
                    <#else>
                        <li class="page-item">
                            <a class="page-link" href="${url}?page=${page.getNumber()}&size=${c}" tabindex="-1">${c}</a>
                        </li>
                    </#if>
                </#list>
            </ul>
        </div>
    </div>
</#macro>