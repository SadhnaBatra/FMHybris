/**
 * 
 */
package com.federalmogul.core.process.uploadorder.action;

import de.hybris.platform.core.model.media.MediaModel;
import de.hybris.platform.core.model.user.CustomerModel;
import de.hybris.platform.processengine.action.AbstractSimpleDecisionAction;
import de.hybris.platform.processengine.model.BusinessProcessModel;
import de.hybris.platform.servicelayer.event.EventService;
import de.hybris.platform.servicelayer.media.MediaService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.task.RetryLaterException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.federalmogul.core.enums.UploadOrderStatus;
import com.federalmogul.core.event.FMUploadOrderEmailProcessEvent;
import com.federalmogul.core.model.UploadOrderProcessEmailNotificationModel;
import com.federalmogul.core.model.UploadOrderProcessModel;
import com.federalmogul.falcon.core.model.UploadOrderEntryModel;
import com.federalmogul.falcon.core.model.UploadOrderHistoryModel;
import com.federalmogul.falcon.core.model.UploadOrderModel;
import com.fmo.wom.common.exception.WOMExcelParseException;


/**
 * @author mamud
 * 
 */
public class UploadOrderEntryAction extends AbstractSimpleDecisionAction
{

	private static final Logger LOG = Logger.getLogger(UploadOrderEntryAction.class);

	final static String FILE_DELIMITERS = "[~;,]";
	final static String VALID_FILE_INPUT = "^.+[~;,][0-9]+$";
	final static String XLSXMIMEType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	final static String XLSMIMEType = "application/vnd.ms-excel";
	final static String TEXTMIMEType = "text/plain";
	final static String XLSSMIMEType = "application/excel";


	private static DataFormatter formatter = new DataFormatter();
	@Autowired
	private ModelService modelService;

	@Autowired
	private MediaService mediaService;

	@Autowired
	private EventService eventService;

	private ExcelFormat excelFormat;

	private FixedWidthFormat textFormat;

	/**
	 * @return the textFormat
	 */
	public FixedWidthFormat getTextFormat()
	{
		return textFormat;
	}

	/**
	 * @param textFormat
	 *           the textFormat to set
	 */
	public void setTextFormat(final FixedWidthFormat textFormat)
	{
		this.textFormat = textFormat;
	}

	public UploadOrderEntryAction(final String parserflag)
	{
		LOG.info("Inside Default constructor");
		if (parserflag != null && "PRODUCT_FLAG".equalsIgnoreCase(parserflag))
		{
			this.textFormat = FixedWidthFormat.PRODUCT_FLAG;
			this.excelFormat = ExcelFormat.PRODUCT_FLAG;
		}
		else
		{
			this.textFormat = FixedWidthFormat.NO_PRODUCT_FLAG;
			this.excelFormat = ExcelFormat.NO_PRODUCT_FLAG;
		}

	}

	ExcelFormat getExcelFormat()
	{
		return excelFormat;
	}

	protected int getNumberOfColumns()
	{
		return getExcelFormat().getNumberOfColumns();
	}

