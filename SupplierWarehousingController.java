/*
 *      Copyright (c) 2018-2028, Chill Zhuang All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the dreamlu.net developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: Chill 庄骞 (smallchill@163.com)
 */
 
 
 
package org.springblade.supplier.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tenant.annotation.NonDS;
import org.springblade.core.tool.api.R;
import org.springblade.supplier.service.ISupplierWarehousingService;
import org.springblade.supplier.vo.SupplierVO;
import org.springblade.supplier.vo.SupplierWarehousingProcessVO;
import org.springblade.supplier.vo.SupplierWarehousingVO;
import org.springblade.supplier.vo.UploadSupplierRequestVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author c-gongnq
 * @version 1.0
 * @description: 供应商入库控制器
 * @date 2021/5/8 10:09
 */
@NonDS
@RestController
@RequestMapping("/supplierWarehousing")
@AllArgsConstructor
public class SupplierWarehousingController {

	@Autowired
	private ISupplierWarehousingService supplierWarehousingService;

	/**
	 * 查询已经公司结束的公司信息
	 * @param query 分页信息
	 * @param supplierVO 公司名称（筛选条件）
	 * @param supplierVO  科目ID（筛选条件）
	 * @return 分页公司信息
	 */
	@PostMapping("/getFinalistSupplier")
	public R<IPage<SupplierWarehousingVO>> getFinalistSupplier(@RequestBody SupplierVO supplierVO, Query query) {
		IPage<SupplierWarehousingVO> pages = supplierWarehousingService.getFinalistSupplier(Condition.getPage(query), supplierVO.getSupplierName(), supplierVO.getSubjectId());
		return R.data(pages);
	}

	/**
	 * 上传战略合同
	 * @param uploadSupplierRequestVo 公司ID
	 * @param uploadSupplierRequestVo 公告ID
	 * @param uploadSupplierRequestVo 公示ID
	 * @param uploadSupplierRequestVo 附件信息
	 * @return 状态
	 */
	@PostMapping("/uploadStrategicContract")
	public R uploadStrategicContract(@RequestBody UploadSupplierRequestVo uploadSupplierRequestVo) {
		try {
			supplierWarehousingService.uploadStrategicContract(uploadSupplierRequestVo);
			return R.success("上传成功");
		}catch (ServiceException e){
			return R.fail(e.getResultCode());
		}
	}

	/**
	 * 查询已经入库的供应商信息
	 * @param query 分页信息
	 * @param supplierVO 供应商名称（筛选条件）
	 * @param supplierVO  科目ID（筛选条件）
	 * @return 分页供应商信息
	 */
	@PostMapping("/getSupplierList")
	public R<IPage<SupplierVO>> getSupplierList(Query query, @RequestBody SupplierVO supplierVO) {
		IPage<SupplierVO> pages = supplierWarehousingService.getSupplierList(Condition.getPage(query), supplierVO.getSupplierName(), supplierVO.getSubjectId());
		return R.data(pages);
	}

	/**
	 * 查询供应商入库过程信息
	 * @param supplierId 供应商ID
	 * @return 供应商入库过程数据
	 */
	@PostMapping("/getStorageProcess")
	public R<SupplierWarehousingProcessVO> getStorageProcess(@Param("supplierId") Long supplierId) {
		SupplierWarehousingProcessVO supplierWarehousingProcessVO = supplierWarehousingService.getStorageProcess(supplierId);
		return R.data(supplierWarehousingProcessVO);
	}

}
