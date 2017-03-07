/**
 * 
 */
package com.federalmogul.storefront.export;

import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.user.data.AddressData;

import java.awt.Color;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.federalmogul.facades.order.data.FMTempLoyaltyPdfViewData;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;


/**
 * @author SR279690
 * 
 */
public class FMLoyaltyOrderConfirmationPdfBuilder extends AbstractPdfView
{

	private static final Logger LOG = Logger.getLogger(FMLoyaltyOrderConfirmationPdfBuilder.class);
	private static final Color COLOR_DARK_BLUE = new Color(9, 10, 154);
	private static final Color COLOR_WHITE = new Color(255, 255, 255);
	//private static final Color COLOR_BLACK = new Color(0, 0, 0);
	private static final Color COLOR_SKY_BLUE = new Color(204, 221, 255);

	private static final Font PANEL_TEXT_FONT_BOLD = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD, COLOR_DARK_BLUE);


	//private static final Font COLUMN_VALUE_FONT_BOLD = FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, COLOR_BLACK);

	private static final Font FONT_TIMES_ROMAN_BOLD_WHITE = FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, Font.BOLD,
			COLOR_WHITE);
	private static final Font PANEL_TEXT_FONT_NORMAL = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, COLOR_DARK_BLUE);




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

		doc.open();
		doc.setPageSize(PageSize.A4.rotate());
		doc.newPage();
		doc.setMargins(50, 50, 65, 50);
		final FMTempLoyaltyPdfViewData globalDataPdf = ( FMTempLoyaltyPdfViewData) model.get("globalData");
		
		
		
		final AddressData shiptoAddress = globalDataPdf.getShipToAddress();
		final AddressData soldtoAddress = globalDataPdf.getSoldToAddress();
		final CartData cartdata = globalDataPdf.getCartData();
		final List<OrderEntryData> entries = globalDataPdf.getCartData().getEntries();



		doc.add(new Paragraph(" "));
		final PdfPTable generalorderInformationTable = new PdfPTable(2);
		generalorderInformationTable.setHorizontalAlignment(Element.ALIGN_LEFT);
		final float[] widths =
		{ 100f, 400f };
		final Rectangle r = new Rectangle(PageSize.A4.getRight(72), PageSize.A4.getTop(72));
		generalorderInformationTable.setWidthPercentage(widths, r);

		final PdfPCell titleHdrCell = getCell(true, "General Order Information", 2, null, FONT_TIMES_ROMAN_BOLD_WHITE);
		titleHdrCell.setBackgroundColor(Color.LIGHT_GRAY);

		generalorderInformationTable.addCell(titleHdrCell);


		generalorderInformationTable.addCell(getCell(false, "Confirmation #"));
		generalorderInformationTable.addCell(getCell(false, globalDataPdf.getCrmOrderConfNo(), null, true));//need to verify..
		//generalorderInformationTable.addCell(getCell(false, "Purchase Order #"));
		//generalorderInformationTable.addCell(getCell(false, "", null, true));
		generalorderInformationTable.addCell(getCell(false, "Order Date"));

		generalorderInformationTable.addCell(getCell(false, convertDate(globalDataPdf.getOrderData().getCreated()), null, true));
		//generalorderInformationTable.addCell(getCell(false, "Order Type"));
		//generalorderInformationTable.addCell(getCell(false, "", null, true));
		generalorderInformationTable.addCell(getCell(false, "Order By"));
		generalorderInformationTable.addCell(getCell(false, globalDataPdf.getFmCustomerData().getName(), null, true));
		//generalorderInformationTable.addCell(getCell(false, "Order Comments"));
		//generalorderInformationTable.addCell(getCell(false, "", null, true));
		generalorderInformationTable.setSpacingAfter(15f);
		doc.add(generalorderInformationTable);






		final PdfPTable userAccountsTable = new PdfPTable(2);
		userAccountsTable.setHorizontalAlignment(Element.ALIGN_LEFT);
		final float[] widths1 =
		{ 250f, 250f };
		final Rectangle r1 = new Rectangle(PageSize.A4.getRight(72), PageSize.A4.getTop(72));
		userAccountsTable.setWidthPercentage(widths1, r1);

		doc.add(userAccountsTable);

		// final List<String> distrubtionCenter = getDistrubtionCenterList(entries); //final String dcName = "";



		doc.add(new Paragraph(" "));





		final PdfPTable shippingMethodTable = new PdfPTable(3);
		shippingMethodTable.setHorizontalAlignment(Element.ALIGN_LEFT);
		final float[] widths2 =
		{ 166f, 166f, 168f };
		final Rectangle r2 = new Rectangle(PageSize.A4.getRight(72), PageSize.A4.getTop(72));
		shippingMethodTable.setWidthPercentage(widths2, r2);

		final PdfPCell titleHdrCell1 = getCell(true, "Shipping Methods", 3, null, FONT_TIMES_ROMAN_BOLD_WHITE);
		titleHdrCell1.setBackgroundColor(Color.LIGHT_GRAY);

		shippingMethodTable.addCell(titleHdrCell1);

		shippingMethodTable.addCell(getCell(true, "Shipping Details",1, Element.ALIGN_LEFT, getTableHeaderFont()));
		shippingMethodTable.addCell(getCell(true, "Redeem Points", 1, Element.ALIGN_LEFT, getTableHeaderFont()));
		shippingMethodTable.addCell(getCell(true, "Available Points", 1, Element.ALIGN_LEFT, getTableHeaderFont()));

		String appendString = null;
		if (shiptoAddress != null)
		{
			if (shiptoAddress.getRegion() != null)
			{
				if (shiptoAddress.getRegion().getIsocodeShort() != null || shiptoAddress.getRegion().getIsocode() != null)
				{
					if (shiptoAddress.getPostalCode() != null)
					{
						if (shiptoAddress.getRegion().getIsocodeShort() != null)
						{
							appendString = shiptoAddress.getRegion().getIsocodeShort() + " " + shiptoAddress.getPostalCode();
						}
						else if (shiptoAddress.getRegion().getIsocode() != null)
						{
							appendString = shiptoAddress.getRegion().getIsocode() + " " + shiptoAddress.getPostalCode();
						}
					}
					else
					{
						appendString = shiptoAddress.getRegion().getIsocodeShort();
					}

				}
				else
				{
					if (shiptoAddress.getPostalCode() != null)
					{
						appendString = shiptoAddress.getPostalCode();
					}
					else
					{
						appendString = "";
					}
				}
			}
		}
		else
		{
			appendString = "";
		}
		if (shiptoAddress != null)
		{
			final String address = shiptoAddress.getLine1() + shiptoAddress.getTown() + appendString
					+ shiptoAddress.getCountry().getIsocode();


			shippingMethodTable.addCell(getCell(true, address, null, true));

		}


		shippingMethodTable.addCell(getCell(true, "" + globalDataPdf.getRedeemPoints(), null, true));
		shippingMethodTable.addCell(getCell(true,
				"" + (Integer.parseInt(globalDataPdf.getAvailablePoints()) - Integer.parseInt(globalDataPdf.getRedeemPoints())),
				null, true));



		shippingMethodTable.setSpacingAfter(15f);
		doc.add(shippingMethodTable);


		doc.add(new Paragraph());
		final PdfPTable partsOrderedTable = new PdfPTable(5);
		partsOrderedTable.setHorizontalAlignment(Element.ALIGN_LEFT);
		final float[] noLocationWidths1 =
		{ 80f, 180f, 80f, 80f, 80f };
		final Rectangle r4 = new Rectangle(PageSize.A4.getRight(72), PageSize.A4.getTop(72));
		partsOrderedTable.setWidthPercentage(noLocationWidths1, r4);


		final PdfPCell titleHdrCell2 = getCell(true, "Products Purchased", 5, null, FONT_TIMES_ROMAN_BOLD_WHITE);
		titleHdrCell2.setBackgroundColor(Color.LIGHT_GRAY);

		partsOrderedTable.addCell(titleHdrCell2);

		partsOrderedTable.addCell(getCell(true, "Product#", 1, Element.ALIGN_LEFT, getTableHeaderFont()));

		partsOrderedTable.addCell(getCell(true, "Description", 1, Element.ALIGN_LEFT, getTableHeaderFont()));
		partsOrderedTable.addCell(getCell(true, "Points", 1, Element.ALIGN_LEFT, getTableHeaderFont()));

		partsOrderedTable.addCell(getCell(true, "Qty", 1, Element.ALIGN_LEFT, getTableHeaderFont()));
		partsOrderedTable.addCell(getCell(true, "Total Points", 1, Element.ALIGN_LEFT, getTableHeaderFont()));


		if (entries != null && !entries.isEmpty())
		{
			for (final OrderEntryData entry : entries)
			{
				partsOrderedTable.addCell(getCell(true, "" + entry.getProduct().getCode(), null, true));
				partsOrderedTable.addCell(getCell(true, "" + entry.getProduct().getName(), null, true));
				partsOrderedTable.addCell(getCell(true, "" + entry.getProduct().getLoyaltyPoints(), null, true));
				/*
				 * totalReedemPoints = ((totalReedemPoints + Double.parseDouble(entry.getProduct().getLoyaltyPoints())) *
				 * entry .getQuantity());
				 */
				partsOrderedTable.addCell(getCell(true, "" + entry.getQuantity(), null, true));
				if (entry.getProduct().getLoyaltyPoints() != null)
				{
					partsOrderedTable.addCell(getCell(true,
							"" + Integer.parseInt(entry.getProduct().getLoyaltyPoints()) * entry.getQuantity(), null, true));
				}


			}

			doc.add(partsOrderedTable);
		}
		doc.close();

	}

	public final String convertDate(final Date date)
	{


		final DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		final String s = df.format(date);
		return s;
	}



	public static PdfPCell getCell(final Boolean isBorder, final String cellValue, final Integer colSpan, final Integer align,
			final Font font)
	{
		return getCell(isBorder, cellValue, colSpan, null, align, font);
	}

	public static PdfPCell getCell(final Boolean isBorder, final String cellValue, final Integer colSpan, final Float indent,
			final Integer align, final Font font)
	{
		final PdfPCell cell = getCell(isBorder, cellValue, font, false);
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

	public static PdfPCell getCell(final boolean isBorder, final String cellValue, final Font font, final boolean color)
	{
		final PdfPCell cell = new PdfPCell();
		if (!isBorder)
		{
			cell.setBorder(0);
		}
		if (color)
		{
			cell.setBackgroundColor(COLOR_SKY_BLUE);

		}

		cell.setPhrase(font != null ? new Phrase(cellValue, font) : new Phrase(cellValue, getDefaultFont()));

		return cell;
	}

	public static Font getDefaultFont()
	{
		return FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.NORMAL);
	}

	public static PdfPCell getCell(final boolean isBorder, final String cellValue)
	{
		return getCell(isBorder, cellValue, null, false);
	}

	public static Font getTableHeaderFont()
	{
		return new Font(FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, Font.BOLD, new Color(13, 64, 168)));
	}


	/*
	 * public List<String> getDistrubtionCenterList(final List<OrderEntryData> entries) {
	 * 
	 * final List<String> distrubtionCenterData = new ArrayList<String>();
	 * 
	 * for (final OrderEntryData entry : entries) { if (entry.getDistrubtionCenter() != null &&
	 * !entry.getDistrubtionCenter().isEmpty()) { for (final DistrubtionCenterData dcentry :
	 * entry.getDistrubtionCenter()) { LOG.info("Center :: " + dcentry.getDistrubtionCenterDataName());
	 * distrubtionCenterData.add(dcentry.getDistrubtionCenterDataName()); } }
	 * 
	 * } final ArrayList<String> distrubtionCenterList = removeDuplicates(distrubtionCenterData); return
	 * distrubtionCenterList; }
	 */

	public ArrayList<String> removeDuplicates(final List<String> list)
	{

		final ArrayList<String> result = new ArrayList<>();

		final HashSet<String> set = new HashSet<>();

		for (final String item : list)
		{
			if (!set.contains(item))
			{
				result.add(item);
				set.add(item);
			}
		}
		return result;
	}

	public String getDCDateformat(final Date date)
	{
		final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String rdate = null;
		try
		{
			rdate = sdf.format(date);
		}
		catch (final Exception e)
		{
			rdate = sdf.format(new Date());
		}
		return rdate;
	}


}
