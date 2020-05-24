package jp.co.rakus_partners.rakusitem.repository;

import jp.co.rakus_partners.rakusitem.controller.ItemConroller;
import jp.co.rakus_partners.rakusitem.entity.Item;
import jp.co.rakus_partners.rakusitem.form.SearchForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@Repository
public class ItemRepository {

    private static final Logger logger = LoggerFactory.getLogger(ItemRepository.class);

    private static final RowMapper<Item> ROW_MAPPER = new BeanPropertyRowMapper<>(Item.class);

    @Autowired
    private NamedParameterJdbcTemplate namedJdbcTemplate;

    public List<Item> search(SearchForm searchForm) {

        MapSqlParameterSource params = new MapSqlParameterSource();
        String sql = createSql(searchForm, params, null);
        return namedJdbcTemplate.query(sql, params, ROW_MAPPER);

    }

    public Integer searchCount(SearchForm searchForm) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        String sql = createSql(searchForm, params, "count");
        return namedJdbcTemplate.queryForObject(sql, params, Integer.class);
    }

    private String createSql(SearchForm searchForm, MapSqlParameterSource params, String mode) {

        String sql = "SELECT";
        sql += "  i.id id";
        sql += " , i.name \"name\"";
        sql += " , condition";
        sql += " , category";
        sql += " , c_chu.id chu_category_id";
        sql += " , c_chu.parent dai_category_id";
        sql += " , category";
        sql += " , brand";
        sql += " , price";
        sql += " , shipping";
        sql += " , description";
        sql += " , c_syo.name_all";
        sql += " FROM items i";
        sql += " LEFT JOIN category c_syo ON c_syo.id = i.category";
        sql += " LEFT JOIN category c_chu ON c_chu.id = c_syo.parent";
        sql += " WHERE 1 = 1";

        // カテゴリー
        if ("search".equals(searchForm.getActionMode())) {
            if (searchForm.getSyoCategoryId() != null) {
                sql += " AND c_syo.id = :c_syo";
                params.addValue("c_syo", searchForm.getSyoCategoryId());
            } else if (searchForm.getChuCategoryId() != null) {
                sql += " AND c_chu.id = :c_chu";
                params.addValue("c_chu", searchForm.getChuCategoryId());
            } else if (searchForm.getDaiCategoryId() != null) {
                sql += " AND c_chu.parent = :c_dai";
                params.addValue("c_dai", searchForm.getDaiCategoryId());
            }
        }

        // 商品名（あいまい検索）
        if (!StringUtils.isEmpty(searchForm.getItemKeyword())) {
            sql += " AND i.name LIKE :name";
            params.addValue("name", "%" + searchForm.getItemKeyword() + "%");
        }
        // ブランド名
        if (!StringUtils.isEmpty(searchForm.getBrand())) {
            sql += " AND brand = :brand";
            params.addValue("brand", searchForm.getBrand());
        }
        if ("count".equals(mode)) {
            sql = sql.replaceFirst("SELECT.+FROM", "SELECT count(*) FROM");
        } else {
            sql += " ORDER BY i.id";
            sql += " LIMIT 30 OFFSET " + ItemConroller.ROW_PAR_PAGE * (searchForm.getPage() - 1);
        }
        logger.info("sql = " + sql);
        for (String paramName : params.getParameterNames()) {
            logger.info(paramName + " = " + params.getValue(paramName));
        }
        return sql;
    }


    /**
     * ボツコード.
     * パラメータをnullにすればその条件はなかったことにできないか確認したがダメだった.
     *
     * @param searchForm
     * @param params
     * @param mode
     * @return
     */
    private String createSqlTest(SearchForm searchForm, MapSqlParameterSource params, String mode) {

        String sql = "SELECT";
        sql += "  i.id id";
        sql += " , i.name \"name\"";
        sql += " , condition";
        sql += " , category";
        sql += " , brand";
        sql += " , price";
        sql += " , shipping";
        sql += " , description";
        sql += " , name_all";
        sql += " FROM items i";
        sql += " LEFT JOIN category cate_syo ON cate_syo.id = i.category";
        sql += " LEFT JOIN category cate_chu ON cate_chu.id = cate_syo.parent";
//        sql += " LEFT JOIN category cate_dai ON cate_dai.id = cate_chu.parent";
        sql += " WHERE 1 = 1";
        sql += " AND i.name LIKE :name";
        sql += " AND brand = :brand";
        sql += " AND cate_syo.id = :cate_syo";
        sql += " AND cate_chu.id = :cate_chu";
        sql += " AND cate_chu.parent = :cate_dai";

        // カテゴリー
        if (searchForm.getSyoCategoryId() != null) {
            params.addValue("cate_syo", searchForm.getSyoCategoryId());
            params.addValue("cate_chu", null);
            params.addValue("cate_dai", null);
        } else if (searchForm.getChuCategoryId() != null) {
            params.addValue("cate_syo", null);
            params.addValue("cate_chu", searchForm.getChuCategoryId());
            params.addValue("cate_dai", null);
        } else if (searchForm.getDaiCategoryId() != null) {
            params.addValue("cate_syo", null);
            params.addValue("cate_chu", null);
            params.addValue("cate_dai", searchForm.getDaiCategoryId());
        }

        // 商品名（あいまい検索）
        if (!StringUtils.isEmpty(searchForm.getItemKeyword())) {
            params.addValue("name", "%" + searchForm.getItemKeyword() + "%");
        }
        // ブランド名
        if (!StringUtils.isEmpty(searchForm.getBrand())) {
            params.addValue("brand", searchForm.getBrand());
        } else {
            params.addValue("brand", null);
        }
        if ("count".equals(mode)) {
            sql = sql.replaceFirst("SELECT.+FROM", "SELECT count(*) FROM");
        } else {
            sql += " ORDER BY i.id";
            sql += " LIMIT 30 OFFSET " + ItemConroller.ROW_PAR_PAGE * (searchForm.getPage() - 1);
        }
        logger.info("sql = " + sql);
        for (String paramName : params.getParameterNames()) {
            logger.info(paramName + " = " + params.getValue(paramName));
        }
        return sql;
    }


}
