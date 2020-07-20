<#if user??>
    <#import "messageEdit.ftl" as send>

    <button class="btn btn-secondary position-fixed rounded-circle floatingButton" type="button" data-toggle="modal"
            data-target="#sendMessage">
        <i class="fas fa-pen"></i>
    </button>

    <@send.messageEditForm "../../add" "" "sendMessage" />
</#if>