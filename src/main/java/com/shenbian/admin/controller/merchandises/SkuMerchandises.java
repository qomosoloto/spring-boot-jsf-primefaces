package com.shenbian.admin.controller.merchandises;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shenbian.admin.controller.BaseBackBean;
import com.shenbian.admin.controller.activities.SkuVo;
import com.shenbian.admin.controller.product.JsonInfo;
import com.shenbian.admin.controller.product.ProductVo;
import com.shenbian.admin.domain.vo.photo.PhotoVo;
import com.shenbian.admin.service.SequenceService;
import com.shenbian.admin.service.synchronize.PrimaryCodeSn;
import com.shenbian.admin.service.synchronize.product.ProductService;
import com.shenbian.admin.service.synchronize.product.SkuService;
import com.shenbian.admin.service.user.UserService;
import com.shenbian.admin.util.qiniu.QRCodeService;
import com.shenbian.ng.model.Product;
import com.shenbian.ng.model.Sku;
import com.shenbian.ng.model.User;
import com.shenbian.ng.model.enums.*;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.context.RequestContext;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by qomo on 15-10-14.
 * 商品管理backing bean
 * 包括
 * 1.参数组合查询
 * 2.添加商品
 * ３.修改商品
 * ４.删除商品
 * ５.编辑商品信息
 * ６.上下架操作
 */
@Component("merchandiseBean")
@ManagedBean(name = "merchandiseBean")
@ViewScoped
public class SkuMerchandises implements BaseBackBean {

    private final static Logger LOGGER = LoggerFactory.getLogger("log merchandiseBackBean");

    @Autowired
    private SkuService skuService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private SequenceService sequenceService;

    @Autowired
    private QRCodeService qrCodeService;

    @Setter
    @Getter
    private List<Sku> skuList;

    @Getter
    @Setter
    private List<SkuVo> skuVoList;

    @Setter
    @Getter
    private Sku selectedSku = new Sku();

    @Getter
    @Setter
    private SkuVo selectedSkuVo = new SkuVo(selectedSku);

    @Getter
    @Setter
    private MerchandiseLazyDataModel lazyDataModel;

    @Setter
    @Getter
    private Map<String, Object> selectOneMenuProductsMap = Maps.newHashMap();

    @Setter
    @Getter
    private Map<String, Object> selectOneMenuMerchantsMap = Maps.newHashMap();

    /**
     * 被选中商品的详情图列表
     */
    @Setter
    @Getter
    private List<PhotoVo> selectedSkuPhotos = Lists.newArrayList();

    /**
     *
     */
    @Setter
    @Getter
    private List<MerchandiseExtProperty> merchandiseExtProperties = Lists.newArrayList();

