/**
 * 
 */
package com.federalmogul.storefront.export;

import de.hybris.platform.commercefacades.order.data.CartData;
import de.hybris.platform.commercefacades.order.data.OrderData;
import de.hybris.platform.commercefacades.order.data.OrderEntryData;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.core.Registry;
import de.hybris.platform.core.model.order.CartModel;
import de.hybris.platform.core.model.user.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import java.awt.Color;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.math.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.federalmogul.facades.order.data.DistrubtionCenterData;
import com.federalmogul.facades.order.data.FMTempPdfViewData;
import com.federalmogul.facades.product.data.FMCorporateData;
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
import com.federalmogul.core.product.PartService;
import com.federalmogul.falcon.core.model.FMPartModel;
import com.federalmogul.falcon.core.model.FMCorporateModel;


/**
 * @author ryadav93
 * 
 */
public class FMOrderConfirmationPdfBuilder extends AbstractPdfView
{
	private static final Logger LOG = Logger.getLogger(FMOrderConfirmationPdfBuilder.class);

	@Autowired
	private PartService partService;
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

		final FMTempPdfViewData globalDataPdf = (FMTempPdfViewData) model.get("globalData");
		final OrderData orderDetails = globalDataPdf.getOrderData();
		final CartModel cartModel = globalDataPdf.getCartModel();
		final UserModel user = cartModel.getUser();
		final String shitoLogin = globalDataPdf.getShipToLogin();

		String userName = user.getUid().split("@")[0].replace(".", ",");
		if (userName != null && userName.length() >= 20)
		{
			userName = userName.split(",")[0];
		}
		final AddressData shiptoAddress = globalDataPdf.getShipToAddress();
		final AddressData soldtoAddress = globalDataPdf.getSoldToAddress();
		final CartData cartdata = globalDataPdf.getCartData();
		final List<OrderEntryData> entries = globalDataPdf.getCartData().getEntries();
		LOG.info("ADDRESS LINE1" + shiptoAddress.getLine1());
		LOG.info("ADDRESS LINE2" + shiptoAddress.getLine2());
		LOG.info("UNIT ID SOLDTO" + globalDataPdf.getShipToUnit().getUid());
		LOG.info("ADDRESS LINE1 SHIPTO" + soldtoAddress.getLine1());
		LOG.info("ADDRESS LINE2 SHIPTO" + soldtoAddress.getLine2());
		LOG.info("UNIT ID SHIPTO" + globalDataPdf.getSoldToUnit().getUid());
		LOG.info("soldtoAddress.getRegion().getIsocodeShort()" + soldtoAddress.getRegion().getIsocodeShort());
		LOG.info("soldtoAddress.getPostalCode()" + soldtoAddress.getPostalCode());
		LOG.info("soldtoAddress.getCountry().getIsocode()" + soldtoAddress.getCountry().getIsocode());
		LOG.info("shiptoAddress.getRegion().getIsocodeShort()" + shiptoAddress.getRegion().getIsocodeShort());
		LOG.info("shiptoAddress.getPostalCode()" + shiptoAddress.getPostalCode());
		LOG.info("shiptoAddress.getCountry().getIsocode()" + shiptoAddress.getCountry().getIsocode());
		LOG.info("cartdata.getPurchaseOrderNumber()" + cartdata.getPurchaseOrderNumber());



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
		generalorderInformationTable.addCell(getCell(false, globalDataPdf.getSapOrderCode(), null, true));//need to verify..
		generalorderInformationTable.addCell(getCell(false, "Purchase Order #"));
		generalorderInformationTable.addCell(getCell(false, cartdata.getCustponumber(), null, true));
		generalorderInformationTable.addCell(getCell(false, "Order Date"));

		generalorderInformationTable.addCell(getCell(false, convertDate(orderDetails.getCreated()), null, true));
		generalorderInformationTable.addCell(getCell(false, "Order Type"));
		generalorderInformationTable.addCell(getCell(false, cartdata.getFmordertype(), null, true));
		generalorderInformationTable.addCell(getCell(false, "Order By"));
		generalorderInformationTable.addCell(getCell(false, userName, null, true));
		generalorderInformationTable.addCell(getCell(false, "Order Comments"));
		generalorderInformationTable.addCell(getCell(false, cartdata.getOrdercomments(), null, true));
		generalorderInformationTable.setSpacingAfter(15f);
		doc.add(generalorderInformationTable);




