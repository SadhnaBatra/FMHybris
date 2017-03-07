
package com.federalmogul.core.job;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.federalmogul.core.constants.FmCoreConstants;
import com.federalmogul.core.model.BrandInformationModel;

import de.hybris.platform.commerceservices.storefinder.StoreFinderService;
import de.hybris.platform.core.model.c2l.CountryModel;
import de.hybris.platform.core.model.c2l.RegionModel;
import de.hybris.platform.core.model.user.AddressModel;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;
import de.hybris.platform.servicelayer.exceptions.ModelNotFoundException;
import de.hybris.platform.servicelayer.i18n.CommonI18NService;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.store.services.BaseStoreService;
import de.hybris.platform.storelocator.model.PointOfServiceModel;
import de.hybris.platform.util.Config;


/**
 * @author SU276498
 * 
 */
public class WheretoBuyDataCronjob extends AbstractJobPerformable<CronJobModel>
{
	private static final Logger LOG = Logger.getLogger(WheretoBuyDataCronjob.class);

	public static final String WHERETOBUY_DATA_FILES_LOCATION = "wheretobuy.data.files.path";
	public static String TEMP_DIRECTORY;
	private static final String DELIMITER = ";";
	@Resource
	private ModelService modelService;
	@Resource
	private CommonI18NService commonI18NService;
	@Resource
	private FlexibleSearchService flexibleSearchService;
	@Resource
	private BaseStoreService baseStoreService;
	@Resource
	private StoreFinderService storeFinderService;


	/**
	 * 
	 */
	@Override
	public PerformResult perform(final CronJobModel cronJobModel)
	{
		LOG.info("******Where To buy Data STARTED ******");
		boolean isSucess = true;
		try
		{
			final List<String> filesToProcess = retrieveFiles();
			if (filesToProcess != null && !filesToProcess.isEmpty())
			{
				LOG.info(" Files to process :" + filesToProcess.size());
				File file = null;
				for (final String fileName : filesToProcess)
				{
					LOG.info(" Processing file name :" + fileName);
					file = new File(fileName);
					processFile(file);
					//file.renameTo(new File(processedDirectory + file.getName() + "_" + System.currentTimeMillis()));
				}
			}
			LOG.info("******Where To Buy Data COMPLETED ********");
		}
		catch (final Exception e)
		{
			LOG.error("Exception:" + e);
			isSucess=false;
			LOG.info("******ERROR IN Where to Buy Data Load ********");
		}
		return new PerformResult(isSucess?CronJobResult.SUCCESS: CronJobResult.FAILURE, isSucess?CronJobStatus.FINISHED:CronJobStatus.ABORTED);
	}

	/**
	 * For Retrieving files
	 * 
	 * @return
	 */
	private List<String> retrieveFiles()
	{
		List<String> fileNames = null;
		TEMP_DIRECTORY = Config.getParameter("wheretobuy.tempdirectory");
		LOG.info("In the retrieveFiles() method");
		final File inputDirectory = new File(Config.getParameter(WHERETOBUY_DATA_FILES_LOCATION));
		final String[] rawFileNames = inputDirectory.list();
		if (rawFileNames.length < 1)
		{
			LOG.info("No files to process");
			return fileNames;
		}
		fileNames = new ArrayList<String>();
		for (int i = 0; i < rawFileNames.length; i++)
		{
			fileNames.add(TEMP_DIRECTORY + rawFileNames[i]);
		}
		return fileNames;
	}