    @Override
    public void init() {
        try {
            //  product -codeSn -name map
            List<Object[]> products = productService.productIdNameList();
            if (products != null && products.size() > 0) {
                for (Object[] product : products) {
                    selectOneMenuProductsMap.put(product[0].toString(), product[1]);
                }
            }

            List<User> merchants = userService.findMerchants();
            if (merchants != null && merchants.size() > 0) {
                for (User merchant : merchants) {
                    this.selectOneMenuMerchantsMap.put(merchant.getCodeSn(), merchant.getName());
                }
            }
            this.lazyDataModel = new MerchandiseLazyDataModel(this.skuService, Maps.newHashMap());
            this.selectedSku = new Sku();
            this.selectedSku.setProduct(new Product());
            this.selectedSkuVo = new SkuVo(this.selectedSku);


            FacesContext.getCurrentInstance().getExternalContext().redirect("/views/merchandise/list.xhtml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addNewEntity(Integer type) {
        this.selectedSku = new Sku();
        this.selectedSku.setOwner(new User());
        selectedSku.setProduct(new Product());
        this.selectedSkuVo = new SkuVo(this.selectedSku);
        this.selectedSkuVo.getJsonInfo().setMerchandiseExtProperties("[]");
        this.selectProductId = "-1";
        this.selectedSkuPhotos = Lists.newArrayList();

    }

    @Override
    public void selectEntity(Object o) {

        setCommonValue((SkuVo) o);
        LOGGER.info("exit select entity ...................");
    }

    /**
     * 设置backbean中一些属性的通用方法
     *
     * @param skuVo
     */
    private void setCommonValue(SkuVo skuVo) {
        this.selectedSku = skuService.findByCodeSn(skuVo.getSku().getCodeSn());
        this.selectedSkuVo = new SkuVo(this.selectedSku);
        String photos = skuVo.getProductVo().getJsonInfo().getPhotos();
        if (!Strings.isNullOrEmpty(photos)) {
            this.selectedSkuPhotos = new Gson().fromJson(photos, new TypeToken<List<PhotoVo>>() {
            }.getType());
        }
        this.selectProductId = skuVo.getSku().getProduct().getId().toString();
        this.selectProductVo = new ProductVo(skuVo.getSku().getProduct());
    }

    @Override
    public void deleteEntity() {
        this.selectedSku.setEntityStatus(EntityStatus.DELETED);
        skuService.save(this.selectedSku);
        this.init();
    }


    /**
     * 新建商品的时候选择产品的 codeSn,默认不选
     */
    @Setter
    @Getter
    private String selectProductId = "";

    /**
     * 新建商品的时候选择产品的 ProductVo,根据上面联动
     */
    @Getter
    @Setter
    private ProductVo selectProductVo = new ProductVo(new Product());

    public void selecteProductChange() {
        LOGGER.info(this.selectProductId);
        Product product = productService.findOne(Long.valueOf(this.selectProductId));
        if (!Strings.isNullOrEmpty(this.selectProductId)) {
            this.selectedSku.setProduct(product);
            this.selectedSkuVo = new SkuVo(this.selectedSku);
            this.selectedSkuVo.setProductVo(new ProductVo(product));
            String photos = this.selectedSkuVo.getProductVo().getJsonInfo().getPhotos();
            if (!Strings.isNullOrEmpty(photos)) {
                this.selectedSkuPhotos = new Gson().fromJson(photos, new TypeToken<List<PhotoVo>>() {
                }.getType());
            } else {
                this.selectedSkuPhotos = Lists.newArrayList();
            }
            this.selectProductVo = new ProductVo(product);


        } else {
            this.selectedSku.setProduct(new Product());
            this.selectedSkuVo = new SkuVo(selectedSku);
            this.selectedSkuVo.setProductVo(new ProductVo(new Product()));
            this.selectProductVo = new ProductVo(new Product());

        }
    }

    public void saveMerchandise(ActionEvent e) {
        Sku sku = this.selectedSkuVo.getSku();
        JsonInfo jsonInfo = this.selectedSkuVo.getJsonInfo();

        if (Strings.isNullOrEmpty(sku.getCodeSn())) {
            sku.setCodeSn(sku.getProduct().getProductType().equals(ProductType.PHYSICAL) ? PrimaryCodeSn
                    .SKU_MERCHANDISE.nextValue(sequenceService).toString() : PrimaryCodeSn.SKU_COUPON.nextValue(sequenceService));
            sku.setName(sku.getProduct().getName());
            sku.setLongName(sku.getProduct().getLongName());
            sku.setTradeDirection(TradeDirection.NORMAL);
            //  查询该商品相关的订单信息,计算实时库存

            sku.setRemainedInventory(sku.getInventory());

            sku.setLimitation(SkuLimitation.LIMITED);

            sku.setOnlineTime(LocalDateTime.now());
            sku.setOfflineTime(LocalDateTime.now().plusYears(10L));

            sku.setEntityStatus(EntityStatus.ACTIVE);
            sku.setPropertyType(PropertyType.CASH);
            sku.setInfo(sku.getProduct().getInfo());
            //  默认审核通过,即,待上架
            sku.setSkuStatus(SkuStatus.AUDIT_PASSED);

            sku.setJsonInfo(new Gson().toJson(jsonInfo));

            sku.setOwner(sku.getProduct().getCreater());
            this.selectedSku = skuService.save(sku);

            this.selectedSkuVo = new SkuVo(this.selectedSku);
            this.selectedSkuVo.setProductVo(new ProductVo(this.selectedSku.getProduct()));
            qrCodeService.createTwoDimensionCode(this.selectedSku.getCodeSn());
        } else {
            this.selectedSku.setJsonInfo((this.selectedSkuVo.getJsonInfo()) != null ? new Gson().toJson
                    (this.selectedSkuVo.getJsonInfo()) : null);
            this.selectedSku = skuService.save(this.selectedSku);
            this.selectedSkuVo = new SkuVo(this.selectedSku);
            this.selectedSkuVo.setProductVo(new ProductVo(this.selectedSku.getProduct()));
        }

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("保存成功", "保存成功"));
        RequestContext.getCurrentInstance().execute("PF('detailDialog').hide()");
    }

    /**
     * 上下架操作
     *
     * @param o
     */
    public void onOffline(Object o, SkuStatus skuStatus) {
        setCommonValue((SkuVo) o);
        this.selectedSkuVo.getSku().setSkuStatus(skuStatus.equals(SkuStatus.OFFLINE) ? skuStatus.OFFLINE : SkuStatus.OFFLINE);
        this.selectedSku = skuService.save(this.selectedSkuVo.getSku());
        setCommonValue(new SkuVo(this.selectedSku));
        FacesContext.getCurrentInstance().addMessage(null, skuStatus.equals(SkuStatus.OFFLINE) ? new FacesMessage("提示", "下架成功") : new FacesMessage("提示", "上架成功"));
    }

    /**
     * FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("form-:search_name")
     * 获取搜索表单的参数
     * 调用自定义查询方法
     */

    public void paramSearch() throws Exception {


        Map<String, String> requestParameterMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        Map<String, Object> paramMap = Maps.newHashMap();
        if (!Strings.isNullOrEmpty(requestParameterMap.get("search_form:merchandiseName"))) {
            paramMap.put("merchandiseName", requestParameterMap.get("search_form:merchandiseName"));
        }

        if (!Strings.isNullOrEmpty(requestParameterMap.get("search_form:merchantName"))) {
            paramMap.put("merchantName", requestParameterMap.get("search_form:merchantName"));
        }

        if (!Strings.isNullOrEmpty(requestParameterMap.get("search_form:productCodeSn"))) {
            paramMap.put("productCodeSn", requestParameterMap.get("search_form:productCodeSn"));
        }

        if (!Strings.isNullOrEmpty(requestParameterMap.get("search_form:ifVoucher_input")) && !"-1".equals
                (requestParameterMap.get("search_form:ifVoucher_input"))) {
            paramMap.put("ifVoucher", requestParameterMap.get("search_form:ifVoucher_input"));
        }

        if (!Strings.isNullOrEmpty(requestParameterMap.get("search_form:category_input")) && !"-1".equals
                (requestParameterMap.get("search_form:category_input"))) {
            paramMap.put("category", Integer.valueOf(requestParameterMap.get("search_form:category_input")));
        }

        if (!Strings.isNullOrEmpty(requestParameterMap.get("search_form:status_input")) && !"-1".equals
                (requestParameterMap.get("search_form:status_input"))) {
            paramMap.put("status", SkuStatus.valueOf(requestParameterMap.get("search_form:status_input")));
        }

        if (!Strings.isNullOrEmpty(requestParameterMap.get("search_form:productType_input")) && !"-1".equals
                (requestParameterMap.get("search_form:productType_input"))) {
            paramMap.put("productType", ProductType.valueOf(requestParameterMap.get("search_form:productType_input")));
        }

        if (!Strings.isNullOrEmpty(requestParameterMap.get("search_form:dateStart_input"))) {
            String dateStr = requestParameterMap.get("search_form:dateStart_input");
            LocalDateTime localDateTime = LocalDateTime.of(Integer.valueOf(dateStr.substring(0, 4)), Integer.valueOf(dateStr.substring(5, 7)), Integer
                    .valueOf(dateStr.substring(9)), 0, 0);
            paramMap.put("onlineTimeStart", localDateTime);
        }

        if (!Strings.isNullOrEmpty(requestParameterMap.get("search_form:dateEnd_input"))) {
            String dateStr = requestParameterMap.get("search_form:dateEnd_input");
            LocalDateTime localDateTime = LocalDateTime.of(Integer.valueOf(dateStr.substring(0, 4)), Integer.valueOf(dateStr.substring(5, 7)), Integer
                    .valueOf(dateStr.substring(9)), 0, 0);
            paramMap.put("onlineTimeEnd", localDateTime);
        }

        this.lazyDataModel = new MerchandiseLazyDataModel(skuService, paramMap);
        RequestContext.getCurrentInstance().update(":form1:infoTable");

    }


    public void paramReset() {

    }


}
