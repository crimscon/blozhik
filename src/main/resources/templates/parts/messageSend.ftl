<style>

    input[type="radio"] {
        display: none;
    }

    input[type="radio"] + label {
        font-weight: 400;
        font-size: 14px;
    }

    input[type="radio"] + label span {
        display: inline-block;
        width: 18px;
        height: 18px;
        margin: -2px 10px 0 0;
        vertical-align: middle;
        cursor: pointer;
        -moz-border-radius: 50%;
        border-radius: 50%;
        border: 3px solid #ffffff;
    }

    input[type="radio"] + label span {
        background-color: #fff;
    }

    input[type="radio"]:checked + label {
        font-weight: 700;
    }

    input[type="radio"]:checked + label span {
        background-color: #ff8800;
        border: 2px solid #ffffff;
        box-shadow: 2px 2px 2px rgba(0, 0, 0, .1);
    }

    input[type="radio"] + label span, input[type="radio"]:checked + label span {
        -webkit-transition: background-color 0.24s linear;
        -o-transition: background-color 0.24s linear;
        -moz-transition: background-color 0.24s linear;
        transition: background-color 0.24s linear;
    }

    .floatingButton {
        width: 50px;
        height: 50px;
        bottom: 20px;
        right: 20px;
        box-shadow: 2px 2px 3px #999;
        z-index: 100;
    }

</style>

<script>
    function changeColor() {
        const id = $(this).attr("id");
        const button = $("#dropdownMenuButton");
        if (changeColor.last) button.removeClass(changeColor.last);
        const currentColor = "btn-outline-" + id.toLowerCase();
        changeColor.last = currentColor;
        $("#dropdownMenuButton").addClass(currentColor);
    }

    changeColor.last = "btn-outline-primary"

    window.onload = function () {

        $(".dropdown-menu").children("input").click(changeColor);

    }
</script>

<button class="btn btn-primary position-fixed rounded-circle floatingButton" type="button" data-toggle="modal"
        data-target="#sendMessage">
    <i class="fas fa-pen"></i>
</button>

<div class="modal fade" id="sendMessage" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog"
     aria-labelledby="sendMessageLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-scrollable">
        <div class="modal-content">
            <form method="post" action="../../add?${_csrf.parameterName}=${_csrf.token}" enctype="multipart/form-data"
                  class="form-group">
                <div class="modal-header">
                    <h5 class="modal-title" id="sendMessageLabel">Новый пост</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true"><i class="fas fa-times"></i></span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="form-group mb-3">
                        <label for="title">Заголовок:</label>
                        <input type="text" id="title" name="title" class="form-control" autocomplete="off" maxlength="300">
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    </div>
                    <div class="form-group">
                        <label for="TextArea">Текст:</label>
                        <textarea name="text" class="form-control"
                                  id="TextArea" rows="5" autocomplete="off"></textarea>
                    </div>
                </div>
                <div class="modal-footer p-1 border-0">
                    <div class="col-sm-12">
                        <div class="input-group">
                            <div class="dropdown mr-2">
                                <button class="rounded btn btn-outline-primary"
                                        type="button" id="dropdownMenuButton"
                                        data-toggle="dropdown" aria-haspopup="true"
                                        aria-expanded="false">
                                    <i class="fas fa-circle"></i>
                                </button>
                                <div class="dropdown-menu m-0 p-0" style="min-width:0"
                                     aria-labelledby="dropdownMenuButton">
                                    <#list colors as color>
                                        <#if color == "PRIMARY">
                                            <input type="radio" id="${color}" name="color" checked
                                                   class="bg-${color?lower_case} form-check-input" value="${color}">
                                            <label for="${color}"
                                                   class="m-0 form-check-label btn btn-outline-${color?lower_case}"
                                                   style="border:none; width:100%; border-radius:0">
                                                <i class="fas fa-circle"></i>
                                            </label>
                                        <#else>
                                            <input type="radio" id="${color}" name="color"
                                                   class="bg-${color?lower_case} form-check-input" value="${color}">
                                            <label for="${color}"
                                                   class="m-0 form-check-label btn btn-outline-${color?lower_case}"
                                                   style="border:none; width:100%; border-radius:0">
                                                <i class="fas fa-circle"></i>
                                            </label>
                                        </#if>
                                    </#list>
                                </div>
                            </div>
                            <div class="custom-file">
                                <input accept=".jpg, .jpeg, .png" name="picture" type="file" style="display:none"
                                       id="inputFile">
                                <label for="inputFile" class="btn btn-outline-primary m-0">
                                    <i class="fas fa-paperclip"></i>
                                </label>
                            </div>
                            <button class="btn btn-outline-primary float-right" type="submit"><i
                                        class="fas fa-arrow-circle-right"></i></button>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>