package com.datn.boarding_house_management_rental_website.controller;

import com.datn.boarding_house_management_rental_website.entity.models.DTO.MessageDTO;
import com.datn.boarding_house_management_rental_website.entity.models.Message;
import com.datn.boarding_house_management_rental_website.entity.models.MessageChat;
import com.datn.boarding_house_management_rental_website.entity.models.User;
import com.datn.boarding_house_management_rental_website.repository.MessageChatRepository;
import com.datn.boarding_house_management_rental_website.repository.MessageRepository;
import com.datn.boarding_house_management_rental_website.repository.UserRepository;
import com.datn.boarding_house_management_rental_website.secruity.CurrentUser;
import com.datn.boarding_house_management_rental_website.secruity.UserPrincipal;
import com.datn.boarding_house_management_rental_website.services.impl.MessageServiceImpl;
import com.datn.boarding_house_management_rental_website.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MessageController {
	
	@Autowired
	private MessageServiceImpl messageServiceImpl;
	
	@Autowired
	private UserServiceImpl userServiceImpl;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private MessageRepository messageRepository;
	
	@Autowired
	private MessageChatRepository messageChatRepository;
	
	@Autowired
    private SimpMessagingTemplate messagingTemplate;

	@GetMapping("/send")
	public Message testSendMessage() {
//		String email1 = "giangnam.trunghoccoso@gmail.com", email2 = "giangnam.trunghocphothong@gmail.com";
//		messageServiceImpl.Producer(email1, email2);
//		messageServiceImpl.Consumer(email1, email2);
//		User sender = userRepository.findByEmail(email1).get();
//		User receiver = userRepository.findByEmail(email2).get();
//		Message message = messageRepository.findBySenderAndReceiver(sender, receiver);
//		System.out.println(message.getId());
//		List <MessageChat> messageChats = message.getContent();
//		if (!(messageChats != null)) messageChats = new ArrayList<>();
//		MessageChat messageChat = new MessageChat();
//		messageChat.setContent("test tinh nang!!!");
//		messageChat.setMessage(message);
//		messageChat.setRead(false);
//		messageChat.setSendBy(true);
//		messageChat.setSentAt(new Date());
//		messageChats.add(messageChat);
//		messageChatRepository.save(messageChat);
//		message.setContent(messageChats);
//		
//		messageRepository.save(message);
		//return "success";
		return userServiceImpl.getMessageChatUser(Long.valueOf(1), Long.valueOf(2));
	}

	@GetMapping("/user/message")
	@PreAuthorize("hasRole('USER') or hasRole('RENTALER')")
    public List<MessageDTO> getMessageUser() {
        return userServiceImpl.getMessageUser();
    }
	
	@GetMapping("/user/message/{userName}")
//	@PreAuthorize("hasRole('USER') or hasRole('RENTALER')")
    public List<User> findMessageUser(@CurrentUser UserPrincipal userPrincipal, @PathVariable String userName) {
        return userServiceImpl.findMessageUser(userName);
    }
	
	@GetMapping("/user/message-chat/{userId}")
//	@PreAuthorize("hasRole('USER') or hasRole('RENTALER')")
    public Message getMessageChatUser(@CurrentUser UserPrincipal userPrincipal, @PathVariable Long userId) {
		return userServiceImpl.getMessageChatUser(userPrincipal.getId(), userId);
    }
	
	
	@MessageMapping("/user/message-chat/{user}/{userId}")
	@SendTo("/topic/messages")
    public String addChatUser(@DestinationVariable Long user, @DestinationVariable  Long userId, @Payload MessageChat messageChat) {
		System.out.println("Current user: " + user);
		System.out.println("User: " + userId);
		String result = userServiceImpl.addChatUser(user,userId, messageChat);
		//String path = "/" + userId.toString() + "/queue/chat";
		if (result.equals("Gửi tin nhắn thành công!!!")) {
			try {
				messagingTemplate.convertAndSend("/queue/messages", result);
				System.out.println("gửi message thành công");
			} catch (Exception e) {
				System.out.println("gửi message thất bại");
			}
			
		}
		String path = user.toString() + " " + userId.toString();
		return path;
        //return new ResponseEntity<String>(result, result.equals("Gửi tin nhắn thành công!!!") ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
}