	protected String[] getColumnHeaderNames()
	{
		return getExcelFormat().getColumnHeaderNames();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hybris.platform.processengine.action.AbstractSimpleDecisionAction#executeAction(de.hybris.platform.processengine
	 * .model.BusinessProcessModel)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public Transition executeAction(final BusinessProcessModel bp) throws RetryLaterException, Exception
	{
		// YTODO Auto-generated method stub
		final UploadOrderProcessModel orderProcess = (UploadOrderProcessModel) bp;
		final UploadOrderModel orderModel = orderProcess.getOrder();
		final MediaModel uploadFileMedia = orderModel.getUploadOrderFile();
		final InputStream inputStream = mediaService.getStreamFromMedia(uploadFileMedia);
		if (orderModel.getUploadOrderStatus().equals(UploadOrderStatus.NEW))
		{
			LOG.info("uploadFileMedia.getMime() {}" + uploadFileMedia.getMime());
			if (uploadFileMedia.getMime().equalsIgnoreCase(XLSMIMEType) || uploadFileMedia.getMime().equalsIgnoreCase(XLSXMIMEType)
					|| uploadFileMedia.getMime().equalsIgnoreCase(XLSSMIMEType))
			{
				LOG.debug("file type is excel File" + uploadFileMedia.getMime());
				final Transition result = excelParser(inputStream, orderModel);
				if (result == Transition.NOK)
				{
					updateErrorStatus(orderModel, orderProcess);
				}
				else
				{
					updateSuccessStatus(orderModel);
				}
				return result;
			}
			if (uploadFileMedia.getMime().equalsIgnoreCase(TEXTMIMEType))
			{
				LOG.info("file type is excel File");
				final Transition result = textParser(inputStream, orderModel);
				if (result == Transition.NOK)
				{
					updateErrorStatus(orderModel, orderProcess);
				}
				else
				{
					updateSuccessStatus(orderModel);
				}
				return result;
			}
			updateErrorStatus(orderModel, orderProcess);
		}
		return Transition.OK;
	}

	private void updateErrorStatus(final UploadOrderModel orderModel, final UploadOrderProcessModel orderProcess)
	{
		orderModel.setUploadOrderStatus(UploadOrderStatus.FILEPARSEERROR);
		orderModel.setUpdatedTime(new Date());
		setHistory(orderModel, "OrderStatus", "FILEPARSEERROR", "FILEPARSEERROR", "Invalid File Format");
		modelService.save(orderModel);

		final UploadOrderProcessEmailNotificationModel uploadOrderEmail = new UploadOrderProcessEmailNotificationModel();
		uploadOrderEmail.setOrder(orderModel);
		uploadOrderEmail.setEmailId(orderModel.getUser().getUid());
		final FMUploadOrderEmailProcessEvent uploadOrderEmailEvent = new FMUploadOrderEmailProcessEvent(uploadOrderEmail);
		//LOG.info("Upload Order Email Event Starts");
		eventService.publishEvent(initializeEvent(uploadOrderEmailEvent, orderModel.getUser(), orderProcess));
	}

	private void updateSuccessStatus(final UploadOrderModel orderModel)
	{
		orderModel.setUploadOrderStatus(UploadOrderStatus.FILEPARSED);
		orderModel.setUpdatedTime(new Date());
		setHistory(orderModel, "OrderStatus", "FILEPARSED", "FILEPARSED", "Upload Order File Parsed");
		modelService.save(orderModel);
		modelService.refresh(orderModel);
	}

	protected void createUploadOrderEntry(final UploadOrderModel orderModel, final UploadOrderEntryModel entry, final int count)
	{
		final UploadOrderEntryModel uploadOrderEntryModel = modelService.create(UploadOrderEntryModel.class);
		uploadOrderEntryModel.setPartNumber(entry.getPartNumber());
		uploadOrderEntryModel.setPartFlag(entry.getPartFlag());
		uploadOrderEntryModel.setQuantity(entry.getQuantity());
		uploadOrderEntryModel.setOrder(orderModel);
		uploadOrderEntryModel.setAccountcode(orderModel.getShipToAccount());
		uploadOrderEntryModel.setCreatedTime(new Date());
		uploadOrderEntryModel.setUpdatedTime(new Date());
		uploadOrderEntryModel.setOrderentryNumber(orderModel.getCode() + "_" + count);
		modelService.save(uploadOrderEntryModel);
		modelService.refresh(uploadOrderEntryModel);
		setHistory(uploadOrderEntryModel, "uploadOrderEntryModel", "NA", "NEW Entry", "Part Added");
	}

	protected void setHistory(final UploadOrderModel orderModel, final String attribute, final String from, final String to,
			final String status)
	{
		final UploadOrderHistoryModel history = modelService.create(UploadOrderHistoryModel.class);
		history.setModifiedattribute(attribute);
		history.setUpdatedTime(new Date());
		history.setModifiedValueFrom(from);
		history.setModifiedValueTo(to);
		history.setOrder(orderModel);
		history.setStatus(status);
		modelService.save(history);
		modelService.save(orderModel);
		modelService.refresh(orderModel);
	}

