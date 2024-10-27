package com.ecommerce.sopi.globalvar;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class ClassToList {
	// Lấy giá trị của tất cả các field trong 1 class đưa vào 1 mảng
	public static List<String> getFieldValues(Object object) {
	    Class<?> clazz = object.getClass();
	    Field[] fields = clazz.getDeclaredFields();
	    List<String> values = new ArrayList<>();
	    
	    for (Field field : fields) {
	        try {
	            field.setAccessible(true);  // Make private fields accessible
	            Object fieldValue = field.get(object);  // Get the field's value
	            if (fieldValue != null) {
	                values.add(fieldValue.toString());  // Add to the list if not null
	            } else {
	                values.add("null");  // Handle null values explicitly
	            }
	        } catch (IllegalArgumentException | IllegalAccessException e) {
	            e.printStackTrace();  // Handle exceptions
	        }
	    }
	    
	    return values;
	}
}