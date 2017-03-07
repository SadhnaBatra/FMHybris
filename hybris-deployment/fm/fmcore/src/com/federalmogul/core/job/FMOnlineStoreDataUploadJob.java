package com.federalmogul.core.job;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.federalmogul.core.constants.FmCoreConstants;
import com.federalmogul.core.model.BrandInformationModel;
import com.federalmogul.core.model.DistributorModel;
import com.federalmogul.core.model.OnlineRetailerModel;
import com.federalmogul.core.services.FMCommonServices;

import de.hybris.platform.core.model.c2l.CountryModel;
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
import de.hybris.platform.servicelayer.search.SearchResult;
import de.hybris.platform.util.Config;

public class FMOnlineStoreDataUploadJob extends AbstractJobPerformable<CronJobModel>{
	public static final String WHERETOBUY_DATA_FILES_LOCATION = "wheretobuy.data.files.path";
	private static final Logger LOG = Logger.getLogger(FMOnlineStoreDataUploadJob.class);
	private static final String DELIMITER = ";";
	public static String TEMP_DIRECTORY;
	@Resource
	private ModelService modelService;
	
	@Resource
	private CommonI18NService commonI18NService;
	
	@Resource
	private FlexibleSearchService flexibleSearchService;
	
	@Resource
	private FMCommonServices fmCommonServices;
	

	@Override
	public PerformResult perform(CronJobModel cronJob) {
		LOG.info("******Data upload started for**"+cronJob.getCode());
		boolean isSucess = true;
		try
		{
			final List<String> filesToProcess = retrieveFiles(cronJob.getCode());
			if (filesToProcess != null && !filesToProcess.isEmpty())
			{
				LOG.info(" Files to process :" + filesToProcess.size());
				File file = null;
				for (final String fileName : filesToProcess)
				{
					LOG.info(" Processing file name :" + fileName);
					file = new File(fileName);
					processFile(file,cronJob.getCode());
				}
			}
			LOG.info("******Data upload completed for**"+cronJob.getCode());
		}
		catch (final Exception e)
		{
			LOG.error("Exception:" + e);
			isSucess=false;
			LOG.info("******ERROR IN Data upload for***"+ cronJob.getCode());
		}
		return new PerformResult(isSucess?CronJobResult.SUCCESS: CronJobResult.FAILURE, isSucess?CronJobStatus.FINISHED:CronJobStatus.ABORTED);
	}
	
	/**
	 * For Retrieving files
	 * 
	 * @return
	 */
     private List<String> retrieveFiles(String code)
	{
		List<String> fileNames = null;
		String tempDirectory = getTempDirectory(code);
		LOG.info("In the retrieveFiles() method");
		final File inputDirectory = getFileInputDirectory(code);
		final String[] rawFileNames = inputDirectory.list();
		if (rawFileNames.length < 1)
		{
			LOG.info("No files to process");
			return fileNames;
		}
		fileNames = new ArrayList<String>();
		for (int i = 0; i < rawFileNames.length; i++)
		{
			fileNames.add(tempDirectory + rawFileNames[i]);
		}
		return fileNames;
	}
	