		doc.add(new Paragraph());


		final PdfPTable billToCellTable = new PdfPTable(1);

		final PdfPCell billto = getCell(true, "Bill To", 2, Element.ALIGN_CENTER, FONT_TIMES_ROMAN_BOLD_WHITE);
		billto.setBackgroundColor(Color.LIGHT_GRAY);
		billToCellTable.addCell(billto);
		final PdfPCell add1 = new PdfPCell();
		add1.setBorder(Rectangle.NO_BORDER);
		add1.setPhrase(new Phrase("" + globalDataPdf.getSoldToUnit().getName() != null ? globalDataPdf.getSoldToUnit().getName()
				: "", PANEL_TEXT_FONT_BOLD));
		billToCellTable.addCell(add1);

		final PdfPCell add2 = new PdfPCell();
		add2.setBorder(Rectangle.NO_BORDER);
		add2.setPhrase(new Phrase("" + soldtoAddress.getLine1() != null ? soldtoAddress.getLine1() : "", PANEL_TEXT_FONT_NORMAL));
		billToCellTable.addCell(add2);

		final PdfPCell add3 = new PdfPCell();
		add3.setBorder(Rectangle.NO_BORDER);
		add3.setPhrase(new Phrase("" + soldtoAddress.getLine2() != null ? soldtoAddress.getLine2() : "", PANEL_TEXT_FONT_NORMAL));
		billToCellTable.addCell(add3);


		final PdfPCell add4 = new PdfPCell();
		add4.setBorder(Rectangle.NO_BORDER);
		add4.setPhrase(new Phrase("" + soldtoAddress.getTown() != null ? soldtoAddress.getTown() : "", PANEL_TEXT_FONT_NORMAL));
		billToCellTable.addCell(add4);

		final PdfPCell add5 = new PdfPCell();
		String appendString = null;
		add5.setBorder(Rectangle.NO_BORDER);
		if (soldtoAddress.getRegion().getIsocodeShort() != null)
		{
			if (soldtoAddress.getPostalCode() != null)
			{
				appendString = soldtoAddress.getRegion().getIsocodeShort() + " " + soldtoAddress.getPostalCode();
			}
			else
			{
				appendString = soldtoAddress.getRegion().getIsocodeShort();
			}

		}
		else
		{
			if (soldtoAddress.getPostalCode() != null)
			{
				appendString = soldtoAddress.getPostalCode();
			}
			else
			{
				appendString = "";
			}
		}
		add5.setPhrase(new Phrase(appendString, PANEL_TEXT_FONT_NORMAL));
		billToCellTable.addCell(add5);

		final PdfPCell add6 = new PdfPCell();
		add6.setBorder(Rectangle.NO_BORDER);
		add6.setPhrase(new Phrase("" + soldtoAddress.getCountry().getIsocode(), PANEL_TEXT_FONT_NORMAL));
		billToCellTable.addCell(add6);

		final PdfPTable shipToCellTable = new PdfPTable(1);
		final PdfPCell shipto = getCell(true, "Ship To", 2, Element.ALIGN_CENTER, FONT_TIMES_ROMAN_BOLD_WHITE);
		shipto.setBackgroundColor(Color.LIGHT_GRAY);
		shipToCellTable.addCell(shipto);



		final PdfPCell add7 = new PdfPCell();
		add7.setBorder(Rectangle.NO_BORDER);
		add7.setPhrase(new Phrase("" + globalDataPdf.getShipToUnit().getName() != null ? globalDataPdf.getShipToUnit().getName()
				: "", PANEL_TEXT_FONT_BOLD));
		shipToCellTable.addCell(add7);

		final PdfPCell add8 = new PdfPCell();
		add8.setBorder(Rectangle.NO_BORDER);
		add8.setPhrase(new Phrase("" + shiptoAddress.getLine1() != null ? shiptoAddress.getLine1() : "", PANEL_TEXT_FONT_NORMAL));
		shipToCellTable.addCell(add8);

		final PdfPCell add9 = new PdfPCell();
		add9.setBorder(Rectangle.NO_BORDER);
		add9.setPhrase(new Phrase("" + shiptoAddress.getLine2() != null ? shiptoAddress.getLine2() : "", PANEL_TEXT_FONT_NORMAL));
		shipToCellTable.addCell(add9);

