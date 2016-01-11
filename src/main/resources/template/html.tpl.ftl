<#macro htmlTpl title="Welcome">
<!doctype html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>${title}</title>
    <#if meta??>${meta}</#if>
    <#if css??>${css}</#if>
    <#if js??>${js}</#if>
    <link rel="stylesheet" type="text/css" href="/css/pure-min.css">
</head>
    <#nested />
</html>
</#macro>