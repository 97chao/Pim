package jd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jd.po.ResultModel;
import jd.service.ProductService;

@Controller
public class ProductController {

	@Autowired
	private ProductService service;
	
	@RequestMapping("/list")
	public String list(String queryString,String catalog_name,String price,
			String sort,Integer page,Model model) throws Exception{
		ResultModel rm = service.getProducts(queryString, catalog_name, price, sort, page);
		
		//将查询结果放到request域中
		model.addAttribute("result",rm);
		//简单类型数据回显
		model.addAttribute("queryString",queryString);
		model.addAttribute("price",price);
		model.addAttribute("sort",sort);
		model.addAttribute("page",page);
		
		
		return "product_list";
	}
	
}