		final PdfPCell add10 = new PdfPCell();
		add10.setBorder(Rectangle.NO_BORDER);
		add10.setPhrase(new Phrase("" + shiptoAddress.getTown() != null ? shiptoAddress.getTown() : "", PANEL_TEXT_FONT_NORMAL));
		shipToCellTable.addCell(add10);

		if (shiptoAddress.getRegion().getIsocodeShort() != null)
		{
			if (shiptoAddress.getPostalCode() != null)
			{
				appendString = shiptoAddress.getRegion().getIsocodeShort() + " " + shiptoAddress.getPostalCode();
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
		final PdfPCell add11 = new PdfPCell();
		add11.setBorder(Rectangle.NO_BORDER);
		add11.setPhrase(new Phrase(appendString, PANEL_TEXT_FONT_NORMAL));
		shipToCellTable.addCell(add11);

		final PdfPCell add12 = new PdfPCell();
		add12.setBorder(Rectangle.NO_BORDER);
		add12.setPhrase(new Phrase("" + shiptoAddress.getCountry().getIsocode(), PANEL_TEXT_FONT_NORMAL));
		shipToCellTable.addCell(add12);


		final PdfPTable userAccountsTable = new PdfPTable(2);
		userAccountsTable.setHorizontalAlignment(Element.ALIGN_LEFT);
		final float[] widths1 =
		{ 250f, 250f };
		final Rectangle r1 = new Rectangle(PageSize.A4.getRight(72), PageSize.A4.getTop(72));
		userAccountsTable.setWidthPercentage(widths1, r1);
		userAccountsTable.addCell(billToCellTable);
		userAccountsTable.addCell(shipToCellTable);
		doc.add(userAccountsTable);

		final List<String> distrubtionCenter = getDistrubtionCenterList(entries);
		//final String dcName = "";


		for (final String dcString : distrubtionCenter)
		{
			boolean flag = false;
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

			shippingMethodTable.addCell(getCell(true, "Location", 1, Element.ALIGN_LEFT, getTableHeaderFont()));
			shippingMethodTable.addCell(getCell(true, "Carrier", 1, Element.ALIGN_LEFT, getTableHeaderFont()));
			shippingMethodTable.addCell(getCell(true, "Shipping Methods", 1, Element.ALIGN_LEFT, getTableHeaderFont()));
			for (final OrderEntryData entry : entries)
			{
				if (entry.getDistrubtionCenter() != null && entry.getDistrubtionCenter().size() > 0)
				{

					for (final DistrubtionCenterData dc : entry.getDistrubtionCenter())
					{
						if (dc.getDistrubtionCenterDataName().equals(dcString))
						{

							shippingMethodTable.addCell(getCell(true,
									"" + dc.getDistrubtionCenterDataName() != null ? dc.getDistrubtionCenterDataName() : "", null, true));
							shippingMethodTable.addCell(getCell(true, "" + dc.getCarrierDispName() != null ? dc.getCarrierDispName()
									: "", null, true));
							shippingMethodTable.addCell(getCell(true,
									"" + dc.getShippingMethodName() != null ? dc.getShippingMethodName() : "", null, true));
							flag = true;
							break;
						}
					}
				}
				if (flag)
				{
					break;
				}

			}

			shippingMethodTable.setSpacingAfter(15f);
			doc.add(shippingMethodTable);


			doc.add(new Paragraph());
			final PdfPTable partsOrderedTable = new PdfPTable(5);
			partsOrderedTable.setHorizontalAlignment(Element.ALIGN_LEFT);
			final float[] noLocationWidths1 =
			{ 80f, 180f, 80f, 80f, 80f };
			final Rectangle r4 = new Rectangle(PageSize.A4.getRight(72), PageSize.A4.getTop(72));
			partsOrderedTable.setWidthPercentage(noLocationWidths1, r4);


			final PdfPCell titleHdrCell2 = getCell(true, "Parts Ordered", 5, null, FONT_TIMES_ROMAN_BOLD_WHITE);
			titleHdrCell2.setBackgroundColor(Color.LIGHT_GRAY);

			partsOrderedTable.addCell(titleHdrCell2);

			partsOrderedTable.addCell(getCell(true, "Part #", 1, Element.ALIGN_LEFT, getTableHeaderFont()));

			partsOrderedTable.addCell(getCell(true, "Description", 1, Element.ALIGN_LEFT, getTableHeaderFont()));
			partsOrderedTable.addCell(getCell(true, "Qty", 1, Element.ALIGN_LEFT, getTableHeaderFont()));

			partsOrderedTable.addCell(getCell(true, "Price", 1, Element.ALIGN_LEFT, getTableHeaderFont()));
			partsOrderedTable.addCell(getCell(true, "Type", 1, Element.ALIGN_LEFT, getTableHeaderFont()));




			for (final OrderEntryData entry : entries)
			{

				if (entry.getDistrubtionCenter() != null && entry.getDistrubtionCenter().size() > 0)
				{

					for (final DistrubtionCenterData dc : entry.getDistrubtionCenter())
					{
						if (dc.getDistrubtionCenterDataName().equals(dcString))
						{
							LOG.info("Product code:::" + entry.getProduct().getCode());
							final ApplicationContext ctx = Registry.getApplicationContext();
							final PartService ps = ctx.getBean(PartService.class);
							final FMPartModel productModel = ps.getPartForCode(entry.getProduct().getCode());
							for (final FMCorporateModel corp : productModel.getCorporate())
							{
							LOG.info("corp"+corp);
								if ("dummy".equalsIgnoreCase(corp.getCorpcode()) || "FELPF".equalsIgnoreCase(corp.getCorpcode())
										|| "SPDPF".equalsIgnoreCase(corp.getCorpcode()) || "SLDPF".equalsIgnoreCase(corp.getCorpcode()))
								{
									LOG.info("in nabs part"+dc.getPartNumber());
									partsOrderedTable.addCell(getCell(true, "" + dc.getPartNumber() != null ? dc.getPartNumber() : "", null, true));
								}else{
									partsOrderedTable.addCell(getCell(true, "" + entry.getProduct().getCode() != null ? entry.getProduct()
											.getCode() : "", null, true));
								} 
							}					
							partsOrderedTable.addCell(getCell(true, "" + entry.getProduct().getName() != null ? entry.getProduct()
									.getName() : "", null, true));
							if (entry.getQuantity() > Long.parseLong(dc.getAvailableQTY())&& (Long.parseLong(dc.getAvailableQTY())>0 && dc.getBackorderFlag().equalsIgnoreCase("available")))
							{
                    LOG.info("in avilable quantity"+(entry.getProduct().getCode()));

								double price=0.0;
								double roundoffPrice=0.0;
								price=(Double.valueOf(dc.getAvailableQTY())*entry.getBasePrice().getValue().doubleValue());
                                                                roundoffPrice=Math.round(price * 100.0) / 100.0;
 								LOG.info("in roundoffPrice"+roundoffPrice);

								partsOrderedTable.addCell(getCell(true, "" + dc.getAvailableQTY().toString() != null ? dc
										.getAvailableQTY().toString() : "", null, true));
								partsOrderedTable.addCell(getCell(true, "" + (String.valueOf(roundoffPrice)) != null ? String.valueOf(roundoffPrice): "", null, true));
							}
							else
							{
								partsOrderedTable.addCell(getCell(true, "" + entry.getQuantity().toString() != null ? entry.getQuantity()
										.toString() : "", null, true));
								partsOrderedTable.addCell(getCell(true, "" + entry.getTotalPrice().getValue().toString() != null ? entry
										.getTotalPrice().getValue().toString() : "", null, true));
							}

							LOG.info("price type"+entry.getPriceType());
							if (null!=shitoLogin && shitoLogin.equals("yes"))
							{
								partsOrderedTable.addCell(getCell(true, "" + "N/A",null, true));
							}
							else 
							{
								partsOrderedTable.addCell(getCell(true, "" + entry.getPriceType() != null ? entry.getPriceType() : "",
										null, true));
							}
													
		
						}
					}
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


	public List<String> getDistrubtionCenterList(final List<OrderEntryData> entries)
	{

		final List<String> distrubtionCenterData = new ArrayList<String>();

		for (final OrderEntryData entry : entries)
		{
			if (entry.getDistrubtionCenter() != null && !entry.getDistrubtionCenter().isEmpty())
			{
				for (final DistrubtionCenterData dcentry : entry.getDistrubtionCenter())
				{
					LOG.info("Center :: " + dcentry.getDistrubtionCenterDataName());
					distrubtionCenterData.add(dcentry.getDistrubtionCenterDataName());
				}
			}

		}
		final ArrayList<String> distrubtionCenterList = removeDuplicates(distrubtionCenterData);
		return distrubtionCenterList;
	}

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
