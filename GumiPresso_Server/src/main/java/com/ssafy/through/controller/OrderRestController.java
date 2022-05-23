package com.ssafy.through.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.javassist.compiler.ast.Stmnt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.through.model.dto.AosOrderDetail;
import com.ssafy.through.model.dto.AosOrderForm;
import com.ssafy.through.model.dto.Order;
import com.ssafy.through.model.dto.OrderDetail;
import com.ssafy.through.model.dto.OrderForm;
import com.ssafy.through.model.dto.Product;
import com.ssafy.through.model.dto.RecentOrder;
import com.ssafy.through.model.dto.Stamps;
import com.ssafy.through.model.dto.Table;
import com.ssafy.through.model.dto.User;
import com.ssafy.through.model.service.OrderDetailService;
import com.ssafy.through.model.service.OrderService;
import com.ssafy.through.model.service.StampsService;
import com.ssafy.through.model.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(allowCredentials = "true", originPatterns = { "*" })
@RestController
@RequestMapping("/order")
@Api(value = "Order")
public class OrderRestController {

	@Autowired
	private OrderService oService;

	@Autowired
	private OrderDetailService detailService;

	@Autowired
	private UserService uService;
	
	@Autowired
	private StampsService sService;


	@ApiOperation(value = "이름(loginUser.id) 클릭시 -> 모든 주문 정보 return: List<Order>", response = Order.class)
	@PostMapping("/")
	public ResponseEntity<?> selectOrders(@RequestBody User user) {
		List<Order> orders = oService.select(user.getId());
		if (orders != null) {
			return new ResponseEntity<List<Order>>(orders, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}

	@ApiOperation(value = "주문시 -> 주문 insert List<OrderForm> + update User.stamps ", response = OrderForm.class)
	@PostMapping("/order")
	public ResponseEntity<?> insertOrder(@RequestBody List<OrderForm> orderForm) {
		Order order = new Order(orderForm.get(1).getUserId());		
		int result = oService.insert(order);
		

		if (result > 0) {
			int result1 = oService.search();
			int stamps = 0;
			for (int i = 1; i < orderForm.size(); i++) {
				orderForm.get(i).setOrderId(result1);
				System.out.println(orderForm.get(i));
				detailService.insert(orderForm.get(i));
				stamps += orderForm.get(i).getQuantity();
			}
			User tmpUser = new User(orderForm.get(1).getUserId(), stamps);
			uService.update(tmpUser);

			return new ResponseEntity<Void>(HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NOT_ACCEPTABLE);
		}
	}

	@ApiOperation(value = "주문정보 클릭 -> 주문 상세정보  return : List<OrderDetail>", response = OrderDetail.class)
	@GetMapping("/{order_id}")
	public ResponseEntity<?> selectOrder(@PathVariable String order_id) {
		List<OrderDetail> orderDetail = detailService.select(order_id);
		if (orderDetail != null) {
			System.out.println(orderDetail);
			return new ResponseEntity<List<OrderDetail>>(orderDetail, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}

	@ApiOperation(value = "상품정보 클릭 -> 총주문갯수 return : int", response = OrderDetail.class)
	@GetMapping("/detail/{productId}")
	public ResponseEntity<?> searchOrder(@PathVariable String productId) {
		int result = detailService.search(productId);
		if (result > 0) {
			return new ResponseEntity<Integer>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping("/recent/{userId}")
	public ResponseEntity<?> searchRecentOrder(@PathVariable String userId) {
		List<Order> orderList = detailService.searchRecentOrder(userId);
		List<RecentOrder> recentOrderList = new ArrayList<RecentOrder>();
		if (orderList != null) {
			for (int i = 0; i < orderList.size(); i++) {
				List<OrderDetail> orderDetailList = detailService.select(orderList.get(i).getoId().toString());
				RecentOrder recentOrder = new RecentOrder(orderList.get(i).getoId(), orderList.get(i).getOrderTime(), orderList.get(i).getOrderTable(), orderList.get(i).getCompleted(),
						orderDetailList);
				recentOrderList.add(recentOrder);
			}
			System.out.println(recentOrderList);
			return new ResponseEntity<List<RecentOrder>>(recentOrderList, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}

	@GetMapping("/order/{orderId}")
	public ResponseEntity<?> searchRecentOrderByOrderId(@PathVariable String orderId) {
		Order order = detailService.searchOrderDetail(orderId);
		System.out.println(order);
		if (order != null) {
			List<OrderDetail> orderDetailList = detailService.select(order.getoId().toString());
			RecentOrder recentOrder = new RecentOrder(order.getoId(), order.getOrderTime(), orderDetailList);
			System.out.println(recentOrder);			
			return new ResponseEntity<RecentOrder>(recentOrder, HttpStatus.OK);
		} else {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
	}
	
	@PostMapping("/new") 
	public ResponseEntity<?> orderUseOrderForm(@RequestBody AosOrderForm aosOrderForm){		
		Order order = aosOrderForm.getOrder();
		oService.insert(order);			
		System.out.println(aosOrderForm);
		List<AosOrderDetail> list = aosOrderForm.getAosOrderList();		
		System.out.println(aosOrderForm.getAosOrderList().toString());
		int orderId = oService.search();
		int stamps = 0;
		for(int i = 0; i < list.size(); i++) {
			detailService.insert(new OrderForm(order.getUserId(), orderId, list.get(i).getProductId(), list.get(i).getQuantity()));
			stamps += list.get(i).getQuantity();
		}
		uService.update(new User(order.getUserId(), stamps));
		sService.insert(new Stamps(order.getUserId(), orderId, stamps));
		return new ResponseEntity<Void>(HttpStatus.OK);
		
	}
	
	@GetMapping("/table")
	public ResponseEntity<?> getTable(){
		List<Table> list = oService.getTable();
		return new ResponseEntity<List<Table>>(list,HttpStatus.OK);
	}
	@GetMapping("/table/{tableId}")
	public ResponseEntity<?> getTable(@PathVariable int tableId){
		int result = oService.updateTable(tableId);
		if(result > 0) {
			List<Table> list = oService.getTable();
			return new ResponseEntity<List<Table>>(list,HttpStatus.OK);
		}
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	

//	@PostMapping("/test")
//	public ResponseEntity<?> test(@RequestBody List<Product> orderForm){
//		Order order = new Order(orderForm.get(0).getPrice());
//		System.out.println(orderForm);
//		System.out.println(order);
//		//안녕하세요 제발 되게 해주세요
//		//이제 됩니다ㅠㅠ
//		return null;
//	}
	
	

}