	protected void setHistory(final UploadOrderEntryModel orderModel, final String attribute, final String from, final String to,
			final String status)
	{

		final UploadOrderHistoryModel history = modelService.create(UploadOrderHistoryModel.class);
		history.setModifiedattribute(attribute);
		history.setUpdatedTime(new Date());
		history.setModifiedValueFrom(from);
		history.setModifiedValueTo(to);
		history.setEntry(orderModel);
		history.setStatus(status);
		modelService.save(history);
		modelService.save(orderModel);
		modelService.refresh(orderModel);
	}

	private Transition excelParser(final InputStream inputStream, final UploadOrderModel orderModel)
	{
		boolean partFound = false;
		try
		{
			final Workbook wb = WorkbookFactory.create(inputStream);

			final int numberOfSheets = wb.getNumberOfSheets();
			if (numberOfSheets == 0)
			{
				return Transition.NOK;
			}
			for (int i = 0; i < numberOfSheets; i++)
			{
				try
				{
					final String result = createUploadedOrderEntryFromSheet(wb.getSheetAt(i), i, orderModel);
					if (result == null)
					{
						LOG.info("Sheet Error");
					}
					else
					{
						partFound = true;
					}
				}
				catch (final WOMExcelParseException e)
				{
					// YTODO Auto-generated catch block
					return Transition.NOK;
				}

			}
		}
		catch (final InvalidFormatException e)
		{
			// YTODO Auto-generated catch block
			return Transition.NOK;
		}
		catch (final IOException e)
		{
			// YTODO Auto-generated catch block
			return Transition.NOK;
		}
		if (partFound)
		{

			return Transition.OK;
		}
		else
		{

			return Transition.NOK;

		}
	}

	private Transition textParser(final InputStream inputStream, final UploadOrderModel orderModel)
	{
		try
		{
			final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String aLine;
			int count = 1;
			while ((aLine = bufferedReader.readLine()) != null)
			{

				if ("".equals(aLine.trim()))
				{
					continue;
				}

				if (aLine.startsWith("--"))
				{
					continue;
				}

				final UploadOrderEntryModel entry = createOrderEntryForText(aLine);
				if (isValidPart(entry))
				{
					createUploadOrderEntry(orderModel, entry, count);
				}
				else
				{
					createUploadOrderEntry(orderModel, entry, count);
					//return Transition.NOK;
				}
				count++;
			}
		}
		catch (final Exception e)
		{
			return Transition.NOK;
		}
		return Transition.OK;
	}

	private int getFirstRowIndex(final Sheet worksheet) throws WOMExcelParseException
	{

		// by default, we'll start at 0 unless the first row headers matches market defined constant values allowed.
		int firstRowIndex = 0;

		// check first row for column headers
		final Row firstRow = worksheet.getRow(0);

		// If first cell is null , throw format exception
		if (firstRow.getCell(0) == null)
		{
			LOG.error("Excel file formate error, please check upload file format");
			throw new WOMExcelParseException();
		}

		boolean firstRowIsHeaderNames = true;

		for (int i = 0; i < getNumberOfColumns(); i++)
		{
			final Cell aCell = firstRow.getCell(i);

			final String cellValue = getCellStringValue(aCell);
			if (!getColumnHeaderNames()[i].equalsIgnoreCase(cellValue))
			{
				// this did not match
				firstRowIsHeaderNames = false;
				break;
			}
		}

		if (firstRowIsHeaderNames)
		{
			LOG.debug("First row of this sheet is header names. Skipping");
			firstRowIndex = 1;
		}

		return firstRowIndex;
	}

	protected enum ExcelFormat
	{

		PRODUCT_FLAG(new String[]
		{ "Product Prefix", "Part Number", "Quantity" }, 0, 1, 2), NO_PRODUCT_FLAG(new String[]
		{ "Part", "Quantity" }, -99, 0, 1);

