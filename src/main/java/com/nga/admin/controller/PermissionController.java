package com.nga.admin.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import com.nga.admin.entity.po.Permission;
import com.nga.admin.service.IPermissionService;
import com.nga.admin.common.utils.PageUtils;
import com.nga.admin.common.utils.R;



/**
 * 
 *
 * @author ChenDingheng
 * @email m13411907763@163.com
 * @date 2021-08-23 14:36:31
 */
@Slf4j
@Api(tags="PermissionController的接口")
@RestController
@RequestMapping("api/admin/permission")
public class PermissionController {
    @Autowired
    private IPermissionService permissionService;



    /**
     * 列表
     */
    @GetMapping("/page")
    @ApiOperation(value="按页获取",notes = "第一个参数page表示第几页，第二个参数limit表示每页几条记录")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = permissionService.queryPage(params);
        return R.ok().put(page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{permissionId}")
    @ApiOperation(value="根据主键获取")
    public R info(@PathVariable("permissionId") Integer permissionId){
		Permission permission = permissionService.getById(permissionId);
        return R.ok().put(permission);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @ApiOperation(value = "添加")
    public R save(@RequestBody Permission permission){
        if (permissionService.save(permission))return R.ok("保存成功");
        return R.error("保存失败");
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    @ApiOperation(value = "修改")
    public R update(@RequestBody Permission permission){
        if (permissionService.updateById(permission))return R.ok("修改成功");
        return R.error("修改失败");
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    @ApiOperation(value = "删除")
    public R delete(Integer[] ids){
        if (permissionService.removeByIds(Arrays.asList(ids)))return R.ok("删除成功");
        return R.error("删除失败");
    }

}
