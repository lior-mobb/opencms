/*
 * File   : $Source: /alkacon/cvs/opencms/src/com/opencms/workplace/Attic/CmsWorkplaceDefault.java,v $
 * Date   : $Date: 2000/03/16 19:21:04 $
 * Version: $Revision: 1.16 $
 *
 * Copyright (C) 2000  The OpenCms Group 
 * 
 * This File is part of OpenCms -
 * the Open Source Content Mananagement System
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * For further information about OpenCms, please see the
 * OpenCms Website: http://www.opencms.com
 * 
 * You should have received a copy of the GNU General Public License
 * long with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package com.opencms.workplace;

import com.opencms.file.*;
import com.opencms.core.*;
import com.opencms.util.*;
import com.opencms.template.*;

import java.util.*;

import javax.servlet.http.*;

/**
 * Common template class for displaying OpenCms workplace screens.
 * <P>
 * Reads template files of the content type <code>CmsXmlWpTemplateFile</code>.
 * <P>
 * Most special workplace classes may extend this class.
 * 
 * @author Alexander Lucas
 * @version $Revision: 1.16 $ $Date: 2000/03/16 19:21:04 $
 * @see com.opencms.workplace.CmsXmlWpTemplateFile
 */
public class CmsWorkplaceDefault extends CmsXmlTemplate implements I_CmsWpConstants {
    
    /** URL of the pics folder in the webserver's docroot */
    private String m_picsurl = null;
    
    /** URL of the help folder */
    private String m_helpfolder = null;

    /** Reference to the config file */
    private CmsXmlWpConfigFile m_configFile = null;    

    /** Constant for the current language
     * HACK: replace this by the corresponding value from the user object
     */
    private final static String C_CURRENT_LANGUAGE = "de";
    
    /**
     * Indicates if the results of this class are cacheable.
     * 
     * @param cms A_CmsObject Object for accessing system resources
     * @param templateFile Filename of the template file 
     * @param elementName Element name of this template in our parent template.
     * @param parameters Hashtable with all template class parameters.
     * @param templateSelector template section that should be processed.
     * @return <EM>true</EM> if cacheable, <EM>false</EM> otherwise.
     */
    public boolean isCacheable(A_CmsObject cms, String templateFile, String elementName, Hashtable parameters, String templateSelector) {
        return true;
    }

    /**
     * Gets the key that should be used to cache the results of
     * this template class. 
     * 
     * @param cms A_CmsObject Object for accessing system resources
     * @param templateFile Filename of the template file 
     * @param parameters Hashtable with all template class parameters.
     * @param templateSelector template section that should be processed.
     * @return key that can be used for caching
     */
    public Object getKey(A_CmsObject cms, String templateFile, Hashtable parameters, String templateSelector) {
        //Vector v = new Vector();
        A_CmsRequestContext reqContext = cms.getRequestContext();
        
        //v.addElement(templateFile);
        //v.addElement(parameters);
        //v.addElement(templateSelector);
        //return v;
        String result = reqContext.currentProject().getName() + templateFile;
        Enumeration keys = parameters.keys();
        while(keys.hasMoreElements()) {
            String key = (String)keys.nextElement();
            result = result + key + parameters.get(key);            
        }
        result = result + templateSelector;
        return result;        
    }    
    
    /**
     * Reads in the template file and starts the XML parser for the expected
     * content type <class>CmsXmlWpTemplateFile</code>
     * 
     * @param cms A_CmsObject Object for accessing system resources.
     * @param templateFile Filename of the template file.
     * @param elementName Element name of this template in our parent template.
     * @param parameters Hashtable with all template class parameters.
     * @param templateSelector template section that should be processed.
     */
    public CmsXmlTemplateFile getOwnTemplateFile(A_CmsObject cms, String templateFile, String elementName, Hashtable parameters, String templateSelector) throws CmsException {
        CmsXmlWpTemplateFile xmlTemplateDocument = new CmsXmlWpTemplateFile(cms, templateFile);       
        return xmlTemplateDocument;
    }        

