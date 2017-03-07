/**
 * 
 */
package com.federalmogul.storefront.export;

import de.hybris.platform.commercefacades.user.data.CustomerData;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.web.servlet.view.document.AbstractExcelView;



/**
 * @author SI279688
 * 
 */
public class FMExcelSheetBuilder extends AbstractExcelView
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.view.document.AbstractExcelView#buildExcelDocument(java.util.Map,
	 * org.apache.poi.hssf.usermodel.HSSFWorkbook, javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void buildExcelDocument(final Map<String, Object> model, final HSSFWorkbook workbook,
			final HttpServletRequest request, final HttpServletResponse response) throws Exception
	{

		response.setContentType("application/vnd.ms-excel");
		final List<CustomerData> listCustomers = (List<CustomerData>) model.get("customersList");
		final HSSFSheet sheet = workbook.createSheet("Users List");
		sheet.setDefaultColumnWidth(30);
		// create style for header cells
		final CellStyle style = workbook.createCellStyle();
		final HSSFFont font = workbook.createFont();
		font.setFontName("Arial");
		style.setFillForegroundColor(HSSFColor.BLUE.index);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setColor(HSSFColor.WHITE.index);
		style.setFont(font);

		// create header row
		final HSSFRow header = sheet.createRow(0);

		header.createCell(0).setCellValue("userID");
		header.getCell(0).setCellStyle(style);

		header.createCell(1).setCellValue("First Name");
		header.getCell(1).setCellStyle(style);

		header.createCell(2).setCellValue("Last Name");
		header.getCell(2).setCellStyle(style);

		header.createCell(3).setCellValue("phone no");
		header.getCell(3).setCellStyle(style);

		header.createCell(4).setCellValue("Account code");
		header.getCell(4).setCellStyle(style);

		// create data rows
		int rowCount = 1;

		for (final CustomerData customer : listCustomers)
		{
			final HSSFRow aRow = sheet.createRow(rowCount++);
			aRow.createCell(0).setCellValue(customer.getUid());
			aRow.createCell(1).setCellValue(customer.getFirstName());
			aRow.createCell(2).setCellValue(customer.getLastName());
			if (customer.getDefaultShippingAddress() != null)
			{
				aRow.createCell(3).setCellValue(customer.getDefaultShippingAddress().getPhone());
			}
			if (customer.getUnit() != null)
			{
				aRow.createCell(4).setCellValue(customer.getUnit().getUid());
			}

		}

	}
}
