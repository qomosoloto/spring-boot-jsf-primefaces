package com.shenbian.admin.controller.product;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shenbian.admin.controller.BaseBackBean;
import com.shenbian.admin.controller.InitInterface;
import com.shenbian.admin.domain.vo.photo.PhotoVo;
import com.shenbian.admin.service.SequenceService;
import com.shenbian.admin.service.synchronize.PrimaryCodeSn;
import com.shenbian.admin.service.synchronize.product.ProductService;
import com.shenbian.admin.service.synchronize.product.ProductServiceExt;
import com.shenbian.admin.service.synchronize.product.SkuService;
import com.shenbian.admin.service.user.UserService;
import com.shenbian.admin.util.base.Constant;
import com.shenbian.admin.util.qiniu.QRCodeService;
import com.shenbian.admin.util.qiniu.UpTokenService;
import com.shenbian.ng.model.Product;
import com.shenbian.ng.model.Sku;
import com.shenbian.ng.model.enums.EntityStatus;
import com.shenbian.ng.model.enums.ProductType;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.context.RequestContext;
import org.primefaces.event.data.PageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by qomo on 15-10-13.
 */
@Component(value = "productBean")
@ManagedBean(name = "productBean")
@ViewScoped
public class ProductBean implements BaseBackBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductBean.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private SkuService skuService;

    @Autowired
    private SequenceService sequenceService;

    @Autowired
    private UserService userService;

    @Autowired
    private QRCodeService qrCodeService;

    @Getter
    @Setter
    private List<Product> productList;

    @Getter
    @Setter
    private List<ProductVo> list = Lists.newArrayList();

    @Getter
    @Setter
    private Product selectedProduct = new Product();

    @Getter
    @Setter
    private ProductVo selectedProductVo = new ProductVo(selectedProduct);

    @Getter
    @Setter
    private Integer productType;


    @Getter
    @Setter
    private ProductLazyDataModel lazyDataModel;

    @Getter
    @Setter
    private String token = UpTokenService.returnToken();
    /**
     * 预览图还是详情图
     */
    @Getter
    @Setter
    private String photoType = "-1";
    /**
     * 用来标识该页面所属模块，相片上传中会根据该标识来调用不同的backBean
     */
    @Getter
    @Setter
    private String viewType = "product";

    private Gson gson = new Gson();


    public void pageFunc(PageEvent event) {
        int var = event.getPage();
        System.out.println();
    }

    @Override
    public void init() {

        Map<String, Object> map = Maps.newHashMap();
        this.lazyDataModel = new ProductLazyDataModel(this.productService, map);
        RequestContext.getCurrentInstance().update(":form1:infoTable");
        //
//        this.productList = productService.customSearch(map);
//        this.selectedProduct = new Product();
//        for (Product product : this.productList) {
//            ProductVo vo = new ProductVo(product);
//            this.list.add(vo);
//        }
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/views/product/list.xhtml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 点击新增实物商品和虚拟商品的时候，
     * 重置选中的product,productVo
     *
     * @param type
     */
    @Override
    public void addNewEntity(Integer type) {

        this.productType = type;
        this.selectedProduct = new Product();
        this.selectedProductVo = new ProductVo(this.selectedProduct);
    }

    /**
     * 选中表格每一行数据后，设置选中的product,productVo
     *
     * @param o
     */
    @Override
    public void selectEntity(Object o) {
        this.selectedProductVo = (ProductVo) o;
        //  重新查询下，保证数据是数据库中的最新数据
        this.selectedProduct = productService.findOne(this.selectedProductVo.getProduct().getId());
        this.selectedProductVo = new ProductVo(this.selectedProduct);
        if (this.selectedProduct.getProductType().equals(ProductType.PHYSICAL)) {
            this.productType = 0;
        } else {
            this.productType = 1;
        }
    }

    /**
     * 保存产品信息
     */
    public void saveProduct(ActionEvent e) {

        Map<String, String> requestParameterMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

        this.selectedProduct = this.selectedProductVo.getProduct();
        JsonInfo JsonInfo = this.selectedProductVo.getJsonInfo();
        String toJson = gson.toJson(JsonInfo);

        this.selectedProduct.setJsonInfo(toJson);
        this.selectedProduct.setLongName(this.selectedProduct.getName());


        if (Strings.isNullOrEmpty(this.selectedProduct.getCodeSn())) {
            this.selectedProduct.setCodeSn(PrimaryCodeSn.PRODUCT_MERCHANDISE.nextValue(sequenceService).toString());
            this.selectedProduct.setCreater(userService.findByCodeSn(Constant.CORP_DEFAULT_USER_CODE_SN));
        }


        //  增删改之后，都要重置下面两个对象的引用，否则修改后的信息无法同步到页面上显示
        this.selectedProduct = productService.save(this.selectedProduct);

        // test 二维码  wangyuxin.qiniudn.com/2a1ddb4f69da4243a8352c942613a047
        qrCodeService.createTwoDimensionCode(this.selectedProduct.getCodeSn());

        this.selectedProductVo = new ProductVo(this.selectedProduct);
        this.lazyDataModel = new ProductLazyDataModel(this.productService, Maps.newHashMap());
        if (!Strings.isNullOrEmpty(this.selectedProduct.getPicture())) {
            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('detailDialog').hide();");
        }

    }


    public void upPhotoType(String photoType) {
        this.photoType = photoType;
    }

    /**
     * 单独处理相片上传:产品预览
     */

    public void savePreviewPhoto() {

        LOGGER.info("enter.....................savePreviewPhoto");
        Map<String, String> requestParameterMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

        Integer photoType = Integer.valueOf(requestParameterMap.get("type"));
        String url = requestParameterMap.get("url");
        String name = requestParameterMap.get("name");

        if (0 == photoType) {
            PhotoVo photoVo = new PhotoVo();
            photoVo.setImageType(0);
            photoVo.setUrl(url);
            photoVo.setMerchandiseId(null);
            //  如果是新增产品，此时是不存在 codeSn的
            //photoVo.setProductCodeSn(this.selectedProductVo.getProduct().getCodeSn());
            if (Strings.isNullOrEmpty(this.selectedProduct.getCodeSn())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("上传失败", "请先保存产品，再上传图片"));
            } else {
                this.selectedProduct.setPicture(url);

                //  增删改之后，都要重置下面两个对象的引用，否则修改后的信息无法同步到页面上显示
                this.selectedProduct = productService.save(this.selectedProduct);
                this.selectedProductVo = new ProductVo(this.selectedProduct);
                this.lazyDataModel = new ProductLazyDataModel(this.productService, Maps.newHashMap());
                RequestContext.getCurrentInstance().update(":form1:infoTable");
            }
        } else if (1 == photoType) {
            this.saveDetailPhoto();
        }


    }

    /**
     * 单独处理相片上传:产品详情图
     */

    public void saveDetailPhoto() {
        LOGGER.info("enter.....................saveDetailPhoto");
        Map<String, String> requestParameterMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String url = requestParameterMap.get("url");
        String name = requestParameterMap.get("name");

        if (Strings.isNullOrEmpty(this.selectedProduct.getCodeSn())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("上传失败", "请先保存产品，再上传图片"));
        } else {
            List<PhotoVo> photoVoList = Lists.newArrayList();
            PhotoVo photoVo = new PhotoVo();
            photoVo.setImageType(1);//  详情图片
            photoVo.setUrl(url);
            photoVo.setMerchandiseId(null);
            photoVo.setProductCodeSn(this.selectedProduct.getCodeSn());

            JsonInfo JsonInfo = this.selectedProductVo.getJsonInfo();
            if (Strings.isNullOrEmpty(JsonInfo.getPhotos())) {
                photoVoList.add(photoVo);
                JsonInfo.setPhotos(gson.toJson(photoVoList));

            } else {
                photoVoList = gson.fromJson(JsonInfo.getPhotos(), new TypeToken<List<PhotoVo>>() {
                }.getType());
                photoVoList.add(photoVo);
                JsonInfo.setPhotos(gson.toJson(photoVoList));
            }
            this.selectedProduct.setJsonInfo(gson.toJson(JsonInfo));

            //  增删改之后，都要重置下面两个对象的引用，否则修改后的信息无法同步到页面上显示
            this.selectedProduct = productService.save(this.selectedProduct);
            this.selectedProductVo = new ProductVo(this.selectedProduct);
            this.lazyDataModel = new ProductLazyDataModel(this.productService, Maps.newHashMap());
            RequestContext.getCurrentInstance().update(":form1:infoTable");

        }

    }


    /**
     * 实际是更新　entityStatus
     * 也要更新相关的商品
     */
    @Override
    public void deleteEntity() {
        List<Sku> byProductCodeSn = skuService.findByProductCodeSn(this.selectedProduct.getCodeSn());
        if (byProductCodeSn != null && byProductCodeSn.size() > 0) {
            for (Sku sku : byProductCodeSn) {
                sku.setEntityStatus(EntityStatus.DELETED);
            }
            skuService.save(byProductCodeSn);
        }
        this.selectedProduct.setEntityStatus(EntityStatus.DELETED);
        productService.save(this.selectedProduct);
        this.lazyDataModel = new ProductLazyDataModel(this.productService, Maps.newHashMap());
    }

    public void paramSearch() {
        Map<String, String> requestParameterMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();

        Map<String, Object> paramMap = Maps.newHashMap();

        if (!Strings.isNullOrEmpty(requestParameterMap.get("search_form:productName"))) {
            paramMap.put("productName", requestParameterMap.get("search_form:productName"));
        }

        if (!Strings.isNullOrEmpty(requestParameterMap.get("search_form:productType_input")) && !"-1".equals((String) requestParameterMap.get("search_form:productType_input"))) {
            paramMap.put("productType", requestParameterMap.get("search_form:productType_input"));
        }

        if (!Strings.isNullOrEmpty(requestParameterMap.get("search_form:codeSn"))) {
            paramMap.put("codeSn", requestParameterMap.get("search_form:codeSn"));
        }

        if (!Strings.isNullOrEmpty(requestParameterMap.get("search_form:category_input")) && !"-1".equals((String) requestParameterMap.get("search_form:category_input"))) {
            paramMap.put("category", requestParameterMap.get("search_form:category_input"));
        }
        this.lazyDataModel = new ProductLazyDataModel(this.productService, paramMap);
        RequestContext.getCurrentInstance().update(":form1:infoTable");
    }

    public void paramReset() {

    }
}