    /**
     * User method to generate an URL for the system pics folder.
     * <P>
     * All pictures should reside in the docroot of the webserver for
     * performance reasons. This folder can be mounted into the OpenCms system to 
     * make it accessible for the OpenCms explorer.
     * <P>
     * The path to the docroot can be set in the workplace ini.
     * <P>
     * In any workplace template file, this method can be invoked by
     * <code>&lt;METHOD name="picsUrl"&gt;<em>PictureName</em>&lt;/METHOD&gt;</code>.
     * <P>
     * <b>Warning:</b> Using this method, only workplace pictures, usually residing
     * in the <code>pics/system/</code> folder, can be accessed. In any workplace class 
     * template pictures can be accessed via <code>commonPicsUrl</code>.
     * 
     * @param cms A_CmsObject Object for accessing system resources.
     * @param tagcontent Unused in this special case of a user method. Can be ignored.
     * @param doc Reference to the A_CmsXmlContent object of the initiating XLM document <em>(not used here)</em>.  
     * @param userObj Hashtable with parameters <em>(not used here)</em>.
     * @return String with the pics URL.
     * @exception CmsException
     * @see #commonPicsUrl
     */    
    public Object picsUrl(A_CmsObject cms, String tagcontent, A_CmsXmlContent doc, Object userObj) 
            throws CmsException {
        if(m_picsurl == null) {            
            CmsXmlWpConfigFile configFile = new CmsXmlWpConfigFile(cms);
            m_picsurl = configFile.getWpPictureUrl();
        }
        return m_picsurl + tagcontent;
    }

    /**
     * User method to generate an URL for the commom template pics folder.
     * <P>
     * All pictures should reside in the docroot of the webserver for
     * performance reasons. This folder can be mounted into the OpenCms system to 
     * make it accessible for the OpenCms explorer.
     * <P>
     * The path to the docroot can be set in the workplace ini.
     * <P>
     * In any workplace template file, this method can be invoked by
     * <code>&lt;METHOD name="picsUrl"&gt;<em>PictureName</em>&lt;/METHOD&gt;</code>.
     * 
     * @param cms A_CmsObject Object for accessing system resources.
     * @param tagcontent Unused in this special case of a user method. Can be ignored.
     * @param doc Reference to the A_CmsXmlContent object of the initiating XLM document <em>(not used here)</em>.  
     * @param userObj Hashtable with parameters <em>(not used here)</em>.
     * @return String with the pics URL.
     * @exception CmsException
     */    
    public Object commonPicsUrl(A_CmsObject cms, String tagcontent, A_CmsXmlContent doc, Object userObj) 
            throws CmsException {
        if(m_picsurl == null) {            
            CmsXmlWpConfigFile configFile = new CmsXmlWpConfigFile(cms);
            m_picsurl = configFile.getCommonPictureUrl();
        }
        return m_picsurl + tagcontent;
    }
        
    /**
     * User method to generate an URL for a help file.
     * The system help file path and the currently selected language will
     * be considered.
     * <P>
     * The path to the help file folder can be set in the workplace ini.
     * <P>
     * In any workplace template file, this method can be invoked by
     * <code>&lt;METHOD name="helpUrl"&gt;<em>HelpFileName</em>&lt;/METHOD&gt;</code>.
     * 
     * @param cms A_CmsObject Object for accessing system resources.
     * @param tagcontent Unused in this special case of a user method. Can be ignored.
     * @param doc Reference to the A_CmsXmlContent object of the initiating XLM document <em>(not used here)</em>.  
     * @param userObj Hashtable with parameters <em>(not used here)</em>.
     * @return String with the pics URL.
     * @exception CmsException
     */    
    public Object helpUrl(A_CmsObject cms, String tagcontent, A_CmsXmlContent doc, Object userObj) 
            throws CmsException {        
        if(m_helpfolder == null) { 
            m_helpfolder = getConfigFile(cms).getHelpPath();
        }
        A_CmsRequestContext reqCont = cms.getRequestContext();
        String servletPath = ((HttpServletRequest)reqCont.getRequest().getOriginalRequest()).getServletPath();
        String currentLanguage = C_CURRENT_LANGUAGE.toLowerCase();
        char separator = m_picsurl.charAt(m_picsurl.length()-1);
        return servletPath + m_helpfolder + currentLanguage + separator + tagcontent;
    }    
    
    /**
     * Gets a reference to the default config file.
     * The path to this file ist stored in <code>C_WORKPLACE_INI</code>
     * 
     * @param cms A_CmsObject Object for accessing system resources.
     * @return Reference to the config file.
     * @exception CmsException
     */
    public CmsXmlWpConfigFile getConfigFile(A_CmsObject cms) throws CmsException {
        //if(m_configFile == null) {
            m_configFile = new CmsXmlWpConfigFile(cms);
        //}
        return m_configFile;
    }
    
    /**
     * Gets all fonts available in the workplace screens.
     * <P>
     * The given vectors <code>names</code> and <code>values</code> will 
     * be filled with the appropriate information to be used for building
     * a select box.
     * <P>
     * Used to build font select boxes in editors.
     * 
     * @param cms A_CmsObject Object for accessing system resources.
     * @param lang reference to the currently valid language file
     * @param names Vector to be filled with the appropriate values in this method.
     * @param values Vector to be filled with the appropriate values in this method.
     * @param parameters Hashtable containing all user parameters <em>(not used here)</em>.
     * @return Index representing the user's current workplace view in the vectors.
     * @exception CmsException
     */
    public Integer getFonts(A_CmsObject cms, CmsXmlLanguageFile lang, Vector names, Vector values, Hashtable parameters) 
            throws CmsException {
        getConstantSelectEntries(names, values, C_SELECTBOX_FONTS, lang);
        return new Integer(0);
    }

