package com.fmo.wom.transformation.util;

import java.io.File;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.rmi.server.UID;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.URIResolver;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.ResourceUtils;
import org.xml.sax.InputSource;

import com.fmo.wom.common.exception.WOMIPOTransformationException;
import com.fmo.wom.common.exception.WOMResourceException;
import com.fmo.wom.common.util.DateUtil;
import com.fmo.wom.common.util.PropertiesUtil;
import com.fmo.wom.common.util.StringUtil;


public class XSLTransformer
{
	private static Logger logger = Logger.getLogger(XSLTransformer.class);
	static TransformerFactory factory = null;
	public static ClassLoader classLoader = XSLTransformer.class.getClassLoader();
	static Map<String, Transformer> xslTransformers = new HashMap<String, Transformer>();
	static Map<String, URL> xslURLMap = new HashMap<String, URL>();
	static
	{
		factory = TransformerFactory.newInstance();
		//		loadXslFilesFromClasspath();
		//		loadFilesFromClasspath("rfq");
		//		loadFilesFromClasspath("aq");
		loadFilesFromClasspath("confirm");
		loadFilesFromClasspath("incoming");
		loadFilesFromClasspath("outgoing");
		loadFilesFromClasspath("getshipment");
		loadFilesFromClasspath("showshipment");
		loadFileFromClasspath("RemoveNamespaces.xsl");
		loadFileFromClasspath("IpoVersion.xsl");
		loadFileFromClasspath("SecretKey.xsl");
		loadFileFromClasspath("BillToAccountCode.xsl");
	}

	public static Map<String, Transformer> getXslTransformers()
	{
		return xslTransformers;
	}

	public static void loadXslFilesFromClasspath()
	{
		try
		{
			final PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(classLoader);
			final Resource[] resources = resolver.getResources("/**/*.xsl");
			if (resources == null)
			{
				return;
			}
			Resource resource;
			for (int i = 0; i < resources.length; i++)
			{
				resource = resources[i];
				final InputStream inputStream = resource.getInputStream();
				createTransformer(inputStream, resource.getDescription());
			}
		}
		catch (final Exception e)
		{
			logger.warn("Unable to load files for reason ", e);
		}

	}

	public static void main(final String[] args)
	{
		try
		{
			final PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(classLoader);
			final Resource[] resources = resolver.getResources("/**/*.xsl");
			if (resources == null)
			{
				return;
			}
			Resource resource;
			for (int i = 0; i < resources.length; i++)
			{
				resource = resources[i];
			}
		}
		catch (final Exception e)
		{
			System.out.println("Exception ");
			e.printStackTrace();
		}
	}

	/**
	 * Load one file and create Transformer for it. Place in static xslTransformers with file key
	 * 
	 * @param aFile
	 */
	public static void loadFileFromClasspath(final String aFile)
	{
		try
		{
			final PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(classLoader);
			final Resource resource = resolver.getResource(aFile);
			if (resource == null)
			{
				return;
			}
			final InputStream inputStream = resource.getInputStream();
			createTransformer(inputStream, resource.getDescription());
		}
		catch (final Exception e)
		{
			logger.warn("Unable to load file " + aFile, e);
		}

	}

	/**
	 * Load all xsl files under the given directory in the classpath and create a Transformer for each. Place in static
	 * xslTransformers with key as the full path and file
	 * 
	 * @param dirFile
	 */
	public static void loadFilesFromClasspath(final String dirFile)
	{

		// You can uncomment this for local 
		// but DO NOT CHECK IT IN UNCOMMENTED
		//	    if("true".equals("true")){
		//			loadFilesFromClasspathLocalEnv(dirFile);
		//			return; 
		//		}
		try
		{
			logger.info("loading files from " + dirFile);
			final PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(classLoader);
			final Resource[] resources = resolver.getResources(dirFile + "/**/*.xsl");
			if (resources == null)
			{
				return;
			}
			Resource resource;
			for (int i = 0; i < resources.length; i++)
			{
				resource = resources[i];
				logger.info("found a file to load: " + resource.getFilename());

				final URL url = resource.getURL();
				if (ResourceUtils.isJarURL(url))
				{
					final InputStream inputStream = resource.getInputStream();
					createTransformer(inputStream, resource.getDescription());
				}
				else
				{
					InputStream inputStream = classLoader.getResourceAsStream(resource.getFilename());
					if (inputStream == null)
					{
						inputStream = resource.getInputStream();
					}
					createTransformer(inputStream, resource.getDescription(), resource);
				}
			}
		}
		catch (final Exception e)
		{
			logger.warn("Unable to load files from " + dirFile + " :reason: ", e);
		}

	}

