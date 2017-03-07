/**
 * 
 */
package com.federalmogul.storefront.export;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;


import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.fmo.wom.business.util.invoice.InvoiceSearchValues;


/**
 * @author SI279688
 * 
 */
public class FMInvoicePdfBuilder extends AbstractPdfView
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.web.servlet.view.document.AbstractPdfView#buildPdfDocument(java.util.Map,
	 * com.lowagie.text.Document, com.lowagie.text.pdf.PdfWriter, javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void buildPdfDocument(final Map<String, Object> model, final com.lowagie.text.Document doc,
			final PdfWriter pdfwriter, final HttpServletRequest request, final HttpServletResponse response) throws Exception
	{
		response.setContentType("application/pdf");

		final List<InvoiceSearchValues> invoiceSearchValuesList = (List<InvoiceSearchValues>) model.get("invoiceSearchValuesList");
		final PdfPTable table = new PdfPTable(7);
		table.setWidthPercentage(100.0f);
		table.setWidths(new float[]
		{ 1.0f, 1.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f });
		table.setSpacingBefore(10);

		// define font for table header row
		final com.lowagie.text.Font font = FontFactory.getFont(FontFactory.HELVETICA);
		//font.setColor(BaseColor.WHITE);

		// define table header cell
		final PdfPCell cell = new PdfPCell();
		//cell.setBackgroundColor(BaseColor.BLUE);
		cell.setPadding(5);
		cell.setPhrase(new Phrase("Invoice/credit memo", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Invoice#", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Invoice to", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Deliver to", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Invoice Date", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Invoice Amount", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("PO#", font));
		table.addCell(cell);

		//Phrase datePhrase = new Phrase(new Chunk("Created Date", font));
		//datePhrase.add(new Phrase(new Chunk("Invoice Date", font)));
		//PdfPCell celldate = new PdfPCell(datePhrase);

		for (final InvoiceSearchValues invoice : invoiceSearchValuesList)
		{
			if (invoice.getInvoiceType() != null)
			{
				table.addCell(invoice.getInvoiceType().getDescription());
			}
			else
			{
				table.addCell(" ");
			}

			if (invoice.getInvoiceNumber() != null)
			{
				table.addCell(invoice.getInvoiceNumber());
			}
			else
			{
				table.addCell(" ");
			}

			if (invoice.getBillToAccount() != null)
			{
				table.addCell(invoice.getBillToAccount().getAccountCode());
			}
			else
			{
				table.addCell(" ");
			}
			if (invoice.getShipToAccount() != null)
			{
				table.addCell(invoice.getShipToAccount().getAccountCode());
			}
			else
			{
				table.addCell(" ");
			}
			if (invoice.getInvoiceDate() != null)
			{
				table.addCell(convertDate(invoice.getInvoiceDate()));
			}
			else
			{
				table.addCell(" ");
			}
			if (invoice.getInvoiceAmount() != null)
			{
				table.addCell(String.valueOf(invoice.getInvoiceAmount().getDisplayPrice()));
			}
			else
			{
				table.addCell(" ");
			}
			if (invoice.getCustomerPurchaseOrderNumber() != null)
			{
				table.addCell(invoice.getCustomerPurchaseOrderNumber());
			}
			else
			{
				table.addCell(" ");
			}
		}

		doc.add(table);

	}
	
	public final String convertDate(final Date date)
	{


		final DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		final String s = df.format(date);
		return s;
	}


}