    /** Gets all fonts available in the workplace screens.
     * <P>
     * The given vectors <code>names</code> and <code>values</code> will 
     * be filled with the appropriate information to be used for building
     * a select box.
     * <P>
     * Used to build font select boxes in editors.
     * 
     * @param cms A_CmsObject Object for accessing system resources.
     * @param lang reference to the currently valid language file
     * @param names Vector to be filled with the appropriate values in this method.
     * @param values Vector to be filled with the appropriate values in this method.
     * @param parameters Hashtable containing all user parameters <em>(not used here)</em>.
     * @return Index representing the user's current workplace view in the vectors.
     * @exception CmsException
     */
    public Integer getFontStyles(A_CmsObject cms, CmsXmlLanguageFile lang, Vector names, Vector values, Hashtable parameters) 
            throws CmsException {
        getConstantSelectEntries(names, values, C_SELECTBOX_FONTSTYLES, lang);
        return new Integer(0);
    }

    /** Gets all fonts available in the workplace screens.
     * <P>
     * The given vectors <code>names</code> and <code>values</code> will 
     * be filled with the appropriate information to be used for building
     * a select box.
     * <P>
     * Used to build font select boxes in editors.
     * 
     * @param cms A_CmsObject Object for accessing system resources.
     * @param lang reference to the currently valid language file
     * @param names Vector to be filled with the appropriate values in this method.
     * @param values Vector to be filled with the appropriate values in this method.
     * @param parameters Hashtable containing all user parameters <em>(not used here)</em>.
     * @return Index representing the user's current workplace view in the vectors.
     * @exception CmsException
     */
    public Integer getFontSizes(A_CmsObject cms, CmsXmlLanguageFile lang, Vector names, Vector values, Hashtable parameters) 
            throws CmsException {
        getConstantSelectEntries(names, values, C_SELECTBOX_FONTSIZES, lang);
        return new Integer(0);
    }
    
    /**
     * Checks if the current project is <STRONG>not</STRONG> the "Online" project.
     * <P>
     * This method is used by workplace icons to decide whether the icon should 
     * be activated or not. Icons will use this method if the attribute <code>method="isNotOnlineProject"</code>
     * is defined in the <code>&lt;ICON&gt;</code> tag.
     * 
     * @param cms A_CmsObject Object for accessing system resources <em>(not used here)</em>.
     * @param lang reference to the currently valid language file <em>(not used here)</em>.
     * @param parameters Hashtable containing all user parameters <em>(not used here)</em>.
     * @return <code>true</code> if the current project is the online project, <code>false</code> otherwise.
     * @exception CmsException if there were errors while accessing project data.
     */
    public Boolean isNotOnlineProject(A_CmsObject cms, CmsXmlLanguageFile lang, Hashtable parameters) 
            throws CmsException {           
        A_CmsRequestContext reqCont = cms.getRequestContext();
        return new Boolean(!reqCont.currentProject().equals(cms.onlineProject()));
    }    

    /**
     * Used by workplace icons to decide whether the icon should 
     * be activated or not. Icons will use this method if the attribute <code>method="doNotShow"</code>
     * is defined in the <code>&lt;ICON&gt;</code> tag.
     * <P>
     * This method always returns <code>false</code> thus icons controlled by
     * this method will never be activated.
     * 
     * @param cms A_CmsObject Object for accessing system resources <em>(not used here)</em>.
     * @param lang reference to the currently valid language file <em>(not used here)</em>.
     * @param parameters Hashtable containing all user parameters <em>(not used here)</em>.
     * @return <code>false</code>.
     */
    public Boolean doNotShow(A_CmsObject cms, CmsXmlLanguageFile lang, Hashtable parameters) {
        return new Boolean(false);
    }    
    
    /**
     * Help method used to fill the vectors returned to 
     * <code>CmsSelectBox</code> with constant values.
     * @param names Vector to be filled with the appropriate values in this method.
     * @param values Vector to be filled with the appropriate values in this method.
     * @param content String array containing the elements to be set.
     * @param lang reference to the currently valid language file
     */
    protected void getConstantSelectEntries(Vector names, Vector values, String[] contents, CmsXmlLanguageFile lang) 
            throws CmsException {
        for(int i=0; i<contents.length; i++) {
            String value = contents[i];
            values.addElement(value);
            if(lang.hasLanguageValue("select." + value)) {   
                names.addElement(lang.getLanguageValue("select." + value));
            } else {
                names.addElement(value);
            }
        }
    }            
}
