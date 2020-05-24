package jp.co.rakus_partners.rakusitem.controller;

import jp.co.rakus_partners.rakusitem.entity.Category;
import jp.co.rakus_partners.rakusitem.form.SearchForm;
import jp.co.rakus_partners.rakusitem.service.CategoryService;
import jp.co.rakus_partners.rakusitem.service.ItemService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/item")
public class ItemConroller {

    public static final int ROW_PAR_PAGE = 30;

    @Autowired
    private HttpSession session;

    @Autowired
    private ItemService itemService;

    @Autowired
    private CategoryService categoryService;

    @ModelAttribute
    private SearchForm setupForm() {
        return new SearchForm();
    }

    @RequestMapping("/search")
    public String search(SearchForm searchForm, Model model) {
        Integer searchCount = itemService.searchCount(searchForm);
        int maxPage = searchCount / ROW_PAR_PAGE;
        if (searchCount % ROW_PAR_PAGE != 0) {
            maxPage++;
        }
        if (searchForm.getPage() > maxPage) {
            searchForm.setPage(1);
        }

        List<Category> daiCategories = categoryService.findByParentCategoryId(null);
        List<Category> chuCategories = null;
        List<Category> syoCategories = null;
        if (searchForm.getDaiCategoryId() != null) {
            chuCategories = categoryService.findByParentCategoryId(searchForm.getDaiCategoryId());
        }
        if (searchForm.getChuCategoryId() != null) {
            syoCategories = categoryService.findByParentCategoryId(searchForm.getChuCategoryId());
        }
        model.addAttribute("daiCategories", daiCategories);
        model.addAttribute("chuCategories", chuCategories);
        model.addAttribute("syoCategories", syoCategories);

        if ("search".equals(searchForm.getActionMode())) {
//            model.addAttribute("itemList", itemService.search(searchForm));
            session.setAttribute("itemList", itemService.search(searchForm));
        }
        model.addAttribute("maxPage", maxPage);
        return "list.html";
    }

}
