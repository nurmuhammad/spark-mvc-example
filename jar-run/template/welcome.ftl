<#import "html.tpl.ftl" as html />
<#import "page.tpl.ftl" as page />

<@html.htmlTpl title="Welcome page">
    <@page.pageTpl>
    <h2>Welcome page</h2><br/>

        <#if email??>
        Hello , ${email}!
        </div>
        </#if>

    <br/>
    <a href="/logout">Log out</a>
    </@page.pageTpl>
</@html.htmlTpl>