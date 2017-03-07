/**
 * 
 */
package com.federalmogul.storefront.controllers.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;

import com.fmo.wom.business.util.backorder.BackOrderSearchValues;
import com.fmo.wom.domain.OrderUnitBO;
import com.fmo.wom.domain.ShippedItemBO;
import com.fmo.wom.domain.ShippedOrderBO;
import com.fmo.wom.domain.ShippingUnitBO;


/**
 * @author ryadav93
 * 
 */
public class MailUtil
{
	private static final String NEWLINE_CHARACTER = "\n";
	private static final String BULLET_UNICODE_VALUE = "\u2022";

	public HSSFWorkbook getBackOrderWorkbookForEmailAttachment(final List<BackOrderSearchValues> backOrderSearchValuesList)
	{
		final HSSFWorkbook workbook = new HSSFWorkbook();
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
		return workbook;
	}



	public HSSFWorkbook getOrderHistoryCSVforEmail(final ShippedOrderBO shippedOrderDetailsBO)
	{
		final HSSFWorkbook workbook = new HSSFWorkbook();

		final HSSFSheet sheet = workbook.createSheet("Order List");
		sheet.setDefaultColumnWidth(15);
		final HSSFFont font = workbook.createFont();
		font.setFontName("Arial");

		// create header row
		final HSSFRow header = sheet.createRow(0);
		final HSSFRow header1 = sheet.createRow(1);
		final HSSFRow header2 = sheet.createRow(2);
		final HSSFRow header3 = sheet.createRow(3);
		final HSSFRow header4 = sheet.createRow(4);
		final HSSFRow header5 = sheet.createRow(5);

		final HSSFRow header6 = sheet.createRow(6);
		final HSSFRow header7 = sheet.createRow(7);
		final HSSFRow header8 = sheet.createRow(8);
		final HSSFRow header9 = sheet.createRow(9);
		final HSSFRow header10 = sheet.createRow(10);
		final HSSFRow header11 = sheet.createRow(11);
		final HSSFRow header12 = sheet.createRow(12);
		final HSSFRow header13 = sheet.createRow(13);
		final HSSFRow header14 = sheet.createRow(14);
		final HSSFRow header15 = sheet.createRow(15);
		final HSSFRow header16 = sheet.createRow(16);
		final HSSFRow header17 = sheet.createRow(17);
		final HSSFRow header18 = sheet.createRow(18);
		final HSSFRow header19 = sheet.createRow(19);

		header.createCell(0).setCellValue("Packing Slip #");
		header1.createCell(0).setCellValue("Shipping DC");
		header2.createCell(0).setCellValue("Ship Date");
		header3.createCell(0).setCellValue("Carrier");
		header4.createCell(0).setCellValue("Tracking #");
		header5.createCell(0).setCellValue("Total # Lines");
		header6.createCell(0).setCellValue("# Lines Shipped");
		header7.createCell(0).setCellValue("Total Pieces");
		header8.createCell(0).setCellValue("# Pieces Shipped");
		header9.createCell(0).setCellValue("Order #");
		header10.createCell(0).setCellValue("Purchase Order #");
		header11.createCell(0).setCellValue("Order Type");
		header12.createCell(0).setCellValue("Order Date");
		header13.createCell(0).setCellValue("Order Source");
		header14.createCell(0).setCellValue("Order Status");
		header15.createCell(0).setCellValue("Ordered By");
		header16.createCell(0).setCellValue("Order Cancelled Date");
		header17.createCell(0).setCellValue("Required Date");
		header18.createCell(0).setCellValue("Comments");
		header19.createCell(0).setCellValue("Line #");
		header19.createCell(1).setCellValue("Part #");
		header19.createCell(2).setCellValue("Description");
		header19.createCell(3).setCellValue("Packing Slip");
		header19.createCell(4).setCellValue("Ordered");
		header19.createCell(5).setCellValue("Assigned");
		header19.createCell(6).setCellValue("Shipped");
		header19.createCell(7).setCellValue("Backordered");

		if (shippedOrderDetailsBO != null)
		{


			for (final OrderUnitBO orderUnitBO : shippedOrderDetailsBO.getOrderUnitList())
			{
				for (final ShippingUnitBO shippingUnitBO : orderUnitBO.getShippingUnitList())
				{

					if (shippingUnitBO.getPackingSlip() != null)
					{
						header.createCell(1).setCellValue(shippingUnitBO.getPackingSlip());
					}
					else
					{
						header.createCell(1).setCellValue("");
					}
					if (shippingUnitBO.getShipFrom().getName() != null)
					{
						header1.createCell(1).setCellValue(shippingUnitBO.getShipFrom().getName());
					}
					else
					{
						header1.createCell(1).setCellValue("");
					}
					if (shippingUnitBO.getShipDate() != null)
					{
						header2.createCell(1).setCellValue(convertDate(shippingUnitBO.getShipDate()));
					}
					else
					{
						header2.createCell(1).setCellValue("");
					}
					if (shippingUnitBO.getCarrierCode() != null)
					{
						header3.createCell(1).setCellValue(shippingUnitBO.getCarrierCode());
					}
					else
					{
						header3.createCell(1).setCellValue("");
					}
					if (shippingUnitBO.getTrackingNumber() != null)
					{
						header4.createCell(1).setCellValue(shippingUnitBO.getTrackingNumber());
					}
					else
					{
						header4.createCell(1).setCellValue("");
					}
					if (shippingUnitBO.getTotalLines() != 0)
					{
						header5.createCell(1).setCellValue(shippingUnitBO.getTotalLines());
					}
					else
					{
						header5.createCell(1).setCellValue(0);
					}
					if (shippingUnitBO.getLinesShipped() != 0)
					{
						header6.createCell(1).setCellValue(shippingUnitBO.getLinesShipped());
					}
					else
					{
						header6.createCell(1).setCellValue(0);
					}
					if (shippingUnitBO.getTotalPieces() != 0)
					{
						header7.createCell(1).setCellValue(shippingUnitBO.getTotalPieces());
					}
					else
					{
						header7.createCell(1).setCellValue(0);
					}
					if (shippingUnitBO.getTotalPiecesShipped() != 0)
					{
						header8.createCell(1).setCellValue(shippingUnitBO.getTotalPiecesShipped());
					}
					else
					{
						header8.createCell(1).setCellValue(0);
					}
					if (orderUnitBO.getOrderNumber() != null)
					{
						header9.createCell(1).setCellValue(orderUnitBO.getOrderNumber());
					}
					else
					{
						header9.createCell(1).setCellValue("");
					}

					if (shippedOrderDetailsBO.getCustomerPurchaseOrderNum() != null)
					{
						header10.createCell(1).setCellValue(shippedOrderDetailsBO.getCustomerPurchaseOrderNum());
					}
					else
					{
						header10.createCell(1).setCellValue("");
					}
					if (shippedOrderDetailsBO.getOrderType() != null)
					{
						header11.createCell(1).setCellValue(shippedOrderDetailsBO.getOrderType().name());
					}
					else
					{
						header11.createCell(1).setCellValue("");
					}

					if (orderUnitBO.getOriginalOrderDate() != null)
					{
						header12.createCell(1).setCellValue(convertDate(orderUnitBO.getOriginalOrderDate()));
					}
					else
					{
						header12.createCell(1).setCellValue("");
					}
					if (orderUnitBO.getOrderSourceKey() != null)
					{
						header13.createCell(1).setCellValue(orderUnitBO.getOrderSourceKey());
					}
					else
					{
						header13.createCell(1).setCellValue("");
					}
					//Order Status
					if (shippingUnitBO.getPackingStatus() != null)
					{
						header14.createCell(1).setCellValue(shippingUnitBO.getPackingStatus());
					}
					else
					{
						header14.createCell(1).setCellValue("");
					}
					if (shippedOrderDetailsBO.getOrderedBy() != null)
					{
						header15.createCell(1).setCellValue(shippedOrderDetailsBO.getOrderedBy());
					}
					else
					{
						header15.createCell(1).setCellValue("");
					}
					if (orderUnitBO.getCancelledDate() != null)
					{
						header16.createCell(1).setCellValue(convertDate(orderUnitBO.getCancelledDate()));
					}
					else
					{
						header16.createCell(1).setCellValue("");
					}
					//required date
					if (orderUnitBO.getRequestedDeliveryDate() != null)
					{
						header17.createCell(1).setCellValue(convertDate(orderUnitBO.getRequestedDeliveryDate()));
					}
					else
					{
						header17.createCell(1).setCellValue("");
					}

					//comments
					if (orderUnitBO.getCommentsList() != null)
					{
						header18.createCell(1).setCellValue(getBulletedCommentsListAsString(orderUnitBO));
					}
					else
					{
						header18.createCell(1).setCellValue("");
					}
					break;
				}
				break;
			}
			int i = 20;
			for (final OrderUnitBO orderUnitBO : shippedOrderDetailsBO.getOrderUnitList())
			{
				for (final ShippingUnitBO shippingUnitBO : orderUnitBO.getShippingUnitList())
				{
					for (final ShippedItemBO shippedItemBO : shippingUnitBO.getShippedItemsList())
					{

						final HSSFRow header27 = sheet.createRow(i);
						if (shippedItemBO.getLineNumber() != 0)
						{
							header27.createCell(0).setCellValue(shippedItemBO.getLineNumber());
						}
						else
						{
							header27.createCell(0).setCellValue("");
						}
						if (shippedItemBO.getDisplayPartNumber() != null)
						{
							header27.createCell(1).setCellValue(shippedItemBO.getDisplayPartNumber());
						}
						else
						{
							header27.createCell(1).setCellValue("");
						}
						if (shippedItemBO.getDescription() != null)
						{
							header27.createCell(2).setCellValue(shippedItemBO.getDescription());
						}
						else
						{
							header27.createCell(2).setCellValue("");
						}
						if (shippingUnitBO.getPackingSlip() != null)
						{
							header27.createCell(3).setCellValue(shippingUnitBO.getPackingSlip());
						}
						else
						{
							header27.createCell(3).setCellValue("");
						}

						if (shippedItemBO.getOrderedQuantity() != 0)
						{
							header27.createCell(4).setCellValue(shippedItemBO.getOrderedQuantity());
						}
						else
						{
							header27.createCell(4).setCellValue(0);
						}
						if (shippedItemBO.getAssignedQuantity() != 0)
						{
							header27.createCell(5).setCellValue(shippedItemBO.getAssignedQuantity());
						}
						else
						{
							header27.createCell(5).setCellValue(0);
						}
						if (shippedItemBO.getShippedQuantity() != 0)
						{
							header27.createCell(6).setCellValue(shippedItemBO.getShippedQuantity());
						}
						else
						{
							header27.createCell(6).setCellValue(0);
						}
						if (shippedItemBO.getBackorderedQuantity() != 0)
						{
							header27.createCell(7).setCellValue(shippedItemBO.getBackorderedQuantity());
						}
						else
						{
							header27.createCell(7).setCellValue(0);
						}


						i++;

					}

				}
			}

		}
		return workbook;
	}

	public final String convertDate(final Date date)
	{


		final DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		final String s = df.format(date);
		return s;
	}

	private String getBulletedCommentsListAsString(final OrderUnitBO orderUnit)
	{

		String bulletedCommentsStr = "";
		final List<String> commentsList = orderUnit.getCommentsList();
		for (final String comments : commentsList)
		{
			bulletedCommentsStr += ("".equals(bulletedCommentsStr) ? "" : NEWLINE_CHARACTER) + BULLET_UNICODE_VALUE + "   "
					+ comments;
		}

		return bulletedCommentsStr;
	}

}
