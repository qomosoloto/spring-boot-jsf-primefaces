var Photo = {


    /**
     *
     * @param options
     */
    createUploader: function (button, container, token) {

        Qiniu.uploader({
            runtimes: 'html5,flash,html4',    //上传模式,依次退化
            browse_button: button,       //上传选择的点选按钮，**必需**
            uptoken: token,
            domain: 'http://wangyuxin.qiniudn.com/',
            container: container,//上传区域DOM ID，默认是browser_button的父元素，
            max_file_size: '100mb',           //最大文件体积限制
            flash_swf_url: '/Moxie.swf',  //引入flash,相对路径
            max_retries: 3,                   //上传失败最大重试次数
            dragdrop: true,                   //开启可拖曳上传
            drop_element: container,        //拖曳上传区域元素的ID，拖曳文件或文件夹后可触发上传
            chunk_size: '4mb',                //分块上传时，每片的体积
            init: {
                'FilesAdded': function (up, files) {
                    up.start();
                },
                'BeforeUpload': function (up, file) {

                },
                'UploadProgress': function (up, file) {
                    // 每个文件上传时,处理相关的事情
                    var percent = Math.round(file.loaded / file.size * 100) + "%";
                    var li = $("#" + file.id);
                    li.find(".progress-bar").attr('style', "width:" + percent + "").html(percent);
                },
                'FileUploaded': function (up, file, info) {
                    var domain = up.getOption('domain');
                    var res = $.parseJSON(info);
                    console.info("result information---------->:", res);
                    console.info("result information---------->:", file);
                    //  具体按配置改domain -- 数据库仅存储key
                    var pic_source_url = res.key;
                    console.info("pic_source_url-------------->" + pic_source_url);


                    //  预览图或者详情图
                    if ("fileToUpload1" == button) {
                        $("#photo_type").val(0);
                    } else if ("fileToUpload2" == button) {
                        $("#photo_type").val(1);
                    }
                    //  todo 把photo的信息设置到 当前编辑 的 bean对象中
                    $("#url-now").val(pic_source_url);

                    Photo.beginBack(pic_source_url, file.name);
                },
                'Error': function (up, err, errTip) {

                },
                'UploadComplete': function () {

                },
                'Key': function (up, file) {
                }
            },
            auto_start: false,                 //选择文件后自动上传，若关闭需要自己绑定事件触发上传
            filters: {
                mime_types: [ //只允许上传图片文件
                    {title: "图片文件", extensions: "jpg,gif,png"}
                ]
            }
        });
    },

    /**
     * 上传到七牛之后的后续处理函数
     * @param imgUrl
     * @param imgName
     */
    beginBack: function (imgUrl, imgName) {
        //创建dom节点，追加到页面，展示图片 [取消，简化！！！]
        var liDetail = "<li><img title='" + imgName + "' src='http://wangyuxin.qiniudn.com/" + imgUrl + "?imageView2/1/w/240/h/160'><p>"+'http://wangyuxin.qiniudn.com/' + imgUrl + "?imageView2/1/w/240/h/160</p><p>" +'http://wangyuxin.qiniudn.com/'+ imgUrl + "?imageView2/1/w/320/h/240</p></li>";

        var liPreview = "<li><img title='" + imgName + "' src='http://wangyuxin.qiniudn.com/" + imgUrl + "?imageView2/1/w/240/h/160'></li><li><p>"+'http://wangyuxin.qiniudn.com/' + imgUrl + "?imageView2/1/w/240/h/160</p><p>"+'http://wangyuxin.qiniudn.com/' + imgUrl + "?imageView2/1/w/320/h/240</p></li>";

        if ($("#photo_type").val() == 0) {
            console.info("photo - type 0 true: ", $("#photo_type").val() == 0);
            upload(0, imgUrl, imgName);
            $("#photo_list1 li").remove();
            $("#photo_list1").append(liPreview);
            //PF('detailDialog').initPosition();
        } else if ($("#photo_type").val() == 1) {
            console.info("photo - type 1 true: ", $("#photo_type").val() == 1);
            upload(1, imgUrl, imgName);
            $("#photo_list2").append(liDetail);
            //PF('detailDialog').initPosition();
        }
    },

    /**
     * 保存 photo
     * @param obj
     */
    save: function (obj, li_id) {

        $.ajax({
            url: '/photo/save.json',
            data: obj,
            type: 'POST',
            dataType: 'json',
            success: function (json) {
                console.info(json);
                if (json && json.photo) {
                    $("#" + li_id).find("p.imgWrap img").attr("upload-result", "success").attr("id", json.photo.id);
                    //  @deprecated 移除 mouseenter/mouseout事件
                    // $("#" + li_id).unbind("mouseenter").unbind("mouseout");
                    $("#" + li_id).find("p:eq(0)").attr('title', "");
                    $("#" + li_id).parent().prev().hide();
                    //  上传成功提示需要优化 todo
                    //layer.msg("上传成功,您可以点击选择图片继续上传哦!", {icon: 1, time: 2000});
                } else {
                    $("#" + li_id).find("p.imgWrap img").attr("upload-result", "failed")
                }
            },
            error: function (xhr, status, errorThrown) {

            },
            complete: function (xhr, status) {

            }
        });
    },

    delete: function (id) {
        $.ajax({
            url: '/photo/delete.json',
            type: 'GET',
            dataType: "json",
            data: {id: id},
            beforeSend: function (xhr) {

            },
            success: function (json) {
                if (json) {
                    console.log("delete - result : ", json);
                }
            },
            error: function (xhr, status, errorThrown) {

            },
            complete: function (xhr, status) {

            }
        });
    },

    /**
     * 排序
     * @param array
     */
    sort: function (array) {
        $.ajax({
            url: '/photo/sort.json',
            data: {ids: array},
            type: 'POST',
            dataType: 'json',
            traditional: true,
            beforeSend: function (xhr) {

            },
            success: function (json) {
                //  不做处理
            },
            error: function (xhr, stuatus, errorThrown) {

            },
            complete: function (xhr, stautus) {

            }
        })
    }

};

