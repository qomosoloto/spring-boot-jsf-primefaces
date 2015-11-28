package com.shenbian.admin.service.crud;

import com.google.common.collect.Lists;
import com.mysql.jdbc.Statement;
import com.shenbian.admin.domain.crud.Crud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by qomo on 15-9-26.
 */
public class CrudServiceImpl implements CrudServiceExt {
    public static final String[] SALT_SOURCE_ARRAY = new String[]{"a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};

    public static final String QUERY_MERCHANDISES_SQL = "SELECT\n" +
            "  mechd.*,\n" +
            "  GROUP_CONCAT(\n" +
            "      CONCAT(\n" +
            "          '{',\n" +
            "          '\"end_time\":', '\"', ifnull(mechd.end_time, 'null'), '\",',\n" +
            "          '\"created_time\":', '\"', ifnull(mechd.created_time, 'null'), '\",',\n" +
            "          '\"modified_time\":', '\"', ifnull(mechd.modified_time, 'null'), '\",',\n" +
            "          '\"begin_time\":', '\"', ifnull(mechd.begin_time, 'null'), '\",',\n" +
            "\n" +
            "          '\"max_discount\":', '\"', ifnull(mechd.max_discount, 'null'), '\",',\n" +
            "          '\"merchandise_label\":', '\"', ifnull(mechd.merchandise_label, 'null'), '\",',\n" +
            "          '\"merchandise_status\":', '\"', ifnull(mechd.merchandise_status, 'null'), '\",',\n" +
            "          '\"merchandise_type\":', '\"', ifnull(mechd.merchandise_type, 'null'), '\",',\n" +
            "          '\"name\":', '\"', ifnull(mechd.name, 'null'), '\",',\n" +
            "\n" +
            "          '\"show_supplier\":', '\"', ifnull(bin(mechd.show_supplier), '0'), '\",',\n" +
            "          '\"show_supplier\":', '\"', ifnull(bin(mechd.usesncode), '0'), '\",',\n" +
            "          '\"notify_type\":', '\"', ifnull(mechd.notify_type, 'null'), '\",',\n" +
            "          '\"price\":', '\"', ifnull(mechd.price, 'null'), '\",'\n" +
            "          '\"stock\":', '\"', ifnull(mechd.stock, 'null'), '\",',\n" +
            "          '\"total_limit\":', '\"', ifnull(mechd.total_limit, 'null'), '\",',\n" +
            "          '\"transport_cost\":', '\"', ifnull(mechd.transport_cost, 'null'), '\",',\n" +
            "          '\"weight\":', '\"', ifnull(mechd.weight, 'null'), '\",',\n" +
            "          '\"category_id\":', '\"', ifnull(mechd.category_id, 'null'), '\",'\n" +
            "          '\"seller_id\":', '\"', ifnull(mechd.seller_id, 'null'), '\",',\n" +
            "          '\"daily_limit\":', '\"', ifnull(mechd.daily_limit, 'null'), '\",'\n" +
            "      )) AS       info1,\n" +
            "  group_concat(concat(\n" +
            "                   '{',\n" +
            "                   '\"id\":', '\"', ifnull(mechdImg.id, 'null'), '\",',\n" +
            "                   '\"created_time\":', '\"', ifnull(mechdImg.created_time, 'null'), '\",',\n" +
            "                   '\"modified_time\":', '\"', ifnull(mechdImg.modified_time, 'null'), '\",',\n" +
            "                   '\"image_type\":', '\"', ifnull(mechdImg.image_type, 'null'), '\",',\n" +
            "                   '\"url\":', '\"', ifnull(mechdImg.url, 'null'), '\",',\n" +
            "                   '\"merchandise_id\":', '\"', ifnull(mechdImg.merchandise_id, 'null'), '\"}'\n" +
            "               )) photos\n" +
            "FROM merchandises mechd LEFT JOIN merchandise_images mechdImg ON mechd.id = mechdImg.merchandise_id\n" +
            "GROUP BY mechd.id";


    private static final String INSERT_NG_PRODUCT_SQL = "insert into (name,sex) values(?,?)";
    @Autowired
    private CrudService crudService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Crud> paramSearch(String name, Integer age) {

        return crudService.findAll();
    }

    @Override
    public List<Map<String, Object>> selectAsJson() {

        //  1.查询 merchandises 表
        //
        return jdbcTemplate.queryForList("SELECT id as merchandises_id ,group_concat(concat('{name:\"',name,'\",','age:\"',age,'\"}')) list FROM crud GROUP BY id ", new Object[]{}, new int[]{});
    }

    @Override
    public List<Map<String, Object>> selectMerchandisesJSON() {

        List<Map<String, Object>> list = Lists.newArrayList();
        //  1.先出导出目的表中 merchandises_id的最大值 todo


        //  2.
        list = jdbcTemplate.queryForList(QUERY_MERCHANDISES_SQL);
        for (Map<String, Object> stringObjectMap : list) {
            System.out.println(stringObjectMap.get("id") + "-----------------" + stringObjectMap.get("info"));
            stringObjectMap.put("info", (String) stringObjectMap.get("info1") + "\"rule\":\"" + (String) stringObjectMap.get("rule") + "\"," + "\"description\":\"" + (String) stringObjectMap.get("description") + "\"," + "\"content\":\"" + (String) stringObjectMap.get("content") + "\",\"photos\":[" + stringObjectMap.get("photos") + "]}");


            KeyHolder key = new GeneratedKeyHolder();


            String ng_product_sn = codeSnGenerator();
            jdbcTemplate.update(connection -> {
                PreparedStatement preState = connection.prepareStatement("INSERT INTO ng_product(code_sn, status, last_modified_time, extra_info, info, long_name, name, entity_status, creater_code_sn) VALUES (?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                preState.setString(1, ng_product_sn);
                preState.setInt(2, (Integer) stringObjectMap.get("merchandise_status"));
                preState.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
                preState.setString(4, "");
                preState.setString(5, (String) stringObjectMap.get("info"));
                preState.setString(6, (String) stringObjectMap.get("name"));
                preState.setString(7, (String) stringObjectMap.get("name"));
                preState.setInt(8, 0);
                preState.setString(9, "");

                return preState;
            }, key);

            //  返回的id
            int product_id = key.getKey().intValue();
            KeyHolder key1 = new GeneratedKeyHolder();
            //  插入 ng_sku表
            jdbcTemplate.update(connection -> {
                PreparedStatement preState = connection.prepareStatement("INSERT INTO ng_sku(code_sn, status, last_modified_time,  merchandises_id, offline_time, online_time, remained_stock, stock, product_code_sn, entity_status, owner_code_sn, inventory, protected_unit_price, remained_inventory, unit_price, limitation, trade_direction, charge_account_code_sn) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
                preState.setString(1, codeSnGenerator());
                preState.setInt(2, (Integer) stringObjectMap.get("merchandise_status"));
                preState.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
                preState.setLong(4, (Long) stringObjectMap.get("id"));
                preState.setTimestamp(5, null);
                preState.setTimestamp(6, null);
                //  remained_stock
                preState.setInt(7, (Integer) stringObjectMap.get("stock"));
                //  stock 库存
                preState.setInt(8, (Integer) stringObjectMap.get("stock"));
                //  product_code_sn
                preState.setString(9, ng_product_sn);
                //  entity_status
                preState.setInt(10, 0);
                //  owner_code_sn 常量 暂代替 todo
                preState.setString(11, "TMP_OWNER_CODE_SN");
                //  inventory 库存
                preState.setBigDecimal(12, new BigDecimal(stringObjectMap.get("price").toString()));
                //  protected_unit_price
                preState.setBigDecimal(13, new BigDecimal(stringObjectMap.get("price").toString()));
                //  remained_inventory
                preState.setBigDecimal(14, new BigDecimal((Integer) stringObjectMap.get("stock")));
                //  unit_price
                preState.setBigDecimal(15, new BigDecimal(stringObjectMap.get("price").toString()));
                //  limitation ? daily limit?
                preState.setInt(16, 1);
                //  trade_direction
                preState.setInt(17, 1);
                //  charge_account_code_sn
                preState.setString(18, "charge_code_sn");
                return preState;
            }, key1);
        }


        return list;
    }


    /**
     * 随机生成32位UUID，由于UUID都为十六进制，所以将UUID分成8组，每4个为一组，然后通过模62操作，结果作为索引取出字符，
     *
     * @return
     */
    private static String codeSnGenerator() {
        StringBuffer shortBuffer = new StringBuffer();

        int j = 0;
        while (j < 3) {
            String uuid = UUID.randomUUID().toString().replace("-", "");
            System.out.println(uuid);
            for (int i = 0; i < 8; i++) {
                String str = uuid.substring(i * 4, i * 4 + 4);
                int x = Integer.parseInt(str, 16);
                shortBuffer.append(SALT_SOURCE_ARRAY[x % 0x3E]);
            }
            j++;
        }

        return shortBuffer.toString().substring(0, 20);
    }

//    public static void main(String[] args) {
//        System.out.println(codeSnGenerator());
//    }
}