	private static void createTransformer(final InputStream inputStream, final String description)
	{

		logger.info("crdeating transformer for: " + description);

		final String xslFile = description.substring(description.indexOf("[") + 1, description.indexOf("]"));
		if (!xslFile.endsWith(".xsl"))
		{
			return;
		}
		final Source source = new SAXSource(new InputSource(inputStream));
		final URL resourceUrl = classLoader.getResource(xslFile);
		if (resourceUrl != null)
		{
			source.setSystemId(resourceUrl.toExternalForm());
			createTransformer(xslFile, source);
			xslURLMap.put(xslFile, resourceUrl);
		}
	}

	private static void createTransformer(final InputStream inputStream, final String description, final Resource resourse)
			throws Exception
	{

		logger.info("crdeating transformer for: " + description);

		String xslFile = description.substring(description.indexOf("[") + 1, description.indexOf("]"));
		if (!xslFile.endsWith(".xsl"))
		{
			return;
		}
		final String webInfFldr = "WEB-INF";
		final String classesFldr = "classes";

		if (xslFile.contains(webInfFldr) || xslFile.contains(classesFldr))
		{
			String tmpXSLFile = "";
			if (xslFile.contains(webInfFldr))
			{
				tmpXSLFile = xslFile.substring(xslFile.indexOf(webInfFldr));
			}
			else if (xslFile.contains(classesFldr))
			{
				tmpXSLFile = xslFile.substring(xslFile.indexOf(classesFldr));
			}

			final String[] filePath = ("\\".equals(File.separator)) ? tmpXSLFile.split("\\\\") : tmpXSLFile.split(File.separator);
			final StringBuilder fileNameBldr = new StringBuilder();
			for (int i = 0; i < filePath.length; i++)
			{
				final String str = filePath[i];
				if ("WEB-INF".equals(str) || "classes".equals(str))
				{
					continue;
				}
				fileNameBldr.append(str);
				if (i < (filePath.length - 1))
				{
					fileNameBldr.append(TransformationConstants.FILE_PATH_SEPERATOR);
				}
			}
			xslFile = fileNameBldr.toString();
		}
		final Source source = new SAXSource(new InputSource(inputStream));
		// URL resourceUrl = classLoader.getResource(xslFile);
		final URL resourceUrl = resourse.getURL();
		if (resourceUrl != null)
		{
			source.setSystemId(resourceUrl.toExternalForm());
			createTransformer(xslFile, source);
			xslURLMap.put(xslFile, resourceUrl);
		}
	}

	private static void createTransformer(final String key, final Source source)
	{

		try
		{
			final Transformer transformer = factory.newTransformer(source);
			//            transformer.setParameter("currentTimeStamp", StringUtil.getISODateTime(currentDate));
			//            transformer.setParameter("BODId", new UID().toString());
			//            transformer.setParameter("EffectivePeriod", StringUtil.getISODate(currentDate));
			//            transformer.setParameter("nextBusinessdayFromTomorrow", StringUtil.getISODate(DateUtil.getNextBusinesDay(1)));
			//            transformer.setParameter("SevenDaysFromToday", StringUtil.getISODate(currentDate,7));

			xslTransformers.put(key, transformer);
		}
		catch (final TransformerConfigurationException e)
		{
			logger.error("Error while creating transformer for:" + key + ":" + e.toString());
			e.printStackTrace();
		}
	}


	/**
	 * Transforms the Request XML into federal Modul iWom Common schema xml
	 * 
	 * @param xslFile
	 *           String xsl file name that needs to e applied on incoming XML to get the FM common XML
	 * @param sourceXml
	 *           String Requested XML string
	 * @return String Requested XML equivalent of Federam mogul formatted XML string
	 * @throws WOMResourceException
	 * @throws TransformerException
	 */
	public static String transformRequest(final String xslFile, final String sourceXml) throws WOMIPOTransformationException,
			WOMResourceException
	{
		String fmCommonModelXml = transform(xslFile, sourceXml, null, null);

		// TODO removing the namespace -- is this needed? chandu
		final String namespaceRemovalXsl = "RemoveNamespaces.xsl";
		fmCommonModelXml = transform(namespaceRemovalXsl, fmCommonModelXml, null, null);
		return fmCommonModelXml;
	}

