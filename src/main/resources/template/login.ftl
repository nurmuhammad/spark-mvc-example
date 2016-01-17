<#import "html.tpl.ftl" as html />
<#import "page.tpl.ftl" as page />

<@html.htmlTpl title="Login page">
    <@page.pageTpl>
    <div class="login">

        <h1>Sign In</h1>
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

            <input type="text" name="email" size="30" class="login-input" maxlength="50" value="${email!}"
                   placeholder="Email Address" autofocus/>
            <input type="password" name="password" size="30" class="login-input" placeholder="Password"/>

            <input type="checkbox" id="remember-me" name="remember-me" size="30" value="remember-me"/>
            <label for="remember-me">Remember me</label>
            <#if r??>
                <input type="hidden" name="r" value="${r}">
            </#if>

            <div class="actions"><input type="submit" value="Sign In" class="login-submit"></div>
        </form>
    </div>
    </@page.pageTpl>
</@html.htmlTpl>