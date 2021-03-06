<!-- freemarker macros have to be imported into a namespace.  We strongly
recommend sticking to 'spring' -->
<#import "spring.ftl" as spring />
<#import "macro/iccs.ftl" as iccs/>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html xmlns="http://www.w3.org/1999/xhtml" xmlns="http://www.w3.org/1999/html">

<head>
    <title>Identity Representation</title>
    <link rel="stylesheet/less" type="text/css" href="<@spring.url "/static/css/main.less"/>">
    <script src="<@spring.url "/static/js/less-1.1.3.min.js" />" type="text/javascript"></script>
    <script src="<@spring.url "/static/js/stardom.js" />" type="text/javascript"></script>
    <script src="<@spring.url "/static/js/jquery-1.6.1-min.js"/>" type="text/javascript"></script>
    <script src="<@spring.url "/static/js/jquery.timers-1.2.js"/>" type="text/javascript"></script>
    <script src="<@spring.url "/static/js/jquery-ui-1.8.17.custom.min.js"/>" type="text/javascript"></script>
    <script src="<@spring.url "/static/js/slimScroll.min.js"/>" type="text/javascript"></script>
</head>
<body>

<div class="container">

    <div class="tabswrapper padding-10">
        <h1>An access code has been sent to <strong>${email}</strong></h1>
        <p>Please check you <strong>junk mail</strong></p>
        <a class="nice-button center" href="<@spring.url "/login" />" >Login</a>
    </div>

</body>
