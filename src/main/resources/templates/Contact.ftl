<#import "./Default_Layout.ftl" as layout>
<@layout.myLayout>
    <div>
        <h3>${greeting}</h3>
        <p>
            From ${message.fromName}
        </p>
        <h3>
            Comment
        </h3>
        <p>
            ${message.body}
        </p>
        <br>
        <img src="cid:logo.png" alt="https://www.javachinna.com" style="display: block;" />
    </div>
</@layout.myLayout>