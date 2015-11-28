package com.shenbian.admin.controller.crud;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.shenbian.admin.domain.crud.*;
import com.shenbian.admin.service.crud.CrudService;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 123 on 2015/9/16.
 * git add?
 */
@Component(value = "infoBean")
@ViewScoped
public class InfoBean implements Serializable {
    @Autowired
    private CrudService crudDao;

    @Setter
    @Getter
    private List<Crud> list;

    //    private LazyDataModel<Crud> lazyModel = new LazyInfoDataModel();
    @Setter
    @Getter
    private int jsonBeanInnerIdx;
    @Setter
    @Getter
    private int jsonBeanPhotoIdx;
    @Setter
    @Getter
    private Inner innerSelected;
    @Setter
    @Getter
    private Photo photoSelected;
    @Setter
    @Getter
    private Inner newInner = new Inner();
    @Setter
    @Getter
    private Photo newphoto = new Photo();

    @Setter
    @Getter
    private Crud selected = new Crud();
    @Setter
    @Getter
    private Integer selectedId = null;
    @Setter
    @Getter
    private List<Crud> selectedList = Lists.newArrayList();

    @Setter
    @Getter
    private List<Map<String, Object>> listJson;

    public void placeHolderFunction(Object o) {
        System.out.println("---------------------------------------placeHolderFunction" +
                "--------------------------------");
    }


    public void pageBlockui() {

    }

//    @PostConstruct
    public void init() throws Exception {
        //  FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        list = crudDao.findAll();
//        synchronizeService.synchronizeActMerch();
        for (Crud crud : list) {
            crud.setJsonBean(null);
        }

    }

    /**
     * 因为jsonBean  List 中的类型实体不依靠数据库主键存储，所以该条json数据向编辑状态切换的时候，需要存储其在 jsonBean 中List中下标，以便保存的时候老数据替换旧数据
     * <p>
     * 也可以再 editInnit事件中缓存旧的 对象， 在edit的时候， 把新对象值拷贝进去
     *
     * @param editEvent
     */
    public void jsonDataInnerEditInit(RowEditEvent editEvent) {
        Object object = editEvent.getObject();
        String simpleName = editEvent.getObject().getClass().getSimpleName();
        if (simpleName.equals(Inner.class.getSimpleName())) {
            jsonBeanInnerIdx = selected.getJsonBean().getJsonBeanList().indexOf(object);
        } else if (simpleName.equals(Photo.class.getSimpleName())) {
            jsonBeanPhotoIdx = selected.getJsonBean().getJsonPhotoList().indexOf(object);
        }

    }


    /**
     * json中的数组数据更新
     * 更新后需要重置 selected，因为缓存的Crud实体的版本号仍是更新之前的版本号，导致下次更新失败，
     * ow was updated or deleted by another transaction (or unsaved-value mapping 。。。。
     *
     * @param editEvent
     * @throws Exception
     */
    public void jsonDataEdit(RowEditEvent editEvent) throws Exception {

        //  json数据内的类名
        Class<?> aClass = editEvent.getObject().getClass();
        if (aClass.getSimpleName().equals(Inner.class.getSimpleName())) {
            List<Inner> jsonBeanList = selected.getJsonBean().getJsonBeanList();
            Inner object = (Inner) editEvent.getObject();
            jsonBeanList.add(jsonBeanInnerIdx, object);
            jsonBeanList.remove(jsonBeanInnerIdx + 1);

            selected.getJsonBean().setJsonBeanList(jsonBeanList);
            Gson gson = new Gson();
            String toJson = gson.toJson(selected.getJsonBean());
            selected.setJsonData(toJson);
            crudDao.save(selected);
        } else if (aClass.getSimpleName().equals(Photo.class.getSimpleName())) {
            List<Photo> jsonPhotoList = selected.getJsonBean().getJsonPhotoList();
            Photo object = (Photo) editEvent.getObject();
            jsonPhotoList.add(jsonBeanPhotoIdx, object);
            jsonPhotoList.remove(jsonBeanPhotoIdx + 1);
            selected.getJsonBean().setJsonPhotoList(jsonPhotoList);
            Gson gson = new Gson();
            String toJson = gson.toJson(selected.getJsonBean());
            selected.setJsonData(toJson);
            crudDao.save(selected);
        }

        System.out.println("complet");
        selected = crudDao.findOne(selected.getId());
        selected.setJsonBean(null);
    }

