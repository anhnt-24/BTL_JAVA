package com.ecommerce.sopi.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ecommerce.sopi.DTO.ADMIN.response.ClientResponse;
import com.ecommerce.sopi.entity.ClientEntity;
import com.ecommerce.sopi.globalvar.CalculateDistance;
import com.ecommerce.sopi.globalvar.ClassToList;
import com.ecommerce.sopi.globalvar.GetPreviousDate;
import com.ecommerce.sopi.repository.ClientRepository;
import com.ecommerce.sopi.service.ClientServiceInterface;

@Service
public class ClientService implements ClientServiceInterface {
	
	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private UserService userService;
	
	@Override
	public ClientEntity getToday() {
		Date today= new Date();
		if(clientRepository.findById(today).orElse(null)==null) {
			create();
		}
		ClientEntity clientEntity=clientRepository.findById(today).orElse(null);
		clientEntity.setTotal(userService.count());
		clientRepository.save(clientEntity);
		return clientEntity ;
	}
	public ClientEntity getPrevious() {
		Date today= new Date();
        Date previousDate = GetPreviousDate.get(today);
		ClientEntity previous=clientRepository.findById(previousDate).orElse(null);
		return previous;
	}
	
	@Override
	@Scheduled(cron = "0 0 0 * * ?")
	public void create(){
		Date today= new Date();
        Date previousDate = GetPreviousDate.get(today);
		ClientEntity previous=clientRepository.findById(previousDate).orElse(null);

		ClientEntity clientEntity=new ClientEntity();
		clientEntity.setDate(today);
		clientEntity.setVisit((long)0);
		clientRepository.save(clientEntity);
	}
	@Override
	public void updateVisit() {
		ClientEntity clientEntity=getToday();
		clientEntity.setVisit(clientEntity.getVisit()+1);
		clientRepository.save(clientEntity);
	}

	@Override	
	public List<String []> getClientResponse() {
		ClientEntity clientEntity=getToday();
		ClientEntity previous=getPrevious();
		List<?> a=ClassToList.getFieldValues(clientEntity);
		a.remove(0);
		ClassToList.getFieldValues(clientEntity);
		String [] titles= {"Tổng","Lượt ghé thăm"};
		String [] units= {"Người","Lượt"};
		String [] data=a.toArray(new String [0]);
		long previousVisit=0;
		if(previous!=null) {
			previousVisit=previous.getVisit();
		}
		String percentVisit=CalculateDistance.calculatePercentageDifference(previousVisit, clientEntity.getVisit()).replace("{unit}","Lượt");
		String [] percent={"-0",percentVisit};
		List<String []> l=new ArrayList<>();
		l.add(titles);
		l.add(data);
		l.add(units);
		l.add(percent);
		return l;
		
	}
}
