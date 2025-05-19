package com.akash.medistock.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.akash.medistock.entity.Cart;
import com.akash.medistock.entity.Medicine;
import com.akash.medistock.entity.Order;
import com.akash.medistock.entity.User;
import com.akash.medistock.repository.CartRepository;
import com.akash.medistock.repository.MedicineRepository;
import com.akash.medistock.repository.OrderRepository;
import com.akash.medistock.repository.UserRepository;
import com.akash.medistock.request.OrderRequest;
import com.akash.medistock.response.OrderResponse;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private MedicineRepository medicineRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CartRepository cartRepositoryl;

	public OrderResponse addOrder(OrderRequest orderRequest) {
		Optional<User> optionalUser = userRepository.findById(orderRequest.getUserId());
		Optional<Medicine> optionalMedicine = medicineRepository.findById(orderRequest.getMedicineId());
		if (optionalUser.isPresent() && optionalUser.get().getRole().equals("USER") && optionalMedicine.isPresent()) {
			Order order = new Order();
			order.setNumber(order.hashCode());
			order.setDate(new Date(System.currentTimeMillis()));
			order.setStatus(orderRequest.getStatus());
			order.setMedicine(optionalMedicine.get());
			Order addedOrder = orderRepository.save(order);
			User user = optionalUser.get();
			List<Order> orders = user.getOrders();
			orders.add(addedOrder);
			user.setOrders(orders);
			Cart cart = user.getCart();
			List<Medicine> medicines = cart.getMedicines();
			medicines.remove(optionalMedicine.get());
			cart.setMedicines(medicines);
			cartRepositoryl.save(cart);
			userRepository.save(user);
			OrderResponse orderResponse = new OrderResponse();
			orderResponse.setNumber(addedOrder.getNumber());
			orderResponse.setDate(addedOrder.getDate());
			orderResponse.setStatus(addedOrder.getStatus());
			orderResponse.setMedicine(addedOrder.getMedicine());
			return orderResponse;
		} else {
			return null;
		}
	}

}
