<#import "/parts/bootstrap.ftl" as e>
<#import "/parts/message.ftl" as m>
<#include "/parts/security.ftl">

<@e.main true>

<style>
    input[type="radio"] { display: none; } input[type="radio"] + label { font-weight: 400; font-size: 14px; } input[type="radio"] + label span { display: inline-block; width: 18px; height: 18px; margin: -2px 10px 0 0; vertical-align: middle; cursor: pointer; -moz-border-radius: 50%; border-radius: 50%; border: 3px solid #ffffff; } input[type="radio"] + label span { background-color: #fff; } input[type="radio"]:checked + label { color: #333; font-weight: 700; } input[type="radio"]:checked + label span { background-color: #ff8800; border: 2px solid #ffffff; box-shadow: 2px 2px 2px rgba(0,0,0,.1); } input[type="radio"] + label span, input[type="radio"]:checked + label span { -webkit-transition: background-color 0.24s linear; -o-transition: background-color 0.24s linear; -moz-transition: background-color 0.24s linear; transition: background-color 0.24s linear; }

</style>

<div class="container">
    <div class="row">
        <div class="col-sm-12 col-md-12 col-lg-12">
            <form method="post" action="add?${_csrf.parameterName}=${_csrf.token}" enctype="multipart/form-data" class="form-group">
                <div class="card mt-2">
                    <div class="card-header">
                        <input type="text" name="title" placeholder="Введите заголовок" class="form-control"
                               autocomplete="off" maxlength="300">
                        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    </div>
                    <div class="card-body">
                        <blockquote class="blockquote">
                            <textarea name="text" placeholder="Введите текст"
                                  class="form-control"
                                  id="textareaExample"
                                  autocomplete="off"></textarea>
                            <div class="input-group mt-3 mb-3">
                                <div class="custom-file">
                                    <input accept=".jpg, .jpeg, .png" name="picture" type="file" class="custom-file-input" id="inputGroupFile02">
                                    <label class="custom-file-label" for="inputGroupFile02" aria-describedby="inputGroupFileAddon02" >Choose file</label>
                                </div>
                            </div>
                        </blockquote>
                            <div class="input-group mt-3 mb-3">
                                <div class="btn-group btn-group-toggle" data-toggle="buttons">
                                    <label class="btn btn-light">
                                        <input type="radio" name="light" id="option1"> Белый
                                    </label>
                                    <label class="btn btn-success">
                                        <input type="radio" name="success" id="option2"> Зеленый
                                    </label>
                                    <label class="btn btn-primary">
                                        <input type="radio" name="primary" id="option3"> Синий
                                    </label>
                                </div>
                                <div class="btn-group btn-group-toggle" data-toggle="buttons">
                                    <label class="btn btn-secondary">
                                        <input type="radio" name="secondary" id="option4"> Серый
                                    </label>
                                    <label class="btn btn-danger">
                                        <input type="radio" name="danger" id="option5"> Красный
                                    </label>
                                    <label class="btn btn-warning">
                                        <input type="radio" name="warning" id="option6"> Оранжевый
                                    </label>
                                </div>
                                <div class="btn-group btn-group-toggle" data-toggle="buttons">
                                    <label class="btn btn-info">
                                        <input type="radio" name="info" id="option7"> Голубой
                                    </label>
                                    <label class="btn btn-dark">
                                        <input type="radio" name="dark" id="option8"> Черный
                                    </label>
                                </div>
                            </div>
                        <button class="btn btn-outline-primary float-right btn-block" type="submit">Отправить</button>
                    </div>
                </div>
            </form>
            <@m.message messages />
        </div>
    </div>
<div class="mb-3"></div>
</@e.main>