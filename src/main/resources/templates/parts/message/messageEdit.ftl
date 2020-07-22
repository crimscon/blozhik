<#macro messageEditForm url message id>

    <#if message?? && message != "">
        <#assign
        title = message.getTitle()
        text = message.getText()?replace("<br/>", "\n")?remove_beginning(" ")
        colorMessage = message.getColor()?lower_case
        >
    <#else>
        <#assign
        title = ""
        text = ""
        >
        <#if errorMessage??>
            <#assign
            colorMessage = errorMessage.color?lower_case
            >
        <#else>
            <#assign
            colorMessage = "primary"
            >
        </#if>

    </#if>

    <div class="modal fade" id="${id}" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog"
         aria-labelledby="sendMessageLabel" aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content border-${colorMessage}" id="Message">
                <form method="post" action="${url}?${_csrf.parameterName}=${_csrf.token}"
                      enctype="multipart/form-data"
                      class="form-group">
                    <div class="modal-header alert-${colorMessage}">
                        <h5 class="modal-title" id="sendMessageLabel">
                            <#if url == "../../add">Новый пост
                            <#else>Редактировать пост
                            </#if>
                        </h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true"><i class="fas fa-times"></i></span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <div class="form-group mb-3">
                            <label for="title">Заголовок:</label>
                            <input type="text" id="title" name="title" style="box-sizing: inherit;" class="form-control shadow-none
                                    ${(errors?? && (errors.titleError?? || errors.fillError??))?string('is-invalid', '')}"
                                   autocomplete="off"
                                   value="<#if errors?? && errors.url?ends_with("add") && errorMessage??>${errorMessage.getTitle()}<#else>${title}</#if>"
                            >
                            <#if errors?? && errors.titleError??>
                                <div class="invalid-feedback">
                                    ${errors.titleError}
                                </div>
                            <#elseif errors?? && errors.fillError??>
                                <div class="invalid-feedback">
                                    ${errors.fillError}
                                </div>
                            </#if>

                            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                        </div>
                        <div class="form-group">
                            <label for="TextArea">Текст:</label>
                            <textarea name="text" class="form-control shadow-none
                                    ${(errors?? && (errors.textError?? || errors.fillError??))?string('is-invalid', '')}"
                                      id="TextArea" rows="5"
                                      autocomplete="off"><#if errors?? && errors.url?ends_with("add") && errorMessage??>${errorMessage.getText()}<#else>${text}</#if></textarea>
                            <#if errors?? && errors.textError??>
                                <div class="invalid-feedback">
                                    ${errors.textError}
                                </div>
                            <#elseif errors?? && errors.fillError??>
                                <div class="invalid-feedback">
                                    ${errors.fillError}
                                </div>
                            </#if>
                        </div>
                    </div>
                    <div class="modal-footer p-1 border-0">
                        <div class="container">
                            <div class="row justify-content-center">
                                <div class="col-9 col-md-10">
                                    <div class="dropdown mb-2">
                                        <button class="rounded btn btn-outline-${colorMessage}"
                                                type="button" id="dropdownMenuButton"
                                                data-toggle="dropdown" aria-haspopup="true"
                                                aria-expanded="false">
                                            <i class="fas fa-circle"></i>
                                        </button>
                                        <div class="dropdown-menu m-0 p-0" style="min-width:0"
                                             aria-labelledby="dropdownMenuButton">
                                            <#list colors as color>
                                                <input type="radio" id="${color}" name="color"
                                                        <#if color == colorMessage?upper_case> checked </#if>
                                                       class="bg-${color?lower_case} form-check-input" value="${color}">
                                                <label for="${color}"
                                                       class="m-0 form-check-label btn btn-outline-${color?lower_case}"
                                                       style="border:none; border-radius:0">
                                                    <i class="fas fa-circle"></i>
                                                </label>
                                                <br>
                                            </#list>
                                        </div>
                                    </div>

                                    <div class="input-group ${(errors?? && (errors.filenameError?? || errors.fillError??))?string('is-invalid', '')}">
                                        <div class="mr-2 buttonFile">
                                            <button class="btn btn-outline-${colorMessage}" type="button"><i
                                                        class="fas fa-paperclip"
                                                        aria-hidden="true"></i></button>
                                        </div>
                                        <div class="custom-file">
                                            <input type="file" name="picture" accept=".jpg, .jpeg, .png"
                                                   class="custom-file-input"
                                                   id="inputFile" aria-describedby="inputGroupFileAddon01">
                                            <label id="send"
                                                   class="custom-file-label border-bottom border-${colorMessage}"
                                                   for="inputFile"></label>
                                        </div>
                                    </div>
                                    <#if errors?? && errors.filenameError??>
                                        <div class="invalid-feedback">
                                            ${errors.filenameError}
                                        </div>
                                    <#elseif errors?? && errors.fillError??>
                                        <div class="invalid-feedback">
                                            ${errors.fillError}
                                        </div>
                                    </#if>

                                    <#--                                    <div class="file-field">-->
                                    <#--                                        <a href class="float-left btn btn-outline-${colorMessage}">-->
                                    <#--                                            <i class="fas fa-paperclip" aria-hidden="true"></i>-->
                                    <#--                                            <input accept=".jpg, .jpeg, .png" name="picture" type="file"-->
                                    <#--                                                   id="inputFile">-->
                                    <#--                                            <#if errors?? && errors.filenameError??>-->
                                    <#--                                                <div class="invalid-feedback">-->
                                    <#--                                                    ${errors.filenameError}-->
                                    <#--                                                </div>-->
                                    <#--                                            <#elseif errors?? && errors.fillError??>-->
                                    <#--                                                <div class="invalid-feedback">-->
                                    <#--                                                    ${errors.fillError}-->
                                    <#--                                                </div>-->
                                    <#--                                            </#if>-->
                                    <#--                                        </a>-->
                                    <#--                                        <div class="file-path-wrapper ">-->
                                    <#--                                            <input class="file-path validate" type="text">-->
                                    <#--                                        </div>-->
                                    <#--                                    </div>-->
                                </div>

                                <#--                                <div class="custom-file }">-->
                                <#--                                    <input accept=".jpg, .jpeg, .png" name="picture" type="file" style="display:none"-->
                                <#--                                           id="inputFile">-->

                                <#--                                    <label for="inputFile" class="m-0 btn btn-outline-${colorMessage}">-->
                                <#--                                        <i class="fas fa-paperclip"></i>-->
                                <#--                                    </label>-->
                                <#--                                </div>-->
                                <div class="col-3 col-md-2">
                                    <button id="submit" class="p-2 btn btn-outline-${colorMessage}"
                                            style="height: 100%; width: 100%" type="submit"><i
                                                class="fas fa-arrow-circle-right"></i></button>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script defer>
        <#if (errors?? && (errors.textError?? || errors.titleError?? || errors.filenameError?? || errors.fillError??))>
        document.addEventListener("DOMContentLoaded", function () {

            $("#<#if errors?? && errors.url?ends_with("add")>sendMessage<#else>editMessage</#if>").modal('show');

        })
        </#if>
    </script>
</#macro>