    /**
     * 取消数据编辑状态《json字段中的数组数据》
     *
     * @param editEvent
     */
    public void jsonDataCancelEdit(RowEditEvent editEvent) {

        //  todo
    }

    /**
     * FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("form-:search_name")
     * 获取搜索表单的参数
     * 调用自定义查询方法
     */

    public void paramSearch() throws Exception {

        Map<String, String> requestParameterMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Map<String, Object> paramMap = new HashMap<String, Object>();//Maps.newHashMap();
//        if (!StringUtils.isEmpty(requestParameterMap.get("form-:search_name"))) {
//            paramMap.put("name", "%" + requestParameterMap.get("form-:search_name") + "%");
//        }
//        if (!StringUtils.isEmpty(requestParameterMap.get("form-:search_age"))) {
//            paramMap.put("age", requestParameterMap.get("form-:search_age"));
//        }
//        if (!StringUtils.isEmpty(requestParameterMap.get("form-:search_type_input"))) {
//            paramMap.put("type", OptionType.valueOf(requestParameterMap.get("form-:search_type_input")).getValue());
//        }

        this.list = crudDao.paramSearch("", 12);
        for (Crud crud : list) {
            crud.setJsonBean(null);
        }
    }


    /**
     * 该业务模块的--{行内编辑}--处理
     * 这个时候的保存和 json数据修改无关，json数据的修改在弹出dialog中完成
     *
     * @param event
     * @throws Exception
     */
    public void onRowEdit(RowEditEvent event) throws Exception {

        Crud crud = (Crud) event.getObject();
        //  乐观锁，弹窗更新其他信息会影响ｖｅｒ值，那个时候不适合全部刷新ｌｉｓｔ<entityManager>
        //  crud.setVer(crudDao.findOne(selectedId).getVer());
        //  spirng data jpa 需要先查询再更新
        Crud one = crudDao.findOne(crud.getId());
        BeanUtils.copyProperties(crud, one);
        crudDao.save(one);
        //  如果内有json字段,需将 对象转为 json串保存到数据库

        list = crudDao.findAll();
        selected = new Crud();

        FacesMessage msg = new FacesMessage("Crud Edited", ("id为" + crud.getId().toString() + "更新成功"));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     * 该业务模块的--{行内编辑}--处理取消
     *
     * @param event
     */
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", ((Crud) event.getObject()).getId().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     * dataTable 单击选择事件
     *
     * @param object
     */
    public void select(Object object) throws Exception {

        RequestContext requestContext = RequestContext.getCurrentInstance();
        requestContext.execute("console.info('hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa');");

        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String contextPath = request.getContextPath();
        System.out.println("contextPath----------------------------------------------->" + contextPath);
        if (object instanceof Crud) {
            this.selected = (Crud) object;
            this.selectedId = selected.getId();
            //  为保证数据准确
            this.selected = crudDao.findOne(selectedId);
            this.selected.setJsonBean(null);
            System.out.print("..");
            this.newInner = new Inner();
        } else if (object instanceof Inner) {
            this.innerSelected = (Inner) object;
        } else if (object instanceof Photo) {
            this.photoSelected = (Photo) object;
        }

    }

    /**
     * 新增实体的保存事件
     * 该新增暂未添加json数据，可在添加成功后，更多编辑处添加
     *
     * @param e
     * @throws Exception
     */
    public void saveBean(ActionEvent e) throws Exception {

        selected.setId(null);
        crudDao.save(selected);
        list = crudDao.findAll();
        for (Crud crud : list) {
            crud.setJsonBean(null);
        }
        selected = new Crud();
    }


    /**
     * 更新实体信息
     *
     * @param e
     * @throws Exception
     */
    public void update(ActionEvent e) throws Exception {

        Crud one = crudDao.findOne(selectedId);
        BeanUtils.copyProperties(selected, one);

        crudDao.save(one);
        list = crudDao.findAll();
        for (Crud crud : list) {
            crud.setJsonBean(null);
        }
        selected = new Crud();
    }

    /**
     * 删除实体
     *
     * @param e
     * @throws Exception
     */
    public void delete(ActionEvent e) throws Exception {

        crudDao.delete(selected.getId());
        list = crudDao.findAll();
        for (Crud crud : list) {
            crud.setJsonBean(null);
        }

        selected = new Crud();
    }

    /**
     * 新增json数据 Inner
     */
    public void saveInner() throws Exception {


        JsonBean jsonBean = selected.getJsonBean() == null ? new JsonBean() : selected.getJsonBean();
        List<Inner> jsonBeanList = jsonBean.getJsonBeanList() == null ? new ArrayList<Inner>() : jsonBean.getJsonBeanList();

        jsonBeanList.add(newInner);
        selected.setJsonBean(jsonBean);
        selected.getJsonBean().setJsonBeanList(jsonBeanList);

        Gson gson = new Gson();
        selected.setJsonData(gson.toJson(selected.getJsonBean()));

        Crud one = crudDao.findOne(selectedId);
        BeanUtils.copyProperties(selected, one);

        //  刷新ver
        selected = crudDao.save(one);
        selected.setJsonBean(null);
        newInner = new Inner();
    }

    public void savePhoto() throws Exception {
        Map<String, String> requestParameterMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String url = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get
                ("url");
        String name = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get
                ("name");
        JsonBean jsonBean = selected.getJsonBean() == null ? new JsonBean() : selected.getJsonBean();
        List<Photo> jsonPhotoList = jsonBean.getJsonPhotoList() == null ? new ArrayList<Photo>() : jsonBean
                .getJsonPhotoList();
        newphoto.setUrl(url);
        newphoto.setName(name);
        newphoto.setDelete(false);
        newphoto.setDescription(name);

        jsonPhotoList.add(newphoto);
        selected.setJsonBean(jsonBean);
        selected.getJsonBean().setJsonPhotoList(jsonPhotoList);

        Gson gson = new Gson();
        selected.setJsonData(gson.toJson(selected.getJsonBean()));

        Crud one = crudDao.findOne(selected.getId());
        BeanUtils.copyProperties(selected, one);

        //  刷新ver
        selected = crudDao.save(one);
        selected.setJsonBean(null);
        newphoto = new Photo();

//                ("v1");
        System.out.println("------>" + url);

        //拦截到这个页面传过来的值后可以进行你自己的业务处理
        //并将处理结果返回给vals，这个vals是这个bean中的一个属性

    }

    /**
     * 删除inner
     *
     * @throws Exception
     */

    public void deleteInner() throws Exception {
        List<Inner> jsonBeanList = selected.getJsonBean().getJsonBeanList();
        jsonBeanList.remove(innerSelected);

        selected.setJsonData(new Gson().toJson(selected.getJsonBean()));
        Crud one = crudDao.findOne(selected.getId());
        BeanUtils.copyProperties(selected, one);

        //  刷新ver
        selected = crudDao.save(one);
        selected.setJsonBean(null);
        innerSelected = new Inner();
    }

    /**
     * 删除inner
     *
     * @throws Exception
     */

    public void deletePhoto() throws Exception {
        List<Photo> jsonBeanList = selected.getJsonBean().getJsonPhotoList();
        jsonBeanList.remove(photoSelected);
        selected.setJsonData(new Gson().toJson(selected.getJsonBean()));
        Crud one = crudDao.findOne(selected.getId());
        BeanUtils.copyProperties(selected, one);

        //  刷新ver
        selected = crudDao.save(one);
        selected.setJsonBean(null);
        photoSelected = new Photo();
    }

//    public LazyDataModel<Crud> getLazyModel() {
//        return lazyModel;
//    }
//
//    public void setLazyModel(LazyDataModel<Crud> lazyModel) {
//        this.lazyModel = lazyModel;
//    }


}
