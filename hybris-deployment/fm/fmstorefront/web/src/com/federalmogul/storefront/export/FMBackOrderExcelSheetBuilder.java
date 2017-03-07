/**
 * 
 */
package com.federalmogul.storefront.export;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.fmo.wom.business.util.backorder.BackOrderSearchValues;



/**
 * @author SI279688
 * 
 */
public class FMBackOrderExcelSheetBuilder extends AbstractExcelView
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
		final List<BackOrderSearchValues> backOrderSearchValuesList = (List<BackOrderSearchValues>) model
				.get("backOrderSearchValuesList");
		final HSSFSheet sheet = workbook.createSheet("Back Order");
		sheet.setDefaultColumnWidth(20);
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

		header.createCell(0).setCellValue("Part Number");
		header.getCell(0).setCellStyle(style);

		header.createCell(1).setCellValue("Deliver To");
		header.getCell(1).setCellStyle(style);

		header.createCell(2).setCellValue("Order #");
		header.getCell(2).setCellStyle(style);

		header.createCell(3).setCellValue("PO #");
		header.getCell(3).setCellStyle(style);

		header.createCell(4).setCellValue("Shipping DC");
		header.getCell(4).setCellStyle(style);
		header.createCell(5).setCellValue("Order Date");
		header.getCell(5).setCellStyle(style);
		header.createCell(6).setCellValue("Original Quanity");
		header.getCell(6).setCellStyle(style);
		header.createCell(7).setCellValue("Back Order Quantity");
		header.getCell(7).setCellStyle(style);

		if (backOrderSearchValuesList != null)
		{

			int rowCount = 1;
			for (final BackOrderSearchValues backOrderSearchValues : backOrderSearchValuesList)
			{
				final HSSFRow aRow = sheet.createRow(rowCount++);
				if (backOrderSearchValues.getPartNumber() != null)
				{
					aRow.createCell(0).setCellValue(backOrderSearchValues.getPartNumber());
				}
				else
				{
					aRow.createCell(0).setCellValue("");
				}

				if (backOrderSearchValues.getShipToAccount() != null)
				{
					if (backOrderSearchValues.getShipToAccount().getAccountCode() != null)
					{
						aRow.createCell(1).setCellValue(backOrderSearchValues.getShipToAccount().getAccountCode());

					}
					else
					{
						aRow.createCell(1).setCellValue("");
					}
				}
				else
				{
					aRow.createCell(1).setCellValue("");
				}

				if (backOrderSearchValues.getOrderNumber() != null)
				{
					aRow.createCell(2).setCellValue(backOrderSearchValues.getOrderNumber());
				}
				else
				{
					aRow.createCell(2).setCellValue("");
				}
				if (backOrderSearchValues.getCustomerPONumber() != null)
				{
					aRow.createCell(3).setCellValue(backOrderSearchValues.getCustomerPONumber());
				}
				else
				{
					aRow.createCell(3).setCellValue("");
				}
				if (backOrderSearchValues.getDistCntr() != null)
				{
					if (backOrderSearchValues.getDistCntr().getName() != null)
					{
						aRow.createCell(4).setCellValue(backOrderSearchValues.getDistCntr().getName());

					}
					else
					{
						aRow.createCell(4).setCellValue("");
					}

				}
				else
				{
					aRow.createCell(4).setCellValue("");
				}
				if (backOrderSearchValues.getOrderDate() != null)
				{
					aRow.createCell(5).setCellValue(convertDate(backOrderSearchValues.getOrderDate()));
				}
				else
				{
					aRow.createCell(5).setCellValue("");
				}
				if (backOrderSearchValues.getOriginalOrderQty() != 0)
				{
					aRow.createCell(6).setCellValue(backOrderSearchValues.getOriginalOrderQty());
				}
				else
				{
					aRow.createCell(6).setCellValue("0");
				}
				if (backOrderSearchValues.getBackOrderQty() != 0)
				{
					aRow.createCell(7).setCellValue(backOrderSearchValues.getBackOrderQty());
				}
				else
				{
					aRow.createCell(7).setCellValue("0");
				}
			}
		}

	}

	public final String convertDate(final Date date)
	{


		final DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		final String s = df.format(date);
		return s;
	}
}