var PhotoUtils = {
    /**
     * 自定义key
     * */
    getCustomKey: function () {
        var data = ["0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"];
        var result = "";
        for (var i = 0; i < 20; i++) {
            var r = Math.floor(Math.random() * 62);     //取得0-62间的随机数，目的是以此当下标取数组data里的值！  
            result += data[r];        //输出20次随机数的同时，让rrr加20次，就是20位的随机字符串了。  
        }

        var date = new Date();
        var _key = result.substr(0, 10) + date.getTime() + result.substr(10, 10);
        return _key;
    },
    //plupload中为我们提供了mOxie对象
    //有关mOxie的介绍和说明请看：https://github.com/moxiecode/moxie/wiki/API
    //如果你不想了解那么多的话，那就照抄本示例的代码来得到预览的图片吧
    previewImage: function (file, callback) {//file为plupload事件监听函数参数中的file对象,callback为预览图片准备完成的回调函数 
        if (!file || !/image\//.test(file.type)) return; //确保文件是图片
        if (file.type == 'image/gif') {//gif使用FileReader进行预览,因为mOxie.Image只支持jpg和png
            var fr = new mOxie.FileReader();
            fr.onload = function () {
                callback(fr.result);
                fr.destroy();
                fr = null;
            }
            fr.readAsDataURL(file.getSource());
        } else {
            var preloader = new mOxie.Image();
            preloader.onload = function () {
                preloader.downsize(316, 236);//先压缩一下要预览的图片,宽300，高300
                var imgsrc = preloader.type == 'image/jpeg' ? preloader.getAsDataURL('image/jpeg', 80) : preloader.getAsDataURL(); //得到图片src,实质为一个base64编码的数据
                callback && callback(imgsrc); //callback传入的参数为预览图片的url
                preloader.destroy();
                preloader = null;
            };
            preloader.load(file.getSource());
        }
    },

    //  选中文件后
    //  文件大小
    addFile: function (ul_id, file) {
        PhotoUtils.previewImage(file, function (imgsrc) {
            // " + file.name + "不再展示原文件名
            var li = "<li id='" + file.id + "'>"
                + "<button class=\"btn btn-minier btn-primary cancel-edit\" onclick=\"PhotoUtils.cancelEdit(this)\" title=\"取消\" ><i class=\"ace-icon fa fa-close\"></i></button><button class=\"btn btn-minier btn-primary save-edit\" onclick=\"PhotoUtils.saveEdit(this)\" title=\"保存\" ><i class=\"ace-icon fa fa-check\"></i></button><textarea class='title-edit-textarea' title=''></textarea>"
                + "<p title='' class=\"title\"></p>"
                + "<p class=\"imgWrap\"><img format='" + file.type + "'   size='" + file.size + "' style=\"vertical-align: middle;\" src='" + imgsrc + "'></p>"
                + "<div class=\"progress progress-striped active\">"
                + "<div class=\"progress-bar progress-bar-yellow\" style=\"width: 0%\"></div>等待上传"
                + "</div>"

                + "<div class=\"file-panel\" style='display:none;'>"
                + "<button class=\"btn btn-inverse btn-minier edit-select-upload\" onclick=\"PhotoUtils.editPhoto(this)\" title='编辑相片标题' >"
                + "<i class=\"ace-icon fa fa-pencil-square-o\"></i>"
                + "</button>"

                + "<button style='left: 6px;' class=\"btn btn-inverse btn-minier cancel-select-upload\" onclick=\"PhotoUtils.deletePhoto(this)\" title='删除' >"
                + "<i class=\"ace-icon fa fa-trash-o\"></i>"
                + "</button>"

                + "</div>"
                + "</li>";

            // '<img width=\'140px\' height=\'120px\'  src="' + imgsrc + '" />'
            $('#' + ul_id).prepend(li);

            //  编辑/删除按钮的显隐
            $("#" + file.id).on('mouseenter', function (e) {
                $(this).find('.file-panel').show();
            }).on('mouseleave', function (e) {
                $(this).find('.file-panel').hide();
            });

        })


    },

    /**
     * 编辑图片
     * @param id
     */
    editPhoto: function (obj) {

        $(obj).parent().siblings(".cancel-edit,.save-edit,.title-edit-textarea").show().siblings(".title").hide();
        $(obj).parent().siblings(".title-edit-textarea").val($(obj).parent().siblings(".title").html());
        //  隐藏自己
        $(obj).hide();

    },
    /**
     * 删除图片
     * @param id
     */
    deletePhoto: function (obj) {

        //  存在id，后台删除
        if ($(obj).parent().parent().find('.imgWrap img').attr("id")) {
            console.info("pci-real-delete");
            Photo.delete($(obj).parent().parent().find('.imgWrap img').attr("id"));
        }
        $(obj).parent().parent().remove();
        if ($(obj).parent().parent().parent().find("li").length <= 0) {
            $(obj).parent().parent().parent().parent().hide();
        }
    },

    /**
     * 保存修改
     * @param obj
     */
    saveEdit: function (obj) {
        //
        $(obj).siblings(".title").html($(obj).siblings(".title-edit-textarea").val());
        $(obj).hide().siblings(".cancel-edit,.title-edit-textarea").hide().siblings(".title").show().siblings(".file-panel").find('.edit-select-upload').show();
        Photo.save({
            id: $(obj).siblings(".imgWrap").find("img").attr('id'),
            picName: $(obj).siblings(".title-edit-textarea").val()
        });

    },

    /**
     * 取消修改
     * @param obj
     */
    cancelEdit: function (obj) {
        $(obj).hide().siblings(".save-edit,.title-edit-textarea").hide().siblings(".title").show().siblings(".file-panel").find('.edit-select-upload').show();
    },
    /**
     * 图片居中展示
     * @param imgWdith
     * @param imgHeight
     * @param parentWidth
     * @param parentHeight
     */
    imageFit: function (imgWdith, imgHeight, parentWidth, parentHeight, img) {
        var width_rotation = parentWidth / imgWdith,
            height_rotation = parentHeight / imgHeight;

        var tmpWidth,
            tmpHeight,
            ration;
        if (width_rotation < 1 || height_rotation < 1) {
            ration = (width_rotation <= height_rotation ? width_rotation : height_rotation);
        }
        if (ration < 1) {
            tmpWidth = imgWdith * ration;
            tmpHeight = imgHeight * ration;
        } else {

        }
        img.style.height = Math.round(tmpHeight) + "px";
        img.style.widith = Math.round(tmpWidth) + "px";

        if (tmpHeight < parentHeight) {
            img.style.marginTop = Math.round((parentHeight - tmpHeight) / 2) + "px";
        }
        if (tmpWidth < parentWidth) {
            // 原因未知！！！！！
            //img.style.marginLeft = Math.round((parentWidth - tmpWidth) / 2) + "px";
            //console.log("marginLeft:", img.style.marginLeft,parentWidth,Math.round((parentWidth - tmpWidth) / 2));
        }

    }


};
