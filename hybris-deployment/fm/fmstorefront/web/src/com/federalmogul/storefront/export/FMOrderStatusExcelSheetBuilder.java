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

import com.fmo.wom.business.orderstatus.OrderStatusResult;


/**
 * @author SI279688
 * 
 */
public class FMOrderStatusExcelSheetBuilder extends AbstractExcelView
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

		final List<OrderStatusResult> orderStatusResult = (List<OrderStatusResult>) model.get("orderStatusResult");
		final HSSFSheet sheet = workbook.createSheet("Order Status List");
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

		header.createCell(0).setCellValue("PO #");
		header.getCell(0).setCellStyle(style);

		header.createCell(1).setCellValue("Confirmation #");
		header.getCell(1).setCellStyle(style);

		header.createCell(2).setCellValue("Order #");
		header.getCell(2).setCellStyle(style);

		header.createCell(3).setCellValue("Packing Slip #");
		header.getCell(3).setCellStyle(style);

		header.createCell(4).setCellValue("Shipping DC");
		header.getCell(4).setCellStyle(style);

		header.createCell(5).setCellValue("ShipDate");
		header.getCell(5).setCellStyle(style);

		header.createCell(6).setCellValue("Tracking #");
		header.getCell(6).setCellStyle(style);

		header.createCell(7).setCellValue("OrderDate:");
		header.getCell(7).setCellStyle(style);

		header.createCell(8).setCellValue("Status:");
		header.getCell(8).setCellStyle(style);

		header.createCell(9).setCellValue("Source:");
		header.getCell(9).setCellStyle(style);


		// create data rows
		int rowCount = 1;

		for (final OrderStatusResult order : orderStatusResult)
		{
			final HSSFRow aRow = sheet.createRow(rowCount++);
			aRow.createCell(0).setCellValue(order.getPurchaseOrderNumber());
			if (order.getShippedOrder() != null && order.getShippedOrder().getShippedOrderBO() != null)
			{
				aRow.createCell(1).setCellValue(order.getShippedOrder().getShippedOrderBO().getMasterOrderNumber());
			}
			aRow.createCell(2).setCellValue(order.getOrderNumber());
			aRow.createCell(3).setCellValue(order.getPackingSlipNumber());
			if (order.getOrderShippingUnit() != null && order.getOrderShippingUnit().getShippingUnitBO() != null
					&& order.getOrderShippingUnit().getShippingUnitBO().getShipFrom() != null)
			{
				aRow.createCell(4).setCellValue(order.getOrderShippingUnit().getShippingUnitBO().getShipFrom().getName());
			}
			if (order.getShipDate() != null)
			{
				aRow.createCell(5).setCellValue(convertDate(order.getShipDate()));
			}
			if (order.getOrderShippingUnit() != null)
			{
				aRow.createCell(6).setCellValue(order.getOrderShippingUnit().getTrackingNumber());
			}
			if (order.getOrderDate() != null)
			{
				aRow.createCell(7).setCellValue(convertDate(order.getOrderDate()));
			}
			aRow.createCell(8).setCellValue(order.getStatus());
			if (order.getOrderShippingUnit() != null)
			{
				aRow.createCell(9).setCellValue(order.getOrderShippingUnit().getOrderSourceKey());
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
