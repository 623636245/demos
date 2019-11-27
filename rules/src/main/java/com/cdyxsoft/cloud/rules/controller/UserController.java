package com.cdyxsoft.cloud.rules.controller;

import com.cdyxsoft.cloud.rules.core.base.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

 /**
 * @Description: 用户信息
 * @author： LuBangTao
 * @date：   2019-11-19 14:43:04
 */
@RestController
@RequestMapping("/rules/user")
@Slf4j
public class UserController extends BaseController {
	 /**
	  * @description: handleCreate
	  * Author: LuBangTao
	  * date: 2019-11-25 13:03
	  * version: 1.0
	  * @return void
	 */
	@RequestMapping("/create")
	public void handleCreate(){
		userService.handleCreate();
	}

	 /**
	  * @description: handleDelete
	  * Author: LuBangTao
	  * date: 2019-11-25 13:03
	  * version: 1.0
	  * @return void
	 */
	 @RequestMapping("/delete")
	public void handleDelete(){
		userService.handleDelete();
	}

	 /**
	  * @description: handleUpdate
	  * Author: LuBangTao
	  * date: 2019-11-25 13:04
	  * version: 1.0
	  * @return void
	 */
	@RequestMapping("/update")
	public void handleUpdate(){
		userService.handleUpdate();
	}

	 /**
	  * @description: handleRetrieve
	  * Author: LuBangTao
	  * date: 2019-11-25 13:04
	  * version: 1.0
	  * @return void
	 */
	public void handleRetrieve(){
		userService.handleRetrieve();
	}
}
