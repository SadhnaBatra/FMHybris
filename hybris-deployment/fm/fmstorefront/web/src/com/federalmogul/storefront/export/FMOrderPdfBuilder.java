/**
 * 
 */
package com.federalmogul.storefront.export;

import java.awt.Color;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.fmo.wom.domain.OrderUnitBO;
import com.fmo.wom.domain.ShippedItemBO;
import com.fmo.wom.domain.ShippedOrderBO;
import com.fmo.wom.domain.ShippingUnitBO;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;



/**
 * @author ryadav93
 * 
 */
public class FMOrderPdfBuilder extends AbstractPdfView
{
	private static final Logger LOG = Logger.getLogger(FMOrderPdfBuilder.class);

	private static final String TOTALS_STRING_DELIMITER = "  |  ";
	private static final Color COLOR_DARK_BLUE = new Color(9, 10, 154);
	private static final Color COLOR_SKY_BLUE = new Color(204, 221, 255);
	private static final Color COLOR_WHITE = new Color(255, 255, 255);
	private static final Color COLOR_BLACK = new Color(0, 0, 0);
	private static final String NEWLINE_CHARACTER = "\n";
	private static final String BULLET_UNICODE_VALUE = "\u2022";

	private static final Font SECTION_HDR_FONT = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD, COLOR_WHITE);
	private static final Font PANEL_HDR_FONT = FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, COLOR_WHITE);
	private static final Font PANEL_TEXT_FONT_BOLD = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, COLOR_DARK_BLUE);
	private static final Font PANEL_TEXT_FONT_NORMAL = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, COLOR_DARK_BLUE);
	private static final Font COLUMN_HDR_FONT = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD, COLOR_DARK_BLUE);
	private static final Font COLUMN_VALUE_FONT = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, COLOR_BLACK);
	private static final Font COLUMN_VALUE_FONT_BOLD = FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, COLOR_BLACK);



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


		final PdfPTable billShipInfoTable = new PdfPTable(3);
		billShipInfoTable.setHorizontalAlignment(Element.ALIGN_LEFT);
		final float[] widths =
		{ 80f, 340f, 80f };
		final Rectangle r = new Rectangle(PageSize.A4.getRight(72), PageSize.A4.getTop(72));
		billShipInfoTable.setWidthPercentage(widths, r);
		billShipInfoTable.setSpacingBefore(3f);
		billShipInfoTable.setSpacingAfter(3f);


		final PdfPTable generalInfoTable = new PdfPTable(9);
		generalInfoTable.setHorizontalAlignment(Element.ALIGN_LEFT);
		final float[] widths2 =
		{ 45f, 55f, 70f, 50f, 50f, 50f, 60f, 70f, 50f };
		final Rectangle r2 = new Rectangle(PageSize.A4.getRight(72), PageSize.A4.getTop(72));
		generalInfoTable.setWidthPercentage(widths2, r2);
		generalInfoTable.setSpacingBefore(3f);
		generalInfoTable.setSpacingAfter(3f);


		final PdfPTable lineItemsTable = new PdfPTable(8);
		lineItemsTable.setHorizontalAlignment(Element.ALIGN_LEFT);
		final float[] widths4 =
		{ 35f, 60f, 170f, 60f, 40f, 40f, 40f, 55f };
		final Rectangle r4 = new Rectangle(PageSize.A4.getRight(72), PageSize.A4.getTop(72));
		lineItemsTable.setWidthPercentage(widths4, r4);
		lineItemsTable.setSpacingBefore(3f);
		lineItemsTable.setSpacingAfter(3f);


		final PdfPTable invoiceTotable = new PdfPTable(1);

		final PdfPTable packingDetailstable = new PdfPTable(5);
		final float[] widths1 =
		{ 55f, 70f, 45f, 80f, 70f };
		final Rectangle r1 = new Rectangle(PageSize.A4.getRight(74), PageSize.A4.getTop(74));
		packingDetailstable.setWidthPercentage(widths1, r1);
		final PdfPTable deliverTotable = new PdfPTable(1);


		final PdfPCell mainHeadingCell = new PdfPCell();
		mainHeadingCell.setBackgroundColor(Color.BLUE);
		mainHeadingCell.setPadding(5);


		mainHeadingCell.setPhrase(new Phrase("Billing and Shipping Information", SECTION_HDR_FONT));
		mainHeadingCell.setColspan(3);
		mainHeadingCell.setBackgroundColor(Color.LIGHT_GRAY);
		mainHeadingCell.setHorizontalAlignment(1);
		billShipInfoTable.addCell(mainHeadingCell);
		final PdfPCell mainHeadingCell1 = new PdfPCell();
		mainHeadingCell1.setBackgroundColor(Color.LIGHT_GRAY);
		mainHeadingCell1.setPadding(5);
		mainHeadingCell1.setColspan(9);
		mainHeadingCell1.setHorizontalAlignment(1);

		mainHeadingCell1.setPhrase(new Phrase("General Information", SECTION_HDR_FONT));
		generalInfoTable.addCell(mainHeadingCell1);

		final PdfPCell mainHeadingCell2 = new PdfPCell();
		mainHeadingCell2.setBackgroundColor(Color.LIGHT_GRAY);
		mainHeadingCell2.setPadding(5);
		mainHeadingCell2.setColspan(8);
		mainHeadingCell2.setHorizontalAlignment(1);
		mainHeadingCell2.setPhrase(new Phrase("Line Items", SECTION_HDR_FONT));
		mainHeadingCell2.setHorizontalAlignment(1);
		lineItemsTable.addCell(mainHeadingCell2);
		final PdfPCell invoiceTo = new PdfPCell();
		invoiceTo.setBackgroundColor(Color.LIGHT_GRAY);
		invoiceTo.setPhrase(new Phrase("Invoice To", PANEL_HDR_FONT));
		invoiceTo.setIndent(Element.ALIGN_CENTER);
		invoiceTotable.addCell(invoiceTo);

		final ShippedOrderBO shippedOrderDetailsBO = (ShippedOrderBO) model.get("shippedOrderDetailsBO");
		if (shippedOrderDetailsBO != null)
		{



			if (shippedOrderDetailsBO.getBillToAccount() != null)
			{
				final PdfPCell invoiceToAccName = new PdfPCell();
				invoiceToAccName.setBorder(Rectangle.NO_BORDER);
				if (shippedOrderDetailsBO.getBillToAccount().getAccountName() != null)
				{
					invoiceToAccName.setPhrase(new Phrase("" + shippedOrderDetailsBO.getBillToAccount().getAccountName(),
							PANEL_TEXT_FONT_BOLD));
					invoiceTotable.addCell(invoiceToAccName);
				}
				else
				{
					invoiceToAccName.setPhrase(new Phrase(""));
					invoiceTotable.addCell(invoiceToAccName);
				}

			}
			else
			{
				final PdfPCell invoiceToAccName = new PdfPCell();
				invoiceToAccName.setBorder(Rectangle.NO_BORDER);
				invoiceToAccName.setPhrase(new Phrase(""));
				invoiceTotable.addCell(invoiceToAccName);
			}
			if (shippedOrderDetailsBO.getBillToAccount() != null)
			{
				final PdfPCell invoiceToAdd1e = new PdfPCell();
				invoiceToAdd1e.setBorder(Rectangle.NO_BORDER);
				if (shippedOrderDetailsBO.getBillToAccount().getAddress() != null)
				{
					if (shippedOrderDetailsBO.getBillToAccount().getAddress().getAddrLine1() != null)
					{
						invoiceToAdd1e.setPhrase(new Phrase("" + shippedOrderDetailsBO.getBillToAccount().getAddress().getAddrLine1(),
								PANEL_TEXT_FONT_NORMAL));


					}
					else
					{
						invoiceToAdd1e.setPhrase(new Phrase(""));


					}
					invoiceTotable.addCell(invoiceToAdd1e);

				}
				else
				{
					invoiceToAdd1e.setPhrase(new Phrase(""));
					invoiceTotable.addCell(invoiceToAdd1e);
				}

			}
			else
			{
				final PdfPCell invoiceToAdd1e = new PdfPCell();
				invoiceToAdd1e.setBorder(Rectangle.NO_BORDER);
				invoiceToAdd1e.setPhrase(new Phrase(""));
				invoiceTotable.addCell(invoiceToAdd1e);
			}
			if (shippedOrderDetailsBO.getBillToAccount() != null)
			{
				final PdfPCell invoiceToAdd2 = new PdfPCell();
				invoiceToAdd2.setBorder(Rectangle.NO_BORDER);
				if (shippedOrderDetailsBO.getBillToAccount().getAddress() != null)
				{
					if (shippedOrderDetailsBO.getBillToAccount().getAddress().getAddrLine2() != null)
					{
						invoiceToAdd2.setPhrase(new Phrase("" + shippedOrderDetailsBO.getBillToAccount().getAddress().getAddrLine2(),
								PANEL_TEXT_FONT_NORMAL));

					}
					else
					{
						invoiceToAdd2.setPhrase(new Phrase(""));


					}

					invoiceTotable.addCell(invoiceToAdd2);
				}
				else
				{
					invoiceToAdd2.setPhrase(new Phrase(""));
					invoiceTotable.addCell(invoiceToAdd2);
				}

			}
			else
			{
				final PdfPCell invoiceToAdd2 = new PdfPCell();
				invoiceToAdd2.setBorder(Rectangle.NO_BORDER);
				invoiceToAdd2.setPhrase(new Phrase(""));
				invoiceTotable.addCell(invoiceToAdd2);
			}
			if (shippedOrderDetailsBO.getBillToAccount() != null)
			{

				if (shippedOrderDetailsBO.getBillToAccount().getAddress() != null)
				{
					final PdfPCell invoiceTocity = new PdfPCell();
					invoiceTocity.setBorder(Rectangle.NO_BORDER);
					if (shippedOrderDetailsBO.getBillToAccount().getAddress().getCity() != null
							&& shippedOrderDetailsBO.getBillToAccount().getAddress().getStateOrProv() != null
							&& shippedOrderDetailsBO.getBillToAccount().getAddress().getZipOrPostalCode() != null)
					{

						invoiceTocity.setPhrase(new Phrase("" + shippedOrderDetailsBO.getBillToAccount().getAddress().getCity() + ", "
								+ "" + shippedOrderDetailsBO.getBillToAccount().getAddress().getStateOrProv() + " " + ""
								+ shippedOrderDetailsBO.getBillToAccount().getAddress().getZipOrPostalCode(), PANEL_TEXT_FONT_NORMAL));
						invoiceTotable.addCell(invoiceTocity);
					}
					else
					{
						invoiceTocity.setPhrase(new Phrase(""));
						invoiceTotable.addCell(invoiceTocity);
					}
				}
				else
				{
					final PdfPCell invoiceTocity = new PdfPCell();
					invoiceTocity.setBorder(Rectangle.NO_BORDER);
					invoiceTocity.setPhrase(new Phrase(""));
					invoiceTotable.addCell(invoiceTocity);
				}


			}
			else
			{
				final PdfPCell invoiceTocity = new PdfPCell();
				invoiceTocity.setBorder(Rectangle.NO_BORDER);
				invoiceTocity.setPhrase(new Phrase(""));
				invoiceTotable.addCell(invoiceTocity);
			}

			if (shippedOrderDetailsBO.getBillToAccount() != null)
			{
				final PdfPCell invoiceToPhNo = new PdfPCell();
				invoiceToPhNo.setBorder(Rectangle.NO_BORDER);
				if (shippedOrderDetailsBO.getBillToAccount().getAddress() != null)
				{
					if (shippedOrderDetailsBO.getBillToAccount().getAddress().getPhoneNum() != null)
					{
						invoiceToPhNo.setPhrase(new Phrase("" + shippedOrderDetailsBO.getBillToAccount().getAddress().getPhoneNum(),
								PANEL_TEXT_FONT_NORMAL));
						invoiceTotable.addCell(invoiceToPhNo);
					}
					else
					{
						invoiceToPhNo.setPhrase(new Phrase(""));
						invoiceTotable.addCell(invoiceToPhNo);
					}


				}
				else
				{
					invoiceToPhNo.setPhrase(new Phrase(""));
					invoiceTotable.addCell(invoiceToPhNo);
				}
			}
			else
			{
				final PdfPCell invoiceToPhNo = new PdfPCell();
				invoiceToPhNo.setBorder(Rectangle.NO_BORDER);
				invoiceToPhNo.setPhrase(new Phrase(""));
				invoiceTotable.addCell(invoiceToPhNo);
			}
			if (shippedOrderDetailsBO.getBillToAccount() != null)
			{
				final PdfPCell invoiceAccNO = new PdfPCell();
				invoiceAccNO.setBorder(Rectangle.NO_BORDER);
				invoiceAccNO.setBackgroundColor(COLOR_SKY_BLUE);
				if (shippedOrderDetailsBO.getBillToAccount().getAccountCode() != null)
				{
					invoiceAccNO.setPhrase(new Phrase("Account #: " + "" + shippedOrderDetailsBO.getBillToAccount().getAccountCode(),
							PANEL_TEXT_FONT_BOLD));
					invoiceTotable.addCell(invoiceAccNO);

				}
				else
				{
					invoiceAccNO.setPhrase(new Phrase(""));
					invoiceTotable.addCell(invoiceAccNO);
				}
			}
			else
			{
				final PdfPCell invoiceAccNO = new PdfPCell();
				invoiceAccNO.setBorder(Rectangle.NO_BORDER);
				invoiceAccNO.setPhrase(new Phrase(""));
				invoiceTotable.addCell(invoiceAccNO);
			}

			for (final OrderUnitBO orderUnitBO : shippedOrderDetailsBO.getOrderUnitList())
			{


				final PdfPCell packingdetailsCell = new PdfPCell();
				packingdetailsCell.setPhrase(new Phrase("Packing Slip #", COLUMN_HDR_FONT));
				packingdetailsCell.setIndent(Element.ALIGN_CENTER);
				packingDetailstable.addCell(packingdetailsCell);

				packingdetailsCell.setPhrase(new Phrase("Shipping DC", COLUMN_HDR_FONT));
				packingdetailsCell.setIndent(Element.ALIGN_CENTER);
				packingDetailstable.addCell(packingdetailsCell);

				packingdetailsCell.setPhrase(new Phrase("Ship Date", COLUMN_HDR_FONT));
				packingdetailsCell.setIndent(Element.ALIGN_CENTER);
				packingDetailstable.addCell(packingdetailsCell);

				packingdetailsCell.setPhrase(new Phrase("Carrier code", COLUMN_HDR_FONT));
				packingdetailsCell.setIndent(Element.ALIGN_CENTER);
				packingDetailstable.addCell(packingdetailsCell);

				packingdetailsCell.setPhrase(new Phrase("Tracking #", COLUMN_HDR_FONT));
				packingdetailsCell.setIndent(Element.ALIGN_CENTER);
				packingDetailstable.addCell(packingdetailsCell);
				int totalLines = 0;
				int totalLinesShipped = 0;
				int totalPieces = 0;
				int totalPiecesShipped = 0;
				String packingStatus = "";
				for (final ShippingUnitBO shippingUnitBO : orderUnitBO.getShippingUnitList())
				{
					if (shippingUnitBO.getPackingSlip() != null)
					{
						final PdfPCell packingSlip = new PdfPCell();
						packingSlip.setBackgroundColor(COLOR_SKY_BLUE);
						packingSlip.setIndent(Element.ALIGN_CENTER);

						packingSlip.setPhrase(new Phrase("" + shippingUnitBO.getPackingSlip(), COLUMN_VALUE_FONT));
						packingDetailstable.addCell(packingSlip);
					}
					else
					{
						final PdfPCell packingSlip = new PdfPCell();
						packingSlip.setBackgroundColor(COLOR_SKY_BLUE);
						packingSlip.setPhrase(new Phrase(""));
						packingDetailstable.addCell(packingSlip);
					}

					//shipping dc
					if (shippingUnitBO.getShipFrom() != null)
					{
						if (shippingUnitBO.getShipFrom().getName() != null)
						{
							final PdfPCell dcName = new PdfPCell();
							dcName.setBackgroundColor(COLOR_SKY_BLUE);
							final String str = shippingUnitBO.getShipFrom().getName();
							dcName.setPhrase(new Phrase(str, COLUMN_VALUE_FONT));
							packingDetailstable.addCell(dcName);
						}
						else
						{
							final PdfPCell dcName = new PdfPCell();
							dcName.setBackgroundColor(COLOR_SKY_BLUE);
							dcName.setPhrase(new Phrase(""));
							packingDetailstable.addCell(dcName);
						}

					}
					else
					{
						final PdfPCell dcName = new PdfPCell();
						dcName.setBackgroundColor(COLOR_SKY_BLUE);
						dcName.setPhrase(new Phrase(""));
						packingDetailstable.addCell(dcName);
					}
					//ship date
					if (shippingUnitBO.getShipDate() != null)
					{
						final PdfPCell shipDate = new PdfPCell();
						shipDate.setBackgroundColor(COLOR_SKY_BLUE);
						shipDate.setIndent(Element.ALIGN_CENTER);
						shipDate.setPhrase(new Phrase(convertDate(shippingUnitBO.getShipDate()), COLUMN_VALUE_FONT));
						packingDetailstable.addCell(shipDate);
					}
					else
					{
						final PdfPCell shipDate = new PdfPCell();
						shipDate.setBackgroundColor(COLOR_SKY_BLUE);
						shipDate.setPhrase(new Phrase(""));
						packingDetailstable.addCell(shipDate);
					}
					if (shippingUnitBO.getCarrierCode() != null)
					{
						final PdfPCell carrierCd = new PdfPCell();
						carrierCd.setBackgroundColor(COLOR_SKY_BLUE);
						carrierCd.setIndent(Element.ALIGN_CENTER);
						if (shippingUnitBO.getShippingMethodCode() != null)
						{
							carrierCd.setPhrase(new Phrase("" + shippingUnitBO.getCarrierCode(), COLUMN_VALUE_FONT));
						}
						else
						{
							carrierCd.setPhrase(new Phrase("TBD"));
						}

						packingDetailstable.addCell(carrierCd);
					}
					else
					{
						final PdfPCell carrierCd = new PdfPCell();
						carrierCd.setBackgroundColor(COLOR_SKY_BLUE);
						carrierCd.setPhrase(new Phrase("TBD"));
						packingDetailstable.addCell(carrierCd);
					}

					if (shippingUnitBO.getTrackingNumber() != null)
					{
						final PdfPCell trackNo = new PdfPCell();
						trackNo.setBackgroundColor(COLOR_SKY_BLUE);
						trackNo.setIndent(Element.ALIGN_CENTER);
						trackNo.setPhrase(new Phrase("" + shippingUnitBO.getTrackingNumber(), COLUMN_VALUE_FONT));
						packingDetailstable.addCell(trackNo);
					}
					else
					{
						final PdfPCell trackNo = new PdfPCell();
						trackNo.setBackgroundColor(COLOR_SKY_BLUE);
						trackNo.setPhrase(new Phrase(""));
						packingDetailstable.addCell(trackNo);
					}
					totalLines += shippingUnitBO.getTotalLines();
					totalLinesShipped += shippingUnitBO.getLinesShipped();
					totalPieces += shippingUnitBO.getTotalPieces();
					totalPiecesShipped += shippingUnitBO.getTotalPiecesShipped();

					packingStatus = shippingUnitBO.getPackingStatus();

					if (null != shippingUnitBO.getPackingStatus())
					{
						if (shippingUnitBO.getPackingStatus().equalsIgnoreCase("IN_PROCESS"))
						{
							packingStatus = "In Progress";
						}
						else if (shippingUnitBO.getPackingStatus().equalsIgnoreCase("Partial_shipment")
								|| shippingUnitBO.getPackingStatus().equalsIgnoreCase("PARTIAL_SHIPMENT"))
						{
							packingStatus = "Partial Shipment";
						}
					}

				}
				final String totalsStr = "Total # Lines" + ": " + totalLines + TOTALS_STRING_DELIMITER + "# Lines Shipped" + ": "
						+ totalLinesShipped + TOTALS_STRING_DELIMITER + "Total Pieces" + ": " + totalPieces + TOTALS_STRING_DELIMITER
						+ "# Pieces Shipped" + ": " + totalPiecesShipped;
				final PdfPCell totalsCell = new PdfPCell();
				totalsCell.setBorder(0);
				totalsCell.setColspan(5);
				totalsCell.setPhrase(new Phrase(totalsStr, COLUMN_VALUE_FONT_BOLD));
				totalsCell.setIndent(Element.ALIGN_CENTER);
				totalsCell.setBackgroundColor(Color.LIGHT_GRAY);
				packingDetailstable.addCell(totalsCell);

				// Blank row
				final PdfPCell blankCell = new PdfPCell(new Phrase(""));
				blankCell.setColspan(5);
				blankCell.setBorder(0);
				packingDetailstable.addCell(blankCell);

				final PdfPCell deliveryTo = new PdfPCell();
				deliveryTo.setBackgroundColor(Color.LIGHT_GRAY);
				deliveryTo.setPhrase(new Phrase("Deliver To", PANEL_HDR_FONT));
				deliveryTo.setIndent(Element.ALIGN_CENTER);
				deliverTotable.addCell(deliveryTo);

				if (shippedOrderDetailsBO.getShipToAccount() != null)
				{
					final PdfPCell deliveryToAdd1e = new PdfPCell();
					deliveryToAdd1e.setBorder(Rectangle.NO_BORDER);
					if (shippedOrderDetailsBO.getShipToAccount().getAccountName() != null)
					{
						deliveryToAdd1e.setPhrase(new Phrase("" + shippedOrderDetailsBO.getShipToAccount().getAccountName(),
								PANEL_TEXT_FONT_BOLD));
						deliverTotable.addCell(deliveryToAdd1e);
					}
					else
					{
						deliveryToAdd1e.setPhrase(new Phrase(""));
						deliverTotable.addCell(deliveryToAdd1e);
					}

				}
				else
				{
					final PdfPCell deliveryToAdd1e = new PdfPCell();
					deliveryToAdd1e.setBorder(Rectangle.NO_BORDER);
					deliveryToAdd1e.setPhrase(new Phrase(""));
					deliverTotable.addCell(deliveryToAdd1e);
				}
				if (shippedOrderDetailsBO.getShipToAccount() != null)
				{
					final PdfPCell deliveryToAdd1 = new PdfPCell();
					deliveryToAdd1.setBorder(Rectangle.NO_BORDER);
					if (shippedOrderDetailsBO.getShipToAccount().getAddress() != null)
					{
						if (shippedOrderDetailsBO.getShipToAccount().getAddress().getAddrLine1() != null)
						{
							deliveryToAdd1.setPhrase(new Phrase(""
									+ shippedOrderDetailsBO.getShipToAccount().getAddress().getAddrLine1(), PANEL_TEXT_FONT_NORMAL));

						}
						else
						{
							deliveryToAdd1.setPhrase(new Phrase(""));


						}

						deliverTotable.addCell(deliveryToAdd1);
						//	deliverTotable.addCell(shippedOrderDetailsBO.getShipToAccount().getAddress().getAddrLine1());
					}
					else
					{
						deliveryToAdd1.setPhrase(new Phrase(""));
						deliverTotable.addCell(deliveryToAdd1);
					}

				}
				else
				{
					final PdfPCell deliveryToAdd1 = new PdfPCell();
					deliveryToAdd1.setBorder(Rectangle.NO_BORDER);
					deliveryToAdd1.setPhrase(new Phrase(""));
					deliverTotable.addCell(deliveryToAdd1);
				}
				if (shippedOrderDetailsBO.getShipToAccount() != null)
				{
					final PdfPCell deliveryToAdd2 = new PdfPCell();
					deliveryToAdd2.setBorder(Rectangle.NO_BORDER);
					if (shippedOrderDetailsBO.getShipToAccount().getAddress() != null)
					{

						if (shippedOrderDetailsBO.getShipToAccount().getAddress().getAddrLine2() != null)
						{

							deliveryToAdd2.setPhrase(new Phrase(""
									+ shippedOrderDetailsBO.getShipToAccount().getAddress().getAddrLine2(), PANEL_TEXT_FONT_NORMAL));


						}
						else
						{
							deliveryToAdd2.setPhrase(new Phrase(""));
						}
						deliverTotable.addCell(deliveryToAdd2);
					}
					else
					{
						deliveryToAdd2.setPhrase(new Phrase(""));
						deliverTotable.addCell(deliveryToAdd2);
					}
				}
				else
				{
					final PdfPCell deliveryToAdd2 = new PdfPCell();
					deliveryToAdd2.setBorder(Rectangle.NO_BORDER);
					deliveryToAdd2.setPhrase(new Phrase(""));
					deliverTotable.addCell(deliveryToAdd2);
				}
				if (shippedOrderDetailsBO.getShipToAccount() != null)
				{
					if (shippedOrderDetailsBO.getShipToAccount().getAddress() != null)
					{
						final PdfPCell deliveryTocity = new PdfPCell();
						deliveryTocity.setBorder(Rectangle.NO_BORDER);
						if (shippedOrderDetailsBO.getShipToAccount().getAddress().getCity() != null
								&& shippedOrderDetailsBO.getShipToAccount().getAddress().getStateOrProv() != null
								&& shippedOrderDetailsBO.getShipToAccount().getAddress().getZipOrPostalCode() != null)
						{
							deliveryTocity.setPhrase(new Phrase("" + shippedOrderDetailsBO.getShipToAccount().getAddress().getCity()
									+ ", " + "" + shippedOrderDetailsBO.getShipToAccount().getAddress().getStateOrProv() + " " + ""
									+ shippedOrderDetailsBO.getShipToAccount().getAddress().getZipOrPostalCode(), PANEL_TEXT_FONT_NORMAL));
							deliverTotable.addCell(deliveryTocity);
						}
						else
						{
							deliveryTocity.setPhrase(new Phrase(""));
							deliverTotable.addCell(deliveryTocity);
						}
					}
					else
					{
						final PdfPCell deliveryTocity = new PdfPCell();
						deliveryTocity.setBorder(Rectangle.NO_BORDER);
						deliveryTocity.setPhrase(new Phrase(""));
						deliverTotable.addCell(deliveryTocity);
					}

				}
				else
				{
					final PdfPCell deliveryTocity = new PdfPCell();
					deliveryTocity.setBorder(Rectangle.NO_BORDER);
					deliveryTocity.setPhrase(new Phrase(""));
					deliverTotable.addCell(deliveryTocity);
				}
				if (shippedOrderDetailsBO.getShipToAccount() != null)
				{
					final PdfPCell deliveryPhNo = new PdfPCell();
					deliveryPhNo.setBorder(Rectangle.NO_BORDER);
					if (shippedOrderDetailsBO.getShipToAccount().getAddress() != null)
					{
						if (shippedOrderDetailsBO.getShipToAccount().getAddress().getPhoneNum() != null)
						{
							deliveryPhNo.setPhrase(new Phrase("" + shippedOrderDetailsBO.getShipToAccount().getAddress().getPhoneNum(),
									PANEL_TEXT_FONT_NORMAL));
							deliverTotable.addCell(deliveryPhNo);
						}
						else
						{
							deliveryPhNo.setPhrase(new Phrase(""));
							deliverTotable.addCell(deliveryPhNo);
						}
					}
					else
					{
						deliveryPhNo.setPhrase(new Phrase(""));
						deliverTotable.addCell(deliveryPhNo);
					}

				}
				else
				{
					final PdfPCell deliveryPhNo = new PdfPCell();
					deliveryPhNo.setBorder(Rectangle.NO_BORDER);
					deliveryPhNo.setPhrase(new Phrase(""));
					deliverTotable.addCell(deliveryPhNo);
				}
				if (shippedOrderDetailsBO.getShipToAccount() != null)
				{
					final PdfPCell detailsAccNO = new PdfPCell();
					detailsAccNO.setBorder(Rectangle.NO_BORDER);
					detailsAccNO.setBackgroundColor(COLOR_SKY_BLUE);
					if (shippedOrderDetailsBO.getShipToAccount().getAccountCode() != null)
					{
						detailsAccNO.setPhrase(new Phrase("Account #: " + ""
								+ shippedOrderDetailsBO.getShipToAccount().getAccountCode(), PANEL_TEXT_FONT_BOLD));
						deliverTotable.addCell(detailsAccNO);

					}
					else
					{
						detailsAccNO.setPhrase(new Phrase(""));
						deliverTotable.addCell(detailsAccNO);
					}
				}
				else
				{
					final PdfPCell detailsAccNO = new PdfPCell();
					detailsAccNO.setBorder(Rectangle.NO_BORDER);
					detailsAccNO.setPhrase(new Phrase(""));
					deliverTotable.addCell(detailsAccNO);
				}


				billShipInfoTable.addCell(invoiceTotable);
				billShipInfoTable.addCell(packingDetailstable);
				billShipInfoTable.addCell(deliverTotable);

				final PdfPCell generalInfoCell = new PdfPCell();
				generalInfoCell.setPhrase(new Phrase("Order #", COLUMN_HDR_FONT));
				generalInfoCell.setIndent(Element.ALIGN_CENTER);
				generalInfoTable.addCell(generalInfoCell);

				generalInfoCell.setPhrase(new Phrase("Purchase Order #", COLUMN_HDR_FONT));
				generalInfoCell.setIndent(Element.ALIGN_CENTER);
				generalInfoTable.addCell(generalInfoCell);

				generalInfoCell.setPhrase(new Phrase("Order Type", COLUMN_HDR_FONT));
				generalInfoCell.setIndent(Element.ALIGN_CENTER);
				generalInfoTable.addCell(generalInfoCell);

				generalInfoCell.setPhrase(new Phrase("Order Date", COLUMN_HDR_FONT));
				generalInfoCell.setIndent(Element.ALIGN_CENTER);
				generalInfoTable.addCell(generalInfoCell);

				generalInfoCell.setPhrase(new Phrase("Order Source", COLUMN_HDR_FONT));
				generalInfoCell.setIndent(Element.ALIGN_CENTER);
				generalInfoTable.addCell(generalInfoCell);
				generalInfoCell.setPhrase(new Phrase("Order Status", COLUMN_HDR_FONT));
				generalInfoCell.setIndent(Element.ALIGN_CENTER);
				generalInfoTable.addCell(generalInfoCell);
				generalInfoCell.setPhrase(new Phrase("Ordered By", COLUMN_HDR_FONT));
				generalInfoCell.setIndent(Element.ALIGN_CENTER);
				generalInfoTable.addCell(generalInfoCell);
				generalInfoCell.setPhrase(new Phrase("Ordered Cancelled Date", COLUMN_HDR_FONT));
				generalInfoCell.setIndent(Element.ALIGN_CENTER);
				generalInfoTable.addCell(generalInfoCell);
				generalInfoCell.setPhrase(new Phrase("Required Date", COLUMN_HDR_FONT));
				generalInfoCell.setIndent(Element.ALIGN_CENTER);
				generalInfoTable.addCell(generalInfoCell);

				if (orderUnitBO.getOrderNumber() != null)
				{
					final PdfPCell orderNo = new PdfPCell();
					orderNo.setBackgroundColor(COLOR_SKY_BLUE);
					orderNo.setIndent(Element.ALIGN_CENTER);
					orderNo.setPhrase(new Phrase("" + orderUnitBO.getOrderNumber(), COLUMN_VALUE_FONT));
					generalInfoTable.addCell(orderNo);
				}
				else
				{
					final PdfPCell orderNo = new PdfPCell();
					orderNo.setBackgroundColor(COLOR_SKY_BLUE);
					orderNo.setPhrase(new Phrase(""));
					generalInfoTable.addCell(orderNo);
				}
				if (shippedOrderDetailsBO.getCustomerPurchaseOrderNum() != null)
				{
					final PdfPCell poNO = new PdfPCell();
					poNO.setBackgroundColor(COLOR_SKY_BLUE);
					poNO.setIndent(Element.ALIGN_CENTER);
					poNO.setPhrase(new Phrase("" + shippedOrderDetailsBO.getCustomerPurchaseOrderNum(), COLUMN_VALUE_FONT));
					generalInfoTable.addCell(poNO);
				}
				else
				{
					final PdfPCell poNO = new PdfPCell();
					poNO.setBackgroundColor(COLOR_SKY_BLUE);
					poNO.setPhrase(new Phrase(""));
					generalInfoTable.addCell(poNO);
				}
				//order Type
				if (shippedOrderDetailsBO.getOrderType() != null)
				{
					final PdfPCell orderType = new PdfPCell();
					orderType.setBackgroundColor(COLOR_SKY_BLUE);
					orderType.setIndent(Element.ALIGN_CENTER);
					orderType.setPhrase(new Phrase("" + shippedOrderDetailsBO.getOrderType().name(), COLUMN_VALUE_FONT));
					generalInfoTable.addCell(orderType);
				}
				else
				{
					final PdfPCell orderType = new PdfPCell();
					orderType.setBackgroundColor(COLOR_SKY_BLUE);
					orderType.setPhrase(new Phrase(""));
					generalInfoTable.addCell(orderType);
				}
				//Order Date
				if (orderUnitBO.getOriginalOrderDate() != null)
				{
					final PdfPCell oDate = new PdfPCell();
					oDate.setBackgroundColor(COLOR_SKY_BLUE);
					oDate.setIndent(Element.ALIGN_CENTER);
					oDate.setPhrase(new Phrase(convertDate(orderUnitBO.getOriginalOrderDate()), COLUMN_VALUE_FONT));
					generalInfoTable.addCell(oDate);

				}
				else
				{
					final PdfPCell oDate = new PdfPCell();
					oDate.setBackgroundColor(COLOR_SKY_BLUE);
					oDate.setPhrase(new Phrase(""));
					generalInfoTable.addCell(oDate);
				}
				//Order Source
				if (orderUnitBO.getOrderSourceKey() != null)
				{
					final PdfPCell oSource = new PdfPCell();
					oSource.setBackgroundColor(COLOR_SKY_BLUE);
					oSource.setIndent(Element.ALIGN_CENTER);
					oSource.setPhrase(new Phrase("" + orderUnitBO.getOrderSourceKey(), COLUMN_VALUE_FONT));
					generalInfoTable.addCell(oSource);
				}
				else
				{

					final PdfPCell oSource = new PdfPCell();
					oSource.setBackgroundColor(COLOR_SKY_BLUE);
					oSource.setPhrase(new Phrase(""));
					generalInfoTable.addCell(oSource);
				}
				//Order Status
				if (packingStatus != null)
				{
					final PdfPCell pStatus = new PdfPCell();
					pStatus.setBackgroundColor(COLOR_SKY_BLUE);
					pStatus.setIndent(Element.ALIGN_CENTER);
					if ("IN_PROCESS".equalsIgnoreCase(packingStatus))
					{
						pStatus.setPhrase(new Phrase("In Progress", COLUMN_VALUE_FONT));
					}
					else
					{
						pStatus.setPhrase(new Phrase("" + packingStatus, COLUMN_VALUE_FONT));
					}

					generalInfoTable.addCell(pStatus);
				}
				else
				{
					final PdfPCell pStatus = new PdfPCell();
					pStatus.setBackgroundColor(COLOR_SKY_BLUE);
					pStatus.setPhrase(new Phrase(""));
					generalInfoTable.addCell(pStatus);
				}
				if (shippedOrderDetailsBO.getOrderedBy() != null)
				{
					final PdfPCell oBy = new PdfPCell();
					oBy.setBackgroundColor(COLOR_SKY_BLUE);
					oBy.setIndent(Element.ALIGN_CENTER);
					oBy.setPhrase(new Phrase("" + shippedOrderDetailsBO.getOrderedBy(), COLUMN_VALUE_FONT));
					generalInfoTable.addCell(oBy);
				}
				else
				{
					final PdfPCell oBy = new PdfPCell();
					oBy.setBackgroundColor(COLOR_SKY_BLUE);
					oBy.setPhrase(new Phrase(""));
					generalInfoTable.addCell(oBy);
				}
				//Ordered Cancelled Date
				if (orderUnitBO.getCancelledDate() != null)
				{
					final PdfPCell cDate = new PdfPCell();
					cDate.setBackgroundColor(COLOR_SKY_BLUE);
					cDate.setIndent(Element.ALIGN_CENTER);
					cDate.setPhrase(new Phrase(convertDate(orderUnitBO.getCancelledDate()), COLUMN_VALUE_FONT));
					generalInfoTable.addCell(cDate);

				}
				else
				{
					final PdfPCell cDate = new PdfPCell();
					cDate.setBackgroundColor(COLOR_SKY_BLUE);
					cDate.setPhrase(new Phrase(""));
					generalInfoTable.addCell(cDate);
				}
				//Required Date
				if (orderUnitBO.getRequestedDeliveryDate() != null)
				{
					final PdfPCell iDate = new PdfPCell();
					iDate.setBackgroundColor(COLOR_SKY_BLUE);
					iDate.setIndent(Element.ALIGN_CENTER);
					iDate.setPhrase(new Phrase(convertDate(orderUnitBO.getRequestedDeliveryDate()), COLUMN_VALUE_FONT));
					generalInfoTable.addCell(iDate);

				}
				else
				{
					final PdfPCell iDate = new PdfPCell();
					iDate.setBackgroundColor(COLOR_SKY_BLUE);
					iDate.setPhrase(new Phrase(""));
					generalInfoTable.addCell(iDate);
				}
				final PdfPCell commentsLabelCell = getCell(false, "Order Comments:" + ":", 4, Element.ALIGN_RIGHT,
						COLUMN_VALUE_FONT_BOLD);
				//commentsLabelCell.setPhrase(new Phrase("Order Comments:", COLUMN_VALUE_FONT_BOLD));
				commentsLabelCell.setBackgroundColor(Color.LIGHT_GRAY);
				commentsLabelCell.setVerticalAlignment(Element.ALIGN_TOP);
				generalInfoTable.addCell(commentsLabelCell);

				final PdfPCell commentsValuesCell = getCell(false, getBulletedCommentsListAsString(orderUnitBO), 5,
						Element.ALIGN_LEFT, COLUMN_VALUE_FONT_BOLD);
				commentsValuesCell.setBackgroundColor(Color.LIGHT_GRAY);
				commentsValuesCell.setVerticalAlignment(Element.ALIGN_TOP);
				generalInfoTable.addCell(commentsValuesCell);

				//break;
				//}
				//break;
			}


			final PdfPCell lineItemsCell = new PdfPCell();
			lineItemsCell.setPhrase(new Phrase("Line #", COLUMN_HDR_FONT));
			lineItemsCell.setIndent(Element.ALIGN_CENTER);
			lineItemsTable.addCell(lineItemsCell);
			lineItemsCell.setPhrase(new Phrase("Part #", COLUMN_HDR_FONT));
			lineItemsCell.setIndent(Element.ALIGN_CENTER);
			lineItemsTable.addCell(lineItemsCell);
			lineItemsCell.setPhrase(new Phrase("Description", COLUMN_HDR_FONT));
			lineItemsCell.setIndent(Element.ALIGN_CENTER);
			lineItemsTable.addCell(lineItemsCell);
			lineItemsCell.setPhrase(new Phrase("Packing Slip #", COLUMN_HDR_FONT));
			lineItemsCell.setIndent(Element.ALIGN_CENTER);
			lineItemsTable.addCell(lineItemsCell);
			lineItemsCell.setPhrase(new Phrase("Ordered", COLUMN_HDR_FONT));
			lineItemsCell.setIndent(Element.ALIGN_CENTER);
			lineItemsTable.addCell(lineItemsCell);
			lineItemsCell.setPhrase(new Phrase("Assigned", COLUMN_HDR_FONT));
			lineItemsCell.setIndent(Element.ALIGN_CENTER);
			lineItemsTable.addCell(lineItemsCell);
			lineItemsCell.setPhrase(new Phrase("Shipped", COLUMN_HDR_FONT));
			lineItemsCell.setIndent(Element.ALIGN_CENTER);
			lineItemsTable.addCell(lineItemsCell);
			lineItemsCell.setPhrase(new Phrase("Back Ordered", COLUMN_HDR_FONT));
			lineItemsCell.setIndent(Element.ALIGN_CENTER);
			lineItemsTable.addCell(lineItemsCell);
			for (final OrderUnitBO orderUnitBO : shippedOrderDetailsBO.getOrderUnitList())
			{
				for (final ShippingUnitBO shippingUnitBO : orderUnitBO.getShippingUnitList())
				{

					for (final ShippedItemBO shippedItemBO : shippingUnitBO.getShippedItemsList())
					{
						LOG.info("Size of shippedItemBO" + shippingUnitBO.getShippedItemsList().size());

						if (shippedItemBO.getLineNumber() != 0)
						{
							final PdfPCell lineNO = new PdfPCell();
							lineNO.setBackgroundColor(COLOR_SKY_BLUE);
							lineNO.setIndent(Element.ALIGN_CENTER);
							lineNO.setPhrase(new Phrase("" + shippedItemBO.getLineNumber(), COLUMN_VALUE_FONT));
							lineItemsTable.addCell(lineNO);
						}
						else
						{
							final PdfPCell lineNO = new PdfPCell();
							lineNO.setBackgroundColor(COLOR_SKY_BLUE);
							lineNO.setPhrase(new Phrase(""));
							lineItemsTable.addCell(lineNO);
						}
						if (shippedItemBO.getDisplayPartNumber() != null)
						{
							final PdfPCell partNO = new PdfPCell();
							partNO.setBackgroundColor(COLOR_SKY_BLUE);
							partNO.setIndent(Element.ALIGN_CENTER);
							partNO.setPhrase(new Phrase("" + shippedItemBO.getDisplayPartNumber(), COLUMN_VALUE_FONT));
							lineItemsTable.addCell(partNO);
						}
						else
						{
							final PdfPCell partNO = new PdfPCell();
							partNO.setBackgroundColor(COLOR_SKY_BLUE);
							partNO.setPhrase(new Phrase("" + shippedItemBO.getPartNumber(), COLUMN_VALUE_FONT));
							lineItemsTable.addCell(partNO);
						}
						if (shippedItemBO.getDescription() != null)
						{
							final PdfPCell desciption = new PdfPCell();
							desciption.setBackgroundColor(COLOR_SKY_BLUE);
							desciption.setPhrase(new Phrase("" + shippedItemBO.getDescription(), COLUMN_VALUE_FONT));
							lineItemsTable.addCell(desciption);
						}
						else
						{
							final PdfPCell desciption = new PdfPCell();
							desciption.setBackgroundColor(COLOR_SKY_BLUE);
							desciption.setPhrase(new Phrase(""));
							lineItemsTable.addCell(desciption);
						}
						if (shippingUnitBO.getPackingSlip() != null)
						{
							final PdfPCell packSlips = new PdfPCell();
							packSlips.setBackgroundColor(COLOR_SKY_BLUE);
							packSlips.setIndent(Element.ALIGN_CENTER);
							packSlips.setPhrase(new Phrase("" + shippingUnitBO.getPackingSlip(), COLUMN_VALUE_FONT));
							lineItemsTable.addCell(packSlips);
						}
						else
						{
							final PdfPCell packSlips = new PdfPCell();
							packSlips.setBackgroundColor(COLOR_SKY_BLUE);
							packSlips.setPhrase(new Phrase(""));
							lineItemsTable.addCell(packSlips);
						}

						if (shippedItemBO.getOrderedQuantity() != 0)
						{
							final PdfPCell oQty = new PdfPCell();
							oQty.setBackgroundColor(COLOR_SKY_BLUE);
							oQty.setIndent(Element.ALIGN_CENTER);
							oQty.setPhrase(new Phrase("" + shippedItemBO.getOrderedQuantity(), COLUMN_VALUE_FONT));
							lineItemsTable.addCell(oQty);
						}
						else
						{
							final PdfPCell oQty = new PdfPCell();
							oQty.setBackgroundColor(COLOR_SKY_BLUE);
							oQty.setPhrase(new Phrase("0"));
							lineItemsTable.addCell(oQty);
						}
						if (shippedItemBO.getAssignedQuantity() != 0)
						{
							final PdfPCell aQty = new PdfPCell();
							aQty.setBackgroundColor(COLOR_SKY_BLUE);
							aQty.setIndent(Element.ALIGN_CENTER);
							aQty.setPhrase(new Phrase("" + shippedItemBO.getAssignedQuantity(), COLUMN_VALUE_FONT));
							lineItemsTable.addCell(aQty);
						}
						else
						{
							final PdfPCell aQty = new PdfPCell();
							aQty.setBackgroundColor(COLOR_SKY_BLUE);
							aQty.setPhrase(new Phrase("0"));
							lineItemsTable.addCell(aQty);
						}
						if (shippedItemBO.getShippedQuantity() != 0)
						{
							final PdfPCell sQty = new PdfPCell();
							sQty.setBackgroundColor(COLOR_SKY_BLUE);
							sQty.setIndent(Element.ALIGN_CENTER);
							sQty.setPhrase(new Phrase("" + shippedItemBO.getShippedQuantity(), COLUMN_VALUE_FONT));
							lineItemsTable.addCell(sQty);
						}
						else
						{
							final PdfPCell sQty = new PdfPCell();
							sQty.setBackgroundColor(COLOR_SKY_BLUE);
							sQty.setPhrase(new Phrase("0"));
							lineItemsTable.addCell(sQty);
						}
						if (shippedItemBO.getBackorderedQuantity() != 0)
						{
							final PdfPCell bQty = new PdfPCell();
							bQty.setBackgroundColor(COLOR_SKY_BLUE);
							bQty.setIndent(Element.ALIGN_CENTER);
							bQty.setPhrase(new Phrase("" + shippedItemBO.getBackorderedQuantity(), COLUMN_VALUE_FONT));
							lineItemsTable.addCell(bQty);
						}
						else
						{
							final PdfPCell bQty = new PdfPCell();
							bQty.setBackgroundColor(COLOR_SKY_BLUE);
							bQty.setIndent(Element.ALIGN_CENTER);
							bQty.setPhrase(new Phrase("0"));
							lineItemsTable.addCell(bQty);
						}


					}

				}
			}
		}

		//	doc = new com.lowagie.text.Document(PageSize.A4.rotate(), 36, 36, 54, 36);

		doc.setPageSize(PageSize.A4.rotate());
		doc.open();
		doc.newPage();
		doc.setMargins(50, 50, 65, 50);
		doc.add(billShipInfoTable);
		doc.add(generalInfoTable);
		doc.add(lineItemsTable);

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

	public static PdfPCell getCell(final Boolean isBorder, final String cellValue, final Integer colSpan, final Integer align,
			final Font font)
	{
		return getCell(isBorder, cellValue, colSpan, null, align, font);
	}

	public static PdfPCell getCell(final Boolean isBorder, final String cellValue, final Integer colSpan, final Float indent,
			final Integer align, final Font font)
	{
		final PdfPCell cell = getCell(isBorder, cellValue, font);
		cell.setColspan(colSpan);
		if (align != null)
		{
			cell.setHorizontalAlignment(align);
		}

		if (indent != null)
		{
			cell.setIndent(indent);
		}
		return cell;
	}

	public static PdfPCell getCell(final boolean isBorder, final String cellValue, final Font font)
	{
		final PdfPCell cell = new PdfPCell();
		if (!isBorder)
		{
			cell.setBorder(0);
		}
		cell.setPhrase(font != null ? new Phrase(cellValue, font) : new Phrase(cellValue, getDefaultFont()));
		return cell;
	}

	public static Font getDefaultFont()
	{
		return FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.NORMAL);
	}


}
