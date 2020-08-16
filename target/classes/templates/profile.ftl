<#import "parts/common.ftl" as c>

<@c.page>
    <div class="mb-1"><h2>Hello, ${username}!</h2></div>
    ${message!}
    <form method="post">
        <div class="form-group row">
            <label class="col-sm-2 col-form-label"> Change password : </label>
            <div class="col-sm-6">
                <input type="password" class="form-control" name="password" placeholder="Password"/>
            </div>
        </div>
            <div class="form-group row">
                <label class="col-sm-2 col-form-label"> Change email : </label>
                <div class="col-sm-6">
                    <input type="email" class="form-control" name="email" placeholder="example@exmpl.com" value="${email!''}"/>
                </div>
            </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button class="btn btn-primary bg-dark border-dark"
                type="submit">Save</button>
    </form>
</@c.page>