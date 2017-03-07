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

import com.fmo.wom.business.util.invoice.InvoiceSearchValues;


/**
 * @author SI279688
 * 
 */
public class FMInvoiceExcelSheetBuilder extends AbstractExcelView
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

		final List<InvoiceSearchValues> invoiceSearchValuesList = (List<InvoiceSearchValues>) model.get("invoiceSearchValuesList");
		final HSSFSheet sheet = workbook.createSheet("Invoice List");
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

		header.createCell(0).setCellValue("Invoice/credit memo");
		header.getCell(0).setCellStyle(style);

		header.createCell(1).setCellValue("Invoice#");
		header.getCell(1).setCellStyle(style);

		header.createCell(2).setCellValue("Invoice to");
		header.getCell(2).setCellStyle(style);

		header.createCell(3).setCellValue("Deliver to");
		header.getCell(3).setCellStyle(style);

		header.createCell(4).setCellValue("Invoice Date");
		header.getCell(4).setCellStyle(style);

		header.createCell(5).setCellValue("Invoice Amount");
		header.getCell(5).setCellStyle(style);

		header.createCell(6).setCellValue("PO#");
		header.getCell(6).setCellStyle(style);

		// create data rows
		int rowCount = 1;

		for (final InvoiceSearchValues invoice : invoiceSearchValuesList)
		{
			final HSSFRow aRow = sheet.createRow(rowCount++);
			aRow.createCell(0).setCellValue(invoice.getInvoiceType().getDescription());
			aRow.createCell(1).setCellValue(invoice.getInvoiceNumber());
			aRow.createCell(2).setCellValue(invoice.getBillToAccount().getAccountCode());
			aRow.createCell(3).setCellValue(invoice.getShipToAccount().getAccountCode());
			aRow.createCell(4).setCellValue(convertDate(invoice.getInvoiceDate()));
			aRow.createCell(5).setCellValue(invoice.getInvoiceAmount().getDisplayPrice());
			aRow.createCell(6).setCellValue(invoice.getCustomerPurchaseOrderNumber());


		}


	}

	public final String convertDate(final Date date)
	{


		final DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		final String s = df.format(date);
		return s;
	}

}
