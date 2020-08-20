<#import "parts/common.ftl" as c>
<@c.page>
<#--    Редактор будет отображаться только на собственной странице пользователя-->
<#--        Имя канала-->
        <h2 style="text-align: center;">${userChannel.username}</h2>
<#--        Если мы не на своей странице, то будет кнопка подписаться и отписаться(если подписан)-->
        <#if !isCurrentUser>
            <#if isSubscriber>
                <a class="btn btn-outline-danger btn-sm" href="/user/unsubscribe/${userChannel.id}">Unsubscribe</a>
            <#else>
                <a class="btn btn-outline-success btn-sm" href="/user/subscribe/${userChannel.id}">Subscribe</a>
            </#if>
        </#if>
        <div class="container-sm my-3" style="padding: 0; text-align: center;">
            <div class="row">
                <div class="col">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">Subscriptions</h5>
                            <h4 class="card-text">
                                <a class="btn btn-warning border-dark" href="/user/subscriptions/${userChannel.id}/list"><h5>${subscriptionsCount}</h5></a>
                            </h4>
                        </div>
                    </div>
                </div>
                <div class="col">
                    <div class="card">
                        <div class="card-body">
                            <h5 class="card-title">Subscribers</h5>
                            <h4 class="card-text">
                                <a class="btn btn-warning border-dark" href="/user/subscribers/${userChannel.id}/list"><h5>${subscribersCount}</h5></a>
                            </h4>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    <#if isCurrentUser>
        <#include "parts/messageEdit.ftl"/>
    </#if>
    <#include "parts/messagelist.ftl"/>

</@c.page>