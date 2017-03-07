
package com.federalmogul.core.job;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.store.BaseStoreModel;
import de.hybris.platform.store.services.BaseStoreService;
import de.hybris.platform.storelocator.model.PointOfServiceModel;
import de.hybris.platform.util.Config;


/**
 * @author Bharadwaj
 * 
 */
public class WhereToBuyEMEAJob extends AbstractJobPerformable<CronJobModel>
{
	private static final Logger LOG = Logger.getLogger(WhereToBuyEMEAJob.class);

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
		BaseStoreModel baseStoreModel=baseStoreService.getBaseStoreForUid(Config.getParameter(FmCoreConstants.STORE_NAME_EMEA));
		deleteExistingData(baseStoreModel);
		try
		{
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis,"UTF-16");
			fileReader = new BufferedReader(isr);
			String line = null;
			PointOfServiceModel pointofServiceModel = null  ;
			AddressModel addressModel = null;
			List<BrandInformationModel> brandInformationModelList=null;
			String name=null;
			while ((line = fileReader.readLine()) != null)
			{
				final String[] contents = line.split(DELIMITER);
				try
				{				
					if (contents.length > 0)
					{
						name = contents[0];						
						try{
							if(null == pointofServiceModel || !name.equals(pointofServiceModel.getName()))
							 {	  
							   if(pointofServiceModel!=null){
							    	pointofServiceModel.setBrandInformation(brandInformationModelList);
							    	modelService.save(pointofServiceModel);
							   }
								pointofServiceModel = storeFinderService.getPointOfServiceForName(baseStoreModel, name);
							  }
							}catch(ModelNotFoundException me) {
								LOG.info("POS not found for::"+name+" creating one::");
								pointofServiceModel=null;
							}				
						if(pointofServiceModel==null)
						{				
							pointofServiceModel = modelService.create(PointOfServiceModel.class);
							pointofServiceModel.setName(contents[0]);
							pointofServiceModel.setBaseStore(baseStoreModel);
							pointofServiceModel.setDisplayName(contents[8]);
						    pointofServiceModel.setType(de.hybris.platform.basecommerce.enums.PointOfServiceTypeEnum.STORE);
						   //pointofServiceModel.setPreferredVendor(Boolean.valueOf(contents[9]));
						    pointofServiceModel.setLatitude(Double.parseDouble(contents[10]));
						    pointofServiceModel.setLongitude(Double.parseDouble(contents[11]));
						    pointofServiceModel.setShopType(contents[15]);
						   pointofServiceModel.setStoreWebsite(contents[12]);
						   pointofServiceModel.setActive((Boolean.valueOf(contents[13])));
							addressModel = modelService.create(AddressModel.class);
							try{
                            addressModel = populateAddressObject(addressModel, contents);
							}catch(Exception e){
								
							}	    
						    addressModel.setOwner(pointofServiceModel);
						    pointofServiceModel.setAddress(addressModel);
						    modelService.save(pointofServiceModel);   
						    addressModel=null;
						   brandInformationModelList= new LinkedList<BrandInformationModel>();
						 }  
						    String brand = contents[14];  
						    String partType = "";  
						   // partType=contents[14].trim();
						    String[] subBrandArray=null;
						   // partType=contents[14].trim();
						   // subBrandArray =	processSubBrandData(contents[15].trim());					    	
						   
						   // if(subBrandArray==null){
								    processAndPopulateBrandInformationData(brand,partType,"",brandInformationModelList);
							//}else{
								/*for(int i=0;i<subBrandArray.length;i++){
								    processAndPopulateBrandInformationData(brand,partType,subBrandArray[i],brandInformationModelList);
								 }					    	
							}		*/				 
						LOG.info("Processed Record:::::::::::::::::::::::" + name);
					}

				}
				catch (final Exception e)
				{
					LOG.error("Failed Record:::::::::::::::::::::::" +name);
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
	
	private void processAndPopulateBrandInformationData(String brand,String partType,String subBrand,List<BrandInformationModel> brandInformationModelList){
		BrandInformationModel brandInformationModel= modelService.create(BrandInformationModel.class);
		brandInformationModel.setBrand(brand);
		if(StringUtils.isNotEmpty(partType))
		{		
		   brandInformationModel.setPartType(partType);
		}
		if(StringUtils.isNotEmpty(subBrand)){
           brandInformationModel.setSubBrand(subBrand);
		}
		List<BrandInformationModel> resultSet= flexibleSearchService.getModelsByExample(brandInformationModel);	
		if(resultSet!=null)
		{	
		   for(BrandInformationModel bim:resultSet){
			   if(bim.getPartType()==null && bim.getSubBrand()==null){
		            brandInformationModelList.add(bim);
			   } 
		}				
	  }
	}
	
	private AddressModel populateAddressObject(AddressModel addressModel,String contents[]){
		if(StringUtils.isNotBlank(contents[1])){
		addressModel.setStreetname(contents[1]);
		}
		if(StringUtils.isNotBlank(contents[2]))
		{		
	      addressModel.setStreetnumber(contents[2]);
		}  
		if(StringUtils.isNotBlank(contents[3])){
		addressModel.setTown(contents[3]);
		}
	    //Temp code
	    String isocode=contents[5].trim();
	    isocode = isocode.substring(0, 2);
	    CountryModel countryModel = commonI18NService.getCountry(isocode);
	    addressModel.setCountry(countryModel);
	    
	    if(StringUtils.isNotBlank(contents[4]))
	    {	
	    RegionModel regionModel = commonI18NService.getRegion(countryModel, isocode+"-"+contents[4]);
	    addressModel.setRegion(regionModel);
	    }
	    if(StringUtils.isNotBlank(contents[6])){
	      addressModel.setPostalcode(contents[6]);
	    }
	    if(StringUtils.isNotBlank(contents[7])){
	    addressModel.setPhone1(contents[7]);
	    }
	    //additions for EMEA
	    addressModel.setLine3("");
	    addressModel.setCellphone("");
	    addressModel.setPhone2("");
	    if(StringUtils.isNotBlank(contents[17])){
	      addressModel.setEmail(contents[17]);
	    }
	    return addressModel;
	}
	
	private void deleteExistingData(BaseStoreModel baseStoreModel){
		String queryString="SELECT {pos:PK} FROM {PointofService AS pos} WHERE {pos.baseStore}=?baseStore";
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("baseStore",baseStoreModel);
		FlexibleSearchQuery query = new FlexibleSearchQuery(queryString);
		query.addQueryParameters(params);
		SearchResult<PointOfServiceModel> resultSet = flexibleSearchService.search(query);
		List<PointOfServiceModel> list=resultSet.getResult();
		for(PointOfServiceModel pos:list){
			if(null!=pos.getAddress())
			{	
			  modelService.remove(pos.getAddress());
			}  
			modelService.remove(pos);
		}
	}

}