	/**
	 * Transforms the Xml String into another string using xsl and data files
	 * 
	 * @param xslFile
	 *           String xsl file name
	 * @param sourceXml
	 *           String Source xml file on which xsl file gets applied
	 * @param referencedXml
	 *           String reference file contents
	 * @param fmCommonXml
	 *           String Federal modul common formatted XML contents
	 * @return String formatted String as per the transformation defined in XSl file
	 * @throws WOMResourceException
	 * @throws TransformerException
	 */
	public static String transform(final String xslFile, final String sourceXml, final String referencedXml,
			final String fmCommonXml) throws WOMIPOTransformationException, WOMResourceException
	{

		logger.info("tranforming xsl File :: " + xslFile);
		final Writer out = new StringWriter();

		final Transformer transformer = xslTransformers.get(xslFile);
		try
		{
			synchronized (transformer)
			{

				final StringURIResolver resolver = new StringURIResolver();
				resolver.put("referenceDocument", referencedXml);
				transformer.setURIResolver(resolver);
				final Date currentDate = new Date();
				transformer.setParameter("currentTimeStamp", StringUtil.getISODateTime(currentDate));
				transformer.setParameter("BODId", new UID().toString());
				transformer.setParameter("EffectivePeriod", StringUtil.getISODate(currentDate));
				transformer.setParameter("nextBusinessdayFromTomorrow", StringUtil.getISODate(DateUtil.getNextBusinesDay(1)));
				transformer.setParameter("SevenDaysFromToday", StringUtil.getISODate(currentDate, 7));
				transformer.setParameter("environment", PropertiesUtil.getEnvironment().getDescription());

				transformer.transform(StringUtil.getStringSource(sourceXml), new StreamResult(out));
				transformer.clearParameters();
				transformer.setURIResolver(null);
			}

		}
		catch (final TransformerConfigurationException e)
		{
			logger.error("TransformerConfigurationException::error is ", e);
			throw new WOMResourceException("Error while locating the xslFile::" + xslFile, e);
		}
		catch (final Exception e)
		{
			logger.error("Exception::error is ", e);
			throw new WOMIPOTransformationException("Error while transformation." + e.toString(), e);
		}
		return out.toString();

	}

	public static void loadFilesFromClasspathLocalEnv(final String dirFile)
	{

		try
		{
			final URL dirURL = classLoader.getResource(dirFile);
			if (dirURL != null && "file".equals(dirURL.getProtocol()))
			{
				final File file = new File(dirURL.toURI());
				if (file.isDirectory())
				{
					for (final String subDirFile : file.list())
					{
						loadFilesFromClasspath(new StringBuffer().append(dirFile).append(TransformationConstants.FILE_PATH_SEPERATOR)
								.append(subDirFile).toString());
					}
				}
				else
				{
					createTransformerLocalEnv(dirFile);
				}
			}
		}
		catch (final Exception e)
		{
			logger.warn("Unable to load files from " + dirFile + " :reason: ", e);
		}

	}

	public static void createTransformerLocalEnv(final String xslFile)
	{
		if (!xslFile.endsWith(".xsl") || xslFile.endsWith("Outgoing.xsl"))
		{
			return;
		}
		final Source source = new SAXSource(new InputSource(classLoader.getResourceAsStream(xslFile)));
		source.setSystemId(classLoader.getResource(xslFile).toExternalForm());
		try
		{
			final Date currentDate = new Date();
			final Transformer transformer = factory.newTransformer(source);
			//			transformer.setParameter("currentTimeStamp", StringUtil.getISODateTime(currentDate));
			//			transformer.setParameter("BODId", new UID().toString());
			//			transformer.setParameter("EffectivePeriod", StringUtil.getISODate(currentDate));
			//			transformer.setParameter("nextBusinessdayFromTomorrow", StringUtil.getISODate(DateUtil.getNextBusinesDay(1)));


			xslTransformers.put(xslFile, transformer);
		}
		catch (final TransformerConfigurationException e)
		{
			//e.printStackTrace();
			logger.error("Error while creating transformer for:" + xslFile + ":" + e.toString());
		}

	}

	public static URL getXSLURL(final String xslFile)
	{
		return xslURLMap.get(xslFile);
	}
}

/**
 * Inner class for resolving the references to the documents in xsl file
 * 
 * @author chanac32
 * 
 */
class StringURIResolver implements URIResolver
{
	Map<String, String> documents = new HashMap<String, String>();

	public StringURIResolver put(final String href, final String document)
	{
		documents.put(href, document);
		return this;
	}

	@Override
	public Source resolve(final String href, final String base) throws TransformerException
	{
		final String s = documents.get(href);
		if (s != null)
		{
			try
			{
				return StringUtil.getStringSource(s);
			}
			catch (final Exception e)
			{

				throw new TransformerException("Error while creating Strem source:Exception::" + e.toString(), e);
			}
		}
		return null;
	}
}
