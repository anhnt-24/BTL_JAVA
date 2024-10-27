package com.ecommerce.sopi.globalvar;

public class CalculateDistance {
	 //	So sánh tăng giảm dữ liệu từ ngày hôm nay với ngày hôm trước
	 public static String calculatePercentageDifference(long original, long newNumber) {
	        if (original == 0) {
	            return TextUtils.formatCurrency2(newNumber - original) +" "+"{unit}";
	        }
	        long difference = newNumber - original;
	        double percentage = Math.ceil((double) difference / original * 100);
	        return  percentage + "%";
	    }
}