	private void processFile(final File file, String code) throws IOException
	{
		BufferedReader fileReader = null;
		InputStreamReader isr = null;
		FileInputStream fis = null;
		try
		{
			fis = new FileInputStream(file);
			//Some special Latin characters are not getting encoded by UTF-8/UTF-16. Changing the encoding pattern below.
			isr = new InputStreamReader(fis,"iso-8859-1");
			fileReader = new BufferedReader(isr);
			String line = null;
			String[] contents = null;
			//Removing call to delete method to allow incremental upload.Holistic fix will be implemented at a later date.Please invoke method here
			DistributorModel model = null;
			OnlineRetailerModel onRetModel=null;
			AddressModel addressModel = null;
			List<BrandInformationModel> brandInformationModelList=null;
			while ((line = fileReader.readLine()) != null)
			{
				// extract contents
				contents = line.split(DELIMITER);
				try
				{				
					if (contents.length > 0)
					{
						String uniqueId = contents[0];
						if(FmCoreConstants.ONLINE_RETAILER.equals(code))
						{	
							try{
								if(null == onRetModel || !uniqueId.equals(onRetModel.getCode()))
								 {	  
								   if(onRetModel!=null){
									   onRetModel.setBrandInfoOnlineRet(brandInformationModelList);
								    	modelService.save(onRetModel);
								    	onRetModel=null;
								   }
							  }
							}catch(ModelNotFoundException me) {
									LOG.info("POS not found for::"+uniqueId+" creating one::");
									onRetModel=null;
							}				  
							if(null==onRetModel) 
							{	
								onRetModel= modelService.create(OnlineRetailerModel.class);	  
								processDataOnlineRetailer(onRetModel,contents);
							  brandInformationModelList= new LinkedList<BrandInformationModel>();
							} 
														
							String brand = null;
							if(contents.length>8){
								brand = contents[8];
							}
						    String partType = "";  	    
						    processAndPopulateBrandInformationData(brand,partType,"",brandInformationModelList);
						}
						else if(FmCoreConstants.DISTRIBUTOR.equals(code)){
							try{
								if(null == model || !uniqueId.equals(model.getCode()))
								{	  
								   if(model!=null){
									   model.setBrandInfoDistributor(brandInformationModelList);
								    	modelService.save(model);
								    	model=null;
								   }
							    }
							    }catch(ModelNotFoundException me) {
									LOG.info("POS not found for::"+uniqueId+" creating one::");
									model=null;
								}				  
							   if(null==model) 
							   {	
							    model= modelService.create(DistributorModel.class);	  
							    processDataDistributor(model,contents);
							    brandInformationModelList= new LinkedList<BrandInformationModel>();
							   } 						
							   String brand = null;
							   if(contents.length>8){
								  brand = contents[8];
							   }
					           String partType = "";  
					           processAndPopulateBrandInformationData(brand,partType,"",brandInformationModelList);					   				 
						}
					}	
				}
				catch (final Exception e)
				{
					LOG.error("Failed Record:::::::::::::::::::::::" + contents[0] + ";" + contents[1] + ";" + contents[2] + ";"
							+ contents[3] + ";" + contents[4] + ";" + contents[5] + ";" + contents[6]);
					LOG.error("Error occured during Saving the data to Model" + e);
				}
			}
		  }catch(Exception e){
			LOG.error("some i/o exception");
		  }finally{
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

		}

		private File getFileInputDirectory(String code){
			File inputDirectory=null;
			if(FmCoreConstants.ONLINE_RETAILER.equals(code)){
				inputDirectory = new File(Config.getParameter(FmCoreConstants.ONLINE_RETAILER_FILE_LOCATION));
			}else if(FmCoreConstants.DISTRIBUTOR.equals(code)){
				inputDirectory = new File(Config.getParameter(FmCoreConstants.DISTRIBUTOR_FILE_LOCATION));
			}
			return inputDirectory;
		}
		
		private String getTempDirectory(String code){
			String tempDirectory=null;
			if(FmCoreConstants.ONLINE_RETAILER.equals(code)){
				tempDirectory = Config.getParameter(FmCoreConstants.ONLINE_RETAILER_TEMP_LOCATION);
			}else if(FmCoreConstants.DISTRIBUTOR.equals(code)){
				tempDirectory = Config.getParameter(FmCoreConstants.DISTRIBUTOR_FILE_TEMP_LOCATION);
			}
			return tempDirectory;
		}
		
		
		private void processDataOnlineRetailer(OnlineRetailerModel object,String[] contents){			  
			object.setCode(contents[0]);
			object.setOnlineRetailerName(contents[1]);
			final String isocode = contents[4].substring(0, 2);
			CountryModel countryModel = commonI18NService.getCountry(isocode);
			object.setCountry(countryModel);
			//Address not there for online Retailer -- A common method should be created for Address
			//AddressModel address = modelService.create(AddressModel.class);
			/*address.setLine1(contents[2]);
			address.setTown(contents[3]);
		    address.setPhone1(contents[6]);
		    address.setPostalcode(contents[5]);
			address.setOwner(object);
			object.setAddress(address);*/
			if(StringUtils.isNotBlank(contents[7]))
			object.setStoreWebsite(contents[7]);
			if(contents.length==13)
			{	
				if(StringUtils.isNotBlank(contents[12]))
				object.setEmail(contents[12]);
			}
			//object.setBrandInfoOnlineRet(processAndValidateBrandInfo(contents[9]));
			modelService.save(object);			 
		}
		
		private void processDataDistributor(DistributorModel object,String[] contents){			  
			object.setCode(contents[0]);
			object.setDistributorName(contents[1]);
			AddressModel address = modelService.create(AddressModel.class);
			address.setLine1(contents[2]);
			address.setTown(contents[3]);
		    final String isocode = contents[4].substring(0, 2);
			CountryModel countryModel = commonI18NService.getCountry(isocode);
			address.setCountry(countryModel);
			object.setCountry(countryModel);
		    address.setPhone1(contents[6]);
			address.setPostalcode(contents[5]);
			address.setOwner(object);
			object.setAddress(address);
			if(StringUtils.isNotBlank(contents[7]))
			object.setStoreWebsite(contents[7]);
			if(contents.length==13)
			{	
				if(StringUtils.isNotBlank(contents[12]))
				object.setEmail(contents[12]);
			}
			//object.setBrandInfoDistributor(processAndValidateBrandInfo(contents[9]));
			modelService.save(object);			 
		}
		
		private void deleteExistingData(String code){
			String query=null;
			if(FmCoreConstants.ONLINE_RETAILER.equals(code))
			{	
				  query = "Select {PK} from {OnlineRetailer}";
				 
			}	else if(FmCoreConstants.DISTRIBUTOR.equals(code)){
				query = "Select {PK} from {Distributor}";
			}
			SearchResult<Object> resultSet = flexibleSearchService.search(query);
			List<Object> list=resultSet.getResult();
			modelService.removeAll(list);
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
		

}
