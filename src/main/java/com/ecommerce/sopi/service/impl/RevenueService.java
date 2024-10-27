package com.ecommerce.sopi.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ecommerce.sopi.DTO.ADMIN.response.RevenueResponse;
import com.ecommerce.sopi.entity.ClientEntity;
import com.ecommerce.sopi.entity.RevenueEntity;
import com.ecommerce.sopi.globalvar.CalculateDistance;
import com.ecommerce.sopi.globalvar.ClassToList;
import com.ecommerce.sopi.globalvar.GetPreviousDate;
import com.ecommerce.sopi.repository.RevenueRepository;
import com.ecommerce.sopi.service.RevenueServiceInterface;

@Service
public class RevenueService implements RevenueServiceInterface {
	@Autowired
	private RevenueRepository revenueRepository;
	
	@Override
	public RevenueEntity getToday() {
		
		if(revenueRepository.findById(new Date()).orElse(null)==null) {
			create();
		}
		return revenueRepository.findById(new Date()).orElse(null);
	}
	@Override
	public RevenueEntity getPrevious() {
		Date today= new Date();
		Date previousDate = GetPreviousDate.get(today);
		return revenueRepository.findById(previousDate).orElse(null);
	}

	@Override
	@Scheduled(cron = "0 0 0 * * ?")
	public void create() {
		Date today= new Date();
        Date previousDate = GetPreviousDate.get(today);
        RevenueEntity previous=revenueRepository.findById(previousDate).orElse(null);
        RevenueEntity revenueEntity=new RevenueEntity();
        long total=0;
        long sales=0;
        if(previous!=null) {
        	sales=previous.getSales();
        	total=previous.getTotal();
        }
        revenueEntity.setDate(today);
        revenueEntity.setTodayTotal((long)0);
        revenueEntity.setTodaySales((long)0);
        revenueEntity.setSales(sales);
        revenueEntity.setTotal(total);
        revenueRepository.save(revenueEntity);
		
	}

	@Override
	public void update(Long total,Long sales) {
		// TODO Auto-generated method stub
		RevenueEntity revenueEntity=getToday();
		revenueEntity.setSales(sales+revenueEntity.getSales());
		revenueEntity.setTodayTotal(total+revenueEntity.getTotal());
		revenueEntity.setTodaySales(sales+revenueEntity.getTodaySales());
		revenueEntity.setTotal(total+revenueEntity.getTodayTotal());
		revenueRepository.save(revenueEntity);
	}

	@Override
	public List<String[]> getRevenueResponse() {
		RevenueEntity revenueEntity=getToday();
		RevenueEntity previous=getPrevious();
		List<?> a=ClassToList.getFieldValues(revenueEntity);
		a.remove(0);
		String [] titles= {"Tổng doanh thu","Doanh thu hôm nay","Tổng doanh số","Doanh số hôm nay"};
		String [] units= {"VND","VND","Sản phẩm","Sản phẩm"};
		String [] data=a.toArray(new String [0]);
		
		long previousTotal=0;
		long previousSales=0;
		if(previous!=null) {
			previousTotal=previous.getTodayTotal();
			previousSales=previous.getTodaySales();
		}
		String percentageTotal=CalculateDistance.calculatePercentageDifference(previousTotal, revenueEntity.getTodayTotal()).replace("{unit}","VND");
		String percentageSales=CalculateDistance.calculatePercentageDifference(previousSales, revenueEntity.getTodaySales()).replace("{unit}","Sản phẩm");
		String [] percent={"-0",percentageTotal,"-0",percentageSales};
		List<String []> l=new ArrayList<>();
		l.add(titles);
		l.add(data);
		l.add(units);
		l.add(percent);
		return l;
	}


}
