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

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.fmo.wom.business.util.backorder.BackOrderSearchValues;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.Phrase;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;


/**
 * @author SI279688
 * 
 */
public class FMBackOrderPdfBuilder extends AbstractPdfView
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.view.document.AbstractPdfView#buildPdfDocument(java.util.Map,
	 * com.lowagie.text.Document, com.lowagie.text.pdf.PdfWriter, javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void buildPdfDocument(Map<String, Object> model, com.lowagie.text.Document doc, PdfWriter arg2, HttpServletRequest arg3,
			HttpServletResponse response) throws Exception
	{
		response.setContentType("application/pdf");

		final List<BackOrderSearchValues> backOrderSearchValuesList = (List<BackOrderSearchValues>) model
				.get("backOrderSearchValuesList");
		final PdfPTable table = new PdfPTable(8);
		table.setWidthPercentage(100.0f);
		table.setWidths(new float[]
		{ 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f,2.0f });
		table.setSpacingBefore(10);

		// define font for table header row
		final com.lowagie.text.Font font = FontFactory.getFont(FontFactory.HELVETICA);
		//font.setColor(BaseColor.WHITE);

		// define table header cell
		final PdfPCell cell = new PdfPCell();
		//cell.setBackgroundColor(BaseColor.BLUE);
		cell.setPadding(5);
		cell.setPhrase(new Phrase("Part Number", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Deliver To", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Order #", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("PO #", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Shipping DC", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Order Date", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Original Quanity", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Back Order Quantity", font));
		table.addCell(cell);

		//Phrase datePhrase = new Phrase(new Chunk("Created Date", font));
		//datePhrase.add(new Phrase(new Chunk("Invoice Date", font)));
		//PdfPCell celldate = new PdfPCell(datePhrase);
		if (backOrderSearchValuesList != null)
		{


			for (final BackOrderSearchValues backOrderSearchValues : backOrderSearchValuesList)
			{
				if (backOrderSearchValues.getPartNumber() != null)
				{
					table.addCell(backOrderSearchValues.getPartNumber());
				}
				else
				{
					table.addCell(" ");
				}

				if (backOrderSearchValues.getShipToAccount() != null)
				{
					if (backOrderSearchValues.getShipToAccount().getAccountCode() != null)
					{
						table.addCell(backOrderSearchValues.getShipToAccount().getAccountCode());
					}
					else
					{
						table.addCell(" ");
					}
				}
				else
				{
					table.addCell(" ");
				}

				if (backOrderSearchValues.getOrderNumber() != null)
				{
					table.addCell(backOrderSearchValues.getOrderNumber());
				}
				else
				{
					table.addCell(" ");
				}
				if (backOrderSearchValues.getCustomerPONumber() != null)
				{
					table.addCell(backOrderSearchValues.getCustomerPONumber());
				}
				else
				{
					table.addCell(" ");
				}
				if (backOrderSearchValues.getDistCntr() != null)
				{
					if (backOrderSearchValues.getDistCntr().getName() != null)
					{
						table.addCell(backOrderSearchValues.getDistCntr().getName());

					}
					else
					{
						table.addCell(" ");
					}

				}
				else
				{
					table.addCell(" ");
				}
				if (backOrderSearchValues.getOrderDate() != null)
				{
					table.addCell(convertDate(backOrderSearchValues.getOrderDate()));
				}
				else
				{
					table.addCell(" ");
				}
				if (backOrderSearchValues.getOriginalOrderQty() != 0)
				{
					table.addCell(""+backOrderSearchValues.getOriginalOrderQty());
				}
				else
				{
					table.addCell("0");
				}
				if (backOrderSearchValues.getBackOrderQty() != 0)
				{
					table.addCell(""+backOrderSearchValues.getBackOrderQty());
				}
				else
				{
					table.addCell("0");
				}
			}
		}
		doc.setPageSize(PageSize.A4.rotate());
		doc.open();
		doc.newPage();
		doc.setMargins(50, 50, 65, 50);

		doc.add(table);

	}

	public final String convertDate(final Date date)
	{


		final DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		final String s = df.format(date);
		return s;
	}


}
