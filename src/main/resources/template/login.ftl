<#import "html.tpl.ftl" as html />
<#import "page.tpl.ftl" as page />

<@html.htmlTpl title="Login page">
    <@page.pageTpl>
    <h2>Sign In</h2>
        <#if message??>
        <div class="success">
        ${message}
        </div>
        </#if>
        <#if error??>
        <div class="error">
            <strong>Error:</strong> ${error}
        </div>
        </#if>
    <form action="/login" method="post">
        <dl>
            <dt>Username:
            <dd><input type="text" name="email" size="30" maxlength="50" value="${email!}">
            <dt>Password:
            <dd><input type="password" name="password" size="30">
        </dl>
        <div class="actions"><input type="submit" value="Sign In"></div>
    </form>
    </@page.pageTpl>
</@html.htmlTpl>