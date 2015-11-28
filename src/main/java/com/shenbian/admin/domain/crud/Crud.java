package com.shenbian.admin.domain.crud;

//import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 123 on 2015/9/16.
 */
@Entity
@Table(name = "crud")
public class Crud {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    //  string
    private String name;
    //  int
    private Integer age;
    // boolean radion
    private Boolean isActive;
    //  select
    private OptionType type;
    //


    @Column(length = 5000)
    private String jsonData = "{\"jsonBeanList\":[{ \"firstName\": \"Sergei\", \"lastName\": \"Rachmaninoff\", \"instrument\": \"piano\" },{ \"firstName\": \"Fssssss\", \"lastName\": \"Rfffffffff\", \"instrument\": \"ooooooo\" }],\"jsonPhotoList\":[{\"url\":\"7xlyy8.com1.z0.glb.clouddn.com/FiqoAEQK6pK4jPMCGElkFB3YmZ9k\",\"name\":\"pic1\",\"delete\":false,\"description\":\"nothing to say!sry....\"},{\"url\":\"7xlyy8.com1.z0.glb.clouddn.com/FiqoAEQK6pK4jPMCGElkFB3YmZ9k\",\"name\":\"pic1\",\"delete\":false,\"description\":\"nothing to say!sry....\"},{\"url\":\"7xlyy8.com1.z0.glb.clouddn.com/FiqoAEQK6pK4jPMCGElkFB3YmZ9k\",\"name\":\"pic1\",\"delete\":false,\"description\":\"nothing to say!sry....\"},{\"url\":\"7xlyy8.com1.z0.glb.clouddn.com/FiqoAEQK6pK4jPMCGElkFB3YmZ9k\",\"name\":\"pic1\",\"delete\":false,\"description\":\"nothing to say!sry....\"},{\"url\":\"7xlyy8.com1.z0.glb.clouddn.com/FiqoAEQK6pK4jPMCGElkFB3YmZ9k\",\"name\":\"pic1\",\"delete\":false,\"description\":\"nothing to say!sry....\"}]}";

    @Column(length = 5000)
    private String htmlStr = "<div class=\"attributes-list\" id=\"J_AttrList\">\n" +
            "                \n" +
            "                    <div class=\"tm-clear tb-hidden tm_brandAttr\" id=\"J_BrandAttr\" style=\"display: block;\"><div class=\"name\">品牌名称：<a class=\"J_EbrandLogo\" target=\"_blank\" href=\"//brand.tmall.com/brandInfo.htm?brandId=157907274&amp;type=0&amp;scm=1048.1.1.4\">Meitu/美图</a></div><a class=\"tm-collectBtn j_DetailBrand\" data-brandid=\"157907274\" href=\"#\" hidefocus=\"true\"><i></i><span>关注</span></a></div>\n" +
            "        <p class=\"attr-list-hd tm-clear\"><a class=\"ui-more-nbg tm-MRswitchAttrs\" href=\"#J_Attrs\">更多参数<i class=\"ui-more-nbg-arrow tm-MRswitchAttrs\"></i></a><em>产品参数：</em></p>\n" +
            "<ul id=\"J_AttrUL\">\n" +
            "                \n" +
            "<li title=\"Meitu/美图 M4\">产品名称：Meitu/美图 M4</li>                                                                                                  <li title=\"&nbsp;MT6752\">CPU型号:&nbsp;MT6752</li>\n" +
            "                                                                                                                                                                      <li title=\"&nbsp;M4\">美图型号:&nbsp;M4</li>\n" +
            "                                                                                                                                                                                                                                                                                                                                     <li title=\"&nbsp;哆啦A梦特别版&nbsp;HelloKitty特别版&nbsp;月光白&nbsp;薄荷绿&nbsp;炫目红\">机身颜色:&nbsp;哆啦A梦特别版&nbsp;HelloKitty特别版&nbsp;月光白&nbsp;薄荷绿&nbsp;炫目红</li>\n" +
            "                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             <li title=\"&nbsp;2GB\">运行内存RAM:&nbsp;2GB</li>\n" +
            "                                                                                                     <li title=\"&nbsp;32GB\">机身内存:&nbsp;32GB</li>\n" +
            "                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             <li title=\"&nbsp;单卡多模\">网络模式:&nbsp;单卡多模</li>\n" +
            "                                                                                                                                                                                                                     <li title=\"&nbsp;2160mAh\">电池容量:&nbsp;2160mAh</li>\n" +
            "                                                                                                                                                \n" +
            "                <!-- 健字号相关-->\n" +
            "                    </ul>\n" +
            "    </div>";

    private Date crtDate = new Date();

    @Version
    private int ver;


    @Transient
    private JsonBean jsonBean;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public OptionType getType() {
        return type;
    }

    public void setType(OptionType type) {
        this.type = type;
    }

    public Date getCrtDate() {
        return crtDate;
    }

    public void setCrtDate(Date crtDate) {
        this.crtDate = crtDate;
    }

    public int getVer() {
        return ver;
    }

    public void setVer(int ver) {
        this.ver = ver;
    }


    public String getJsonData() {
        return jsonData;
    }

    public void setJsonData(String jsonData) {
        this.jsonData = jsonData;
    }

    public String getHtmlStr() {
        return htmlStr;
    }

    public void setHtmlStr(String htmlStr) {
        this.htmlStr = htmlStr;
    }


    public JsonBean getJsonBean() {
        return jsonBean;
    }

    public void setJsonBean(JsonBean jsonBean) {

        if (null != jsonBean) {
            this.jsonBean = jsonBean;
        } else {
            if (!StringUtils.isEmpty(getJsonData())) {
                String json = getJsonData();
                Gson gson = new Gson();
                java.lang.reflect.Type type = new TypeToken<JsonBean>() {
                }.getType();
                this.jsonBean = gson.fromJson(json, type);
            } else {
                this.jsonBean = new JsonBean();
            }
        }


    }


}
