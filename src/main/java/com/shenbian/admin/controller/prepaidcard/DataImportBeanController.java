package com.shenbian.admin.controller.prepaidcard;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.shenbian.admin.service.SequenceService;
import com.shenbian.admin.service.producerconsumer.ProducerConsumerService;
import com.shenbian.admin.service.synchronize.PrimaryCodeSn;
import com.shenbian.admin.service.synchronize.account.AccountService;
import com.shenbian.ng.model.Account;
import com.shenbian.ng.model.ProducerConsumer;
import com.shenbian.ng.model.enums.AccountStatus;
import com.shenbian.ng.model.enums.AccountType;
import lombok.Getter;
import lombok.Setter;
import org.jumpmind.symmetric.csv.CsvReader;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Created by qomo on 15-10-13.
 */
@Component(value = "dataImportBean")
@ManagedBean(name = "dataImportBean")
@ViewScoped
public class DataImportBeanController implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataImportBeanController.class);

    @Autowired
    private AccountService accountService;
    @Autowired
    private SequenceService sequenceService;
    @Autowired
    private ProducerConsumerService producerConsumerService;
    @Setter
    @Getter
    private UploadedFile file;

    @Setter
    @Getter
    private List<Account> accountList;

    @Getter
    @Setter
    private List<ProducerConsumer> producerConsumerList;


    @Setter
    @Getter
    private ProducerConsumerLazyDataModel lazyDataModel;
    /**
     * 预付费充值卡
     *
     * @throws IOException
     */
    public void prepaidCardImport() throws IOException {
        LOGGER.info(" init -----------------------------------------------");
        this.accountList = accountService.findByCodeSnStartsWith(PrimaryCodeSn.ACCOUNT_PREPAID_CARD.getCode().toString());
        FacesContext.getCurrentInstance().getExternalContext().redirect("/views/dataimport/prepaid.xhtml");
    }


    /**
     * 优惠券
     *
     * @throws IOException
     */
    public void couponImport() throws IOException {
        LOGGER.info(" init -----------------------------------------------");
        this.lazyDataModel = new ProducerConsumerLazyDataModel(this.producerConsumerService);
        FacesContext.getCurrentInstance().getExternalContext().redirect("/views/dataimport/coupons.xhtml");
    }

    @Transactional
    public void handleFileUpload(FileUploadEvent event) throws IOException {
        System.out.println("Handling Upload: " + event.getFile());
        file = event.getFile();
        InputStream input = file.getInputstream();
        int i = 0;
        CsvReader csvReader = new CsvReader(input, ',', Charset.forName("UTF-8"));
        Gson gson = new Gson();

        List<Account> accountList = Lists.newArrayList();

        while (csvReader.readRecord()) {
            String[] values = csvReader.getValues();
            //  csv : 卡号／１６，　ｓａｌｔ／　，　cadrno+pk hex /,   pwd hex / , 有效期
            Map<String, Object> map = Maps.newHashMap();
            map.put("salt", values[1]);
            map.put("card_pk_md5", values[2]);
            map.put("card_pk_pwd_md5", values[3]);
            map.put("expire_date", values[4]);
            //  保存到数据库
            Account account = new Account();
            account.setCodeSn(PrimaryCodeSn.ACCOUNT_PREPAID_CARD.nextValue(sequenceService));
            account.setName(values[0]);
            account.setNameShadow(values[0]);
            account.setJsonInfo(gson.toJson(map));
            account.setAccountStatus(AccountStatus.DEACTIVE);
            account.setCash(BigDecimal.ZERO);
            account.setFrozenCash(BigDecimal.ZERO);
            account.setPoint(BigDecimal.ZERO);
            account.setFrozenPoint(BigDecimal.ZERO);
            account.setVoucher(BigDecimal.ZERO);
            account.setFrozenVoucher(BigDecimal.ZERO);
            account.setType(AccountType.LIABILITY);
            account.setFrozenGameTimes(BigDecimal.ZERO);
            account.setGameTimes(BigDecimal.ZERO);

            account.setExpireDate(LocalDate.parse(values[4]).atStartOfDay());
            accountList.add(account);

            i += 1;
        }
        csvReader.close();
        accountService.save(accountList);
        this.accountList = accountService.findByCodeSnStartsWith(PrimaryCodeSn.ACCOUNT_PREPAID_CARD.getCode().toString());
        FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "导入成功！", "一共导入" + i + "条数据!"));
    }


    public void handleProducerConsumerUpload(FileUploadEvent event) throws Exception {
        producerConsumerService.importCouponsFile(event.getFile());
        this.lazyDataModel = new ProducerConsumerLazyDataModel(this.producerConsumerService);
        FacesContext.getCurrentInstance().addMessage("", new FacesMessage(FacesMessage.SEVERITY_INFO, "导入成功！", "导入成功~!"));
    }


}
