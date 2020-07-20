<#import "messageEdit.ftl" as edit>

<div class="card-footer bg-transparent position-relative">
    <div class="row justify-content-between">
        <div class="<#if availableEdit??>col<#else>col-5</#if> pl-1">
            <div class="btn-group" role="group" aria-label="Basic example">
                <a href="/messages/${message.getId()}/like" class="btn btn-outline-${message.getColor()?lower_case}">
                    <#if message.getMeLiked()>
                        <i class="fas fa-heart"></i>
                    <#else>
                        <i class="far fa-heart"></i>
                    </#if>
                    ${message.getLikes()}</a>
                <#if availableEdit??>
                    <#if availableEdit>
                        <button class="btn btn-outline-${message.getColor()?lower_case}" type="button" data-toggle="modal"
                                data-target="#editMessage">
                            <i class="far fa-edit"></i>
                        </button>
                        <@edit.messageEditForm "/messages/${message.getId()}/edit" message "editMessage"/>

                        <a href="/messages/${message.getId()}/delete" class="btn btn-outline-${message.getColor()?lower_case}"><i
                                    class="far fa-trash-alt"></i></a>
                    </#if>
                </#if>
            </div>
        </div>
        <div class="<#if availableEdit??>col<#else>col-7</#if> pr-1">
            <div class="media">
                <div class="media-body pr-1">
                    <#if availableEdit??>
                        <h6 class="mt-0 mr-1 mb-0 text-right align-middle">
                            <a href="../../${message.getAuthor().getUsername()}/profile"
                               class="text-${message.getColor()?lower_case}">${message.getAuthor().getUsername()}</a>
                        </h6>
                        <div class="mb-0 text-right">
                            <div class="text-${message.getColor()?lower_case}"><i
                                        class="far fa-eye"></i> ${message.getViewers()}</div>
                        </div>
                    <#else>
                        <h6 class="mr-1 mb-0 text-right align-middle mt-2">
                            <a href="../../${message.getAuthor().getUsername()}/profile"
                               class="text-${message.getColor()?lower_case} text-decoration-none">${message.getAuthor().getUsername()}</a>
                        </h6>
                    </#if>
                </div>
                <#if message.getAuthor().getProfile_pic()??>
                    <a href="../../${message.getAuthor().getUsername()}/profile"><img src="/img/thumbs/${message.getAuthor().getProfile_pic()}"
                         class="align-self-center img-fluid rounded-circle" alt="..."></a>
                <#else>
                    <a href="../../${message.getAuthor().getUsername()}/profile"><img src="/static/img/avatar.svg" id="imageResource"
                         class="rounded-circle" height="40px" width="40px"></a>
                </#if>
            </div>
        </div>
    </div>
</div>