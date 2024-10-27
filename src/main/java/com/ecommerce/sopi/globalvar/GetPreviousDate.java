package com.ecommerce.sopi.globalvar;

import java.util.Calendar;
import java.util.Date;

public class GetPreviousDate {
	// Lấy ra ngày hôm qua, dùng để thống kê trong trang admin
	public static Date get(Date today) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.DATE, -1); 
        Date previousDate = calendar.getTime();
        return previousDate;
	}
}
