package com.ecommerce.sopi.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.sopi.DTO.request.AddressResquest;
import com.ecommerce.sopi.DTO.response.AddressResponse;
import com.ecommerce.sopi.DTO.response.UserResponse;
import com.ecommerce.sopi.entity.AddressEntity;
import com.ecommerce.sopi.entity.UserEntity;
import com.ecommerce.sopi.mapper.AddressMapper;
import com.ecommerce.sopi.repository.AddressRepository;
import com.ecommerce.sopi.service.AddressServiceInterface;
import com.fasterxml.jackson.databind.ser.std.StdKeySerializers.Default;

@Service
public class AddressService implements AddressServiceInterface {

	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AddressMapper addressMapper;
	
	@Override
	public AddressEntity getAddressEntityById(Long id) {
		AddressEntity addressEntity=addressRepository.findById(id).orElseThrow(null);
		return addressEntity;
	}
	
	
	@Override
	public List<AddressResponse> getAllAddressByUser() {
		
		List<AddressResponse> list=new LinkedList<AddressResponse>();
		AddressResponse addressResponse=getDefaultAddress();
		if(addressResponse!=null) {			
			list.add(addressResponse);
		}
		list.addAll(getNotDefaultAddress());	
		return list;
	}

	@Override
	public AddressResponse getDefaultAddress() {
		UserEntity userEntity=userService.getAthenticatedUser();
		AddressEntity addressEntity= addressRepository.findDefaultAddressByUser(userEntity);
	
		return  addressMapper.toAddressResponse(addressEntity) ;
	}

	@Override
	public List<AddressResponse> getNotDefaultAddress() {
		UserEntity userEntity=userService.getAthenticatedUser();
		List<AddressResponse> list=addressRepository.findAllNotDefaultAddressByUser(userEntity)
				.stream().map(x->addressMapper.toAddressResponse(x)).collect(Collectors.toList());
		return list;
	}

	@Override
	public AddressResponse getAddressById(Long id) {
		AddressEntity addressEntity=addressRepository.findById(id).orElseThrow(null);
		AddressResponse addressResponse=addressMapper.toAddressResponse(addressEntity);
		return addressResponse;
	}
	
	

	@Override
	@Transactional
	public void deleteAddressById(Long id) {
		AddressEntity addressEntity=addressRepository.findById(id).orElseThrow();
		addressRepository.delete(addressEntity);
	}

	@Override
	public void updateAddress(AddressResquest resquest,Long id) {
		AddressEntity addressEntity=addressRepository.findById(id).orElseThrow();
		addressMapper.toAddressEntity(resquest,addressEntity);
		addressRepository.save(addressEntity);
		
		
		
	}

	@Override
	public void createAddress(AddressResquest resquest) {
		AddressEntity addressEntity=new AddressEntity();
		addressMapper.toAddressEntity(resquest,addressEntity);
		if(getDefaultAddress()==null) {
			addressEntity.setIsdefault(Boolean.TRUE);
		}else {
			addressEntity.setIsdefault(Boolean.FALSE);
		}
		addressEntity.setUser(userService.getAthenticatedUser());
		addressRepository.save(addressEntity);
	}

	@Override
	public void setDefaultAddressById(Long id) {
		// TODO Auto-generated method stub
		UserEntity userEntity=userService.getAthenticatedUser();
		AddressEntity defaultAddress= addressRepository.findDefaultAddressByUser(userEntity);
		defaultAddress.setIsdefault(Boolean.FALSE);
		AddressEntity notDefaultAddress= addressRepository.findById(id).orElseThrow();
		notDefaultAddress.setIsdefault(Boolean.TRUE);
		addressRepository.save(defaultAddress);
		addressRepository.save(notDefaultAddress);
		
	}

}
