package com.ecommerce.sopi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ecommerce.sopi.entity.MessageEntity;
import com.ecommerce.sopi.repository.MessageRepository;
import com.ecommerce.sopi.service.MessageServiceInterface;

@Service
public class MessageService implements MessageServiceInterface {

	@Autowired
	private MessageRepository messageRepository;
	
	@Override
	public void create(String title,String content) {
		MessageEntity messageEntity=new MessageEntity();
		messageEntity.setContent(content);
		messageEntity.setTitle(title);
		messageRepository.save(messageEntity);
	}
	@Override
	public List<?> getMessageForDashboard(){
		Sort sort=Sort.by("createdAt").descending();
		return messageRepository.findAll(sort);
	}
}
