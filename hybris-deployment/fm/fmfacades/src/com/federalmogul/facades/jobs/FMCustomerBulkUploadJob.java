/**
 * 
 */
package com.federalmogul.facades.jobs;

import de.hybris.platform.b2bacceleratorfacades.company.CompanyB2BCommerceFacade;
import de.hybris.platform.commercefacades.user.data.AddressData;
import de.hybris.platform.commercefacades.user.data.CountryData;
import de.hybris.platform.commercefacades.user.data.RegionData;
import de.hybris.platform.cronjob.enums.CronJobResult;
import de.hybris.platform.cronjob.enums.CronJobStatus;
import de.hybris.platform.cronjob.model.CronJobModel;
import de.hybris.platform.servicelayer.config.ConfigurationService;
import de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable;
import de.hybris.platform.servicelayer.cronjob.PerformResult;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Resource;
import javax.xml.soap.SOAPException;

import org.apache.commons.configuration.Configuration;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.federalmogul.core.enums.Fmusertype;
import com.federalmogul.facades.account.FMCustomerFacade;
import com.federalmogul.facades.user.data.FMCustomerData;


/**
 * @author SI279688
 * 
 */
public class FMCustomerBulkUploadJob extends AbstractJobPerformable<CronJobModel>
{
	@Autowired
	private ConfigurationService configurationService;



	@Resource(name = "b2bCommerceFacade")
	protected CompanyB2BCommerceFacade companyB2BCommerceFacade;

	@Autowired
	private FMCustomerFacade fmCustomerFacade;

	private static final String DELIMITER = ";";
	private String tempDirectory;
	private static final Logger LOG = Logger.getLogger(FMCustomerBulkUploadJob.class);
	private String inputDirectory;
	private int totalRows = 0;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.hybris.platform.servicelayer.cronjob.AbstractJobPerformable#perform(de.hybris.platform.cronjob.model.CronJobModel
	 * )
	 */
	@SuppressWarnings("resource")
	@Override
	public PerformResult perform(final CronJobModel arg0)
	{
		initialize();
		//get all the files from the FTP server
		//	final List<String> filesToProcess = retrieveFiles();

		LOG.info("b4 file");

		final File file = new File("//sharespace//bulkUserLoad.txt");
		LOG.info("after file");
		BufferedReader fileReader = null;
		InputStreamReader isr = null;
		FileInputStream fis = null;

		try
		{
			fis = new FileInputStream(file);
			isr = new InputStreamReader(fis);
			fileReader = new BufferedReader(isr);
			String line = null;
			String[] contents = null;
			try
			{
				while ((line = fileReader.readLine()) != null)
				{


					contents = line.split(DELIMITER);
					
					LOG.info("Creating User " + contents[0]);
					LOG.info("CONTENT[0]::" + contents[0]);
					LOG.info("CONTENT[1]::" + contents[1]);
					LOG.info("CONTENT[2]::" + contents[2]);
					LOG.info("CONTENT[3]::" + contents[3]);
					LOG.info("CONTENT[4]::" + contents[4]);
					LOG.info("CONTENT[5]::" + contents[5]);

					LOG.info("CONTENT[6]::" + contents[6]);

					totalRows++;
					final FMCustomerData fmcustomerdata = new FMCustomerData();
					fmcustomerdata.setUid(contents[0]);
					fmcustomerdata.setEmail(contents[0]);
					fmcustomerdata.setUserTypeDescription(Fmusertype.WAREHOUSEDISTRIBUTORCOMMERCIALVEHICLE);
					fmcustomerdata.setUnit(companyB2BCommerceFacade.getUnitForUid(contents[2]));
					fmcustomerdata.setPassword(contents[3]);
					fmcustomerdata.setFirstName(contents[4]);
					fmcustomerdata.setLastName(contents[5]);
					final String myRole = contents[6];
					final Collection<String> fmRoles = new ArrayList<String>();
					fmRoles.add(myRole);
					fmcustomerdata.setRoles(fmRoles);

					AddressData addressdata = (companyB2BCommerceFacade.getUnitForUid(contents[2])).getAddresses().get(0);
					if(addressdata.getPhone() == null){
						addressdata.setPhone("555-555-1212");
					}	

					fmcustomerdata.setDefaultShippingAddress(addressdata);

//					fmcustomerdata.setDefaultShippingAddress((companyB2BCommerceFacade.getUnitForUid(contents[2])).getAddresses().get(0));					
					fmcustomerdata.setNewsLetterSubscription(false);
					fmcustomerdata.setNewProductAlerts(false);
					fmcustomerdata.setPromotionInfoSubscription(false);
					fmcustomerdata.setTechAcademySubscription(false);
					fmcustomerdata.setLoyaltySignup(false);
					fmcustomerdata.setIsLoginDisabled(true);
					fmcustomerdata.setFromCronJob("fromCronJob");
					try
					{
						fmCustomerFacade.createCustomerAccount(fmcustomerdata);
					}
					catch (UnsupportedOperationException | ClassNotFoundException | SOAPException | IOException e)
					{ // YTODO Auto-generated catch block
						e.printStackTrace();
					}
					LOG.info("End Creating User " + contents[0]);
				}
			}
			catch (final IOException e)
			{
				// YTODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		catch (final FileNotFoundException e1)
		{
			// YTODO Auto-generated catch block
			e1.printStackTrace();
		}
		LOG.info("Completed Bulk User Import for " + totalRows);



		return new PerformResult(CronJobResult.SUCCESS, CronJobStatus.FINISHED);
	}


	/**
	 * Initializes all properties stored in the config/local.properties file
	 * 
	 */
	private void initialize()
	{

		LOG.info("intializing");
		final Configuration config = configurationService.getConfiguration();
		tempDirectory = config.getString("fm.customerupload.tempdirectory");
		inputDirectory = config.getString("fm.customerupload.inputdirectory");
		LOG.info("tempDirectory" + tempDirectory);
	}
}
