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

import com.fmo.wom.business.orderstatus.OrderStatusResult;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;


/**
 * @author SI279688
 * 
 */
public class FMOrderStatusPdfBuilder extends AbstractPdfView
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

		final List<OrderStatusResult> orderStatusResult = (List<OrderStatusResult>) model.get("orderStatusResult");
		final PdfPTable table = new PdfPTable(10);
		table.setWidthPercentage(100.0f);
		table.setWidths(new float[]
		{ 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f });
		table.setSpacingBefore(10);

		// define font for table header row
		final com.lowagie.text.Font font = FontFactory.getFont(FontFactory.HELVETICA);
		//font.setColor(BaseColor.WHITE);

		// define table header cell
		final PdfPCell cell = new PdfPCell();
		//cell.setBackgroundColor(BaseColor.BLUE);
		cell.setPadding(5);
		cell.setPhrase(new Phrase("PO #", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Confirmation #", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Order #", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Packing Slip #", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Shipping DC ", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Ship Date", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Tracking #", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase("Order Date: ", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase(" Status: ", font));
		table.addCell(cell);

		cell.setPhrase(new Phrase(" Source:", font));
		table.addCell(cell);




		//Phrase datePhrase = new Phrase(new Chunk("Created Date", font));
		//datePhrase.add(new Phrase(new Chunk("Invoice Date", font)));
		//PdfPCell celldate = new PdfPCell(datePhrase);

		for (final OrderStatusResult order : orderStatusResult)
		{
			if (order.getPurchaseOrderNumber() != null)
			{
				table.addCell(order.getPurchaseOrderNumber());
			}
			else
			{
				table.addCell(" ");
			}

			if (order.getShippedOrder() != null && order.getShippedOrder().getShippedOrderBO() != null)
			{
				table.addCell(order.getShippedOrder().getShippedOrderBO().getMasterOrderNumber());
			}
			else
			{
				table.addCell(" ");
			}

			if (order.getOrderNumber() != null)
			{
				table.addCell(order.getOrderNumber());
			}
			else
			{
				table.addCell(" ");
			}
			if (order.getPackingSlipNumber() != null)
			{
				table.addCell(order.getPackingSlipNumber());
			}
			else
			{
				table.addCell(" ");
			}
			if (order.getOrderShippingUnit() != null && order.getOrderShippingUnit().getShippingUnitBO() != null
					&& order.getOrderShippingUnit().getShippingUnitBO().getShipFrom() != null)
			{
				table.addCell(order.getOrderShippingUnit().getShippingUnitBO().getShipFrom().getName());
			}
			else
			{
				table.addCell(" ");
			}
			if (order.getShipDate() != null)
			{
				table.addCell(convertDate(order.getShipDate()));
			}
			else
			{
				table.addCell(" ");
			}
			if (order.getOrderShippingUnit() != null)
			{
				table.addCell(order.getOrderShippingUnit().getTrackingNumber());
			}
			else
			{
				table.addCell(" ");
			}
			if (order.getOrderDate() != null)
			{
				table.addCell(convertDate(order.getOrderDate()));
			}
			else
			{
				table.addCell(" ");
			}
			if (order.getStatus() != null)
			{
				table.addCell(order.getStatus());
			}
			else
			{
				table.addCell(" ");
			}
			if (order.getOrderShippingUnit() != null)
			{
				table.addCell(order.getOrderShippingUnit().getOrderSourceKey());
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