		private final String[] columnHeaders;
		private final int productFlagIndex;
		private final int partNumberIndex;
		private final int quantityIndex;

		private ExcelFormat(final String[] columnHeaders, final int productFlagIndex, final int partNumberIndex, final int quantity)
		{
			this.productFlagIndex = productFlagIndex;
			this.partNumberIndex = partNumberIndex;
			this.quantityIndex = quantity;
			this.columnHeaders = columnHeaders;
		}

		public int getOrderQuantityCellIndex()
		{
			return quantityIndex;
		}

		public int getPartNumberCellIndex()
		{
			return partNumberIndex;
		}

		public int getProductFlagCellIndex()
		{
			return productFlagIndex;
		}

		public String[] getColumnHeaderNames()
		{
			return columnHeaders;
		}

		public int getNumberOfColumns()
		{
			return columnHeaders.length;
		}


	}

	private String createUploadedOrderEntryFromSheet(final Sheet worksheet, final int sheetIndex, final UploadOrderModel orderModel)
			throws WOMExcelParseException
	{

		final String name = worksheet.getSheetName();
		LOG.info("Name ==>" + name);

		if (sheetIndex == 0)
		{
			if (worksheet.getRow(0) == null)
			{
				LOG.error("Excel file formate error, please check upload file format");
				return null;
			}
		}

		if (worksheet.getLastRowNum() == 0 && worksheet.getRow(0) == null)
		{
			return null;
		}

		final int firstRowIndex = getFirstRowIndex(worksheet);
		if (worksheet.getLastRowNum() == 0 && firstRowIndex == 1)
		{
			return null;
		}
		for (int rowIndex = firstRowIndex; rowIndex <= worksheet.getLastRowNum(); rowIndex++)
		{

			final Row row = worksheet.getRow(rowIndex);
			if (row == null)
			{
				continue;
			}
			if (row !=null){
				final int noOfindexColumns = worksheet.getRow(rowIndex).getLastCellNum();
				if (noOfindexColumns > 3)
				{
					return null;
				}
				}
			final UploadOrderEntryModel entry = createOrderEntryForExcel(row);
			if (isValidPart(entry))
			{
				createUploadOrderEntry(orderModel, entry, rowIndex);
			}
			
		}
		return "sucess";
	}

	protected boolean isValidPart(final UploadOrderEntryModel part)
	{
		return isValidPartNumber(part) && isValidQuantity(part);
	}

	protected boolean isValidPartNumber(final UploadOrderEntryModel part)
	{
		if (GenericValidator.isBlankOrNull(part.getPartNumber()))
		{
			return false;
		}
		return true;
	}

	protected boolean isValidQuantity(final UploadOrderEntryModel part)
	{
		final int quantity = part.getQuantity();

		if (quantity <= 0)
		{
			return false;
		}
		return true;
	}

	private String getCellValue(final Row row, final int cellIndex) throws WOMExcelParseException
	{
		String value = "";
		try
		{
			final int numberOfCells = row.getPhysicalNumberOfCells();

			if (cellIndex >= 0 && cellIndex < numberOfCells)
			{
				value = getCellStringValue(row.getCell(cellIndex)).toUpperCase();
			}
		}
		catch (final RuntimeException e)
		{
			final String message = "Excel Parse Error Row:" + row.getRowNum() + " Cell:" + cellIndex + ".  Error=" + e.getMessage();
			//getLogger().error(message);
			throw new WOMExcelParseException(message, e);
		}
		return value;
	}

	private String getCellStringValue(final Cell cell)
	{
		if (cell == null)
		{
			return "";
		}
		return formatter.formatCellValue(cell).trim();
	}