	/**
	 * 
	 * @param file
	 * @throws IOException
	 */
	private void processFile(final File file) throws IOException
	{
		BufferedReader fileReader = null;
		InputStreamReader isr = null;
		FileInputStream fis = null;
		BaseStoreModel baseStoreModel=baseStoreService.getBaseStoreForUid(Config.getParameter(FmCoreConstants.STORE_NAME));
		try
		{
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis);
			fileReader = new BufferedReader(isr);
			String line = null;

			while ((line = fileReader.readLine()) != null)
			{
				// extract contents
				final String[] contents = line.split(DELIMITER);
				try
				{				
					if (contents.length > 0)
					{
						PointOfServiceModel pointofServiceModel = null;
						AddressModel addressModel = null;
						final String name = contents[8] + " " + contents[3] + " " + contents[4] + " " + contents[6];
											
						try{
						  pointofServiceModel = storeFinderService.getPointOfServiceForName(baseStoreModel, name);
						}catch(ModelNotFoundException me) {
							LOG.info("POS not found for::"+name+" creating one::");
						}
						
						if(pointofServiceModel==null)
						{				
							pointofServiceModel = modelService.create(PointOfServiceModel.class);
							pointofServiceModel.setName(name);
							pointofServiceModel.setBaseStore(baseStoreModel);
							addressModel = modelService.create(AddressModel.class);
                            addressModel = populateAddressObject(addressModel, contents);
						    pointofServiceModel.setDisplayName(contents[8]);
						    pointofServiceModel.setType(de.hybris.platform.basecommerce.enums.PointOfServiceTypeEnum.STORE);
						   					   
						    final String address = addressModel.getStreetnumber() + addressModel.getStreetname() + " "
								+ addressModel.getPostalcode();
						    //final String latlang = FMUtils.makeLocation(address);
						   // final String[] latlangtude = latlang.split("_");
						    pointofServiceModel.setPreferredVendor(Boolean.valueOf(contents[9]));
						    pointofServiceModel.setLatitude(Double.parseDouble(contents[10]));
						    pointofServiceModel.setLongitude(Double.parseDouble(contents[11]));
						    pointofServiceModel.setShopType(contents[13]);
						    addressModel.setOwner(pointofServiceModel);
						    pointofServiceModel.setAddress(addressModel);
						    modelService.save(addressModel);
						 }  
						    String brand = contents[12];  
						    String partType = "";
						    if(contents.length==15)
						    {	
						    	partType=contents[14].trim();
						    }
						    String[] subBrandArray=null;
						    if(contents.length==16)
						    {	 
						    	partType=contents[14].trim();
						    	subBrandArray =	processSubBrandData(contents[15].trim());					    	
						    }
						    if(subBrandArray==null){
						    	processAndPopulateBrandInformationData(brand,partType,"",pointofServiceModel);
						    }else{
						    	for(int i=0;i<subBrandArray.length;i++){
						    		processAndPopulateBrandInformationData(brand,partType,subBrandArray[i],pointofServiceModel);
							    }					    	
						    }
						 
						LOG.info("Processed Record:::::::::::::::::::::::" + contents[0] + ";" + contents[1] + ";" + contents[2] + ";"
								+ contents[3] + ";" + contents[4] + ";" + contents[5] + ";" + contents[6] + ";" + contents[7] + ";"
								+ contents[8] + ";" + contents[9] + ";" + contents[10]);
					}

				}
				catch (final Exception e)
				{
					LOG.error("Failed Record:::::::::::::::::::::::" + contents[0] + ";" + contents[1] + ";" + contents[2] + ";"
							+ contents[3] + ";" + contents[4] + ";" + contents[5] + ";" + contents[6] + ";" + contents[7] + ";"
							+ contents[8] + ";" + contents[9] + ";" + contents[10]);
					LOG.error("Error occured during Saving the data to Model" + e);
				}

			}
		}
		finally
		{
			try
			{
				if (fileReader != null)
				{
					fileReader.close();
				}
				if (isr != null)
				{
					isr.close();
				}
				if (fis != null)
				{
					fis.close();
				}
			}
			catch (final IOException io)
			{
				LOG.error("Error occured during stream close" + io);
			}
		}
	}

	private String[] processSubBrandData(String subBrand){
		String[] subBrandList = subBrand.split(",");
		return subBrandList;
	}
	
	private void processAndPopulateBrandInformationData(String brand,String partType,String subBrand,PointOfServiceModel pointOfServiceModel){
		BrandInformationModel brandInformationModel= modelService.create(BrandInformationModel.class);
		brandInformationModel.setBrand(brand);
		if(StringUtils.isNotEmpty(partType))
		{		
		   brandInformationModel.setPartType(partType);
		}
		if(StringUtils.isNotEmpty(subBrand)){
           brandInformationModel.setSubBrand(subBrand);
		}
		brandInformationModel= flexibleSearchService.getModelByExample(brandInformationModel);			
		List<BrandInformationModel> brandInformationModelList = pointOfServiceModel.getBrandInformation();
		if(brandInformationModelList==null){
		    brandInformationModelList = new ArrayList<BrandInformationModel>();
		}else{
		    brandInformationModelList= new ArrayList<BrandInformationModel>(brandInformationModelList);
		}
		if(!brandInformationModelList.contains(brandInformationModel))
		{	
		    brandInformationModelList.add(brandInformationModel);
		}
		pointOfServiceModel.setBrandInformation(brandInformationModelList);					
	    modelService.save(pointOfServiceModel);
	}
	
	private AddressModel populateAddressObject(AddressModel addressModel,String contents[]){
		addressModel.setStreetname(contents[1]);
	    addressModel.setStreetnumber(contents[2]);
	    addressModel.setTown(contents[3]);

	    final String isocode = contents[5].substring(0, 2);
	    CountryModel countryModel = commonI18NService.getCountry(isocode);
	    addressModel.setCountry(countryModel);
	    RegionModel regionModel = commonI18NService.getRegion(countryModel, isocode+"-"+contents[4]);
	    addressModel.setRegion(regionModel);
	
	    final String zipCode = contents[6];
	    addressModel.setPostalcode(zipCode);
	    addressModel.setPhone1(contents[7]);
	    return addressModel;
	}

}
