<#import "parts/common.ftl" as c>


<@c.page>
    <div class="form-row mb-3">
        <form method="get" action="/main" class="form-inline">
            <input class="form-control" type="text" name="filter" value="${filter!}" placeholder="Search by tag">
            <button type="submit" class="btn btn-primary ml-2 bg-dark border-dark">Search</button>
        </form>
    </div>
    <#include "parts/messageEdit.ftl"/>

    <#include "parts/messagelist.ftl"/>

</@c.page>