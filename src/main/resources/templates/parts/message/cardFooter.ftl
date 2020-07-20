<div class="card-footer bg-transparent position-relative">
    <div class="row justify-content-between">
        <div class="col pl-1">
            <div class="btn-group" role="group" aria-label="Basic example">
                <a href="/messages/${message.getId()}/like" class="btn btn-outline-${message.getColor()?lower_case}">
                    <#if message.getMeLiked()>
                        <i class="fas fa-heart"></i>
                    <#else>
                        <i class="far fa-heart"></i>
                    </#if>
                    ${message.getLikes()}</a>
                <#if availableEdit??>
                    <#if availableEdit || isAdmin>
                        <a href="/messages/${message.getId()}/edit" class="btn btn-outline-${message.getColor()?lower_case}"><i
                                    class="far fa-edit"></i></a>
                        <a href="/messages/${message.getId()}/delete" class="btn btn-outline-${message.getColor()?lower_case}"><i
                                    class="far fa-trash-alt"></i></a>
                    </#if>
                </#if>
            </div>
        </div>
        <div class="col-5 pr-1">
            <div class="media">
                <div class="media-body">
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
                        <h6 class="mt-0 mb-0 text-right align-middle mt-2">
                            <a href="../../${message.getAuthor().getUsername()}/profile"
                               class="text-${message.getColor()?lower_case} text-decoration-none">${message.getAuthor().getUsername()}</a>
                        </h6>
                    </#if>
                </div>
                <#if message.getAuthor().getProfile_pic()??>
                    <img src="/img/thumbs/${message.getAuthor().getProfile_pic()}"
                         class="align-self-center ml-2 img-fluid rounded-circle" alt="...">
                <#else>
                    <img src="/static/img/avatar.svg" id="imageResource"
                         class="rounded-circle ml-2" height="40px" width="40px">
                </#if>
            </div>
        </div>
    </div>
</div>