<#import "/parts/bootstrap.ftl" as e>
<#import "/parts/navbar.ftl" as nav>


<@e.main>

<script>
    /*!
 * jQuery Textarea AutoSize plugin : https://github.com/javierjulio/textarea-autosize
 * Author: Javier Julio
 * Licensed under the MIT license
 */
! function(t, e, i, n) {
  function s(e, i) {
    this.element = e, this.$element = t(e), this.init()
  }
  var h = "textareaAutoSize",
    o = "plugin_" + h,
    r = function(t) {
      return t.replace(/\s/g, "").length > 0
    };
  s.prototype = {
    init: function() {
      var i = parseInt(this.$element.css("paddingBottom")) + parseInt(this.$element.css("paddingTop")) + parseInt(this.$element.css("borderTopWidth")) + parseInt(this.$element.css("borderBottomWidth")) || 0;
      r(this.element.value) && this.$element.height(this.element.scrollHeight - i), this.$element.on("input keyup", function(n) {
        var s = t(e),
          h = s.scrollTop();
        t(this).height(0).height(this.scrollHeight - i), s.scrollTop(h)
      })
    }
  }, t.fn[h] = function(e) {
    return this.each(function() {
      t.data(this, o) || t.data(this, o, new s(this, e))
    }), this
  }
}(jQuery, window, document);

// Initialize Textarea
$('.textarea-autosize').textareaAutoSize();


</script>

<style>
    textarea.textarea-autosize {
  height: 2.25rem;
  min-height: 2.25rem;
  resize: none;
  overflow-y:hidden;
}

</style>

<@nav.bar>
<li class="nav-item active">
    <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
</li>
<li class="nav-item">
    <a class="nav-link" href="#">Link</a>
</li>
</@nav.bar>

<div class="container">
    <div class="row">
        <div class="col-sm-12 col-md-12 col-lg-10 offset-lg-1">
            <form method="post" action="add" class="form-group">
                <div class="card mt-1">
                    <div class="card-header">
                        <input required type="text" name="title" placeholder="Введите заголовок" class="form-control"
                               autocomplete="off" maxlength="300">
                    </div>
                    <div class="card-body">
                        <blockquote class="blockquote">
                        <textarea required name="text" placeholder="Введите текст"
                                  class="form-control textarea-autosize"
                                  id="textareaExample"
                                  autocomplete="off" maxlength="500" rows="1"></textarea>
                        </blockquote>
                        <button class="btn btn-outline-primary float-right btn-block" type="submit">Отправить</button>
                    </div>
                </div>
            </form>
            <#list messages as message>
            <div class="card mt-2">
                <div class="card-header">
                    ${message.title}
                </div>
                <div class="card-body">
                    <blockquote class="blockquote mb-0">
                        ${message.text}
                    </blockquote>
                </div>
                <form method="post" action="delete">
                    <input name="id" value="${message.id}" hidden>
                    <button class="btn btn-outline-primary float-right" type="submit">Удалить</button>
                </form>
            </div>

            <#else>
            <div class="card mt-2">
                <div class="card-body">
                    <blockquote class="blockquote mb-0 text-center">
                        Нет собщений
                    </blockquote>
                </div>
            </div>
        </#list>
    </div>
</div>
</@e.main>