/**
 * 
 */
package com.federalmogul.storefront.export;

import de.hybris.platform.commercefacades.user.data.CustomerData;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.FontFactory;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;


/**
 * @author SI279688
 * 
 */
public class FMPdfBuilder extends AbstractPdfView
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

		final List<CustomerData> listCustomers = (List<CustomerData>) model.get("customersList");
		final PdfPTable table = new PdfPTable(5);
		table.setWidthPercentage(100.0f);
		table.setWidths(new float[]
		{ 3.0f, 2.0f, 2.0f, 2.0f, 1.0f });
		table.setSpacingBefore(10);

		// define font for table header row
		final com.lowagie.text.Font font = FontFactory.getFont(FontFactory.HELVETICA);
		//font.setColor(BaseColor.WHITE);

		// define table header cell
		final PdfPCell cell = new PdfPCell();
		//cell.setBackgroundColor(BaseColor.BLUE);
		cell.setPadding(5);
		cell.setPhrase(new Phrase("userID", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("First Name", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Last Name", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("phone no", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Account code", font));
		table.addCell(cell);

		for (final CustomerData customer : listCustomers)
		{
			table.addCell(customer.getUid());
			table.addCell(customer.getFirstName());
			table.addCell(customer.getLastName());
			if (customer.getDefaultShippingAddress() != null)
			{
				table.addCell(customer.getDefaultShippingAddress().getPhone());
			}
			else
			{
				table.addCell(" ");
			}
			if (customer.getUnit() != null)
			{
				table.addCell(customer.getUnit().getUid());
			}
			else
			{
				table.addCell(" ");
			}
		}

		doc.add(table);

	}


}