	protected UploadOrderEntryModel createOrderEntryForExcel(final Row row)
	{

		//final Row row = (Row) fileData;

		final UploadOrderEntryModel part = new UploadOrderEntryModel();
		try
		{
			part.setPartNumber(getCellValue(row, getExcelFormat().getPartNumberCellIndex()));
		}
		catch (final WOMExcelParseException e)
		{
			part.setPartNumber("Invalid Part Number");
		}

		try
		{
			final String quantity = getCellValue(row, getExcelFormat().getOrderQuantityCellIndex());
			if (quantity == null || quantity.isEmpty())
			{
				part.setQuantity(0);
			}
			else
			{
				part.setQuantity(Integer.parseInt(quantity));
			}

		}
		catch (final WOMExcelParseException e)
		{
			part.setQuantity(0);
		}
		catch (final Exception e)
		{
			part.setQuantity(0);
		}

		try
		{
			part.setPartFlag(getCellValue(row, getExcelFormat().getProductFlagCellIndex()));
		}
		catch (final WOMExcelParseException e)
		{
			part.setPartFlag("X");
		}
		return part;
	}

	protected UploadOrderEntryModel createOrderEntryForText(final String line)
	{

		//final Row row = (Row) fileData;

		final UploadOrderEntryModel part = new UploadOrderEntryModel();
		final String partNumber = getTextFormat().getPartNumber(line).trim().toUpperCase();
		final String quantity = getTextFormat().getQuantity(line).trim();
		final String productFlag = getTextFormat().getProductFlag(line).trim().toUpperCase();
		part.setPartNumber(partNumber);
		if (quantity == null || quantity.isEmpty())
		{
			part.setQuantity(0);
		}
		else
		{
			part.setQuantity(Integer.parseInt(quantity));
		}
		part.setPartFlag(productFlag);

		return part;
	}

	protected enum FixedWidthFormat
	{

		PRODUCT_FLAG(true, 0, 3, 4, 15, 20, 6), NO_PRODUCT_FLAG(false, 0, 0, 0, 20, 20, 5);

		private boolean productFlagSupported;
		private Field productFlag;
		private Field partNumber;
		private Field quantity;

		private FixedWidthFormat(final boolean productFlagSupported, final int productFlagStart, final int productFlagLength,
				final int partNumberStart, final int partNumberLength, final int quantityStart, final int quantityLength)
		{

			this.productFlagSupported = productFlagSupported;
			this.productFlag = new Field(productFlagStart, productFlagLength);
			this.partNumber = new Field(partNumberStart, partNumberLength);
			this.quantity = new Field(quantityStart, quantityLength);
		}


		public boolean isProductFlagSupported()
		{
			return productFlagSupported;
		}


		public String getPartNumber(final String line)
		{
			return partNumber.getValue(line);
		}

		public String getProductFlag(final String line)
		{
			return productFlag.getValue(line);
		}

		public String getQuantity(final String line)
		{
			return quantity.getValue(line);
		}

		public int getMaxLength()
		{
			return productFlag.getLength() + partNumber.getLength() + quantity.getLength();
		}


		private class Field
		{
			int start;
			int length;

			public Field(final int start, final int length)
			{
				this.start = start;
				this.length = length;
			}

			public int getStart()
			{
				return start;
			}

			public int getLength()
			{
				return length;
			}

			public String getValue(final String line)
			{

				// try to do some sanity checking here to avoid substring errors.
				// return nothing when these are encountered and let the validity checking handle it

				// if the length of the line is less than where we want to start == bad
				if (line.length() < getStart())
				{
					return "";
				}

				// if the length is shorter than the field length we want to get, use the shorter value
				final int endIndex = line.length() < getStart() + getLength() ? line.length() : getStart() + getLength();

				return line.substring(getStart(), endIndex);
			}
		}
	}

	public FMUploadOrderEmailProcessEvent initializeEvent(final FMUploadOrderEmailProcessEvent event,
			final CustomerModel customerModel, final UploadOrderProcessModel orderProcess)
	{
		try
		{
			event.setBaseStore(orderProcess.getStore());
			event.setSite(orderProcess.getSite());
			event.setCustomer(customerModel);
			event.setLanguage(orderProcess.getStore().getDefaultLanguage());
			event.setCurrency(orderProcess.getStore().getDefaultCurrency());
			return event;
		}
		catch (final Exception e)
		{
			LOG.error("Exception  ...." + e.getMessage());
		}
		return null;
	}
}