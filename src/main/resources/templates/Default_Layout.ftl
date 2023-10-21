<#macro myLayout>
    <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
    <html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    </head>
    <body style="width:100%;height:100%">
    <table cellspacing="0" cellpadding="0" style="width:100%;height:100%">
        <tr>
            <td colspan="2" align="center">
                <#include "Header.ftl"/>
            </td>
        </tr>
        <tr>
            <td>
                <#nested/>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <#include "Footer.ftl"/>
            </td>
        </tr>
    </table>
    </body>
    </html>
</#macro>